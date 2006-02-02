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
 * @version $Id: DeliveryOrderUI.java,v 1.27 2006/02/02 22:18:27 raedler Exp $
 * @since EcoBill 1.0
 */
public class DeliveryOrderUI extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {

    // Icons used in this delivery order user interface.
    private final Icon ICON_NEW_DELIVERY_ORDER = new ImageIcon("images/deliveryorder/delivery_order_new.png");
    private final Icon ICON_SAVE_DELIVERY_ORDER = new ImageIcon("images/deliveryorder/delivery_order_save.png");
    private final Icon ICON_DELETE_DELIVERY_ORDER = new ImageIcon("images/deliveryorder/delivery_order_delete.png");
    private final Icon ICON_OPEN_EDITABLE_DELIVERY_ORDER = new ImageIcon("images/deliveryorder/delivery_order_open_editable.png");
    private final Icon ICON_OPEN_DELIVERY_ORDER = new ImageIcon("images/deliveryorder/delivery_order_open_all.png");
    private final Icon ICON_VIEW_DELIVERY_ORDER = new ImageIcon("images/deliveryorder/delivery_order_view.png");
    private final Icon ICON_PREFIX_TEXT_BLOCK = new ImageIcon("images/deliveryorder/textblock_prefix.png");
    private final Icon ICON_SUFFIX_TEXT_BLOCK = new ImageIcon("images/deliveryorder/textblock_suffix.png");
    private final Icon ICON_ADD_EXISTING_ARTICLE = new ImageIcon("images/deliveryorder/article_add.png");
    private final Icon ICON_ADD_NOT_EXISTING_ARTICLE = new ImageIcon("images/deliveryorder/article_add_new.png");

    // Buttons used in this delivery order user interface.
    private JToolBarButton newDeliveryOrderB = new JToolBarButton(ICON_NEW_DELIVERY_ORDER);
    private JToolBarButton saveDeliveryOrderB = new JToolBarButton(ICON_SAVE_DELIVERY_ORDER);
    private JToolBarButton deleteDeliveryOrderB = new JToolBarButton(ICON_DELETE_DELIVERY_ORDER);
    private JToolBarButton openEditableDeliveryOrderB = new JToolBarButton(ICON_OPEN_EDITABLE_DELIVERY_ORDER);
    private JToolBarButton openDeliveryOrderB = new JToolBarButton(ICON_OPEN_DELIVERY_ORDER);
    private JToolBarButton viewDeliveryOrderB = new JToolBarButton(ICON_VIEW_DELIVERY_ORDER);
    private JToolBarButton prefixTextBlockB = new JToolBarButton(ICON_PREFIX_TEXT_BLOCK);
    private JToolBarButton suffixTextBlockB = new JToolBarButton(ICON_SUFFIX_TEXT_BLOCK);
    private JToolBarButton addExistingArticleB = new JToolBarButton(ICON_ADD_EXISTING_ARTICLE);
    private JToolBarButton addNotExistingArtilceB = new JToolBarButton(ICON_ADD_NOT_EXISTING_ARTICLE);

    /**
     * In diesem <code>Log</code> k�nnen Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben k�nnen in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(DeliveryOrderUI.class);

    /**
     * Der <code>ApplicationContext</code> beinhaltet alle Beans die darin angegeben sind
     * und erm�glicht wahlfreien Zugriff auf diese.
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
     * Gibt den <code>BaseService</code> und somit die Business Logik zur�ck.
     *
     * @return Der <code>BaseService</code>.
     */
    public BaseService getBaseService() {
        return baseService;
    }

    /**
     * Setzt den <code>BaseService</code> der die komplette Business Logik enth�lt
     * um bspw Daten aus der Datenbank zu laden und dorthin auch wieder abzulegen.
     *
     * @param baseService Der <code>BaseService</code>.
     */
    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    /**
     * Enth�lt die Pfade an denen die bestimmten Objekte serialisiert werden
     * sollen.
     */
    private Properties serializeIdentifiers;

