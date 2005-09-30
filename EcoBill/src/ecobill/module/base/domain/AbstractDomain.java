package ecobill.module.base.domain;

import java.io.Serializable;

/**
 * Jede Klasse, deren Objekte persistent abgelegt werden sollen, müssen von dieser Klasse
 * <code>AbstractDomain</code> abgeleitet sein.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 11:31:31
 *
 * @author Roman R&auml;dle
 * @version $Id: AbstractDomain.java,v 1.2 2005/09/30 09:01:59 raedler Exp $
 * @since EcoBill 1.0
 */
public abstract class AbstractDomain implements Serializable {

    /**
     * Die persistente ID unter der dieses Objekt in der
     * Datenbank abgelegt ist.
     */
    private Long id;

    /**
     * Gibt die persistente ID des Objektes zurück.
     *
     * @return Die persistente ID des Objektes.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setzt die persistente ID des Objektes.
     *
     * @param id Die persistente ID des Objektes.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
