package ecobill.module.base.ui.deliveryorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.ui.component.VerticalButton;
import ecobill.module.base.ui.component.AbstractTablePanel;
import ecobill.module.base.ui.article.ArticleTable;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.ReduplicatedArticle;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.core.util.FileUtils;
import ecobill.core.util.IdKeyItem;
import ecobill.core.ui.MainFrame;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;

import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.awt.event.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: basti
 * Date: 09.10.2005
 * Time: 21:00:35
 * To change this template use File | Settings | File Templates.
 */
public class OverviewPanel extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {


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

        tabbedPane.setEnabledAt(1, false);

        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
        try {
            if (leftTable instanceof DeliveryOrderTable) {
                leftTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("delivery_order_table")));
            }
            else if (leftTable instanceof ArticleTable)
            {
                leftTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("article_table")));
            }
        }
        catch (FileNotFoundException fnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(fnfe.getMessage(), fnfe);
            }
        }

        reinitI18N();
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {

        if (LOG.isInfoEnabled()) {
            LOG.info("Schlie�e OverviewPanel und speichere die Daten.");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu k�nnen.
        if (leftTable instanceof DeliveryOrderTable) {
            leftTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
        }
        else if (leftTable instanceof ArticleTable) {
            leftTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
        }

    }

    public OverviewPanel (AbstractTablePanel lT, JPanel component) {
        verticalButton = new VerticalButton();
        OverviewPanel.this.leftTable = lT;
        OverviewPanel.this.panelRight = component;
    }

    public void init(){
        initComponents();
        initLayout();
    }

    public void addButtonToVerticalButton(int x, ImageIcon icon, String toolTip, ActionListener aListener) {
        JButton button = verticalButton.getButtonX(x);
        button.setVisible(true);
        button.setIcon(icon);
        button.setToolTipText(toolTip);
        if (aListener != null)
        {
            System.out.println("ActionListener �bergeben");
            button.addActionListener(aListener);
        }
        verticalButton.setButtonX(x,button);
        System.out.println("Button hinzugef�gt");
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        tabbedPane = new JTabbedPane();
       // overview = new JPanel();
        splitPane = new JSplitPane();
        panelLeft = new JPanel();

        overviewPanel = new JPanel();
//        detail = new JPanel();

//        MainFrame mainFrame = (MainFrame) applicationContext.getBean("mainFrame");

//        deliveryOrderPrintPanel = new DeliveryOrderPrintPanel(mainFrame, baseService);

        splitPane.setDividerLocation(200);
        splitPane.setLeftComponent(leftTable);
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        setLayout(new BorderLayout());

        splitPane.setBorder(null);
        splitPane.setDividerLocation(200);
        splitPane.setOneTouchExpandable(true);

        GroupLayout panelLeftLayout = new GroupLayout(panelLeft);
        panelLeft.setLayout(panelLeftLayout);
        panelLeftLayout.setHorizontalGroup(
            panelLeftLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, panelLeftLayout.createSequentialGroup()
                .add(leftTable, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelLeftLayout.setVerticalGroup(
            panelLeftLayout.createParallelGroup(GroupLayout.LEADING)
            .add(leftTable, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        splitPane.setLeftComponent(panelLeft);
        splitPane.setRightComponent(panelRight);

        GroupLayout overviewLayout = new GroupLayout(this);
        this.setLayout(overviewLayout);
        overviewLayout.setHorizontalGroup(
            overviewLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                .addContainerGap()
                .add(verticalButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(splitPane, GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                .addContainerGap())
        );
        overviewLayout.setVerticalGroup(
            overviewLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, overviewLayout.createSequentialGroup()
                .addContainerGap()
                .add(overviewLayout.createParallelGroup(GroupLayout.TRAILING)
                    .add(GroupLayout.LEADING, splitPane)
                    .add(GroupLayout.LEADING, verticalButton, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE))
                .addContainerGap())
        );




        //OverviewPanel.this.add(verticalButton);
        //OverviewPanel.this.add(splitPane);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

    }

    /**
     * Erneuert das <code>TableModel</code> der Left Tabelle.
     */
    public void renewLeftTableModel() {
        leftTable.renewTableModel();
    }

    private AbstractTablePanel leftTable;
    private JPanel overviewPanel;
 //   private JPanel detail;
 //   private JPanel overview;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JPanel panelDataRight;
    private JSplitPane splitPane;
    private JTabbedPane tabbedPane;
    private VerticalButton verticalButton;



/*    private void showAddArticleDialog(Long id) {

        Article article = (Article) baseService.load(Article.class, id);

        JFrame frame = (JFrame) applicationContext.getBean("mainFrame");

        JDialog d = new DeliveryOrderDialog(frame, this, article, true);
        d.setModal(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Dimension size = d.getSize();

        double x = screenSize.getWidth() - size.getWidth();
        double y = screenSize.getHeight() - size.getHeight();

        d.setLocation((int) x / 2, (int) y / 2);

        d.setVisible(true);
    }
  */


    private Long id;

}

