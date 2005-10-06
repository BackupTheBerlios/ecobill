package ecobill.module.base.ui.start;

import ecobill.core.system.Internationalization;

import javax.swing.*;

/**
 * Das <code>InputBanking</code> <code>JPanel</code> stellt die Eingabemöglichkeit für
 * Bankdaten zur Verfügung.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: List.java,v 1.1 2005/10/06 12:15:17 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class List extends JPanel implements Internationalization {

    /**
     * Erzeugt eine neues <code>List</code> Panel.
     */
    public List() {
        initComponents();

        reinitI18N();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {
    }


    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {
    }
}
