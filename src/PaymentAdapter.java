public class PaymentAdapter implements PaymentByCard{

    private Tokenizer tokenizer;
    private PaymentAPI paymentAPI;

    public PaymentAdapter(PaymentAPI paymentAPI) {
        this.paymentAPI = paymentAPI;
        this.tokenizer = new Tokenizer();
    }

    @Override
    public void processPayment(PaymentInfo paymentInfo){
        String token = tokenizer.createToken(paymentInfo.getCardNumber(),
                paymentInfo.getExpiry(), paymentInfo.getCvv());

        paymentAPI.charge(token, paymentInfo.getAmount());
    }
}
