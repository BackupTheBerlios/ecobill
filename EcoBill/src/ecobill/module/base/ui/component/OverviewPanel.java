package ecobill.module.base.ui.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import javax.swing.*;

import ecobill.module.base.service.BaseService;
import ecobill.core.system.Internationalization;

import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * Created by IntelliJ IDEA.
 * User: gath
 * Date: 09.10.2005
 * Time: 21:00:35
 *
 * @author Sebastian Gath
 * @version $Id: OverviewPanel.java,v 1.4 2005/12/07 18:13:41 raedler Exp $
 * @since EcoBill 1.0
 */
public class OverviewPanel extends JPanel implements Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(OverviewPanel.class);

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

    // Tabelle die auf der rechten Seiten neben dem JSplitPane angezeigt werden soll
    private AbstractTablePanel leftTable;

    // Panel für der Inhalt der rechten Seiten neben dem JSplitPane
    private JPanel panelLeft;

    // Panel für der Inhalt der linken Seiten neben dem JSplitPane
    private JPanel panelRight;

    // Pane der das Fenster teil
    private JSplitPane splitPane;

    public OverviewPanel(BaseService baseService, AbstractTablePanel leftTable, JPanel rightPanel) {

        this.baseService = baseService;
        this.leftTable = leftTable;
        this.panelRight = rightPanel;

        initComponents();
        initLayout();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        splitPane = new JSplitPane();
        panelLeft = new JPanel();
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
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(splitPane, GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                        .addContainerGap())
        );
        overviewLayout.setVerticalGroup(
                overviewLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.TRAILING, overviewLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(overviewLayout.createParallelGroup(GroupLayout.TRAILING)
                                .add(GroupLayout.LEADING, splitPane))
                        .addContainerGap())
        );
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
}


