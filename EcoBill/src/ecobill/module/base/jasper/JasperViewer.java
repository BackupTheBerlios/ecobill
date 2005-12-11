package ecobill.module.base.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JRViewer;

import javax.swing.*;
import java.util.HashMap;
import java.util.Collection;
import java.util.Map;
import java.awt.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ecobill.core.ui.MainFrame;

/**
 * Der <code>JasperViewer</code> bietet die Möglichkeit einen Report auf einem <code>JPanel</code>
 * anzuzeigen. Der Viewer enthält auch Operationen wie drucken, vergrößern,... eines Reports.
 * <p/>
 * User: Paul Chef
 * Date: 13.08.2005
 * Time: 12:43:59
 *
 * @author Andreas Weiler
 * @version $Id: JasperViewer.java,v 1.7 2005/12/11 17:16:01 raedler Exp $
 * @since EcoBill 1.0
 */
public class JasperViewer {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(JasperViewer.class);

    /**
     * JPanel auf das der JRViewer gelegt wird.
     */
    private JPanel viewerPanel;

    /**
     * Der <code>JRViewer</code> der alle Optionen wie Vergrößerung, Drucken, etc.
     * enthält.
     */
    private JRViewer viewer;

    /**
     * Diese <code>Map</code> enthält Parameter die an die Jasper Engine übergeben werden und
     * zur Anzeige des Reports benötigt werden.
     */
    private Map<Object, Object> parameters;

    /**
     * Erzeugt einen neuen <code>JasperViewer</code>, der als Parameter ein <code>JPanel</code>
     * erhält, auf dem dieser Viewer angezeigt werden soll.
     *
     * @param viewerPanel Das <code>JPanel</code> auf dem der Viewer angezeigt werden soll.
     */
    public JasperViewer(JPanel viewerPanel) {
        this.viewerPanel = viewerPanel;
        this.parameters = new HashMap<Object, Object>();
    }

    /**
     * Gibt die <code>Map</code>, die für den Report evtl. benötigte Parameter enthält,
     * zurück.
     *
     * @return Eine <code>Map</code> die für den Report evtl. Parameter enthält.
     */
    public Map<Object, Object> getParameters() {
        return parameters;
    }

    /**
     * Setzt eine <code>Map</code>, die für den Report evtl. benötigte Parameter enthalten kann.
     *
     * @param parameters Eine <code>Map</code> die für den Report evtl. Parameter enthalten kann.
     */
    public void setParameters(Map<Object, Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * Fügt der Parameter <code>Map</code> einen Wert hinzu.
     *
     * @param key   Der Schlüssel für diesen Wert.
     * @param value Der Wert der unter dem Schlüssel gespeichert werden soll.
     */
    public void addParameter(Object key, Object value) {
        parameters.put(key, value);
    }

    /**
     * Entfernt einen Parameter aus der <code>Map</code> und gibt diesen noch
     * zurück.
     *
     * @param key Der Schlüssel dessen Wert gelöscht werden soll.
     * @return Der Wert der unter diesem Schlüssel gefunden und gelöscht wurde.
     */
    public Object removeParameter(Object key) {
        return parameters.remove(key);
    }

    /**
     * Zeigt den <code>JRViewer</code> auf dem <code>JPanel</code> an, das beim Erzeugen des
     * <code>JasperViewer</code> übergeben wurde.
     *
     * @param jrxmlFilename Der Dateiname der spezifischen jrxml Datei.
     * @param datasets      Eine <code>Collection</code> die alle dynamischen Datensätze, die im
     *                      Report angezeigt werden sollen, enthält.
     * @throws Exception Diese wird geworfen wenn ein Fehler während des erzeugens auftreten
     *                   sollte.
     */
    public void view(MainFrame mainFrame, String jrxmlFilename, Collection<Object> datasets) throws Exception {

        try {

            JasperReport report = JasperCompileManager.compileReport(jrxmlFilename);

            mainFrame.setProgressPercentage(60);

            JasperDataSource dataSource = new JasperDataSource(datasets);

            mainFrame.setProgressPercentage(70);

            JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);

            mainFrame.setProgressPercentage(80);

            viewer = new JRViewer(print);

            viewerPanel.add(viewer, BorderLayout.CENTER);
            viewerPanel.validate();

            mainFrame.setProgressPercentage(90);
        }
        catch (JRException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Entfernt den <code>JasperViewer</code>, von diesem als Parameter übergebenen
     * <code>JPanel</code>.
     */
    public void remove() {
        viewerPanel.remove(viewer);
        viewerPanel.repaint();
    }
}
