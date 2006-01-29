package ecobill.module.base.ui.message;


import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.IdValueItem;
import ecobill.module.base.domain.Message;
import ecobill.module.base.service.BaseService;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.Enumeration;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;


/**
 * Die <code>News</code> erstellt das User Interface zur Eingabe von Nachrichten.
 * <p/>
 * User: Andreas Weiler
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Andreas Weiler
 * @version $Id: News.java,v 1.8 2006/01/29 23:16:46 raedler Exp $
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

        newsTreeSplitPanel = new JSplitPane();

        treePanel = new JPanel();

        treeScrollPane = new JScrollPane();

        newsTree = new JTree();

        newsPanel = new JPanel();

        addresserLabel = new JLabel();

        addresserTextField = new JTextField();

        subjectLabel = new JLabel();

        subjectTextField = new JTextField();

        messageLabel = new JLabel();

        newsScrollPane = new JScrollPane();

        newsTextArea = new JTextArea();
        newsTextArea.setLineWrap(true);
        newsTextArea.setWrapStyleWord(true);

        initTree();
        newsTreeSplitPanel.setBorder(newsBorder);

        newsTreeSplitPanel.setDividerLocation(200);

        treeScrollPane.setViewportView(newsTree);


        GroupLayout jPanel1Layout = new GroupLayout(treePanel);

        treePanel.setLayout(jPanel1Layout);

        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.LEADING)

                .add(GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()

                .addContainerGap()

                .add(treeScrollPane, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)

                .addContainerGap()));

        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.LEADING)

                .add(GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()

                .addContainerGap()

                .add(treeScrollPane, GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)

                .addContainerGap()));

        newsTreeSplitPanel.setLeftComponent(treePanel);

        newsTextArea.setColumns(20);

        newsTextArea.setRows(5);

        newsScrollPane.setViewportView(newsTextArea);


        GroupLayout jPanel2Layout = new GroupLayout(newsPanel);

        newsPanel.setLayout(jPanel2Layout);

        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.LEADING)

                .add(GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()

                .addContainerGap()

                .add(jPanel2Layout.createParallelGroup(GroupLayout.LEADING)

                .add(newsScrollPane, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)

                .add(subjectTextField, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)

                .add(addresserTextField, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)

                .add(addresserLabel)

                .add(subjectLabel)

                .add(messageLabel))

                .addContainerGap()));

        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.LEADING)

                .add(GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()

                .addContainerGap()

                .add(addresserLabel)

                .addPreferredGap(LayoutStyle.RELATED)

                .add(addresserTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)

                .addPreferredGap(LayoutStyle.RELATED)

                .add(subjectLabel)

                .addPreferredGap(LayoutStyle.RELATED)

                .add(subjectTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)

                .addPreferredGap(LayoutStyle.RELATED)

                .add(messageLabel)

                .addPreferredGap(LayoutStyle.RELATED)

                .add(newsScrollPane, GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)

                .addContainerGap()));

        newsTreeSplitPanel.setRightComponent(newsPanel);


        GroupLayout layout = new GroupLayout(this);

        this.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.LEADING)

                .add(GroupLayout.LEADING, layout.createSequentialGroup()

                .addContainerGap()

                .add(newsTreeSplitPanel, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)

                .addContainerGap()));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.LEADING)

                .add(GroupLayout.LEADING, layout.createSequentialGroup()

                .addContainerGap()

                .add(layout.createParallelGroup(GroupLayout.LEADING)

                .add(GroupLayout.TRAILING, newsTreeSplitPanel, GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE))

                .addContainerGap()));

    }

    private JLabel addresserLabel;

    private JLabel subjectLabel;

    private JLabel messageLabel;

    private JPanel treePanel;

    private JPanel newsPanel;

    private JScrollPane treeScrollPane;

    private JScrollPane newsScrollPane;

    private JSplitPane newsTreeSplitPanel;

    private JTextArea newsTextArea;

    private JTextField addresserTextField;

    private JTextField subjectTextField;

    private JTree newsTree;

     /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        /* TODO: fix me!!!
        overviewVerticalButton.reinitI18N();

        overviewVerticalButton.getButton1().setToolTipText(WorkArea.getMessage(Constants.MESSAGE_BUTTON1_TOOLTIP));
        overviewVerticalButton.getButton2().setToolTipText(WorkArea.getMessage(Constants.MESSAGE_BUTTON2_TOOLTIP));
        overviewVerticalButton.getButton3().setToolTipText(WorkArea.getMessage(Constants.MESSAGE_BUTTON3_TOOLTIP));
        overviewVerticalButton.getButton4().setToolTipText(WorkArea.getMessage(Constants.MESSAGE_BUTTON4_TOOLTIP));
        */

        messageLabel.setText(WorkArea.getMessage(Constants.MESSAGE));
        subjectLabel.setText(WorkArea.getMessage(Constants.SUBJECT));
        addresserLabel.setText(WorkArea.getMessage(Constants.ADDRESSER));
        newsBorder.setTitle(WorkArea.getMessage(Constants.NEWS));
    }
    /**
     * Getter und Setter werden initialisiert
     * @return die jeweiligen Komponenten
     */
    public JTextArea getNewsTextArea() {
        return newsTextArea;
    }

    public void setNewsTextArea(JTextArea newsTextArea) {
        this.newsTextArea = newsTextArea;
    }

    public JTextField getAddresserTextField() {
        return addresserTextField;
    }

    public void setAddresserTextField(JTextField addresserTextField) {
        this.addresserTextField = addresserTextField;
    }

    public JTextField getSubjectTextField() {
        return subjectTextField;
    }

    public void setSubjectTextField(JTextField subjectTextField) {
        this.subjectTextField = subjectTextField;
    }

    public JTree getNewsTree() {
        return newsTree;
    }

    public void setNewsTree(JTree newsTree) {
        this.newsTree = newsTree;
    }

    /**
     * Baum wird als JTree initialisiert
     */
    public void initTree() {

        newsTree = new JTree(new DefaultMutableTreeNode("\u00dcbersicht"));

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

    /**
     * Methode um eine Nachricht zum JTree hinzuzuf�gen
     * @param message
     */
    public void addMessageToTree(Message message) {

        IdValueItem idValueItem = new IdValueItem(message.getId(), message.getSubject());

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

    /**
     * Methode um eine aus dem Baum ausgew�hlte Nachricht in den Textfeldern anzuzeigen
     * @param message
     */
    public void showMessage(Message message) {

        addresserTextField.setText(message.getAddresser());
        subjectTextField.setText(message.getSubject());
        newsTextArea.setText(message.getMessage());
    }

    /**
     * Methode um eine Nachricht zu l�schen
     */
    public void deleteMessage() {

        Object o = newsTree.getLastSelectedPathComponent();

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) o;

        try { if (node.isLeaf()) {

            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

            node.removeFromParent();

            if (parent.getChildCount() < 1) {
                parent.removeFromParent();
            }

            IdValueItem idValueItem = (IdValueItem) node.getUserObject();

            Long diaId = idValueItem.getId();

            baseService.delete(Message.class, diaId);

            addresserTextField.setText(null);
            subjectTextField.setText(null);
            newsTextArea.setText(null);
        }
        }
        catch(NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Bitte eine Nachricht zum l�schen w�hlen", "Information", 1);
        }
    }

    /**
     * Methode um eine neue Nachricht zu erstellen
     */
    public void newMessage() {

        addresserTextField.setText(null);
        subjectTextField.setText(null);
        newsTextArea.setText(null);
    }

    /**
     * Methode um den JTree zu aktualisieren
     */
    public void refreshTree() {

        treeScrollPane.remove(newsTree);
        initTree();
        treeScrollPane.setViewportView(newsTree);
        treeScrollPane.validate();
    }
}

