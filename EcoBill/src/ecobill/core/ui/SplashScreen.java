package ecobill.core.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.ComponentUtils;

/**
 *
 */
public class SplashScreen extends JFrame implements InitializingBean, DisposableBean, Runnable {

    /**
     * In diesem <code>Log</code> k�nnen Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben k�nnen in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(MainFrame.class);

    /**
     * Ein eigenst�ndiger <code>Thread</code> der w�hrend des Ladens der eigentlichen Applikation
     * l�uft und sich nach vollst�ndig geladener Anwendung zerst�rt.
     */
    private Thread thread;

    /**
     * Es muss ein <code>Splashable</code> gesetzt werden. MarkerInterface.
     */
    private Splashable splashable;

    /**
     * Setzt das <code>Splashable</code>. MarkerInterface.
     *
     * @param splashable Das <code>Splashabl</code> Marker Interface.
     */
    public void setSplashable(Splashable splashable) {
        this.splashable = splashable;
    }

    /**
     * Das anzuzeigende <code>Image</code>.
     */
    private BufferedImage image;

    /**
     * Erzeugt eine neue <code>SplashScreen</code> und sobald das Splashable gesetzt wurde
     * beendet sich die Screen von selbst.
     *
     * @param splashImageFilename Der Dateiname incl. des relativen Pfades des anzuzeigenden Images.
     */
    public SplashScreen(String splashImageFilename) {

        // Setzt das IconImage des <code>JFrame</code>.
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/ico/currency_dollar.png"));

        setTitle(WorkArea.getMessage(Constants.APPLICATION_TITLE));

        // Setze die f�r einen SplashScreen �blichen Einstellungen.
        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try {
            image = ImageIO.read(new FileInputStream(splashImageFilename));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        setAlwaysOnTop(true);

        thread = new Thread(this);
        thread.start();

        ComponentUtils.centerComponentOnScreen(this);

        setVisible(true);
    }

    /**
     * Diese Methode wird nur verwendet um, nach dem Setzen des <code>Splashable</code>, das
     * <code>SplashScreen</code> zu schlie�en.
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() {
        thread = null;
    }

    /**
     * Diese Methode wird nur verwendet um, nach dem Setzen des <code>Splashable</code>, das
     * <code>SplashScreen</code> zu schlie�en. -> Nur im Fehlerfall.
     *
     * @throws Exception - sollte nicht auftreten.
     */
    public void destroy() throws Exception {
        thread = null;
    }

    /**
     * @see JFrame#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);
    }

    /**
     * @see Runnable#run()
     */
    public void run() {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Starting splash screen animation...");
        }

        while (Thread.currentThread() == thread && thread != null) {

            try {
                Thread.sleep(100);
            }
            catch (InterruptedException ie) {
                if (LOG.isErrorEnabled()) {
                    LOG.error(ie.getMessage(), ie);
                }
            }
        }

        dispose();

        if (LOG.isDebugEnabled()) {
            LOG.debug("... splash screen animation finished.");
        }
    }
}