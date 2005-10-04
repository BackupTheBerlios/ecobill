package ecobill.module.base.domain;

// @todo document me!

/**
 * SystemCountry.
 * <p/>
 * User: rro
 * Date: 28.09.2005
 * Time: 23:58:53
 *
 * @author Roman R&auml;dle
 * @version $Id: SystemCounty.java,v 1.1 2005/10/04 09:21:29 raedler Exp $
 * @since EcoBill 1.0
 */
public class SystemCounty extends AbstractI18NDomain implements Comparable {

    private SystemCountry systemCountry;

    public SystemCountry getSystemCountry() {
        return systemCountry;
    }

    public void setSystemCountry(SystemCountry systemCountry) {
        this.systemCountry = systemCountry;
    }

    public int compareTo(Object o) {
        return toString().compareTo(o.toString());
    }
}