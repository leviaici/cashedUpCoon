import java.io.*;
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
                    Audit.writeLog(Audit.Type.SAVINGS_ACCOUNT_CREATION, false);
                    return;
                }
            }
            bw.write(phoneNumber[0] + "," + this.getIBAN() + "," + this.getBalance() + "," + this.getCurrency() + "," + this.getInterestRate() + "," + new SimpleDateFormat("yyyy-MM-dd").format(this.dayOfPayment));
            Audit.writeLog(Audit.Type.SAVINGS_ACCOUNT_CREATION, true);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.SAVINGS_ACCOUNT_CREATION, false);
        }
    }

    @Override
    public void updateCSV(String path) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[1].equals(this.getIBAN()))
                    data += temporary[0] + "," + this.getIBAN() + "," + this.getBalance() + "," + this.getCurrency() + "," + this.getInterestRate() + "," + new SimpleDateFormat("yyyy-MM-dd").format(this.dayOfPayment) + "\n";
                else
                    data += line + "\n";
            }
            data = data.substring(0, data.length() - 1);
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            Audit.writeLog(Audit.Type.SAVINGS_ACCOUNT_UPDATE, true);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.SAVINGS_ACCOUNT_UPDATE, false);
        }
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