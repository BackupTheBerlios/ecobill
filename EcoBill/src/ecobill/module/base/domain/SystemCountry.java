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
 * @version $Id: SystemCountry.java,v 1.1 2005/09/30 09:01:59 raedler Exp $
 * @since EcoBill 1.0
 */
public class SystemCountry extends AbstractI18NDomain {

    private SystemLanguage systemLanguage;

    public SystemLanguage getSystemLanguage() {
        return systemLanguage;
    }

    public void setSystemLanguage(SystemLanguage systemLanguage) {
        this.systemLanguage = systemLanguage;
    }
}
