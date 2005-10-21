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
 * @version $Id: HelpBrowser.java,v 1.1 2005/10/21 15:04:03 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class HelpBrowser{

    private String htmlPath = "file:\\c:\\App\\de\\App\\vorlagen\\";
        private String page = htmlPath+"vorlage1.htm";

        private JEditorPane browser = null;
        private JScrollPane sp = null;
        private JPanel panel = null;

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

            panel = new JPanel();
            panel.add(sp);
        }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

}
