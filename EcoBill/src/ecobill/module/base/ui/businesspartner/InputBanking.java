package ecobill.module.base.ui.businesspartner;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.layout.GroupLayout;

import java.awt.*;

/**
 * Das <code>InputBanking</code> <code>JPanel</code> stellt die Eingabemöglichkeit für
 * Bankdaten zur Verfügung.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: InputBanking.java,v 1.5 2005/10/05 23:41:27 raedler Exp $
 * @since EcoBill 1.0
 */
public class InputBanking extends JPanel implements Internationalization {

    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.BANK_DATA), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private JLabel bankEstablishmentL = new JLabel();
    private JTextField bankEstablishment = new JTextField();

    private JLabel bankIdentificationNumberL = new JLabel();
    private JTextField bankIdentificationNumber = new JTextField();

    private JLabel accountNumberL = new JLabel();
    private JTextField accountNumber = new JTextField();

    /**
     * Erzeugt eine neues <code>InputFirm</code> Panel.
     */
    public InputBanking() {
        initComponents();
        initLayout();

        reinitI18N();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        setBorder(border);

        bankEstablishment.setMinimumSize(new Dimension(120, 20));
        bankEstablishment.setPreferredSize(new Dimension(120, 20));

        bankIdentificationNumber.setMinimumSize(new Dimension(120, 20));
        bankIdentificationNumber.setPreferredSize(new Dimension(120, 20));

        accountNumber.setMinimumSize(new Dimension(120, 20));
        accountNumber.setPreferredSize(new Dimension(120, 20));
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
                                .add(bankEstablishmentL)
                                .add(bankEstablishment, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                .add(bankIdentificationNumberL)
                                .add(bankIdentificationNumber, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                .add(accountNumberL)
                                .add(accountNumber, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(bankEstablishmentL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(bankEstablishment, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(bankIdentificationNumberL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(bankIdentificationNumber, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(accountNumberL)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(accountNumber, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        border.setTitle(WorkArea.getMessage(Constants.BANK_DATA));

        bankEstablishmentL.setText(WorkArea.getMessage(Constants.BANK));
        bankIdentificationNumberL.setText(WorkArea.getMessage(Constants.BANK_CODE));
        accountNumberL.setText(WorkArea.getMessage(Constants.ACCOUNT_NUMBER));
    }

    /**
     * Setzt die Eingabefelder zurück.
     */
    public void resetInput() {
        bankEstablishment.setText("");
        bankIdentificationNumber.setText("");
        accountNumber.setText("");
    }

    public String getBankEstablishment() {
        return bankEstablishment.getText();
    }

    public void setBankEstablishment(String bankEstablishment) {
        this.bankEstablishment.setText(bankEstablishment);
    }

    public String getBankIdentificationNumber() {
        return bankIdentificationNumber.getText();
    }

    public void setBankIdentificationNumber(String bankIdentificationNumber) {
        this.bankIdentificationNumber.setText(bankIdentificationNumber);
    }

    public String getAccountNumber() {
        return accountNumber.getText();
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber.setText(accountNumber);
    }
}
