package ecobill.module.base.ui.component;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;

/**
 * Die Klasse <code>VerticalButton</code> ist ein <code>JPanel</code> mit sieben
 * <code>JButton</code> die vertikal zueinander angeordnet sind. Diese Buttons sind
 * default auf unvisible.
 * <p/>
 * User: rro
 * Date: 28.09.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: VerticalButton.java,v 1.5 2005/10/04 15:15:13 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class VerticalButton extends JPanel {

    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.ACTIONS), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private JButton button1 = new JButton();
    private JButton button2 = new JButton();
    private JButton button3 = new JButton();
    private JButton button4 = new JButton();
    private JButton button5 = new JButton();
    private JButton button6 = new JButton();
    private JButton button7 = new JButton();

    /**
     * Erzeugt eine neues Vertical Button Panel für Artikel.
     */
    public VerticalButton() {
        initComponents();
        initLayout();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        setBorder(border);

        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        button4.setVisible(false);
        button5.setVisible(false);
        button6.setVisible(false);
        button7.setVisible(false);
    }

    private void initLayout() {
        GroupLayout layout = new GroupLayout(this);

        setLayout(layout);
        
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(button1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .add(button2, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .add(button3, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .add(button4, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .add(button5, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .add(button6, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .add(button7, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(button1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(button2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(button3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(button4, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(button5, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(button6, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(button7, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    public JButton getButton1() {
        return button1;
    }

    public void setButton1(JButton button1) {
        this.button1 = button1;
    }

    public JButton getButton2() {
        return button2;
    }

    public void setButton2(JButton button2) {
        this.button2 = button2;
    }

    public JButton getButton3() {
        return button3;
    }

    public void setButton3(JButton button3) {
        this.button3 = button3;
    }

    public JButton getButton4() {
        return button4;
    }

    public void setButton4(JButton button4) {
        this.button4 = button4;
    }

    public JButton getButton5() {
        return button5;
    }

    public void setButton5(JButton button5) {
        this.button5 = button5;
    }

    public JButton getButton6() {
        return button6;
    }

    public void setButton6(JButton button6) {
        this.button6 = button6;
    }

    public JButton getButton7() {
        return button7;
    }

    public void setButton7(JButton button7) {
        this.button7 = button7;
    }

    public void reinitI18N() {
        border.setTitle(WorkArea.getMessage(Constants.ACTIONS));
    }
}
