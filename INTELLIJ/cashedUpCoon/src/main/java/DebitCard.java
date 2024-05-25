import java.io.*;
import java.text.SimpleDateFormat;
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

    public DebitCard(int withdrawLimitPerTransaction) {
        super();
        this.withdrawLimitPerTransaction = withdrawLimitPerTransaction;
    }

    public int getWithdrawLimitPerTransaction() {
        return withdrawLimitPerTransaction;
    }

    public void setWithdrawLimitPerTransaction(int withdrawLimitPerTransaction) {
        this.withdrawLimitPerTransaction = withdrawLimitPerTransaction;
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
                    Audit.writeLog(Audit.Type.DEBIT_CARD_CREATION, false);
                    return;
                }
            }
            bw.write(phoneNumber[0] + "," + this.getNumber() + "," + this.getCvv() + "," + new SimpleDateFormat("yyyy-MM").format(this.getExpirationDate()) + "," + this.getBlocked() + "," + this.withdrawLimitPerTransaction);
            Audit.writeLog(Audit.Type.DEBIT_CARD_CREATION, true);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.DEBIT_CARD_CREATION, false);
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
                if (temporary[1].equals(this.getNumber()))
                    data += temporary[0] + "," + this.getNumber() + "," + this.getCvv() + "," + new SimpleDateFormat("yyyy-MM").format(this.getExpirationDate()) + "," + this.getBlocked() + "," + this.withdrawLimitPerTransaction + "\n";
                else
                    data += line + "\n";
            }
            data = data.substring(0, data.length() - 1);
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            Audit.writeLog(Audit.Type.DEBIT_CARD_UPDATE, true);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.DEBIT_CARD_UPDATE, false);
        }
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