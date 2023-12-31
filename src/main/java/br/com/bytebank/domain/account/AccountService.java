package main.java.br.com.bytebank.domain.account;

import main.java.br.com.bytebank.domain.ExceptionRuleNegociation;
import main.java.br.com.bytebank.domain.client.Client;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class AccountService {
    private Set<Account> accounts = new HashSet<>();
    public Set<Account> listOpenedAccounts()
    {
        return accounts;
    }

    public BigDecimal consultBalance(Integer accountNumber)
    {
        var account = searchAccountByNumber(accountNumber);
        return account.getSaldo();
    }

    public void openAccount(OpenedAccountDetails accountData)
    {
        var client = new Client(accountData.clientData());
        var conta = new Account(accountData.number(), client);
        if(accounts.contains(conta))
        {
            throw new ExceptionRuleNegociation("Já existe outra conta com esse número");
        }
        accounts.add(conta);
    }

    public void serveWithdraw(Integer accountNumber, BigDecimal value)
    {
        var conta = searchAccountByNumber(accountNumber);
        if(value.compareTo(BigDecimal.ZERO) <= 0){
            throw new ExceptionRuleNegociation("Valor do saque deve ser superior a zero");
        }
        if(value.compareTo(conta.getSaldo()) > 0){
            throw new ExceptionRuleNegociation("Saldo insuficiente!");
        }
        conta.withdrawValue(value);
    }

    public void doDeposit(Integer accountNumber, BigDecimal value)
    {
        var conta = searchAccountByNumber(accountNumber);
        if(value.compareTo(BigDecimal.ZERO) <= 0){
            throw new ExceptionRuleNegociation("Valor do deposito deve ser superior a zero");
        }
        conta.depoist(value);
    }

    public void deleteAccount(Integer accountNumber)
    {
        var conta = searchAccountByNumber(accountNumber);
        if(conta.hasBalance()){
            throw new ExceptionRuleNegociation("Conta não deve encerrar com valor positivo");
        }
        accounts.remove(conta);
    }

    private Account searchAccountByNumber(Integer accountNumber)
    {
        return accounts
                .stream()
                .filter(c -> c.getNumber() == accountNumber)
                .findFirst()
                .orElseThrow(() -> new ExceptionRuleNegociation("Não existe uma conta cadastrada com este número"));
    }
}
