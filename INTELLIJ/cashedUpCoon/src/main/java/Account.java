import java.util.Objects;
import java.util.ArrayList;
//
public class Account {
    private String IBAN;
    private float balance;
    private String currency; // possible mappings: USD, EUR, RON, GBP, etc.
    ArrayList<Transaction> transactions = new ArrayList<>(); // possible next to become map
    // TBA : Transaction history

    public Account(String IBAN, float balance, String currency, ArrayList<Transaction> transactions) {
        this.IBAN = IBAN;
        this.balance = balance;
        this.currency = currency;
        this.transactions = transactions;
    }

    public Account(String IBAN,  float balance, String currency) {
        this.IBAN = IBAN;
        this.balance = balance;
        this.currency = currency;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Float.compare(balance, account.balance) == 0 && Objects.equals(IBAN, account.IBAN) && Objects.equals(currency, account.currency) && Objects.equals(transactions, account.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN, balance, currency, transactions);
    }

    @Override
    public String toString() {
        String returned = "Account:\n\t\tIBAN: " + IBAN + "\n\t\tBalance: " + balance + "\n\t\tCurrency: " + currency + "\n";
        if (!transactions.isEmpty()) {
            returned += "\tTransactions:\n";
            for (Transaction transaction : transactions)
                returned += transaction.toString() + "\n";
            return returned.substring(0, returned.length() - 1);
        }
        returned += "\t\tNo transactions yet.";
        return returned + "\n";
    }
}