package ecobill.module.base.ui.textblock;

import ecobill.module.base.ui.component.AbstractTablePanel;
import ecobill.module.base.domain.TextBlock;
import ecobill.module.base.service.BaseService;
import ecobill.core.util.I18NItem;
import ecobill.core.util.IdKeyItem;
import ecobill.core.util.IdValueItem;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.util.Vector;
import java.util.Collection;
import java.util.Collections;
import java.awt.*;
import java.awt.event.*;

// @todo document me!

/**
 * TextBlockTable.
 * <p/>
 * User: rro
 * Date: 10.12.2005
 * Time: 15:25:23
 *
 * @author Roman R&auml;dle
 * @version $Id: TextBlockTable.java,v 1.1 2005/12/11 19:57:33 raedler Exp $
 * @since EcoBill 1.1
 */
public class TextBlockTable extends AbstractTablePanel {

    private TextBlockDialog textBlockDialog;

    private Long textBlockId;

    public TextBlockTable(TextBlockDialog textBlockDialog, BaseService baseService) {
        super(baseService);

        this.textBlockDialog = textBlockDialog;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createPanelBorder()
     */
    protected Border createPanelBorder() {
        return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.INPUT), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createTableColumnOrder()
     */
    protected Vector<I18NItem> createTableColumnOrder() {

        Vector<I18NItem> tableColumnOrder = new Vector<I18NItem>();
        tableColumnOrder.add(new I18NItem(Constants.NAME));
        tableColumnOrder.add(new I18NItem(Constants.TEXT));

        return tableColumnOrder;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#getDataCollection()
     */
    protected Collection getDataCollection() {
        return getBaseService().loadAll(TextBlock.class);
    }

    /**
     * @see AbstractTablePanel#createLineVector(Object)
     */
    protected Vector createLineVector(Object o) {

        // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
        Vector<Object> line = new Vector<Object>();

        if (o instanceof TextBlock) {

            TextBlock textBlock = (TextBlock) o;

            // Setzen der Werte eines <code>BusinessPartner</code> im Zeilen Datenvektor.
            for (I18NItem order : getTableColumnOrder()) {

                String key = order.getKey();

                if (Constants.NAME.equals(key)) {
                    line.add(new IdValueItem(textBlock.getId(), textBlock.getName()));
                }
                else if (Constants.TEXT.equals(key)) {
                    line.add(textBlock.getText());
                }
            }
        }

        return line;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createKeyListeners()
     */
    protected KeyListener[] createKeyListeners() {

        KeyListener[] keyListeners = new KeyListener[1];

        // Ein <code>KeyListener</code> um auf VK_UP und VK_DOWN in der Tabelle
        // geeignet zu reagieren.
        keyListeners[0] = new KeyAdapter() {

            /**
             * @see KeyAdapter#keyPressed(java.awt.event.KeyEvent)
             */
            public void keyPressed(KeyEvent e) {

                // Hole den KeyCode der gedrückten Taste.
                int keyCode = e.getKeyCode();

                // Es soll nur diese Aktion ausgeführt werden wenn entweder Key UP oder Key DOWN
                // gedrückt wurde.
                if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {

                    // Hole die selektierte Reihe.
                    int row = getTable().getSelectedRow();

                    // Korrigiere die Key UP oder die Key DOWN Verschiebung.
                    if (keyCode == KeyEvent.VK_UP) {
                        --row;
                    }
                    else if (keyCode == KeyEvent.VK_DOWN) {
                        ++row;
                    }

                    // Fange die <code>ArrayIndexOutOfBoundsException</code> ab, die auftreten kann wenn
                    // durch die Korrektur eine Zeile zurückgegeben wird, die aber nicht in der Tabelle
                    // besteht.
                    try {
                        textBlockId = ((IdValueItem) getTableModel().getValueAt(row, 0)).getId();
                    }
                    catch (ArrayIndexOutOfBoundsException aioobe) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(aioobe.getMessage(), aioobe);
                        }
                    }

                    textBlockDialog.showTextBlock(textBlockId);
                }
            }
        };

        return keyListeners;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createMouseListeners()
     */
    protected MouseListener[] createMouseListeners() {

        MouseListener[] mouseListeners = new MouseListener[1];

        // Ein <code>MouseAdapter</code> mit einer implementierten mouseDown Methode
        // um auf Klicks auf die Tabelle geeignet zu reagieren.
        mouseListeners[0] = new MouseAdapter() {

            /**
             * @see MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
             */
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1) {
                    int row = getTable().getSelectedRow();

                    textBlockId = ((IdValueItem) getTableModel().getValueAt(row, 0)).getId();

                    textBlockDialog.showTextBlock(textBlockId);
                }
            }
        };

        return mouseListeners;
    }
}
