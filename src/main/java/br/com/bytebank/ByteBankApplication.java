package br.com.bytebank;

import br.com.bytebank.domain.ExceptionRuleNegociation;
import br.com.bytebank.domain.account.AccountService;
import br.com.bytebank.domain.account.OpenedAccountDetails;
import br.com.bytebank.domain.client.ClientData;

import java.util.Scanner;

public class ByteBankApplication {
    private static final AccountService service = new AccountService();
    private static final Scanner keyboard = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {
        var option = renderMenu();
        while (option != 8) {
            try {
                switch (option) {
                    case 1 -> listAccounts();
                    case 2 -> openAccount();
                    case 3 -> endAccount();
                    case 4 -> checkBalance();
                    case 5 -> withdrawBalance();
                    case 6 -> depositBalance();
                    case 7 -> doTransference();
                }
            } catch (ExceptionRuleNegociation e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Pressione qualquer tecla e de enter para voltar ao menu");
                keyboard.next();
            }
            option = renderMenu();
        }
        System.out.println("Finalizado");
    }
    private static int renderMenu()
    {
        System.out.println("""
                 BYTEBANK - ESCOLHA UMA OPÇÃO:
                 1 - Listar contas abertas
                 2 - Abertura de conta
                 3 - Encerramento de conta
                 4 - Consultar saldo de uma conta
                 5 - Realizar saque em uma conta
                 6 - Realizar depósito em uma conta
                 7 - Realizar Transferência
                 8 - Sair
                """);
        return keyboard.nextInt();
    }
    private static void listAccounts()
    {
        System.out.println("Contas abertas");
        var accounts = service.listOpenedAccounts();
        accounts.stream().forEach(System.out::println);
        System.out.println("pressione qualquer tecla para voltar");
        keyboard.next();
    }
    private static void openAccount()
    {
        System.out.println("Digite o número da conta: ");
        var accountNumber = keyboard.nextInt();

        System.out.println("Digite o nome do cliente: ");
        var name = keyboard.next();

        System.out.println("Digite o cpf do cliente");
        var cpf = keyboard.next();

        System.out.println("Digite o email do cliente");
        var email = keyboard.next();

        service.openAccount(new OpenedAccountDetails(accountNumber, new ClientData(name, cpf, email)));

        System.out.println("Conta aberta com sucesso");
        System.out.println("Pressione qualquer tecla para retornar ao menu");
        keyboard.next();
    }
    private static void endAccount()
    {
        System.out.println("Digite o número da conta");
        var accountNumber = keyboard.nextInt();

        service.deleteAccount(accountNumber);

        System.out.println("Conta encerrada com sucesso");
        System.out.println("Pressione qualquer tecla para retornar ao menu");
        keyboard.next();
    }
    private static void checkBalance()
    {
        System.out.println("Digite o número da conta");
        var accountNumber = keyboard.nextInt();
        var balance = service.consultBalance(accountNumber);
        System.out.println("Saldo da conta"+ balance);

        System.out.println("Presisone qualquer tecla para retornar ao menu");
        keyboard.next();
    }
    private static void withdrawBalance()
    {
        System.out.println("Digite o número ad conta");
        var accountNumber = keyboard.nextInt();

        System.out.println("Digite o valor do saque");
        var value = keyboard.nextBigDecimal();

        service.serveWithdraw(accountNumber, value);
        System.out.println("saque realizado com sucesso");
        System.out.println("Pressione qualquer tecla para retornar ao menu");
        keyboard.next();
    }
    private static void depositBalance()
    {
        System.out.println("Digite o número de conta");
        var accountNumber = keyboard.nextInt();

        System.out.println("Digite o valor do deposito");
        var value = keyboard.nextBigDecimal();

        service.doDeposit(accountNumber, value);

        System.out.println("Depósito realizado com sucesso");
        System.out.println("Pressione qualquer tecla para retornar ao menu");
        keyboard.next();
    }
    private static void doTransference() {
        System.out.println("Digite a conta de origem: ");
        var originAccountNumber = keyboard.nextInt();

        System.out.println("Digite a conta que vai receber: ");
        var accountNumber = keyboard.nextInt();

        System.out.println("Valor a ser transferido: ");
        var value = keyboard.nextBigDecimal();

        service.doTransferBalance(originAccountNumber, accountNumber, value);
        System.out.println("Transferência realizado com sucesso");
        System.out.println("Pressione qualquer tecla para retornar ao menu");
        keyboard.next();;
    }
}
