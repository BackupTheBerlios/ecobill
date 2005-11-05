package ecobill.module.base.ui.bill;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.layout.GroupLayout;
import ecobill.module.base.ui.article.LabellingTable;
import ecobill.module.base.ui.article.ArticleUI;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.ArticleDescription;
import ecobill.core.util.I18NItem;
import ecobill.core.util.IdKeyItem;
import ecobill.core.system.Constants;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Internationalization;
import ecobill.core.system.Persistable;

import javax.swing.border.TitledBorder;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.Vector;
import java.util.Set;
import java.awt.*;
import java.io.*;

/**
 * Die <code>BillPreviewTable</code> enthält alle Daten, zur Vorschau der Lieferscheindaten  zur Rechnungsvor
 * schau auf dem Rechnungsübersichtstab
 * <p/>
 * User: sega
 * Date: 10.10.2005
 * Time: 17:49:23
 *
 * @author Sebastian Gath
 * @version $Id: BillPreviewTableN.java,v 1.2 2005/11/05 19:34:42 gath Exp $
 * @since EcoBill 1.0
 */

public class BillPreviewTableN extends JPanel implements Internationalization, Persistable {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(LabellingTable.class);

    /**
     * Die Reihenfolge wie die Spalten angezeigt werden, sofern das <code>TableColumnModel</code>
     * noch nicht serialisiert wurde.
     */
    private final static Vector<I18NItem> PREVBILLS_TABLE_ORDER = new Vector<I18NItem>();

    static {
        PREVBILLS_TABLE_ORDER.add(new I18NItem(Constants.DELIVERY_ORDER_NUMBER));
        PREVBILLS_TABLE_ORDER.add(new I18NItem(Constants.DELIVERY_ORDER_DATE));
        PREVBILLS_TABLE_ORDER.add(new I18NItem(Constants.DELIVERY_ORDER_AMOUNT));
     }

    ;

    /**
     * Der <code>Border</code> der um das <code>JPanel</code> gelegt wird.
     */
    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.RESIDUAL_DESCRIPTIONS), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    /**
     * Das <code>TableModel</code> beinhaltet die eigentlichen Daten, die zur Anzeige verwendet werden
     * sollen.
     */
    private DefaultTableModel tableModel = new DefaultTableModel();

    /**
     * Die eigentlich <code>JTable</code> mit ihrem <code>TableModel</code>.
     */
    private JTable table = new JTable(tableModel);

    /**
     * Gibt die Tabelle in der Anzeige zurück.
     *
     * @return Die <code>JTable</code> in der Anzeige.
     */
    public JTable getTable() {
        return table;
    }

    /**
     * Setzt die Tabelle die anzeigt werden soll.
     *
     * @param table Eine <code>JTable</code> die angezeigt werden soll.
     */
    public void setTable(JTable table) {
        this.table = table;
    }

    /**
     * Eine <code>JScrollPane</code> um zu ermöglichen, dass die Tabelle gescrollt werden kann und der
     * Tabellen Header angezeigt wird.
     */
    private JScrollPane tableSP = new JScrollPane();

    /**
     * Erzeugt eine neues Labelling Table Panel für Artikel.
     */
    public BillPreviewTableN() {
        initComponents();
        initLayout();
        reinitI18N();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        setBorder(border);

        tableSP.setViewportView(table);
        tableSP.getViewport().setBackground(Color.WHITE);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Schaltet das Tabellengitter aus.
        table.setShowGrid(false);
    }

    private void initLayout() {

        GroupLayout layout = new GroupLayout(this);

        setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(tableSP, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(tableSP, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                        .addContainerGap())
        );
    }

    public void renewTableModel(BillPreviewCollection bpc) {

        if (bpc != null) {

            // Entfernt alle schon vorhandenen Artikel Beschreibungen aus dem Datenvektor
            // der Tabelle. Dies muss gemacht werden, das sonst alle Einträge die schon
            // vorhanden sind auch nochmal angezeigt werden.
            Vector dataVector = tableModel.getDataVector();
            Vector<Object> lineV = createVectorOfPreviewBills(bpc);
            dataVector.add(lineV);

            // Zeichnet die Tabelle nach hinzufügen des Artikels neu.
            table.repaint();
            tableSP.setViewportView(table);
        }
    }

    public void reinitI18N() {
        border.setTitle(WorkArea.getMessage(Constants.RESIDUAL_DESCRIPTIONS));

        tableModel.setColumnIdentifiers(PREVBILLS_TABLE_ORDER);
    }

    /**
     * Erzeugt einen <code>Vector</code> der Artikel Beschreibung.
     *
     * @param description Eine <code>ArticleDescription</code> die in einen <code>Vector</code> umgewandelt
     *                werden soll.
     * @return Gibt den <code>Vector</code> zurück der aus der <code>ArticleDescription</code> erzeugt wurde.
     */
    private Vector<Object> createVectorOfPreviewBills(BillPreviewCollection bpc) {
        // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
        Vector<Object> lineV = new Vector<Object>();

        // Setzen der Werte eines <code>Article</code> im Zeilen Datenvektor.
        for (I18NItem order : PREVBILLS_TABLE_ORDER) {

            String key = order.getKey();

            if (Constants.DELIVERY_ORDER_NUMBER.equals(key)) {
                lineV.add(bpc.getDeliveryOrderNumber());
            }
            else if (Constants.DELIVERY_ORDER_DATE.equals(key)) {
                lineV.add(bpc.deliveryOrderDate);
            }
            else if (Constants.DELIVERY_ORDER_AMOUNT.equals(key)) {
                lineV.add(bpc.getSum());
            }
        }

        return lineV;
    }

    /**
     * @see ecobill.core.system.Persistable#persist(java.io.OutputStream)
     */
    public void persist(OutputStream outputStream) {

        try {
            table.removeEditor();

            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(table.getColumnModel());
            oos.flush();
            oos.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @see ecobill.core.system.Persistable#persist(java.io.InputStream)
     */
    public void unpersist(InputStream inputStream) {

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(inputStream);

            TableColumnModel columnModel = (TableColumnModel) ois.readObject();

            table.setColumnModel(columnModel);
        }
        catch (IOException ioe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(ioe.getMessage(), ioe);
            }
        }
        catch (ClassNotFoundException cnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(cnfe.getMessage(), cnfe);
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
}





