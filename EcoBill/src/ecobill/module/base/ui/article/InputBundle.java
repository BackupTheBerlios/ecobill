package ecobill.module.base.ui.article;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.SystemUnit;

/**
 * Das <code>InputBundle</code> <code>JPanel</code> stellt die Eingabemöglichkeit für
 * Gebindedaten zur Verfügung.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: InputBundle.java,v 1.4 2005/10/05 23:41:27 raedler Exp $
 * @since EcoBill 1.0
 */
public class InputBundle extends JPanel implements Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    protected final static Log LOG = LogFactory.getLog(InputBundle.class);

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

    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.BUNDLE), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private JLabel unitL = new JLabel();
    private ComboBoxModel unitModel;
    private JComboBox unit = new JComboBox();

    private JLabel capacityL = new JLabel();
    private SpinnerModel capacityModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.01);
    private JSpinner capacity = new JSpinner(capacityModel);

    /**
     * Erzeugt eine neues <code>InputBundle</code> Panel.
     */
    public InputBundle(BaseService baseService) {

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

        unitModel = new DefaultComboBoxModel(baseService.getSystemUnitsByCategory(Constants.SYSTEM_UNIT_BUNDLE_UNIT).toArray());
        unit.setModel(unitModel);

        unit.setMinimumSize(new Dimension(80, 20));
        unit.setPreferredSize(new Dimension(80, 20));

        capacity.setMinimumSize(new Dimension(80, 20));
        capacity.setPreferredSize(new Dimension(80, 20));
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
                                .add(unitL)
                                .add(unit, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .add(capacityL)
                                .add(capacity, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(unitL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(unit, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(capacityL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(capacity, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        border.setTitle(WorkArea.getMessage(Constants.BUNDLE));

        unitL.setText(WorkArea.getMessage(Constants.UNIT));
        unit.setToolTipText(WorkArea.getMessage(Constants.UNIT_TOOLTIP));
        capacityL.setText(WorkArea.getMessage(Constants.BUNDLE_CAPACITY));
        capacity.setToolTipText(WorkArea.getMessage(Constants.BUNDLE_CAPACITY_TOOLTIP));
    }

    public SystemUnit getUnit() {
        return (SystemUnit) unit.getSelectedItem();
    }

    public void setUnit(SystemUnit unit) {
        this.unit.setSelectedItem(unit);
    }

    public Double getCapacity() {
        return (Double) capacity.getValue();
    }

    public void setCapacity(Double content) {
        this.capacity.setValue(content);
    }
}
