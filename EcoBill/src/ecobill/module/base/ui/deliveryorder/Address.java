/*
 * Address.java
 *
 * Created on 5. Oktober 2005, 16:44
 */

package ecobill.module.base.ui.deliveryorder;

import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.Person;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.layout.GroupLayout;

/**
 *
 * @author  Roman Georg R�dle
 */
public class Address extends javax.swing.JPanel {

    private GroupLayout layout;

    /** Creates new form Address */
    public Address() {
        initComponents();
        initLayout();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
    private void initComponents() {
        titleL = new javax.swing.JLabel();
        nameL = new javax.swing.JLabel();
        faoL = new javax.swing.JLabel();
        zipCodeCityL = new javax.swing.JLabel();
        countryL = new javax.swing.JLabel();
        branchL = new javax.swing.JLabel();
        streetL = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Anschrift", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
    }

    private void initLayout() {

        layout = new GroupLayout(this);

        GroupLayout.ParallelGroup parallelGroup = layout.createParallelGroup(GroupLayout.LEADING);

        parallelGroup = parallelGroup.add(zipCodeCityL);
        parallelGroup = parallelGroup.add(titleL);
        parallelGroup = parallelGroup.add(nameL);

        if (faoL.getText() != null && !"".equals(faoL.getText())) {
            parallelGroup = parallelGroup.add(faoL);
        }

        if (branchL.getText() != null && !"".equals(branchL.getText())) {
            parallelGroup = parallelGroup.add(branchL);
        }

        if (countryL.getText() != null && !"".equals(countryL.getText())) {
            parallelGroup = parallelGroup.add(countryL);
        }

        if (streetL.getText() != null && !"".equals(streetL.getText())) {
            parallelGroup = parallelGroup.add(streetL);
        }

        this.setLayout(layout);

        GroupLayout.SequentialGroup sequentialGroup = layout.createSequentialGroup();

        sequentialGroup = sequentialGroup.add(titleL).addPreferredGap(LayoutStyle.RELATED);
        sequentialGroup = sequentialGroup.add(nameL).addPreferredGap(LayoutStyle.RELATED);

        if (faoL.getText() != null && !"".equals(faoL.getText())) {
            sequentialGroup = sequentialGroup.add(faoL).addPreferredGap(LayoutStyle.RELATED);
        }

        if (branchL.getText() != null && !"".equals(branchL.getText())) {
            sequentialGroup = sequentialGroup.add(branchL).addPreferredGap(LayoutStyle.RELATED);
        }

        if (streetL.getText() != null && !"".equals(streetL.getText())) {
            sequentialGroup = sequentialGroup.add(streetL).add(20, 20, 20);
        }

        if (countryL.getText() != null && !"".equals(countryL.getText())) {
            sequentialGroup = sequentialGroup.add(countryL).addPreferredGap(LayoutStyle.RELATED);
        }

        sequentialGroup = sequentialGroup.add(zipCodeCityL).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(parallelGroup)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, sequentialGroup)
        );
    }
    // </editor-fold>


    // Variables declaration - do not modify
    private javax.swing.JLabel titleL;
    private javax.swing.JLabel nameL;
    private javax.swing.JLabel faoL;
    private javax.swing.JLabel zipCodeCityL;
    private javax.swing.JLabel countryL;
    private javax.swing.JLabel branchL;
    private javax.swing.JLabel streetL;
    // End of variables declaration

    private BusinessPartner businessPartner;

    public BusinessPartner getBusinessPartner() {
        return businessPartner;
    }

    public void setBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartner = businessPartner;

        Person person = businessPartner.getPerson();
        ecobill.module.base.domain.Address address = businessPartner.getAddress();

        String title = businessPartner.getCompanyTitle();
        if (title == null || "".equals(title)) {
            title = businessPartner.getPerson().getLetterTitle();
        }
        titleL.setText(title);

        String name = businessPartner.getCompanyName();
        if (name == null || "".equals(name)) {
            name = person.getFirstname() + " " + person.getLastname();
        }
        nameL.setText(name);

        if (businessPartner.isForAttentionOf()) {
            faoL.setText(WorkArea.getMessage(Constants.FOR_ATTENTION_OF) + " " + person.getLetterTitle() + " " + person.getLastname());
        }

        branchL.setText(businessPartner.getCompanyBranch());

        streetL.setText(address.getStreet());
        zipCodeCityL.setText(address.getZipCode() + " " + address.getCity());
        countryL.setText(address.getCountry().toString());

        initLayout();
    }
}