package ecobill.core.ui;

import ecobill.module.base.ui.ArticleUI;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

// @todo document me!

/**
 * MainFrame.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:43:36
 *
 * @author Roman R&auml;dle
 * @version $Id: MainFrame.java,v 1.26 2005/08/04 20:13:14 jfuckerweiler Exp $
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
        ebutton.setMnemonic(KeyEvent.VK_X);

        // Exit Button Action Listener
        ebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Exit"))
                    System.exit(0);
            }
        });

        // fügt ExitButton hinzu
        this.getContentPane().add(ebutton, BorderLayout.SOUTH);
    }

    public void tabPane() {

        // erstellt TabFeld
        JTabbedPane jtab = new JTabbedPane();
        JComponent tab0 = new JPanel();
        JComponent tab1 = new JPanel();
        JComponent tab2 = new JPanel();
        JComponent tab3 = new JPanel();

        // fügt Tabs dem Tabfeld hinzu
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

        // fügt JLabels tab0 zu
        tab0.add(lab1, BorderLayout.CENTER);
        tab0.add(descrip1, BorderLayout.SOUTH);

        // fügt JLabel tab1 zu
        JLabel descrip2 = new JLabel("Hier kommt die ArtikelGui rein");
        tab1.add(descrip2, BorderLayout.NORTH);

        // fügt JLabel tab2 zu
        JLabel descrip3 = new JLabel("Hier kommt die KundenGui rein");
        tab2.add(descrip3);

        // fügt JLabel tab3 zu
        JLabel descrip4 = new JLabel("Hier kommt die RechnungsGui bzw. Rechnungen rein");
        tab3.add(descrip4);

        // fügt Tabfeld hinzu
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

        // product Action Listener
        product.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("New Product"))
                    product();
            }
        });

        // customer Action Listener
        customer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("New Customer"))
                    customer();
            }
        });

        // open Action Listener
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Open"))
                    open();
            }
        });

        // erstellt ShortCuts für MenüItems des DateiMenü
        product.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        customer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

        // fügt MenüItems dem DateiMenü hinzu
        file.add(product);
        file.add(customer);
        file.add(open);
        file.addSeparator();
        file.add(save);
        file.add(saveas);

        // erstellt exit MenüItem
        JMenuItem exit = new JMenuItem("Exit", 'X');

        // Exit Action Listener
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Exit"))
                    System.exit(0);
            }
        });

        // fügt MenüItem exit zu DateiMenü hinzu
        file.add(exit);

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

        // erstellt ShortCuts für BearbeitenMenü
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.ALT_DOWN_MASK));

        // fügt MenüItems zu BearbeitenMenü hinzu
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

        // HelpTopic ActionListener
        ht.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Help Topics"))
                    topic();
            }
        });

        // setzt HelpTopic Shortcut
        ht.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        // fügt HelpTopic zu Help hinzu
        help.add(ht);

        // About ActionListener
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("About"))
                    about();
            }
        });

        // setzt About ShortCut
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
        // fügt About zu Help hinzu
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

        // German ActionListener
        german.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("German"))
                    german();
            }
        });

        // CheckBox English wird erstellt
        JCheckBoxMenuItem english = new JCheckBoxMenuItem("English");
        english.setState(false);
        english.setMnemonic(KeyEvent.VK_E);
        english.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));

        // English ActionListener
        english.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("English"))
                    english();
            }
        });

        // fügt die CheckBoxItems der Gruppe zu
        lang.add(german);
        lang.add(english);

        // fügt LanguageMenüItems dem LanguageMenü zu
        language.add(german);
        language.add(english);

    }

    // wird benutzt um neue Zeile zu erzeugen
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    // wird aufgerufen bei About
    public void about() {

        // AusgabeStrings im PopUp Fenster About
        String ab = "About";
        String ec = "Economy Bill Agenda" + LINE_SEPARATOR + "        Version 1.0";

        // erstellt PopUp About
        JOptionPane.showMessageDialog(this, ec, ab, 1, new ImageIcon("About.gif"));
    }

    public void topic() {

        // AusgabeStrings im PopUp Fenster Topic
        String to = "Help Topics";
        String ec = "Economy Bill Agenda" + LINE_SEPARATOR + "        Version 1.0";

        // erstellt PopUp Topic
        JOptionPane.showMessageDialog(this, ec, to, 1, new ImageIcon("Topic.gif"));
    }

    public void german() {

        // AusgabeStrings im PopUp Fenster German
        String la = "Language";
        String sd = "Sprache ist jetzt" + LINE_SEPARATOR + "        Deutsch";

        // erstellt PopUp German
        JOptionPane.showMessageDialog(this, sd, la, 1, new ImageIcon("German.jpg"));
    }

    public void english() {

        // AusgabeStrings im PopUp Fenster English
        String la = "Language";
        String se = "Language is now " + LINE_SEPARATOR + "          English";

        // erstellt PopUp English
        JOptionPane.showMessageDialog(this, se, la, 1, new ImageIcon("English.gif"));
    }

    public void open() {
        FileDialog pd = new FileDialog(this, "Öffnen");
        pd.setFile("*.*");
        String filename = pd.getFile();
        if (filename != null) {
        }
        pd.setVisible(true);
        pd.dispose();
        this.getContentPane().add(pd);
    }

    public void product() {
        String pr1 = "New Product";
        String pr2 = "Hiermit fügen sie ein neues Produkt ein";

        // erstellt PopUp Product
        JOptionPane.showMessageDialog(this, pr2, pr1, 1);

    }

     public void customer() {
        String cr1 = "New Customer";
        String cr2 = "Hiermit fügen sie einen neuen Kunden ein";

        // erstellt PopUp Customer
        JOptionPane.showMessageDialog(this, cr2, cr1, 1);

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