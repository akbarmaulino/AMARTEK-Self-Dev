// Data structure
let data = {
    tasks: [],
    bids: [],
    submissions: []
};

let currentRole = 'customer';
let currentUser = {
    customer: 'Customer User',
    worker: 'Worker User',
    admin: 'Admin User'
};

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    loadData();
    switchRole('customer');
    
    // Setup form handlers
    document.getElementById('taskForm').addEventListener('submit', handleSubmitTask);
    document.getElementById('bidForm').addEventListener('submit', handleSubmitBid);
    document.getElementById('submitWorkForm').addEventListener('submit', handleSubmitWork);
    document.getElementById('revisionForm').addEventListener('submit', handleSubmitRevision);
});

// Load and Save Data
function loadData() {
    const saved = localStorage.getItem('jokiMarketplaceV2');
    if (saved) {
        data = JSON.parse(saved);
    }
}

function saveData() {
    localStorage.setItem('jokiMarketplaceV2', JSON.stringify(data));
}

// Role Management
function switchRole(role) {
    currentRole = role;
    
    // Hide all pages
    document.querySelectorAll('.page').forEach(page => {
        page.classList.remove('active');
    });
    
    // Show selected page
    document.getElementById(role).classList.add('active');
    
    // Update user display
    document.getElementById('currentUserDisplay').textContent = 
        `${role.charAt(0).toUpperCase() + role.slice(1)}: ${currentUser[role]}`;
    
    // Render appropriate content
    renderAll();
}

function renderAll() {
    if (currentRole === 'customer') {
        renderCustomerTasks();
    } else if (currentRole === 'worker') {
        renderAvailableTasks();
        renderWorkerBids();
        renderWorkerTasks();
    } else if (currentRole === 'admin') {
        renderPendingTasks();
        renderApprovedTasks();
        renderActiveTasks();
    }
}

// ===== CUSTOMER FUNCTIONS =====

function handleSubmitTask(e) {
    e.preventDefault();
    
    const task = {
        id: Date.now(),
        customerId: currentUser.customer,
        title: document.getElementById('taskTitle').value,
        description: document.getElementById('taskDesc').value,
        requirements: document.getElementById('taskReq').value,
        deadline: document.getElementById('taskDeadline').value,
        budget: parseInt(document.getElementById('taskBudget').value),
        status: 'pending_approval',
        revisionCount: 0,
        createdAt: new Date().toISOString()
    };
    
    data.tasks.push(task);
    saveData();
    
    e.target.reset();
    alert('Task berhasil disubmit! Menunggu approval admin.');
    renderAll();
}

function renderCustomerTasks() {
    const container = document.getElementById('customerTasksList');
    const myTasks = data.tasks.filter(t => t.customerId === currentUser.customer);
    
    if (myTasks.length === 0) {
        container.innerHTML = '<div class="empty-state"><p>Anda belum submit task apapun</p></div>';
        return;
    }
    
    container.innerHTML = myTasks.map(task => {
        let statusText = getStatusText(task.status);
        let statusClass = task.status.replace('_', '-');
        
        return `
            <div class="task-card ${statusClass}">
                <h4>${task.title}</h4>
                <p><strong>Deskripsi:</strong> ${task.description}</p>
                <p><strong>Budget:</strong> Rp ${formatNumber(task.budget)}</p>
                <span class="status-badge ${statusClass}">${statusText}</span>
                ${task.revisionCount > 0 ? `
                    <span class="revision-counter">🔄 Revisi: ${task.revisionCount}/2</span>
                ` : ''}
                <div class="meta-row">
                    <span class="meta-item">📅 Deadline: ${formatDate(task.deadline)}</span>
                    <span class="meta-item">📝 Dibuat: ${formatDateTime(task.createdAt)}</span>
                </div>
                ${renderCustomerTaskActions(task)}
            </div>
        `;
    }).join('');
}

