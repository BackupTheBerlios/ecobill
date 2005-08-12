package ecobill.module.base.ui;

import ecobill.module.base.service.BaseService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;


import org.springframework.beans.factory.InitializingBean;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.engine.*;

/**
 * Created by IntelliJ IDEA.
 * User: Paul Chef
 * Date: 08.08.2005
 * Time: 22:23:20
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryOrderUI extends JPanel implements InitializingBean {
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
     * JRViewer
     *
     * Bitte no des itext-1.3.jar (isch im CVS) bei Settings neilada sonsch god des mit dem PDF macha it
     * Jetzt kamas als Pdf speichra
     */
    public void jasper() throws Exception {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecobill", "root", "x2kub2");


            JasperReport js = JasperCompileManager.compileReport("lieferschein.jrxml");
            JasperRunManager.runReportToPdf(js, new HashMap(), con);
            JRViewer viewer = new JRViewer(JasperFillManager.fillReport(js, new HashMap(), con));
            bill.add(viewer, BorderLayout.CENTER);

        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
        }
    }

    /**
     * Buttons
     */
    private JButton makeB = new JButton("Viewer erstellen");
    private JButton delB = new JButton("Viewer schließen");
    private JLabel customer = new JLabel("KundenID");
    private JLabel order = new JLabel("AuftragsID");


    /**
     * Standard Konstruktor.
     */
    public DeliveryOrderUI() {

    }

    /**
     * Globale Initialisierung des Artikel User Interfaces.
     * Es wird die Größe, minimale Größe des Fensters,... gesetzt.
     * Diese wird nach den gesetzten Properties des <code>ApplicationContext</code>
     * durchgeführt.
     */
    public void afterPropertiesSet() throws Exception {

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
                if (e.getActionCommand().equals("Viewer erstellen"))
                    try {
                        DeliveryOrderUI.this.jasper();
                        DeliveryOrderUI.this.bill.validate();
                    } catch (Exception e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
            }
        });

        billBorder.setTitleColor(Color.BLACK);
        billBorder.setTitle("Lieferschein");
        bill.setBorder(billBorder);

        customer.setBounds(10, 20, 50, 20);
        top.add(customer);
        customerTF.setBounds(60, 20, 120, 20);
        top.add(customerTF);
        order.setBounds(190, 20, 60, 20);
        top.add(order);
        orderTF.setBounds(250, 20, 120, 20);
        top.add(orderTF);
        makeB.setBounds(410, 20, 150, 20);
        makeB.setToolTipText("In diesem Viewer können sie den Lieferschein drucken und als PDF speichern");
        top.add(makeB);
        delB.setBounds(580, 20, 150, 20);
        top.add(delB);


        //bill.add(billi, BorderLayout.CENTER);

        overview.add(top, BorderLayout.NORTH);
        overview.add(bill, BorderLayout.CENTER);

        this.add(overview);

    }
}

