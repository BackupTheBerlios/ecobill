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
 * @version $Id: HelpBrowser.java,v 1.4 2005/10/22 14:12:27 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class HelpBrowser extends JPanel {

    private String htmlPath = "file:\\EcoBill\\htmlfiles";
      //  private String page = htmlPath+"index.html";

        private JEditorPane browser = null;
        private JScrollPane sp = null;

        public HelpBrowser(String bummel)
        {
            browser = new JEditorPane();
            browser.setEditable(false);
            String page = htmlPath+bummel;
            createBrowser(page);
        }

        public void createBrowser(String page)
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
