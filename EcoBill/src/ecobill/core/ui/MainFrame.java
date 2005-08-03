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
 * @version $Id: MainFrame.java,v 1.5 2005/08/03 13:12:16 raedler Exp $
 * @since EcoBill 1.0
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

    public MainFrame() throws HeadlessException {
        super();
    }

    public void afterPropertiesSet() throws Exception {
        this.setTitle("Economy Bill Agenda");
        this.setSize(new Dimension(800, 600));
        this.getContentPane().setLayout(new BorderLayout());
        this.tabPane();
        this.quitButton();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void quitButton() {
        JButton qbutton = new JButton("Quit");

        qbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Quit"))
                    System.exit(0);
            }
        });

        this.getContentPane().add(qbutton, BorderLayout.SOUTH);
    }

   private void tabPane() {
        JTabbedPane jtab = new JTabbedPane();
        JComponent tab0 = new JPanel();
        JComponent tab1 = new JPanel();
        JComponent tab2 = new JPanel();
        JComponent tab3 = new JPanel();

        jtab.addTab("Start", tab0);
        jtab.addTab("Article", articleUI);
        jtab.addTab("Customer", tab2);
        jtab.addTab("Bill", tab3);

        ButtonGroup language = new ButtonGroup();
        JRadioButton german = new JRadioButton();
        JRadioButton english = new JRadioButton();

        german.setText("German");
        english.setText("English");

        language.add(german);
        language.add(english);

        tab0.add(german);
        tab0.add(english);

        this.getContentPane().add(jtab, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        try {
            frame.afterPropertiesSet();
        }
        catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}