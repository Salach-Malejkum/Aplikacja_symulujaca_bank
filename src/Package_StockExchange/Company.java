package Package_StockExchange;

import Package_GUI.Bank.Bank;
import Package_User.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

public class Company implements Serializable {

    private static final long serialVersionUID = 4015780275907325915L;
    private String name;
    private BigDecimal value;


    public Company(String name, BigDecimal value){

        this.name = name;
        this.value = value;
        value.setScale(2);
    }
    public void randomChange(){

        Random r = new Random();
        if (r.nextInt()%2 ==0){
            BigDecimal add = new BigDecimal(1.05);
            value = value.multiply(add).setScale(2, RoundingMode.DOWN);
        }else {
            BigDecimal substract = new BigDecimal(0.95);
            value = value.multiply(substract).setScale(2, RoundingMode.DOWN);
        }
    }
    public String toString(int i){

        return "Akcje " + getName() + " cena: " + getValue()  + " zł" + " Posiadane: " + Bank.getUser().getOwned(i);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
