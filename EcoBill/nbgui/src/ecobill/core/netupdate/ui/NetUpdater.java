/*
 * NetUpdater.java
 *
 * Created on 3. Februar 2006, 17:43
 */

package ecobill.core.netupdate.ui;

/**
 *
 * @author  Romsl
 */
public class NetUpdater extends javax.swing.JFrame {
    
    /** Creates new form NetUpdater */
    public NetUpdater() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        nameL = new javax.swing.JLabel();
        nameContentL = new javax.swing.JLabel();
        infoL = new javax.swing.JLabel();
        infoContentL = new javax.swing.JLabel();
        fileL = new javax.swing.JLabel();
        fileContentL = new javax.swing.JLabel();
        progressPB = new javax.swing.JProgressBar();
        progressStateL = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        nameL.setText("Name");

        nameContentL.setText("ecobill-1.1.1");

        infoL.setText("Info");

        infoContentL.setText("Update Hibernate3 Library");

        fileL.setText("Datei");

        fileContentL.setText("/lib/hibernate3.jar");

        progressStateL.setText("321 von 391 KB bei 121 KB/s");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(progressPB, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(nameL)
                            .add(infoL))
                        .add(24, 24, 24)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(infoContentL)
                            .add(nameContentL)
                            .add(fileContentL)))
                    .add(fileL)
                    .add(progressStateL))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(nameL)
                    .add(nameContentL))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(infoL)
                    .add(infoContentL))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fileL)
                    .add(fileContentL))
                .add(21, 21, 21)
                .add(progressPB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(progressStateL)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NetUpdater().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fileContentL;
    private javax.swing.JLabel fileL;
    private javax.swing.JLabel infoContentL;
    private javax.swing.JLabel infoL;
    private javax.swing.JLabel nameContentL;
    private javax.swing.JLabel nameL;
    private javax.swing.JProgressBar progressPB;
    private javax.swing.JLabel progressStateL;
    // End of variables declaration//GEN-END:variables
    
}