package gq.gouq;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by Mega
 * Intellij IDEA
 */
public class Gui extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel panel1;

    private JTextArea showPassword;
    private JButton deletePassword;
    private JTextField newUsage;
    public JTextArea newPassword;
    private JButton selectExportFile;
    private JButton startExportButton;
    private JComboBox showUsage;
    private JButton addPassword;
    private JButton selectImportFile;
    private JTextField exportText;
    private JTextField importText;
    private JButton startImportButton;
    private JButton generateRandomPassword;
    private JButton sourceforgeSiteButton;
    private JButton sourceCodeButton;
    private JButton donateButton;
    private JTextArea thisFreeSoftwareIsTextArea;

    private KeyedTable passwords = new KeyedTable();

    private GenerateRandom randomPassword;

    public TimerTask save_passwords;

    Gui(){
        super("Gouq.gq Serializer");
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/savewords.gpw"))) {
            passwords = (KeyedTable) in.readObject();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        save_passwords = new TimerTask() {
            @Override
            public void run() {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/savewords.gpw"))) {
                    out.writeObject(passwords);
                    out.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };
        Timer saving_timer = new Timer();
        saving_timer.scheduleAtFixedRate(save_passwords, 1800, 1800);
        

        addPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newUsage.getText().equals("") || newUsage.getText().equals(null)){
                    JOptionPane.showMessageDialog(null, "You have to enter an usage.", "Gouq.gq Saveword", JOptionPane.WARNING_MESSAGE);
                } else if (newPassword.getText().equals("") || newPassword.getText().equals(null)){
                    JOptionPane.showMessageDialog(null, "You have to enter a password.", "Gouq.gq Saveword", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (passwords.getData(newUsage.getText()) != null) {
                        int ans = JOptionPane.showConfirmDialog(null, "This usage (" + newUsage.getText() + ") is already assigned.\nDo you want to overide it?", "Gouq.gq Saveword", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (ans != JOptionPane.YES_OPTION)
                            return;
                    }

                    passwords.setData(newUsage.getText(), newPassword.getText());
                    updateShowUsageModel();

                    newPassword.setText("");
                    newUsage.setText("");
                    JOptionPane.showMessageDialog(null, "Successful", "Gouq.gq Saveword", JOptionPane.INFORMATION_MESSAGE);
                    
                    save_passwords.run();
                }
            }
        });

        deletePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwords.getData((String)showUsage.getSelectedItem()) != null){
                    int ans = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the password for \""+(String) showUsage.getSelectedItem() + "\"?", "Gouq.gq Saveword", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (ans == JOptionPane.YES_OPTION)
                        passwords.removeData((String)showUsage.getSelectedItem());
                    updateShowUsageModel();
                }
                save_passwords.run();
            }
        });

        selectExportFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();

                fc.setCurrentDirectory(new File("."));
                fc.setDialogTitle("Gouq.gq Saveword");
                fc.setFileFilter(new FileNameExtensionFilter(".gpw", "gpw"));
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.showOpenDialog(null);

                exportText.setText(fc.getSelectedFile().getPath());
                if (!exportText.getText().substring(exportText.getText().length() - 4, exportText.getText().length()).equals(".gpw"))
                    exportText.setText(exportText.getText() + ".gpw");
            }
        });

        startExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(exportText.getText()))) {
                    out.writeObject(passwords);
                    out.close();
                    JOptionPane.showMessageDialog(null, "Successful", "Gouq.gq Saveword", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showConfirmDialog(null, "Illegal file!", "Gouq.gq Saveword", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        startImportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(importText.getText()))) {
                    KeyedTable table = (KeyedTable) in.readObject();
                    for (int i = 0; i < table.getSize() ; i++) {
                        boolean set = true;
                        if (passwords.hasData(table.getKeyAt(i)))
                            set = JOptionPane.showConfirmDialog(null, "The password for \"" + table.getKeyAt(i) + "\" already exists.\nDo you want to overide it?", "Gouq.gq Saveword", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                                    == JOptionPane.YES_OPTION;
                        if(set) passwords.setData(table.getKeyAt(i), table.getDataAt(i));
                    }
                    in.close();
                    JOptionPane.showMessageDialog(null, "Successful", "Gouq.gq Saveword", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showConfirmDialog(null, "Illegal file!", "Gouq.gq Saveword", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                }
                updateShowUsageModel();
                save_passwords.run();
            }
        });


        selectImportFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();

                fc.setCurrentDirectory(new File("."));
                fc.setDialogTitle("Gouq.gq Saveword");
                fc.setFileFilter(new FileNameExtensionFilter(".gpw", "gpw"));
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.showOpenDialog(null);

                importText.setText(fc.getSelectedFile().getPath());
                if (!importText.getText().substring(importText.getText().length() - 4, importText.getText().length()).equals(".gpw"))
                    importText.setText(importText.getText() + ".gpw");
            }
        });

        generateRandomPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomPassword = new GenerateRandom(Main.gui);
                setEnabled(false);
            }
        });


        donateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Desktop.isDesktopSupported())
                {
                    try {
                        Desktop.getDesktop().browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=G47UDT9NVW3EW"));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        sourceforgeSiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Desktop.isDesktopSupported())
                {
                    try {
                        Desktop.getDesktop().browse(new URI("https://sourceforge.net/u/gouqgq/profile/"));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        sourceCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Desktop.isDesktopSupported())
                {
                    try {
                        Desktop.getDesktop().browse(new URI("https://github.com/GouqGq/ClickItAuto/"));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        thisFreeSoftwareIsTextArea.setText("This free software is brought to you by Gouq.gq, please consider donating to us by clicking the button down below. Alwas think of the license which was enclosed to this software (LICENSE.txt)");


        updateShowUsageModel();

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        setLocation(gd.getDisplayMode().getWidth() / 2 - getWidth() / 2, gd.getDisplayMode().getHeight() / 2 - getHeight() / 2);

        setDefaultLookAndFeelDecorated(true);
        setResizable(true);

        setVisible(true);

        pack();
    }

    void updateShowUsageModel(){
        showUsage.setModel(new ComboBoxModel() {

            String select_name = "";

            @Override
            public void setSelectedItem(Object anItem) {
                select_name = (String) anItem;
                showPassword.setText((String) passwords.getData(select_name));
            }

            @Override
            public Object getSelectedItem() {
                return select_name;
            }

            @Override
            public int getSize() {
                return passwords.getSize();
            }

            @Override
            public Object getElementAt(int index) {
                return (String)passwords.getKeyAt(index);
            }

            @Override
            public void addListDataListener(ListDataListener l) {

            }

            @Override
            public void removeListDataListener(ListDataListener l) {

            }
        });
        showPassword.setText("");
    }

    public void closeRandomGui(){
        randomPassword.dispatchEvent(new WindowEvent(randomPassword, WindowEvent.WINDOW_CLOSING));
    }
}
