// @todo document me!

import ecobill.core.ui.MainFrame;

import javax.swing.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Start.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:47:38
 *
 * @author Roman R&auml;dle
 * @version $Id: Start.java,v 1.1 2005/07/28 21:03:46 raedler Exp $
 * @since DAPS INTRA 1.0
 */
public class Start {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new ClassPathXmlApplicationContext("ecobill/config/applicationContext.xml");
    }
}