package Package_GUI.Actions;

import Package_GUI.Bank.Bank;
import Package_GUI.Register.Register;
import Package_GUI.Start.GUIstart;
import Package_User.Subject;
import Package_User.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static Package_GUI.Bank.Bank.*;
import static Package_GUI.Register.Register.*;
import static Package_GUI.Start.GUIstart.*;

public interface Actions {

    /*
    //
    // Actionlistener odpalajacy Panel rejestracji
    //
     */
    class ButtonRegisterStart implements ActionListener, Actions {

        private JPanel panel;
        private JPanel panel2;

        public ButtonRegisterStart(JPanel panel, JPanel panel2) {

            this.panel = panel;
        }


        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            new Register();
            panel2 = getPanelregister();
            panel.setVisible(false);
            getPanelbackground().setVisible(false);
            panel2.setVisible(true);
        }
    }

    /*
    //
    //  ActionListener odpowiedzialny za przycisk anuluj
    //
     */
    class ButtonCancel implements ActionListener, Actions {

        private JPanel panel;
        private JPanel panel2;

        public ButtonCancel(JPanel panel, JPanel panel2) {

            this.panel = panel;
            this.panel2 = panel2;

        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            panel.setVisible(false);
            panel2.setVisible(true);
        }
    }

    /*
    //
    //      ActionListener odpowiadajacy za rejestrację użytkownika
    //
     */
    class toRegister extends Component implements ActionListener, Serializable, Actions {

        private static final long serialVersionUID = 5497802233801340672L;
        private JPanel panelregister;
        private JPanel panellogin;
        private JTextField textlogin;
        private JTextField textname;
        private JTextField textsurname;
        private JTextField textpesel;
        private JTextField textmail;
        private JPasswordField passwordreg1;
        private JPasswordField passwordreg2;


        public toRegister(JPanel panel, JPanel panellogin, JTextField textlogin, JTextField textname, JTextField textsurname, JTextField textpesel, JTextField textmail, JPasswordField passwordreg1, JPasswordField passwordreg2) {

            panelregister = panel;
            this.panellogin = panellogin;
            this.textlogin = textlogin;
            this.textname = textname;
            this.textsurname = textsurname;
            this.textpesel = textpesel;
            this.textmail = textmail;
            this.passwordreg1 = passwordreg1;
            this.passwordreg2 = passwordreg2;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String login = getTextloginin().toString();
            String name = getTextname().toString();
            String surname = getTextsurname().toString();
            String pesel = getTextpesel().toString();
            String mail = getTextmail().toString();
            JTextField[] textFields = {textlogin, textname, textsurname, textmail, textpesel};

            String password = new String(passwordreg1.getPassword());

            Pattern namesurname = Pattern.compile("[A-Z].+");
            Matcher matchername = namesurname.matcher(name);
            Matcher matchersurname = namesurname.matcher(surname);

            Pattern passwordpattern = Pattern.compile("(\\w){8,}");
            Matcher matcherpassword = passwordpattern.matcher(password);

            Pattern peselpattern = Pattern.compile("(\\d{11})");
            Matcher matcherpesel = peselpattern.matcher(pesel);

            Pattern mailpattern = Pattern.compile(".+@.+\\..+");
            Matcher matchermail = mailpattern.matcher(mail);

            if (!textlogin.getText().equals("") && matchername.matches() && matchersurname.matches() && matcherpesel.matches() && matcherpassword.matches() &&
                    getPasswordreg(passwordreg1, passwordreg2) && matchermail.matches()) {
                User user = new User(login, name, surname, pesel, mail, password);
                saveUser(user);
                panelregister.setVisible(false);
                panellogin.setVisible(true);

            } else {
                for (int i = 0; i < textFields.length; i++) {
                    textFields[i].setText("");
                }

                passwordreg1.setText("");
                passwordreg2.setText("");
                JOptionPane.showMessageDialog(getPanelregister(), "Nie poprawne dane!");
            }
        }
    }

    /*
    //
    //      ActionListener odpowiadający logowaniu i otwarciu aplikacji banku
    //
     */
    class ButtonLogin implements ActionListener, Actions {

        private JPanel loginpanel;
        private JTextField textlogin;
        private JPasswordField passwordField;

        public ButtonLogin(JPanel loginpanel, JTextField textlogin, JPasswordField passwordField) {

            this.loginpanel = loginpanel;
            this.textlogin = textlogin;
            this.passwordField = passwordField;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String login = getTextlogin();
            String password = getPasswordField();


            try {
                if (!login.equals("") && !password.equals("") && login.equals(getUser(login).getLogin())) {
                    if (password.equals(getUser(login).getPassword())) {
                        getFramestart().dispose();
                        User user = getUser(getTextlogin());
                        user.removeObserver();
                        new Bank(user);
                        getThreadbalance().start();
                        getThreadstate().start();


                    } else {
                        textlogin.setText("");
                        passwordField.setText("");
                        JOptionPane.showMessageDialog(getPanellogin(), "Nie poprawne dane logowania!");
                    }
                }
                else {
                    textlogin.setText("");
                    passwordField.setText("");
                    JOptionPane.showMessageDialog(getPanellogin(), "Nie poprawne dane logowania!");
                }
            } catch (NullPointerException e) {
                if (loginpanel.isVisible()) {
                    textlogin.setText("");
                    passwordField.setText("");
                    JOptionPane.showMessageDialog(getPanellogin(), "Nie poprawne dane logowania!");
                } else ;
            }
        }
    }

    /*
    //
    //      ActionLitener odpalający menu wykonania przelewu
    //
     */
    class newPayment implements ActionListener, Actions {


        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            paymentMenu();
        }
    }

    /*
    //
    //      ActionListener wykonujący przelew
    //
     */
    class ButtonNewPayment implements ActionListener, Actions {

        private User user;

        public ButtonNewPayment(User user) {

            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) throws NumberFormatException {
            String title = getTexttitle().getText();
            String accountnumb = getTextaccountnumb().getText();
            String amounttext = "-" + getTextamount().getText();
            String amountfinal;
            if (amounttext.equals("-"))
                amountfinal = "0.00";
            else {
                amountfinal = amounttext.replace(',', '.');
            }
            BigDecimal amount =new BigDecimal(amountfinal);

            Pattern patternaccountnumb = Pattern.compile("\\d{26}");
            Matcher matcheraccountnumb = patternaccountnumb.matcher(accountnumb);

            Pattern patternamount = Pattern.compile("\\d*+.+\\d{2}");
            Matcher matcheramount = patternamount.matcher(amountfinal);

            if (matcheraccountnumb.matches() && matcheramount.matches() && !title.equals("") && amount.doubleValue() !=0.00) {

                if (user.getAccountbalance().doubleValue() > amount.doubleValue()*-1) {
                    user.newPayment(accountnumb, title, amount);
                    getFramepayment().dispose();
                    saveUser(user);
                    new updateAccountState(user);
                    ////////////////////
                }else {

                    getTexttitle().setText("");
                    getTextaccountnumb().setText("");
                    getTextamount().setText("");
                    JOptionPane.showMessageDialog(getPanelpayment(), "Niewystarczająca ilośc środków na konice!");
                }

            } else if (!matcheraccountnumb.matches()) {

                getTexttitle().setText("");
                getTextaccountnumb().setText("");
                getTextamount().setText("");
                JOptionPane.showMessageDialog(getPanelpayment(), "Niepoprawny numer konta!");
            } else if (!matcheramount.matches() || amount.doubleValue() == 0) {

                getTexttitle().setText("");
                getTextaccountnumb().setText("");
                getTextamount().setText("");
                JOptionPane.showMessageDialog(getPanelpayment(), "Niepoprawna kwota!");
            } else if (!title.equals("")) {

                getTexttitle().setText("");
                getTextaccountnumb().setText("");
                getTextamount().setText("");
                JOptionPane.showMessageDialog(getPanelpayment(), "Niepoprawny tytuł!");
            }
        }
    }

    /*
    //
    //      ActionListener odpowiadający odpaleniu deserializacji i wylogowaniu
    //
     */
    class ButtonLogout implements ActionListener, Actions{

        private User user;
        private JFrame frame;

        public ButtonLogout(User user,JFrame frame, JTabbedPane jTabbedPane){

            this.user = user;
            this.frame = frame;
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            saveUser(user);
            frame.dispose();
            new GUIstart();
            Bank.getStockEchangedata().saveStockExchange();
        }
    }

    class ButtonBlik implements ActionListener, Actions{


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(getPanelpayment(), "Sorry, work in progres!");
        }
    }

    /*
    //
    //      ActionListener odpowiedzialny za dodanie pieniedzy
    //
     */
    class ButtonAddMoney implements ActionListener, Actions{


        private User user;

        public ButtonAddMoney(User user){

            this.user = user;
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String amount;
            if (getTextpayment().getText().equals("")) {

                JOptionPane.showMessageDialog(getPanelpayment(), "Niepoprawna kwota!");
                amount = "0.00";
            }else {
                String s = getTextpayment().getText();
                amount = s.replace(',', '.');
            }

            Pattern patternamount = Pattern.compile("\\d*+.+\\d{2}");
            Matcher matcheramount = patternamount.matcher(amount);

            if (matcheramount.matches() && !amount.equals("0.00")) {
                user.AddMoney(amount);
                JOptionPane.showMessageDialog(getPanelpayment(), "Wpłaciłeś " + getTextpayment().getText() + " na konto!");
                new updateAccountState(user);
                getTextpayment().setText("");
                saveUser(user);
                //////

            }else {

                getTextpayment().setText("");
                JOptionPane.showMessageDialog(getPanelpayment(), "Niepoprawna kwota!");
            }
        }
    }

    /*
    //
    //      ActionListener odpalający menu zmiany hasła
    //
     */
    class ButtonChangePasswordFrame implements ActionListener, Actions{


        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            PanelChangePassword();
            getFramepassword().setVisible(true);


        }
    }

    /*
    //
    //      ActionListener zmieniający hasło
    //
     */
    class ButtonChangePassword implements ActionListener, Actions{


        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String actualpassword = getStringActualpassword();
            String newpassword1 = getStringNewpassword1();
            String newpassword2 = getStringNewpassword2();
            Pattern passwordpattern = Pattern.compile("(\\w){8,}");
            Matcher matcherpassword = passwordpattern.matcher(newpassword2);

            if (actualpassword.equals(Bank.getUser().getPassword()) || !actualpassword.equals("") || !newpassword1.equals("") || !newpassword2.equals("")){
                if (newpassword1.equals(newpassword2) && matcherpassword.matches()){

                    Bank.getUser().setPassword(newpassword1);
                    JOptionPane.showMessageDialog(getPanelpassword(), "Hasło pomyślnie zmienione!");
                    getFramepassword().dispose();
                }else {
                    getActualpassword().setText("");
                    getNewpassword1().setText("");
                    getNewpassword2().setText("");
                    JOptionPane.showMessageDialog(getPanelpassword(), "Hasła się nie zgadzają!");
                }

            }else {

                getActualpassword().setText("");
                getNewpassword1().setText("");
                getNewpassword2().setText("");
                JOptionPane.showMessageDialog(getPanelpassword(), "Nie poprawne hasło!");
            }
        }
    }

    /*
    //
    // ActionListener odpowiadajacy wyświetleniu menu zmiany danej
    //
     */
    class ButtonChangeFrame implements ActionListener, Actions{

        private String s;

        public ButtonChangeFrame(String s){

            this.s = s;
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            StringBuilder builder = new StringBuilder();
            builder.append(s);
            builder.delete(builder.indexOf(":"), builder.length());

            String change = builder.toString();
            Bank.PanelChange(change);
        }
    }

    /*
    //
    //      ActionListener zmieniajacy daną daną u użytkownika
    //
     */
    class ButtonChange implements ActionListener, Actions{

        private String s;


        public ButtonChange(String s){

            this.s = s;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String text =getTextchange().getText();

            Pattern namesurname = Pattern.compile("[A-Z].+");
            Matcher matchername = namesurname.matcher(text);
            Matcher matchersurname = namesurname.matcher(text);

            Pattern peselpattern = Pattern.compile("(\\d{11})");
            Matcher matcherpesel = peselpattern.matcher(text);

            Pattern mailpattern = Pattern.compile(".+@.+\\..+");
            Matcher matchermail = mailpattern.matcher(text);

            if (s.equals("Imie") && matchername.matches()){
                Bank.getUser().setName(text);
                new updateUserData(Bank.getUser(), s);
                saveUser(Bank.getUser());
                Bank.getFramchange().dispose();
            }else if (s.equals("Imie")){

                getTextchange().setText("");
                JOptionPane.showMessageDialog(getPanelchange(), "Niepoprawne imie!");
            }
            if (s.equals("Nazwisko") && matchersurname.matches()){
                Bank.getUser().setSurname(text);
                new updateUserData(Bank.getUser(), s);
                saveUser(Bank.getUser());
                Bank.getFramchange().dispose();
            }else if (s.equals("Nazwisko")){

                getTextchange().setText("");
                JOptionPane.showMessageDialog(getPanelchange(), "Niepoprawne nazwisko!");
            }
            if (s.equals("Pesel") && matcherpesel.matches()){
                Bank.getUser().setPesel(text);
                new updateUserData(Bank.getUser(), s);
                saveUser(Bank.getUser());
                Bank.getFramchange().dispose();
            }else if (s.equals("Pesel")){

                getTextchange().setText("");
                JOptionPane.showMessageDialog(getPanelchange(), "Niepoprawny pesel!");
            }
            if (s.equals("Mail") && matchermail.matches()){
                Bank.getUser().setMail(text);
                new updateUserData(Bank.getUser(), s);
                saveUser(Bank.getUser());
                Bank.getFramchange().dispose();
            }else if (s.equals("Mail")){

                getTextchange().setText("");
                JOptionPane.showMessageDialog(getPanelchange(), "Niepoprawny mail!");
            }

        }
    }
    class ButtonBuyAction implements ActionListener, Actions{

        private int i;

        public ButtonBuyAction(int i){

            this.i = i-1;
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (Bank.getUser().getAccountbalance().compareTo(Bank.getStockEchangedata().getCompanyArrayList().get(i).getValue()) == 1) {
                Bank.getUser().buyAction(i, Bank.getStockEchangedata().getCompanyArrayList().get(i).getValue());
                new updateAccountState(Bank.getUser());
                saveUser(Bank.getUser());
            }
            else
                JOptionPane.showMessageDialog(Bank.getStockexchange(), "Za mało środków na koncie!");
        }
    }
    public static class ButtonSellAction implements ActionListener, Actions{

        private int i;

        public ButtonSellAction(int i){

            this.i = i-1;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (Bank.getUser().getOwned(i) > 0){

                Bank.getUser().sellAction(i, Bank.getStockEchangedata().getCompanyArrayList().get(i).getValue());
                new updateAccountState(Bank.getUser());
                saveUser(Bank.getUser());
            }else
                JOptionPane.showMessageDialog(Bank.getStockexchange(), "Nie posiadasz akcji tej firmy!");
        }
    }

    /*
    //
    //          Klasa zmieniająca daną w banku
    //
     */
    class updateUserData {

        public updateUserData(User user, String s){

            if (s.equals("Imie")){
                Bank.getLabelimie().setText("Imie: " + user.getName());
            }
            if (s.equals("Nazwisko")){
                Bank.getLabelsurname().setText("Nazwisko: " + user.getSurname());
            }
            if (s.equals("Pesel")){
                Bank.getLabelpesel().setText("Pesel: " + user.getPesel());
            }
            if (s.equals("Mail")){
                Bank.getLabelmail().setText("Mail: " + user.getMail());
            }

        }
    }

    /*
    //
    //      Klasa uaktualniająca stan konta
    //
     */
    class updateAccountState extends Thread {

        public updateAccountState(User user){

                getAccountbalance().setText("Stan konta: " + user.getAccountbalance().toString() + " zł");
        }
    }

    /*
    //
    //      Serializacja
    //
     */
    static void saveUser(User user) {

        try {

            FileOutputStream fos = new FileOutputStream("C:\\Users\\mateu\\OneDrive\\Dokumenty\\Projects\\PWRbank\\users\\" + user.getLogin() + ".ser");
            ObjectOutputStream so = new ObjectOutputStream(fos);

            so.writeObject(user);

            fos.close();
            so.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    //
    //      Deserializacja
    //
     */
    static User getUser(String login) {

        try (FileInputStream fis = new FileInputStream("C:\\Users\\mateu\\OneDrive\\Dokumenty\\Projects\\PWRbank\\users\\" + login + ".ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            return (User) ois.readObject();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}



