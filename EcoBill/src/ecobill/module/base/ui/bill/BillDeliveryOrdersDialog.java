package ecobill.module.base.ui.bill;

import ecobill.module.base.ui.deliveryorder.DeliveryOrderTableWithCB;
import ecobill.module.base.service.BaseService;
import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.ComponentUtils;

import javax.swing.*;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// @todo document me!

/**
 * BillDeliveryOrdersDialog.
 * <p/>
 * User: rro
 * Date: 11.12.2005
 * Time: 00:48:35
 *
 * @author Roman R&auml;dle
 * @version $Id: BillDeliveryOrdersDialog.java,v 1.3 2006/01/30 23:43:14 raedler Exp $
 * @since EcoBill 1.1
 */
public class BillDeliveryOrdersDialog extends JDialog implements Internationalization {

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
     * TODO: document me!!!
     */
    private BillUI billUI;

    /**
     * TODO: document me!!!
     */
    private Long businessPartnerId;

    private DeliveryOrderTableWithCB deliveryOrderTableWithCB;
    private JButton closeB = new JButton();
    private JButton applyB = new JButton();

    /**
     * TODO: document me!!!
     *
     * @param baseService
     * @param businessPartnerId
     */
    public BillDeliveryOrdersDialog(Frame owner, boolean modal, BillUI billUI, BaseService baseService, Long businessPartnerId) {
        super(owner, modal);

        this.billUI = billUI;
        this.baseService = baseService;
        this.businessPartnerId = businessPartnerId;

        // Initialisiert die Komponenten.
        initComponents();

        // Initialisiert das Layout.
        initLayout();

        reinitI18N();

        pack();

        ComponentUtils.centerComponentOnScreen(this);

        setVisible(true);
    }

    /**
     * TODO: document me!!!
     */
    private void initComponents() {
        deliveryOrderTableWithCB = new DeliveryOrderTableWithCB(businessPartnerId, baseService);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        closeB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                BillDeliveryOrdersDialog.this.dispose();
            }
        });

        applyB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                billUI.saveOrUpdateBill(deliveryOrderTableWithCB);
                BillDeliveryOrdersDialog.this.dispose();
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
                                .add(deliveryOrderTableWithCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(289, Short.MAX_VALUE)
                        .add(applyB)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(closeB)
                        .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[]{closeB, applyB}, GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(deliveryOrderTableWithCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(closeB)
                                .add(applyB))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {
        closeB.setText(WorkArea.getMessage(Constants.CLOSE));
        applyB.setText(WorkArea.getMessage(Constants.APPLY));
    }
}
