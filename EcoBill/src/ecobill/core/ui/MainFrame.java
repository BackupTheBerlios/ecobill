package ecobill.core.ui;

import ecobill.module.base.ui.*;
import ecobill.core.system.WorkArea;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
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
 * @version $Id: MainFrame.java,v 1.45 2005/08/19 12:38:59 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class MainFrame extends JFrame implements ApplicationContextAware, InitializingBean {

    // @todo document me!
    protected ApplicationContext context;

    // erstellt Instanz einer ArtikelUI
    private ArticleUI articleUI;// = ArticleUI.getInstance();

    // getter für ArtikelUI
    public ArticleUI getArticleUI() {
        return articleUI;
    }

    // setter für ArtikelUI
    public void setArticleUI(ArticleUI articleUI) {
        this.articleUI = articleUI;
    }

    // erstellt Instanz einer BusinessPartnerUI
    private BusinessPartnerUI businessPartnerUI;

    // getter für BusinessPartnerUI
    public BusinessPartnerUI getbusinessPartnerUI() {
        return businessPartnerUI;
    }

    // setter für BusinessPartnerUI
    public void setbusinessPartnerUI(BusinessPartnerUI businessPartnerUI) {
        this.businessPartnerUI = businessPartnerUI;
    }

    // erstellt Instanz einer PrintUI
    private PrintUI printUI;

    // getter für PrintUI
    public PrintUI getPrintUI() {
        return printUI;
    }

    // setter für RechnungsUI
    public void setPrintUI(PrintUI printUI) {
        this.printUI = printUI;
    }


    // @todo document me!
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    // StandardKonstruktor
    public MainFrame() throws HeadlessException {
        super();

        WorkArea.setWorkArea(new WorkArea());
    }

    // alle Sachen erstellen die man braucht
    private JButton ebutton = new JButton("Exit");
    // erstellt TabFeld mit Komponenten
    private JTabbedPane jtab = new JTabbedPane();
    private JComponent tab = new JPanel(new BorderLayout());
    // erstellt MenuBar
    private JMenuBar menuBar = new JMenuBar();
    // erstellt MenuBarItems
    private JMenu file = new JMenu("File");
    private JMenu edit = new JMenu("Edit");
    private JMenu help = new JMenu("Help");
    private JMenu language = new JMenu("Language");
    // erstellt MenuItems
    // erstellt DateiMenü
    private JMenuItem open = new JMenuItem("Open", 'O');
    private JMenuItem save = new JMenuItem("Save", 'S');
    private JMenuItem saveas = new JMenuItem("Save As");
    private JMenuItem exit = new JMenuItem("Exit", 'X');
    // erstellt BearbeitenMenü
    private JMenuItem undo = new JMenuItem("Undo", 'U');
    private JMenuItem redo = new JMenuItem("Redo", 'R');
    private JMenuItem cut = new JMenuItem("Cut", 'T');
    private JMenuItem copy = new JMenuItem("Copy", 'C');
    private JMenuItem paste = new JMenuItem("Paste", 'P');
    private JMenuItem delete = new JMenuItem("Delete", 'D');
    // erstellt HilfeMenü
    private JMenuItem ht = new JMenuItem("Help Topics", 'H');
    private JMenuItem about = new JMenuItem("About", 'A');

    private UndoManager um = new UndoManager();


    public void afterPropertiesSet() throws Exception {

        // erstellt Mainframe
        // setzt Title des Mainframe
        this.setTitle("Economy Bill Agenda");
        // setzt Größe des Mainframe
        this.setSize(new Dimension(950, 700));
        // setzt UIManger der ans Windows Design anlegt
        UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
        // setzt LayoutManger für das Pane
        this.getContentPane().setLayout(new BorderLayout());
        // ruft Methode jMenuBar() auf die die MenuBar erstellt
        this.jMenuBar();
        // ruft Methode tabPane() auf die das TabPane erstellt
        this.tabPane();
        // ruft Methode exitButton() auf die den ExitButton erstellt
        this.exitButton();
        // setzt alles auf Sichtbar
        this.setVisible(true);
        // setzt die Operation die auf X am Fenster gemacht wird
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void exitButton() {

        //setzt ExitButton Mnemonic
        ebutton.setMnemonic(KeyEvent.VK_X);

        // Exit Button Action Listener
        ebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Exit"))
                // System wird beendet
                    System.exit(0);
            }
        });

        // fügt ExitButton hinzu
        this.getContentPane().add(ebutton, BorderLayout.SOUTH);
    }

    public void tabPane() {

        // fügt Tabs dem Tabfeld hinzu
        jtab.addTab("Start", new ImageIcon("images/s.gif"), tab);
        // hier wird die ArtikleUI als neuer Tab eingefügt
        jtab.addTab("Artikel", new ImageIcon("images/a.gif"), articleUI);
        // hier wird die BusinessPartnerUI als neuer Tab eingefügt
        jtab.addTab("Kunden", new ImageIcon("images/k.gif"), businessPartnerUI);
        // hier wird die RechnungsUI als neuer Tab eingefügt
        jtab.addTab("Lieferscheine/Rechnungen", new ImageIcon("images/l.gif"), printUI);

        // erstellt JLabels
        JLabel lab1 = new JLabel(new ImageIcon("images/Startbild.jpg"));
        // setzt ToolTip
        lab1.setToolTipText("Copyright @ JFuckers");
        // fügt JLabels tab0 zu
        tab.add(lab1, BorderLayout.CENTER);

        // fügt Tabfeld hinzu
        this.getContentPane().add(jtab, BorderLayout.CENTER);
    }


    public void jMenuBar() {

        // definiert MenuBar
        MainFrame.this.setJMenuBar(menuBar);
        // das FileMenu wird zur MenuBar zugefügt
        menuBar.add(file);
        // setzt FileMnemonic
        file.setMnemonic(KeyEvent.VK_F);

        // OpenMenuItem Action Listener
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Open"))
                // Methode open() wird aufgerufen
                    open();
            }
        });

        // erstellt ShortCuts für MenüItems des DateiMenü
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

        // fügt MenüItems dem DateiMenü hinzu
        file.add(open);
        file.add(save);
        file.add(saveas);
        // fügt SeperatorLinie dem DateiMenü hinzu
        file.addSeparator();

        // ExitMenuItem Action Listener
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Exit"))
                // System wird beendet
                    System.exit(0);
            }
        });

        // fügt MenüItem exit zu DateiMenü hinzu
        file.add(exit);

        // fügt MenuItem Bearbeiten der MenuBar zu
        menuBar.add(edit);
        // setzt Mnemonic für Edit
        edit.setMnemonic(KeyEvent.VK_E);

        // erstellt ShortCuts für BearbeitenMenü
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.ALT_DOWN_MASK));


         // undo ActionListener
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Undo"))

                    um.setLimit(1000);

                if (um.canUndo()) {
                    um.undo();
                }
            }
        });

         // redo ActionListener
        redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Redo"))

                    um.setLimit(1000);

                if (um.canRedo()) {
                    um.redo();
                }
            }
        });

        // fügt MenüItems zu BearbeitenMenü hinzu
        edit.add(undo);
        edit.add(redo);

        // copy ActionListener
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Copy"))
                // Methode topic() wird aufgerufen
                    copy();
            }
        });

        // paste ActionListener
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Paste"))
                // Methode topic() wird aufgerufen
                    paste();
            }
        });

        // fügt SeperatorLinie zu BearbeitenMenü hinzu
        edit.addSeparator();
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete);

        // setzt Mnemonic beim MenuItem Hilfe
        help.setMnemonic(KeyEvent.VK_H);
        // setzt ToolTip
        help.setToolTipText("Benutzen Sie ShortCuts um schneller zu navigieren");
        // fügt HilfeMenuItem der MenuBar zu
        menuBar.add(help);

        // HelpTopic ActionListener
        ht.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Help Topics"))
                // Methode topic() wird aufgerufen
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
                // Methode about() wird aufgerufen
                    about();
            }
        });

        // setzt About ShortCut
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
        // fügt About zu Help hinzu
        help.add(about);

        // Language wir zur MenuBar hinzugefügt
        menuBar.add(language);
        // setzt Language Mnemonic
        language.setMnemonic(KeyEvent.VK_L);

        // erstellt Gruppe der Checkboxen
        ButtonGroup lang = new ButtonGroup();

        // Checkbox German wird erstellt
        JCheckBoxMenuItem german = new JCheckBoxMenuItem("German", new ImageIcon("images/german.jpg"));
        // setzt das German am Anfang ausgewählt ist
        german.setState(true);
        // setzt Mnemonic bei German
        german.setMnemonic(KeyEvent.VK_G);
        // setzt ShorCut bei German
        german.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));

        // German ActionListener
        german.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("German"))
                // Methode german() wird aufgerufen
                    german();
            }
        });

        // CheckBox English wird erstellt
        JCheckBoxMenuItem english = new JCheckBoxMenuItem("English", new ImageIcon("images/english.jpg"));
        // setzt das English am Anfang nicht ausgewählt ist
        english.setState(false);
        // setzt EnglishMnemonic
        english.setMnemonic(KeyEvent.VK_E);
        // setzt ShortCut bei English
        english.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));

        // English ActionListener
        english.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("English"))
                // Methode english() wird aufgerufen
                    english();
            }
        });

        // fügt die CheckBoxItems German und English der Gruppe zu
        lang.add(german);
        lang.add(english);

        // fügt LanguageMenüItems German und English dem LanguageMenü zu
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
        JOptionPane.showMessageDialog(this, ec, ab, 1, new ImageIcon("images/About.gif"));
    }

    // wird aufgerufen bei Help Topics
    public void topic() {

        // AusgabeStrings im PopUp Fenster Topic
        String to = "Help Topics";
        String ec = "Economy Bill Agenda" + LINE_SEPARATOR + "        Version 1.0";

        // erstellt PopUp Topic
        JOptionPane.showMessageDialog(this, ec, to, 1, new ImageIcon("images/Topic.gif"));
    }

    public void german() {
    }

    public void english() {
    }

    public void open() {
        // erstellt FileDialog setzt Title
        FileDialog pd = new FileDialog(this, "Öffnen");
        // setzt was für Dateien im FileDialog ausgewählt werden dürfen
        pd.setFile("*.*");
        String filename = pd.getFile();
        if (filename != null) {
        }
        pd.setVisible(true);
        pd.dispose();
        this.getContentPane().add(pd);
    }

    private TextField tf = new TextField();

    public void copy() {

        StringSelection data = new StringSelection(tf.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(data, data);
    }

    public void paste() {

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable data = clipboard.getContents(this);
        String s;
        try {
            s = (String) (data.getTransferData(DataFlavor.stringFlavor));
        } catch (Exception e) {
            s = data.toString();
        }
        tf.setText(s);
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
