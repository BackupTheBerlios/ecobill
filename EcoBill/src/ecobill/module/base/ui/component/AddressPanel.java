package ecobill.module.base.ui.component;

import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.Person;
import ecobill.module.base.domain.Address;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;
import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.layout.GroupLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * AddressPanel.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: AddressPanel.java,v 1.3 2006/01/30 23:43:14 raedler Exp $
 * @since EcoBill 1.0
 */
public class AddressPanel extends JPanel implements Internationalization {

    private GroupLayout layout;

    /**
     * Erzeugt ein neues Adresse <code>JPanel</code>.
     */
    public AddressPanel() {
        initComponents();
        initLayout();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {
        title = new JLabel();
        name = new JLabel();
        fao = new JLabel();
        zipCodeCity = new JLabel();
        country = new JLabel();
        branch = new JLabel();
        street = new JLabel();

        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.ADDRESS), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0)));
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        layout = new GroupLayout(this);

        GroupLayout.ParallelGroup parallelGroup = layout.createParallelGroup(GroupLayout.LEADING);

        parallelGroup = parallelGroup.add(zipCodeCity);
        parallelGroup = parallelGroup.add(title);
        parallelGroup = parallelGroup.add(name);

        if (fao.getText() != null && !"".equals(fao.getText())) {
            parallelGroup = parallelGroup.add(fao);
        }

        if (branch.getText() != null && !"".equals(branch.getText())) {
            parallelGroup = parallelGroup.add(branch);
        }

        if (country.getText() != null && !"".equals(country.getText())) {
            parallelGroup = parallelGroup.add(country);
        }

        if (street.getText() != null && !"".equals(street.getText())) {
            parallelGroup = parallelGroup.add(street);
        }

        setLayout(layout);

        GroupLayout.SequentialGroup sequentialGroup = layout.createSequentialGroup();

        sequentialGroup = sequentialGroup.add(title).addPreferredGap(LayoutStyle.RELATED);
        sequentialGroup = sequentialGroup.add(name).addPreferredGap(LayoutStyle.RELATED);

        if (fao.getText() != null && !"".equals(fao.getText())) {
            sequentialGroup = sequentialGroup.add(fao).addPreferredGap(LayoutStyle.RELATED);
        }

        if (branch.getText() != null && !"".equals(branch.getText())) {
            sequentialGroup = sequentialGroup.add(branch).addPreferredGap(LayoutStyle.RELATED);
        }

        if (street.getText() != null && !"".equals(street.getText())) {
            sequentialGroup = sequentialGroup.add(street).add(20, 20, 20);
        }

        sequentialGroup = sequentialGroup.add(zipCodeCity).addPreferredGap(LayoutStyle.RELATED);

        if (country.getText() != null && !"".equals(country.getText())) {
            sequentialGroup = sequentialGroup.add(country);
        }

        sequentialGroup.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

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

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {
        ((TitledBorder) getBorder()).setTitle(WorkArea.getMessage(Constants.ADDRESS));
    }

    private JLabel title;
    private JLabel name;
    private JLabel fao;
    private JLabel zipCodeCity;
    private JLabel country;
    private JLabel branch;
    private JLabel street;

    private BusinessPartner businessPartner;

    public BusinessPartner getBusinessPartner() {
        return businessPartner;
    }

    public void setBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartner = businessPartner;

        Person person = businessPartner.getPerson();
        Address address = businessPartner.getAddress();

        String title = businessPartner.getCompanyTitle();
        if (title == null || "".equals(title)) {
            title = businessPartner.getPerson().getLetterTitle();
        }
        this.title.setText(title);

        String name = businessPartner.getCompanyName();
        if (name == null || "".equals(name)) {
            name = person.getFirstname() + " " + person.getLastname();
        }
        this.name.setText(name);

        if (businessPartner.isForAttentionOf()) {
            fao.setText(WorkArea.getMessage(Constants.FOR_ATTENTION_OF) + " " + person.getLetterTitle() + " " + person.getLastname());
        }
        else {
            fao.setText(null);
        }

        branch.setText(businessPartner.getCompanyBranch());

        street.setText(address.getStreet());
        zipCodeCity.setText(address.getZipCode() + " " + address.getCity());

        if (address.getCountry() != null) {
            country.setText(address.getCountry().toString());
        }

        initLayout();
    }
}
