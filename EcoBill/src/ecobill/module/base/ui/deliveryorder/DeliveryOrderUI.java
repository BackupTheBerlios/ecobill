package ecobill.module.base.ui.deliveryorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.ui.component.*;
import ecobill.module.base.ui.textblock.TextBlockDialog;
import ecobill.module.base.domain.*;
import ecobill.core.util.FileUtils;
import ecobill.core.util.IdValueItem;
import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.ui.MainFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * DeliveryOrderUI.
 * <p/>
 * User: rro
 * Date: 05.10.2005
 * Time: 16:57:16
 *
 * @author Roman R&auml;dle
 * @version $Id: DeliveryOrderUI.java,v 1.21 2005/12/15 12:35:57 raedler Exp $
 * @since EcoBill 1.0
 */
public class DeliveryOrderUI extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(DeliveryOrderUI.class);

    /**
     * Der <code>ApplicationContext</code> beinhaltet alle Beans die darin angegeben sind
     * und ermöglicht wahlfreien Zugriff auf diese.
     */
    protected ApplicationContext applicationContext;

    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Der <code>BaseService</code> ist die Business Logik.
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

    /**
     * Enthält die Pfade an denen die bestimmten Objekte serialisiert werden
     * sollen.
     */
    private Properties serializeIdentifiers;

    /**
     * Gibt die Pfade, an denen die bestimmten Objekte serialisiert werden
     * sollen, zurück.
     *
     * @return Die Pfade an denen die bestimmten Objekte serialisiert werden
     *         sollen.
     */
    public Properties getSerializeIdentifiers() {
        return serializeIdentifiers;
    }

    /**
     * Setzt die Pfade, an denen die bestimmten Objekte serialisiert werden
     * sollen.
     *
     * @param serializeIdentifiers Die Pfade an denen die bestimmten Objekte
     *                             serialisiert werden sollen.
     */
    public void setSerializeIdentifiers(Properties serializeIdentifiers) {
        this.serializeIdentifiers = serializeIdentifiers;
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() {

        // Initialisieren der Komponenten und des Layouts.
        initComponents();
        initLayout();

        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
        try {
            deliveryOrderTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("delivery_order_table")));
        }
        catch (FileNotFoundException fnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(fnfe.getMessage());
            }
        }

        reinitI18N();
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {

        if (LOG.isInfoEnabled()) {
            LOG.info("Schließe DeliveryOrderUI und speichere die Daten.");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu können.
        deliveryOrderTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        tabbedPane = new JTabbedPane();
        overview = new JPanel();

        deliveryOrderTable = new DeliveryOrderTable(baseService);
        addressPanel = new AddressPanel();
        formularDataPanel = new FormularDataPanel(Constants.DATA, Constants.DELIVERY_ORDER_NUMBER, Constants.DATE);
        prefixPanel = new TitleBorderedTextAreaPanel(Constants.PREFIX_FREE_TEXT);
        suffixPanel = new TitleBorderedTextAreaPanel(Constants.SUFFIX_FREE_TEXT);
    }

    private JButton viewDeliveryOrderB;

    private JToolBar createDeliveryOrderToolBar() {

        JToolBar toolBar = new JToolBar();

        JButton newDeliveryOrderB = new JButton(new ImageIcon("images/delivery_order_new.png"));
        newDeliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                // Setzt den Lieferschein Anzeigeknopf auf disabled da bei einem neuen Lieferschein
                // noch kein View bereit steht.
                viewDeliveryOrderB.setEnabled(false);

                deliveryOrderTable.clearDataCollection();

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.DELIVERY_ORDER);

                resetInput(numberSequence.getNextNumber());
            }
        });

        JButton okDeliveryOrderB = new JButton(new ImageIcon("images/delivery_order_ok.png"));
        okDeliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                saveOrUpdateDeliveryOrder();

                // Setzt den Lieferschein Anzeigeknopf auf enabled da nach dem Speichern oder Ändern
                // der Lieferschein View bereitsteht.
                viewDeliveryOrderB.setEnabled(true);

                String deliveryOrderNumber = formularDataPanel.getNumber();

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.DELIVERY_ORDER);

                if (numberSequence.compareWithNumber(deliveryOrderNumber) <= -1) {
                    numberSequence.setNumber(deliveryOrderNumber);
                    baseService.saveOrUpdate(numberSequence);
                }

                deliveryOrderTable.getTableModel().getDataVector().removeAllElements();
                resetInput(numberSequence.getNextNumber());
            }
        });

        JButton deleteDeliveryOrderB = new JButton(new ImageIcon("images/delivery_order_delete.png"));
        deleteDeliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton editDeliveryOrderB = new JButton(new ImageIcon("images/delivery_order_edit.png"));
        editDeliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new DeliveryOrderChooseDialog((MainFrame) applicationContext.getBean("mainFrame"), true, DeliveryOrderUI.this, baseService, businessPartnerId, false);
            }
        });

        viewDeliveryOrderB = new JButton(new ImageIcon("images/jasper_view.png"));
        viewDeliveryOrderB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new DeliveryOrderViewerDialog((MainFrame) applicationContext.getBean("mainFrame"), true, baseService, deliveryOrderId);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton prefixTextBlockB = new JButton(new ImageIcon("images/textblock_prefix.png"));
        prefixTextBlockB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new TextBlockDialog((MainFrame) applicationContext.getBean("mainFrame"), true, prefixPanel.getTextArea(), baseService);
            }
        });

        JButton suffixTextBlockB = new JButton(new ImageIcon("images/textblock_suffix.png"));
        suffixTextBlockB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new TextBlockDialog((MainFrame) applicationContext.getBean("mainFrame"), true, suffixPanel.getTextArea(), baseService);
            }
        });

        JButton addExistingArticleB = new JButton(new ImageIcon("images/article_add.png"));
        addExistingArticleB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new DeliveryOrderArticlesDialog((MainFrame) applicationContext.getBean("mainFrame"), DeliveryOrderUI.this, true, baseService);
            }
        });

        JButton addNotExistingArtilceB = new JButton(new ImageIcon("images/article_add_new.png"));
        addNotExistingArtilceB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                showAddArticleDialog(null);
            }
        });

        toolBar.add(newDeliveryOrderB);
        toolBar.add(okDeliveryOrderB);
        toolBar.add(deleteDeliveryOrderB);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(editDeliveryOrderB);
        toolBar.add(viewDeliveryOrderB);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(prefixTextBlockB);
        toolBar.add(suffixTextBlockB);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(addExistingArticleB);
        toolBar.add(addNotExistingArtilceB);

        return toolBar;
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        setLayout(new BorderLayout());

        overview.setLayout(new BorderLayout());
        JPanel createDeliveryOrderPanel = new JPanel();

        GroupLayout createDeliveryOrderPanelLayout = new GroupLayout(createDeliveryOrderPanel);
        createDeliveryOrderPanel.setLayout(createDeliveryOrderPanelLayout);
        createDeliveryOrderPanelLayout.setHorizontalGroup(
            createDeliveryOrderPanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, createDeliveryOrderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(createDeliveryOrderPanelLayout.createParallelGroup(GroupLayout.TRAILING)
                    .add(GroupLayout.LEADING, deliveryOrderTable, GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .add(GroupLayout.LEADING, createDeliveryOrderPanelLayout.createSequentialGroup()
                        .add(createDeliveryOrderPanelLayout.createParallelGroup(GroupLayout.TRAILING)
                            .add(GroupLayout.LEADING, prefixPanel, 0, 300, Short.MAX_VALUE)
                            .add(addressPanel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(createDeliveryOrderPanelLayout.createParallelGroup(GroupLayout.LEADING)
                            .add(suffixPanel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .add(formularDataPanel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))))
                .add(10, 10, 10))
        );
        createDeliveryOrderPanelLayout.setVerticalGroup(
            createDeliveryOrderPanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, createDeliveryOrderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(createDeliveryOrderPanelLayout.createParallelGroup(GroupLayout.TRAILING, false)
                    .add(addressPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(formularDataPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(createDeliveryOrderPanelLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(prefixPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .add(suffixPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(deliveryOrderTable, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );

        overview.add(createDeliveryOrderToolBar(), BorderLayout.NORTH);
        overview.add(createDeliveryOrderPanel, BorderLayout.CENTER);

        tabbedPane.addTab(WorkArea.getMessage(Constants.OVERVIEW), overview);

        /*
        JButton viewDeliveryOrderB = new JButton(new ImageIcon("images/jasper_view.png"));
        viewDeliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             *
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = orderTable.getTable().getSelectedRow();

                    if (row != -1) {
                        deliveryOrderViewerDialogOverview.doJasper(((IdValueItem) orderTable.getTable().getValueAt(row,0)).getId());
                    }
                    else {
                        JOptionPane.showMessageDialog(DeliveryOrderUI.this, "Datensatz auswählen", "Nachricht", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton deleteDeliveryOrder = new JButton(new ImageIcon("images/delivery_order_delete.png"));
        deleteDeliveryOrder.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             *
            public void actionPerformed(ActionEvent e) {
                Long deliveryOrderId = orderTable.getIdOfSelectedRow();

                DeliveryOrder deliveryOrder = (DeliveryOrder) baseService.load(DeliveryOrder.class, deliveryOrderId);
                baseService.delete(deliveryOrder);

                orderTable.renewTableModel();
                orderTable.repaint();

                if (deliveryOrderViewerDialogOverview != null) {
                    deliveryOrderViewerDialogOverview.clearViewerPanel();
                }
            }
        });
        */

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        tabbedPane.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));

        addressPanel.reinitI18N();
        deliveryOrderTable.reinitI18N();

        ((TitledBorder) deliveryOrderTable.getPanelBorder()).setTitle(WorkArea.getMessage(Constants.DELIVERY_ORDER));

        /* TODO: repair me!!!
        verticalButton.reinitI18N();
        verticalButton.getButton1().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON1_TOOLTIP));
        verticalButton.getButton2().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON2_TOOLTIP));
        verticalButton.getButton3().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON3_TOOLTIP));
        verticalButton.getButton4().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON4_TOOLTIP));
        */
    }

    private JTabbedPane tabbedPane;
    private JPanel overview;

    private AddressPanel addressPanel;
    private DeliveryOrderTable deliveryOrderTable;
    private FormularDataPanel formularDataPanel;
    private TitleBorderedTextAreaPanel prefixPanel;
    private TitleBorderedTextAreaPanel suffixPanel;

    public void setBusinessPartner(BusinessPartner businessPartner) {
        addressPanel.setBusinessPartner(businessPartner);
    }

    private void showAddArticleDialog(Long id) {

        JFrame frame = (JFrame) applicationContext.getBean("mainFrame");

        Article article = null;
        if (id != null) {
            article = (Article) baseService.load(Article.class, id);
        }

        new DeliveryOrderArticleDialog(frame, this, article, true);
    }

    public void addReduplicatedArticle(ReduplicatedArticle reduplicatedArticle) {
        // TODO: hier liegt der fehler für erneutes erscheinen des artikels.

        deliveryOrderTable.addReduplicatedArticle(reduplicatedArticle);
    }

    private void saveOrUpdateDeliveryOrder() {

        DeliveryOrder deliveryOrder = new DeliveryOrder();

        deliveryOrder.setBusinessPartner(addressPanel.getBusinessPartner());
        deliveryOrder.setCharacterisationType("delivery_order");
        deliveryOrder.setDeliveryOrderNumber(formularDataPanel.getNumber());
        deliveryOrder.setDeliveryOrderDate(formularDataPanel.getDate());
        deliveryOrder.setPrefixFreetext(prefixPanel.getTextArea().getText());
        deliveryOrder.setSuffixFreetext(suffixPanel.getTextArea().getText());
        deliveryOrder.setPreparedBill(false);

        Vector dataVector = ((DefaultTableModel) deliveryOrderTable.getTable().getModel()).getDataVector();

        ReduplicatedArticle article;

        Enumeration lines = dataVector.elements();
        for (int i = 1; lines.hasMoreElements(); i++) {

            Vector line = (Vector) lines.nextElement();

            article = new ReduplicatedArticle();

            article.setArticleNumber((String) ((IdValueItem) line.get(0)).getValue());
            article.setDescription((String) line.get(1));
            article.setQuantity((Double) line.get(2));
            article.setUnit((String) line.get(3));
            article.setPrice((Double) line.get(4));

            //ReduplicatedArticle reduplicatedArticle = (ReduplicatedArticle) line.get(6);
            article.setOrderPosition(i);

            deliveryOrder.addArticle(article);
        }

        baseService.saveOrUpdate(deliveryOrder);

        this.deliveryOrderId = deliveryOrder.getId();

        deliveryOrderTable.clearDataCollection();
    }

    public void resetInput(String deliveryOrderNumber) {

        formularDataPanel.setNumber(deliveryOrderNumber);
        formularDataPanel.setDate(new Date());
        prefixPanel.getTextArea().setText("");
        suffixPanel.getTextArea().setText("");

        JTable table = deliveryOrderTable.getTable();
        ((DefaultTableModel) table.getModel()).getDataVector().removeAllElements();
        table.updateUI();
    }

    private Long businessPartnerId;

    // TODO: Brauchen wir das noch?!? Reicht doch eigentlich setBusinessPartner oder umgekehrt.
    public void setActualBusinessPartnerId(Long actualBusinessPartnerId) {
        businessPartnerId = actualBusinessPartnerId;
    }

    private Long deliveryOrderId;

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {

        // Setzt den Lieferschein Anzeigeknopf auf enabled da nach dem Aufruf eines gespeicherten
        // Lieferscheines der View dafür bereitsteht.
        viewDeliveryOrderB.setEnabled(true);

        this.deliveryOrderId = deliveryOrder.getId();

        formularDataPanel.setNumber(deliveryOrder.getDeliveryOrderNumber());
        formularDataPanel.setDate(deliveryOrder.getDeliveryOrderDate());
        prefixPanel.getTextArea().setText(deliveryOrder.getPrefixFreetext());
        suffixPanel.getTextArea().setText(deliveryOrder.getSuffixFreetext());

        deliveryOrderTable.setDataCollection(deliveryOrder.getArticles());
    }
}