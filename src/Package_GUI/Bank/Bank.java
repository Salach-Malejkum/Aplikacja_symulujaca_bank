package Package_GUI.Bank;

import Package_GUI.Actions.Actions;
import Package_GUI.Observers.Observer;
import Package_StockExchange.StockEchange;
import Package_User.Payment;
import Package_User.Subject;
import Package_User.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

import static Package_GUI.Bank.PaymentHistoryData.getPaymentData;
import static Package_GUI.Styles.Style.*;

public class Bank extends JFrame implements Observer {

    private static JFrame frame;
    private static JTabbedPane jtpbank;
    private static JPanel transfer;
    private static JTable transferhistory;
    private static JPanel payment;
    private static JPanel stockexchange;
    private static JPanel data;
    private static User user;
    private static JTextField texttitle;
    private static JTextField textaccountnumb;
    private static JTextField textamount;
    private static JFrame framepayment;
    private static JPanel panelpayment;
    private static JTextField textpayment;
    private static JLabel accountbalance;
    private static final String [] columnnameshistory = {"Data", "Numer konta", "Tytuł", "Kwota"};
    private static JFrame framepassword;
    private static JPasswordField actualpassword;
    private static JPasswordField newpassword1;
    private static JPasswordField newpassword2;
    private static JPanel panelpassword;
    private static JFrame framchange;
    private static JTextField textchange;
    private static JPanel panelchange;
    private static JLabel labelimie;
    private static JLabel labelsurname;
    private static JLabel labelpesel;
    private static JLabel labelmail;
    private static String [][] transferdata;
    private static DefaultTableModel defaultTableModelhistory;
    private static final String [] columnnamesstockexchange = {"Nazwa firmy" , "Cena akcji", "Posiadane akcje", ""};
    private static DefaultTableModel defaultTableModelstockexchange;
    private static ArrayList<StockEchange> arrayListstockechgane;
    private static JLabel company1;
    private static JLabel company2;
    private static JLabel company3;
    private static JLabel company4;
    private static Thread threadbalance;
    private static Thread threadstate;
    private static StockEchange stockEchangedata;
    private Subject subjectuser;

