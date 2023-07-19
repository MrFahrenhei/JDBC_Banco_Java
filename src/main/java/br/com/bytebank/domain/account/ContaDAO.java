package br.com.bytebank.domain.account;

import br.com.bytebank.domain.client.Client;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContaDAO {
    private Connection conn;
    ContaDAO(Connection connection)
    {
       this.conn = connection;
    }

    public void insert(OpenedAccountDetails accountData)
    {
        var client = new Client(accountData.clientData());
        var conta = new Account(accountData.number(), client);

        String sql = """
                INSERT INTO `conta`(numero, saldo, cliente_nome, cliente_cpf, cliente_email)
                VALUES(?, ?, ?, ?, ?)
                """;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, conta.getNumber());
            stmt.setBigDecimal(2, BigDecimal.ZERO);
            stmt.setString(3, accountData.clientData().name());
            stmt.setString(4, accountData.clientData().cpf());
            stmt.setString(5, accountData.clientData().email());

            stmt.execute();
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
