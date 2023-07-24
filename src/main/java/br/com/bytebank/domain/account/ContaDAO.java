package br.com.bytebank.domain.account;

import br.com.bytebank.domain.client.Client;
import br.com.bytebank.domain.client.ClientData;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {
    private Connection conn;

    ContaDAO(Connection connection) {
        this.conn = connection;
    }

    public void insert(OpenedAccountDetails accountData) {
        var client = new Client(accountData.clientData());
        var conta = new Account(accountData.number(), BigDecimal.ZERO, client, true);

        String sql = """
                INSERT INTO `conta`(numero, saldo, cliente_nome, cliente_cpf, cliente_email, esta_ativa)
                VALUES(?, ?, ?, ?, ?, ?)
                """;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, conta.getNumber());
            stmt.setBigDecimal(2, BigDecimal.ZERO);
            stmt.setString(3, accountData.clientData().name());
            stmt.setString(4, accountData.clientData().cpf());
            stmt.setString(5, accountData.clientData().email());
            stmt.setBoolean(6, true);

            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Set<Account> list() {
        PreparedStatement stmt;
        ResultSet resultSet;
        Set<Account> accounts = new HashSet<>();
        String sql = """
                SELECT * FROM `conta` where esta_ativa = true;
                """;
        try {
            stmt = conn.prepareStatement(sql);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Integer accountNumber = resultSet.getInt(1);
                BigDecimal balance = resultSet.getBigDecimal(2);
                String name = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);
                Boolean estaAtiva = resultSet.getBoolean(6);
                ClientData clientData = new ClientData(name, cpf, email);
                Client client = new Client(clientData);
                accounts.add(new Account(accountNumber, balance, client, estaAtiva));
            }
            resultSet.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

    public void insertBalance(Integer accountNumber, BigDecimal balance) {
        PreparedStatement stmt;
        String sql = "UPDATE `conta` SET saldo = ? WHERE numero = ?";
        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);
            stmt.setBigDecimal(1, balance);
            stmt.setInt(2, accountNumber);
            stmt.execute();
            stmt.close();
            conn.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    public Account searchByAccountNumber(Integer accountNumber) {
        PreparedStatement stmt;
        ResultSet resultSet;
        Account account = null;
        String sql = "SELECT * FROM `conta` WHERE numero = ? AND esta_ativa = true";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountNumber);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Integer accountNumberSelected = resultSet.getInt(1);
                BigDecimal balance = resultSet.getBigDecimal(2);
                String client_name = resultSet.getString(3);
                String client_cpf = resultSet.getString(4);
                String client_email = resultSet.getString(5);
                Boolean estaAtiva = resultSet.getBoolean(6);
                ClientData clientData = new ClientData(client_name, client_cpf, client_email);
                Client client = new Client(clientData);
                account = new Account(accountNumberSelected, balance, client, estaAtiva);
            }
            resultSet.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return account;
    }

    public void deleteAccount(Integer accountNumber) {
        String sql = "UPDATE `conta` SET esta_ativa = ? WHERE numero = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, false);
            stmt.setInt(2, accountNumber);
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
