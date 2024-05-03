import java.io.*;
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

    static void deleteClientCSV(String path, Client client) {
        try {
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
}
