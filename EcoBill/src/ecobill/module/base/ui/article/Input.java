package ecobill.module.base.ui.article;

import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.SystemUnit;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Das <code>Input</code> <code>JPanel</code> stellt die Eingabemöglichkeit für einen
 * Artikel zur Verfügung, die restlichen Daten werden über weitere <code>JPanel</code>
 * angeboten.
 * <p/>
 * User: rro
 * Date: 28.09.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: Input.java,v 1.4 2005/10/05 23:41:27 raedler Exp $
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

    private JLabel articleNumberL = new JLabel();
    private JTextField articleNumber = new JTextField();

    private JLabel unitL = new JLabel();
    private JComboBox unit = new JComboBox();
    private ComboBoxModel unitModel;

    private JLabel priceL = new JLabel();
    private SpinnerModel priceModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.01);
    private JSpinner price = new JSpinner(priceModel);

    private JLabel inStockL = new JLabel();
    private SpinnerModel inStockModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 1);
    private JSpinner inStock = new JSpinner(inStockModel);

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

        unitModel = new DefaultComboBoxModel(baseService.getSystemUnitsByCategory(Constants.SYSTEM_UNIT_UNIT).toArray());
        unit.setModel(unitModel);

        unit.setMinimumSize(new Dimension(80, 20));
        unit.setPreferredSize(new Dimension(80, 20));

        price.setMinimumSize(new Dimension(80, 20));
        price.setPreferredSize(new Dimension(80, 20));

        inStock.setMinimumSize(new Dimension(80, 20));
        inStock.setPreferredSize(new Dimension(80, 20));
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
                        .add(layout.createParallelGroup(GroupLayout.LEADING, false)
                                .add(articleNumberL)
                                .add(GroupLayout.LEADING, layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                                .add(GroupLayout.LEADING, layout.createSequentialGroup()
                                                        .addPreferredGap(LayoutStyle.RELATED)
                                                        .add(unit, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                                .add(unitL))
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                                .add(price, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                .add(priceL))
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(layout.createParallelGroup(GroupLayout.LEADING, false)
                                        .add(inStockL)
                                        .add(inStock, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
                                .add(articleNumber))
                        .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(articleNumberL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(articleNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(GroupLayout.LEADING, layout.createParallelGroup(GroupLayout.TRAILING)
                                        .add(unitL)
                                        .add(priceL))
                                .add(inStockL))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(GroupLayout.LEADING, layout.createParallelGroup(GroupLayout.BASELINE)
                                        .add(price, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .add(inStock, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .add(unit, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
        );
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        border.setTitle(WorkArea.getMessage(Constants.DATA));

        articleNumberL.setText(WorkArea.getMessage(Constants.ARTICLE_NR));
        articleNumber.setToolTipText(WorkArea.getMessage(Constants.ARTICLE_NR_TOOLTIP));
        unitL.setText(WorkArea.getMessage(Constants.UNIT));
        unit.setToolTipText(WorkArea.getMessage(Constants.UNIT_TOOLTIP));
        priceL.setText(WorkArea.getMessage(Constants.PRICE));
        price.setToolTipText(WorkArea.getMessage(Constants.PRICE_TOOLTIP));
        inStockL.setText(WorkArea.getMessage(Constants.IN_STOCK));
        inStock.setToolTipText(WorkArea.getMessage(Constants.IN_STOCK_TOOLTIP));
    }

    public String getArticleNumber() {
        return articleNumber.getText();
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber.setText(articleNumber);
    }

    public SystemUnit getUnit() {
        return (SystemUnit) unit.getSelectedItem();
    }

    public void setUnit(SystemUnit unit) {
        this.unit.setSelectedItem(unit);
    }

    public Double getPrice() {
        return (Double) price.getValue();
    }

    public void setPrice(Double price) {
        this.price.setValue(price);
    }

    public Double getInStock() {
        return (Double) inStock.getValue();
    }

    public void setInStock(Double inStock) {
        this.inStock.setValue(inStock);
    }
}
