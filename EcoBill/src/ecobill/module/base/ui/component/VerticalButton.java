package ecobill.module.base.ui.component;

import org.jdesktop.layout.GroupLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;

/**
 * ArticleUIOld.
 * <p/>
 * User: rro
 * Date: 28.09.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: VerticalButton.java,v 1.1 2005/09/30 09:05:19 raedler Exp $
 * @since EcoBill 1.0
 */
public class VerticalButton extends JPanel {

    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.ACTIONS), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private JButton first = new JButton();
    private JButton back = new JButton();
    private JButton cancel = new JButton();
    private JButton change = new JButton();
    private JButton submit = new JButton();
    private JButton next = new JButton();
    private JButton last = new JButton();

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

        // TODO: Plaziere icons in package Struktur. 
        first.setIcon(new javax.swing.ImageIcon("D:\\Projects\\EcoBill\\images\\first.gif"));
        back.setIcon(new javax.swing.ImageIcon("D:\\Projects\\EcoBill\\images\\back.gif"));
        cancel.setIcon(new javax.swing.ImageIcon("D:\\Projects\\EcoBill\\images\\cancel.gif"));
        change.setIcon(new javax.swing.ImageIcon("D:\\Projects\\EcoBill\\images\\change.gif"));
        submit.setIcon(new javax.swing.ImageIcon("D:\\Projects\\EcoBill\\images\\submit.gif"));
        next.setIcon(new javax.swing.ImageIcon("D:\\Projects\\EcoBill\\images\\next.gif"));
        last.setIcon(new javax.swing.ImageIcon("D:\\Projects\\EcoBill\\images\\last.gif"));
    }

    private void initLayout() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.LEADING)
                    .add(first, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                    .add(back, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                    .add(cancel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                    .add(change, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                    .add(submit, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                    .add(next, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                    .add(last, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(first, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(back, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cancel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(change, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(submit, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(next, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(last, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    public JButton getFirst() {
        return first;
    }

    public void setFirst(JButton first) {
        this.first = first;
    }

    public JButton getBack() {
        return back;
    }

    public void setBack(JButton back) {
        this.back = back;
    }

    public JButton getCancel() {
        return cancel;
    }

    public void setCancel(JButton cancel) {
        this.cancel = cancel;
    }

    public JButton getChange() {
        return change;
    }

    public void setChange(JButton change) {
        this.change = change;
    }

    public JButton getSubmit() {
        return submit;
    }

    public void setSubmit(JButton submit) {
        this.submit = submit;
    }

    public JButton getNext() {
        return next;
    }

    public void setNext(JButton next) {
        this.next = next;
    }

    public JButton getLast() {
        return last;
    }

    public void setLast(JButton last) {
        this.last = last;
    }
}
