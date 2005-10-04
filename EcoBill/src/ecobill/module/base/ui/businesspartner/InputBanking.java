/*
 * InputBanking.java
 *
 * Created on 2. Oktober 2005, 00:59
 */

package ecobill.module.base.ui.businesspartner;

import javax.swing.*;

/**
 *
 * @author  Roman Georg R�dle
 */
public class InputBanking extends javax.swing.JPanel {

    /** Creates new form InputBanking */
    public InputBanking() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
    private void initComponents() {
        bankEstablishmentL = new javax.swing.JLabel();
        bankEstablishment = new javax.swing.JTextField();
        bankIdentificationNumberL = new javax.swing.JLabel();
        bankIdentificationNumber = new javax.swing.JTextField();
        accountNumberL = new javax.swing.JLabel();
        accountNumber = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Bankdaten", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
        bankEstablishmentL.setText("Bank");

        bankEstablishment.setMinimumSize(new java.awt.Dimension(120, 20));
        bankEstablishment.setPreferredSize(new java.awt.Dimension(120, 20));

        bankIdentificationNumberL.setText("Bankleitzahl");

        bankIdentificationNumber.setMinimumSize(new java.awt.Dimension(120, 20));
        bankIdentificationNumber.setPreferredSize(new java.awt.Dimension(120, 20));

        accountNumberL.setText("Kontonummer");

        accountNumber.setMinimumSize(new java.awt.Dimension(120, 20));
        accountNumber.setPreferredSize(new java.awt.Dimension(120, 20));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(bankEstablishmentL)
                    .add(bankEstablishment, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .add(bankIdentificationNumberL)
                    .add(bankIdentificationNumber, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .add(accountNumberL)
                    .add(accountNumber, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .add(bankEstablishmentL)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(bankEstablishment, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(bankIdentificationNumberL)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(bankIdentificationNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(accountNumberL)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(accountNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    // </editor-fold>


    // Variables declaration - do not modify
    private javax.swing.JTextField accountNumber;
    private javax.swing.JLabel accountNumberL;
    private javax.swing.JTextField bankEstablishment;
    private javax.swing.JLabel bankEstablishmentL;
    private javax.swing.JTextField bankIdentificationNumber;
    private javax.swing.JLabel bankIdentificationNumberL;
    // End of variables declaration

    public String getAccountNumber() {
        return accountNumber.getText();
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber.setText(accountNumber);
    }

    public String getBankEstablishment() {
        return bankEstablishment.getText();
    }

    public void setBankEstablishment(String bankEstablishment) {
        this.bankEstablishment.setText(bankEstablishment);
    }

    public String getBankIdentificationNumber() {
        return bankIdentificationNumber.getText();
    }

    public void setBankIdentificationNumber(String bankIdentificationNumber) {
        this.bankIdentificationNumber.setText(bankIdentificationNumber);
    }
}