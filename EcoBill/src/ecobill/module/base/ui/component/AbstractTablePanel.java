package ecobill.module.base.ui.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.layout.GroupLayout;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import ecobill.core.util.I18NItem;
import ecobill.core.system.Internationalization;
import ecobill.module.base.service.BaseService;

import java.util.Vector;
import java.util.Collection;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.*;

/**
 * Das <code>AbstractTablePanel</code> ist eine abstrakte Klasse um auf einfache Art und Weise
 * Tabellen für EcoBill zu erstellen.
 * <p/>
 * User: rro
 * Date: 02.10.2005
 * Time: 12:33:23
 *
 * @author Roman R&auml;dle
 * @version $Id: AbstractTablePanel.java,v 1.3 2005/10/06 14:07:17 raedler Exp $
 * @since EcoBill 1.0
 */
public abstract class AbstractTablePanel extends JPanel implements Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    protected final Log LOG = LogFactory.getLog(getClass());

    /**
     * Der <code>Border</code> der um das gesamte <code>JPanel</code> gelegt wird.
     */
    private Border panelBorder;

    /**
     * Gibt den <code>Border</code>, der um das gesamte <code>JPanel</code> gelegt wird, zurück.
     *
     * @return Der <code>Border</code> der um das gesamte <code>JPanel</code> gelegt wird.
     */
    public Border getPanelBorder() {
        return panelBorder;
    }

    /**
     * Die Reihenfolge wie die Spalten angezeigt werden, sofern das <code>TableColumnModel</code>
     * noch nicht serialisiert wurde.
     */
    private Vector<I18NItem> tableColumnOrder;

    /**
     * Gibt die Reihenfolge wie die Spalten angezeigt werden sollen, zurück.
     *
     * @return Die Reihenfolge wie die Spalten angezeigt werden sollen.
     */
    public Vector<I18NItem> getTableColumnOrder() {
        return tableColumnOrder;
    }

    /**
     * Das <code>TableModel</code> beinhaltet die eigentlichen Daten, die zur Anzeige verwendet werden
     * sollen.
     */
    private DefaultTableModel tableModel = new DefaultTableModel();

    /**
     * Gibt das <code>TableModel</code> der Tabelle zurück.
     *
     * @return Das <code>TableModel</code> der Tabelle.
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    /**
     * Die eigentliche <code>JTable</code> mit ihrem <code>TableModel</code>.
     */
    private JTable table = new JTable(getTableModel());

    /**
     * Gibt die eigentliche <code>JTable</code> mit ihrem <code>TableModel</code>, zurück.
     *
     * @return Die eigentliche <code>JTable</code> mit ihrem <code>TableModel</code>.
     */
    public JTable getTable() {
        return table;
    }

    /**
     * Eine <code>JScrollPane</code> um zu ermöglichen, dass die Tabelle gescrollt werden kann und der
     * Tabellen Header angezeigt wird.
     */
    private JScrollPane tableSP = new JScrollPane();

    /**
     * Gibt die <code>JScrollPane</code> zurück, in der die Tabelle liegt. Diese <code>JScrollPane</code>
     * ist nötig um den Tabellen Header anzuzeigen und auch um die Tabelle scrollbar zu gestallten.
     *
     * @return Die <code>JScrollPane</code>, in der die Tabelle liegt.
     */
    public JScrollPane getTableSP() {
        return tableSP;
    }

    /**
     * Der <code>BaseService</code> ist die Business Logik. Unter anderem können hierdurch Daten
     * aus der Datenbank ausgelesen und gespeichert werden.
     */
    private BaseService baseService;

    /**
     * Gibt den <code>BaseService</code> und somit die Business Logik zurück.
     *
     * @return Der <code>BaseService</code>.
     */
    public BaseService getBaseService() {
        return baseService;
    }

    /**
     * Setzt den <code>BaseService</code> der die komplette Business Logik enthält
     * um bspw Daten aus der Datenbank zu laden und dorthin auch wieder abzulegen.
     *
     * @param baseService Der <code>BaseService</code>.
     */
    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    protected AbstractTablePanel(BaseService baseService) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Erzeuge TablePanel.");
        }

        // Der <code>BaseService</code> um den Zugriff auf die Datenbank zu ermöglichen.
        this.baseService = baseService;

        // Erzeugt den Panel <code>Border</code> und setzt die Reihenfolge der Spalten.
        panelBorder = createPanelBorder();
        tableColumnOrder = createTableColumnOrder();

        // Initialisieren der Komponenten und des Layouts.
        initComponents();
        initLayout();

        // Fügt der <code>JTable</code> und dem <code>TableModel</code> die Listener hinzu.
        addKeyListeners(createKeyListeners());
        addMouseListeners(createMouseListeners());
        addTableModelListeners(createTableModelListeners());

        // Erstes initialisieren der Labels, etc...
        reinitI18N();

        // Setzt die Reihenfolge der Spalten.
        tableModel.setColumnIdentifiers(getTableColumnOrder());

        // Dieser renew wird ausgeführt um die Tabelle beim ersten Laden mit Daten zu
        // füllen.
        renewTableModel();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        setBorder(panelBorder);

        tableSP.setViewportView(table);
        tableSP.getViewport().setBackground(Color.WHITE);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Schaltet das Tabellengitter aus.
        table.setShowGrid(false);
    }

    /**
     * Initialisiert den <code>LayoutManager</code>.
     */
    private void initLayout() {

        GroupLayout layout = new GroupLayout(this);

        setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(tableSP, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(tableSP, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                        .addContainerGap())
        );
    }

    /**
     * Fügt der Tabelle alle <code>KeyListener</code> aus diesem Array hinzu.
     *
     * @param keyListeners Ein Array mit <code>KeyListener</code>.
     */
    private void addKeyListeners(KeyListener[] keyListeners) {

        if (keyListeners != null) {
            for (KeyListener keyListener : keyListeners) {
                table.addKeyListener(keyListener);
            }
        }
    }

    /**
     * Fügt der Tabelle alle <code>MouseListener</code> aus diesem Array hinzu.
     *
     * @param mouseListeners Ein Array mit <code>MouseListener</code>.
     */
    private void addMouseListeners(MouseListener[] mouseListeners) {

        if (mouseListeners != null) {
            for (MouseListener mouseListener : mouseListeners) {
                table.addMouseListener(mouseListener);
            }
        }
    }

    /**
     * Fügt der Tabelle alle <code>TableModelListener</code> aus diesem Array hinzu.
     *
     * @param tableModelListeners Ein Array mit <code>TableModelListener</code>.
     */
    private void addTableModelListeners(TableModelListener[] tableModelListeners) {

        if (tableModelListeners != null) {
            for (TableModelListener tableModelListener : tableModelListeners) {
                tableModel.addTableModelListener(tableModelListener);
            }
        }
    }

    /**
     * Erneuert das <code>TableModel</code> und somit die Daten die darin enthalten sind.
     * Es müssen dazu die Methoden {@link this#createLineVector(Object)} und
     * {@link this#getDataCollection()} richtig implementiert werden.
     */
    public void renewTableModel() {

        // Entfernt alle schon vorhandenen Zeilen aus dem <code>TableModel</code>.
        // Dies muss gemacht werden, das sonst alle Einträge die schon vorhanden
        // sind auch nochmal angezeigt werden.
        Vector dataVector = tableModel.getDataVector();
        dataVector.removeAllElements();

        // Holt die in der Methode implementierte <code>Collection</code> um diese
        // später dem <code>TableModel</code> hinzufügen zu können.
        Collection dataCollection = getDataCollection();

        // Iteriert über die <code>Collection</code> und fügt jedes <code>Object</code>
        // als Zeile dem Datenvektor hinzu. Dazu muss die Methode createLineVector(Object)
        // richtig implementiert werden.
        for (Object o : dataCollection) {

            // Fügt den erzeugten <code>Vector</code> als Zeile dem Datenvektor hinzu.
            dataVector.add(createLineVector(o));
        }

        // Zeichnet die Tabelle nach hinzufügen aller Objekte neu.
        table.repaint();
        tableSP.setViewportView(table);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {
        tableSP.setViewportView(table);
    }

    /**
     * @see ecobill.core.system.Persistable#persist(java.io.OutputStream)
     */
    public void persist(OutputStream outputStream) {

        try {

            // Entferne alle Editoren, da sonst das <code>TableModel</code> nicht serialisiert
            // werden kann.
            table.removeEditor();

            // Entferne alle Daten aus dem Datenvektor.
            tableModel.getDataVector().removeAllElements();

            // Erzeuge einen <code>ObjectOutputStream</code> um das <code>TableModel</code>
            // zu serialisieren. Danach wird das serialisierte <code>Object</code> geschrieben,
            // der <code>ObjectOutputStream</code> geflusht und geschlossen.
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(table.getColumnModel());
            oos.flush();
            oos.close();
        }
        catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * @see ecobill.core.system.Persistable#unpersist(java.io.InputStream)
     */
    public void unpersist(InputStream inputStream) {

        // Erzeuge einen <code>ObjectInputStream</code> um das <code>TableModel</code> wieder
        // zu laden, sollte dieses bereits schon persistiert sein.
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(inputStream);

            // Lade das persistierte <code>TableColumnModel</code>.
            TableColumnModel columnModel = (TableColumnModel) ois.readObject();

            // Abschließend wird das geladene <code>TableColumnModel</code> wieder in der Tabelle
            // gesetzt.
            table.setColumnModel(createEditoredColumnModelAfterUnpersist(columnModel));
        }
        catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
        }
        finally {
            if (ois != null) {
                try {
                    ois.close();
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    /**
     * Diese Methode kann dazu verwendet werden, um dem <code>TableColumnModel</code> nach einem
     * {@link this#unpersist(java.io.InputStream)}, wieder Editoren hinzuzufügen.
     *
     * @param tableColumnModel Das <code>TableColumnModel</code> der Tabelle.
     * @return Das <code>TableColumnModel</code> dem Editoren hinzugefügt worden sein können.
     */
    protected TableColumnModel createEditoredColumnModelAfterUnpersist(TableColumnModel tableColumnModel) {
        return tableColumnModel;
    }

    /**
     * Durch Überschreiben dieser Methode ist es möglich der <code>JTable</code> <code>KeyListener</code>
     * hinzuzufügen.
     *
     * @return Ein Array mit <code>KeyListener</code> die der <code>JTable</code> hinzugefügt
     *         werden sollen.
     */
    protected KeyListener[] createKeyListeners() {
        return null;
    }

    /**
     * Durch Überschreiben dieser Methode ist es möglich der <code>JTable</code> <code>MouseListener</code>
     * hinzuzufügen.
     *
     * @return Ein Array mit <code>MouseListener</code> die der <code>JTable</code> hinzugefügt
     *         werden sollen.
     */
    protected MouseListener[] createMouseListeners() {
        return null;
    }

    /**
     * Durch Überschreiben dieser Methode ist es möglich dem <code>TableModel</code>
     * <code>TableModelListener</code> hinzuzufügen.
     *
     * @return Ein Array mit <code>TableModelListener</code> die dem <code>TableModel</code> hinzugefügt
     *         werden sollen.
     */
    protected TableModelListener[] createTableModelListeners() {
        return null;
    }

    /**
     * Erzeugt einen <code>Border</code> der um das gesamte <code>JPanel</code> gelegt wird.
     *
     * @return Ein <code>Border</code> der um das <code>JPanel</code> gelegt werden soll.
     */
    protected abstract Border createPanelBorder();

    /**
     * Erzeugt einen <code>Vector</code> mit <code>I18NItem</code> Elementen um die Tabelle
     * beim ersten Anwendungsstart zu initialisieren. Die Reihenfolge der Spalten gleicht
     * der Reihenfolge wie die <code>I18NItem</code> Elemente im <code>Vector</code>
     * stecken.
     *
     * @return Ein <code>Vector</code> mit <code>I18NItem</code> Elementen.
     */
    protected abstract Vector<I18NItem> createTableColumnOrder();

    /**
     * Es wird hier eine <code>Collection</code> zurückgeliefert. Diese <code>Collection</code>
     * enthält <code>Object</code> die später bei {@link this#createLineVector(Object)}
     * ankommen und dort entsprechend behandelt werden können.
     *
     * @return Eine <code>Collection</code> die alle Daten enthält die in der Tabelle angezeigt
     *         werden sollen.
     */
    protected abstract Collection getDataCollection();

    /**
     * Erzeugt einen <code>Vector</code>, der als Zeile in der Tabelle angezeigt wird.
     *
     * @param o Ein <code>Object</code> aus der <code>Collection</code> das bei
     *          {@link this#getDataCollection()} zurückgeliefert wird.
     * @return Der erzeugte <code>Vector</code> wird als Zeilenvektor dem Datenvektor
     *         hinzugefügt.
     */
    protected abstract Vector createLineVector(Object o);
}
