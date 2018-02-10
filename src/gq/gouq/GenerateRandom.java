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
    private JSpinner spinner_lower_case;
    private JSpinner spinner_capital;
    private JSpinner spinner_digits;
    private JSpinner spinner_punctuations;

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
                    for (int i = 0; i < (int)spinner_lower_case.getValue(); i++)
                        characters += "abcdefghijklmnopqrstuvwxyz";

                if (capitalLettersCheckBox.isSelected())
                    for (int i = 0; i < (int)spinner_capital.getValue(); i++)
                        characters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

                if (digitsCheckBox.isSelected())
                    for (int i = 0; i < (int)spinner_digits.getValue(); i++)
                        characters += "0123456789";

                if(punctuationsCheckBox.isSelected())
                    for (int i = 0; i < (int)spinner_punctuations.getValue(); i++)
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

        SpinnerNumberModel model1 = new SpinnerNumberModel(1, 1, 25, 1);
        spinner_lower_case = new JSpinner(model1);
        SpinnerNumberModel model2 = new SpinnerNumberModel(1, 1, 25, 1);
        spinner_capital = new JSpinner(model2);
        SpinnerNumberModel model3 = new SpinnerNumberModel(1, 1, 25, 1);
        spinner_digits = new JSpinner(model3);
        SpinnerNumberModel model4 = new SpinnerNumberModel(1, 1, 25, 1);
        spinner_punctuations = new JSpinner(model4);
    }

}
