package ecobill.module.base.ui.businesspartner;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.SystemCountry;
import ecobill.module.base.domain.SystemUnit;
import ecobill.module.base.domain.SystemCounty;
import ecobill.core.system.Constants;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Internationalization;
import ecobill.core.util.ComboBoxUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import java.util.List;
import java.util.Collections;
import java.util.Set;
import java.util.ArrayList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.*;

/**
 * Das <code>Input</code> <code>JPanel</code> stellt die Eingabemöglichkeit für einen
 * Geschäftspartner zur Verfügung, die restlichen Daten werden über weitere <code>JPanel</code>
 * angeboten.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: Input.java,v 1.11 2006/02/01 01:06:47 raedler Exp $
 * @since EcoBill 1.0
 */
public class Input extends JPanel implements Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    protected final static Log LOG = LogFactory.getLog(Input.class);

    /**
     * Der <code>BaseService</code> ist die Business Logik. Unter anderem können hierdurch Daten
     * aus der Datenbank ausgelesen und gespeichert werden.
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

    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.DATA), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private JLabel customerNumberL = new JLabel();
    private JTextField customerNumber = new JTextField();

    private JLabel titleL = new JLabel();
    private ComboBoxModel titleModel;
    private JComboBox title = new JComboBox();

    private JLabel academicTitleL = new JLabel();
    private ComboBoxModel academicTitleModel;
    private JComboBox academicTitle = new JComboBox();

    private JLabel firstnameL = new JLabel();
    private JTextField firstname = new JTextField();

    private JLabel lastnameL = new JLabel();
    private JTextField lastname = new JTextField();

    private JLabel streetL = new JLabel();
    private JTextField street = new JTextField();

    private JLabel zipCodeL = new JLabel();
    private JTextField zipCode = new JTextField();

    private JLabel cityL = new JLabel();
    private JTextField city = new JTextField();

    private JLabel countryL = new JLabel();
    private ComboBoxModel countryModel;
    private JComboBox country = new JComboBox();

    private JLabel countyL = new JLabel();
    private ComboBoxModel countyModel;
    private JComboBox county = new JComboBox();

    /**
     * Erzeugt eine neues <code>Input</code> Panel.
     */
    public Input(BaseService baseService) {

        this.baseService = baseService;

        initComponents();
        initLayout();

        reinitI18N();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        setBorder(border);

        titleModel = new DefaultComboBoxModel(ComboBoxUtils.createNullLeadingItems(baseService.getSystemUnitsByCategory(Constants.SYSTEM_UNIT_TITLE)));
        title.setModel(titleModel);

        academicTitleModel = new DefaultComboBoxModel(ComboBoxUtils.createNullLeadingItems(baseService.getSystemUnitsByCategory(Constants.SYSTEM_UNIT_ACADEMIC_TITLE)));
        academicTitle.setModel(academicTitleModel);

        countryModel = new DefaultComboBoxModel(ComboBoxUtils.createNullLeadingItems(baseService.loadAll(SystemCountry.class)));
        country.setModel(countryModel);
        fireCountryStateChanged();

        country.addItemListener(new ItemListener() {

            /**
             * @see ItemListener#itemStateChanged(java.awt.event.ItemEvent)
             */
            public void itemStateChanged(ItemEvent e) {
                fireCountryStateChanged();
            }
        });


        title.setMinimumSize(new Dimension(80, 20));
        title.setPreferredSize(new Dimension(80, 20));

        academicTitle.setMinimumSize(new Dimension(80, 20));
        academicTitle.setPreferredSize(new Dimension(80, 20));

        firstname.setMinimumSize(new Dimension(120, 20));
        firstname.setPreferredSize(new Dimension(120, 20));

        lastname.setMinimumSize(new Dimension(120, 20));
        lastname.setPreferredSize(new Dimension(120, 20));

        street.setMinimumSize(new Dimension(120, 20));
        street.setPreferredSize(new Dimension(120, 20));

        zipCode.setMinimumSize(new Dimension(80, 20));
        zipCode.setPreferredSize(new Dimension(80, 20));

        city.setMinimumSize(new Dimension(120, 20));
        city.setPreferredSize(new Dimension(120, 20));

        country.setMinimumSize(new Dimension(140, 20));
        country.setPreferredSize(new Dimension(140, 20));

        county.setMinimumSize(new Dimension(140, 20));
        county.setPreferredSize(new Dimension(140, 20));
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        GroupLayout layout = new GroupLayout(this);

        setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(customerNumber, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                .add(customerNumberL)
                                .add(GroupLayout.LEADING, layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                                .add(titleL)
                                                .add(title, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                        .add(academicTitle, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                        .add(academicTitleL)))
                                .add(GroupLayout.LEADING, layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                                .add(firstname, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                                .add(firstnameL))
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                        .add(lastnameL)
                                        .add(lastname, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                                .add(streetL)
                                .add(street, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                .add(GroupLayout.LEADING, layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                                .add(zipCode, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                .add(zipCodeL))
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                        .add(cityL)
                                        .add(city, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                                .add(GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(layout.createParallelGroup(GroupLayout.LEADING)
                                        .add(country, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                        .add(countryL))
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(countyL)
                                .add(county, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(customerNumberL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(customerNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.TRAILING)
                                .add(titleL)
                                .add(academicTitleL))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(title, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .add(academicTitle, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(firstnameL)
                                .add(lastnameL))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(firstname, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .add(lastname, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(streetL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(street, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(zipCodeL)
                                .add(cityL))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(zipCode, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .add(city, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(countryL)
                                .add(countyL))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(country, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .add(county, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        border.setTitle(WorkArea.getMessage(Constants.DATA));

        customerNumberL.setText(WorkArea.getMessage(Constants.CUSTOMER_NUMBER));
        titleL.setText(WorkArea.getMessage(Constants.TITLE));
        academicTitleL.setText(WorkArea.getMessage(Constants.ACADEMIC_TITLE));
        firstnameL.setText(WorkArea.getMessage(Constants.FIRSTNAME));
        lastnameL.setText(WorkArea.getMessage(Constants.LASTNAME));
        streetL.setText(WorkArea.getMessage(Constants.STREET));
        zipCodeL.setText(WorkArea.getMessage(Constants.ZIP_CODE));
        cityL.setText(WorkArea.getMessage(Constants.CITY));
        countryL.setText(WorkArea.getMessage(Constants.COUNTRY));
        countyL.setText(WorkArea.getMessage(Constants.COUNTY));
    }

    /**
     * Setzt die Eingabefelder zurück.
     */
    public void resetInput() {
        customerNumber.setText("");
        title.setSelectedIndex(0);
        academicTitle.setSelectedIndex(0);
        firstname.setText("");
        lastname.setText("");
        street.setText("");
        zipCode.setText("");
        city.setText("");
        country.setSelectedIndex(0);
        country.updateUI();

        // Falls noch kein Land ausgewählt wurde, können auch keine Bundesländer
        // ausgewählt werden.
        try {
            if (county.getItemCount() > 0) {
                county.setSelectedIndex(0);
            }
        }
        catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * This method should be fired if the country state has
     * been changed.
     */
    private void fireCountryStateChanged() {
        if (countryModel.getSelectedItem() != null) {

            Set<SystemCounty> counties = ((SystemCountry) countryModel.getSelectedItem()).getSystemCounties();

            List<SystemCounty> countiesList = new ArrayList<SystemCounty>(counties);

            Collections.sort(countiesList);

            countiesList.add(0, null);

            countyModel = new DefaultComboBoxModel(countiesList.toArray());
            county.setModel(countyModel);
        }
        else {
            county.removeAllItems();
        }
    }

    public String getCustomerNumber() {
        return customerNumber.getText();
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber.setText(customerNumber);
    }

    public SystemUnit getTitle() {
        return (SystemUnit) title.getSelectedItem();
    }

    public void setTitle(SystemUnit title) {
        this.title.setSelectedItem(title);
    }

    public SystemUnit getAcademicTitle() {
        return (SystemUnit) academicTitle.getSelectedItem();
    }

    public void setAcademicTitle(SystemUnit academicTitle) {
        this.academicTitle.setSelectedItem(academicTitle);
    }

    public String getFirstname() {
        return firstname.getText();
    }

    public void setFirstname(String firstname) {
        this.firstname.setText(firstname);
    }

    public String getLastname() {
        return lastname.getText();
    }

    public void setLastname(String lastname) {
        this.lastname.setText(lastname);
    }

    public String getStreet() {
        return street.getText();
    }

    public void setStreet(String street) {
        this.street.setText(street);
    }

    public String getZipCode() {
        return zipCode.getText();
    }

    public void setZipCode(String zipCode) {
        this.zipCode.setText(zipCode);
    }

    public String getCity() {
        return city.getText();
    }

    public void setCity(String city) {
        this.city.setText(city);
    }

    public SystemCountry getCountry() {
        return (SystemCountry) country.getSelectedItem();
    }

    public void setCountry(SystemCountry country) {
        this.country.setSelectedItem(country);
    }

    public SystemCounty getCounty() {
        return (SystemCounty) county.getSelectedItem();
    }

    public void setCounty(SystemCounty county) {
        this.county.setSelectedItem(county);
    }
}
