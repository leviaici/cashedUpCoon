import java.util.Date;
import java.util.Objects;

public class DebitCard extends Card {
    private int withdrawLimitPerTransaction;

    public DebitCard(String number, String cvv, Date expirationDate, int withdrawLimitPerTransaction) {
        super(number, cvv, expirationDate);
        this.withdrawLimitPerTransaction = withdrawLimitPerTransaction;
    }

    public DebitCard(String number, String cvv, Date expirationDate, Boolean blocked, int withdrawLimitPerTransaction) {
        super(number, cvv, expirationDate, blocked);
        this.withdrawLimitPerTransaction = withdrawLimitPerTransaction;
    }

    public int getWithdrawLimitPerTransaction() {
        return withdrawLimitPerTransaction;
    }

    public void setWithdrawLimitPerTransaction(int withdrawLimitPerTransaction) {
        this.withdrawLimitPerTransaction = withdrawLimitPerTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DebitCard debitCard = (DebitCard) o;
        return withdrawLimitPerTransaction == debitCard.withdrawLimitPerTransaction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), withdrawLimitPerTransaction);
    }

    @Override
    public String toString() {
        return super.toString() + "\n\t\tWithdraw Limit Per Transaction: " + withdrawLimitPerTransaction + "\n";
    }
}