    /**
     * Gibt die Pfade, an denen die bestimmten Objekte serialisiert werden
     * sollen, zur�ck.
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
            deliveryOrderArticleTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("delivery_order_table")));
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
            LOG.info("Schlie\u00dfe DeliveryOrderUI und speichere die Daten.");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu k�nnen.
        deliveryOrderArticleTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        tabbedPane = new JTabbedPane();
        overview = new JPanel();

        deliveryOrderArticleTable = new DeliveryOrderArticleTable(baseService);
        addressPanel = new AddressPanel();
        formularDataPanel = new FormularDataPanel(Constants.DATA, Constants.DELIVERY_ORDER_NUMBER, Constants.DATE);
        prefixPanel = new TitleBorderedTextAreaPanel(Constants.PREFIX_FREE_TEXT);
        suffixPanel = new TitleBorderedTextAreaPanel(Constants.SUFFIX_FREE_TEXT);
    }

    /**
     * TODO: document me!!!
     *
     * @return
     */
    private JToolBar createDeliveryOrderToolBar() {

        JToolBar toolBar = new JToolBar();

        newDeliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                // Setzt den Lieferschein Anzeigeknopf auf disabled da bei einem neuen Lieferschein
                // noch kein View bereit steht.
                viewDeliveryOrderB.setEnabled(false);

                deliveryOrderArticleTable.clearDataCollection();

                resetInput();
            }
        });

        saveDeliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                saveOrUpdateDeliveryOrder();

                // Setzt den Lieferschein Anzeigeknopf auf enabled da nach dem Speichern oder aendern
                // der Lieferschein View bereitsteht.
                viewDeliveryOrderB.setEnabled(true);

                String deliveryOrderNumber = formularDataPanel.getNumber();

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.DELIVERY_ORDER);

                if (numberSequence.compareWithNumber(deliveryOrderNumber) <= -1) {
                    numberSequence.setNumber(deliveryOrderNumber);
                    baseService.saveOrUpdate(numberSequence);
                }
            }
        });

        deleteDeliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                int selection = JOptionPane.showConfirmDialog((MainFrame) applicationContext.getBean("mainFrame"), "Lieferschein wirklich l\u00f6schen?", "L\u00f6schen Abfrage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (JOptionPane.YES_OPTION == selection) {
                    baseService.delete(deliveryOrder);

                    resetInput();
                }
            }
        });

        openEditableDeliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new DeliveryOrderChooseDialog((MainFrame) applicationContext.getBean("mainFrame"), true, DeliveryOrderUI.this, baseService, businessPartner.getId(), true);
            }
        });

        openDeliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new DeliveryOrderChooseDialog((MainFrame) applicationContext.getBean("mainFrame"), true, DeliveryOrderUI.this, baseService, businessPartner.getId(), false);
            }
        });

        viewDeliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    new DeliveryOrderViewerDialog((MainFrame) applicationContext.getBean("mainFrame"), true, baseService, deliveryOrder.getId());
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        prefixTextBlockB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new TextBlockDialog((MainFrame) applicationContext.getBean("mainFrame"), true, prefixPanel.getTextArea(), baseService);
            }
        });

        suffixTextBlockB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new TextBlockDialog((MainFrame) applicationContext.getBean("mainFrame"), true, suffixPanel.getTextArea(), baseService);
            }
        });

        addExistingArticleB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new DeliveryOrderArticlesDialog((MainFrame) applicationContext.getBean("mainFrame"), DeliveryOrderUI.this, true, baseService);
            }
        });

        addNotExistingArtilceB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                showAddArticleDialog(null);
            }
        });

        toolBar.add(newDeliveryOrderB);
        toolBar.add(saveDeliveryOrderB);
        toolBar.add(deleteDeliveryOrderB);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(openEditableDeliveryOrderB);
        toolBar.add(openDeliveryOrderB);
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
                                .add(GroupLayout.LEADING, deliveryOrderArticleTable, GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
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
                        .add(deliveryOrderArticleTable, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                        .addContainerGap())
        );

        overview.add(createDeliveryOrderToolBar(), BorderLayout.NORTH);
        overview.add(createDeliveryOrderPanel, BorderLayout.CENTER);

        tabbedPane.addTab(WorkArea.getMessage(Constants.OVERVIEW), overview);

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        tabbedPane.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));

        ((TitledBorder) deliveryOrderArticleTable.getPanelBorder()).setTitle(WorkArea.getMessage(Constants.DELIVERY_ORDER));

        // Tooltips of each button in this delivery order user interface.
        newDeliveryOrderB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.deliveryorder.DeliveryOrderUI.newDeliveryOrderB"));
        saveDeliveryOrderB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.deliveryorder.DeliveryOrderUI.saveDeliveryOrderB"));
        deleteDeliveryOrderB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.deliveryorder.DeliveryOrderUI.deleteDeliveryOrderB"));
        openEditableDeliveryOrderB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.deliveryorder.DeliveryOrderUI.openEditableDeliveryOrderB"));
        openDeliveryOrderB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.deliveryorder.DeliveryOrderUI.openDeliveryOrderB"));
        viewDeliveryOrderB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.deliveryorder.DeliveryOrderUI.viewDeliveryOrderB"));
        prefixTextBlockB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.deliveryorder.DeliveryOrderUI.prefixTextBlockB"));
        suffixTextBlockB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.deliveryorder.DeliveryOrderUI.suffixTextBlockB"));
        addExistingArticleB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.deliveryorder.DeliveryOrderUI.addExistingArticleB"));
        addNotExistingArtilceB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.deliveryorder.DeliveryOrderUI.addNotExistingArticleB"));

        // Cascading reinitialization of known <code>Internationalization</code>
        // subclasses.
        addressPanel.reinitI18N();
        deliveryOrderArticleTable.reinitI18N();
    }

    private JTabbedPane tabbedPane;
    private JPanel overview;

    private AddressPanel addressPanel;
    private DeliveryOrderArticleTable deliveryOrderArticleTable;
    private FormularDataPanel formularDataPanel;
    private TitleBorderedTextAreaPanel prefixPanel;
    private TitleBorderedTextAreaPanel suffixPanel;

    private BusinessPartner businessPartner;

    public void setBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartner = businessPartner;
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

        deliveryOrderArticleTable.addReduplicatedArticle(reduplicatedArticle);
    }

    private void saveOrUpdateDeliveryOrder() {

        deliveryOrder.setBusinessPartner(addressPanel.getBusinessPartner());
        deliveryOrder.setCharacterisationType("delivery_order");
        deliveryOrder.setDeliveryOrderNumber(formularDataPanel.getNumber());
        deliveryOrder.setDeliveryOrderDate(formularDataPanel.getDate());
        deliveryOrder.setPrefixFreetext(prefixPanel.getTextArea().getText());
        deliveryOrder.setSuffixFreetext(suffixPanel.getTextArea().getText());

        Vector dataVector = deliveryOrderArticleTable.getTableModel().getDataVector();

        ReduplicatedArticle article;

        Enumeration lines = dataVector.elements();
        for (int i = 1; lines.hasMoreElements(); i++) {

            Vector line = (Vector) lines.nextElement();

            article = (ReduplicatedArticle) ((IdValueItem) line.get(0)).getOriginalValue();

            article.setOrderPosition(i);
        }

        baseService.saveOrUpdate(deliveryOrder);
    }

    public void resetInput() {

        NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.DELIVERY_ORDER);

        formularDataPanel.setNumber(numberSequence.getNextNumber());
        formularDataPanel.setDate(new Date());
        prefixPanel.getTextArea().setText("");
        suffixPanel.getTextArea().setText("");

        JTable table = deliveryOrderArticleTable.getTable();
        ((DefaultTableModel) table.getModel()).getDataVector().removeAllElements();
        table.updateUI();
    }

    private DeliveryOrder deliveryOrder;

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {

        // Setzt den Lieferschein Anzeigeknopf auf enabled da nach dem Aufruf eines gespeicherten
        // Lieferscheines der View daf�r bereitsteht.
        viewDeliveryOrderB.setEnabled(true);

        this.deliveryOrder = deliveryOrder;
        deliveryOrderArticleTable.setDeliveryOrder(deliveryOrder);

        if (deliveryOrder.getId() == null) {
            resetInput();
            return;
        }

        formularDataPanel.setNumber(deliveryOrder.getDeliveryOrderNumber());
        formularDataPanel.setDate(deliveryOrder.getDeliveryOrderDate());
        prefixPanel.getTextArea().setText(deliveryOrder.getPrefixFreetext());
        suffixPanel.getTextArea().setText(deliveryOrder.getSuffixFreetext());
    }

    public Integer getNextOrderPosition() {
        return deliveryOrderArticleTable.getTableModel().getRowCount() + 1;
    }
}