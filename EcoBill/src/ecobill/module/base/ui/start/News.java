package ecobill.module.base.ui.start;


import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;



/**
 * Das <code>InputFirm</code> <code>JPanel</code> stellt die Eingabemöglichkeit für zusätzliche
 * Firmendaten zur Verfügung.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: News.java,v 1.4 2005/10/06 14:45:32 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class News extends JPanel implements Internationalization {

    /**
     * Erzeugt eine neues <code>InputFirm</code> Panel.
     */
    public News() {
        initComponents();

        reinitI18N();
    }

    private void initComponents() {
           jTextField1 = new javax.swing.JTextField();
           jTextField2 = new javax.swing.JTextField();
           jScrollPane1 = new javax.swing.JScrollPane();
           jTextArea1 = new javax.swing.JTextArea();
           jScrollPane2 = new javax.swing.JScrollPane();
           jTree1 = new javax.swing.JTree();
           jPanel1 = new javax.swing.JPanel();
           jButton1 = new javax.swing.JButton();
           jButton2 = new javax.swing.JButton();
           jButton5 = new javax.swing.JButton();
           jButton6 = new javax.swing.JButton();

           jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Absender", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));

           jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Betreff", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));

           jTextArea1.setColumns(20);
           jTextArea1.setRows(5);
           jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nachricht", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
           jScrollPane1.setViewportView(jTextArea1);

           jTree1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "\u00dcbersicht", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
           jScrollPane2.setViewportView(jTree1);

           jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Aktionen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
           jButton1.setText("jButton1");

           jButton2.setText("jButton2");

           jButton5.setText("jButton5");

           jButton6.setText("jButton6");

           org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
           jPanel1.setLayout(jPanel1Layout);
           jPanel1Layout.setHorizontalGroup(
               jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                   .add(20, 20, 20)
                   .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                       .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                           .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                           .add(jButton5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                       .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                           .add(jButton2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                           .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                           .add(jButton6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                   .addContainerGap())
           );
           jPanel1Layout.setVerticalGroup(
               jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                   .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                       .add(jButton5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                       .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
                   .add(21, 21, 21)
                   .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                       .add(jButton6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                       .add(jButton2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                   .addContainerGap())
           );

           org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
           this.setLayout(layout);
           layout.setHorizontalGroup(
               layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                   .addContainerGap()
                   .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                       .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                           .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 244, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 36, Short.MAX_VALUE)
                           .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 208, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .add(34, 34, 34))
                       .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                               .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                               .addContainerGap())
                           .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                               .add(jTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                               .add(265, 265, 265))
                           .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                               .add(jTextField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                               .add(265, 265, 265)))))
           );
           layout.setVerticalGroup(
               layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                   .addContainerGap()
                   .add(jTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                   .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                   .add(jTextField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                   .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                   .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                   .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                   .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                       .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 330, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                       .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                   .add(33, 33, 33))
           );
       }
       // </editor-fold>


       // Variables declaration - do not modify
       private javax.swing.JButton jButton1;
       private javax.swing.JButton jButton2;
       private javax.swing.JButton jButton5;
       private javax.swing.JButton jButton6;
       private javax.swing.JPanel jPanel1;
       private javax.swing.JScrollPane jScrollPane1;
       private javax.swing.JScrollPane jScrollPane2;
       private javax.swing.JTextArea jTextArea1;
       private javax.swing.JTextField jTextField1;
       private javax.swing.JTextField jTextField2;
       private javax.swing.JTree jTree1;
       // End of variables declaration
         
    public void reinitI18N() {

    }
}
