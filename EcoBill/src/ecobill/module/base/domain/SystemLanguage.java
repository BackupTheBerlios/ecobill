package ecobill.module.base.domain;

import java.util.Set;
import java.util.HashSet;

// @todo document me!

/**
 * SystemLanguage.
 * <p/>
 * User: rro
 * Date: 28.09.2005
 * Time: 23:59:46
 *
 * @author Roman R&auml;dle
 * @version $Id: SystemLanguage.java,v 1.1 2005/09/30 09:01:59 raedler Exp $
 * @since EcoBill 1.0
 */
public class SystemLanguage extends AbstractI18NDomain {

    private Set<SystemCountry> systemCountries = new HashSet<SystemCountry>();

    public Set<SystemCountry> getSystemCountries() {
        return systemCountries;
    }

    public void setSystemCountries(Set<SystemCountry> systemCountries) {
        this.systemCountries = systemCountries;
    }
}
