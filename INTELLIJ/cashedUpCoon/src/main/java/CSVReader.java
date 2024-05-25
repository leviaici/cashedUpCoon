import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVReader {
    static HashMap<String, Client> readClientCSV(String path) {
        try {
            HashMap<String, Client> clients = new HashMap<>();
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                Client client;
                temporary = line.split(",");
                client = new Client(temporary[0], temporary[1], temporary[2], temporary[3], temporary[4]);
                clients.put(temporary[2], client);
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.CLIENT_READ, true);
            return clients;
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.CLIENT_READ, false);
            return null;
        }
    }
    static ArrayList<Account> readAccountCSV(String path, String phoneNumber) {
        try {
            ArrayList<Account> accounts = new ArrayList<>();
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                Account account;
                temporary = line.split(",");
                if (temporary[0].equals(phoneNumber)) {
                    account = new Account(temporary[1], Float.parseFloat(temporary[2]), Currencies.valueOf(temporary[3]));
                    accounts.add(account);
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.ACCOUNT_READ, true);
            return accounts;
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.ACCOUNT_READ, false);
            return null;
        }
    }
    static ArrayList<SavingsAccount> readSavingsAccountCSV(String path, String phoneNumber) {
        try {
            ArrayList<SavingsAccount> accounts = new ArrayList<>();
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                SavingsAccount account;
                temporary = line.split(",");
                if (temporary[0].equals(phoneNumber)) {
                    account = new SavingsAccount(temporary[1], Float.parseFloat(temporary[2]), Currencies.valueOf(temporary[3]), Float.parseFloat(temporary[4]), new SimpleDateFormat("dd").parse(temporary[5]));
                    accounts.add(account);
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.SAVINGS_ACCOUNT_READ, true);
            return accounts;
        } catch (Exception e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.SAVINGS_ACCOUNT_READ, false);
            return null;
        }
    }
    static ArrayList<Transaction> readTransactionCSV(String path, String phoneNumber) {
        try {
            ArrayList<Transaction> transactions = new ArrayList<>();
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                Transaction transaction;
                temporary = line.split(",");
                if (temporary[0].equals(phoneNumber) || temporary[1].equals(phoneNumber)) {
                    transaction = new Transaction(temporary[2], temporary[3], Float.parseFloat(temporary[4]), Currencies.valueOf(temporary[5]), new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(temporary[6]));
                    transactions.add(transaction);
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.TRANSACTION_READ, true);
            return transactions;
        } catch (Exception e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.TRANSACTION_READ, false);
            return null;
        }
    }
    static ArrayList<CreditCard> readCreditCardCSV(String path, String phoneNumber) {
        try {
            ArrayList<CreditCard> cards = new ArrayList<>();
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                CreditCard card;
                temporary = line.split(",");
                if (temporary[0].equals(phoneNumber)) {
                    card = new CreditCard(temporary[1], temporary[2], new SimpleDateFormat("yyyy-MM").parse(temporary[3]), Boolean.parseBoolean(temporary[4]), Float.parseFloat(temporary[5]));
                    cards.add(card);
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.CREDIT_CARD_READ, true);
            return cards;
        } catch (Exception e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.CREDIT_CARD_READ, false);
            return null;
        }
    }
    static ArrayList<DebitCard> readDebitCardCSV(String path, String phoneNumber) {
        try {
            ArrayList<DebitCard> cards = new ArrayList<>();
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                DebitCard card;
                temporary = line.split(",");
                if (temporary[0].equals(phoneNumber)) {
                    card = new DebitCard(temporary[1], temporary[2], new SimpleDateFormat("yyyy-MM").parse(temporary[3]), Boolean.parseBoolean(temporary[4]), Integer.parseInt(temporary[5]));
                    cards.add(card);
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.DEBIT_CARD_READ, true);
            return cards;
        } catch (Exception e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.DEBIT_CARD_READ, false);
            return null;
        }
    }

    static HashMap<Integer, String> getPhoneNumberFromTransaction(String path, Transaction transaction, String phoneNumber) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[2].equals(transaction.getSourceIBAN()) && temporary[3].equals(transaction.getDestinationIBAN())) {
                    Audit.writeLog(Audit.Type.TRANSACTION_READ, true);
                    if (temporary[0].equals(phoneNumber)) {
                        String[] finalTemporary = temporary;
                        return new HashMap<>() {{
                            put(1, finalTemporary[1]);
                        }};
                    } else {
                        String[] finalTemporary = temporary;
                        return new HashMap<>() {{
                            put(0, finalTemporary[0]);
                        }};
                    }
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.TRANSACTION_READ, false);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.TRANSACTION_READ, false);
            return null;
        }
    }
    static String getPhoneNumberFromIBAN(String IBAN) {
        try {
            File file = new File("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/accounts_test.csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[1].equals(IBAN)) {
                    Audit.writeLog(Audit.Type.ACCOUNT_READ, true);
                    return temporary[0];
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.ACCOUNT_READ, false);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.ACCOUNT_READ, false);
            return null;
        }
    }
    static Account getAccountFromIBAN(String IBAN) {
        try {
            File file = new File("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/accounts_test.csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[1].equals(IBAN)) {
                    Audit.writeLog(Audit.Type.ACCOUNT_READ, true);
                    return new Account(temporary[1], Float.parseFloat(temporary[2]), Currencies.valueOf(temporary[3]));
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.ACCOUNT_READ, false);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.ACCOUNT_READ, false);
            return null;
        }
    }
    static SavingsAccount getSavingsAccountFromIBAN(String IBAN) {
        try {
            File file = new File("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/savings_accounts_test.csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[1].equals(IBAN)) {
                    Audit.writeLog(Audit.Type.SAVINGS_ACCOUNT_READ, true);
                    return CSVReader.readSavingsAccountCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/savings_accounts_test.csv", temporary[0]).getFirst();
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.SAVINGS_ACCOUNT_READ, false);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.SAVINGS_ACCOUNT_READ, false);
            return null;
        }
    }
}
