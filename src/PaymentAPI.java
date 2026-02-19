public class PaymentAPI {

    public void charge(String token, double amount){
        System.out.println("Charging $" + amount + " using token " + token);
        System.out.println("Payment Successful");
    }
}
