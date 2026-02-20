public class PaymentAPI implements PaymentByToken {

    @Override
    public String createToken(String cardNumber, String expiry, String cvv){
        System.out.println("Generating Token....");
        return "tok_" + cardNumber.substring(cardNumber.length() - 4) + expiry + cvv;
    }

    @Override
    public void charge(String token, double amount){
        System.out.println("Charging $" + amount + " using token " + token);
        System.out.println("Payment Successful");
    }
}
