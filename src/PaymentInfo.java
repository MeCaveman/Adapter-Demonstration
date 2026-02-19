public class PaymentInfo {
    private String cardNumber;
    private String expiry;
    private String cvv;
    private double amount;


    public PaymentInfo(String cardNumber, String expiry, String cvv, double amount) {
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cvv = cvv;
        this.amount = amount;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getCvv() {
        return cvv;
    }

    public double getAmount() {
        return amount;
    }
}
