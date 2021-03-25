package Package_GUI.Start;

import Package_GUI.Actions.Actions;

import javax.swing.*;
import static Package_GUI.Styles.Style.*;

public class GUIstart extends JFrame {


    private static JFrame framestart;
    private static JPanel panellogin;
    private JButton buttonlogin;
    private JButton buttonregister;
    private static JTextField textlogin;
    private static JPasswordField passwordField;
    private static JPanel panelbackground;

    public GUIstart() {

        framestart = new JFrame("Logowanie");
        framestart.setBounds(600, 300, 600, 400);
        framestart.setDefaultCloseOperation(EXIT_ON_CLOSE);
        FrameIcon(framestart);


        panellogin = new JPanel();
        panellogin.setBounds(0, 0, 190, 400);
        panellogin.setLayout(null);
        framestart.add(panellogin);
        panelStyle(panellogin);

        JLabel labellogin = new JLabel("Podaj login: ");
        labellogin.setBounds(50, 50, 100, 20);
        panellogin.add(labellogin);
        textStyle(labellogin);

        textlogin = new JTextField();
        textlogin.setBounds(50, 70, 100, 20);
        panellogin.add(textlogin);

        JLabel labelpassword = new JLabel("Podaj hasło: ");
        labelpassword.setBounds(50, 120, 100, 20);
        panellogin.add(labelpassword);
        textStyle(labelpassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 140, 100, 20);
        panellogin.add(passwordField);


        buttonlogin = new JButton("Zaloguj");
        buttonlogin.setBounds(50, 200, 100, 30);
        buttonlogin.addActionListener(new Actions.ButtonLogin(panellogin, textlogin, passwordField));
        panellogin.add(buttonlogin);
        buttonStyle(buttonlogin);


        buttonregister = new JButton("Zarejestruj");
        buttonregister.setBounds(50, 250, 100, 30);
        panellogin.add(buttonregister);
        buttonStyle(buttonregister);

        panelbackground = new JPanel();
        panelbackground.setBounds(190,0,400,400);
        panelbackground.setLayout(null);
        framestart.add(panelbackground);
        panelStyle2(panelbackground);


        framestart.setVisible(true);

        buttonregister.addActionListener(new Actions.ButtonRegisterStart(panellogin,  null));

    }

    public static JPanel getPanelbackground() {
        return panelbackground;
    }

    public static void setPanelbackground(JPanel panelbackground) {
        GUIstart.panelbackground = panelbackground;
    }

    public static JFrame getFramestart() {
        return framestart;
    }

    public static void setFramestart(JFrame framestart) {
        GUIstart.framestart = framestart;
    }

    public static JPanel getPanellogin() {
        return panellogin;
    }

    public void setPanellogin(JPanel panellogin) {
        this.panellogin = panellogin;
    }

    public static String getPasswordField() {
        String password = new String(passwordField.getPassword());
        return password;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public static String getTextlogin() {
        return textlogin.getText();
    }

    public void setTextlogin(JTextField textlogin) {
        this.textlogin = textlogin;
    }
}