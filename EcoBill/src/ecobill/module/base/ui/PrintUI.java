package ecobill.module.base.ui;

import org.springframework.beans.factory.InitializingBean;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import ecobill.module.base.service.BaseService;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// @todo document me!

/**
 * PrintUI.
 * <p/>
 * User: Paul Chef
 * Date: 18.08.2005
 * Time: 16:45:41
 *
 * @author Andreas Weiler
 * @version $Id: PrintUI.java,v 1.10 2005/08/23 14:01:24 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class PrintUI extends JPanel implements InitializingBean {

    /**
     * Die <code>PrintUI</code> stellt ein Singleton dar, da es immer nur eine
     * Instanz pro Arbeitsplatz geben kann.
     * -> spart kostbare Ressourcen.
     */
    private static PrintUI singelton = null;

    /**
     * Gibt die einzigste Instanz der <code>BillUI</code> zurück um diese
     * dann bspw im Hauptfenster anzeigen zu können.
     *
     * @return Die <code>PrintUI</code> ist abgeleitet von <code>JInternalFrame</code>
     *         und kann auf einer <code>JDesktopPane</code> angezeigt werden.
     */
    public static PrintUI getInstance() {
        if (singelton == null) {
            singelton = new PrintUI();
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
    private JPanel top = new JPanel(null);
    private TitledBorder dataBorder = new TitledBorder(new EtchedBorder());
    private TitledBorder billBorder = new TitledBorder(new EtchedBorder());
    private JTextField customerTF = new JTextField();
    private JTextField orderTF = new JTextField();
    private JProgressBar jb = new JProgressBar(1, 10000000);


    /**
     * Buttons
     */
    private JButton makeB = new JButton("Viewer laden");
    private JButton delB = new JButton("Viewer schließen");
    private JLabel customer = new JLabel("KundenID");
    private JLabel order = new JLabel("AuftragsID/LieferscheinID");
    private JLabel close = new JLabel("Viewer wurde geschlossen");


    /**
     * Standard Konstruktor.
     */
    public PrintUI() {

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


        top.setPreferredSize(new Dimension(300, 50));

        dataBorder.setTitleColor(Color.BLACK);
        dataBorder.setTitle("Daten/Aktionen");
        top.setBorder(dataBorder);

        makeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Viewer laden"))

                   PrintUI.this.threadies();
                   close.setVisible(false);
                }
        });

        // Button ActionListener versteckt JRViewer
        delB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action: " + e.getActionCommand());
                if (e.getActionCommand().equals("Viewer schließen")) {

                    reJasper();
                    jb.setVisible(false);
                    close.setVisible(true);
                    close.setBounds(770, 20, 150, 20);
                    top.add(close);
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
        order.setBounds(190, 20, 130, 20);
        top.add(order);
        orderTF.setBounds(320, 20, 120, 20);
        top.add(orderTF);
        makeB.setBounds(450, 15, 150, 25);
        makeB.setIcon(new ImageIcon("images/ArrowRight.gif"));
        makeB.setToolTipText("In diesem Viewer können sie die Rechnung drucken und als PDF speichern");
        top.add(makeB);
        delB.setBounds(610, 15, 150, 25);
        delB.setIcon(new ImageIcon("images/ArrowLeft.gif"));
        delB.setToolTipText("Hierdurch wird der Viewer geschlossen");
        top.add(delB);


        overview.add(top, BorderLayout.NORTH);
        overview.add(bill, BorderLayout.CENTER);

        this.add(overview);

    }
     private JasperViewer jv = new JasperViewer(bill);
    /**
     * JRViewer
     */
    public void jasper() throws Exception {


        jv.jasper("jasperfiles/print.jrxml");
    }

    public void reJasper() {
        jv.reJasper();
    }


    /**
     * ThreadVerwalter
     */

    public void threadies() {
        Thread t1 = new Thread(new Thread1());
        t1.start();

        Thread t2 = new Thread(new Thread2());
        t2.start();
    }

   /**
    * Thread1 wird erstellt, der die ProgressBar beinhaltet
    */
   private int x = 0;

    class Thread1 implements Runnable {
        public void run() {

            int max = 10000000;
            close.setVisible(false);
            jb.setBounds(770, 20, 150, 25);
            jb.setString("Viewer wird geladen");
            jb.setStringPainted(true);
            top.add(jb);

            if (x == 0) {
            try {
                jb.setVisible(true);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                x = 1;


            for (int i = 1; i <= max; i++) {
                int j = i;
                jb.setValue(j);
            }
            }
            else {
                try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 1; i <= max; i++) {
                jb.setVisible(true);
                int j = i;
                jb.setValue(j);
            }
            }
        }
    }

    /**
     *  Thread2 wird erstellt, der den JRViewer beinhaltet
     */

    class Thread2 implements Runnable {
        public void run() {
            bill.setVisible(true);
            try {
                PrintUI.this.jasper();
                PrintUI.this.validate();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

}

