import javax.swing.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.BeansException;
import ecobill.core.ui.MainFrame;
import ecobill.core.ui.SplashScreen;

/**
 * Diese Klasse bietet die eine Startmethode zum starten der Economy Bill Agenda.
 * Start des <code>Spring</code> application contextes.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:47:38
 *
 * @author Roman R&auml;dle
 * @version $Id: Start.java,v 1.6 2005/11/07 00:01:27 raedler Exp $
 * @since Ecobill 1.0
 */
public class Start {

    /**
     * Die Startmethode um Economy Bill Agenda zu starten.
     *
     * @param args - Wird nicht verwendet.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        try {
            new ClassPathXmlApplicationContext("ecobill/config/applicationContext.xml");
        }
        catch (BeansException be) {
            be.printStackTrace();

            System.exit(100);
        }
    }
}