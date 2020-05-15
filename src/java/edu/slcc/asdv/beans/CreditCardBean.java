package edu.slcc.asdv.beans;

import edu.slcc.asdv.utils.TestCreditCard;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.flow.FlowScoped;

@Named(value = "creditCardBean")
@FlowScoped(value = "creditcard")
public class CreditCardBean implements Serializable {
    private String card = "";
    private String cvc = "";
    private String month;
    private String year;

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    public String checkCard(){
        InformationBean info = new InformationBean();
        String city = info.getCity();
        String state = info.getState();
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        TestCreditCard c = new TestCreditCard(city, state, cvc, monthInt, yearInt, card);
        boolean check = c.check;
        if (check){
            return "confirm";
        }
        else{
            return "failed";
        }
    }

}
