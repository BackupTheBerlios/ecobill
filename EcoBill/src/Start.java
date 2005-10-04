import javax.swing.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Diese Klasse bietet die eine Startmethode zum starten der Economy Bill Agenda.
 * Start des <code>Spring</code> application contextes.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:47:38
 *
 * @author Roman R&auml;dle
 * @version $Id: Start.java,v 1.4 2005/10/04 11:04:31 raedler Exp $
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

        new ClassPathXmlApplicationContext("ecobill/config/applicationContext.xml");
    }
}