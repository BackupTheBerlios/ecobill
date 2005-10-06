package ecobill.module.base.ui.start;


import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.module.base.domain.Message;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.border.TitledBorder;
import java.awt.*;



/**
 * Das <code>InputFirm</code> <code>JPanel</code> stellt die Eingabemöglichkeit für zusätzliche
 * Firmendaten zur Verfügung.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: News.java,v 1.17 2005/10/06 21:09:08 jfuckerweiler Exp $
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

    private TitledBorder addresserBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.ADDRESSER), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));
    private TitledBorder subjectBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.SUBJECT), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));
    private TitledBorder newsBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.NEWS), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));
    //private TitledBorder actionsBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.BANK_DATA), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));
    private TitledBorder overviewBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.OVERVIEW), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private DefaultMutableTreeNode user = new DefaultMutableTreeNode("Benutzer");
    private DefaultMutableTreeNode user1 = new DefaultMutableTreeNode("Maier");
    private DefaultMutableTreeNode news = new DefaultMutableTreeNode("Alle Nachrichten");
    private DefaultMutableTreeNode newNews = new DefaultMutableTreeNode("Neue Nachrichten");

    private void initComponents() {
            jPanel1 = new javax.swing.JPanel();
            jTextField1 = new javax.swing.JTextField();
            jPanel2 = new javax.swing.JPanel();
            jTextField2 = new javax.swing.JTextField();
            jScrollPane1 = new javax.swing.JScrollPane();
            jTextArea1 = new javax.swing.JTextArea();
            jPanel3 = new javax.swing.JPanel();
            jScrollPane2 = new javax.swing.JScrollPane();
            overviewVerticalButton = new ecobill.module.base.ui.component.VerticalButton();


            user.add(user1);
            user1.add(news);
            user1.add(newNews);
            jTree1 = new JTree(user);


            overviewVerticalButton.getButton1().setVisible(true);
            overviewVerticalButton.getButton2().setVisible(true);
            overviewVerticalButton.getButton3().setVisible(true);
            overviewVerticalButton.getButton4().setVisible(true);

         overviewVerticalButton.getButton1().setIcon(new ImageIcon("images/news_new.png"));
         overviewVerticalButton.getButton2().setIcon(new ImageIcon("images/news_ok.png"));
         overviewVerticalButton.getButton3().setIcon(new ImageIcon("images/news_delete.png"));
         overviewVerticalButton.getButton4().setIcon(new ImageIcon("images/refresh.png"));

            jPanel1.setBorder(addresserBorder);

            org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                    .add(jTextField1)
                    .addContainerGap())
            );

            jPanel2.setBorder(subjectBorder);

            org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                    .add(jTextField2)
                    .addContainerGap())
            );

            jScrollPane1.setBorder(newsBorder);
            jTextArea1.setRows(5);
            jScrollPane1.setViewportView(jTextArea1);

            jPanel3.setBorder(overviewBorder);
            jScrollPane2.setViewportView(jTree1);

            org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 260, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );
            jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3Layout.createSequentialGroup()
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 270, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                    .addContainerGap()
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2)
                                    .add(jPanel1)))
                            .add(33, 33, 33))
                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                            .add(overviewVerticalButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(19, 19, 19)
                            .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 49, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 49, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 111, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(overviewVerticalButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(30, Short.MAX_VALUE))
            );
        }
        // </editor-fold>


        // Variables declaration - do not modify
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JTextArea jTextArea1;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JTextField jTextField2;
        private javax.swing.JTree jTree1;
        private ecobill.module.base.ui.component.VerticalButton overviewVerticalButton;
        // End of variables declaration

         
    public void reinitI18N() {

        overviewVerticalButton.getButton1().setToolTipText(WorkArea.getMessage(Constants.START_BUTTON1_TOOLTIP));
        overviewVerticalButton.getButton2().setToolTipText(WorkArea.getMessage(Constants.START_BUTTON2_TOOLTIP));
        overviewVerticalButton.getButton3().setToolTipText(WorkArea.getMessage(Constants.START_BUTTON3_TOOLTIP));
        overviewVerticalButton.getButton4().setToolTipText(WorkArea.getMessage(Constants.START_BUTTON4_TOOLTIP));

        addresserBorder.setTitle(WorkArea.getMessage(Constants.ADDRESSER));
        subjectBorder.setTitle(WorkArea.getMessage(Constants.SUBJECT));
        newsBorder.setTitle(WorkArea.getMessage(Constants.NEWS));
        overviewBorder.setTitle(WorkArea.getMessage(Constants.OVERVIEW));

        overviewVerticalButton.reinitI18N();

    }

    public void save() {
        Message message = new Message();

        message.setAddresser(jTextField1.getText());
        message.setSubject(jTextField2.getText());
        message.setMessage(jTextArea1.getText());
    }
}
