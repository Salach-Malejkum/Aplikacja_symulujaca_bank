package Package_User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment implements Serializable {

    private static final long serialVersionUID = -3967243121329668223L;
    private String date;
    private String accoutnumb;
    private String title;
    private BigDecimal amount;

    public Payment(String accoutnumb, String title, BigDecimal amount){

        date = String.valueOf(LocalDate.now());
        this.accoutnumb = accoutnumb;
        this.title = title;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getAccoutnumb() {
        return accoutnumb;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public String [] getState(){
        String [] paymentstate = {getDate(), getAccoutnumb(), getTitle(), String.valueOf(getAmount())};

        return paymentstate;
    }
}