    public Bank(Subject subjectuser){

        this.user = (User) subjectuser;
        this.subjectuser = subjectuser;
        this.subjectuser.registerObserver(this);

        /*
        //
        //      Frame banku
        //
         */

        frame = new JFrame("PWRbank");
        frame.setBounds(600,300,600,400);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        FrameIcon(frame);


        /*
        //
        // Panel historia i nowy przelew
        //
         */

        transfer = new JPanel();
        transfer.setBounds(0, 0, 600, 400);
        transfer.setLayout(null);
        transfer.setVisible(true);
        panelStyle2(transfer);


        JButton newPayment = new JButton("Nowy przelew");
        newPayment.setBounds(30,20,100,20);
        newPayment.addActionListener(new Actions.newPayment());
        transfer.add(newPayment);
        buttonStyle(newPayment);

        JButton buttonblik = new JButton("BLIK!");
        buttonblik.setBounds(200,20,100,20);
        buttonblik.addActionListener(new Actions.ButtonBlik());
        transfer.add(buttonblik);
        buttonStyle(buttonblik);

        JButton buttonlogout1 = new JButton("Wyloguj");
        buttonlogout1.setBounds(370,20,100,20);
        buttonlogout1.addActionListener(new Actions.ButtonLogout(user, frame, jtpbank));
        transfer.add(buttonlogout1);
        buttonStyle(buttonlogout1);

        accountbalance = new JLabel("Stan konta: " + user.getAccountbalance().toString() + " zł");
        accountbalance.setBounds(20,50,200,20);
        transfer.add(accountbalance);
        textStyleBig(accountbalance);


        // Utowrzenie tablicy historii transakcji

        transferdata = getPaymentData(user);
        defaultTableModelhistory = new DefaultTableModel(transferdata, columnnameshistory);
        transferhistory = new JTable(defaultTableModelhistory);
        transferhistory.setDefaultEditor(Object.class, null);


        JScrollPane jhistory = new JScrollPane(transferhistory);
        jhistory.setBounds(0,100,600,220);

        transfer.add(jhistory);


        /*
        //
        //      Panel wpłaty na konto
        //
         */

        payment = new JPanel();
        payment.setLayout(null);
        panelStyle(payment);

        JLabel paymentlabel = new JLabel("Wpisz kwotę którą chcesz wpłacić:");
        paymentlabel.setBounds(150,100,200,20);
        payment.add(paymentlabel);
        textStyle(paymentlabel);

        textpayment = new JTextField();
        textpayment.setBounds(150,120,200,20);
        payment.add(textpayment);

        JButton buttonaddmoney = new JButton("Wpłać");
        buttonaddmoney.setBounds(200,160,100,20);
        buttonaddmoney.addActionListener(new Actions.ButtonAddMoney(user));
        payment.add(buttonaddmoney);
        buttonStyle(buttonaddmoney);

        /*
        //
        //  Panel Giełda
        //
         */


        stockexchange = new JPanel();
        stockexchange.setLayout(null);
        panelStyle(stockexchange);

        stockEchangedata = new StockEchange();

        company1 = new JLabel(stockEchangedata.getCompanyArrayList().get(0).toString(0));
        company1.setBounds(10,50,300,20);
        stockexchange.add(company1);
        textStyleBig(company1);

        JButton buttonbuy1 = new JButton("Kup");
        buttonbuy1.setBounds(340,50,100,20);
        stockexchange.add(buttonbuy1);
        buttonStyle(buttonbuy1);
        buttonbuy1.addActionListener(new Actions.ButtonBuyAction(1));

        JButton buttonsell1 = new JButton("Sprzedaj");
        buttonsell1.setBounds(450,50,100,20);
        stockexchange.add(buttonsell1);
        buttonStyle(buttonsell1);
        buttonsell1.addActionListener(new Actions.ButtonSellAction(1));


        company2 = new JLabel(stockEchangedata.getCompanyArrayList().get(1).toString(1));
        company2.setBounds(10,100,300,20);
        stockexchange.add(company2);
        textStyleBig(company2);

        JButton buttonbuy2 = new JButton("Kup");
        buttonbuy2.setBounds(340,100,100,20);
        stockexchange.add(buttonbuy2);
        buttonStyle(buttonbuy2);
        buttonbuy2.addActionListener(new Actions.ButtonBuyAction(2));

        JButton buttonsell2 = new JButton("Sprzedaj");
        buttonsell2.setBounds(450,100,100,20);
        stockexchange.add(buttonsell2);
        buttonStyle(buttonsell2);
        buttonsell2.addActionListener(new Actions.ButtonSellAction(2));


        company3 = new JLabel(stockEchangedata.getCompanyArrayList().get(2).toString(2));
        company3.setBounds(10,150,300,20);
        stockexchange.add(company3);
        textStyleBig(company3);

        JButton buttonbuy3 = new JButton("Kup");
        buttonbuy3.setBounds(340,150,100,20);
        stockexchange.add(buttonbuy3);
        buttonStyle(buttonbuy3);
        buttonbuy3.addActionListener(new Actions.ButtonBuyAction(3));

        JButton buttonsell3 = new JButton("Sprzedaj");
        buttonsell3.setBounds(450,150,100,20);
        stockexchange.add(buttonsell3);
        buttonStyle(buttonsell3);
        buttonsell3.addActionListener(new Actions.ButtonSellAction(3));


        company4 = new JLabel(stockEchangedata.getCompanyArrayList().get(3).toString(3));
        company4.setBounds(10,200,300,20);
        stockexchange.add(company4);
        textStyleBig(company4);

        JButton buttonbuy4 = new JButton("Kup");
        buttonbuy4.setBounds(340,200,100,20);
        stockexchange.add(buttonbuy4);
        buttonStyle(buttonbuy4);
        buttonbuy4.addActionListener(new Actions.ButtonBuyAction(4));

        JButton buttonsell4 = new JButton("Sprzedaj");
        buttonsell4.setBounds(450,200,100,20);
        stockexchange.add(buttonsell4);
        buttonStyle(buttonsell4);
        buttonsell4.addActionListener(new Actions.ButtonSellAction(4));

        threadbalance = new Thread(()->{
            while (frame.isVisible()) {
                AtomicInteger i= new AtomicInteger();
                try {

                    stockEchangedata.getCompanyArrayList().forEach(company -> {

                        if (i.get() == 0){

                            company.randomChange();
                        }else if (i.get() == 1){

                            company.randomChange();
                        }else if (i.get() == 2){

                            company.randomChange();
                        }else if (i.get() == 3){

                            company.randomChange();
                        }
                        i.getAndIncrement();

                        stockEchangedata.saveStockExchange();
                    });

                    Thread.sleep(Duration.ofSeconds(3).toMillis());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        });
        threadstate = new Thread(()->{
            while (frame.isVisible()) {
                AtomicInteger i= new AtomicInteger();
                try {

                    stockEchangedata.getCompanyArrayList().forEach(company -> {

                        if (i.get() == 0){

                            getCompany(1).setText(company.toString(i.get()));
                        }else if (i.get() == 1){

                            getCompany(2).setText(company.toString(i.get()));
                        }else if (i.get() == 2){

                            getCompany(3).setText(company.toString(i.get()));
                        }else if (i.get() == 3){

                            getCompany(4).setText(company.toString(i.get()));
                        }
                        i.getAndIncrement();

                        stockEchangedata.saveStockExchange();
                    });

                    Thread.sleep(Duration.ofMillis(50).toMillis());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        });



        /*
        //
        //      Panel danych
        //
         */

        data = new JPanel();
        data.setLayout(null);
        panelStyle(data);

        labelimie = new JLabel("Imie: " + user.getName());
        labelimie.setBounds(100,20,200,20);
        data.add(labelimie);
        textStyleBig(labelimie);

        JButton buttonName = new JButton("Zmień");
        buttonName.setBounds(350,20,80,20);
        data.add(buttonName);
        buttonStyle(buttonName);
        buttonName.addActionListener(new Actions.ButtonChangeFrame(labelimie.getText()));

        labelsurname = new JLabel("Nazwisko: " + user.getSurname());
        labelsurname.setBounds(100,50,200,20);
        data.add(labelsurname);
        textStyleBig(labelsurname);

        JButton buttonSurname= new JButton("Zmień");
        buttonSurname.setBounds(350,50,80,20);
        data.add(buttonSurname);
        buttonStyle(buttonSurname);
        buttonSurname.addActionListener(new Actions.ButtonChangeFrame(labelsurname.getText()));

        labelpesel = new JLabel("Pesel: " + user.getPesel());
        labelpesel.setBounds(100,80,200,20);
        data.add(labelpesel);
        textStyleBig(labelpesel);

        JButton buttonPesel = new JButton("Zmień");
        buttonPesel.setBounds(350,80,80,20);
        data.add(buttonPesel);
        buttonStyle(buttonPesel);
        buttonPesel.addActionListener(new Actions.ButtonChangeFrame(labelpesel.getText()));

        labelmail = new JLabel("Mail: " + user.getMail());
        labelmail.setBounds(100,110,300,20);
        data.add(labelmail);
        textStyleBig(labelmail);

        JButton buttonMail = new JButton("Zmień");
        buttonMail.setBounds(350,110,80,20);
        data.add(buttonMail);
        buttonStyle(buttonMail);
        buttonMail.addActionListener(new Actions.ButtonChangeFrame(labelmail.getText()));

        JButton buttonchangepassword = new JButton("Zmień hasło");
        buttonStyle(buttonchangepassword);
        buttonchangepassword.setBounds(100,140,100,20);
        buttonchangepassword.addActionListener(new Actions.ButtonChangePasswordFrame());
        data.add(buttonchangepassword);


        /*
        //
        //  Dodanie wszystkiego do jtabbedpane
        //
         */

        jtpbank = new JTabbedPane();
        jtpbank.addTab("Zrób przelew", transfer);
        jtpbank.addTab("Wpłać", payment);
        jtpbank.addTab("Giełda", stockexchange);
        jtpbank.addTab("Zmień dane", data);
        frame.add(jtpbank);

        frame.setVisible(true);
    }

    /*
    //
    // Frame do zmiany danych
    //
     */
    public static void PanelChange(String s){

        framchange = new JFrame("Zmień " + s);
        framchange.setBounds(600,300,250,200);
        framchange.setLayout(null);
        FrameIcon(framchange);

        panelchange = new JPanel();
        panelchange.setBounds(0,0,250,200);
        panelchange.setLayout(null);
        framchange.add(panelchange);
        panelStyle(panelchange);

        JLabel label = new JLabel("Podaj " + s);
        label.setBounds(50,10,100,20);
        panelchange.add(label);
        textStyle(label);

        textchange = new JTextField();
        textchange.setBounds(50,35,100,20);
        panelchange.add(textchange);

        JButton buttonChange = new JButton("Zmień");
        buttonChange.setBounds(50,80,80,20);
        panelchange.add(buttonChange);
        buttonStyle(buttonChange);
        buttonChange.addActionListener(new Actions.ButtonChange(s));

        framchange.setVisible(true);


    }

    public static JLabel getCompany( int i) {
        if (i == 1) {
            return company1;
        }
        if (i == 2){
            return company2;
        }
        if (i == 3 ){
            return company3;
        }
        if (i == 4){
            return company4;
        }return null;
    }

    public static void setCompany1(int i, JLabel company1) {

        if (i == 1){
            Bank.company1 = company1;
        }
        if (i == 2){
            Bank.company2 = company1;
        }
        if (i == 3){
            Bank.company3 = company1;
        }
        if (i == 4){
            Bank.company4 = company1;
        }
    }

    public static StockEchange getStockEchangedata() {
        return stockEchangedata;
    }

    public static JPanel getStockexchange() {
        return stockexchange;
    }

    public static void setStockexchange(JPanel stockexchange) {
        Bank.stockexchange = stockexchange;
    }

    public void setStockEchangedata(StockEchange stockEchangedata) {
        this.stockEchangedata = stockEchangedata;
    }

    public static Thread getThreadbalance() {
        return threadbalance;
    }

    public static void setThreadbalance(Thread threadbalance) {
        Bank.threadbalance = threadbalance;
    }

    public static Thread getThreadstate() {
        return threadstate;
    }

    public static void setThreadstate(Thread threadstate) {
        Bank.threadstate = threadstate;
    }

    public static DefaultTableModel getDefaultTableModel() {
        return defaultTableModelhistory;
    }

    public static void setDefaultTableModel(DefaultTableModel defaultTableModel) {
        Bank.defaultTableModelhistory = defaultTableModel;
    }

    public static String[][] getTransferdata() {
        return transferdata;
    }

    public void setTransferdata(String[][] transferdata) {
        this.transferdata = transferdata;
    }

    public static JLabel getLabelimie() {
        return labelimie;
    }

    public static void setLabelimie(JLabel labelimie) {
        Bank.labelimie = labelimie;
    }

    public static JLabel getLabelsurname() {
        return labelsurname;
    }

    public static void setLabelsurname(JLabel labelsurname) {
        Bank.labelsurname = labelsurname;
    }

    public static JLabel getLabelpesel() {
        return labelpesel;
    }

    public static void setLabelpesel(JLabel labelpesel) {
        Bank.labelpesel = labelpesel;
    }

    public static JLabel getLabelmail() {
        return labelmail;
    }

    public static void setLabelmail(JLabel labelmail) {
        Bank.labelmail = labelmail;
    }

    public static JPanel getPanelchange() {
        return panelchange;
    }

    public static void setPanelchange(JPanel panelchange) {
        Bank.panelchange = panelchange;
    }

    public static JTextField getTextchange() {
        return textchange;
    }

    public static void setTextchange(JTextField textchange) {
        Bank.textchange = textchange;
    }

    public static JFrame getFramchange() {
        return framchange;
    }

    public static void setFramchange(JFrame framchange) {
        Bank.framchange = framchange;
    }

    /*
    //
    // Frame zmiany hasła
    //
     */
    public static void PanelChangePassword(){

        framepassword = new JFrame("Zmień hasło");
        framepassword.setBounds(600,300,400,300);
        framepassword.setLayout(null);
        FrameIcon(framepassword);

        panelpassword = new JPanel();
        panelpassword.setBounds(0,0,400,300);
        panelpassword.setLayout(null);
        framepassword.add(panelpassword);
        panelStyle(panelpassword);

        JLabel labelactualpassword = new JLabel("Podaj aktualne hasło:");
        labelactualpassword.setBounds(100,30,150,20);
        panelpassword.add(labelactualpassword);
        textStyle(labelactualpassword);

        actualpassword = new JPasswordField();
        actualpassword.setBounds(100,55,100,20);
        panelpassword.add(actualpassword);

        JLabel labelnewpassword1 = new JLabel("Podaj nowe hasło:");
        labelnewpassword1.setBounds(100,80,150,20);
        panelpassword.add(labelnewpassword1);
        textStyle(labelnewpassword1);

        newpassword1 = new JPasswordField();
        newpassword1.setBounds(100,105,100,20);
        panelpassword.add(newpassword1);

        JLabel labelnewlassword2 = new JLabel("Powtórz nowe hasło:");
        labelnewlassword2.setBounds(100,130,150,20);
        panelpassword.add(labelnewlassword2);
        textStyle(labelnewlassword2);

        newpassword2 = new JPasswordField();
        newpassword2.setBounds(100,155,100,20);
        panelpassword.add(newpassword2);

        JButton buttonpassword = new JButton("Zmień hasło");
        buttonpassword.setBounds(100,200,100,20);
        panelpassword.add(buttonpassword);
        buttonStyle(buttonpassword);
        buttonpassword.addActionListener(new Actions.ButtonChangePassword());

    }

    public static JPanel getPanelpassword() {
        return panelpassword;
    }

    public static JPasswordField getActualpassword() {
        return actualpassword;
    }

    public static JPasswordField getNewpassword1() {
        return newpassword1;
    }

    public static JPasswordField getNewpassword2() {
        return newpassword2;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Bank.user = user;
    }

    public static JPanel getTransfer() {
        return transfer;
    }

    public static void setTransfer(JPanel transfer) {
        Bank.transfer = transfer;
    }

    public static String getStringActualpassword() {

        String password = new String(actualpassword.getPassword());
        return password;
    }

    public static void setActualpassword(JPasswordField actualpassword) {
        Bank.actualpassword = actualpassword;
    }

    public static String getStringNewpassword1() {

        String password = new String(newpassword1.getPassword());
        return password;
    }

    public static void setNewpassword1(JPasswordField newpassword1) {
        Bank.newpassword1 = newpassword1;
    }

    public static String getStringNewpassword2() {

        String password = new String(newpassword2.getPassword());
        return password;
    }

    public static void setNewpassword2(JPasswordField newpassword2) {
        Bank.newpassword2 = newpassword2;
    }

    public static JFrame getFramepassword() {
        return framepassword;
    }

    public static void setFramepassword(JFrame framepassword) {
        Bank.framepassword = framepassword;
    }

    public static JPanel getData() {
        return data;
    }

    public static void setData(JPanel data) {
        Bank.data = data;
    }

    public static String[] getColumnnames() {
        return columnnameshistory;
    }

    public static JTable getTransferhistory() {
        return transferhistory;
    }

    public static void setTransferhistory(JTable transferhistory) {
        Bank.transferhistory = transferhistory;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static void setFrame(JFrame frame) {
        Bank.frame = frame;
    }

    public static JLabel getAccountbalance() {
        return accountbalance;
    }

    public static void setAccountbalance(JLabel accountbalance) {
        Bank.accountbalance = accountbalance;
    }

    public static JTextField getTextpayment() {
        return textpayment;
    }

    public static void setTextpayment(JTextField textpayment) {
        Bank.textpayment = textpayment;
    }

    public static JTabbedPane getJtpbank() {
        return jtpbank;
    }

    public static void setJtpbank(JTabbedPane jtpbank) {
        Bank.jtpbank = jtpbank;
    }

    /*
    //
    //      Frame do przelewu
    //
     */
    public static void paymentMenu(){

        framepayment = new JFrame("Nowy przelew");
        framepayment.setBounds(600,300,400,300);
        framepayment.setLayout(null);
        framepayment.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        FrameIcon(framepayment);

        panelpayment = new JPanel();
        panelpayment.setBounds(0,0,400,300);
        panelpayment.setLayout(null);
        framepayment.add(panelpayment);
        panelStyle(panelpayment);


        JLabel title = new JLabel("Podaj tytuł:");
        title.setBounds(50,10, 100,20);
        panelpayment.add(title);
        textStyle(title);

        texttitle = new JTextField();
        texttitle.setBounds(50,30,200,20);
        panelpayment.add(texttitle);


        JLabel accountnumb = new JLabel("Podaj numer konta (Bez spacji):");
        accountnumb.setBounds(50,60,200,20);
        panelpayment.add(accountnumb);
        textStyle(accountnumb);

        textaccountnumb = new JTextField();
        textaccountnumb.setBounds(50,80,200,20);
        panelpayment.add(textaccountnumb);


        JLabel amount = new JLabel("Podaj kwotę:");
        amount.setBounds(50,110,100,20);
        panelpayment.add(amount);
        textStyle(amount);

        textamount = new JTextField();
        textamount.setBounds(50,130,200,20);
        panelpayment.add(textamount);

        JButton buttonnewpayment = new JButton("Wykonaj");
        buttonnewpayment.setBounds(100,200,80,25);
        buttonnewpayment.addActionListener(new Actions.ButtonNewPayment(user));
        panelpayment.add(buttonnewpayment);
        buttonStyle(buttonnewpayment);

        framepayment.setVisible(true);

    }
    public static JFrame getFramepayment() {
        return framepayment;
    }

    public static void setFramepayment(JFrame framepayment) {
        Bank.framepayment = framepayment;
    }

    public static JPanel getPanelpayment() {
        return panelpayment;
    }

    public static void setPanelpayment(JPanel panelpayment) {
        Bank.panelpayment = panelpayment;
    }

    public static JTextField getTexttitle() {
        return texttitle;
    }

    public static void setTexttitle(JTextField texttitle) {
        Bank.texttitle = texttitle;
    }

    public static JTextField getTextaccountnumb() {
        return textaccountnumb;
    }

    public static void setTextaccountnumb(JTextField textaccountnumb) {
        Bank.textaccountnumb = textaccountnumb;
    }

    public static JTextField getTextamount() {
        return textamount;
    }

    public static void setTextamount(JTextField textamount) {
        Bank.textamount = textamount;
    }


    /*
    //
    //      Aktualizacja historii transakcji
    //
     */

    @Override
    public void update(String [] strings) {

        defaultTableModelhistory.insertRow(0, (strings));
        transferhistory.updateUI();
    }
}
