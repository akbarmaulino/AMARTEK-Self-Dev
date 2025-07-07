package com.database

import com.kms.katalon.core.annotation.Keyword
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

public class PostgreDBUtils {
    private static Connection connection = null

    @Keyword
    static def connectDB(String host, String port, String dbName, String username, String password) {
        String url = "jdbc:postgresql://${host}:${port}/${dbName}"
        connection = DriverManager.getConnection(url, username, password)
        println "Connected to PostgreSQL database!"
    }

    @Keyword
    static ResultSet executeQuery(String query) {
        Statement stmt = connection.createStatement()
        return stmt.executeQuery(query)
    }

    @Keyword
    static def closeDB() {
        if (connection != null && !connection.isClosed()) {
            connection.close()
            println "Database connection closed."
        }
    }
}
