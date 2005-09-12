package ecobill.core.system;

/**
 * Das Interface <code>Internationalization</code> bietet für Klassen,
 * die dieses Interface implementieren, die Möglichkeit mit einer
 * anderen landesspezifischen Sprache reinitialisiert zu werden.
 * <p/>
 * User: rro
 * Date: 08.08.2005
 * Time: 19:53:52
 *
 * @author Roman R&auml;dle
 * @version $Id: Internationalization.java,v 1.3 2005/09/12 20:43:04 raedler Exp $
 * @since EcoBill 1.0
 */
public interface Internationalization {

    /**
     * Reninitialisiert ein <code>Object</code> deren Klasse dieses
     * Interface implementiert.
     */
    public void reinitI18N();
}
