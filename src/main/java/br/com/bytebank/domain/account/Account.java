package br.com.bytebank.domain.account;

import br.com.bytebank.domain.client.Client;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private Integer number;
    private BigDecimal balance;
    private Client titular;

    private Boolean estaAtiva;

    public Account(Integer number, BigDecimal balance, Client titular, Boolean estaAtiva)
    {
        this.number = number;
        this.titular = titular;
        this.balance = balance;
        this.estaAtiva = estaAtiva;
    }

    public boolean hasBalance()
    {
        return this.balance.compareTo(BigDecimal.ZERO) != 0;
    }

    public void withdrawValue(BigDecimal value)
    {
        this.balance = this.balance.subtract(value);
    }
    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return number.equals(account.number);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(number);
    }

    public Integer getNumber()
    {
        return number;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }

    public Client getTitular()
    {
        return titular;
    }

    public Boolean getEstaAtiva() {
        return estaAtiva;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='"+number+"', " +
                "saldo='"+balance+"', " +
                "titular='"+titular+"'" +
                "}";
    }
}
