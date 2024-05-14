public interface Audit {
    enum Type {
        ACCOUNT_CREATION, ACCOUNT_UPDATE, ACCOUNT_DELETION, ACCOUNT_READ,
        SAVINGS_ACCOUNT_CREATION, SAVINGS_ACCOUNT_UPDATE, SAVINGS_ACCOUNT_DELETION, SAVINGS_ACCOUNT_READ,
        TRANSACTION_CREATION, TRANSACTION_UPDATE, TRANSACTION_DELETION, TRANSACTION_READ,
        CLIENT_CREATION, CLIENT_UPDATE, CLIENT_DELETION, CLIENT_READ,
        CLIENT_LOGIN, CLIENT_LOGOUT,
        CREDIT_CARD_CREATION, CREDIT_CARD_UPDATE, CREDIT_CARD_DELETION, CREDIT_CARD_READ,
        DEBIT_CARD_CREATION, DEBIT_CARD_UPDATE, DEBIT_CARD_DELETION, DEBIT_CARD_READ,
        FILES_INITIALIZATION, FILES_DELETION
    }
}
