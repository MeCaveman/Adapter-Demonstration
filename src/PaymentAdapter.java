public class PaymentAdapter implements PaymentByCard{

    private PaymentByToken paymentByToken;
    private PaymentAPI paymentAPI;

    public PaymentAdapter(PaymentAPI paymentAPI) {
        this.paymentAPI = paymentAPI;
        this.paymentByToken = new PaymentAPI();
    }

    @Override
    public void processPayment(PaymentInfo paymentInfo){
        String token = paymentByToken.createToken(paymentInfo.getCardNumber(), paymentInfo.getExpiry(), paymentInfo.getCvv());

        paymentAPI.charge(token, paymentInfo.getAmount());
    }
}
