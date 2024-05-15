import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface Audit {
    enum Type {
        ACCOUNT_CREATION, ACCOUNT_UPDATE, ACCOUNT_DELETION, ACCOUNT_READ,
        SAVINGS_ACCOUNT_CREATION, SAVINGS_ACCOUNT_UPDATE, SAVINGS_ACCOUNT_DELETION, SAVINGS_ACCOUNT_READ,
        TRANSACTION_CREATION, TRANSACTION_UPDATE, TRANSACTION_DELETION, TRANSACTION_READ,
        CLIENT_CREATION, CLIENT_UPDATE, CLIENT_DELETION, CLIENT_READ,
        CLIENT_LOGIN, CLIENT_LOGOUT,
        CREDIT_CARD_CREATION, CREDIT_CARD_UPDATE, CREDIT_CARD_DELETION, CREDIT_CARD_READ,
        DEBIT_CARD_CREATION, DEBIT_CARD_UPDATE, DEBIT_CARD_DELETION, DEBIT_CARD_READ,
        CARD_READ,
        SAVE_MONEY
    }

    static void writeLog(Type log, Boolean success) {
        String path = "/Users/levismac/Documents/INTELLIJ/cashedUpCoon/src/main/resources/audit_log.csv";
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() != 0) {
                bw.newLine();
            }
            bw.write(log.toString() + "," + success.toString() + "," + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            System.out.println("Log updated successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
