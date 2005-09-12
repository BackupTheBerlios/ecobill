package ecobill.core.ui.component;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Internationalization;
import ecobill.core.system.service.Service;
import ecobill.core.system.service.ServiceProvider;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.*;
import java.util.Vector;

// @todo document me!

/**
 * GenericTable.
 * <p/>
 * User: rro
 * Date: 08.08.2005
 * Time: 19:39:19
 *
 * @author Roman R&auml;dle
 * @version $Id: GenericTable.java,v 1.2 2005/09/12 17:29:32 raedler Exp $
 * @since EcoBill 1.0
 */
public abstract class GenericTable extends JTable implements Internationalization {

    /**
     * Dieser <code>Vector</code> enth�lt die Keys der Namen des Tabellen
     * Header.
     */
    private Vector<String> keyHeaderVector = new Vector<String>();

    /**
     * Dieses Array enth�lt die Reihenfolge wie die Spalten der Tabelle
     * angezeigt werden sollen.
     */
    private String[] keyOrder = new String[0];

    /**
     * Das <code>TableModel</code> enth�lt sp�ter die Daten der Tabelle.
     */
    private DefaultTableModel tableModel = new DefaultTableModel();

    /**
     * Dieses Objekt beinhaltet alle zur Anzeige ben�tigten Daten.
     */
    private Object view = null;

    /**
     * Dieser <code>Service</code> wird evtl. ben�tigt um Daten von der
     * Datenbank zu laden bzw. wieder dort abzulegen.
     */
    private Service service = null;

    /**
     * Der <code>ServiceProvider</code> bietet den <code>Service</code>
     * eines bestimmten Modules an.
     */
    private ServiceProvider serviceProvider = null;

    /**
     * Initialisiert abgeleitete Klassen der <code>GenericTable</code>.
     *
     * @param keyOrder    Die Reihenfolge der Tabellen Header.
     * @param view        Dieses Objekt beinhaltet alle zur Anzeige
     *                    ben�tigten Tabelle.
     * @param cellEditors Dieses Array aus <code>JComponent</code>
     *                    beinhaltet die Komponenten die einem
     *                    <code>CellEditor</code> hinzugef�gt werden
     *                    k�nnen.
     * @param service     Dieser <code>Service</code> wird evtl.
     *                    ben�tigt um Daten von der Datenbank zu laden
     *                    bzw. wieder dort abzulegen.
     */
    public GenericTable(String[] keyOrder, Object view, JComponent[] cellEditors, Service service) {
        this.keyOrder = keyOrder;
        this.view = view;
        this.service = service;

        /*
         * Erste Initialisierung des <code>TableModel</code> und der <code>Table</code> mit den
         * landesspezifischen Werten.
         */
        reinitI18N();

        // Setzen des <code>TableModel</code> f�r diese generische Tabelle.
        setModel(tableModel);

        // Initialisieren einiger Komponenten der abgeleiteten Klassen.
        initServices();
        initTableListeners();
        initCellEditors();
        initCellEditors(cellEditors);
    }

    /**
     * Initialisiert abgeleitete Klassen der <code>GenericTable</code>.
     *
     * @param keyOrder    Die Reihenfolge der Tabellen Header.
     * @param view        Dieses Objekt beinhaltet alle zur Anzeige
     *                    ben�tigten Tabelle.
     * @param cellEditors Dieses Array aus <code>JComponent</code>
     *                    beinhaltet die Komponenten die einem
     *                    <code>CellEditor</code> hinzugef�gt werden
     *                    k�nnen.
     */
    public GenericTable(String[] keyOrder, Object view, JComponent[] cellEditors) {
        this(keyOrder, view, cellEditors, null);
    }

    /**
     * Initialisiert abgeleitete Klassen der <code>GenericTable</code>.
     *
     * @param keyOrder Die Reihenfolge der Tabellen Header.
     * @param view     Dieses Objekt beinhaltet alle zur Anzeige
     *                 ben�tigten Tabelle.
     */
    public GenericTable(String[] keyOrder, Object view) {
        this(keyOrder, view, null, null);
    }

