import java.io.*;
import java.util.Date;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class Transaction implements CSVManager {
    private static int idCounter = 1;
    private int id;
    private String sourceIBAN;
    private String destinationIBAN;
    private float amount;
    private Currencies currency;
    private Date date;

    public Transaction(String sourceIBAN, String destinationIBAN, float amount, Currencies currency, Date date) {
        this.id = idCounter++;
        this.sourceIBAN = sourceIBAN;
        this.destinationIBAN = destinationIBAN;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
    }

    public Transaction(String sourceIBAN, String destinationIBAN, float amount, Currencies currency) {
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

    public Currencies getCurrency() {
        return currency;
    }

    public void setCurrency(Currencies currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean verifyNotInCSV(String path) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (temporary[2].equals(this.sourceIBAN) && temporary[3].equals(this.destinationIBAN) && temporary[6].equals(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(this.date))) {
                    fr.close();
                    br.close();
                    return false;
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.TRANSACTION_READ, true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.TRANSACTION_READ, false);
            return false;
        }
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
                    Audit.writeLog(Audit.Type.TRANSACTION_CREATION, false);
                    return;
                }
            }
            bw.write(phoneNumber[0] + "," + phoneNumber[1] + "," + this.sourceIBAN + "," + this.destinationIBAN + "," + this.amount + "," + this.currency + "," + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(this.date));
            Audit.writeLog(Audit.Type.TRANSACTION_CREATION, true);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.TRANSACTION_CREATION, false);
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
                if (temporary[2].equals(this.sourceIBAN) || temporary[3].equals(this.destinationIBAN))
                    data += temporary[0] + "," + temporary[1] + "," + this.sourceIBAN + "," + this.destinationIBAN + "," + this.amount + "," + this.currency + "," + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.date) + "\n";
                else
                    data += line + "\n";
            }
            data = data.substring(0, data.length() - 1);
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            Audit.writeLog(Audit.Type.TRANSACTION_UPDATE, true);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.TRANSACTION_UPDATE, false);
        }
    }

    @Override
    public void deleteCSV(String path) {
        try {
            boolean deleted = false;
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] temporary;
            String data = "";
            while ((line = br.readLine()) != null) {
                temporary = line.split(",");
                if (!(temporary[2].equals(this.sourceIBAN) && temporary[3].equals(this.destinationIBAN)))
                    data += line + "\n";
                else
                    deleted = true;
            }
            data = data.substring(0, data.length() - 1);
            if (deleted) {
                Audit.writeLog(Audit.Type.TRANSACTION_DELETION, true);
            } else {
                System.out.println("Transaction not found!");
                Audit.writeLog(Audit.Type.TRANSACTION_DELETION, false);
            }
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.TRANSACTION_DELETION, false);
        }
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "\t\tTransaction:\n\t\tID: " + id + "\n\t\tSource IBAN: " + sourceIBAN + "\n\t\tDestination IBAN: " + destinationIBAN + "\n\t\tAmount: " + amount + "\n\t\tCurrency: " + currency + "\n\t\tDate: " + sdf.format(date) + "\n";
    }
}