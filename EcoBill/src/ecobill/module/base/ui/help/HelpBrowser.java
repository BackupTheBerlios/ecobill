package ecobill.module.base.ui.help;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;

// @todo document me!

/**
 * Der <code>HelpBrowser</code> erstellt den HTML-Browser f�r die HTML-Hilfeseiten
 * <p/>
 * User: Andreas Weiler
 * Date: 21.10.2005
 * Time: 17:00:54
 *
 * @author Andreas Weiler
 * @version $Id: HelpBrowser.java,v 1.14 2005/11/06 16:55:17 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class HelpBrowser extends JPanel {

    // Initialisierung der Komponenten
        private JEditorPane browser = new JEditorPane();
        private JScrollPane sp = new JScrollPane();

        /**
         * Konstruktor der den String page �bergeben bekommt
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
