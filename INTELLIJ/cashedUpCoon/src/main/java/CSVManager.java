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
    //            SavingsAccount savingsAccount2 = new SavingsAccount("RO1234567890123456789012", 1000, Currencies.RON, 0.1f, new SimpleDateFormat("dd").parse("2021-06-01"));
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
            if (deleted) {
                data = data.substring(0, data.length() - 1);
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
            if (deleted) {
                data = data.substring(0, data.length() - 1);
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

    static void createDirectory(Client client){
        String path = "/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/" + client.getPhoneNumber() + "/";
        File directory = new File(path);

        if (!directory.exists())
            if (directory.mkdir())
                System.out.println("Directory created successfully!");
            else
                System.out.println("Failed to create directory.");
        else
            System.out.println("Directory already exists.");
    }
}
