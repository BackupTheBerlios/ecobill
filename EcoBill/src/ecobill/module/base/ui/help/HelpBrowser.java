package ecobill.module.base.ui.help;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;

// @todo document me!

/**
 * HelpBrowser.
 * <p/>
 * User: Paul Chef
 * Date: 21.10.2005
 * Time: 17:00:54
 *
 * @author Andreas Weiler
 * @version $Id: HelpBrowser.java,v 1.10 2005/10/22 16:27:27 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class HelpBrowser extends JPanel {

    //private String htmlPath = "file:\\EcoBill\\htmlfiles";
      //  private String page = htmlPath+"index.html";

        private JEditorPane browser = new JEditorPane();
        private JScrollPane sp = new JScrollPane();


        public HelpBrowser(String page) {

            createBrowser(page);
        }

        public void createBrowser(String page) {

            File localFile = new File(page);

            try
            {
               browser = new JEditorPane(localFile.toURL());
               browser.setEditable(false);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            browser.setPreferredSize(new Dimension(600, 500));
            sp = new JScrollPane(browser);

            this.add(sp);
        }
}
