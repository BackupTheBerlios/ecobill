package ecobill.core.ui;

import ecobill.module.base.ui.start.StartUI;
import ecobill.module.base.ui.article.ArticleUI;
import ecobill.module.base.ui.businesspartner.BusinessPartnerUI;
import ecobill.module.base.ui.deliveryorder.DeliveryOrderUI;
import ecobill.module.base.ui.bill.BillUI;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

// @todo document me!

/**
 * MainFrame.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:43:36
 *
 * @author Roman R&auml;dle
 * @version $Id: MainFrame.java,v 1.85 2005/10/21 12:03:43 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class MainFrame extends JFrame implements ApplicationContextAware, InitializingBean, Splashable, Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(MainFrame.class);

    /**
     * Der <code>ApplicationContext</code> beinhaltet alle Beans die darin angegeben sind
     * und ermöglicht wahlfreien Zugriff auf diese.
     */
    protected ApplicationContext applicationContext;

    /**
     * @see ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Die Instanz des Start User Interface.
     */
    private StartUI startUI;

    /**
     * Gibt die Instanz des Start User Interface zurück.
     *
     * @return Die Instanz des <code>StartUI</code>.
     */
    public StartUI getStartUI() {
        return startUI;
    }

    /**
     * Setzt die Instanz des Start User Interface.
     *
     * @param startUI Eine Instanz des <code>StartUI</code>.
     */
    public void setStartUI(StartUI startUI) {
        this.startUI = startUI;
    }

    /**
     * Die Instanz des Article User Interface.
     */
    private ArticleUI articleUI;

    /**
     * Gibt die Instanz des Article User Interface zurück.
     *
     * @return Die Instanz des <code>ArticleUI</code>.
     */
    public ArticleUI getArticleUI() {
        return articleUI;
    }

    /**
     * Setzt die Instanz des Article User Interface.
     *
     * @param articleUI Eine Instanz des <code>ArticleUI</code>.
     */
    public void setArticleUI(ArticleUI articleUI) {
        this.articleUI = articleUI;
    }

    /**
     * Die Instanz des BusinessPartner User Interface.
     */
    private BusinessPartnerUI businessPartnerUI;

    /**
     * Gibt die Instanz des BusinessPartner User Interface zurück.
     *
     * @return Die Instanz des <code>BusinessPartnerUI</code>.
     */
    public BusinessPartnerUI getBusinessPartnerUI() {
        return businessPartnerUI;
    }

    /**
     * Setzt die Instanz des BusinessPartner User Interface.
     *
     * @param businessPartnerUI Eine Instanz des <code>BusinessPartnerUI</code>.
     */
    public void setBusinessPartnerUI(BusinessPartnerUI businessPartnerUI) {
        this.businessPartnerUI = businessPartnerUI;
    }

    /**
     * Die Instanz des DeliveryOrder User Interface.
     */
    public DeliveryOrderUI deliveryOrderUI;

    /**
     * Gibt die Instanz des DeliveryOrder User Interface zurück.
     *
     * @return Die Instanz des <code>DeliveryOrderUI</code>.
     */
    public DeliveryOrderUI getDeliveryOrderUI() {
        return deliveryOrderUI;
    }

    /**
     * Setzt die Instanz des DeliveryOrder User Interface.
     *
     * @param deliveryOrderUI Eine Instanz des <code>DeliveryOrderUI</code>.
     */
    public void setDeliveryOrderUI(DeliveryOrderUI deliveryOrderUI) {
        this.deliveryOrderUI = deliveryOrderUI;
    }

    /**
     * Gibt die Instanz des Bill User Interface zurück.
     *
     * @return Die Instanz des <code>BillUI</code>.
     */

    public BillUI getBillUI() {
        return billUI;
    }

    /**
     * Setzt die Instanz des Bill User Interface zurück.
     *
     */

    public void setBillUI(BillUI billUI) {
        this.billUI = billUI;
    }

    /**
     * Die Instanz des DeliveryOrder User Interface.
     */
    public BillUI billUI;

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {

        // Setzt das IconImage des <code>JFrame</code>.
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/ico/currency_dollar.png"));

        // Setzt Title des <code>MainFrame</code>.
        setTitle(WorkArea.getMessage(Constants.APPLICATION_TITLE));

        // Setzt Größe des <code>MainFrame</code>.
        setSize(new Dimension(950, 700));

        // Setzt den <code>LayoutManger</code> für das <code>JFrame</code>.
        getContentPane().setLayout(new BorderLayout());

        // ruft Methode jMenuBar() auf die die MenuBar erstellt
        jMenuBar();

        // ruft Methode tabPane() auf die das TabPane erstellt
        tabPane();

        center();

        reinitI18N();

        // Setzt die Operation, die auf X am Fenster gemacht wird.
        addWindowListener(new WindowAdapter() {

            /**
             * @see WindowAdapter#windowClosing(java.awt.event.WindowEvent)
             */
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        // Setzt den <code>JFrame</code>, der bis dahin "unsichtbar" war, sichtbar.
        setVisible(true);
    }

    /**
     * Zentriert das <code>MainFrame</code> im sichtbaren Bereich des Bildschirms.
     */
    private void center() {

        // Größe der eingestellten Bildschirmauflösung.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        // Größe des <code>JFrame</code>.
        Dimension frameSize = this.getSize();

        width -= frameSize.getWidth();
        height -= frameSize.getHeight();

        this.setLocation((int) width / 2, (int) height / 2);
    }



    // alle Sachen erstellen die man braucht
    private JButton ebutton = new JButton();
    // erstellt TabFeld mit Komponenten
    private JTabbedPane jtab = new JTabbedPane();
    private JComponent tab = new JPanel(new BorderLayout());
    // erstellt MenuBar
    private JMenuBar menuBar = new JMenuBar();

    // erstellt MenuBarItems
    private JMenu file = new JMenu();
    private JMenu edit = new JMenu();
    private JMenu help = new JMenu();
    private JMenu language = new JMenu();
    // erstellt MenuItems
    // erstellt DateiMenü
    private JMenuItem open = new JMenuItem(new ImageIcon("images/open.png"));
    private JMenuItem save = new JMenuItem(new ImageIcon("images/save.png"));
    private JMenuItem saveAs = new JMenuItem(new ImageIcon("images/save_as.png"));
    private JMenuItem exit = new JMenuItem(new ImageIcon("images/exit.png"));

    // erstellt BearbeitenMenü
    private JMenuItem undo = new JMenuItem(new ImageIcon("images/undo.png"));
    private JMenuItem redo = new JMenuItem(new ImageIcon("images/redo.png"));
    private JMenuItem cut = new JMenuItem(new ImageIcon("images/cut.png"));
    private JMenuItem copy = new JMenuItem(new ImageIcon("images/copy.png"));
    private JMenuItem paste = new JMenuItem(new ImageIcon("images/paste.png"));
    private JMenuItem delete = new JMenuItem(new ImageIcon("images/delete.png"));

    // erstellt HilfeMenü
    private JMenuItem ht = new JMenuItem(new ImageIcon("images/help.png"));
    private JMenuItem about = new JMenuItem(new ImageIcon("images/about.png"));

    //erstellt JLabels
    private JPanel startPanel = new JPanel();

    // CheckBox English wird erstellt
    private JCheckBoxMenuItem english = new JCheckBoxMenuItem(new ImageIcon("images/flag_great_britain.png"));

    // Checkbox German wird erstellt
    private JCheckBoxMenuItem german = new JCheckBoxMenuItem(new ImageIcon("images/flag_germany.png"));

    private UndoManager um = new UndoManager();

    private StatePanel statePanel = new StatePanel();

    public void setProgressPercentage(int percentage) {
        statePanel.setProgressPercentage(percentage);
    }

    public void tabPane() {

        jtab.addTab(null, new ImageIcon("images/home.png"), startUI);
        // hier wird die ArtikleUI als neuer Tab eingefügt
        jtab.addTab(null, new ImageIcon("images/article.png"), articleUI);
        // hier wird die BusinessPartnerUI als neuer Tab eingefügt
        jtab.addTab(null, new ImageIcon("images/business_partner.png"), businessPartnerUI);
        // hier wird die LieferscheinUI als neuer Tab eingefügt
        jtab.addTab(null, new ImageIcon("images/delivery_order.png"), deliveryOrderUI);
        jtab.setEnabledAt(3, false);
        // hier wird die RechnungsUI als neuer Tab eingefügt
        jtab.addTab(null, new ImageIcon("images/delivery_order.png"), billUI);
        jtab.setEnabledAt(4, false);


        // setzt ToolTip
        startPanel.add(new JLabel("Economy Bill Agenda"));
        // fügt JLabels tab0 zu
        tab.add(startPanel, BorderLayout.CENTER);

        jtab.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {

                if (jtab.getSelectedIndex() != 3) {
                    jtab.setEnabledAt(3, false);
                }
                else {
                    deliveryOrderUI.renewArticleTableModel();
                }
            }
        });

        // fügt Tabfeld hinzu
        this.getContentPane().add(jtab, BorderLayout.CENTER);

        statePanel.setPreferredSize(new Dimension(200, 19));
        this.getContentPane().add(statePanel, BorderLayout.SOUTH);
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

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Action: " + e.getActionCommand());
                }

                if (e.getSource().equals(open))
                    // Methode open() wird aufgerufen
                    open();
            }
        });

        // erstellt ShortCuts für MenüItems des DateiMenü
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

        // fügt MenüItems dem DateiMenü hinzu
        file.add(open);
        file.add(save);
        file.add(saveAs);
        // fügt SeperatorLinie dem DateiMenü hinzu
        file.addSeparator();

        // ExitMenuItem Action Listener
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Action: " + e.getActionCommand());
                }

                if (e.getSource().equals(exit))

                    // System wird beendet
                    exit();
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

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Action: " + e.getActionCommand());
                }

                if (e.getSource().equals(undo))

                    um.setLimit(1000);

                if (um.canUndo()) {
                    um.undo();
                }
            }
        });

        // redo ActionListener
        redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Action: " + e.getActionCommand());
                }

                if (e.getSource().equals(redo))

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

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Action: " + e.getActionCommand());
                }

                if (e.getSource().equals(copy))
                    // Methode topic() wird aufgerufen
                    copy();
            }
        });

        // paste ActionListener
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Action: " + e.getActionCommand());
                }

                if (e.getSource().equals(paste))
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

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Action: " + e.getActionCommand());
                }

                if (e.getSource().equals(ht))
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

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Action: " + e.getActionCommand());
                }

                if (e.getSource().equals(about))
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

        // setzt das German am Anfang ausgewählt ist
        german.setState(true);
        // setzt Mnemonic bei German
        german.setMnemonic(KeyEvent.VK_G);
        // setzt ShorCut bei German
        german.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));

        // German ActionListener
        german.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Action: " + e.getActionCommand());
                }

                if (e.getSource().equals(german))
                    // Methode german() wird aufgerufen
                    german();
            }
        });

        // setzt das English am Anfang nicht ausgewählt ist
        english.setState(false);
        // setzt EnglishMnemonic
        english.setMnemonic(KeyEvent.VK_E);
        // setzt ShortCut bei English
        english.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));

        // English ActionListener
        english.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Action: " + e.getActionCommand());
                }

                if (e.getSource().equals(english))
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
        JOptionPane.showMessageDialog(this, ec, ab, JOptionPane.DEFAULT_OPTION, new ImageIcon("images/About.gif"));
    }

    // wird aufgerufen bei Help Topics
    public void topic() {

        // AusgabeStrings im PopUp Fenster Topic
        String to = "Help Topics";
        String ec = "Economy Bill Agenda" + LINE_SEPARATOR + "        Version 1.0";

        // erstellt PopUp Topic
        JOptionPane.showMessageDialog(this, ec, to, JOptionPane.DEFAULT_OPTION, new ImageIcon("images/Topic.gif"));
    }

    /**
     * Umstellen der Sprache auf Deutsch.
     */
    public void german() {
        WorkArea.setLocale(Locale.GERMAN);
        reinitI18N();
    }

    /**
     * Umstellen der Sprache auf Englisch.
     */
    public void english() {
        WorkArea.setLocale(Locale.ENGLISH);
        reinitI18N();
    }

    /**
     * Diese Methode kann später dazu verwendet werden um Dateien zu laden.
     */
    public void open() {

        // Erstellt <code>FileDialog</code> und setzt den Titel.
        FileDialog fileDialog = new FileDialog(this, WorkArea.getMessage(Constants.OPEN), FileDialog.LOAD);
        fileDialog.setVisible(true);

        String filename = fileDialog.getFile();

        if (filename != null) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Die selektierte Datei heißt " + filename);
            }
        }

        fileDialog.dispose();
    }

    /**
     * Versucht den <code>ApplicationContext</code> herunterzufahren, sofern dieser das
     * Interface <code>DisposableBean</code> implementiert. Gelingt dies wird die
     * Anwendung ohne Fehler beendet, ansonsten wird mit Fehlercode beendet.
     */
    private void exit() {

        // Überprüft ob der <code>ApplicationContext</code> das Interface
        // <code>DisposableBean</code> implementiert, um den gesamten
        // applicationContext beim Schließen der Anwendung herunterzufahren.
        //
        // Dies ist notwendig um die gesamten Beans die auch dieses Interface
        // implementieren herunterzufahren und somit zu gewährleisten, dass
        // evtl. Daten persistiert werden.
        if (applicationContext instanceof DisposableBean) {
            try {
                ((DisposableBean) applicationContext).destroy();
            }
            catch (Exception ex) {
                if (LOG.isErrorEnabled()) {
                    LOG.error(ex.getMessage(), ex);
                }

                // TODO: Status 100 bitte in der Dokumentation eintragen.
                System.exit(100);
            }
        }

        // Beendet die Anwendung ohne Fehler.
        System.exit(0);
    }

    /**
     * Diese Methode kann später dazu verwendet werden um Text zu kopieren.
     */
    public void copy() {
        throw new RuntimeException("Diese Methode muss noch implementiert werden.");
    }

    /**
     * Diese Methode kann später dazu verwendet werden um Text einzufügen.
     */
    public void paste() {
        throw new RuntimeException("Diese Methode muss noch implementiert werden.");
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        file.setText(WorkArea.getMessage(Constants.FILE));
        edit.setText(WorkArea.getMessage(Constants.EDIT));
        help.setText(WorkArea.getMessage(Constants.HELP));
        language.setText(WorkArea.getMessage(Constants.LANGUAGE));

        open.setText(WorkArea.getMessage(Constants.OPEN));
        save.setText(WorkArea.getMessage(Constants.SAVE));
        saveAs.setText(WorkArea.getMessage(Constants.SAVEAS));
        exit.setText(WorkArea.getMessage(Constants.EXIT));

        undo.setText(WorkArea.getMessage(Constants.UNDO));
        redo.setText(WorkArea.getMessage(Constants.REDO));
        cut.setText(WorkArea.getMessage(Constants.CUT));
        copy.setText(WorkArea.getMessage(Constants.COPY));
        paste.setText(WorkArea.getMessage(Constants.PASTE));
        delete.setText(WorkArea.getMessage(Constants.DELETE));

        ht.setText(WorkArea.getMessage(Constants.HT));
        about.setText(WorkArea.getMessage(Constants.ABOUT));

        jtab.setTitleAt(0, WorkArea.getMessage(Constants.START));
        jtab.setTitleAt(1, WorkArea.getMessage(Constants.ARTICLE));
        jtab.setTitleAt(2, WorkArea.getMessage(Constants.CUSTOMERS));
        jtab.setTitleAt(3, WorkArea.getMessage(Constants.DORDER));
        jtab.setTitleAt(4, WorkArea.getMessage(Constants.BILL));

        english.setText(WorkArea.getMessage(Constants.ENGLISH));
        german.setText(WorkArea.getMessage(Constants.GERMAN));

        ebutton.setText(WorkArea.getMessage(Constants.EXIT));

        startUI.reinitI18N();
        articleUI.reinitI18N();
        businessPartnerUI.reinitI18N();
        billUI.reinitI18N();
        deliveryOrderUI.reinitI18N();
    }

    public void setSelectedTab(int index) {
        jtab.setEnabledAt(index, true);
        jtab.setSelectedIndex(index);
    }
}
