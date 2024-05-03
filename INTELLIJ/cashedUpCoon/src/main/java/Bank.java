import java.util.HashMap;

public class Bank {
    private String name;
    private HashMap<String, Client> clients = new HashMap<>();

    public Bank(String name, HashMap<String, Client> clients) {
        this.name = name;
        this.clients = clients;
    }

    public Bank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void initiateClients() {
        clients = CSVManager.readClientCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/clients_test.csv");
    }
}