function renderCustomerTaskActions(task) {
    if (task.status === 'submitted' || task.status === 'revision') {
        const submission = data.submissions
            .filter(s => s.taskId === task.id)
            .sort((a, b) => new Date(b.submittedAt) - new Date(a.submittedAt))[0];
        
        if (submission) {
            return `
                <div class="work-submission">
                    <h5>📦 Hasil dari Worker:</h5>
                    <p><strong>Catatan:</strong> ${submission.notes}</p>
                    <p><strong>Link:</strong> <a href="${submission.link}" target="_blank">${submission.link}</a></p>
                    <p style="font-size: 12px; color: #9ca3af;">Disubmit: ${formatDateTime(submission.submittedAt)}</p>
                </div>
                <div class="actions">
                    <button class="btn btn-success btn-small" onclick="approveWork(${task.id})">
                        ✅ Approve & Selesai
                    </button>
                    ${task.revisionCount < 2 ? `
                        <button class="btn btn-warning btn-small" onclick="requestRevision(${task.id})">
                            🔄 Request Revisi (${2 - task.revisionCount} tersisa)
                        </button>
                    ` : `
                        <p style="color: #ef4444; font-size: 13px; margin-top: 8px;">⚠️ Revisi maksimal tercapai. Silakan approve atau hubungi admin.</p>
                    `}
                </div>
            `;
        }
    }
    return '';
}

function approveWork(taskId) {
    if (!confirm('Approve hasil kerja ini dan tandai task sebagai selesai?')) return;
    
    const task = data.tasks.find(t => t.id === taskId);
    if (task) {
        task.status = 'completed';
        task.completedAt = new Date().toISOString();
        saveData();
        alert('Task selesai! Terima kasih.');
        renderAll();
    }
}

function requestRevision(taskId) {
    const revisionNote = prompt('Masukkan catatan revisi untuk worker:');
    if (!revisionNote) return;
    
    const task = data.tasks.find(t => t.id === taskId);
    if (task) {
        task.status = 'revision';
        task.revisionCount++;
        task.revisionNote = revisionNote;
        task.revisionRequestedAt = new Date().toISOString();
        saveData();
        alert(`Revisi direquest (${task.revisionCount}/2). Worker akan mengerjakan revisi.`);
        renderAll();
    }
}

// ===== WORKER FUNCTIONS =====

function renderAvailableTasks() {
    const container = document.getElementById('availableTasks');
    const availableTasks = data.tasks.filter(t => t.status === 'approved');
    
    if (availableTasks.length === 0) {
        container.innerHTML = '<div class="empty-state"><p>Tidak ada task tersedia untuk bid saat ini</p></div>';
        return;
    }
    
    container.innerHTML = availableTasks.map(task => {
        const taskBids = data.bids.filter(b => b.taskId === task.id);
        const myBid = taskBids.find(b => b.workerId === currentUser.worker);
        
        return `
            <div class="task-card approved">
                <h4>${task.title}</h4>
                <p><strong>Deskripsi:</strong> ${task.description}</p>
                <p><strong>Requirements:</strong> ${task.requirements}</p>
                <p><strong>Budget Max:</strong> Rp ${formatNumber(task.budget)}</p>
                <div class="meta-row">
                    <span class="meta-item">📅 Deadline: ${formatDate(task.deadline)}</span>
                    <span class="meta-item">💼 ${taskBids.length} bids</span>
                </div>
                ${myBid ? `
                    <div class="alert alert-info" style="margin-top: 12px;">
                        ✅ Anda sudah bid: Rp ${formatNumber(myBid.amount)}
                    </div>
                ` : `
                    <div class="actions">
                        <button class="btn btn-success btn-small" onclick="openBidModal(${task.id})">
                            💰 Submit Bid
                        </button>
                    </div>
                `}
            </div>
        `;
    }).join('');
}

function renderWorkerBids() {
    const container = document.getElementById('workerBids');
    const myBids = data.bids.filter(b => b.workerId === currentUser.worker);
    
    if (myBids.length === 0) {
        container.innerHTML = '<div class="empty-state"><p>Anda belum submit bid apapun</p></div>';
        return;
    }
    
    container.innerHTML = myBids.map(bid => {
        const task = data.tasks.find(t => t.id === bid.taskId);
        if (!task) return '';
        
        return `
            <div class="bid-card">
                <h5>${task.title}</h5>
                <p class="bid-amount">Bid: Rp ${formatNumber(bid.amount)}</p>
                <p><strong>Proposal:</strong> ${bid.proposal}</p>
                <span class="status-badge ${bid.status}">${getStatusText(bid.status)}</span>
                <p style="font-size: 12px; color: #9ca3af; margin-top: 8px;">
                    Disubmit: ${formatDateTime(bid.submittedAt)}
                </p>
            </div>
        `;
    }).join('');
}

