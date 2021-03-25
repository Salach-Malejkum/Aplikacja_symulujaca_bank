package Package_GUI.Register;

import Package_GUI.Actions.Actions;

import javax.swing.*;

import static Package_GUI.Start.GUIstart.getFramestart;
import static Package_GUI.Start.GUIstart.getPanellogin;
import static Package_GUI.Styles.Style.*;

public class Register extends JFrame{

    private static JPanel panelregister;
    private static JTextField textloginin;
    private static JTextField textname;
    private static JTextField textsurname;
    private static JTextField textpesel;
    private static JTextField textmail;
    private static JPasswordField passwordreg;
    private static JPasswordField passwordreg2;
    private static JButton buttontoregister;
    public Register() {


        /*
        //
        //      Panel który wyswietla się przy rejestracji
        //
         */
        panelregister = new JPanel();
        panelregister.setBounds(600,300,600,400);
        panelregister.setLayout(null);
        panelregister.setVisible(false);
        getFramestart().add(panelregister);
        panelStyle(panelregister);


    JLabel loginin = new JLabel("Podaj login:");
        loginin.setBounds(50,10,100,20);
        panelregister.add(loginin);
        textStyle(loginin);

    textloginin = new JTextField();
        textloginin.setBounds(50,30,100,20);
        panelregister.add(textloginin);



    JLabel name = new JLabel("Podaj imię:");
        name.setBounds(250,10,100,20);
        panelregister.add(name);
        textStyle(name);


    textname = new JTextField();
        textname.setBounds(250,30,100,20);
        panelregister.add(textname);


    JLabel surname = new JLabel("Podaj nazwisko:");
        surname.setBounds(50,60,100,20);
        panelregister.add(surname);
        textStyle(surname);

    textsurname = new JTextField();
        textsurname.setBounds(50,80,100,20);
        panelregister.add(textsurname);


    JLabel pesel = new JLabel("Podaj pesel:");
        pesel.setBounds(250,60,100,20);
        panelregister.add(pesel);
        textStyle(pesel);

    textpesel = new JTextField();
        textpesel.setBounds(250,80,100,20);
        panelregister.add(textpesel);


    JLabel mail = new JLabel("Podaj adres e-mail:");
        mail.setBounds(50,110,150,20);
        panelregister.add(mail);
        textStyle(mail);

    textmail = new JTextField();
        textmail.setBounds(50,130,150,20);
        panelregister.add(textmail);


    JLabel haslo = new JLabel("Utwórz hasło (przynajmniej 8 liter bądź cyfr):");
        haslo.setBounds(250,110,250,20);
        panelregister.add(haslo);
        textStyle(haslo);

    passwordreg = new JPasswordField();
        passwordreg.setBounds(250,130,100,20);
        panelregister.add(passwordreg);


    JLabel haslo2 = new JLabel("Powtórz hasło:");
        haslo2.setBounds(259,160,100,20);
        panelregister.add(haslo2);
        textStyle(haslo2);

    passwordreg2 = new JPasswordField();
        passwordreg2.setBounds(250,180,100,20);
        panelregister.add(passwordreg2);

        buttontoregister = new JButton("Zarejestruj");
        buttontoregister.setBounds(200,300,100,30);
        panelregister.add(buttontoregister);
        buttontoregister.addActionListener(new Actions.toRegister(panelregister, getPanellogin(), textloginin, textname, textsurname, textpesel, textmail, passwordreg, passwordreg2));
        buttonStyle(buttontoregister);

    JButton cancel = new JButton("Anuluj");
        cancel.setBounds(350,300,100,30);
        cancel.addActionListener(new Actions.ButtonCancel(panelregister, getPanellogin()));
        panelregister.add(cancel);
        buttonStyle(cancel);
}

    public static JButton getButtontoregister() {
        return buttontoregister;
    }

    public static void setButtontoregister(JButton buttontoregister) {
        Register.buttontoregister = buttontoregister;
    }

    public static JPanel getPanelregister() {
        return panelregister;
    }

    public void setPanelregister(JPanel panelregister) {
        this.panelregister = panelregister;
    }

    public static String getTextname() {
        return textname.getText();
    }

    public void setTextname(JTextField textname) {
        this.textname = textname;
    }


    /*
    //
    //  Metoda sprawdzajaca poprawnośc podanych haseł
    //
     */
    public static boolean getPasswordreg(JPasswordField passwordreg, JPasswordField passwordreg2) {
        char [] password = passwordreg.getPassword();
        char [] password2 = passwordreg2.getPassword();
        if (!(password.length == password2.length)) return false;
        else {
            for (int i=0; i<password.length; i++){
                if (password[i] != password2[i]) return false;
            }return true;
        }
    }

    public static String getTextloginin() {
        return textloginin.getText();
    }

    public static void setTextloginin(JTextField textloginin) {
        Register.textloginin = textloginin;
    }

    public void setPasswordreg(JPasswordField passwordreg) {
        this.passwordreg = passwordreg;
    }

    public void setPasswordreg2(JPasswordField passwordreg2) {
        this.passwordreg2 = passwordreg2;
    }

    public static String getTextsurname() {
        return textsurname.getText();
    }

    public void setTextsurname(JTextField textsurname) {
        this.textsurname = textsurname;
    }

    public static String getTextpesel() {
        return textpesel.getText();
    }

    public void setTextpesel(JTextField textpesel) {
        this.textpesel = textpesel;
    }

    public static String getTextmail() {
        return textmail.getText();
    }

    public void setTextmail(JTextField textmail) {
        this.textmail = textmail;
    }
}

