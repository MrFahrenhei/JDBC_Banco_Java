package br.com.bytebank.domain.account;

import br.com.bytebank.domain.client.Client;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private Integer number;
    private BigDecimal saldo;
    private Client titular;

    public Account(Integer number, Client titular)
    {
        this.number = number;
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
    }

    public boolean hasBalance()
    {
        return this.saldo.compareTo(BigDecimal.ZERO) != 0;
    }

    public void withdrawValue(BigDecimal value)
    {
        this.saldo = this.saldo.subtract(value);
    }

    public void depoist(BigDecimal value)
    {
        this.saldo = this.saldo.add(value);
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

    public BigDecimal getSaldo()
    {
        return saldo;
    }

    public Client getTitular()
    {
        return titular;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='"+number+"', " +
                "saldo='"+saldo+"', " +
                "titular='"+titular+"'" +
                "}";
    }
}
