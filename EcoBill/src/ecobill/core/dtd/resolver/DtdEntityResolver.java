package ecobill.core.dtd.resolver;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

// @todo document me!
/**
 * DtdEntityResolver.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 14:01:14
 *
 * @author Roman R&auml;dle
 * @version $Id: DtdEntityResolver.java,v 1.1 2005/07/29 13:21:15 raedler Exp $
 */
public class DtdEntityResolver implements EntityResolver {
    private static final Log LOG = LogFactory.getLog(DtdEntityResolver.class);

    public static final String DEFAULT_SEARCH_PACKAGE = "ecobill/core/dtd/";

    private String searchPackage = DEFAULT_SEARCH_PACKAGE;
    private String dtdName;

    public DtdEntityResolver(String dtdName) {
        this.dtdName = dtdName;
    }

    public DtdEntityResolver(String dtdName, String searchPackage) {
        this(dtdName);
        this.searchPackage = searchPackage;
    }

    /**
     * @see EntityResolver#resolveEntity(String, String)
     */
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Trying to resolve XML entity with public ID [" + publicId + "] and system ID [" + systemId + "]");
        }

        if (systemId != null && systemId.indexOf(dtdName) > systemId.lastIndexOf("/")) {
            String dtdFile = systemId.substring(systemId.indexOf(dtdName));
            // Search for DTD
            LOG.debug("Trying to locate [" + dtdFile + "] under [" + searchPackage + "]");
            InputStream is = (new ClassPathResource(searchPackage + dtdFile)).getInputStream();
            if (is != null) {
                LOG.debug("Found DTD [" + systemId + "] in class path");
                InputSource source = new InputSource(is);
                source.setPublicId(publicId);
                source.setSystemId(systemId);
                return source;
            }
            else {
                LOG.debug("Could not resolve DTD [" + systemId + "]: not found in class path");
            }
        }
        // use the default behaviour -> download from website or whatever
        return null;
    }
}
