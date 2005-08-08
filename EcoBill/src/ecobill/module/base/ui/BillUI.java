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
     * Gibt die einzigste Instanz der <code>BusinessPartnerUI</code> zur�ck um diese
     * dann bspw im Hauptfenster anzeigen zu k�nnen.
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
     * Das �bersichtspanel gibt Auskunft �ber alle Artikel per <code>JTable</code> und bietet
     * eine Eingabemaske (auch f�r �nderungen) von Artikeln.
     */
    private JPanel overview = new JPanel(new FlowLayout());
    private JPanel bill = new JPanel(new BorderLayout());
    private TitledBorder dataBorder = new TitledBorder(new EtchedBorder());


    /**
     * Buttons
     */
    private JButton printB = new JButton("Drucken");
    private JButton makeP = new JButton("PDF machen");
    private JButton makeB = new JButton("Rechnung erstellen");
    private JButton saveB = new JButton("Rechnung speichern");
    private JButton delB = new JButton("Rechnung l�schen");
    private JLabel billi = new JLabel(new ImageIcon("rechnung.jpg"));


    /**
     * Standard Konstruktor.
     */
    public BillUI() {

    }

    /**
     * Globale Initialisierung des Artikel User Interfaces.
     * Es wird die Gr��e, minimale Gr��e des Fensters,... gesetzt.
     * Diese wird nach den gesetzten Properties des <code>ApplicationContext</code>
     * durchgef�hrt.
     */
    public void afterPropertiesSet() {

        /*
         * Es wird die Gr��e, das Layout und verschiedenste Optionen gesetzt.
         */
        this.setSize(new Dimension(870, 525));
        this.setMinimumSize(new Dimension(870, 325));
        this.setLayout(new BorderLayout());

        /*
         * Startet die Initialisierung der Kunden Oberfl�che.
         */
        this.initUI();
    }

    /**
     * Gibt den <code>BaseService</code> und somit die Business Logik zur�ck.
     *
     * @return Der <code>BaseService</code>.
     */
    public BaseService getBaseService() {
        return baseService;
    }

    /**
     * Setzt den <code>BaseService</code> der die komplette Business Logik enth�lt
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

        dataBorder.setTitleColor(Color.BLACK);
        dataBorder.setTitle("Aktionen");
        overview.setBorder(dataBorder);

        makeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Rechnung erstellen"))
                // Methode makeB() wird aufgerufen
                    makeB();
            }
        });

        overview.add(makeB);
        overview.add(delB);
        overview.add(saveB);
        overview.add(printB);
        overview.add(makeP);

        bill.add(billi, BorderLayout.CENTER);

        this.add(overview, BorderLayout.NORTH);
        this.add(bill, BorderLayout.CENTER);

    }

    // wird benutzt um neue Zeile zu erzeugen
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public void makeB() {

        // AusgabeStrings im PopUp Fenster makeB
        String la = "Rechnung erstellen";
        String se = "Geben Sie KundenID und BestellungsID ein! " + LINE_SEPARATOR +
                "       Ihre Rechnung wird dann erstellt!";

        // erstellt PopUp English
        JOptionPane.showMessageDialog(this, se, la, 1);
    }
}


