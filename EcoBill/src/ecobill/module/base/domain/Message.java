package ecobill.module.base.domain;

// @todo document me!

/**
 * Message.
 * <p/>
 * User: Paul Chef
 * Date: 06.10.2005
 * Time: 23:04:58
 *
 * @author Andreas Weiler
 * @version $Id: Message.java,v 1.1 2005/10/06 21:03:54 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class Message extends AbstractDomain {

    /**
     * Absender
     */
    public String addresser;

    /**
     * Betreff
     */
    public String subject;

    /**
     * Nachricht
     */
    public String message;
}
