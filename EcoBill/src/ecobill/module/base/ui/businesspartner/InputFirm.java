/*
 * InputFirm.java
 *
 * Created on 2. Oktober 2005, 11:13
 */

package ecobill.module.base.ui.businesspartner;

import javax.swing.*;

/**
 *
 * @author  Roman Georg R�dle
 */
public class InputFirm extends javax.swing.JPanel {

    /** Creates new form InputFirm */
    public InputFirm() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
    private void initComponents() {
        titleL = new javax.swing.JLabel();
        title = new javax.swing.JTextField();
        firmL = new javax.swing.JLabel();
        firm = new javax.swing.JTextField();
        branchL = new javax.swing.JLabel();
        branch = new javax.swing.JTextField();
        forAttentionOf = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Firmendaten", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
        titleL.setText("Anrede");

        title.setMinimumSize(new java.awt.Dimension(120, 20));
        title.setPreferredSize(new java.awt.Dimension(120, 20));

        firmL.setText("Firma");

        firm.setMinimumSize(new java.awt.Dimension(120, 20));
        firm.setPreferredSize(new java.awt.Dimension(120, 20));

        branchL.setText("Branche");

        branch.setMinimumSize(new java.awt.Dimension(120, 20));
        branch.setPreferredSize(new java.awt.Dimension(120, 20));

        forAttentionOf.setText("z.H. Person");
        forAttentionOf.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        forAttentionOf.setMargin(new java.awt.Insets(0, 0, 0, 0));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(titleL)
                    .add(title, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(firmL)
                    .add(firm, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(branchL)
                    .add(branch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(forAttentionOf))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .add(titleL)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(title, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(firmL)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(firm, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(branchL)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(branch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(25, 25, 25)
                .add(forAttentionOf)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    // </editor-fold>


    // Variables declaration - do not modify
    private javax.swing.JTextField branch;
    private javax.swing.JLabel branchL;
    private javax.swing.JTextField firm;
    private javax.swing.JLabel firmL;
    private javax.swing.JCheckBox forAttentionOf;
    private javax.swing.JTextField title;
    private javax.swing.JLabel titleL;
    // End of variables declaration


    public String getBranch() {
        return branch.getText();
    }

    public void setBranch(String branch) {
        this.branch.setText(branch);
    }

    public String getFirm() {
        return firm.getText();
    }

    public void setFirm(String firm) {
        this.firm.setText(firm);
    }

    public boolean isForAttentionOf() {
        return forAttentionOf.isSelected();
    }

    public void setForAttentionOf(boolean forAttentionOf) {
        this.forAttentionOf.setSelected(forAttentionOf);
    }

    public String getTitle() {
        return title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}