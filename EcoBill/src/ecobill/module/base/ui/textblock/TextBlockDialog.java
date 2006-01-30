package ecobill.module.base.ui.textblock;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.TextBlock;
import ecobill.module.base.ui.component.JToolBarButton;
import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.ComponentUtils;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// @todo document me!

/**
 * TextBlockDialog.
 * <p/>
 * User: rro
 * Date: 10.12.2005
 * Time: 15:24:42
 *
 * @author Roman R&auml;dle
 * @version $Id: TextBlockDialog.java,v 1.3 2006/01/30 23:43:14 raedler Exp $
 * @since EcoBill 1.1
 */
public class TextBlockDialog extends JDialog implements Internationalization {

    /**
     * Der <code>BaseService</code> ist die Business Logik. Unter anderem können hierdurch Daten
     * aus der Datenbank ausgelesen und gespeichert werden.
     */
    private BaseService baseService;

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
     * TODO: document me!!!
     */
    private JTextComponent harvester;

    private TextBlockTable textBlockTable;
    private Input input = new Input();
    private JPanel textBlockPanel = new JPanel();
    private JButton closeB = new JButton();
    private JButton pasteB = new JButton();

    private Long actualTextBlockId;

    /**
     * TODO: document me!!!
     *
     * @param owner
     * @param modal
     * @param harvester
     * @param baseService
     */
    public TextBlockDialog(Frame owner, boolean modal, JTextComponent harvester, BaseService baseService) {
        super(owner, WorkArea.getMessage(Constants.TEXT_BLOCK), modal);

        this.baseService = baseService;
        this.harvester = harvester;

        initComponents();
        initLayout();

        reinitI18N();

        pack();

        ComponentUtils.centerComponentOnScreen(this);

        setVisible(true);
    }

    /**
     * TODO: document me!!!
     */
    private void initComponents() {
        textBlockTable = new TextBlockTable(this, baseService);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        closeB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                TextBlockDialog.this.dispose();
            }
        });

        pasteB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                TextBlockDialog.this.harvester.setText(input.getTextBlockText());
                TextBlockDialog.this.dispose();
            }
        });
    }

    private void initLayout() {

        GroupLayout textBlockPanelLayout = new GroupLayout(textBlockPanel);

        textBlockPanel.setLayout(textBlockPanelLayout);

        textBlockPanelLayout.setHorizontalGroup(
                textBlockPanelLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, textBlockPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(textBlockPanelLayout.createParallelGroup(GroupLayout.LEADING)
                                .add(GroupLayout.TRAILING, textBlockPanelLayout.createSequentialGroup()
                                        .add(pasteB)
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(closeB))
                                .add(textBlockTable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(input, GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE))
                        .addContainerGap())
        );

        textBlockPanelLayout.linkSize(new Component[]{closeB, pasteB}, GroupLayout.HORIZONTAL);

        textBlockPanelLayout.setVerticalGroup(
                textBlockPanelLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, textBlockPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(input, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(textBlockTable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(textBlockPanelLayout.createParallelGroup(GroupLayout.BASELINE)
                                .add(closeB)
                                .add(pasteB))
                        .addContainerGap())
        );
        getContentPane().add(textBlockPanel, BorderLayout.CENTER);
        getContentPane().add(createToolBar(), BorderLayout.NORTH);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {
        closeB.setText(WorkArea.getMessage(Constants.CLOSE));
        pasteB.setText(WorkArea.getMessage(Constants.PASTE));
    }

    /**
     * Erzeugt die <code>JToolBar</code> für dieses User Interface.
     */
    private JToolBar createToolBar() {

        JToolBar toolBar = new JToolBar();

        JToolBarButton newTextBlock = new JToolBarButton(new ImageIcon("images/textblock_new.png"));
        newTextBlock.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                input.resetInput();
            }
        });

        JToolBarButton okTextBlock = new JToolBarButton(new ImageIcon("images/textblock_ok.png"));
        okTextBlock.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                saveOrUpdateTextBlock();
            }
        });

        JToolBarButton deleteTextBlock = new JToolBarButton(new ImageIcon("images/textblock_delete.png"));
        deleteTextBlock.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                if (actualTextBlockId != null) {
                    baseService.delete(TextBlock.class, actualTextBlockId);
                }

                input.resetInput();
                textBlockTable.renewTableModel();
            }
        });

        JToolBarButton refreshTextBlock = new JToolBarButton(new ImageIcon("images/refresh.png"));
        refreshTextBlock.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                textBlockTable.renewTableModel();
            }
        });

        toolBar.add(newTextBlock);
        toolBar.add(okTextBlock);
        toolBar.add(deleteTextBlock);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(refreshTextBlock);

        return toolBar;
    }

    private void saveOrUpdateTextBlock() {

        TextBlock textBlock = new TextBlock();

        if (actualTextBlockId != null) {
            textBlock = (TextBlock) baseService.load(TextBlock.class, actualTextBlockId);
        }

        if (textBlock == null) {
            textBlock = new TextBlock();
        }

        textBlock.setName(input.getTextBlockName());
        textBlock.setText(input.getTextBlockText());

        baseService.saveOrUpdate(textBlock);

        actualTextBlockId = textBlock.getId();

        textBlockTable.renewTableModel();
    }

    public void showTextBlock(Long textBlockId) {

        TextBlock textBlock = (TextBlock) baseService.load(TextBlock.class, textBlockId);

        input.setTextBlockName(textBlock.getName());
        input.setTextBlockText(textBlock.getText());

        actualTextBlockId = textBlockId;
    }
}
