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
 * @version $Id: MainFrame.java,v 1.17 2005/08/03 20:52:52 jfuckerweiler Exp $
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

        // erstellt Mainframe
        this.setTitle("Economy Bill Agenda");
        this.setSize(new Dimension(800, 600));
        this.getContentPane().setLayout(new BorderLayout());
        this.jMenuBar();
        this.tabPane();
        this.exitButton();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void exitButton() {

        //erstellt Exit Button
        JButton ebutton = new JButton("Exit");

        // Exit Button Action Listener
        ebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Exit"))
                    System.exit(0);
            }
        });

        // f�gt ExitButton hinzu
        this.getContentPane().add(ebutton, BorderLayout.SOUTH);
    }

    public void tabPane() {

        // erstellt TabFeld
        JTabbedPane jtab = new JTabbedPane();
        JComponent tab0 = new JPanel();
        JComponent tab1 = new JPanel();
        JComponent tab2 = new JPanel();
        JComponent tab3 = new JPanel();

        // f�gt Tabs dem Tabfeld hinzu
        jtab.addTab("Start", tab0);
        jtab.addTab("Artikel", tab1);
        jtab.addTab("Kunden", tab2);
        jtab.addTab("Rechnungen", tab3);


        // erstellt JLabels
        JLabel lab1 = new JLabel(new ImageIcon("Startbild.jpg"));
        lab1.setToolTipText("Copyright @ JFuckers");
        lab1.setVisible(true);
        JLabel descrip1 = new JLabel("Effiziente und Effektive Kunden-, Artikel- und " +
                "Rechnungsverwaltung mit Economy Bill Agenda");

        // f�gt JLabels tab0 zu
        tab0.add(lab1, BorderLayout.CENTER);
        tab0.add(descrip1, BorderLayout.SOUTH);

        // f�gt JLabel tab1 zu
        JLabel descrip2 = new JLabel("Hier kommt die ArtikelGui rein");
        tab1.add(descrip2);

        // f�gt JLabel tab2 zu
        JLabel descrip3 = new JLabel("Hier kommt die KundenGui rein");
        tab2.add(descrip3);

        // f�gt JLabel tab3 zu
        JLabel descrip4 = new JLabel("Hier kommt die RechnungsGui bzw. Rechnungen rein");
        tab3.add(descrip4);

        // f�gt Tabfeld hinzu
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


        // erstellt DateiMen�
        JMenuItem product = new JMenuItem("New Product", 'R');
        JMenuItem customer = new JMenuItem("New Customer", 'M');
        JMenuItem open = new JMenuItem("Open", 'O');
        JMenuItem save = new JMenuItem("Save", 'S');
        JMenuItem saveas = new JMenuItem("Save As");

        // erstellt ShortCuts f�r Men�Items des DateiMen�
        product.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        customer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

        // f�gt Men�Items dem DateiMen� hinzu
        file.add(product);
        file.add(customer);
        file.add(open);
        file.addSeparator();
        file.add(save);
        file.add(saveas);

        // erstellt exit Men�Item
        JMenuItem exit = new JMenuItem("Exit", 'X');

        // Exit Action Listener
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Exit"))
                    System.exit(0);
            }
        });

        // f�gt Men�Item exit zu DateiMen� hinzu
        file.add(exit);

        // erstellt MenuItem Bearbeiten
        JMenu edit = new JMenu("Edit");
        menuBar.add(edit);
        edit.setMnemonic(KeyEvent.VK_E);

        // erstellt BearbeitenMen�
        JMenuItem undo = new JMenuItem("Undo", 'U');
        JMenuItem redo = new JMenuItem("Redo", 'R');
        JMenuItem cut = new JMenuItem("Cut", 'T');
        JMenuItem copy = new JMenuItem("Copy", 'C');
        JMenuItem paste = new JMenuItem("Paste", 'P');
        JMenuItem delete = new JMenuItem("Delete", 'D');

        // erstellt ShortCuts f�r BearbeitenMen�
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.ALT_DOWN_MASK));

        // f�gt Men�Items zu BearbeitenMen� hinzu
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

        // erstellt HilfeMen�
        JMenuItem ht = new JMenuItem("Help Topics", 'H');
        JMenuItem about = new JMenuItem("About", 'A');
        ht.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        help.add(ht);

        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("About"))
                    about();
            }
        });

        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
        help.add(about);

        // erstellt MenuItem Sprache
        JMenu language = new JMenu("Language");
        menuBar.add(language);
        language.setMnemonic(KeyEvent.VK_L);

        // erstellt Gruppe der Checkboxen
        ButtonGroup lang = new ButtonGroup();

        // Checkbox German wird erstellt
        JCheckBoxMenuItem german = new JCheckBoxMenuItem("German");
        german.setState(true);
        german.setMnemonic(KeyEvent.VK_G);
        german.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));

        // CheckBox English wird erstellt
        JCheckBoxMenuItem english = new JCheckBoxMenuItem("English");
        english.setState(false);
        english.setMnemonic(KeyEvent.VK_E);
        english.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));

        // f�gt die CheckBoxItems der Gruppe zu
        lang.add(german);
        lang.add(english);

        // f�gt LanguageMen�Items dem LanguageMen� zu
        language.add(german);
        language.add(english);

    }

    // wird von about abwechselnd auf true/false gesetzt
    private boolean visible = false;

     // wird aufgerufen bei About
    public void about(){

         // macht neues Label About
         JLabel about = new JLabel(new ImageIcon("About.jpg"));
         visible =! visible;
         about.setVisible(visible);
         this.getContentPane().add(about, BorderLayout.EAST);
         validate();
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