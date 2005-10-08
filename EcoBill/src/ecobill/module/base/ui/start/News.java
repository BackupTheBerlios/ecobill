package ecobill.module.base.ui.start;


import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.IdValueItem;
import ecobill.module.base.domain.Message;
import ecobill.module.base.ui.component.VerticalButton;
import ecobill.module.base.service.BaseService;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.Enumeration;


/**
 * Das <code>InputFirm</code> <code>JPanel</code> stellt die Eingabemöglichkeit für zusätzliche
 * Firmendaten zur Verfügung.
 * <p/>
 * User: aw
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Andreas Weiler
 * @version $Id: News.java,v 1.47 2005/10/08 12:11:29 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class News extends JPanel implements Internationalization {

    /**
     * Erzeugt eine neues <code>InputFirm</code> Panel.
     */
    public News(BaseService baseService) {

        this.baseService = baseService;


        initComponents();

        reinitI18N();
    }

    private BaseService baseService;

    private TitledBorder newsBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.NEWS), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    private void initComponents() {

        newsTreeSplitPanel = new javax.swing.JSplitPane();

        treePanel = new javax.swing.JPanel();

        treeScrollPane = new javax.swing.JScrollPane();

        newsTree = new javax.swing.JTree();

        newsPanel = new javax.swing.JPanel();

        addresserLabel = new javax.swing.JLabel();

        addresserTextField = new javax.swing.JTextField();

        subjectLabel = new javax.swing.JLabel();

        subjectTextField = new javax.swing.JTextField();

        messageLabel = new javax.swing.JLabel();

        newsScrollPane = new javax.swing.JScrollPane();

        newsTextArea = new javax.swing.JTextArea();

        overviewVerticalButton = new ecobill.module.base.ui.component.VerticalButton();

        initTree();
        newsTreeSplitPanel.setBorder(newsBorder);

        newsTreeSplitPanel.setDividerLocation(200);

        treeScrollPane.setViewportView(newsTree);


        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(treePanel);

        treePanel.setLayout(jPanel1Layout);

        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()

                .addContainerGap()

                .add(treeScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)

                .addContainerGap()));

        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()

                .addContainerGap()

                .add(treeScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)

                .addContainerGap()));

        newsTreeSplitPanel.setLeftComponent(treePanel);

        overviewVerticalButton.getButton1().setVisible(true);
        overviewVerticalButton.getButton1().setIcon(new ImageIcon("images/news_new.png"));
        overviewVerticalButton.getButton2().setVisible(true);
        overviewVerticalButton.getButton2().setIcon(new ImageIcon("images/news_ok.png"));
        overviewVerticalButton.getButton3().setVisible(true);
        overviewVerticalButton.getButton3().setIcon(new ImageIcon("images/news_delete.png"));
        overviewVerticalButton.getButton4().setVisible(true);
        overviewVerticalButton.getButton4().setIcon(new ImageIcon("images/refresh.png"));


        newsTextArea.setColumns(20);

        newsTextArea.setRows(5);

        newsScrollPane.setViewportView(newsTextArea);


        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(newsPanel);

        newsPanel.setLayout(jPanel2Layout);

        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()

                .addContainerGap()

                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

                .add(newsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)

                .add(subjectTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)

                .add(addresserTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)

                .add(addresserLabel)

                .add(subjectLabel)

                .add(messageLabel))

                .addContainerGap()));

        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()

                .addContainerGap()

                .add(addresserLabel)

                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

                .add(addresserTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

                .add(subjectLabel)

                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

                .add(subjectTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

                .add(messageLabel)

                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

                .add(newsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)

                .addContainerGap()));

        newsTreeSplitPanel.setRightComponent(newsPanel);


        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);

        this.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()

                .addContainerGap()

                .add(overviewVerticalButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

                .add(newsTreeSplitPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)

                .addContainerGap()));

        layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()

                .addContainerGap()

                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

                .add(org.jdesktop.layout.GroupLayout.TRAILING, newsTreeSplitPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)

                .add(overviewVerticalButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE))

                .addContainerGap()));

    }

    // </editor-fold>





    // Variables declaration - do not modify

    private javax.swing.JLabel addresserLabel;

    private javax.swing.JLabel subjectLabel;

    private javax.swing.JLabel messageLabel;

    private javax.swing.JPanel treePanel;

    private javax.swing.JPanel newsPanel;

    private javax.swing.JScrollPane treeScrollPane;

    private javax.swing.JScrollPane newsScrollPane;

    private javax.swing.JSplitPane newsTreeSplitPanel;

    private javax.swing.JTextArea newsTextArea;

    private javax.swing.JTextField addresserTextField;

    private javax.swing.JTextField subjectTextField;

    private javax.swing.JTree newsTree;

    private ecobill.module.base.ui.component.VerticalButton overviewVerticalButton;

    // End of variables declaration



    public void reinitI18N() {

        overviewVerticalButton.getButton1().setToolTipText(WorkArea.getMessage(Constants.START_BUTTON1_TOOLTIP));
        overviewVerticalButton.getButton2().setToolTipText(WorkArea.getMessage(Constants.START_BUTTON2_TOOLTIP));
        overviewVerticalButton.getButton3().setToolTipText(WorkArea.getMessage(Constants.START_BUTTON3_TOOLTIP));
        overviewVerticalButton.getButton4().setToolTipText(WorkArea.getMessage(Constants.START_BUTTON4_TOOLTIP));

        messageLabel.setText(WorkArea.getMessage(Constants.MESSAGE));
        subjectLabel.setText(WorkArea.getMessage(Constants.SUBJECT));
        addresserLabel.setText(WorkArea.getMessage(Constants.ADDRESSER));
        newsBorder.setTitle(WorkArea.getMessage(Constants.NEWS));

        overviewVerticalButton.reinitI18N();

    }

    public JTextArea getnewsTextArea() {
        return newsTextArea;
    }

    public void setnewsTextArea(JTextArea newsTextArea) {
        this.newsTextArea = newsTextArea;
    }

    public JTextField getaddresserTextField() {
        return addresserTextField;
    }

    public void setaddresserTextField(JTextField addresserTextField) {
        this.addresserTextField = addresserTextField;
    }

    public JTextField getsubjectTextField() {
        return subjectTextField;
    }

    public void setsubjectTextField(JTextField subjectTextField) {
        this.subjectTextField = subjectTextField;
    }

    public VerticalButton getOverviewVerticalButton() {
        return overviewVerticalButton;
    }

    public JTree getnewsTree() {
        return newsTree;
    }

    public void setnewsTree(JTree newsTree) {
        this.newsTree = newsTree;
    }

    public void initTree() {

        newsTree = new JTree(new DefaultMutableTreeNode("Übersicht"));

        List messages = baseService.loadAll(Message.class);

        for (Object o : messages) {
            Message message = (Message) o;

            addMessageToTree(message);
        }

        // Tree listener
        newsTree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {

                Object o = newsTree.getLastSelectedPathComponent();

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) o;

                if (node != null && node.isLeaf()) {

                    IdValueItem idValueItem = (IdValueItem) node.getUserObject();

                    Long diaId = idValueItem.getId();

                    Message m = (Message) baseService.load(Message.class, diaId);

                    showMessage(m);
                }
            }
        });

        newsTree.updateUI();
    }

    public void addMessageToTree(Message message) {

        IdValueItem idValueItem = new IdValueItem();
        idValueItem.setId(message.getId());
        idValueItem.setValue(message.getSubject());

        boolean found = false;

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) newsTree.getModel().getRoot();

        Enumeration childs = root.children();

        while (childs.hasMoreElements()) {

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) childs.nextElement();

            if (node.getUserObject().equals(message.getAddresser())) {

                found = true;

                node.add(new DefaultMutableTreeNode(idValueItem));

                break;
            }
        }

        if (!found) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(message.getAddresser());
            node.add(new DefaultMutableTreeNode(idValueItem));

            root.add(node);
        }
    }

    public void showMessage(Message message) {

        addresserTextField.setText(message.getAddresser());
        subjectTextField.setText(message.getSubject());
        newsTextArea.setText(message.getMessage());
    }

    public void deleteMessage() {

        Object o = newsTree.getLastSelectedPathComponent();

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) o;

        if (node.isLeaf()) {

            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

            node.removeFromParent();

            if (parent.getChildCount() < 1) {
                parent.removeFromParent();
            }

            IdValueItem idValueItem = (IdValueItem) node.getUserObject();

            Long diaId = idValueItem.getId();

            baseService.delete(Message.class, diaId);
        }
    }

    public void newMessage() {

        addresserTextField.setText(null);
        subjectTextField.setText(null);
        newsTextArea.setText(null);
    }

    public void refreshTree() {

        treeScrollPane.remove(newsTree);
        initTree();
        treeScrollPane.setViewportView(newsTree);
        treeScrollPane.validate();
    }
}

