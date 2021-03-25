package Package_GUI.Styles;

import javax.swing.*;
import java.awt.*;

public class Style {

    public static void buttonStyle(JButton button){

        button.setFont(new Font("Arial", Font.BOLD, 13));
        Color colorbackground = new Color(47,53,235);
        button.setBackground(colorbackground);
        button.setForeground(Color.WHITE);

        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
    }
    public static void textStyle(JLabel label){

        label.setFont(new Font("Arial", Font.BOLD,11));
        label.setForeground(Color.WHITE);
    }
    public static void textStyleBig(JLabel label){

        label.setFont(new Font("Arial", Font.BOLD,14));
        label.setForeground(Color.WHITE);
    }
    public static void panelStyle(JPanel panel){

        Color background = new Color(81, 81, 81);
        panel.setBackground(background);
    }
    public static void panelStyle2(JPanel panel){

        Color background = new Color(138,138,138);
        panel.setBackground(background);
    }
    public static void FrameIcon(JFrame frame){

        Image iconstart = new ImageIcon("icon.png").getImage();
        frame.setIconImage(iconstart);
    }
}
