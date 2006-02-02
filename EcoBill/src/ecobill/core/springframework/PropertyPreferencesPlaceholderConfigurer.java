package ecobill.core.springframework;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;
import java.util.prefs.Preferences;
import java.io.IOException;

/**
 * PropertyPreferencesPlaceholderConfigurer.
 * <p/>
 * User: Romsl
 * Date: 02.02.2006
 * Time: 16:53:51
 *
 * @author Roman R&auml;dle
 * @vesion $Id: PropertyPreferencesPlaceholderConfigurer.java,v 1.1 2006/02/02 22:18:27 raedler Exp $
 * @since EcoBill 1.1
 */
public class PropertyPreferencesPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    // The <code>Preferences</code> keys for the hibernate connection.
    public final static String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";
    public final static String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";
    public final static String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
    public final static String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";

    /**
     * TODO: document me!!!
     *
     * @return
     * @throws IOException
     * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#mergeProperties()
     */
    protected Properties mergeProperties() throws IOException {
        Properties props = super.mergeProperties();

        Preferences prefs = Preferences.userNodeForPackage(getClass());

        String driverClass = prefs.get(HIBERNATE_CONNECTION_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        String url = prefs.get(HIBERNATE_CONNECTION_URL, "jdbc:mysql://localhost:3306/ecobill");
        String username = prefs.get(HIBERNATE_CONNECTION_USERNAME, "ecobill");
        String password = prefs.get(HIBERNATE_CONNECTION_PASSWORD, "ecobill");

        props.put(HIBERNATE_CONNECTION_DRIVER_CLASS, driverClass);
        props.put(HIBERNATE_CONNECTION_URL, url);
        props.put(HIBERNATE_CONNECTION_USERNAME, username);
        props.put(HIBERNATE_CONNECTION_PASSWORD, password);

        return props;
    }
}