    /**
     * Die von dem Interface <code>Internationalization</code> implementierte
     * Methode soll die Komponente sprachunabh�ngig halten. Die Daten die
     * angezeigt werden sollen werden von der abstrakten Methode
     * reinitTableModel bereit gestellt.
     */
    public void reinitI18N() {
        reinitTableHeader();

        Vector<Vector<Object>> dataVector = reinitTableModel(view);

        if (dataVector != null) {
            tableModel.setDataVector(dataVector, keyHeaderVector);
        }
    }

    /**
     * Initialisiert den Tabellen Header mit der spezifischen Sprache
     * neu.
     */
    private void reinitTableHeader() {

        // Entfernen aller Elemente im <code>Vector</code>.
        if (keyHeaderVector != null) {
            keyHeaderVector.removeAllElements();
        }
        else {
            keyHeaderVector = new Vector<String>();
        }

        /*
         * Hinzuf�gen der landesspezifischen Bezeichnungen in der richtigen
         * Reihenfolge.
         */
        for (String tableOrder : keyOrder) {
            keyHeaderVector.add(WorkArea.getMessage(tableOrder));
        }

        tableModel.setColumnIdentifiers(keyHeaderVector);
    }

    /**
     * Durch implementation dieser Methode ist es durch �bergabe des
     * view <code>Object</code> k�nnen die Daten in diesem Objekt
     * zur Anzeige gebracht und n�tigenfalls neu initialisiert werden.
     *
     * @param view Dieses Objekt beinhaltet alle zur Anzeige ben�tigten
     *             Daten.
     */
    public abstract Vector<Vector<Object>> reinitTableModel(Object view);

    /**
     * Diese Methode kann dazu verwendet werden um den Service in ein bestimmtes
     * Format zu casten.
     */
    protected abstract void initServices();

    /**
     * Durch die Implementation dieser Methode k�nnen der Tabelle Listener
     * hinzugef�gt werden.
     */
    protected abstract void initTableListeners();

    /**
     * Durch die Implementation dieser Methode k�nnen der Tabelle (Spalten)
     * <code>CellEditor</code> gesetzt werden.
     * Hier kann das <code>JComponent</code> Array verwendet werden, das dem
     * Konstruktor �bergeben wurde.
     * Es kann entweder nur diese oder Methode oder initCellEditors() implementiert
     * werden. Werden beide implementiert, werden beide zusammengelegt wobei
     * diese eine h�here Priorit�t erh�lt.
     */
    protected abstract void initCellEditors(JComponent[] components);

    /**
     * Durch die Implementation dieser Methode k�nnen der Tabelle (Spalten)
     * <code>CellEditor</code> gesetzt werden.
     */
    protected abstract void initCellEditors();

    /**
     * Setzt einen neuen View.
     *
     * @param view Die Daten die angezeigt werden sollen.
     */
    public void setView(Object view) {
        this.view = view;
    }

    /**
     * @see javax.swing.JPanel#repaint()
     */
    public void repaint() {
        super.repaint();
    }

    /**
     * Gibt das Model der Tabelle zur�ck.
     *
     * @return Das <code>TableModel</code> der Tabelle.
     */
    protected TableModel getTableModel() {
        return this.tableModel;
    }

    /**
     * Gibt den <code>Service</code> zur�ck, der in der Lage ist Daten
     * von der Datenbank zu laden, �ndern und wieder abzuspeichern. Dieser
     * Service h�ngt von dem jeweiligen Modul ab. Er ist auch leer sowie
     * das Interface <code>Serializable</code>.
     *
     * @return Der <code>Service</code> mit dem es m�glich ist Daten zu laden
     *         �ndern und wieder zu speichern.
     */
    protected Service getService() {
        return service != null ? service : serviceProvider.getService();
    }

    /**
     * Gibt einen leeren Datenvektor zur�ck.
     *
     * @return Ein leerer Datenvektor.
     */
    protected Vector<Vector<Object>> getEmptyDataVector() {
        return new Vector<Vector<Object>>();
    }

    /**
     * Setzt den <code>ServiceProvider</code>, mit dem man in der Lage den
     * <code>Service</code> zu erhalten.
     *
     * @param serviceProvider Der <code>ServiceProvider</code> bietet den
     *                        <code>Service</code> eines bestimmten Modules
     *                        an.
     */
    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
