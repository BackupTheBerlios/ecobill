/*
 * InputDescription.java
 *
 * Created on 28. September 2005, 14:42
 */

package ecobill.module.base.ui.article;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.SystemCountry;
import ecobill.module.base.domain.SystemLanguage;

import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.Locale;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author  Roman Georg Rädle
 */
public class InputDescription extends JPanel implements Internationalization {

    private BaseService baseService;

    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.DATA), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private JComboBox country = new JComboBox();
    private ComboBoxModel countryModel;
    private JLabel countryL = new JLabel();
    private JComboBox language = new JComboBox();
    private ComboBoxModel languageModel;
    private JLabel languageL = new JLabel();
    private JComboBox variant = new JComboBox();
    private JLabel variantL = new JLabel();

    /**
     * Erzeugt eine neues Input Description Panel für Artikel.
     */
    public InputDescription(BaseService baseService) {
        this.baseService = baseService;
        initComponents();
        initListeners();
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
        language.setMinimumSize(new java.awt.Dimension(80, 20));
        language.setPreferredSize(new java.awt.Dimension(80, 20));

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
        country.setMinimumSize(new java.awt.Dimension(80, 20));
        country.setPreferredSize(new java.awt.Dimension(80, 20));


        //variant.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        variant.setMinimumSize(new java.awt.Dimension(80, 20));
        variant.setPreferredSize(new java.awt.Dimension(80, 20));
    }

    private void initLayout() {

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.LEADING)
                    .add(languageL)
                    .add(language, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                    .add(countryL)
                    .add(country, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
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

    private void initListeners() {

        language.addItemListener(new ItemListener() {

            /**
             * @see ItemListener#itemStateChanged(java.awt.event.ItemEvent)
             */
            public void itemStateChanged(ItemEvent e) {

                SystemLanguage systemLanguage = (SystemLanguage) e.getItem();

                countryModel = new DefaultComboBoxModel(systemLanguage.getSystemCountries().toArray());
                country.setModel(countryModel);
            }
        });
    }

    public void reinitI18N() {

        border.setTitle(WorkArea.getMessage(Constants.DATA));

        languageL.setText(WorkArea.getMessage(Constants.LANGUAGE));
        language.setToolTipText(WorkArea.getMessage(Constants.LANGUAGE_TOOLTIP));
        countryL.setText(WorkArea.getMessage(Constants.COUNTRY));
        country.setToolTipText(WorkArea.getMessage(Constants.COUNTRY_TOOLTIP));
        variantL.setText(WorkArea.getMessage(Constants.VARIANT));
        variant.setToolTipText(WorkArea.getMessage(Constants.VARIANT_TOOLTIP));
    }

    public SystemCountry getCountry() {
        return (SystemCountry) country.getSelectedItem();
    }

    public void setCountry(SystemCountry country) {
        this.country.setSelectedItem(country);
    }

    public SystemLanguage getLanguage() {
        return (SystemLanguage) language.getSelectedItem();
    }

    public void setLanguage(SystemLanguage language) {
        this.language.setSelectedItem(language);
    }

    public Locale getPreparedLocale() {
        return new Locale(getLanguage().getKey(), getCountry().getKey());
    }
}
