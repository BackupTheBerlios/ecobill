package ecobill.module.base.domain;

// @todo document me!

/**
 * TextBlock.
 * <p/>
 * User: rro
 * Date: 10.12.2005
 * Time: 15:32:29
 *
 * @author Roman R&auml;dle
 * @version $Id: TextBlock.java,v 1.1 2005/12/11 17:17:12 raedler Exp $
 * @since EcoBill 1.1
 */
public class TextBlock extends AbstractDomain {

    private String name;

    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
