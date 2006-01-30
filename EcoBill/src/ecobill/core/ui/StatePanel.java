package ecobill.core.ui;

/**
 * @author Roman Georg Rädle
 */
public class StatePanel extends javax.swing.JPanel {

    /**
     * Creates new form StatePanel
     */
    public StatePanel() {
        initComponents();
    }

    private void initComponents() {
        progressBar = new javax.swing.JProgressBar(0, 0, 100);

        setLayout(null);

        progressBar.setBounds(2, 2, 150, 15);

        add(progressBar);

        /*
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 220, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        */
    }
    // </editor-fold>


    // Variables declaration - do not modify
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration

    public void setProgressPercentage(int percentage) {
        progressBar.setValue(percentage);
    }
}