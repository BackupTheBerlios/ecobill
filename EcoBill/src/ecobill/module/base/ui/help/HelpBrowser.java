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
 * @version $Id: HelpBrowser.java,v 1.13 2005/10/28 15:44:28 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class HelpBrowser extends JPanel {

    // Initialisierung der Komponenten
        private JEditorPane browser = new JEditorPane();
        private JScrollPane sp = new JScrollPane();

        /**
         * Konstruktor der den String page übergeben bekommt
         */
        public HelpBrowser(String page) {

            createBrowser(page);
        }
        /**
         * Methode wird im Konstruktor aufgerufen um
         * den Browser zu generieren
         */
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

            browser.setPreferredSize(new Dimension(600, 400));
            sp.setPreferredSize(new Dimension(600,400));
            sp.setViewportView(browser);

            this.add(sp);
        }
}
