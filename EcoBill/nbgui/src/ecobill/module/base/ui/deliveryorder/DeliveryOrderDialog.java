/*
 * DeliveryOrderDialog.java
 *
 * Created on 5. Oktober 2005, 18:01
 */

package ecobill.module.base.ui.deliveryorder;

/**
 *
 * @author  Roman Georg R�dle
 */
public class DeliveryOrderDialog extends javax.swing.JPanel {
    
    /** Creates new form DeliveryOrderDialog */
    public DeliveryOrderDialog() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jDialog1 = new javax.swing.JDialog();
        quantityL = new javax.swing.JLabel();
        quantity = new javax.swing.JSpinner();
        unitL = new javax.swing.JLabel();
        unit = new javax.swing.JTextField();
        priceL = new javax.swing.JLabel();
        price = new javax.swing.JSpinner();
        labellingScrollPane = new javax.swing.JScrollPane();
        labelling = new javax.swing.JTextArea();
        labellingL = new javax.swing.JLabel();
        cancel = new javax.swing.JButton();
        ok = new javax.swing.JButton();

        org.jdesktop.layout.GroupLayout jDialog1Layout = new org.jdesktop.layout.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        );

        quantityL.setText("Menge");

        quantity.setMinimumSize(new java.awt.Dimension(80, 20));
        quantity.setPreferredSize(new java.awt.Dimension(80, 20));

        unitL.setText("Einheit");

        unit.setMinimumSize(new java.awt.Dimension(80, 20));
        unit.setPreferredSize(new java.awt.Dimension(80, 20));

        priceL.setText("Preis");

        price.setMinimumSize(new java.awt.Dimension(80, 20));
        price.setPreferredSize(new java.awt.Dimension(80, 20));

        labelling.setColumns(20);
        labelling.setRows(5);
        labellingScrollPane.setViewportView(labelling);

        labellingL.setText("Bezeichnung");

        org.openide.awt.Mnemonics.setLocalizedText(cancel, "Abbrechen");

        org.openide.awt.Mnemonics.setLocalizedText(ok, "OK");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(quantityL)
                            .add(quantity, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(6, 6, 6)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(unitL)
                            .add(unit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(priceL)
                            .add(price, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(ok)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cancel))
                    .add(labellingL)
                    .add(labellingScrollPane))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(new java.awt.Component[] {cancel, ok}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(quantityL)
                    .add(unitL)
                    .add(priceL))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(quantity, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(price, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(unit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(20, 20, 20)
                .add(labellingL)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(labellingScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(25, 25, 25)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancel)
                    .add(ok))
                .addContainerGap(13, Short.MAX_VALUE))
        );
    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JTextArea labelling;
    private javax.swing.JLabel labellingL;
    private javax.swing.JScrollPane labellingScrollPane;
    private javax.swing.JButton ok;
    private javax.swing.JSpinner price;
    private javax.swing.JLabel priceL;
    private javax.swing.JSpinner quantity;
    private javax.swing.JLabel quantityL;
    private javax.swing.JTextField unit;
    private javax.swing.JLabel unitL;
    // End of variables declaration//GEN-END:variables
    
}
