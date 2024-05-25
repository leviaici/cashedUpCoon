import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface FileManager {
    static void createClientMainDirectory(Client client) {
        String path = "/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/" + client.getPhoneNumber() + "/";
        File directory = new File(path);

        if (!directory.exists())
            if (directory.mkdir())
                System.out.println("Client directory created successfully!");
            else
                System.out.println("Failed to create client directory.");
        else
            System.out.println("Client directory already exists.");
    }
    static void createClientFiles(Client client) {
        try {
            String path = "/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/" + client.getPhoneNumber() + "/";
            String[] files = {"accounts.csv", "savings_accounts.csv", "transactions.csv", "debit_cards.csv", "credit_cards.csv", "account_details.csv"};
            File directory = new File(path);
            if(!directory.exists())
                createClientMainDirectory(client);
            for (String file : files) {
                directory = new File(path + file);
                if (!directory.exists())
                    if (directory.createNewFile())
                        System.out.println("Client " + file + " directory created successfully!");
                    else
                        System.out.println("Failed to create client " + file + " directory.");
                else
                    System.out.println("Client " + file + " directory already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void deleteClientMainDirectory(Client client) {
        String path = "/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/" + client.getPhoneNumber() + "/";
        File directory = new File(path);
        String[] files = {"accounts.csv", "savings_accounts.csv", "transactions.csv", "debit_cards.csv", "credit_cards.csv", "account_details.csv"};
        if (directory.exists()) {
            for (String file : files) {
                File fileToDelete = new File(path + file);
                if (fileToDelete.delete())
                    System.out.println("Client " + file + " directory deleted successfully!");
                else
                    System.out.println("Failed to delete client " + file + " directory.");
            }
            if (directory.delete())
                System.out.println("Client directory deleted successfully!");
            else
                System.out.println("Failed to delete client directory.");
        } else
            System.out.println("Client directory does not exist.");
    }
    static void initiateDirectories(String path) {
        createAuditFile();
        HashMap<String, Client> clients = CSVReader.readClientCSV(path);
        for (String key : clients.keySet()) {
            Client client = clients.get(key);
            String filePath = "/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/";
            deleteClientMainDirectory(client);
            ArrayList<Account> accounts = CSVReader.readAccountCSV(filePath + "accounts_test.csv", client.getPhoneNumber());
            ArrayList<SavingsAccount> savingsAccounts = CSVReader.readSavingsAccountCSV(filePath + "savings_accounts_test.csv", client.getPhoneNumber());
            ArrayList<Transaction> transactions = CSVReader.readTransactionCSV(filePath + "transactions_test.csv", client.getPhoneNumber());
            ArrayList<CreditCard> creditCards = CSVReader.readCreditCardCSV(filePath + "credit_card_test.csv", client.getPhoneNumber());
            ArrayList<DebitCard> debitCards = CSVReader.readDebitCardCSV(filePath + "debit_card_test.csv", client.getPhoneNumber());
            filePath = filePath + client.getPhoneNumber() + "/";

            FileManager.createClientMainDirectory(client);   // creating main directory of client
            FileManager.createClientFiles(client);  // creating subdirectories of client (accounts, transactions, cards)
            for (Account account : accounts)
                CSVManager.addAccountCSV(filePath + "accounts.csv", client.getPhoneNumber(), account);
            for (SavingsAccount account : savingsAccounts)
                CSVManager.addSavingsAccountCSV(filePath + "savings_accounts.csv", client.getPhoneNumber(), account);
            for (Transaction transaction : transactions) {
                HashMap<Integer, String> otherPhoneNumber = CSVReader.getPhoneNumberFromTransaction("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/transactions_test.csv", transaction, client.getPhoneNumber());
                for (int index : otherPhoneNumber.keySet())
                    if (index == 1)
                        CSVManager.addTransactionCSV(filePath + "transactions.csv", client.getPhoneNumber(), otherPhoneNumber.get(index), transaction);
                    else
                        CSVManager.addTransactionCSV(filePath + "transactions.csv", otherPhoneNumber.get(index), client.getPhoneNumber(), transaction);
            }
            for (CreditCard card : creditCards)
                CSVManager.addCreditCardCSV(filePath + "credit_cards.csv", client.getPhoneNumber(), card);
            for (DebitCard card : debitCards)
                CSVManager.addDebitCardCSV(filePath + "debit_cards.csv", client.getPhoneNumber(), card);

            CSVManager.addClientCSV(filePath + "account_details.csv", client);
        }
    }
    static void initiateDirectory(Client client) {
        String path = "/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/" + client.getPhoneNumber() + "/";
        FileManager.createClientMainDirectory(client);
        FileManager.createClientFiles(client);
        CSVManager.addClientCSV(path + "account_details.csv", client);
    }
    static void createAuditFile() {
        try {
            String path = "/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/audit_log.csv";
            File directory = new File(path);

            if (!directory.exists())
                if (directory.createNewFile())
                    System.out.println("Audit log file created successfully!");
                else
                    System.out.println("Failed to create audit log file.");
            else
                System.out.println("Audit log file already exists.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
