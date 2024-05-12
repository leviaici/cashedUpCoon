import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

abstract class Card {
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
        if (CSVManager.verifyCardNotInCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/credit_card_test.csv",debitCard) && CSVManager.verifyCardNotInCSV("/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/debit_card_test.csv",debitCard))
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