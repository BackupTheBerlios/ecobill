package ecobill.module.base.ui.deliveryorder;

import ecobill.module.base.domain.ReduplicatedArticle;
import ecobill.module.base.domain.Article;
import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.ComponentUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

/**
 * Der <code>DeliveryOrderArticleDialog</code> stellt die Eingabemöglichkeit für einen Artikel zur
 * Verfügung.
 * <p/>
 * User: rro
 * Date: 16.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: DeliveryOrderArticleDialog.java,v 1.2 2006/01/29 23:16:45 raedler Exp $
 * @since EcoBill 1.0
 */
public class DeliveryOrderArticleDialog extends JDialog implements Internationalization {

    /**
     * TODO: document me!!!
     */
    private DeliveryOrderUI deliveryOrderUI;

    /**
     * TODO: document me!!!
     */
    private Article article;

    /**
     * TODO: document me!!!
     */
    private JButton cancel;
    private JTextArea labelling;
    private JLabel labellingL;
    private JScrollPane labellingScrollPane;
    private JButton ok;
    private JSpinner price;
    private JLabel priceL;
    private JSpinner quantity;
    private JLabel quantityL;
    private JTextField unit;
    private JLabel unitL;

    /**
     * TODO: document me!!!
     *
     * @param owner
     * @param deliveryOrderUI
     * @param article
     * @param modal
     * @throws HeadlessException
     */
    public DeliveryOrderArticleDialog(Frame owner, DeliveryOrderUI deliveryOrderUI, Article article, boolean modal) throws HeadlessException {
        super(owner, article != null ? article.getArticleNumber() : WorkArea.getMessage(Constants.ARTICLE), modal);

        this.deliveryOrderUI = deliveryOrderUI;
        this.article = article;

        // Initialisiert die Komponenten.
        initComponents();

        // Initilisiert das Layout.
        initLayout();

        reinitI18N();

        // Zentriert den <code>JDialog</code> auf dem Bildschirm.
        ComponentUtils.centerComponentOnScreen(this);

        setVisible(true);
    }

    /**
     * TODO: document me!!!
     *
     * @param owner
     * @param deliveryOrderUI
     * @param modal
     */
    public DeliveryOrderArticleDialog(Frame owner, DeliveryOrderUI deliveryOrderUI, boolean modal) {
        this(owner, deliveryOrderUI, null, modal);
    }

    /**
     * TODO: document me!!!
     */
    private void initComponents() {
        quantityL = new JLabel();
        quantity = new JSpinner(new SpinnerNumberModel(0D, 0D, Double.MAX_VALUE, 0.01));
        unitL = new JLabel();
        unit = new JTextField();
        priceL = new JLabel();
        price = new JSpinner(new SpinnerNumberModel(0D, 0D, Double.MAX_VALUE, 0.01));
        labellingScrollPane = new JScrollPane();
        labelling = new JTextArea();
        labellingL = new JLabel();
        cancel = new JButton("Abbrechen");
        ok = new JButton("OK");

        quantityL.setText("Menge");

        quantity.setMinimumSize(new java.awt.Dimension(80, 20));
        quantity.setPreferredSize(new java.awt.Dimension(80, 20));

        unitL.setText("Einheit");

        unit.setMinimumSize(new java.awt.Dimension(80, 20));
        unit.setPreferredSize(new java.awt.Dimension(80, 20));

        priceL.setText("Preis");

        price.setMinimumSize(new java.awt.Dimension(80, 20));
        price.setPreferredSize(new java.awt.Dimension(80, 20));

        labelling.setWrapStyleWord(true);
        labelling.setLineWrap(true);
        labelling.setColumns(20);
        labelling.setRows(5);
        labellingScrollPane.setViewportView(labelling);

        labellingL.setText("Bezeichnung");

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeliveryOrderArticleDialog.this.dispose();
            }
        });

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ReduplicatedArticle reduplicatedArticle = new ReduplicatedArticle();

                if (article != null) {
                    reduplicatedArticle.setArticleNumber(article.getArticleNumber());
                }

                reduplicatedArticle.setDescription(labelling.getText());
                reduplicatedArticle.setPrice((Double) price.getValue());
                reduplicatedArticle.setUnit(unit.getText());
                reduplicatedArticle.setQuantity((Double) quantity.getValue());
                reduplicatedArticle.setOrderPosition(deliveryOrderUI.getNextOrderPosition());

                deliveryOrderUI.addReduplicatedArticle(reduplicatedArticle);

                DeliveryOrderArticleDialog.this.dispose();
            }
        });
    }

    /**
     * TODO: document me!!!
     */
    private void initLayout() {

        GroupLayout layout = new GroupLayout(this.getContentPane());

        this.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.LEADING, false)
                    .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                            .add(quantityL)
                            .add(quantity, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                        .add(6, 6, 6)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                            .add(unitL)
                            .add(unit, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                            .add(priceL)
                            .add(price, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
                    .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(ok)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(cancel))
                    .add(labellingL)
                    .add(labellingScrollPane))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(new java.awt.Component[] {cancel, ok}, GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.LEADING)
                    .add(quantityL)
                    .add(unitL)
                    .add(priceL))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(layout.createParallelGroup(GroupLayout.LEADING)
                    .add(quantity, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                    .add(price, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                    .add(unit, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                .add(20, 20, 20)
                .add(labellingL)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(labellingScrollPane, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                .add(25, 25, 25)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(cancel)
                    .add(ok))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        this.setPreferredSize(new Dimension(280, 275));
        this.setSize(new Dimension(280, 275));
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        if (article != null) {
            price.setValue(article.getPrice());
            unit.setText(article.getUnit().toString());
            labelling.setText(article.getLocalizedDescription());
        }
    }
}