function renderWorkerTasks() {
    const container = document.getElementById('workerTasks');
    const myTasks = data.tasks.filter(t => 
        t.workerId === currentUser.worker && 
        ['assigned', 'submitted', 'revision'].includes(t.status)
    );
    
    if (myTasks.length === 0) {
        container.innerHTML = '<div class="empty-state"><p>Anda belum memiliki task yang diassign</p></div>';
        return;
    }
    
    container.innerHTML = myTasks.map(task => {
        const latestSubmission = data.submissions
            .filter(s => s.taskId === task.id)
            .sort((a, b) => new Date(b.submittedAt) - new Date(a.submittedAt))[0];
        
        return `
            <div class="task-card ${task.status}">
                <h4>${task.title}</h4>
                <p><strong>Deskripsi:</strong> ${task.description}</p>
                <p><strong>Requirements:</strong> ${task.requirements}</p>
                <p><strong>Bayaran:</strong> Rp ${formatNumber(task.assignedBidAmount)}</p>
                ${task.status === 'revision' && task.revisionNote ? `
                    <div class="alert alert-warning" style="margin-top: 12px;">
                        <strong>⚠️ Revisi Diminta:</strong><br>
                        ${task.revisionNote}
                    </div>
                    <span class="revision-counter">🔄 Revisi ke-${task.revisionCount}</span>
                ` : ''}
                <span class="status-badge ${task.status}">${getStatusText(task.status)}</span>
                <div class="meta-row">
                    <span class="meta-item">📅 Deadline: ${formatDate(task.deadline)}</span>
                    ${latestSubmission ? `
                        <span class="meta-item">📤 Submit terakhir: ${formatDateTime(latestSubmission.submittedAt)}</span>
                    ` : ''}
                </div>
                ${(task.status === 'assigned' || task.status === 'revision') ? `
                    <div class="actions">
                        ${task.status === 'revision' ? `
                            <button class="btn btn-warning btn-small" onclick="openRevisionModal(${task.id})">
                                🔄 Submit Revisi
                            </button>
                        ` : `
                            <button class="btn btn-success btn-small" onclick="openSubmitWorkModal(${task.id})">
                                📤 Submit Hasil Kerja
                            </button>
                        `}
                    </div>
                ` : task.status === 'submitted' ? `
                    <div class="alert alert-info" style="margin-top: 12px;">
                        ⏳ Menunggu review dari customer...
                    </div>
                ` : ''}
            </div>
        `;
    }).join('');
}

// Bid Modal
function openBidModal(taskId) {
    document.getElementById('bidTaskId').value = taskId;
    document.getElementById('bidModal').classList.add('active');
}

function closeBidModal() {
    document.getElementById('bidModal').classList.remove('active');
    document.getElementById('bidForm').reset();
}

function handleSubmitBid(e) {
    e.preventDefault();
    
    const bid = {
        id: Date.now(),
        taskId: parseInt(document.getElementById('bidTaskId').value),
        workerId: currentUser.worker,
        workerName: document.getElementById('bidWorkerName').value,
        workerEmail: document.getElementById('bidWorkerEmail').value,
        workerPhone: document.getElementById('bidWorkerPhone').value,
        amount: parseInt(document.getElementById('bidAmount').value),
        proposal: document.getElementById('bidProposal').value,
        status: 'pending',
        submittedAt: new Date().toISOString()
    };
    
    data.bids.push(bid);
    saveData();
    
    closeBidModal();
    alert('Bid berhasil disubmit!');
    renderAll();
}

// Submit Work Modal
function openSubmitWorkModal(taskId) {
    document.getElementById('submitTaskId').value = taskId;
    document.getElementById('submitWorkModal').classList.add('active');
}

function closeSubmitWorkModal() {
    document.getElementById('submitWorkModal').classList.remove('active');
    document.getElementById('submitWorkForm').reset();
}

function handleSubmitWork(e) {
    e.preventDefault();
    
    const taskId = parseInt(document.getElementById('submitTaskId').value);
    const task = data.tasks.find(t => t.id === taskId);
    
    if (task) {
        const submission = {
            id: Date.now(),
            taskId: taskId,
            workerId: currentUser.worker,
            notes: document.getElementById('submitNotes').value,
            link: document.getElementById('submitLink').value,
            submittedAt: new Date().toISOString(),
            isRevision: false
        };
        
        data.submissions.push(submission);
        task.status = 'submitted';
        
        saveData();
        closeSubmitWorkModal();
        alert('Hasil kerja berhasil disubmit! Menunggu review customer.');
        renderAll();
    }
}

