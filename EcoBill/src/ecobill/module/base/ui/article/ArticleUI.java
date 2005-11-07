package ecobill.module.base.ui.article;

import ecobill.module.base.ui.component.VerticalButton;
import ecobill.module.base.ui.component.Labelling;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.*;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;
import ecobill.core.util.FileUtils;
import ecobill.core.util.IdKeyItem;
import ecobill.util.exception.LocalizerException;
import ecobill.util.LocalizerUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.layout.GroupLayout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * TODO: document me!!!
 * <p/>
 * ArticleUI
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: ArticleUI.java,v 1.15 2005/11/07 21:49:30 raedler Exp $
 * @since EcoBill 1.0
 */
public class ArticleUI extends JPanel implements InitializingBean, Internationalization, DisposableBean {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(ArticleUI.class);

    /**
     * Der <code>BaseService</code> ist die Business Logik.
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

    /**
     * Enthält die Pfade an denen die bestimmten Objekte serialisiert werden
     * sollen.
     */
    private Properties serializeIdentifiers;

    /**
     * Gibt die Pfade, an denen die bestimmten Objekte serialisiert werden
     * sollen, zurück.
     *
     * @return Die Pfade an denen die bestimmten Objekte serialisiert werden
     *         sollen.
     */
    public Properties getSerializeIdentifiers() {
        return serializeIdentifiers;
    }

