import java.util.Date;
import java.util.Objects;

public class CreditCard extends Card {
    private float creditLimitPerTransaction;

    public CreditCard(String number, String cvv, Date expirationDate, float creditLimitPerTransaction) {
        super(number, cvv, expirationDate);
        this.creditLimitPerTransaction = creditLimitPerTransaction;
    }

    public CreditCard(String number, String cvv, Date expirationDate, Boolean blocked, float creditLimitPerTransaction) {
        super(number, cvv, expirationDate, blocked);
        this.creditLimitPerTransaction = creditLimitPerTransaction;
    }

    public CreditCard(float creditLimitPerTransaction) {
        super();
        this.creditLimitPerTransaction = creditLimitPerTransaction;
    }

    public float getCreditLimitPerTransaction() {
        return creditLimitPerTransaction;
    }

    public void setCreditLimitPerTransaction(float creditLimitPerTransaction) {
        this.creditLimitPerTransaction = creditLimitPerTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CreditCard that = (CreditCard) o;
        return Float.compare(creditLimitPerTransaction, that.creditLimitPerTransaction) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), creditLimitPerTransaction);
    }

    @Override
    public String toString() {
        return super.toString() + "\n\tCredit Limit Per Transaction: " + creditLimitPerTransaction + "\n";
    }
}