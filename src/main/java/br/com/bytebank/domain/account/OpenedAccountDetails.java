package br.com.bytebank.domain.account;

import br.com.bytebank.domain.client.ClientData;

public record OpenedAccountDetails(Integer number, ClientData clientData) {
}
