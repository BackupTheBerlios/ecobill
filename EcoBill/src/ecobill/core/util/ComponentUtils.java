package ecobill.core.util;

import java.awt.*;

// @todo document me!

/**
 * ComponentUtils.
 * <p/>
 * User: rro
 * Date: 10.12.2005
 * Time: 16:15:03
 *
 * @author Roman R&auml;dle
 * @version $Id: ComponentUtils.java,v 1.1 2005/12/11 17:17:12 raedler Exp $
 * @since EcoBill 1.1
 */
public class ComponentUtils {

    /**
     * TODO: document me!!!
     *
     * @param c
     */
    public static void centerComponentOnScreen(Component c) {

        // Größe der eingestellten Bildschirmauflösung.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        // Größe des <code>JFrame</code>.
        Dimension size = c.getSize();

        width -= size.getWidth();
        height -= size.getHeight();

        c.setLocation((int) width / 2, (int) height / 2);
    }
}
