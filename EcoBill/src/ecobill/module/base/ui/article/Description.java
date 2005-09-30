package ecobill.module.base.ui.article;

import org.jdesktop.layout.GroupLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;

import java.awt.*;

/**
 * ArticleUIOld.
 * <p/>
 * User: rro
 * Date: 28.09.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: Description.java,v 1.1 2005/09/30 09:06:01 raedler Exp $
 * @since EcoBill 1.0
 */
public class Description extends JPanel implements Internationalization {

    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.LABELLING), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private JTextArea description;
    private JScrollPane descriptionSP;

    /**
     * Erzeugt eine neues Description Panel für Artikel.
     */
    public Description() {
        initComponents();
        initLayout();
        reinitI18N();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        setBorder(border);

        descriptionSP = new JScrollPane();
        description = new JTextArea();

        description.setColumns(20);
        description.setRows(5);
        descriptionSP.setViewportView(description);
    }

    private void initLayout() {

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
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
