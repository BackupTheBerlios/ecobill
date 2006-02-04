package ecobill.core.netupdate.ui;

import ecobill.core.netupdate.NetUpdateFeedback;
import ecobill.core.util.ComponentUtils;
import ecobill.core.ui.MainFrame;
import ecobill.core.ui.ConnectionSettings;
import ecobill.Start;

import javax.swing.*;

import org.jdesktop.layout.GroupLayout;

import java.awt.*;
import java.util.Locale;
import java.text.NumberFormat;

// @todo document me!

/**
 * <code>NetUpdater</code>.
 * <p/>
 * User: rro
 * Date: 03.02.2006
 * Time: 15:59:22
 *
 * @author Roman R&auml;dle
 * @version $Id: NetUpdater.java,v 1.1 2006/02/04 00:46:52 raedler Exp $
 * @since EcoBill 1.1
 */
public class NetUpdater extends JFrame implements NetUpdateFeedback {

    private MainFrame mainFrame;

    private boolean updated = false;

    private JLabel nameL;
    private JLabel nameContentL;
    private JLabel infoL;
    private JLabel infoContentL;
    private JLabel fileL;
    private JLabel fileContentL;

    private JProgressBar progressPB;
    private JLabel progressStateL;

    NumberFormat format = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private String[] rateLabels = new String[]{"B/s", "KB/s", "MB/s", "GB/s"};

    public NetUpdater() throws HeadlessException {

        setTitle("EcoBill - NetUpdate Tool");

        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);

        initComponents();
        initLayout();

        pack();

        ComponentUtils.centerComponentOnScreen(this);

        setVisible(true);
    }

    public NetUpdater(MainFrame mainFrame) throws HeadlessException {

        this.mainFrame = mainFrame;

        setTitle("EcoBill - NetUpdate Tool");

        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);

        initComponents();
        initLayout();

        pack();

        ComponentUtils.centerComponentOnScreen(this);

        setVisible(true);
    }

    private void initComponents() {
        nameL = new JLabel();
        nameContentL = new JLabel();
        infoL = new JLabel();
        infoContentL = new JLabel();
        fileL = new JLabel();
        fileContentL = new JLabel();
        progressPB = new JProgressBar();
        progressStateL = new JLabel();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        nameL.setText("Name");
        nameContentL.setText(" ");
        infoL.setText("Info");
        infoContentL.setText(" ");
        fileL.setText("Datei");
        fileContentL.setText(" ");
        progressStateL.setText(" ");
    }

    private void initLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.LEADING)
                    .add(progressPB, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                            .add(nameL)
                            .add(infoL))
                        .add(24, 24, 24)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                            .add(infoContentL)
                            .add(nameContentL)
                            .add(fileContentL)))
                    .add(fileL)
                    .add(progressStateL))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(nameL)
                    .add(nameContentL))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(infoL)
                    .add(infoContentL))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(fileL)
                    .add(fileContentL))
                .add(21, 21, 21)
                .add(progressPB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(progressStateL)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    private String[] createBreakedNumbers(int number) {

        String[] breakedNumbers = new String[2];

        double breakNumber = number;
        int i = 0;
        for (; breakNumber > 1000; i++) {
            breakNumber /= 1024;
        }

        breakedNumbers[0] = format.format(breakNumber);
        breakedNumbers[1] = rateLabels[i];

        return breakedNumbers;
    }

    public void upToDate() {
        fileContentL.setText("Alle Dateien sind auf dem aktuellen Stand.");
    }

    public void start(String name) {

        updated = true;

        nameContentL.setText(name);
    }

    public void startResource(String name, String action) {
        infoContentL.setText(action + " " + name);
        progressPB.setValue(0);
    }

    public void process(String name, int total, int part, int rate) {

        progressPB.setMaximum(total);
        progressPB.setValue(part);

        String[] breakTotal = createBreakedNumbers(total);
        String[] breakPart = createBreakedNumbers(part);
        String[] breakRate = createBreakedNumbers(rate);

        fileContentL.setText(name);
        progressStateL.setText(breakPart[0] + " " + breakPart[1] + " von " + breakTotal[0] + " " + breakTotal[1] + " bei " + breakRate[0] + " " + breakRate[1]);
    }

    public void doneResource() {
        progressPB.setValue(progressPB.getMaximum());
    }

    public void done() {
        System.out.println("NetUpdater.done");
    }

    public void finished() {

        progressPB.setMaximum(3);
        progressPB.setValue(0);

        Thread thread = new Thread() {

            public void run() {

                for (int i = 0; i < 4; i++) {

                    nameContentL.setText("Update finished.");
                    infoContentL.setText("Fenster wird in " + (3 - i) + " Sekunden geschlossen.");
                    progressPB.setValue(i);

                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }

                NetUpdater.this.setVisible(false);

                if (updated) {
                    JOptionPane.showConfirmDialog(mainFrame, "EcoBill muss neu gestartet werden.", "EcoBill Neustart", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.exit();
                }
            }
        };

        thread.start();
    }
}