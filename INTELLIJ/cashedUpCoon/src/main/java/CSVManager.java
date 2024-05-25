public interface CSVManager {
    boolean verifyNotInCSV(String path);
    void addCSV(String path, String ... phoneNumber);
    void updateCSV(String path);
    void deleteCSV(String path);
}