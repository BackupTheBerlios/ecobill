package ecobill.core.ui;

import org.jdesktop.layout.GroupLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;

import ecobill.core.util.ComponentUtils;
import ecobill.core.springframework.PropertyPreferencesPlaceholderConfigurer;
import ecobill.Start;

/**
 * ConnectionSettings
 * <p/>
 * User: rro
 * Date: 02.02.2006
 * Time: 16:21:50
 *
 * @author Roman R&auml;dle
 * @version $Id: ConnectionSettings.java,v 1.1 2006/02/02 22:18:27 raedler Exp $
 * @since EcoBill 1.1
 */
public class ConnectionSettings extends JFrame {

    private Start start;

    private JButton applyB;
    private JLabel driverClassL;
    private JTextField driverClassTF;
    private JButton exitB;
    private JButton okB;
    private JLabel passwordL;
    private JPasswordField passwordPF;
    private JLabel urlL;
    private JTextField urlTF;
    private JLabel usernameL;
    private JTextField usernameTF;

    public ConnectionSettings(Start start) throws HeadlessException {

        this.start = start;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initComponents();
        initLayout();

        pack();

        ComponentUtils.centerComponentOnScreen(this);

        setVisible(true);
    }

    private void initComponents() {
        driverClassL = new JLabel();
        driverClassTF = new JTextField();
        urlL = new JLabel();
        urlTF = new JTextField();
        usernameL = new JLabel();
        usernameTF = new JTextField();
        passwordL = new JLabel();
        passwordPF = new JPasswordField();
        applyB = new JButton();
        exitB = new JButton();
        okB = new JButton();

        Preferences prefs = Preferences.userNodeForPackage(PropertyPreferencesPlaceholderConfigurer.class);
        driverClassTF.setText(prefs.get(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_DRIVER_CLASS, "com.mysql.jdbc.Driver"));
        urlTF.setText(prefs.get(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_URL, "jdbc:mysql://localhost:3306/ecobill"));
        usernameTF.setText(prefs.get(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_USERNAME, "ecobill"));
        passwordPF.setText(prefs.get(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_PASSWORD, "ecobill"));

        applyB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Preferences prefs = Preferences.userNodeForPackage(PropertyPreferencesPlaceholderConfigurer.class);

                prefs.put(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_DRIVER_CLASS, driverClassTF.getText());
                prefs.put(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_URL, urlTF.getText());
                prefs.put(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_USERNAME, usernameTF.getText());
                prefs.put(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_PASSWORD, String.valueOf(passwordPF.getPassword()));
            }
        });

        exitB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ConnectionSettings.this.dispose();

                System.exit(0);
            }
        });

        okB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Preferences prefs = Preferences.userNodeForPackage(PropertyPreferencesPlaceholderConfigurer.class);

                prefs.put(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_DRIVER_CLASS, driverClassTF.getText());
                prefs.put(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_URL, urlTF.getText());
                prefs.put(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_USERNAME, usernameTF.getText());
                prefs.put(PropertyPreferencesPlaceholderConfigurer.HIBERNATE_CONNECTION_PASSWORD, String.valueOf(passwordPF.getPassword()));

                ConnectionSettings.this.dispose();

                if (start != null) {
                    start.startUp(driverClassTF.getText(), urlTF.getText(), usernameTF.getText(), String.valueOf(passwordPF.getPassword()));
                }
            }
        });

        driverClassL.setText("Driver Class");

        urlL.setText("URL");

        usernameL.setText("Username");

        passwordL.setText("Password");

        applyB.setText("\u00dcbernehmen");

        exitB.setText("Abbrechen");

        okB.setText("OK");
    }

    private void initLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                                .add(driverClassL)
                                                .add(urlL)
                                                .add(usernameL)
                                                .add(passwordL))
                                        .add(43, 43, 43)
                                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                        .add(passwordPF, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                        .add(urlTF, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                        .add(driverClassTF, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                        .add(usernameTF, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)))
                                .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(okB)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(exitB)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(applyB)))
                        .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[]{applyB, exitB, okB}, GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(driverClassL)
                                .add(driverClassTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(urlL)
                                .add(urlTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(usernameL)
                                .add(usernameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(passwordL)
                                .add(passwordPF, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                        .add(30, 30, 30)
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(applyB)
                                .add(exitB)
                                .add(okB))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
}
