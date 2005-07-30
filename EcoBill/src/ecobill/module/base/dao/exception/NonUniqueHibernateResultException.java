package ecobill.module.base.dao.exception;

/**
 * Eine <code>NonUniqueHibernateResultException</code> kann geworfen werden, wenn bspw bei einer
 * Datenbankanfrage ein eindeutiges <code>Object</code> hätte zurückgeliefert werden müssen, dies
 * aber nicht geschehen ist.
 * <br/>
 * -> Es wurden mehrere <code>Object</code> zurückgeliefert, da ein Schlüssel bspw nicht eindeutig
 * war.
 * <p/>
 * User: rro
 * Date: 30.07.2005
 * Time: 11:43:36
 *
 * @author Roman R&auml;dle
 * @version $Id: NonUniqueHibernateResultException.java,v 1.1 2005/07/30 11:18:03 raedler Exp $
 * @since EcoBill 1.0
 */
public class NonUniqueHibernateResultException extends RuntimeException {

    /**
     * Zu dieser <code>NonUniqueHibernateResultException</code> muss eine Message übergeben
     * werden, die den aufgetretenen Fehler etwas genauer beschreibt.
     *
     * @see Exception#Exception(String)
     */
    public NonUniqueHibernateResultException(String message) {
        super(message);
    }
}
