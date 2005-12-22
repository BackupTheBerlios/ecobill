package ecobill.module.base.ui.component;

import javax.swing.*;
import java.awt.*;

// TODO: document me!!!

/**
 * JToolBarButton.
 * <p/>
 * User: rro
 * Date: 20.12.2005
 * Time: 19:37:32
 *
 * @author Roman R&auml;dle
 * @version $Id: JToolBarButton.java,v 1.1 2005/12/22 12:52:54 raedler Exp $
 * @since EcoBill 1.1
 */
public class JToolBarButton extends JButton {

    public JToolBarButton() {
    }

    public JToolBarButton(Icon icon) {
        super(icon);
        setMaximumSize(new Dimension(icon.getIconWidth() + 6, icon.getIconHeight() + 6));
    }

    public JToolBarButton(String text) {
        super(text);
    }

    public JToolBarButton(Action a) {
        super(a);
    }

    public JToolBarButton(String text, Icon icon) {
        super(text, icon);
        setMaximumSize(new Dimension(icon.getIconWidth() + 6, icon.getIconHeight() + 6));
    }
}
