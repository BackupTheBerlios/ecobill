package ecobill;

import javax.swing.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.BeansException;
import ecobill.core.ui.MainFrame;
import ecobill.core.ui.SplashScreen;
import ecobill.core.ui.ConnectionSettings;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Diese Klasse bietet die eine Startmethode zum starten der Economy Bill Agenda.
 * Start des <code>Spring</code> application contextes.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:47:38
 *
 * @author Roman R&auml;dle
 * @version $Id: Start.java,v 1.1 2006/02/02 22:18:27 raedler Exp $
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
        catch (UnsupportedLookAndFeelException ulafe) {
            ulafe.printStackTrace();
        }

        new ConnectionSettings(new Start());
    }

    public void startUp(String className, String url, String username, String password) {

        Class clazz = null;
        try {
            //clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            clazz = getClass().getClassLoader().loadClass(className);
        }
        catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        if (clazz == null) {
            new ConnectionSettings(this);
            return;
        }

        Object o = null;

        try {
            o = clazz.newInstance();
        }
        catch (InstantiationException ie) {
            ie.printStackTrace();
        }
        catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }

        if (o instanceof Driver) {

            Driver drv = (Driver) o;

            try {
                drv.acceptsURL(url);
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();

                new ConnectionSettings(this);
                return;
            }

            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);

            try {
                drv.connect(url, props);
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();

                new ConnectionSettings(this);
                return;
            }
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