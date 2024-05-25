import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

abstract class Card implements CSVManager {
    private String number;
    private String cvv;
    private Date expirationDate; // sdf: MM-YYYY
    private Boolean blocked = false;

    public Card(String number, String cvv, Date expirationDate) {
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public Card(String number, String cvv, Date expirationDate, Boolean blocked) {
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.blocked = blocked;
    }

    public Card() {
        this.number = generateRandomNumber();
        this.cvv = generateRandomCVV();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 4);
        this.expirationDate = calendar.getTime();
        this.blocked = false;
    }

    public String generateRandomNumber() {
        String numbers = "0123456789";
        Random random = new Random();
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < 16; i++)
            number.append(numbers.charAt(random.nextInt(numbers.length())));

        DebitCard debitCard = new DebitCard(number.toString(), "123", new Date(), false, 1000);
        if (debitCard.verifyNotInCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/credit_card_test.csv") && debitCard.verifyNotInCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/debit_card_test.csv"))
            return number.toString();
        else
            return generateRandomNumber();
    }

    public String generateRandomCVV() {
        String numbers = "0123456789";
        Random random = new Random();
        StringBuilder cvv = new StringBuilder();

        for (int i = 0; i < 3; i++)
            cvv.append(numbers.charAt(random.nextInt(numbers.length())));

        return cvv.toString();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
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
                if (temporary[1].equals(this.number)) {
                    fr.close();
                    br.close();
                    return false;
                }
            }
            fr.close();
            br.close();
            Audit.writeLog(Audit.Type.CARD_READ, true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Audit.writeLog(Audit.Type.CARD_READ, false);
            return false;
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
                if (!(temporary[1].equals(this.number)))
                    data += line + "\n";
                else
                    deleted = true;
            }
            data = data.substring(0, data.length() - 1);
            if (deleted) {
                if (this.getClass() == CreditCard.class) {
                    Audit.writeLog(Audit.Type.CREDIT_CARD_DELETION, true);
                } else {
                    Audit.writeLog(Audit.Type.DEBIT_CARD_DELETION, true);
                }
            } else {
                if (this.getClass() == CreditCard.class) {
                    System.out.println("Credit card not found!");
                    Audit.writeLog(Audit.Type.CREDIT_CARD_DELETION, false);
                } else {
                    System.out.println("Debit card not found!");
                    Audit.writeLog(Audit.Type.DEBIT_CARD_DELETION, false);
                }
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
            if (this.getClass() == CreditCard.class)
                Audit.writeLog(Audit.Type.CREDIT_CARD_DELETION, false);
            else
                Audit.writeLog(Audit.Type.DEBIT_CARD_DELETION, false);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(number, card.number) && Objects.equals(cvv, card.cvv) && Objects.equals(expirationDate, card.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, cvv, expirationDate);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return "Card:\n\t\tNumber: " + number + "\n\t\tCVV: " + cvv + "\n\t\tExpiration Date: " + sdf.format(expirationDate) + "\n\t\tBlocked: " + blocked + "\n";
    }
}