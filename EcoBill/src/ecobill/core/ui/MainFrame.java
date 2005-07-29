package ecobill.core.ui;

import ecobill.module.base.ui.ArticleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
// @todo document me!

/**
 * MainFrame.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:43:36
 *
 * @author Roman R&auml;dle
 * @version $Id: MainFrame.java,v 1.1 2005/07/29 13:21:15 raedler Exp $
 * @since DAPS INTRA 1.0
 */
public class MainFrame extends JFrame implements ApplicationContextAware, InitializingBean {

    // @todo document me!
    protected ApplicationContext context;

    private ArticleUI articleUI;// = ArticleUI.getInstance();

    public ArticleUI getArticleUI() {
        return articleUI;
    }

    public void setArticleUI(ArticleUI articleUI) {
        this.articleUI = articleUI;
    }

    // @todo document me!
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }


    public void afterPropertiesSet() throws Exception {
         //articleUI = ArticleUI.getInstance();
        System.out.println("ARTICLE_UI: " + articleUI);

        this.setTitle("??? bundle title ???");

        this.setSize(new Dimension(800, 600));
        this.getContentPane().setLayout(new BorderLayout());

        this.initDesktopPane();
        this.initButton();

        this.setVisible(true);
    }



    boolean visible = false;

    public MainFrame() throws HeadlessException {
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initDesktopPane() {

        JDesktopPane desktopPane = new JDesktopPane();

        this.getContentPane().add(desktopPane, BorderLayout.CENTER);

        desktopPane.add(articleUI);
    }

    private void initButton() {
        JButton button = new JButton("Klick mich");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                /*
                 * Setzt die <code>ArtikelUI</code> sichtbar.
                 */
                visible = !visible;

                MainFrame.this.articleUI.setVisible(visible);
            }
        });

        this.getContentPane().add(button, BorderLayout.NORTH);
    }
}