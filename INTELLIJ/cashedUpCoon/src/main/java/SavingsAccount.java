import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class SavingsAccount extends Account {
    private float interestRate;
    private Date dayOfPayment = new Date(); // sdf: dd

    public SavingsAccount(String IBAN, float balance, Currencies currency, float interestRate) {
        super(IBAN, balance, currency);
        this.interestRate = interestRate;
    }

    public SavingsAccount(String IBAN, float balance, Currencies currency, float interestRate, Date dayOfPayment) {
        super(IBAN, balance, currency);
        this.interestRate = interestRate;
        this.dayOfPayment = dayOfPayment;
    }

    public SavingsAccount(float balance, Currencies currency, float interestRate) {
        super(balance, currency);
        this.interestRate = interestRate;
    }

    public SavingsAccount(Currencies currency, float interestRate) {
        super(currency);
        this.interestRate = interestRate;
    }

    public SavingsAccount(Currencies currency) {
        super(currency);
        this.interestRate = 1.3f;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public Date getDayOfPayment() {
        return dayOfPayment;
    }

    public void setDayOfPayment(Date dayOfCreation) {
        this.dayOfPayment = dayOfCreation;
    }

    public void addInterest() {
        setBalance(getBalance() + getBalance() * interestRate);
    }

    public boolean verifyPayment() {
        if (new SimpleDateFormat("dd").format(new Date()).equals(new SimpleDateFormat("dd").format(dayOfPayment)))
            addInterest();
        else
            return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SavingsAccount that = (SavingsAccount) o;
        return Float.compare(interestRate, that.interestRate) == 0 && Objects.equals(dayOfPayment, that.dayOfPayment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interestRate, dayOfPayment);
    }

    @Override
    public String toString() {
        return super.toString() + "\n\t\tInterest Rate: " + interestRate + "\n\t\tDay of Payment: " + new SimpleDateFormat("dd").format(dayOfPayment) + "\n";
    }
}