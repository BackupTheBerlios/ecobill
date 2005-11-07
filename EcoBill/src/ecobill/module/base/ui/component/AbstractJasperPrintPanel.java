package ecobill.module.base.ui.component;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.jasper.JasperViewer;
import ecobill.core.ui.MainFrame;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;

import java.awt.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Das <code>AbstractJasperPrintPanel</code> ist ein abstraktes <code>JPanel</code> das eine Anzeige eines
 * zu erzeugenden Jasper Reportes erlaubt.
 * <p/>
 * User: Paul Chef
 * Date: 18.08.2005
 * Time: 16:45:41
 *
 * @author Andreas Weiler
 * @version $Id: AbstractJasperPrintPanel.java,v 1.2 2005/11/07 21:49:30 raedler Exp $
 * @since EcoBill 1.0
 */
public abstract class AbstractJasperPrintPanel extends JPanel implements Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private final Log LOG = LogFactory.getLog(getClass());

    /**
     * Der Hauptframe der Anwendung.
     */
    private MainFrame mainFrame;

    /**
     * Gibt den Hauptframe der Anwendung zur zurück.
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
     * Gibt den <code>BaseService</code>, die Business Logik, zurück.
     *
     * @return Der <code>BaseService</code> ist die Business Logik.
     */
    public BaseService getBaseService() {
        return baseService;
    }

    /**
     * Der <code>JasperViewer</code> enthält die Logik zum Füllen und zur Anzeige eines Reports.
     */
    private JasperViewer jasperViewer = new JasperViewer(this);

    /**
     * Gibt den <code>JasperViewer</code> zurück, der die die Logik zum Füllen und zur Anzeige
     * eines Reports, enthält.
     *
     * @return Der <code>JasperViewer</code> enthält die Logik zum Füllen und zur Anzeige eines
     *         Reports.
     */
    public JasperViewer getJasperViewer() {
        return jasperViewer;
    }

    /**
     * Standard Konstruktor.
     */
    public AbstractJasperPrintPanel(MainFrame mainFrame, BaseService baseService) {

        this.mainFrame = mainFrame;
        this.baseService = baseService;

        initComponents();
        initLayout();

        reinitI18N();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {
        setBorder(createPanelBorder());
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {
        setLayout(new BorderLayout());
    }

    /**
     * Entfernt zuerst alle Komponenten dieses <code>JPanel</code> und danach wird
     * der <code>JasperViewer</code> gestartet und das neue Resultat auf dem Panel
     * angezeigt.
     *
     * @param id Eine Id zum Laden der anzuzeigenden Daten.
     */
    public void doJasper(Long id) throws Exception {

        // Entferne evtl. schon vorhandene Komponenten.
        removeAll();

        // TODO: Hier wäre es auch möglich direkt von Thread abzuleiten. SINNVOLL?!?
        // Starte nebenläufigen <code>JasperThread</code>.
        new Thread(new JasperThread(id)).start();
    }

    /**
     * Löscht den Inhalt des Viewer <code>JPanel</code>.
     */
    public void clearViewerPanel() {
        jasperViewer.remove();
    }

    /**
     * In dieser Methode werden die spezifischen Jasperdaten geladen und dem <code>JasperViewer</code>
     * übergeben.
     *
     * @param id Eine Id zum Laden der anzuzeigenden Daten.
     * @throws Exception Diese <code>Exception</code> tritt evtl. während des Jasper Vorganges
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
     * dem <code>JPanel</code> an. Er ist nebenläufig zum eigentlichen Programm.
     */
    private class JasperThread implements Runnable {

        /**
         * Die id zum Laden des <code>Object</code>.
         */
        private Long id;

        /**
         * Ein neuer <code>JasperThread</code> der eine id zum Laden eines, für den
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
                validate();
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
