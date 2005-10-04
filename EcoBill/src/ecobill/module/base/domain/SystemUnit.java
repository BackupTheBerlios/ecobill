package ecobill.module.base.domain;

import ecobill.core.system.WorkArea;

/**
 * Die <code>SystemUnit</code> kann über einen landesunabhängigen Schlüssel die
 * jeweilige landesspezifische Einheit über die <code>WorkArea</code> herausholen.
 * <p/>
 * User: rro
 * Date: 31.07.2005
 * Time: 17:19:20
 *
 * @author Roman R&auml;dle
 * @version $Id: SystemUnit.java,v 1.5 2005/10/04 09:21:29 raedler Exp $
 * @since EcoBill 1.0
 */
public class SystemUnit extends AbstractI18NDomain {

    /**
     * Die Kategorie zu der diese <code>SystemUnit</code> angehört.
     */
    private String category;

    /**
     * Gibt die Kategorie zu der diese <code>SystemUnit</code> angehört, zurück.
     *
     * @return Die Kategorie zu der diese <code>SystemUnit</code> angehört.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setzt die Kategorie zu der diese <code>SystemUnit</code> angehört.
     *
     * @param category Die Kategorie zu der diese <code>SystemUnit</code> angehört.
     */
    public void setCategory(String category) {
        this.category = category;
    }
}
