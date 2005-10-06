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
 * @version $Id: Message.java,v 1.2 2005/10/06 21:07:20 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class Message extends AbstractDomain {

    /**
     * Absender
     */
    private String addresser;

    /**
     * Betreff
     */
    private String subject;

    /**
     * Nachricht
     */
    private String message;


    public String getAddresser() {
        return addresser;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public void setAddresser(String addresser) {
        this.addresser = addresser;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