// Revision Modal
function openRevisionModal(taskId) {
    document.getElementById('revisionTaskId').value = taskId;
    document.getElementById('revisionModal').classList.add('active');
}

function closeRevisionModal() {
    document.getElementById('revisionModal').classList.remove('active');
    document.getElementById('revisionForm').reset();
}

function handleSubmitRevision(e) {
    e.preventDefault();
    
    const taskId = parseInt(document.getElementById('revisionTaskId').value);
    const task = data.tasks.find(t => t.id === taskId);
    
    if (task) {
        const submission = {
            id: Date.now(),
            taskId: taskId,
            workerId: currentUser.worker,
            notes: document.getElementById('revisionNotes').value,
            link: document.getElementById('revisionLink').value,
            submittedAt: new Date().toISOString(),
            isRevision: true,
            revisionNumber: task.revisionCount
        };
        
        data.submissions.push(submission);
        task.status = 'submitted';
        task.revisionNote = null;
        
        saveData();
        closeRevisionModal();
        alert('Revisi berhasil disubmit! Menunggu review customer.');
        renderAll();
    }
}

// ===== ADMIN FUNCTIONS =====

function renderPendingTasks() {
    const container = document.getElementById('pendingTasks');
    const pending = data.tasks.filter(t => t.status === 'pending_approval');
    
    if (pending.length === 0) {
        container.innerHTML = '<div class="empty-state"><p>Tidak ada task menunggu approval</p></div>';
        return;
    }
    
    container.innerHTML = pending.map(task => `
        <div class="task-card pending-approval">
            <h4>${task.title}</h4>
            <p><strong>Customer:</strong> ${task.customerId}</p>
            <p><strong>Deskripsi:</strong> ${task.description}</p>
            <p><strong>Requirements:</strong> ${task.requirements}</p>
            <p><strong>Budget:</strong> Rp ${formatNumber(task.budget)}</p>
            <div class="meta-row">
                <span class="meta-item">📅 Deadline: ${formatDate(task.deadline)}</span>
                <span class="meta-item">📝 Disubmit: ${formatDateTime(task.createdAt)}</span>
            </div>
            <div class="actions">
                <button class="btn btn-success btn-small" onclick="approveTask(${task.id})">
                    ✅ Approve Task
                </button>
                <button class="btn btn-danger btn-small" onclick="rejectTask(${task.id})">
                    ❌ Reject
                </button>
            </div>
        </div>
    `).join('');
}

function renderApprovedTasks() {
    const container = document.getElementById('approvedTasks');
    const approved = data.tasks.filter(t => t.status === 'approved');
    
    if (approved.length === 0) {
        container.innerHTML = '<div class="empty-state"><p>Tidak ada task dalam proses bidding</p></div>';
        return;
    }
    
    container.innerHTML = approved.map(task => {
        const taskBids = data.bids.filter(b => b.taskId === task.id);
        
        return `
            <div class="task-card approved">
                <h4>${task.title}</h4>
                <p><strong>Customer:</strong> ${task.customerId}</p>
                <p><strong>Budget:</strong> Rp ${formatNumber(task.budget)}</p>
                <span class="status-badge bidding">🔥 ${taskBids.length} Bids Masuk</span>
                <div class="meta-row">
                    <span class="meta-item">📅 Deadline: ${formatDate(task.deadline)}</span>
                </div>
                <div class="actions">
                    <button class="btn btn-primary btn-small" onclick="viewTaskBids(${task.id})">
                        👀 Lihat & Pilih Bid (${taskBids.length})
                    </button>
                </div>
            </div>
        `;
    }).join('');
}

function renderActiveTasks() {
    const container = document.getElementById('activeTasks');
    const active = data.tasks.filter(t => 
        ['assigned', 'submitted', 'revision'].includes(t.status)
    );
    
    if (active.length === 0) {
        container.innerHTML = '<div class="empty-state"><p>Tidak ada task aktif</p></div>';
        return;
    }
    
    container.innerHTML = active.map(task => `
        <div class="task-card ${task.status}">
            <h4>${task.title}</h4>
            <p><strong>Customer:</strong> ${task.customerId}</p>
            <p><strong>Worker:</strong> ${task.workerId || '-'}</p>
            <p><strong>Budget:</strong> Rp ${formatNumber(task.assignedBidAmount || 0)}</p>
            <span class="status-badge ${task.status}">${getStatusText(task.status)}</span>
            ${task.revisionCount > 0 ? `
                <span class="revision-counter">🔄 Revisi: ${task.revisionCount}/2</span>
            ` : ''}
            <div class="meta-row">
                <span class="meta-item">📅 Deadline: ${formatDate(task.deadline)}</span>
            </div>
        </div>
    `).join('');
}

