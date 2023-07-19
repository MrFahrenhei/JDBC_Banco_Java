package br.com.bytebank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection recoveryConnection()
    {
        // \sql -> \connect root@localhost:3307 -> psw
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3307/byte_bank?user=root&password=root");
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
