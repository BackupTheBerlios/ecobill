package ecobill.module.base.ui;

import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;

import javax.swing.*;
import java.awt.*;

// @todo document me!

/**
 * ArticleDescriptionPanel.
 * <p/>
 * User: rro
 * Date: 08.08.2005
 * Time: 22:40:51
 *
 * @author Roman R&auml;dle
 * @version $Id: ArticleDescriptionPanel.java,v 1.1 2005/08/11 18:10:31 raedler Exp $
 * @since EcoBill 1.0
 */
public class ArticleDescriptionPanel extends JPanel implements Internationalization {

    private JLabel languageL = new JLabel();
    private JLabel countryL = new JLabel();
    private JLabel variantL = new JLabel();

    private JComboBox languageCB = new JComboBox();
    private JComboBox countryCB = new JComboBox();
    private JComboBox variantCB = new JComboBox();

    private JTextArea descriptionTA = new JTextArea();

    public ArticleDescriptionPanel() {
        super();

        init();
    }

    private void init() {
        JPanel localeSettingP = new JPanel(null);

        languageL.setBounds(10, 20, 100, 20);
        localeSettingP.add(languageL);

        languageCB.setBounds(10, 40, 100, 20);
        localeSettingP.add(languageCB);

        countryL.setBounds(10, 70, 100, 20);
        localeSettingP.add(countryL);

        countryCB.setBounds(10, 90, 100, 20);
        localeSettingP.add(countryCB);

        variantL.setBounds(10, 120, 100, 20);
        localeSettingP.add(variantL);

        variantCB.setBounds(10, 140, 100, 20);
        localeSettingP.add(variantCB);

        descriptionTA.setLineWrap(true);
        descriptionTA.setWrapStyleWord(true);

        GridBagLayout gbl = new GridBagLayout();
        JPanel descriptionTopP = new JPanel(new BorderLayout());

        GridBagConstraints c1 = new GridBagConstraints();
        c1.fill = GridBagConstraints.NONE;
        c1.weightx = 0.0;
        c1.weighty = 0.0;
        c1.anchor = GridBagConstraints.WEST;
        c1.ipadx = 0;
        c1.ipady = 0;
        c1.gridx = 0;
        c1.gridy = 0;
        c1.gridwidth = 1;
        c1.gridheight = 1;
        //gbl.setConstraints(localeSettingP, c1);
        //localeSettingP.setMinimumSize(new Dimension(180, 120));
        descriptionTopP.add(localeSettingP, BorderLayout.CENTER);

        /*
        GridBagConstraints c2 = new GridBagConstraints();
        c2.fill = GridBagConstraints.BOTH;
        c2.weightx = 1.0;
        c2.weighty = 1.0;
        c2.anchor = GridBagConstraints.CENTER;
        c2.ipadx = 0;
        c2.ipady = 0;
        c2.gridx = 1;
        c2.gridy = 0;
        c2.gridwidth = 1;
        c2.gridheight = 1;
        gbl.setConstraints(descriptionsTA, c2);
        descriptionTopP.add(descriptionsTA);
        */

        //ocaleSettingP.setBorder(localeBorder);

        //descriptionTopP.setLayout(gbl);

        JPanel descriptionsTableP = new JPanel(new BorderLayout());
        descriptionsTableP.add(new JScrollPane(new JTable(new Object[][]{{"a", "a", "a"}, {"b", "b", "b"}}, new Object[]{"test1", "test2", "test3"})));

        descriptionTopP.setPreferredSize(new Dimension(400, 200));
        //descriptionTopP.setBorder(new EtchedBorder());

        /*
        someP.add(descriptionsTA);
        someP.add(languageCB);
        someP.add(countryCB);
        someP.add(addDescriptionB);

        descriptionsP.add(someP, BorderLayout.NORTH);
        */

        this.add(descriptionTopP, BorderLayout.NORTH);
        this.add(descriptionsTableP, BorderLayout.CENTER);
    }

    public void reinitI18N() {
        languageL.setText(WorkArea.getWorkArea().getMessage(Constants.LANGUAGE));

        repaint();
    }
}