function approveTask(taskId) {
    if (!confirm('Approve task ini? (Pastikan customer sudah bayar)')) return;
    
    const task = data.tasks.find(t => t.id === taskId);
    if (task) {
        task.status = 'approved';
        task.approvedAt = new Date().toISOString();
        task.approvedBy = currentUser.admin;
        saveData();
        alert('Task approved! Sekarang worker bisa bid.');
        renderAll();
    }
}

function rejectTask(taskId) {
    const reason = prompt('Alasan reject:');
    if (!reason) return;
    
    const task = data.tasks.find(t => t.id === taskId);
    if (task) {
        task.status = 'rejected';
        task.rejectedAt = new Date().toISOString();
        task.rejectedBy = currentUser.admin;
        task.rejectReason = reason;
        saveData();
        alert('Task rejected.');
        renderAll();
    }
}

function viewTaskBids(taskId) {
    const task = data.tasks.find(t => t.id === taskId);
    const taskBids = data.bids.filter(b => b.taskId === taskId);
    
    const container = document.getElementById('bidsContainer');
    
    if (taskBids.length === 0) {
        container.innerHTML = '<div class="empty-state"><p>Belum ada bid</p></div>';
    } else {
        container.innerHTML = `
            <h4 style="margin-bottom: 15px;">${task.title}</h4>
            <p style="color: #6b7280; margin-bottom: 20px;">Budget: Rp ${formatNumber(task.budget)}</p>
            ${taskBids.map(bid => `
                <div class="bid-card">
                    <h5>${bid.workerName}</h5>
                    <p><strong>Email:</strong> ${bid.workerEmail}</p>
                    <p><strong>Phone:</strong> ${bid.workerPhone}</p>
                    <p class="bid-amount">Bid: Rp ${formatNumber(bid.amount)}</p>
                    <p><strong>Proposal:</strong> ${bid.proposal}</p>
                    <p style="font-size: 12px; color: #9ca3af;">Disubmit: ${formatDateTime(bid.submittedAt)}</p>
                    ${bid.status === 'pending' ? `
                        <div class="actions">
                            <button class="btn btn-success btn-small" onclick="acceptBid(${bid.id})">
                                ✅ Pilih Bid Ini
                            </button>
                        </div>
                    ` : `
                        <span class="status-badge ${bid.status}">${getStatusText(bid.status)}</span>
                    `}
                </div>
            `).join('')}
        `;
    }
    
    document.getElementById('viewBidsModal').classList.add('active');
}

function closeViewBidsModal() {
    document.getElementById('viewBidsModal').classList.remove('active');
}

function acceptBid(bidId) {
    if (!confirm('Pilih bid ini sebagai pemenang?')) return;
    
    const bid = data.bids.find(b => b.id === bidId);
    if (!bid) return;
    
    const task = data.tasks.find(t => t.id === bid.taskId);
    if (!task) return;
    
    // Update bid status
    bid.status = 'accepted';
    
    // Update task
    task.status = 'assigned';
    task.workerId = bid.workerId;
    task.assignedBidAmount = bid.amount;
    task.assignedAt = new Date().toISOString();
    
    // Reject other bids
    data.bids.forEach(b => {
        if (b.taskId === bid.taskId && b.id !== bidId) {
            b.status = 'rejected';
        }
    });
    
    saveData();
    closeViewBidsModal();
    alert('Bid diterima! Task telah diassign ke worker.');
    renderAll();
}

// ===== UTILITY FUNCTIONS =====

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('id-ID', { 
        year: 'numeric', 
        month: 'long', 
        day: 'numeric' 
    });
}

function formatDateTime(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('id-ID', { 
        year: 'numeric', 
        month: 'short', 
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

function formatNumber(num) {
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

function getStatusText(status) {
    const statusMap = {
        'pending_approval': 'Menunggu Approval Admin',
        'approved': 'Dibuka untuk Bidding',
        'assigned': 'Sedang Dikerjakan',
        'submitted': 'Menunggu Review Customer',
        'revision': 'Perlu Revisi',
        'completed': 'Selesai',
        'rejected': 'Ditolak',
        'pending': 'Menunggu',
        'accepted': 'Diterima',
        'bidding': 'Proses Bidding'
    };
    return statusMap[status] || status;
}