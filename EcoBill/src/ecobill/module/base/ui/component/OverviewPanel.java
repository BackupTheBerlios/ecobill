package ecobill.module.base.ui.component;

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
import ecobill.module.base.ui.deliveryorder.DeliveryOrderUI;
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
 * TODO: document me!!!
 * <p/>
 * Created by IntelliJ IDEA.
 * User: basti
 * Date: 09.10.2005
 * Time: 21:00:35
 * To change this template use File | Settings | File Templates.
 */
public class OverviewPanel extends JPanel implements Internationalization {

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

    public OverviewPanel(BaseService baseService, AbstractTablePanel leftTable, JPanel rightPanel) {
        this.baseService = baseService;
        this.leftTable = leftTable;
        this.panelRight = rightPanel;

        initComponents();
        initLayout();
    }

    public void addButtonToVerticalButton(int x, ImageIcon icon, String toolTip, ActionListener aListener) {
        JButton button = verticalButton.getButtonX(x);
        button.setVisible(true);
        button.setIcon(icon);
        button.setToolTipText(toolTip);
        if (aListener != null) {
            button.addActionListener(aListener);
        }
        verticalButton.setButtonX(x, button);
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        verticalButton = new VerticalButton();

        splitPane = new JSplitPane();
        panelLeft = new JPanel();
        splitPane.setDividerLocation(200);
        splitPane.setLeftComponent(leftTable);

        verticalButton.getButton3().setVisible(true);
        verticalButton.getButton3().setIcon(new ImageIcon("images/delivery_order_delete.png"));
        verticalButton.getButton3().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                Long deliveryOrderId = leftTable.getIdOfSelectedRow();

                DeliveryOrder deliveryOrder = (DeliveryOrder) baseService.load(DeliveryOrder.class, deliveryOrderId);
                baseService.delete(deliveryOrder);

                leftTable.renewTableModel();

                if (panelRight instanceof AbstractJasperPrintPanel) {
                    ((AbstractJasperPrintPanel) panelRight).clearViewerPanel();
                }
            }
        });
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
    private JPanel panelLeft;
    private JPanel panelRight;
    private JSplitPane splitPane;
    private VerticalButton verticalButton;

    public VerticalButton getVerticalButton() {
        return verticalButton;
    }
}


