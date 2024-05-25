import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Client implements CSVManager {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();

    public Client(String name, String email, String phoneNumber, String address,
                  String password, ArrayList<Account> accounts, ArrayList<Card> cards) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.accounts = accounts;
        this.cards = cards;
    }

    public Client(String name, String email, String phoneNumber, String address, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
    }

    public Client(String address, String phoneNumber, String email, String name) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.name = name;
        this.password = generatePassword();
    }

    public static String generatePassword() {
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = lowercase.toUpperCase();
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()-+\\|<>?{}[]~/'.:,;_`";
        String allChars = lowercase + uppercase + numbers + specialChars;

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        password.append(lowercase.charAt(random.nextInt(lowercase.length())));
        password.append(uppercase.charAt(random.nextInt(uppercase.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        for (int i = 0; i < 6; i++)
            password.append(allChars.charAt(random.nextInt(allChars.length())));

        System.out.println("Your password has been changed to: " + password + "\n");
        return password.toString();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public void updateAccount(Account oldAccount, Account newAccount) {
        accounts.set(accounts.indexOf(oldAccount), newAccount);
    }

    public void updateCard(Card oldCard, Card newCard) {
        cards.set(cards.indexOf(oldCard), newCard);
    }

    public void updatePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(password)) {
            password = newPassword;
            System.out.println("Password has been changed successfully!\n");
        } else {
            System.out.println("Old password is incorrect!\n");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<SavingsAccount> getSavingsAccounts() {
        ArrayList<SavingsAccount> savingsAccounts = new ArrayList<>();
        for (Account account : accounts)
            if (account instanceof SavingsAccount)
                savingsAccounts.add((SavingsAccount) account);
        return savingsAccounts;
    }

    public Account getAccount(String IBAN) {
        for (Account account : accounts)
            if (account.getIBAN().equals(IBAN))
                return account;
        return null;
    }

    public SavingsAccount getSavingsAccount(String IBAN) {
        for (Account account : accounts)
            if (account.getIBAN().equals(IBAN) && account instanceof SavingsAccount)
                return (SavingsAccount) account;
        return null;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void printAccountIBANs() {
        int index = 1;
        for (Account account : accounts)
            if (!(account instanceof SavingsAccount))
                System.out.println(index++ + ". " + account.getIBAN() + " - " + account.getBalance() + " " + account.getCurrency());
        System.out.println("1 - " + (index - 1) + ": ");
    }

    public void printSavingsAccountIBANs() {
        int index = 1;
        for (Account account : accounts)
            if (account instanceof SavingsAccount)
                System.out.println(index++ + ". " + account.getIBAN() + " - " + account.getBalance() + " " + account.getCurrency());
        System.out.println("1 - " + (index - 1) + ": ");
    }

    @Override
    public boolean verifyNotInCSV(String path) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[2].equals(this.phoneNumber) || temporary[1].equals(this.email)) {
                    fr.close();
                    br.close();
                    return false;
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.CLIENT_READ, true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.CLIENT_READ, false);
            return false;
        }
    }

    @Override
    public void addCSV(String path, String ... phoneNumber) {
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() != 0) {
                if (verifyNotInCSV(path)) {
                    bw.newLine();
                } else {
                    bw.close();
                    fw.close();
                    Audit.writeLog(Audit.Type.CLIENT_CREATION, false);
                    return;
                }
            }
            bw.write(this.name + "," + this.email + "," + this.phoneNumber + "," + this.address + "," + this.password);
            Audit.writeLog(Audit.Type.CLIENT_CREATION, true);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.CLIENT_CREATION, false);
        }
    }

    @Override
    public void updateCSV(String path) {
        try {
            File file = new File(path);
            if (file.length() != 0) {
                if (verifyNotInCSV(path)) {
                    addCSV(path);
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
                if (temporary[2].equals(this.phoneNumber) || temporary[1].equals(this.email))
                    data += this.name + "," + this.email + "," + this.phoneNumber + "," + this.address + "," + this.password + "\n";
                else
                    data += line + "\n";
            }
            data = data.substring(0, data.length() - 1);
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            Audit.writeLog(Audit.Type.CLIENT_UPDATE, true);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.CLIENT_UPDATE, false);
        }
    }

    @Override
    public void deleteCSV(String path) {
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
                if (!(temporary[2].equals(this.phoneNumber)))
                    data += line + "\n";
                else
                    deleted = true;
            }
            data = data.substring(0, data.length() - 1);
            if (deleted) {
                Audit.writeLog(Audit.Type.CLIENT_DELETION, true);
            } else {
                System.out.println("Client not found!");
                Audit.writeLog(Audit.Type.CLIENT_DELETION, false);
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
            Audit.writeLog(Audit.Type.CLIENT_DELETION, false);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) && Objects.equals(email, client.email) && Objects.equals(phoneNumber, client.phoneNumber) && Objects.equals(address, client.address) && Objects.equals(password, client.password) && Objects.equals(accounts, client.accounts) && Objects.equals(cards, client.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phoneNumber, address, password, accounts, cards);
    }

    @Override
    public String toString() {
        String returned = "Client:\n\tName: " + name + "\n\tEmail: " + email + "\n\tPhone Number: " + phoneNumber + "\n\tAddress: " + address + "\n\tPassword: " + password;
        if (!accounts.isEmpty()) {
            returned += "\n\tAccounts:";
            for (Account account : accounts)
                returned += "\n\t\t" + account.toString();
        } else
            returned += "\n\tNo accounts available!\n";
        if (!cards.isEmpty()) {
            returned += "\tCards:";
            for (Card card : cards)
                returned += "\n\t\t" + card.toString();
        } else
            returned += "\tNo cards available!";
        return returned + "\n";
    }
}