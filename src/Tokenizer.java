public class Tokenizer {

    public String createToken(String cardNumber, String expiry, String cvv){
        System.out.println("Generating Token....");
        return "tok_" + cardNumber.substring(cardNumber.length() - 4);
    }
}
