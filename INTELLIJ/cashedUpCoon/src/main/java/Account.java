import java.util.Objects;
import java.util.ArrayList;

public class Account {
    private String IBAN;
    private float balance;
    private Currencies currency; // possible mappings: USD, EUR, RON, GBP, etc.
    ArrayList<Transaction> transactions = new ArrayList<>(); // possible next to become map

    public Account(String IBAN, float balance, Currencies currency, ArrayList<Transaction> transactions) {
        this.IBAN = IBAN;
        this.balance = balance;
        this.currency = currency;
        this.transactions = transactions;
    }

    public Account(String IBAN, float balance, Currencies currency) {
        this.IBAN = IBAN;
        this.balance = balance;
        this.currency = currency;
    }

    public Account(float balance, Currencies currency) {
        this.IBAN = generateIBAN(currency);
        this.balance = balance;
        this.currency = currency;
    }

    public Account(Currencies currency) {
        this.IBAN = generateIBAN(currency);
        this.balance = 0.0f;
        this.currency = currency;
    }

    public Account(String IBAN, Currencies currency) {
        this.IBAN = IBAN;
        this.balance = 0.0f;
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

    public Currencies getCurrency() {
        return currency;
    }

    public void setCurrency(Currencies currency) {
        this.currency = currency;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    public void addFunds(float amount) {
        balance += amount;
    }

    public void withdrawFunds(float amount) {
        balance -= amount;
    }

    public boolean transferFunds(Account destination, float amount) {
        float newAmount = amount;
        if (currency != destination.getCurrency()) {
            switch (currency) {
                case USD:
                    switch (destination.getCurrency()) {
                        case EUR -> newAmount *= 1.09f;
                        case RON -> newAmount *= 0.22f;
                        case GBP -> newAmount *= 1.27f;
                    }
                    break;
                case EUR:
                    switch (destination.getCurrency()) {
                        case USD -> newAmount *= 0.92f;
                        case RON -> newAmount *= 0.2f;
                        case GBP -> newAmount *= 1.16f;
                    }
                    break;
                case RON:
                    switch (destination.getCurrency()) {
                        case USD -> newAmount *= 4.58f;
                        case EUR -> newAmount *= 4.98f;
                        case GBP -> newAmount *= 5.8f;
                    }
                    break;
                case GBP:
                    switch (destination.getCurrency()) {
                        case USD -> newAmount *= 0.79f;
                        case EUR -> newAmount *= 0.86f;
                        case RON -> newAmount *= 0.17f;
                    }
                    break;
            }
        }
        if (balance < newAmount)
            return false;
        addTransaction(new Transaction(IBAN, destination.getIBAN(), amount, destination.getCurrency()));
        withdrawFunds(newAmount);
        destination.addFunds(amount);
        return true;
    }

    public void saveMoney(SavingsAccount destination, float amount) {
        addTransaction(new Transaction(IBAN, destination.getIBAN(), amount, currency));
        withdrawFunds(amount);
        destination.addFunds(amount);
    }

    private String generateIBAN(Currencies currency) {
        String prefix = currency.toString().substring(0, 2) + "13" + "CUCB";
        String toBeIBAN = prefix;
        for (int i = 0; i < 14; i++)
            toBeIBAN += (int) (Math.random() * 10);
        while (CSVReader.getAccountFromIBAN(toBeIBAN) != null) {
            toBeIBAN = prefix;
            for (int i = 0; i < 14; i++)
                toBeIBAN += (int) (Math.random() * 10);
        }
        return toBeIBAN;
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