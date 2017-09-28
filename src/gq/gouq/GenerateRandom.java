package gq.gouq;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Created by Mega
 * Intellij IDEA
 */
public class GenerateRandom extends JFrame {
    private JCheckBox lowerCaseLettersCheckBox;
    private JCheckBox capitalLettersCheckBox;
    private JCheckBox digitsCheckBox;
    private JCheckBox punctuationsCheckBox;
    private JButton okButton;
    private JButton generateButton;
    private JTextField textField1;
    private JTextArea textArea1;
    private JPanel panel1;
    private JSpinner spinner1;

    private Gui gui;

    public GenerateRandom(Gui gui){
        super("Gouq.gq Saveword - Random generator");

        this.gui = gui;

        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                gui.setEnabled(true);
            }
        };
        addWindowListener(exitListener);


        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Random rnd = new Random();

                int length = (int) spinner1.getValue();
                String characters = textArea1.getText();
                String password = "";

                if(lowerCaseLettersCheckBox.isSelected())
                    characters += "abcdefghijklmnopqrstuvwxyz";

                if (capitalLettersCheckBox.isSelected())
                    characters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

                if (digitsCheckBox.isSelected())
                    characters += "0123456789";

                if(punctuationsCheckBox.isSelected())
                    characters += "!@#$%&*()_+-=[]|,.;:/?><";

                for (int i = 0; i < length; i++)
                    password += characters.toCharArray()[rnd.nextInt(characters.length())];

                textField1.setText(password);
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.newPassword.setText(textField1.getText());
                gui.closeRandomGui();
            }
        });


        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        setLocation(gd.getDisplayMode().getWidth() / 2 - getWidth() / 2, gd.getDisplayMode().getHeight() / 2 - getHeight() / 2);

        setDefaultLookAndFeelDecorated(true);
        setResizable(true);

        setVisible(true);

        pack();
    }

    private void createUIComponents() {
        SpinnerNumberModel model = new SpinnerNumberModel(6, 2, 200, 1);
        spinner1 = new JSpinner(model);
    }

}
