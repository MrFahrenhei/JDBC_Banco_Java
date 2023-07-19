package br.com.bytebank.domain.client;

import java.util.Objects;

public class Client {
    private String name;
    private String cpf;
    private String email;

    public Client(ClientData data)
    {
        this.name = data.name();
        this.cpf = data.cpf();
        this.email = data.email();
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return cpf.equals(client.cpf);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(cpf);
    }

    public String getName()
    {
        return name;
    }

    public String getCpf()
    {
        return cpf;
    }

    public String getEmail()
    {
        return email;
    }

    @Override
    public String toString() {
       return "Client{" +
               "name='"+name+"'"+
               ", cpf='"+cpf+"'"+
               ", email='"+email+"'"+
               "}";
    }
}
