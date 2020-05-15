package edu.slcc.asdv.utils;

import com.simplify.payments.PaymentsApi;
import com.simplify.payments.PaymentsMap;
import com.simplify.payments.domain.CardToken;
import com.simplify.payments.domain.Payment;

public class TestCreditCard {

    final static String publicKey = "sbpb_Nzk0NDRjNjAtZjU0Mi00NmE4LWE2MWQtNDU3MDNhZmIwNWU4";
    final static String privateKey = "zu/lhpHjycgRqyHuQHxLZr3c2udxavluC2zWIBE7TKF5YFFQL0ODSXAOkNtXTToq";

    public boolean check = false;

    public TestCreditCard(String city, String state, String cvc, int month, int year, String cardNumber) {
        try {
            String token = generateToken(city, state, cvc, month, year, cardNumber);
            //PaymentsMap map = new PaymentsMap();            
            Payment payment = Payment.create(new PaymentsMap()
                    .set("currency", "USD")
                    .set("token", token)
                    .set("amount", 19999) // In cents e.g. $10.00
                    .set("description", "prod description")
            );

            if ("APPROVED".equals(payment.get("paymentStatus"))) {
                System.out.println("Payment approved");
                check = true;
            } else {
                System.out.println("Payment failed: \n" + payment.toString() + "\nstatus: " + payment.get("paymentStatus"));
                check = false;
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private String generateToken(String city, String state, String cvc, int month, int year, String cardNumber) {
        PaymentsApi.PUBLIC_KEY = publicKey;
        PaymentsApi.PRIVATE_KEY = privateKey;
        CardToken cardToken = null;
        try {
            PaymentsMap pm = new PaymentsMap()
                    .set("card.addressCity", city)
                    .set("card.addressState", state)
                    .set("card.cvc", cvc)
                    .set("card.expMonth", month)
                    .set("card.expYear", year)
                    .set("card.number", cardNumber);
            cardToken = CardToken.create(pm);
        } catch (Exception e) {
            System.err.println("token generation failed " + e.getMessage());
        }
        System.out.println("card token: " + cardToken);
        return cardToken.get("id").toString();

    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

}
