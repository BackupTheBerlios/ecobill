package ecobill.module.base.ui.article;

import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.layout.GroupLayout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.SystemCountry;
import ecobill.module.base.domain.SystemLanguage;

import java.awt.*;
import java.util.Locale;
import java.util.Set;
import java.util.HashSet;

/**
 * Das <code>InputLabelling</code> <code>JPanel</code> stellt die Eingabemöglichkeit für
 * Bezeichnungen zur Verfügung.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: InputLabelling.java,v 1.3 2006/01/30 23:43:14 raedler Exp $
 * @since EcoBill 1.0
 */
public class InputLabelling extends JPanel implements Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    protected final static Log LOG = LogFactory.getLog(InputLabelling.class);

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

    private JLabel countryL = new JLabel();
    private ComboBoxModel countryModel;
    private JComboBox country = new JComboBox();

    private JLabel languageL = new JLabel();
    private ComboBoxModel languageModel;
    private JComboBox language = new JComboBox();

    private JLabel variantL = new JLabel();
    private ComboBoxModel variantModel;
    private JComboBox variant = new JComboBox();

    /**
     * Erzeugt eine neues <code>InputLabelling</code> Panel.
     */
    public InputLabelling(BaseService baseService) {

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

        languageModel = new DefaultComboBoxModel(baseService.loadAll(SystemLanguage.class).toArray());
        language.setModel(languageModel);

        SystemLanguage systemLanguage = (SystemLanguage) languageModel.getSelectedItem();

        Set systemCountries = null;
        if (systemLanguage != null) {
            systemCountries = systemLanguage.getSystemCountries();
        }

        if (systemCountries == null) {
            systemCountries = new HashSet();
        }

        countryModel = new DefaultComboBoxModel(systemCountries.toArray());
        country.setModel(countryModel);

        language.setMinimumSize(new Dimension(120, 20));
        language.setPreferredSize(new Dimension(120, 20));

        country.setMinimumSize(new Dimension(120, 20));
        country.setPreferredSize(new Dimension(120, 20));

        variant.setMinimumSize(new Dimension(120, 20));
        variant.setPreferredSize(new Dimension(120, 20));
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
                                .add(languageL)
                                .add(language, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .add(countryL)
                                .add(country, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                        /*
                        .add(variantL)
                        .add(variant, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                        */
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(languageL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(language, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(countryL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(country, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                /*
                .add(variantL)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(variant, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                */
        );
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        border.setTitle(WorkArea.getMessage(Constants.DATA));

        languageL.setText(WorkArea.getMessage(Constants.LANGUAGE));
        language.setToolTipText(WorkArea.getMessage(Constants.LANGUAGE_TOOLTIP));
        countryL.setText(WorkArea.getMessage(Constants.COUNTRY));
        country.setToolTipText(WorkArea.getMessage(Constants.COUNTRY_TOOLTIP));
        variantL.setText(WorkArea.getMessage(Constants.VARIANT));
        variant.setToolTipText(WorkArea.getMessage(Constants.VARIANT_TOOLTIP));
    }

    public JComboBox getLanguage() {
        return language;
    }

    public void setLanguage(JComboBox language) {
        this.language = language;
    }

    public JComboBox getCountry() {
        return country;
    }

    public void setCountry(JComboBox country) {
        this.country = country;
    }

    public SystemCountry getSystemCountry() {
        return (SystemCountry) country.getSelectedItem();
    }

    public void setSystemCountry(SystemCountry country) {
        this.country.setSelectedItem(country);
    }

    public SystemLanguage getSystemLanguage() {
        return (SystemLanguage) language.getSelectedItem();
    }

    public void setSystemLanguage(SystemLanguage language) {
        this.language.setSelectedItem(language);
    }

    public Locale getPreparedLocale() {
        return new Locale(getSystemLanguage().getKey(), getSystemCountry().getKey());
    }
}
