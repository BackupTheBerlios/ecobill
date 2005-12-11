package ecobill.module.base.ui.textblock;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

// @todo document me!

/**
 * Input.
 * <p/>
 * User: rro
 * Date: 10.12.2005
 * Time: 15:23:11
 *
 * @author Roman R&auml;dle
 * @version $Id: Input.java,v 1.1 2005/12/11 19:57:33 raedler Exp $
 * @since EcoBill 1.1
 */
public class Input extends JPanel {

    private JLabel nameL = new JLabel();
    private JTextField textBlockName = new JTextField();;

    private JLabel textL = new JLabel();
    private JTextArea textBlockText = new JTextArea();

    private JScrollPane textSP = new JScrollPane();

    /**
     * TODO: document me!!!
     */
    public Input() {
        initComponents();
    }

    /**
     * TODO: document me!!!
     */
    private void initComponents() {

        textBlockText.setLineWrap(true);
        textBlockText.setWrapStyleWord(true);

        // TODO: Border auslagern.
        setBorder(javax.swing.BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Eingabe", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0)));
        nameL.setText("Name");

        textBlockName.setMinimumSize(new java.awt.Dimension(11, 20));

        textL.setText("Text");

        textBlockText.setColumns(20);
        textBlockText.setRows(5);
        textSP.setViewportView(textBlockText);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.LEADING)
                    .add(GroupLayout.TRAILING, textBlockName, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .add(nameL)
                    .add(textL)
                    .add(textSP, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .add(nameL)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(textBlockName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(textL)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(textSP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    /**
     * TODO: document me!!!
     */
    public void resetInput() {
        textBlockName.setText("");
        textBlockText.setText("");
    }

    /**
     * TODO: document me!!!
     *
     * @return
     */
    public String getTextBlockName() {
        return textBlockName.getText();
    }

    /**
     * TODO: document me!!!
     *
     * @param textBlockName
     */
    public void setTextBlockName(String textBlockName) {
        this.textBlockName.setText(textBlockName);
    }

    /**
     * TODO: document me!!!
     *
     * @return
     */
    public String getTextBlockText() {
        return textBlockText.getText();
    }

    /**
     * TODO: document me!!!
     *
     * @param textBlockText
     */
    public void setTextBlockText(String textBlockText) {
        this.textBlockText.setText(textBlockText);
    }
}