    /**
     * Setzt die Pfade, an denen die bestimmten Objekte serialisiert werden
     * sollen.
     *
     * @param serializeIdentifiers Die Pfade an denen die bestimmten Objekte
     *                             serialisiert werden sollen.
     */
    public void setSerializeIdentifiers(Properties serializeIdentifiers) {
        this.serializeIdentifiers = serializeIdentifiers;
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {

        // Initialisieren der Komponenten und des Layouts.
        initComponents();
        initLayout();
        initListeners();

        // Setze das Bezeichnungen Tab disabled solange noch kein Artikel besteht, zu
        // dem Bezeichnungen hinzugefügt werden können.
        tabbedPane.setEnabledAt(1, false);

        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
        try {
            overviewArticleTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("article_table")));
            labellingTableOverview.unpersist(new FileInputStream(serializeIdentifiers.getProperty("residual_labelling_table")));
            labellingTableLabelling.unpersist(new FileInputStream(serializeIdentifiers.getProperty("labelling_table")));
        }
        catch (FileNotFoundException fnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(fnfe.getMessage());
            }
        }

        reinitI18N();

        NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.ARTICLE);
        inputOverview.setArticleNumber(numberSequence.getNextNumber());
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {

        if (LOG.isInfoEnabled()) {
            LOG.info("Schließe ArticleUI und speichere die Daten.");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu können.
        overviewArticleTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
        labellingTableOverview.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("residual_labelling_table"))));
        labellingTableLabelling.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("labelling_table"))));
    }

    /**
     * Die <code>ArticleTable</code> zeigt alle in der Datenbank verfügbaren Artikel im
     * Übersichtstab an.
     */
    private ArticleTable overviewArticleTable;

    /**
     * Gibt die <code>ArticleTable</code> zurück, die zur Anzeige der Artikel, in der Datenbank,
     * verwendet wird.
     *
     * @return Die <code>ArticleTable</code> zur Anzeige der Artikel.
     */
    public ArticleTable getOverviewArticleTable() {
        return overviewArticleTable;
    }

    private Labelling labellingLabelling = new Labelling();
    private Labelling labellingOverview = new Labelling();
    private LabellingTable labellingTableLabelling;
    private LabellingTable labellingTableOverview;
    private InputBundle inputBundleOverview;
    private InputLabelling inputLabellingLabelling;
    private Input inputOverview;
    private JPanel labelling = new JPanel();
    private JPanel overview = new JPanel();
    private JTabbedPane tabbedPane = new JTabbedPane();
    private VerticalButton verticalButtonLabelling = new VerticalButton();
    private VerticalButton verticalButtonOverview = new VerticalButton();

    private Long actualArticleId;

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {
        overviewArticleTable = new ArticleTable(this, baseService);
        inputOverview = new Input(baseService);
        inputBundleOverview = new InputBundle(baseService);
        inputLabellingLabelling = new InputLabelling(baseService);
        labellingTableOverview = new LabellingTable(this, baseService);
        labellingTableLabelling = new LabellingTable(this, baseService);

        verticalButtonOverview.getButton1().setVisible(true);
        verticalButtonOverview.getButton1().setIcon(new ImageIcon("images/article_new.png"));
        verticalButtonOverview.getButton1().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                actualArticleId = null;

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.ARTICLE);

                resetInput(numberSequence.getNextNumber());
            }
        });

        verticalButtonOverview.getButton2().setVisible(true);
        verticalButtonOverview.getButton2().setIcon(new ImageIcon("images/article_ok.png"));
        verticalButtonOverview.getButton2().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                saveOrUpdateArticle();
                overviewArticleTable.renewTableModel();

                String actualArticleNumber = inputOverview.getArticleNumber();

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.ARTICLE);

                if (numberSequence.compareWithNumber(actualArticleNumber) <= -1) {

                    numberSequence.setNumber(actualArticleNumber);

                    baseService.saveOrUpdate(numberSequence);
                }
            }
        });

        verticalButtonOverview.getButton3().setVisible(true);
        verticalButtonOverview.getButton3().setIcon(new ImageIcon("images/article_delete.png"));
        verticalButtonOverview.getButton3().addActionListener(new ArticleAction(this).DELETE_ACTION);

        verticalButtonOverview.getButton4().setVisible(true);
        verticalButtonOverview.getButton4().setIcon(new ImageIcon("images/refresh.png"));
        verticalButtonOverview.getButton4().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                overviewArticleTable.renewTableModel();

                if (actualArticleId != null) {
                    Article article = (Article) baseService.load(Article.class, actualArticleId);

                    labellingTableOverview.renewTableModel(article);
                    labellingTableLabelling.renewTableModel(article);
                }
            }
        });

        verticalButtonLabelling.getButton1().setVisible(true);
        verticalButtonLabelling.getButton1().setIcon(new ImageIcon("images/article_labelling_new.png"));
        verticalButtonLabelling.getButton1().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                labellingLabelling.setDescription("");
            }
        });

        verticalButtonLabelling.getButton2().setVisible(true);
        verticalButtonLabelling.getButton2().setIcon(new ImageIcon("images/article_labelling_ok.png"));
        verticalButtonLabelling.getButton2().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveOrUpdateArticleDescription();
            }
        });

        verticalButtonLabelling.getButton3().setVisible(true);
        verticalButtonLabelling.getButton3().setIcon(new ImageIcon("images/article_labelling_delete.png"));
        verticalButtonLabelling.getButton3().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Article article = (Article) baseService.load(Article.class, actualArticleId);

                Locale locale = inputLabellingLabelling.getPreparedLocale();

                ArticleDescription articleDescription;
                try {
                    articleDescription = (ArticleDescription) LocalizerUtils.getExactLocalizedObject(article.getDescriptions(), locale);

                    baseService.delete(articleDescription);

                    showArticle(article.getId());
                }
                catch (LocalizerException le) {
                    if (LOG.isErrorEnabled()) {
                        LOG.debug(le.getMessage(), le);
                    }
                }
            }
        });

        verticalButtonLabelling.getButton4().setVisible(true);
        verticalButtonLabelling.getButton4().setIcon(new ImageIcon("images/refresh.png"));
        verticalButtonLabelling.getButton4().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Article article = (Article) baseService.load(Article.class, actualArticleId);

                labellingTableLabelling.renewTableModel(article);
            }
        });
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        setLayout(new BorderLayout());

        GroupLayout overviewLayout = new GroupLayout(overview);

        overview.setLayout(overviewLayout);

        overviewLayout.setHorizontalGroup(
                overviewLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(overviewLayout.createParallelGroup(GroupLayout.LEADING)
                                .add(overviewArticleTable, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                                .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                                .add(verticalButtonOverview, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(overviewLayout.createParallelGroup(GroupLayout.LEADING, false)
                                        .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                                                .add(inputOverview, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.RELATED)
                                                .add(inputBundleOverview, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .add(labellingOverview, 0, 0, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(labellingTableOverview, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)))
                        .addContainerGap())
        );
        overviewLayout.setVerticalGroup(
                overviewLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(overviewLayout.createParallelGroup(GroupLayout.TRAILING, false)
                                .add(GroupLayout.LEADING, labellingTableOverview, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(verticalButtonOverview, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                                .add(overviewLayout.createParallelGroup(GroupLayout.TRAILING, false)
                                        .add(GroupLayout.LEADING, inputBundleOverview, 0, 0, Short.MAX_VALUE)
                                        .add(inputOverview, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(labellingOverview, 0, 166, Short.MAX_VALUE)))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(overviewArticleTable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
        );
        tabbedPane.addTab(WorkArea.getMessage(Constants.OVERVIEW), overview);

        GroupLayout labellingLayout = new GroupLayout(labelling);
        labelling.setLayout(labellingLayout);
        labellingLayout.setHorizontalGroup(
                labellingLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, labellingLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(labellingLayout.createParallelGroup(GroupLayout.LEADING)
                                .add(labellingTableLabelling, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                                .add(GroupLayout.LEADING, labellingLayout.createSequentialGroup()
                                .add(verticalButtonLabelling, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(inputLabellingLabelling, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(labellingLabelling, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)))
                        .addContainerGap())
        );
        labellingLayout.setVerticalGroup(
                labellingLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, labellingLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(labellingLayout.createParallelGroup(GroupLayout.TRAILING, false)
                                .add(GroupLayout.LEADING, labellingLabelling, 0, 0, Short.MAX_VALUE)
                                .add(GroupLayout.LEADING, inputLabellingLabelling, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(verticalButtonLabelling, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(labellingTableLabelling, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                        .addContainerGap())
        );
        tabbedPane.addTab(WorkArea.getMessage(Constants.LABELLING), labelling);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void initListeners() {

        labellingTableLabelling.getTable().addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {

                JTable table = labellingTableLabelling.getTable();

                int col = table.getColumnModel().getColumnIndex(WorkArea.getMessage(Constants.KEY));

                int row = table.getSelectedRow();

                Object o = table.getValueAt(row, col);

                if (o instanceof IdKeyItem) {

                    IdKeyItem idKeyItem = (IdKeyItem) o;

                    Long id = idKeyItem.getId();

                    showArticleDescription(id);
                }
            }
        });

        inputLabellingLabelling.getLanguage().addItemListener(new ItemListener() {

            /**
             * @see ItemListener#itemStateChanged(java.awt.event.ItemEvent)
             */
            public void itemStateChanged(ItemEvent e) {

                SystemLanguage systemLanguage = (SystemLanguage) e.getItem();

                ComboBoxModel countryModel = new DefaultComboBoxModel(systemLanguage.getSystemCountries().toArray());
                inputLabellingLabelling.getCountry().setModel(countryModel);

                Article article = (Article) baseService.load(Article.class, actualArticleId);

                Locale locale = inputLabellingLabelling.getPreparedLocale();

                ArticleDescription articleDescription;
                try {
                    articleDescription = (ArticleDescription) LocalizerUtils.getExactLocalizedObject(article.getDescriptions(), locale);

                    labellingLabelling.setDescription(articleDescription.getDescription());
                }
                catch (LocalizerException le) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(le.getMessage());
                    }

                    labellingLabelling.setDescription("");
                }
            }
        });

        inputLabellingLabelling.getCountry().addItemListener(new ItemListener() {

            /**
             * @see ItemListener#itemStateChanged(java.awt.event.ItemEvent)
             */
            public void itemStateChanged(ItemEvent e) {

                Article article = (Article) baseService.load(Article.class, actualArticleId);

                Locale locale = inputLabellingLabelling.getPreparedLocale();

                ArticleDescription articleDescription;
                try {
                    articleDescription = (ArticleDescription) LocalizerUtils.getExactLocalizedObject(article.getDescriptions(), locale);

                    labellingLabelling.setDescription(articleDescription.getDescription());
                }
                catch (LocalizerException le) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(le.getMessage());
                    }

                    labellingLabelling.setDescription("");
                }
            }
        });
    }

    public ArticleTable getArticleTable() {
        return overviewArticleTable;
    }

    public LabellingTable getDescriptionTable() {
        return labellingTableOverview;
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {
        tabbedPane.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));
        tabbedPane.setTitleAt(1, WorkArea.getMessage(Constants.LABELLING));

        verticalButtonOverview.reinitI18N();

        verticalButtonOverview.getButton1().setToolTipText(WorkArea.getMessage(Constants.BUTTON1_ARTICLE_TOOLTIP));
        verticalButtonOverview.getButton2().setToolTipText(WorkArea.getMessage(Constants.BUTTON2_ARTICLE_TOOLTIP));
        verticalButtonOverview.getButton3().setToolTipText(WorkArea.getMessage(Constants.BUTTON3_ARTICLE_TOOLTIP));
        verticalButtonOverview.getButton4().setToolTipText(WorkArea.getMessage(Constants.BUTTON4_ARTICLE_TOOLTIP));

        verticalButtonLabelling.getButton1().setToolTipText(WorkArea.getMessage(Constants.BUTTON1_LABELLING_TOOLTIP));
        verticalButtonLabelling.getButton2().setToolTipText(WorkArea.getMessage(Constants.BUTTON2_LABELLING_TOOLTIP));
        verticalButtonLabelling.getButton3().setToolTipText(WorkArea.getMessage(Constants.BUTTON3_LABELLING_TOOLTIP));
        verticalButtonLabelling.getButton4().setToolTipText(WorkArea.getMessage(Constants.BUTTON4_LABELLING_TOOLTIP));


        overviewArticleTable.reinitI18N();
        labellingLabelling.reinitI18N();
        labellingOverview.reinitI18N();
        labellingTableLabelling.reinitI18N();
        labellingTableOverview.reinitI18N();
        inputBundleOverview.reinitI18N();
        inputLabellingLabelling.reinitI18N();
        inputOverview.reinitI18N();
    }

    public void resetInput(String nextArticleNumber) {

        tabbedPane.setEnabledAt(1, false);

        inputOverview.setArticleNumber(nextArticleNumber);
        inputOverview.setPrice(0D);
        inputOverview.setInStock(0D);
        inputBundleOverview.setCapacity(0D);
        labellingOverview.setDescription("");

        ((DefaultTableModel) labellingTableOverview.getTable().getModel()).getDataVector().removeAllElements();
        ((DefaultTableModel) labellingTableLabelling.getTable().getModel()).getDataVector().removeAllElements();
    }

    public void showArticle(Long id) {

        tabbedPane.setEnabledAt(1, true);

        actualArticleId = id;

        Article article = (Article) baseService.load(Article.class, id);

        labellingTableOverview.renewTableModel(article);
        labellingTableLabelling.renewTableModel(article);

        inputOverview.setArticleNumber(article.getArticleNumber());
        inputOverview.setUnit(article.getUnit());
        inputOverview.setPrice(article.getPrice());
        inputOverview.setInStock(article.getInStock());
        inputBundleOverview.setUnit(article.getBundleUnit());
        inputBundleOverview.setCapacity(article.getBundleCapacity());
        labellingOverview.setDescription(article.getLocalizedDescription());

        try {
            showArticleDescription(article.getLocalizedArticleDescription());
        }
        catch (LocalizerException le) {
            if (LOG.isErrorEnabled()) {
                LOG.error(le.getMessage(), le);
            }
        }
    }

    public void showArticleDescription(Long id) {

        ArticleDescription articleDescription = (ArticleDescription) baseService.load(ArticleDescription.class, id);

        showArticleDescription(articleDescription);
    }

    private Long actualArticleDescriptionId;

    public void showArticleDescription(ArticleDescription articleDescription) {

        actualArticleDescriptionId = articleDescription.getId();

        labellingLabelling.setDescription(articleDescription.getDescription());

        SystemLocale systemLocale = articleDescription.getSystemLocale();

        inputLabellingLabelling.setSystemLanguage(systemLocale.getSystemLanguage());
        inputLabellingLabelling.setSystemCountry(systemLocale.getSystemCountry());
    }

    /**
     * Speichert oder ändert den Artikel mit den in der UI angegebenen Daten.
     */
    private void saveOrUpdateArticle() {

        tabbedPane.setEnabledAt(1, true);

        Article article = null;
        if (actualArticleId != null) {
            article = (Article) baseService.load(Article.class, actualArticleId);
        }

        if (article == null) {
            article = new Article();
        }

        article.setArticleNumber(inputOverview.getArticleNumber());
        article.setUnit(inputOverview.getUnit());
        article.setPrice(inputOverview.getPrice());
        article.setInStock(inputOverview.getInStock());
        article.setBundleUnit(inputBundleOverview.getUnit());
        article.setBundleCapacity(inputBundleOverview.getCapacity());

        SystemLocale systemLocale = baseService.getSystemLocaleByLocale(WorkArea.getLocale());

        ArticleDescription articleDescription;
        try {
            articleDescription = article.getLocalizedArticleDescription();
        }
        catch (LocalizerException le) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(le.getMessage());
            }
            articleDescription = new ArticleDescription();
        }

        articleDescription.setDescription(labellingOverview.getDescription());
        articleDescription.setSystemLocale(systemLocale);

        article.addArticleDescription(articleDescription);

        overviewArticleTable.renewTableModel();

        baseService.saveOrUpdate(article);

        labellingTableOverview.renewTableModel(article);
        labellingTableLabelling.renewTableModel(article);

        actualArticleId = article.getId();
    }

    private void saveOrUpdateArticleDescription() {

        Article article = null;
        if (actualArticleId != null) {
            article = (Article) baseService.load(Article.class, actualArticleId);
        }

        Locale locale = inputLabellingLabelling.getPreparedLocale();

        SystemLocale systemLocale = baseService.getSystemLocaleByLocale(locale);

        ArticleDescription articleDescription;
        try {
            articleDescription = (ArticleDescription) LocalizerUtils.getExactLocalizedObject(article.getDescriptions(), locale);
        }
        catch (LocalizerException le) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(le.getMessage());
            }
            articleDescription = new ArticleDescription();

            articleDescription.setSystemLocale(systemLocale);

            article.addArticleDescription(articleDescription);
        }

        articleDescription.setDescription(labellingLabelling.getDescription());

        baseService.saveOrUpdate(article);

        labellingTableOverview.renewTableModel(article);
        labellingTableLabelling.renewTableModel(article);

        labellingOverview.setDescription(article.getLocalizedDescription());
        overviewArticleTable.renewTableModel();
    }

    public Long getActualArticleId() {
        return actualArticleId;
    }

    public void setActualArticleId(Long actualArticleId) {
        this.actualArticleId = actualArticleId;
    }
}
