package br.com.bytebank.domain.account;

import br.com.bytebank.ConnectionFactory;
import br.com.bytebank.domain.ExceptionRuleNegociation;

import java.math.BigDecimal;
import java.sql.Connection;
;
import java.util.Set;

public class AccountService {

    private ConnectionFactory connection;

    public AccountService() {
        this.connection = new ConnectionFactory();
    }

    public Set<Account> listOpenedAccounts() {
        Connection conn = connection.recoveryConnection();
        return new ContaDAO(conn).list();
    }

    public BigDecimal consultBalance(Integer accountNumber) {
        var account = searchAccountByNumber(accountNumber);
        return account.getBalance();
    }

    public void openAccount(OpenedAccountDetails accountData){
        Connection conn = connection.recoveryConnection();
        new ContaDAO(conn).insert(accountData);

    }

    public void serveWithdraw(Integer accountNumber, BigDecimal value) {
        var conta = searchAccountByNumber(accountNumber);

        if (value.compareTo(BigDecimal.ZERO) <= 0)
            throw new ExceptionRuleNegociation("Valor do saque deve ser superior a zero");

        if (value.compareTo(conta.getBalance()) > 0)
            throw new ExceptionRuleNegociation("Saldo insuficiente!");

        if(!conta.getEstaAtiva())
            throw new ExceptionRuleNegociation("Conta não está ativa");

        BigDecimal newValue = conta.getBalance().subtract(value);
        alternValue(conta, newValue);
    }

    public void doDeposit(Integer accountNumber, BigDecimal value) {
        var conta = searchAccountByNumber(accountNumber);
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ExceptionRuleNegociation("Valor do deposito deve ser superior a zero");
        }
        BigDecimal newValue = conta.getBalance().add(value);
        alternValue(conta, newValue);
    }

    public void deleteAccount(Integer accountNumber) {
        var conta = searchAccountByNumber(accountNumber);
        if (conta.hasBalance()) {
            throw new ExceptionRuleNegociation("Conta não deve encerrar com valor positivo");
        }
        Connection conn = connection.recoveryConnection();
        new ContaDAO(conn).deleteAccount(accountNumber);
    }

    private Account searchAccountByNumber(Integer accountNumber) {
        Connection conn = connection.recoveryConnection();
        Account account = new ContaDAO(conn).searchByAccountNumber(accountNumber);
        if(account != null){
            return account;
        }else{
            throw new ExceptionRuleNegociation("Não existe uma conta com esse número");
        }
    }

    public void doTransferBalance(Integer originAccountNumber, Integer accountNumber, BigDecimal value)
    {
        this.serveWithdraw(originAccountNumber, value);
        this.doDeposit(accountNumber, value);
    }
    public void alternValue(Account account, BigDecimal value)
    {
        Connection conn = connection.recoveryConnection();
        new ContaDAO(conn).insertBalance(account.getNumber(), value);
    }
}
