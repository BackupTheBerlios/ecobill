package ecobill.module.base.ui;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.awt.*;

import org.apache.xalan.xsltc.runtime.Node;
import ecobill.Test;

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
    private HashMap parameters;

    // StandardKonstruktor
    JasperViewer(JPanel panel) {
        this.viewerPanel = panel;
        parameters = new HashMap();
    }

    public void jasper(String jrxmlFilename, List articles) throws Exception {
        System.out.println(jrxmlFilename);
/*        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection con = null;

        try {
    */
             JRXmlDataSource jrxmlDS1 = new JRXmlDataSource("data.xml", "/lieferschein/artikel");


             try{
                 JasperReport js = JasperCompileManager.compileReport(jrxmlFilename);


                 JasperDesign design = JasperManager.loadXmlDesign(jrxmlFilename);
                 JasperReport report = JasperManager.compileReport(jrxmlFilename);
                 Test jrxmlDS2 = new Test(articles);
                 JasperPrint print = JasperFillManager.fillReport(report,parameters,jrxmlDS2);

                 viewer = new JRViewer(print);

                 viewerPanel.add(viewer, BorderLayout.CENTER);

                 JasperExportManager.exportReportToXmlFile(print, "datatest.xml", false);

             }
             catch (JRException e)
    {
                  e.printStackTrace();
                  System.exit(1);
              }
              catch (Exception e)
              {
                  e.printStackTrace();
                  System.exit(1);
              }


    /*    } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
        } */
    }

    public void reJasper() {
        viewerPanel.remove(viewer);
        viewerPanel.repaint();
    }

    public void setParameters(Object key, Object value) {
        parameters.put(key, value);
    }
}
