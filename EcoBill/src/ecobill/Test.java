package ecobill;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.List;
import java.util.Iterator;

import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.ReduplicatedArticle;

/**
 * Created by IntelliJ IDEA.
 * User: basti
 * Date: 26.09.2005
 * Time: 15:54:10
 * To change this template use File | Settings | File Templates.
 */
public class Test implements JRDataSource {

    private ReduplicatedArticle article;

    private Iterator iter;

    private List articles;

    public Test(List articles) {
        this.iter = articles.iterator();
        this.articles = articles;
    }

    public List getArticles() {
        return articles;
    }

    public void setArticles(List articles) {

        iter = articles.iterator();

        this.articles = articles;
    }

    public boolean next() throws JRException {
        System.out.println("Test.next");

        if (iter.hasNext()) {
            article = (ReduplicatedArticle) iter.next();
            return true;
        }

        return false;
    }

    public Object getFieldValue(JRField jrField) throws JRException {

        String name = jrField.getName();

        if ("ANZAHL".equals(name)) {
           return article.getAmount();
        }
        else if ("ARTIKELNR".equals(name)) {
            return "Artikel 08/15";
        }
        else if ("ARTIKELPREIS".equals(name)) {
           return article.getPrice();
        }

        return "hello";
    }
}
