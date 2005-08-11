package test;

import ecobill.module.base.ui.article.ArticleTable;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.service.impl.BaseServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

// @todo document me!

/**
 * GenericTableTest.
 * <p/>
 * User: rro
 * Date: 10.08.2005
 * Time: 20:18:10
 *
 * @author Roman R&auml;dle
 * @version $Id: GenericTableTest.java,v 1.1 2005/08/11 18:13:54 raedler Exp $
 * @since EcoBill 1.0
 */
public class GenericTableTest extends JFrame {

    public GenericTableTest() throws HeadlessException {
        this.getContentPane();

        new ArticleTable(new HashSet(), new BaseServiceImpl());

        this.setPreferredSize(new Dimension(400, 400));
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new GenericTableTest();
    }
}
