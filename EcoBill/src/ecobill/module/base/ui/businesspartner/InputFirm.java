package ecobill.module.base.ui.businesspartner;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import java.awt.*;

/**
 * Das <code>InputFirm</code> <code>JPanel</code> stellt die Eingabemöglichkeit für zusätzliche
 * Firmendaten zur Verfügung.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: InputFirm.java,v 1.4 2005/10/05 23:41:27 raedler Exp $
 * @since EcoBill 1.0
 */
public class InputFirm extends JPanel implements Internationalization {

    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.FIRM_DATA), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private JLabel titleL = new JLabel();
    private JTextField title = new JTextField();

    private JLabel firmL = new JLabel();
    private JTextField firm = new JTextField();

    private JLabel branchL = new JLabel();
    private JTextField branch = new JTextField();

    private JCheckBox forAttentionOf = new JCheckBox();

    /**
     * Erzeugt eine neues <code>InputFirm</code> Panel.
     */
    public InputFirm() {
        initComponents();
        initLayout();

        reinitI18N();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        setBorder(border);

        title.setMinimumSize(new Dimension(120, 20));
        title.setPreferredSize(new Dimension(120, 20));

        firm.setMinimumSize(new Dimension(120, 20));
        firm.setPreferredSize(new Dimension(120, 20));

        branch.setMinimumSize(new Dimension(120, 20));
        branch.setPreferredSize(new Dimension(120, 20));

        forAttentionOf.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        forAttentionOf.setMargin(new java.awt.Insets(0, 0, 0, 0));
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
                                .add(titleL)
                                .add(title, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .add(firmL)
                                .add(firm, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .add(branchL)
                                .add(branch, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .add(forAttentionOf))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(titleL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(title, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(firmL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(firm, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(branchL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(branch, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .add(25, 25, 25)
                        .add(forAttentionOf)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        border.setTitle(WorkArea.getMessage(Constants.FIRM_DATA));

        titleL.setText(WorkArea.getMessage(Constants.TITLE));
        firmL.setText(WorkArea.getMessage(Constants.FIRM));
        branchL.setText(WorkArea.getMessage(Constants.BRANCH));
        forAttentionOf.setText(WorkArea.getMessage(Constants.FOR_ATTENTION_OF));
    }

    /**
     * Setzt die Eingabefelder zurück.
     */
    public void resetInput() {
        title.setText("");
        firm.setText("");
        branch.setText("");
        forAttentionOf.setSelected(false);
    }

    public String getTitle() {
        return title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public String getFirm() {
        return firm.getText();
    }

    public void setFirm(String firm) {
        this.firm.setText(firm);
    }

    public String getBranch() {
        return branch.getText();
    }

    public void setBranch(String branch) {
        this.branch.setText(branch);
    }

    public boolean isForAttentionOf() {
        return forAttentionOf.isSelected();
    }

    public void setForAttentionOf(boolean forAttentionOf) {
        this.forAttentionOf.setSelected(forAttentionOf);
    }
}
