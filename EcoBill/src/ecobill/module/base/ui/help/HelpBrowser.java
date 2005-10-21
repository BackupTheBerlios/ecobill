package ecobill.module.base.ui.help;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;

// @todo document me!

/**
 * HelpBrowser.
 * <p/>
 * User: Paul Chef
 * Date: 21.10.2005
 * Time: 17:00:54
 *
 * @author Andreas Weiler
 * @version $Id: HelpBrowser.java,v 1.2 2005/10/21 15:09:14 raedler Exp $
 * @since EcoBill 1.0
 */
public class HelpBrowser extends JPanel {

    private String htmlPath = "file:\\c:\\App\\de\\App\\vorlagen\\";
        private String page = htmlPath+"vorlage1.htm";

        private JEditorPane browser = null;
        private JScrollPane sp = null;

        public HelpBrowser()
        {
            browser = new JEditorPane();
            browser.setEditable(false);
        }

        public void createBrowser()
        {
            File localFile = new File(page);

            try
            {
                browser.setPage(page);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            browser.setPreferredSize(new Dimension(400, 300));
            sp = new JScrollPane(browser);

            add(sp);
        }
}
