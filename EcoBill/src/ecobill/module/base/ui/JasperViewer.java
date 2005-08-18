package ecobill.module.base.ui;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JRViewer;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Paul Chef
 * Date: 13.08.2005
 * Time: 12:43:59
 * To change this template use File | Settings | File Templates.
 */
public class JasperViewer {

    // JPanel auf das der JRViewer gelegt wird
    private JPanel viewerPanel;
    private JRViewer viewer;

    // StandardKonstruktor
    JasperViewer(JPanel panel) {
        this.viewerPanel = panel;
    }

    public void jasper(String jrxmlFilename) throws Exception {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection con = null;

        try {
            // Connection wird initialisiert
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecobill", "root", "x2kub2");
            // JasperReport wird initialisiert
            JasperReport js = JasperCompileManager.compileReport(jrxmlFilename);
            // JasperReport wird zu Pdf konvertiert
            JasperRunManager.runReportToPdf(js, new HashMap(), con);
            // JRViewer wird mit dem Report gefüllt und HashMap und Connection übergeben
            viewer = new JRViewer(JasperFillManager.fillReport(js, new HashMap(), con));
            // JRViewer wird auf viewerPanel gelegt
            viewerPanel.add(viewer, BorderLayout.CENTER);

        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
        }
    }

    public void reJasper() {
        viewerPanel.remove(viewer);
        viewerPanel.repaint();
    }
}
