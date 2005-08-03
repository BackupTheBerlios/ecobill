package ecobill.core.ui;

import ecobill.module.base.ui.ArticleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

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
 * @version $Id: MainFrame.java,v 1.13 2005/08/03 18:12:59 jfuckerweiler Exp $
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
        this.jMenuBar();
        this.tabPane();
        this.quitButton();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void quitButton() {

        //erstellt Quit Button
        JButton qbutton = new JButton("Exit");

        // Quit Button Action Listener
        qbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Exit"))
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
        jtab.addTab("Artikel", tab1);
        jtab.addTab("Kunden", tab2);
        jtab.addTab("Rechnungen", tab3);


        JLabel lab1 = new JLabel(new ImageIcon("Startbild.jpg"));
        lab1.setToolTipText("Copyright @ JFuckers");
        lab1.setVisible(true);
        JLabel descrip1 = new JLabel("Effiziente und Effektive Kunden-, Artikel- und " +
                "Rechnungsverwaltung mit Economy Bill Agenda");

        tab0.add(lab1, BorderLayout.CENTER);
        tab0.add(descrip1, BorderLayout.SOUTH);

        JLabel descrip2 = new JLabel("Hier kommt die ArtikelGui rein");
        tab1.add(descrip2);

        JLabel descrip3 = new JLabel("Hier kommt die KundenGui rein");
        tab2.add(descrip3);

        JLabel descrip4 = new JLabel("Hier kommt die RechnungsGui bzw. Rechnungen rein");
        tab3.add(descrip4);


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
        file.setMnemonic(KeyEvent.VK_F);


        // erstellt DateiMenü
        JMenuItem product = new JMenuItem("New Product", 'R');
        JMenuItem customer = new JMenuItem("New Customer", 'M');
        JMenuItem open = new JMenuItem("Open", 'O');
        JMenuItem save = new JMenuItem("Save", 'S');
        JMenuItem saveas = new JMenuItem("Save As");
        product.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        customer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        file.add(product);
        file.add(customer);
        file.add(open);
        file.addSeparator();
        file.add(save);
        file.add(saveas);

        // Quit Action Listener
        JMenuItem quit = new JMenuItem("Exit", 'X');
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));

        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Exit"))
                    System.exit(0);
            }
        });

        file.add(quit);

        // erstellt MenuItem Bearbeiten
        JMenu edit = new JMenu("Edit");
        menuBar.add(edit);
        edit.setMnemonic(KeyEvent.VK_E);

        // erstellt BearbeitenMenü
        JMenuItem undo = new JMenuItem("Undo", 'U');
        JMenuItem redo = new JMenuItem("Redo", 'R');
        JMenuItem cut = new JMenuItem("Cut", 'T');
        JMenuItem copy = new JMenuItem("Copy", 'C');
        JMenuItem paste = new JMenuItem("Paste", 'P');
        JMenuItem delete = new JMenuItem("Delete", 'D');
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.ALT_DOWN_MASK));
        edit.add(undo);
        edit.add(redo);
        edit.addSeparator();
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete);

        // erstellt MenuItem Hilfe
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        help.setMnemonic(KeyEvent.VK_H);

        // erstellt HilfeMenü
        JMenuItem ht = new JMenuItem("Help Topics", 'H');
        JMenuItem about = new JMenuItem("About", 'A');
        ht.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        help.add(ht);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        help.add(about);

        // erstellt MenuItem Sprache
        JMenu language = new JMenu("Language");
        menuBar.add(language);
        language.setMnemonic(KeyEvent.VK_L);

        // erstellt SprachMenü
        ButtonGroup lang = new ButtonGroup();
        JCheckBoxMenuItem german = new JCheckBoxMenuItem("German");
        german.setState(true);
        german.setMnemonic(KeyEvent.VK_G);
        german.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
        JCheckBoxMenuItem english = new JCheckBoxMenuItem("English");
        english.setState(false);
        english.setMnemonic(KeyEvent.VK_E);
        english.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        lang.add(german);
        lang.add(english);
        language.add(german);
        language.add(english);

    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        try {
            frame.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}