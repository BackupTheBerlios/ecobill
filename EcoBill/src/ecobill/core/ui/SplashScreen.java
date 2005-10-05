package ecobill.core.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 */
public class SplashScreen extends JFrame implements InitializingBean, Runnable {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(SplashScreen.class);

    /**
     * Ein eigenständiger <code>Thread</code> der während des Ladens der eigentlichen Applikation
     * läuft und sich nach vollständig geladener Anwendung zerstört.
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

        // Setze die für einen SplashScreen üblichen Einstellungen.
        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try {
            image = ImageIO.read(new FileInputStream(splashImageFilename));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        center();

        thread = new Thread(this);
        thread.start();

        setVisible(true);
    }

    /**
     * Zentriert die <code>SplashScreen</code> im sichtbaren Bereich des Bildschirms.
     */
    private void center() {

        // Größe der eingestellten Bildschirmauflösung.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        width -= image.getWidth();
        height -= image.getHeight();

        setSize(new Dimension(image.getWidth(), image.getHeight()));

        setLocation((int) width / 2, (int) height / 2);
    }

    /**
     * Diese Methode wird nur verwendet um, nach dem Setzen des <code>Splashable</code>, das
     * <code>SplashScreen</code> zu schließen.
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() {
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