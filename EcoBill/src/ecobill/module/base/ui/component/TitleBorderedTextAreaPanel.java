package ecobill.module.base.ui.component;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Internationalization;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import org.jdesktop.layout.GroupLayout;

// @todo document me!

/**
 * TitleBorderedTextAreaPanel.
 * <p/>
 * User: rro
 * Date: 10.12.2005
 * Time: 20:45:55
 *
 * @author Roman R&auml;dle
 * @version $Id: TitleBorderedTextAreaPanel.java,v 1.1 2005/12/11 17:17:12 raedler Exp $
 * @since EcoBill 1.1
 */
public class TitleBorderedTextAreaPanel extends JPanel implements Internationalization {

    /**
     * Die Konstante für den <code>JPanel</code> Rahmen.
     */
    private final String BORDER_TITLE_KEY;

    /**
     * Der Rahmen der im <code>JPanel</code> liegt bzw. um die <code>JTextArea</code>
     * gelegt wird.
     */
    private TitledBorder border;

    /**
     * Die <code>JScrollPane</code> um mit der <code>JTextArea</code> scrollen zu können.
     */
    private JScrollPane textAreaSP = new JScrollPane();

    /**
     * Die <code>JTextArea</code> die den Text enthält bzw. enthalten kann.
     */
    private JTextArea textArea = new JTextArea();

    /** Creates new form TitleBorderedTextAreaPanel */
    public TitleBorderedTextAreaPanel(final String BORDER_TITLE_KEY) {

        // Setzt den Konstanten BORDER_TITLE_KEY.
        this.BORDER_TITLE_KEY = BORDER_TITLE_KEY;

        // Initialisiert den Rahmen zum ersten Mal.
        border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(BORDER_TITLE_KEY), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

        // Initialisiert die Komponenten.
        initComponents();

        // Initialisiert das Layout.
        initLayout();
    }

    /**
     * TODO: document me!!!
     */
    private void initComponents() {

        // Setzt den Rahmen für das <code>JPanel</code>.
        setBorder(border);

        textArea.setColumns(20);
        textArea.setRows(5);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textAreaSP.setViewportView(textArea);
    }

    /**
     * TODO: document me!!!
     */
    private void initLayout() {

        GroupLayout layout = new GroupLayout(this);

        setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(textAreaSP, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .add(textAreaSP, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        // Erneuert den Titel des <code>JPanel</code> in die landesspezifisch
        // eingestellte Sprache.
        border.setTitle(WorkArea.getMessage(BORDER_TITLE_KEY));
    }

    /**
     * TODO: document me!!!
     *
     * @return
     */
    public JTextArea getTextArea() {
        return textArea;
    }
}
