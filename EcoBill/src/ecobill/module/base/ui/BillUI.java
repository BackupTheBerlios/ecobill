package ecobill.module.base.ui;

import ecobill.module.base.service.BaseService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.InitializingBean;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Paul Chef
 * Date: 08.08.2005
 * Time: 17:13:18
 * To change this template use File | Settings | File Templates.
 */
public class BillUI extends JPanel implements InitializingBean {

    /**
     * Die <code>BusinessPartnerUI</code> stellt ein Singleton dar, da es immer nur eine
     * Instanz pro Arbeitsplatz geben kann.
     * -> spart kostbare Ressourcen.
     */
    private static BusinessPartnerUI singelton = null;

    /**
     * Gibt die einzigste Instanz der <code>BusinessPartnerUI</code> zurück um diese
     * dann bspw im Hauptfenster anzeigen zu können.
     *
     * @return Die <code>BusinessPartnerUI</code> ist abgeleitet von <code>JInternalFrame</code>
     *         und kann auf einer <code>JDesktopPane</code> angezeigt werden.
     */
    public static BusinessPartnerUI getInstance() {
        if (singelton == null) {
            singelton = new BusinessPartnerUI();
        }
        return singelton;
    }

    /**
     * Der <code>BaseService</code> ist die Business Logik.
     */
    private BaseService baseService;

    /**
     * Das Übersichtspanel gibt Auskunft über alle Artikel per <code>JTable</code> und bietet
     * eine Eingabemaske (auch für Änderungen) von Artikeln.
     */
    private JPanel overview = new JPanel(new BorderLayout());
    private JPanel bill = new JPanel(new BorderLayout());
    private TitledBorder dataBorder = new TitledBorder(new EtchedBorder());
    private TitledBorder billBorder = new TitledBorder(new EtchedBorder());
    private JTextField customerTF = new JTextField();
    private JTextField orderTF = new JTextField();


    /**
     * Buttons
     */
    private JButton printB = new JButton("Drucken");
    private JButton makeP = new JButton("PDF machen");
    private JButton makeB = new JButton("Erstellen");
    private JButton saveB = new JButton("Speichern");
    private JButton delB = new JButton("Löschen");
    private JLabel billi = new JLabel(new ImageIcon("images/rechnung.jpg"));
    private JLabel customer = new JLabel("KundenID");
    private JLabel order = new JLabel("LieferscheinID");


    /**
     * Standard Konstruktor.
     */
    public BillUI() {

    }

    /**
     * Globale Initialisierung des Artikel User Interfaces.
     * Es wird die Größe, minimale Größe des Fensters,... gesetzt.
     * Diese wird nach den gesetzten Properties des <code>ApplicationContext</code>
     * durchgeführt.
     */
    public void afterPropertiesSet() {

        /*
         * Es wird die Größe, das Layout und verschiedenste Optionen gesetzt.
         */
        this.setSize(new Dimension(870, 525));
        this.setMinimumSize(new Dimension(870, 325));
        this.setLayout(new BorderLayout());

        /*
         * Startet die Initialisierung der Kunden Oberfläche.
         */
        this.initUI();
    }

    /**
     * Gibt den <code>BaseService</code> und somit die Business Logik zurück.
     *
     * @return Der <code>BaseService</code>.
     */
    public BaseService getBaseService() {
        return baseService;
    }

    /**
     * Setzt den <code>BaseService</code> der die komplette Business Logik enthält
     * um bspw Daten aus der Datenbank zu laden und dorthin auch wieder abzulegen.
     *
     * @param baseService Der <code>BaseService</code>.
     */
    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    /**
     * Initialisieren des Graphical User Interface mit all seinen Panels, Eingabemasken
     * Rahmen, Buttons, usw.
     */
    private void initUI() {

        JPanel top = new JPanel(null);
        top.setPreferredSize(new Dimension(300, 50));

        dataBorder.setTitleColor(Color.BLACK);
        dataBorder.setTitle("Daten/Aktionen");
        top.setBorder(dataBorder);

        makeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Rechnung erstellen"))
                // Methode makeB() wird aufgerufen
                    makeB();
            }
        });

        billBorder.setTitleColor(Color.BLACK);
        billBorder.setTitle("Rechnung");
        billi.setBorder(billBorder);

        customer.setBounds(10, 20, 50, 20);
        top.add(customer);
        customerTF.setBounds(60, 20, 100, 20);
        top.add(customerTF);
        order.setBounds(170, 20, 70, 20);
        top.add(order);
        orderTF.setBounds(240, 20, 100, 20);
        top.add(orderTF);
        makeB.setBounds(350, 20, 100, 20);
        top.add(makeB);
        delB.setBounds(460, 20, 100, 20);
        top.add(delB);
        saveB.setBounds(570, 20, 100, 20);
        top.add(saveB);
        printB.setBounds(680, 20, 100, 20);
        top.add(printB);
        makeP.setBounds(790, 20, 100, 20);
        top.add(makeP);


        bill.add(billi, BorderLayout.CENTER);

        overview.add(top, BorderLayout.NORTH);
        overview.add(billi, BorderLayout.CENTER);

        this.add(overview);

    }

    // wird benutzt um neue Zeile zu erzeugen
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public void makeB() {

        // AusgabeStrings im PopUp Fenster makeB
        String la = "Rechnung erstellen";
        String se = "Geben Sie KundenID und BestellungsID ein! " + LINE_SEPARATOR +
                "       Ihre Rechnung wird dann erstellt!";

        // erstellt PopUp makeBill
        JOptionPane.showMessageDialog(this, se, la, 1);
    }
}


