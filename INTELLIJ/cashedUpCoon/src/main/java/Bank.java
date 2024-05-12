import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Bank {
    private static Bank instance = null;
    private static int menu = 0;
    private Scanner scanner;
    private Client client;
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

    private void mainMenu() {
        System.out.println("Menu:");
        System.out.println("1. Login");
        System.out.println("2. Create Client Account");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        menu = getUserChoice();
    }

    private void createClientAccountMenu() {
        System.out.println("Create Account Menu:");
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("Email: ");
        String email = scanner.next();
        System.out.println("Phone Number: ");
        String phoneNumber = scanner.next();
        System.out.println("Address: ");
        String address = scanner.next();
        System.out.println("Password: ");
        System.out.println("Do you want to generate a password? (Y/N)");
        String choice = scanner.next();
        String password;
        if (choice.equalsIgnoreCase("Y")) {
            password = new Client(address, phoneNumber, email, name).generatePassword();
        } else {
            password = scanner.next();
        }
        Client newClient = new Client(name, email, phoneNumber, address, password);
        addClient(newClient);
        FileManager.initiateDirectory(newClient);
        System.out.println("Account created successfully.");
        menu = 0;
    }

    private void loginMenu() {
        System.out.println("Login Menu:");
        System.out.println("Phone Number: ");
        String phoneNumber = scanner.next();
        if (clients.containsKey(phoneNumber)) {
            int attempts = 0;
            System.out.println("Password: ");
            String password = scanner.next();
            while (!clients.get(phoneNumber).getPassword().equals(password) && attempts < 3) {
                System.out.println("Incorrect password. Please try again.");
                password = scanner.next();
                attempts++;
            }
            if (clients.get(phoneNumber).getPassword().equals(password)) {
                System.out.println("Login successful.");
                client = clients.get(phoneNumber);
                menu = 3;
            } else {
                System.out.println("Too many attempts. Please try again later.");
                menu = -1;
            }
        } else {
            System.out.println("Client not found. Please try again.");
        }
    }

    private void clientAccountMenu() {
        System.out.println("Account Menu:");
        System.out.println("1. View Accounts");
        System.out.println("2. View Cards");
        System.out.println("3. View Transactions");
        System.out.println("4. Transfer Money");
        System.out.println("5. Apply for Credit Card"); // to be tested
        System.out.println("6. Apply for Debit Card"); // to be tested
//        System.out.println("7. Apply for Savings Account");
//        System.out.println("8. Open New Account");
        System.out.println("9. View Client Account Details");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        menu = getUserChoice();
        switch (menu) {
            case 1:
                menu = 4;
                break;
            case 2:
                menu = 5;
                break;
            case 3:
                menu = 6;
                break;
            case 4:
                menu = 7;
                break;
            case 5:
                menu = 8;
                break;
            case 6:
                menu = 9;
                break;
            case 7:
                menu = 10;
                break;
            case 8:
                menu = 11;
                break;
            case 9:
                menu = 12;
                break;
            case 0:
                menu = -1;
                break;
            default:
                clientAccountMenu();
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void viewAccounts() {
        System.out.println("Accounts:");
        for (Account account : client.getAccounts())
            System.out.println(account);
        menu = 3;
    }

    private void viewCards() {
        System.out.println("Cards:");
        for (Card card : client.getCards())
            if (card instanceof DebitCard)
                System.out.println("Debit Card: " + card);
            else
                System.out.println("Credit Card: " + card);
        menu = 3;
    }

    private void viewTransactions() {
        System.out.println("Transactions:");
        for (Account account : client.getAccounts())
            for (Transaction transaction : account.getTransactions())
                System.out.println(transaction);
        menu = 3;
    }

    private void transferMoney() {
        System.out.println("Transfer Money:");
        System.out.println("From Account: ");
        String fromIBAN = scanner.next();
        if (client.getAccount(fromIBAN) == null) {
            System.out.println("Account not found. Please try again.");
            menu = 3;
            return;
        }
        System.out.println("To Account: ");
        String toIBAN = scanner.next();
        Account destination = CSVManager.getAccountFromIBAN(toIBAN);
        System.out.println("Amount: ");
        float amount = scanner.nextFloat();
        if (!client.getAccount(fromIBAN).transferFunds(destination, amount)) {
            System.out.println("Insufficient funds. Please try again.");
            menu = 3;
            return;
        }
        Currencies currency = client.getAccount(fromIBAN).getCurrency();
        Transaction transaction = new Transaction(fromIBAN, toIBAN, amount, currency);
        destination.addTransaction(transaction);
        CSVManager.updateAccountCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/accounts_test.csv", client.getAccount(fromIBAN));
        CSVManager.updateAccountCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/accounts_test.csv", destination);
        CSVManager.updateAccountCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/" + client.getPhoneNumber() + "/accounts.csv", client.getAccount(fromIBAN));
        String otherPhoneNumber = CSVManager.getPhoneNumberFromIBAN(toIBAN);
        CSVManager.updateAccountCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/" + otherPhoneNumber + "/accounts.csv", destination);
        CSVManager.addTransactionCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/transactions_test.csv", client.getPhoneNumber(), otherPhoneNumber, transaction);
        CSVManager.addTransactionCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/" + client.getPhoneNumber() + "/transactions.csv", client.getPhoneNumber(), otherPhoneNumber, transaction);
        if (otherPhoneNumber != null)
            CSVManager.addTransactionCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/" + otherPhoneNumber + "/transactions.csv", client.getPhoneNumber(), otherPhoneNumber, transaction);
        System.out.println("Transaction successful.");
        menu = 3;
    }

    private void applyForCreditCard() {
        System.out.println("Apply for Credit Card:");
        System.out.println("Credit Limit: ");
        float creditLimit = scanner.nextFloat();
        CreditCard creditCard = new CreditCard(creditLimit);
        client.addCard(creditCard);
        CSVManager.addCreditCardCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/" + client.getPhoneNumber() + "/credit_cards.csv", client.getPhoneNumber(), creditCard);
        System.out.println("Credit Card application successful. Here are your card details:");
        System.out.println(creditCard);
        menu = 3;
    }

    private void applyForDebitCard() {
        System.out.println("Apply for Debit Card:");
        System.out.println("Withdrawal Limit: ");
        int withdrawalLimit = scanner.nextInt();
        DebitCard debitCard = new DebitCard(withdrawalLimit);
        client.addCard(debitCard);
        CSVManager.addDebitCardCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/" + client.getPhoneNumber() + "/debit_cards.csv", client.getPhoneNumber(), debitCard);
        System.out.println("Debit Card application successful. Here are your card details:");
        System.out.println(debitCard);
        menu = 3;
    }

    private void viewClientAccountDetails() {
        System.out.println("Client Account Details:");
        System.out.println(client);
        menu = 3;
    }

    public void run() {
        FileManager.initiateDirectories("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/clients_test.csv");
        initiateClients();
        while (true) {
            switch (menu) {
                case -1:
                    System.out.println("Shutting down...");
                    return;
                case 0:
                    mainMenu();
                    break;
                case 1:
                    loginMenu();
                    break;
                case 2:
                    createClientAccountMenu(); // to be tested
                    break;
                case 3:
                    clientAccountMenu();
                    break;
                case 4:
                    viewAccounts();
                    break;
                case 5:
                    viewCards();
                    break;
                case 6:
                    viewTransactions();
                    break;
                case 7:
                    transferMoney();
                    break;
                case 8:
                    applyForCreditCard();
                    break;
                case 9:
                    applyForDebitCard();
                    break;
//                case 10:
//                    applyForSavingsAccount();
//                    break;
//                case 11:
//                    openNewAccount();
//                    break;
                case 12:
                    viewClientAccountDetails();
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
