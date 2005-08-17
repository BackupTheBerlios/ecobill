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
     * Die <code>BillUI</code> stellt ein Singleton dar, da es immer nur eine
     * Instanz pro Arbeitsplatz geben kann.
     * -> spart kostbare Ressourcen.
     */
    private static BillUI singelton = null;

    /**
     * Gibt die einzigste Instanz der <code>BillUI</code> zur�ck um diese
     * dann bspw im Hauptfenster anzeigen zu k�nnen.
     *
     * @return Die <code>BillUI</code> ist abgeleitet von <code>JInternalFrame</code>
     *         und kann auf einer <code>JDesktopPane</code> angezeigt werden.
     */
    public static BillUI getInstance() {
        if (singelton == null) {
            singelton = new BillUI();
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
    private JPanel overview = new JPanel(new BorderLayout());
    private JPanel bill = new JPanel(new BorderLayout());
    private JPanel top = new JPanel(null);
    private TitledBorder dataBorder = new TitledBorder(new EtchedBorder());
    private TitledBorder billBorder = new TitledBorder(new EtchedBorder());
    private JTextField customerTF = new JTextField();
    private JTextField orderTF = new JTextField();


    /**
     * Buttons
     */
    private JButton makeB = new JButton("Viewer laden");
    private JButton delB = new JButton("Viewer schlie�en");
    private JLabel customer = new JLabel("KundenID");
    private JLabel order = new JLabel("LieferscheinID");


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


        top.setPreferredSize(new Dimension(300, 50));

        dataBorder.setTitleColor(Color.BLACK);
        dataBorder.setTitle("Daten/Aktionen");
        top.setBorder(dataBorder);

        makeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Viewer laden"))
                    try {

                        bill.setVisible(true);
                        BillUI.this.jasper();
                        BillUI.this.bill.validate();

                    } catch (Exception e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
            }
        });

        // Button ActionListener versteckt JRViewer
        delB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Viewer schlie�en")) {

                    bill.setVisible(false);
                }
            }
        });

        billBorder.setTitleColor(Color.BLACK);
        billBorder.setTitle("Rechnung");
        bill.setBorder(billBorder);

        customer.setBounds(10, 20, 50, 20);
        top.add(customer);
        customerTF.setBounds(60, 20, 120, 20);
        top.add(customerTF);
        order.setBounds(190, 20, 75, 20);
        top.add(order);
        orderTF.setBounds(265, 20, 120, 20);
        top.add(orderTF);
        makeB.setBounds(425, 20, 150, 20);
        makeB.setToolTipText("In diesem Viewer k�nnen sie die Rechnung drucken und als PDF speichern");
        top.add(makeB);
        delB.setBounds(595, 20, 150, 20);
        top.add(delB);


        overview.add(top, BorderLayout.NORTH);
        overview.add(bill, BorderLayout.CENTER);

        this.add(overview);

    }

    /**
     * JRViewer
     */
    public void jasper() throws Exception {

        JasperViewer jv = new JasperViewer(bill);
        jv.jasper("jasperfiles/rechnung.jrxml");
    }
}