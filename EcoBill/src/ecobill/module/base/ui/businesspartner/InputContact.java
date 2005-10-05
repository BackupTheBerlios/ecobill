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
 * Das <code>InputContact</code> <code>JPanel</code> stellt die Eingabemöglichkeit für
 * Kontaktdaten zur Verfügung.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: InputContact.java,v 1.5 2005/10/05 23:41:27 raedler Exp $
 * @since EcoBill 1.0
 */
public class InputContact extends JPanel implements Internationalization {

    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.CONTACT), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private JLabel phoneL = new JLabel();
    private JTextField phone = new JTextField();

    private JLabel faxL = new JLabel();
    private JTextField fax = new JTextField();

    private JLabel emailL = new JLabel();
    private JTextField email = new JTextField();

    /**
     * Erzeugt eine neues <code>InputContact</code> Panel.
     */
    public InputContact() {
        initComponents();
        initLayout();

        reinitI18N();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        setBorder(border);

        phone.setMinimumSize(new Dimension(120, 20));
        phone.setPreferredSize(new Dimension(120, 20));

        fax.setMinimumSize(new Dimension(120, 20));
        fax.setPreferredSize(new Dimension(120, 20));

        email.setMinimumSize(new Dimension(120, 20));
        email.setPreferredSize(new Dimension(120, 20));
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
                                .add(phoneL)
                                .add(phone, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                .add(faxL)
                                .add(fax, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                .add(emailL)
                                .add(email, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(phoneL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(phone, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(faxL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(fax, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(emailL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(email, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        border.setTitle(WorkArea.getMessage(Constants.CONTACT));

        phoneL.setText(WorkArea.getMessage(Constants.PHONE));
        faxL.setText(WorkArea.getMessage(Constants.FAX));
        emailL.setText(WorkArea.getMessage(Constants.EMAIL));
    }

    /**
     * Setzt die Eingabefelder zurück.
     */
    public void resetInput() {
        phone.setText("");
        fax.setText("");
        email.setText("");
    }

    public String getPhone() {
        return phone.getText();
    }

    public void setPhone(String phone) {
        this.phone.setText(phone);
    }

    public String getFax() {
        return fax.getText();
    }

    public void setFax(String fax) {
        this.fax.setText(fax);
    }

    public String getEmail() {
        return email.getText();
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }
}
