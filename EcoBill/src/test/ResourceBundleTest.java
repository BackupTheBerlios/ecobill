package test;

import junit.framework.TestCase;
import junit.framework.Assert;

import java.util.ResourceBundle;
import java.util.Locale;
import java.io.InputStream;

/**
 * ResourceBundleTest.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 00:45:14
 *
 * @author Roman R&auml;dle
 * @version $Id: ResourceBundleTest.java,v 1.1 2005/07/28 21:03:54 raedler Exp $
 */
public class ResourceBundleTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testBundle() {
        ResourceBundle bundle = ResourceBundle.getBundle("test/bundle");
        Assert.assertTrue("Test".equals(bundle.getString("test.label")));
    }

    public void testLocaleBundle() {
        ResourceBundle bundle = ResourceBundle.getBundle("test/bundle", Locale.US);
        Assert.assertTrue("test".equals(bundle.getString("test.label")));
    }
}
