package main.java.br.com.bytebank.domain.account;

import main.java.br.com.bytebank.domain.client.ClientData;

public record OpenedAccountDetails(Integer number, ClientData clientData) {
}
