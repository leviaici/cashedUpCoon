import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public interface CSVManager {
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
            return clients;
        } catch (IOException e) {
            e.printStackTrace();
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
            return accounts;
        } catch (IOException e) {
            e.printStackTrace();
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
            return accounts;
        } catch (Exception e) {
            e.printStackTrace();
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
                    transaction = new Transaction(temporary[2], temporary[3], Float.parseFloat(temporary[4]), Currencies.valueOf(temporary[5]), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(temporary[6]));
                    transactions.add(transaction);
                }
            }
            fr.close();
            br.close();
            return transactions;
        } catch (Exception e) {
            e.printStackTrace();
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
            return cards;
        } catch (Exception e) {
            e.printStackTrace();
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
            return cards;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static boolean verifyClientNotInCSV(String path, Client client) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[2].equals(client.getPhoneNumber()) || temporary[1].equals(client.getEmail())) {
                    fr.close();
                    br.close();
                    return false;
                }
            }
            fr.close();
            br.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    static boolean verifyAccountNotInCSV(String path, Account account) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[1].equals(account.getIBAN())) {
                    fr.close();
                    br.close();
                    return false;
                }
            }
            fr.close();
            br.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    static boolean verifyTransactionNotInCSV(String path, Transaction transaction) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[2].equals(transaction.getSourceIBAN()) && temporary[3].equals(transaction.getDestinationIBAN()) && temporary[6].equals(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(transaction.getDate()))) {
                    fr.close();
                    br.close();
                    return false;
                }
            }
            fr.close();
            br.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    static boolean verifyCardNotInCSV(String path, Card card) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[1].equals(card.getNumber())) {
                    fr.close();
                    br.close();
                    return false;
                }
            }
            fr.close();
            br.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    static void addClientCSV(String path, Client client) {
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() != 0) {
                if (verifyClientNotInCSV(path, client)) {
                    bw.newLine();
                } else {
                    bw.close();
                    fw.close();
                    return;
                }
            }
            bw.write(client.getName() + "," + client.getEmail() + "," + client.getPhoneNumber() + "," + client.getAddress() + "," + client.getPassword());
            System.out.println("Client added successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void addAccountCSV(String path, String phoneNumber, Account account) {
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() != 0) {
                if (verifyAccountNotInCSV(path, account)) {
                    bw.newLine();
                } else {
                    bw.close();
                    fw.close();
                    return;
                }
            }
            bw.write(phoneNumber + "," + account.getIBAN() + "," + account.getBalance() + "," + account.getCurrency());
            System.out.println("Account added successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void addSavingsAccountCSV(String path, String phoneNumber, SavingsAccount account) {
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() != 0) {
                if (verifyAccountNotInCSV(path, account)) {
                    bw.newLine();
                } else {
                    bw.close();
                    fw.close();
                    return;
                }
            }
            bw.write(phoneNumber + "," + account.getIBAN() + "," + account.getBalance() + "," + account.getCurrency() + "," + account.getInterestRate() + "," + new SimpleDateFormat("yyyy-MM-dd").format(account.getDayOfPayment()));
            System.out.println("Savings account added successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void addTransactionCSV(String path, String phoneNumber1, String phoneNumber2, Transaction transaction) {
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() != 0) {
                if (verifyTransactionNotInCSV(path, transaction)) {
                    bw.newLine();
                } else {
                    bw.close();
                    fw.close();
                    return;
                }
            }
            bw.write(phoneNumber1 + "," + phoneNumber2 + "," + transaction.getSourceIBAN() + "," + transaction.getDestinationIBAN() + "," + transaction.getAmount() + "," + transaction.getCurrency() + "," + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(transaction.getDate()));
            System.out.println("Transaction added successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void addCreditCardCSV(String path, String phoneNumber, CreditCard card) {
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() != 0) {
                if (verifyCardNotInCSV(path, card)) {
                    bw.newLine();
                } else {
                    bw.close();
                    fw.close();
                    return;
                }
            }
            bw.write(phoneNumber + "," + card.getNumber() + "," + card.getCvv() + "," + new SimpleDateFormat("yyyy-MM").format(card.getExpirationDate()) + "," + card.getBlocked() + "," + card.getCreditLimitPerTransaction());
            System.out.println("Credit card added successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void addDebitCardCSV(String path, String phoneNumber, DebitCard card) {
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() != 0) {
                if (verifyCardNotInCSV(path, card)) {
                    bw.newLine();
                } else {
                    bw.close();
                    fw.close();
                    return;
                }
            }
            bw.write(phoneNumber + "," + card.getNumber() + "," + card.getCvv() + "," + new SimpleDateFormat("yyyy-MM").format(card.getExpirationDate()) + "," + card.getBlocked() + "," + card.getWithdrawLimitPerTransaction());
            System.out.println("Debit card added successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    static void write_log

    static void updateClientCSV(String path, Client client) {
        try {
            File file = new File(path);
            if (file.length() != 0) {
                if (verifyClientNotInCSV(path, client)) {
                    addClientCSV(path, client);
                    return;
                }
            }
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[2].equals(client.getPhoneNumber()) || temporary[1].equals(client.getEmail()))
                    data += client.getName() + "," + client.getEmail() + "," + client.getPhoneNumber() + "," + client.getAddress() + "," + client.getPassword() + "\n";
                else
                    data += line + "\n";
            }
            data = data.substring(0, data.length() - 1);
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void updateAccountCSV(String path, Account account) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[1].equals(account.getIBAN()))
                    data += temporary[0] + "," + account.getIBAN() + "," + account.getBalance() + "," + account.getCurrency() + "\n";
                else
                    data += line + "\n";
            }
            data = data.substring(0, data.length() - 1);
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            System.out.println("Account updated successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void updateSavingsAccountCSV(String path, SavingsAccount account) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[1].equals(account.getIBAN()))
                    data += temporary[0] + "," + account.getIBAN() + "," + account.getBalance() + "," + account.getCurrency() + "," + account.getInterestRate() + "," + new SimpleDateFormat("yyyy-MM-dd").format(account.getDayOfPayment()) + "\n";
                else
                    data += line + "\n";
            }
            data = data.substring(0, data.length() - 1);
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            System.out.println("Savings account updated successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void updateTransactionCSV(String path, Transaction transaction) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[2].equals(transaction.getSourceIBAN()) || temporary[3].equals(transaction.getDestinationIBAN()))
                    data += temporary[0] + "," + temporary[1] + "," + transaction.getSourceIBAN() + "," + transaction.getDestinationIBAN() + "," + transaction.getAmount() + "," + transaction.getCurrency() + "," + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transaction.getDate()) + "\n";
                else
                    data += line + "\n";
            }
            data = data.substring(0, data.length() - 1);
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            System.out.println("Transaction updated successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void updateCreditCardCSV(String path, CreditCard card) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[1].equals(card.getNumber()))
                    data += temporary[0] + "," + card.getNumber() + "," + card.getCvv() + "," + new SimpleDateFormat("yyyy-MM").format(card.getExpirationDate()) + "," + card.getBlocked() + "," + card.getCreditLimitPerTransaction() + "\n";
                else
                    data += line + "\n";
            }
            data = data.substring(0, data.length() - 1);
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            System.out.println("Credit card updated successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void updateDebitCardCSV(String path, DebitCard card) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[1].equals(card.getNumber()))
                    data += temporary[0] + "," + card.getNumber() + "," + card.getCvv() + "," + new SimpleDateFormat("yyyy-MM").format(card.getExpirationDate()) + "," + card.getBlocked() + "," + card.getWithdrawLimitPerTransaction() + "\n";
                else
                    data += line + "\n";
            }
            data = data.substring(0, data.length() - 1);
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            System.out.println("Debit card updated successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void deleteClientCSV(String path, Client client) {
        try {
            boolean deleted = false;
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (!(temporary[2].equals(client.getPhoneNumber())))
                    data += line + "\n";
                else
                    deleted = true;
            }
            data = data.substring(0, data.length() - 1);
            if (deleted) {
                System.out.println("Client deleted successfully!");
            }
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void deleteAccountCSV(String path, Account account) {
        try {
            boolean deleted = false;
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (!(temporary[1].equals(account.getIBAN())))
                    data += line + "\n";
                else
                    deleted = true;
            }
            data = data.substring(0, data.length() - 1);
            if (deleted) {
                if (account.getClass() == SavingsAccount.class)
                    System.out.println("Savings account deleted successfully!");
                else
                    System.out.println("Account deleted successfully!");
            }
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void deleteTransactionCSV(String path, Transaction transaction) {
        try {
            boolean deleted = false;
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (!(temporary[2].equals(transaction.getSourceIBAN()) && temporary[3].equals(transaction.getDestinationIBAN())))
                    data += line + "\n";
                else
                    deleted = true;
            }
            data = data.substring(0, data.length() - 1);
            if (deleted) {
                System.out.println("Transaction deleted successfully!");
            }
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void deleteCardCSV(String path, Card card) {
        try {
            boolean deleted = false;
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (!(temporary[1].equals(card.getNumber())))
                    data += line + "\n";
                else
                    deleted = true;
            }
            data = data.substring(0, data.length() - 1);
            if (deleted) {
                if (card.getClass() == CreditCard.class)
                    System.out.println("Credit card deleted successfully!");
                else
                    System.out.println("Debit card deleted successfully!");
            }
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                if (temporary[2].equals(transaction.getSourceIBAN()) && temporary[3].equals(transaction.getDestinationIBAN()))
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
            fr.close();
            br.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
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
                    return temporary[0];
                }
            }
            fr.close();
            br.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
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
                    return CSVManager.readAccountCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/accounts_test.csv", temporary[0]).getFirst();
                }
            }
            fr.close();
            br.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}