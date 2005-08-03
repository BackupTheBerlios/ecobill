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
 * @version $Id: MainFrame.java,v 1.7 2005/08/03 15:06:34 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class MainFrame extends JFrame implements ApplicationContextAware, InitializingBean  {

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
        this.jMenuBar();
        this.tabPane();
        this.quitButton();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void quitButton() {

        //erstellt Quit Button
        JButton qbutton = new JButton("Quit");

        // Quit Button Action Listener
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

       // erstellt TabFeld
        JTabbedPane jtab = new JTabbedPane();
        JComponent tab0 = new JPanel();
        JComponent tab1 = new JPanel();
        JComponent tab2 = new JPanel();
        JComponent tab3 = new JPanel();

        jtab.addTab("Start", tab0);
        jtab.addTab("Article", tab1);
        jtab.addTab("Customer", tab2);
        jtab.addTab("Bill", tab3);

        JLabel lab = new JLabel(new ImageIcon("Startbild.jpg"));
        lab.setVisible(true);
        JLabel lab1 = new JLabel("Copyright @ JFuckers");
        tab0.add(lab);
        tab0.add(lab1);
        this.getContentPane().add(jtab, BorderLayout.CENTER);
    }


    public void jMenuBar() {

        // erstellt MenuBar
        JMenuBar menuBar = new JMenuBar();
        MainFrame.this.setJMenuBar(menuBar);
        menuBar.setVisible(true);

        // erstellt MenuItem Datei
        JMenu file = new JMenu("File");
        menuBar.add(file);

         // erstellt DateiMenü
        file.add("New Product");
        file.add("New Customer");
        file.add("Open");
        file.addSeparator();
        file.add("Save");
        file.add("Save As");

        // Quit Action Listener
        file.add(new AbstractAction("Quit") {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Quit"))
                    System.exit(0);
            }
        });


        // erstellt MenuItem Bearbeiten
        JMenu edit = new JMenu("Edit");
        menuBar.add(edit);

        // erstellt BearbeitenMenü
        edit.add("Undo");
        edit.add("Redo");
        edit.addSeparator();
        edit.add("Cut");
        edit.add("Copy");
        edit.add("Paste");
        edit.addSeparator();
        edit.add("Delete");

        // erstellt MenuItem Hilfe
        JMenu help = new JMenu("Help");
        menuBar.add(help);

        // erstellt HilfeMenü
        help.add("Help Topics");
        help.add("About");

        // erstellt MenuItem Sprache
        JMenu language = new JMenu("Language");
        menuBar.add(language);

        // erstellt SprachMenü
        ButtonGroup lang = new ButtonGroup();
        JCheckBoxMenuItem german = new JCheckBoxMenuItem("German");
        german.setState(true);
        JCheckBoxMenuItem english = new JCheckBoxMenuItem("English");
        english.setState(false);
        lang.add(german);
        lang.add(english);
        language.add(german);
        language.add(english);

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