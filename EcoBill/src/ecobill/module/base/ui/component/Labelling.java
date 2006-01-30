package ecobill.module.base.ui.component;

import org.jdesktop.layout.GroupLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;

import java.awt.*;

/**
 * Das <code>Labelling</code> <code>JPanel</code> stellt die Eingabemöglichkeit für Bezeichnungen,
 * etc. dar.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: Labelling.java,v 1.2 2006/01/30 23:43:14 raedler Exp $
 * @since EcoBill 1.0
 */
public class Labelling extends JPanel implements Internationalization {

    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.LABELLING), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private JScrollPane descriptionSP = new JScrollPane();
    private JTextArea description = new JTextArea();

    /**
     * Erzeugt eine neues <code>Labelling</code> Panel.
     */
    public Labelling() {

        initComponents();
        initLayout();

        reinitI18N();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        setBorder(border);

        description.setColumns(20);
        description.setRows(5);

        descriptionSP.setViewportView(description);
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
                        .add(descriptionSP, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(descriptionSP, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                        .addContainerGap())
        );
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {
        border.setTitle(WorkArea.getMessage(Constants.LABELLING));

        description.setToolTipText(WorkArea.getMessage(Constants.LABELLING_TOOLTIP));
    }

    public String getDescription() {
        return description.getText();
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }
}
