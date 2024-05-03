import java.util.Date;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class Transaction {
    private static int idCounter = 1;
    private int id;
    private String sourceIBAN;
    private String destinationIBAN;
    private float amount;
    private String currency;
    private Date date;

    public Transaction(String sourceIBAN, String destinationIBAN, float amount, String currency, Date date) {
        this.id = idCounter++;
        this.sourceIBAN = sourceIBAN;
        this.destinationIBAN = destinationIBAN;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
    }

    public Transaction(String sourceIBAN, String destinationIBAN, float amount, String currency) {
        this.id = idCounter++;
        this.sourceIBAN = sourceIBAN;
        this.destinationIBAN = destinationIBAN;
        this.amount = amount;
        this.currency = currency;
        this.date = new Date();
    }

    public static int getId_counter() {
        return idCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceIBAN() {
        return sourceIBAN;
    }

    public void setSourceIBAN(String sourceIBAN) {
        this.sourceIBAN = sourceIBAN;
    }

    public String getDestinationIBAN() {
        return destinationIBAN;
    }

    public void setDestinationIBAN(String destinationIBAN) {
        this.destinationIBAN = destinationIBAN;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && Float.compare(amount, that.amount) == 0 && Objects.equals(sourceIBAN, that.sourceIBAN) && Objects.equals(destinationIBAN, that.destinationIBAN) && Objects.equals(currency, that.currency) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceIBAN, destinationIBAN, amount, currency, date);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        return "\t\tTransaction:\n\t\tID: " + id + "\n\t\tSource IBAN: " + sourceIBAN + "\n\t\tDestination IBAN: " + destinationIBAN + "\n\t\tAmount: " + amount + "\n\t\tCurrency: " + currency + "\n\t\tDate: " + sdf.format(date) + "\n";
    }
}