public interface PaymentByToken {

    String createToken(String cardNumber, String expiry, String cvv);

    void charge(String token, double amount);
}
