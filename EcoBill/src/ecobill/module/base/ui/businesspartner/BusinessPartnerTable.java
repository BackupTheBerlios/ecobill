package ecobill.module.base.ui.businesspartner;

import ecobill.core.util.I18NItem;
import ecobill.core.util.IdKeyItem;
import ecobill.core.util.IdValueItem;
import ecobill.core.system.Constants;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Internationalization;
import ecobill.core.system.Persistable;
import ecobill.module.base.ui.component.AbstractTablePanel;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.*;

import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumnModel;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Die <code>BusinessPartnerTable</code> ist zur Anzeige und �nderung von Kundendaten bereit.
 * <p/>
 * User: rro
 * Date: 28.09.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: BusinessPartnerTable.java,v 1.6 2006/01/29 23:16:45 raedler Exp $
 * @since EcoBill 1.0
 */
public class BusinessPartnerTable extends AbstractTablePanel {

    /**
     * Die <code>BusinessPartnerUI</code> um den Gesch�ftspartner anzeigen zu k�nnen.
     */
    private BusinessPartnerUI businessPartnerUI;

    /**
     * Die id des <code>Article</code> der in der aktuell ausgew�hlt ist.
     */
    private Long businessPartnerId;

    /**
     * Creates new form BusinessPartnerTable
     */
    public BusinessPartnerTable(BusinessPartnerUI businessPartnerUI, BaseService baseService) {
        super(baseService, false);

        this.businessPartnerUI = businessPartnerUI;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createPanelBorder()
     */
    protected Border createPanelBorder() {
        return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.BUSINESS_PARTNER), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createTableColumnOrder()
     */
    protected Vector<I18NItem> createTableColumnOrder() {

        Vector<I18NItem> tableColumnOrder = new Vector<I18NItem>();
        tableColumnOrder.add(new I18NItem(Constants.CUSTOMER_NUMBER));
        tableColumnOrder.add(new I18NItem(Constants.TITLE));
        tableColumnOrder.add(new I18NItem(Constants.ACADEMIC_TITLE));
        tableColumnOrder.add(new I18NItem(Constants.FIRSTNAME));
        tableColumnOrder.add(new I18NItem(Constants.LASTNAME));
        tableColumnOrder.add(new I18NItem(Constants.STREET));
        tableColumnOrder.add(new I18NItem(Constants.ZIP_CODE));
        tableColumnOrder.add(new I18NItem(Constants.CITY));
        tableColumnOrder.add(new I18NItem(Constants.COUNTRY));
        tableColumnOrder.add(new I18NItem(Constants.COUNTY));
        tableColumnOrder.add(new I18NItem(Constants.FIRM));
        tableColumnOrder.add(new I18NItem(Constants.BRANCH));
        tableColumnOrder.add(new I18NItem(Constants.PHONE));
        tableColumnOrder.add(new I18NItem(Constants.FAX));
        tableColumnOrder.add(new I18NItem(Constants.EMAIL));

        return tableColumnOrder;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#getDataCollection()
     */
    protected Collection getDataCollection() {
        return getBaseService().loadAll(BusinessPartner.class);
    }

    /**
     * @see AbstractTablePanel#createLineVector(Object)
     */
    protected Vector createLineVector(Object o) {

        // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
        Vector<Object> line = new Vector<Object>();

        if (o instanceof BusinessPartner) {

            BusinessPartner businessPartner = (BusinessPartner) o;

            // Setzen der Werte eines <code>BusinessPartner</code> im Zeilen Datenvektor.
            for (I18NItem order : getTableColumnOrder()) {

                String key = order.getKey();

                if (Constants.CUSTOMER_NUMBER.equals(key)) {
                    line.add(new IdValueItem(businessPartner.getId(), businessPartner.getCustomerNumber()));
                }
                else if (Constants.TITLE.equals(key)) {
                    line.add(businessPartner.getPerson().getTitle());
                }
                else if (Constants.ACADEMIC_TITLE.equals(key)) {
                    line.add(businessPartner.getPerson().getAcademicTitle());
                }
                else if (Constants.FIRSTNAME.equals(key)) {
                    line.add(businessPartner.getPerson().getFirstname());
                }
                else if (Constants.LASTNAME.equals(key)) {
                    line.add(businessPartner.getPerson().getLastname());
                }
                else if (Constants.STREET.equals(key)) {
                    line.add(businessPartner.getAddress().getStreet());
                }
                else if (Constants.ZIP_CODE.equals(key)) {
                    line.add(businessPartner.getAddress().getZipCode());
                }
                else if (Constants.CITY.equals(key)) {
                    line.add(businessPartner.getAddress().getCity());
                }
                else if (Constants.COUNTRY.equals(key)) {
                    line.add(businessPartner.getAddress().getCountry());
                }
                else if (Constants.COUNTY.equals(key)) {
                    line.add(businessPartner.getAddress().getCounty());
                }
                else if (Constants.FIRM.equals(key)) {
                    line.add(businessPartner.getCompanyName());
                }
                else if (Constants.BRANCH.equals(key)) {
                    line.add(businessPartner.getCompanyBranch());
                }
                else if (Constants.PHONE.equals(key)) {
                    line.add(businessPartner.getPerson().getPhone());
                }
                else if (Constants.FAX.equals(key)) {
                    line.add(businessPartner.getPerson().getFax());
                }
                else if (Constants.EMAIL.equals(key)) {
                    line.add(businessPartner.getPerson().getEmail());
                }
            }
        }

        return line;
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {
        super.reinitI18N();

        ((TitledBorder) getPanelBorder()).setTitle(WorkArea.getMessage(Constants.BUSINESS_PARTNER));
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

                // Hole den KeyCode der gedr�ckten Taste.
                int keyCode = e.getKeyCode();

                // Es soll nur diese Aktion ausgef�hrt werden wenn entweder Key UP oder Key DOWN
                // gedr�ckt wurde.
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
                    // durch die Korrektur eine Zeile zur�ckgegeben wird, die aber nicht in der Tabelle
                    // besteht.
                    try {
                        businessPartnerId = ((IdKeyItem) getTableModel().getValueAt(row, 0)).getId();
                    }
                    catch (ArrayIndexOutOfBoundsException aioobe) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(aioobe.getMessage(), aioobe);
                        }
                    }

                    businessPartnerUI.showBusinessPartner(businessPartnerId);
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

                    businessPartnerId = ((IdValueItem) getTableModel().getValueAt(row, 0)).getId();

                    businessPartnerUI.showBusinessPartner(businessPartnerId);
                }
            }
        };

        return mouseListeners;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createTableModelListeners()
     */
    protected TableModelListener[] createTableModelListeners() {
                                       
        TableModelListener[] tableModelListeners = new TableModelListener[1];

        /*
            // Ein <code>TableModelListener</code> um die �nderungen der Tabellendaten in der
            // Datenbank zu persistieren.
            tableModelListeners[0] = new TableModelListener() {

            /**
             * @see TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
             *
            public void tableChanged(TableModelEvent e) {

                // �berpr�fe das <code>TableModelEvent</code> auf UPDATE.
                if (e.getType() == TableModelEvent.UPDATE) {

                    // Die Reihe und Spalte des zu ver�ndernden Wertes.
                    int row = e.getFirstRow();
                    int col = e.getColumn();

                    // Sicherheitscheck.
                    if (row > -1 && row < tableModel.getRowCount()) {

                        // Lade betreffenden Artikel von der Datenbank.
                        Article article = (Article) baseService.load(Article.class, articleId);

                        // Ver�nderter Wert.
                        Object value = tableModel.getValueAt(row, col);

                        // Der �bersetzte Name der Spalte um herauszufinden welcher Wert �berhaupt
                        // ge�ndert werden muss.
                        String columnName = tableModel.getColumnName(col);

                        if (columnName.equals(WorkArea.getMessage(Constants.ARTICLE_NR))) {
                            article.setArticleNumber((String) value);
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.UNIT))) {
                            article.setUnit((SystemUnit) value);
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.SINGLE_PRICE))) {
                            article.setPrice(Double.valueOf((String) value));
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.DESCRIPTION))) {
                            try {
                                article.getLocalizedArticleDescription().setDescription((String) value);
                            }
                            catch (LocalizerException le) {
                                ArticleDescription articleDescription = new ArticleDescription();

                                SystemLocale systemLocale = baseService.getSystemLocaleByLocale(WorkArea.getLocale());

                                articleDescription.setDescription((String) value);
                                articleDescription.setSystemLocale(systemLocale);

                                article.addArticleDescription(articleDescription);
                            }
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.IN_STOCK))) {
                            article.setInStock(Double.valueOf((String) value));
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.BUNDLE_UNIT))) {
                            article.setBundleUnit((SystemUnit) value);
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.BUNDLE_CAPACITY))) {
                            article.setBundleCapacity(Double.valueOf((String) value));
                        }

                        baseService.saveOrUpdate(article);

                        if (LOG.isDebugEnabled()) {
                            //LOG.debug("In der Spalte [" + col + "] und Zeile [" + row + "] wurde f�r den Artikel [id=\"" + articleId + "\"] der Wert auf \"" + tableModel.getValueAt(row, col) + "\" ge�ndert.");
                        }

                        renewArticleTableModel();
                        articleUI.showArticle(articleId);
                    }
                }
            }
        });
        */

        return tableModelListeners;
    }
}
