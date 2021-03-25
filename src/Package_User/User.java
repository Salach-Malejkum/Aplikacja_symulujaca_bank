package Package_User;

import Package_GUI.Bank.Bank;
import Package_GUI.Observers.Observer;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


public class User implements Serializable, Subject {


    private static final long serialVersionUID = 1133222626933495786L;
    private String login;
    private String name;
    private String surname;
    private String pesel;
    private String mail;
    private String password;
    private BigDecimal accountbalance;
    private ArrayList<Payment> paymenthistory;
    private int [] owned;
    ArrayList<Observer> ObserverList = new ArrayList<Observer>();

    public User(String login, String name, String surname, String pesel, String mail, String password){

        paymenthistory = new ArrayList<>();
        paymenthistory.add(new Payment("", "Konto otwarte", new BigDecimal(0.00)));
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.mail = mail;
        this.password = password;
        accountbalance = new BigDecimal(0.00);
        owned = new int [4];
        for (int a: owned) {
            a = 0;
        }
    }

    /*
    //
    //      Nowy przelew
    //
     */
    public  void newPayment(String accountnumb, String title, BigDecimal amount){

        setData(accountnumb, title, amount);
        BigDecimal substractbd = new BigDecimal(amount.doubleValue()*-1).setScale(2, RoundingMode.DOWN);
        accountbalance = accountbalance.subtract(substractbd);
        String [] strings = {getPaymenthistory().get(getPaymenthistory().size()-1).getDate(), getPaymenthistory().get(getPaymenthistory().size()-1).getAccoutnumb(),
                getPaymenthistory().get(getPaymenthistory().size()-1).getTitle(), getPaymenthistory().get(getPaymenthistory().size()-1).getAmount().toString()};
        notifyObservers();

    }

    /*
    //
    //      Wpłata pieniedzy
    //
     */
    public void AddMoney(String s){

        String accountnumb = "--";
        String title = "Wpłata";
        setData(accountnumb, title, new BigDecimal(s));
        BigDecimal bigDecimal = new BigDecimal(s);
        accountbalance = new BigDecimal(String.valueOf(accountbalance.add(bigDecimal)));
        String [] strings = {getPaymenthistory().get(getPaymenthistory().size()-1).getDate(), getPaymenthistory().get(getPaymenthistory().size()-1).getAccoutnumb(),
                getPaymenthistory().get(getPaymenthistory().size()-1).getTitle(), getPaymenthistory().get(getPaymenthistory().size()-1).getAmount().toString()};
        notifyObservers();

    }
    public void buyAction(int i, BigDecimal bigDecimal){

     int value = Bank.getUser().getOwned(i);
     value ++;
     Bank.getUser().setOwned(i, value);
     i++;
     newPayment("--", "Kupno akcji firmy" + i, bigDecimal.multiply(new BigDecimal(-1)));

    }
    public void sellAction(int i, BigDecimal bigDecimal){
        int value = Bank.getUser().getOwned(i);
        value--;
        Bank.getUser().setOwned(i, value);
        i++;
        setAccountbalance(getAccountbalance().add(bigDecimal));
        setData("--", "Sprzedaż akcji firmy" + i, bigDecimal);
        String [] strings = {getPaymenthistory().get(getPaymenthistory().size()-1).getDate(), getPaymenthistory().get(getPaymenthistory().size()-1).getAccoutnumb(),
                getPaymenthistory().get(getPaymenthistory().size()-1).getTitle(), getPaymenthistory().get(getPaymenthistory().size()-1).getAmount().toString()};
        notifyObservers();


    }

    public int  getOwned(int i) {
        return owned[i];
    }

    public void setOwned(int i, int value) {
        this.owned[i] = value;
    }

    public BigDecimal getAccountbalance() {
        return accountbalance;
    }

    public void setAccountbalance(BigDecimal accountbalance) {
        this.accountbalance = accountbalance;
    }

    public  ArrayList<Payment> getPaymenthistory() {
        return paymenthistory;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPesel() {
        return pesel;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public void setData(String accountnumb, String title, BigDecimal bigDecimal) {

        paymenthistory.add(new Payment(accountnumb, title, bigDecimal));
    }


    @Override
    public void registerObserver(Observer observer) {

        ObserverList.add(observer);
    }

    @Override
    public void removeObserver() {

        ObserverList.removeAll(ObserverList);
    }

    @Override
    public void notifyObservers() {

        for (int i=0; i<ObserverList.size(); i++){
            ObserverList.get(i).update(paymenthistory.get(paymenthistory.size()-1).getState());
        }
    }
}
