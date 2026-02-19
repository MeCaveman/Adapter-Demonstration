public class App {

    public static void main(String[] args) {
        String cardNumber = "1234567891011112";
        String expiry = "02/26"; //(MM/YY)
        String cvv = "123";
        double amount = 1533.50;

        PaymentInfo paymentInfo = new PaymentInfo(cardNumber, expiry, cvv, amount);

        PaymentByCard paymentByCard = new PaymentAdapter(new PaymentAPI());

        paymentByCard.processPayment(paymentInfo);
    }
}
