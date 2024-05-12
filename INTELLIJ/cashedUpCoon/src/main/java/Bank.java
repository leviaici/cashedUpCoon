import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Bank {
    private static Bank instance = null;
    private Scanner scanner;
    private HashMap<String, Client> clients = new HashMap<>();

    private Bank() {
        scanner = new Scanner(System.in);
    }

    public static Bank getInstance() {
        if (instance == null)
            instance = new Bank();
        return instance;
    }

    public void initiateClients() {
        clients = CSVManager.readClientCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/clients_test.csv");
        String filePath = "/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/";
        for (String phoneNumber : clients.keySet()) {
            String path = filePath + phoneNumber;
            ArrayList<Transaction> transactions = CSVManager.readTransactionCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/transactions_test.csv", phoneNumber);
            ArrayList<Account> accounts = CSVManager.readAccountCSV(path + "/accounts.csv", phoneNumber);
            ArrayList<SavingsAccount> savingsAccounts = CSVManager.readSavingsAccountCSV(path + "/savings_accounts.csv", phoneNumber);
            ArrayList<Account> allAccounts = new ArrayList<>();
            ArrayList<DebitCard> debitCards = CSVManager.readDebitCardCSV(path + "/debit_cards.csv", phoneNumber);
            ArrayList<CreditCard> creditCards = CSVManager.readCreditCardCSV(path + "/credit_cards.csv", phoneNumber);
            ArrayList<Card> allCards = new ArrayList<>();
            allAccounts.addAll(accounts);
            allAccounts.addAll(savingsAccounts);
            allCards.addAll(debitCards);
            allCards.addAll(creditCards);
            for (Transaction transaction : transactions)
                for (Account account : allAccounts)
                    if (transaction.getSourceIBAN().equals(account.getIBAN()) || transaction.getDestinationIBAN().equals(account.getIBAN()))
                        account.addTransaction(transaction);
            for (Account account : allAccounts)
                clients.get(phoneNumber).addAccount(account);
            for (Card card : allCards)
                clients.get(phoneNumber).addCard(card);
        }
    }

    private int getUserChoice() {
        int choice;
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        choice = scanner.nextInt();
        return choice;
    }

    private void menu() {
        System.out.println("Menu:");
        System.out.println("1. Option 1");
        System.out.println("2. Option 2");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    public void run() {
        FileManager.initiateDirectories("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/clients_test.csv");
        initiateClients();
        for (Client client : clients.values())
            System.out.println(client);
        while (true) {
            menu();
            int choice = getUserChoice();
            switch (choice) {
                case 0:
                    System.out.println("Shutting down...");
                    return;
                case 1:
                    System.out.println("Option 1 selected.");
                    break;
                case 2:
                    System.out.println("Option 2 selected.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public HashMap<String, Client> getClients() {
        return clients;
    }

    public void setClients(HashMap<String, Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        clients.put(client.getPhoneNumber(), client);
    }

    public void removeClient(Client client) {
        clients.remove(client.getPhoneNumber());
    }

    public void removeClient(String phoneNumber) {
        clients.remove(phoneNumber);
    }

    public Client getClient(String phoneNumber) {
        return clients.get(phoneNumber);
    }

    public void updateClient(String phoneNumber, Client client) {
        clients.put(phoneNumber, client);
    }

    public void updateClient(Client client) {
        clients.put(client.getPhoneNumber(), client);
    }
}
