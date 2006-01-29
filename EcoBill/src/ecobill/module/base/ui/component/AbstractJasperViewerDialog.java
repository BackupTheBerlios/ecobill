package ecobill.module.base.ui.component;

import ecobill.core.system.Internationalization;
import ecobill.core.ui.MainFrame;
import ecobill.core.util.ComponentUtils;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.jasper.JasperViewer;

import javax.swing.*;
import javax.swing.border.Border;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;

// @todo document me!

/**
 * AbstractJasperViewerDialog.
 * <p/>
 * User: rro
 * Date: 11.12.2005
 * Time: 16:06:51
 *
 * @author Roman R&auml;dle
 * @version $Id: AbstractJasperViewerDialog.java,v 1.2 2006/01/29 23:16:45 raedler Exp $
 * @since EcoBill 1.1
 */
public abstract class AbstractJasperViewerDialog extends JDialog implements Internationalization {

    /**
     * In diesem <code>Log</code> k\u00f6nnen Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben k\u00f6nnen in einem separaten File spezifiziert werden.
     */
    private final Log LOG = LogFactory.getLog(getClass());

    /**
     * Der Hauptframe der Anwendung.
     */
    private MainFrame mainFrame;

    /**
     * Gibt den Hauptframe der Anwendung zur zur�ck.
     *
     * @return Der Hauptframe der Anwendung.
     */
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * Der <code>BaseService</code> ist die Business Logik.
     */
    private BaseService baseService;

    /**
     * Gibt den <code>BaseService</code>, die Business Logik, zur�ck.
     *
     * @return Der <code>BaseService</code> ist die Business Logik.
     */
    public BaseService getBaseService() {
        return baseService;
    }

    /**
     * Das <code>JPanel</code> auf dem der <code>JasperViewer</code> liegt.
     */
    private JPanel viewerPanel = new JPanel(new BorderLayout());

    /**
     * Der <code>JasperViewer</code> enth�lt die Logik zum F�llen und zur Anzeige eines Reports.
     */
    private JasperViewer jasperViewer = new JasperViewer(viewerPanel);

    /**
     * Gibt den <code>JasperViewer</code> zur�ck, der die die Logik zum F�llen und zur Anzeige
     * eines Reports, enth�lt.
     *
     * @return Der <code>JasperViewer</code> enth�lt die Logik zum F�llen und zur Anzeige eines
     *         Reports.
     */
    public JasperViewer getJasperViewer() {
        return jasperViewer;
    }

    /**
     * Standard Konstruktor.
     */
    public AbstractJasperViewerDialog(MainFrame mainFrame, boolean modal, BaseService baseService, Long id) {
        super(mainFrame, modal);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.mainFrame = mainFrame;
        this.baseService = baseService;

        initComponents();
        initLayout();

        reinitI18N();

        try {
            doJasper(id);
        }
        catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {
        viewerPanel.setBorder(createPanelBorder());
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {
        setLayout(new BorderLayout());

        setSize(new Dimension(700, 600));

        getContentPane().add(viewerPanel, BorderLayout.CENTER);
    }

    /**
     * Entfernt zuerst alle Komponenten dieses <code>JPanel</code> und danach wird
     * der <code>JasperViewer</code> gestartet und das neue Resultat auf dem Panel
     * angezeigt.
     *
     * @param id Eine Id zum Laden der anzuzeigenden Daten.
     */
    public void doJasper(Long id) throws Exception {

        // TODO: Hier w�re es auch m�glich direkt von Thread abzuleiten. SINNVOLL?!?
        // Starte nebenl�ufigen <code>JasperThread</code>.
        new Thread(new JasperThread(id)).start();
    }

    /**
     * L�scht den Inhalt des Viewer <code>JPanel</code>.
     */
    public void clearViewerPanel() {
        jasperViewer.remove();
    }

    /**
     * Gibt den Rahmen zur�ck der um das <code>JPanel</code> viewerPanel liegt.
     *
     * @return Der Rahmen um das <code>JPanel</code> viewerPanel.
     */
    public Border getBorder() {
        return viewerPanel.getBorder();
    }

    /**
     * In dieser Methode werden die spezifischen Jasperdaten geladen und dem <code>JasperViewer</code>
     * �bergeben.
     *
     * @param id Eine Id zum Laden der anzuzeigenden Daten.
     * @throws Exception Diese <code>Exception</code> tritt evtl. w�hrend des Jasper Vorganges
     *                   auf.
     */
    protected abstract void jasper(Long id) throws Exception;

    /**
     * Erzeugt einen <code>Border</code> der um das gesamte <code>JPanel</code> gelegt wird.
     *
     * @return Ein <code>Border</code> der um das <code>JPanel</code> gelegt werden soll.
     */
    protected abstract Border createPanelBorder();

    /**
     * Dieser <code>Thread</code> erzeugt die Report Seiten und zeigt diese auf
     * dem <code>JPanel</code> an. Er ist nebenl�ufig zum eigentlichen Programm.
     */
    private class JasperThread implements Runnable {

        /**
         * Die id zum Laden des <code>Object</code>.
         */
        private Long id;

        /**
         * Ein neuer <code>JasperThread</code> der eine id zum Laden eines, f�r den
         * <code>JasperViewer</code> vorgesehenen, <code>Object</code>.
         *
         * @param id Die id zum Laden des <code>Object</code>.
         */
        protected JasperThread(Long id) {
            this.id = id;
        }

        /**
         * @see Runnable#run()
         */
        public void run() {
            try {
                jasper(id);

                ComponentUtils.centerComponentOnScreen(AbstractJasperViewerDialog.this);

                setVisible(true);
            }
            catch (Exception e) {
                if (LOG.isErrorEnabled()) {
                    LOG.error(e.getMessage(), e);
                }
                e.printStackTrace();
            }
        }
    }
}

