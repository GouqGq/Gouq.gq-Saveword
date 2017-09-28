package gq.gouq;

import javax.swing.*;
import java.awt.event.InputEvent;

/**
 * Created by Mega
 * Intellij IDEA
 */
public class Main {

    public static Gui gui;

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            e.printStackTrace();
        }

        gui = new Gui();
        while (true) {
            if (!gui.isVisible()) {
                gui.save_passwords.run();
                System.exit(0);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
