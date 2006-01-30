package ecobill.module.base.ui.deliveryorder;

import ecobill.module.base.ui.article.ArticleTable;
import ecobill.module.base.service.BaseService;
import ecobill.core.util.ComponentUtils;

import javax.swing.*;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// @todo document me!

/**
 * DeliveryOrderArticlesDialog.
 * <p/>
 * User: rro
 * Date: 10.12.2005
 * Time: 22:49:46
 *
 * @author Roman R&auml;dle
 * @version $Id: DeliveryOrderArticlesDialog.java,v 1.2 2006/01/30 23:43:14 raedler Exp $
 * @since EcoBill 1.1
 */
public class DeliveryOrderArticlesDialog extends JDialog {

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
     * Der Eigentümer auf den der modale Dialog angwandt werden soll.
     */
    private Frame owner;

    /**
     * Das Lieferschein User Interface um den Artikel später hinzufügen zu können.
     */
    private DeliveryOrderUI deliveryOrderUI;

    /**
     * TODO: document me!!!
     */
    private ArticleTable articleTable;
    private JButton chooseB;
    private JButton closeB;

    /**
     * TODO: document me!!!
     *
     * @param baseService
     */
    public DeliveryOrderArticlesDialog(Frame owner, DeliveryOrderUI deliveryOrderUI, boolean modal, BaseService baseService) {
        super(owner, modal);

        this.baseService = baseService;
        this.owner = owner;
        this.deliveryOrderUI = deliveryOrderUI;

        // Initialisiert die Komponenten.
        initComponents();

        // Initialisiert das Layout.
        initLayout();

        pack();

        // Zentriert den <code>JDialog</code> auf dem Bildschirm.
        ComponentUtils.centerComponentOnScreen(this);

        setVisible(true);
    }

    /**
     * TODO: document me!!!
     */
    private void initComponents() {
        articleTable = new ArticleTable(baseService);
        closeB = new JButton();
        chooseB = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        closeB.setText("Schlie\u00dfen");
        closeB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                DeliveryOrderArticlesDialog.this.dispose();
            }
        });

        chooseB.setText("Ausw\u00e4hlen");
        chooseB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                DeliveryOrderArticlesDialog.this.dispose();
                new DeliveryOrderArticleDialog(owner, deliveryOrderUI, articleTable.getSelectedArticle(), true);
            }
        });
    }

    /**
     * TODO: document me!!!
     */
    private void initLayout() {

        GroupLayout layout = new GroupLayout(getContentPane());

        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                                .addContainerGap()
                                .add(articleTable, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                                .addContainerGap())
                        .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(285, Short.MAX_VALUE)
                        .add(chooseB)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(closeB)
                        .addContainerGap())
        );

        layout.linkSize(new Component[]{chooseB, closeB}, GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(articleTable, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(closeB)
                                .add(chooseB))
                        .addContainerGap())
        );
    }
}
