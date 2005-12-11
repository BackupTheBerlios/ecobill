package ecobill.module.base.ui.deliveryorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.ui.textblock.TextBlockDialog;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;
import ecobill.core.ui.MainFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Calendar;

/**
 * Das <code>DeliveryOrderData</code> <code>JPanel</code> stellt die Eingabemöglichkeit für die
 * Lieferscheindaten zur Verfügung, die restlichen Daten werden über weitere <code>JPanel</code>
 * angeboten.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: DeliveryOrderData.java,v 1.5 2005/12/11 17:16:01 raedler Exp $
 * @since EcoBill 1.0
 */
public class DeliveryOrderData extends JPanel implements Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    protected final static Log LOG = LogFactory.getLog(DeliveryOrderData.class);

    /**
     * Der <code>MainFrame</code> ist der Vaterframe aller <code>Components</code>.
     */
    private MainFrame mainFrame;

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

    /**
     * Erzeugt eine neues <code>DeliveryOrderData</code> Panel.
     */
    public DeliveryOrderData(MainFrame mainFrame, BaseService baseService) {

        this.mainFrame = mainFrame;
        this.baseService = baseService;

        initComponents();
        initLayout();

        reinitI18N();
    }

    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.DATA), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private JLabel deliveryOrderNumberL = new JLabel();
    private JTextField deliveryOrderNumber = new JTextField();

    private JLabel deliveryOrderDateL = new JLabel();
    private JSpinner deliveryOrderDate = new JSpinner();

    private JLabel typeL = new JLabel();
    private JComboBox type = new JComboBox();

    private JLabel prefixL = new JLabel();
    private JScrollPane prefixScrollPane = new JScrollPane();
    private JTextArea prefix = new JTextArea();

    private JLabel suffixL = new JLabel();
    private JScrollPane suffixScrollPane = new JScrollPane();
    private JTextArea suffix = new JTextArea();

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        setBorder(border);

        deliveryOrderNumber.setMinimumSize(new Dimension(80, 20));
        deliveryOrderNumber.setPreferredSize(new Dimension(80, 20));

        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateModel.setCalendarField(Calendar.ERA);
        deliveryOrderDate.setModel(dateModel);
        deliveryOrderDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
        deliveryOrderDate.setMinimumSize(new Dimension(80, 20));
        deliveryOrderDate.setPreferredSize(new Dimension(80, 20));

        type.setMinimumSize(new Dimension(120, 20));
        type.setPreferredSize(new Dimension(120, 20));

        prefix.setColumns(20);
        prefix.setRows(5);
        prefixScrollPane.setViewportView(prefix);

        suffix.setColumns(20);
        suffix.setRows(5);
        suffixScrollPane.setViewportView(suffix);
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
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(suffixScrollPane, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                                .addContainerGap())
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(layout.createParallelGroup(GroupLayout.LEADING)
                                        .add(prefixScrollPane, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                                                .add(layout.createParallelGroup(GroupLayout.LEADING)
                                                        .add(deliveryOrderNumber, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                        .add(deliveryOrderNumberL))
                                                .addPreferredGap(LayoutStyle.RELATED)
                                                .add(layout.createParallelGroup(GroupLayout.LEADING)
                                                        .add(deliveryOrderDate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                        .add(deliveryOrderDateL))
                                                .addPreferredGap(LayoutStyle.RELATED)
                                                .add(layout.createParallelGroup(GroupLayout.LEADING)
                                                .add(typeL)
                                                .add(type, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                                        .add(prefixL))
                                .addContainerGap())
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(suffixL)
                        .addContainerGap(274, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(GroupLayout.TRAILING)
                                .add(GroupLayout.TRAILING, layout.createParallelGroup(GroupLayout.BASELINE)
                                        .add(deliveryOrderNumberL)
                                        .add(deliveryOrderDateL))
                                .add(typeL))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(deliveryOrderNumber, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .add(deliveryOrderDate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .add(type, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(prefixL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(prefixScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(suffixL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(suffixScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        border.setTitle(WorkArea.getMessage(Constants.DATA));

        deliveryOrderNumberL.setText(WorkArea.getMessage(Constants.DELIVERY_ORDER_NUMBER));
        deliveryOrderDateL.setText(WorkArea.getMessage(Constants.DATE));
        typeL.setText(WorkArea.getMessage(Constants.TYPE));
        prefixL.setText(WorkArea.getMessage(Constants.TEXT_PREFIX));
        suffixL.setText(WorkArea.getMessage(Constants.TEXT_SUFFIX));
    }

    public String getDeliveryOrderNumber() {
        return deliveryOrderNumber.getText();
    }

    public void setDeliveryOrderNumber(String deliveryOrderNumber) {
        this.deliveryOrderNumber.setText(deliveryOrderNumber);
    }

    public Date getDeliveryOrderDate() {
        return (Date) deliveryOrderDate.getValue();
    }

    public void setDeliveryOrderDate(Date deliveryOrderDate) {
        this.deliveryOrderDate.setValue(deliveryOrderDate);
    }

    public String getPrefixText() {
        return prefix.getText();
    }

    public void setPrefixText(String prefix) {
        this.prefix.setText(prefix);
    }

    public String getSuffixText() {
        return suffix.getText();
    }

    public void setSuffixText(String suffix) {
        this.suffix.setText(suffix);
    }

    public JTextArea getPrefix() {
        return prefix;
    }

    public JTextArea getSuffix() {
        return suffix;
    }
}
