package ecobill.module.base.dao.impl;

import ecobill.module.base.dao.BaseDao;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.Person;
import ecobill.module.base.domain.BusinessPartner;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Collections;

/**
 * Das <code>BaseDaoImpl</code> ist eine Implementation des Interfaces <code>BaseDao</code>.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 12:29:43
 *
 * @author Roman R&auml;dle
 * @version $Id: BaseDaoImpl.java,v 1.2 2005/07/29 20:59:07 raedler Exp $
 * @see BaseDao
 * @since EcoBill 1.0
 */
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

    /**
     * Gibt den <code>BusinessPartner</code>, dessen ID der Parameter ID entspricht, zurück.
     *
     * @param id Die ID unter der ein <code>BusinessPartner</code> in der Datenbank abegelegt
     *           ist.
     * @return Der <code>BusinessPartner</code> der unter dieser ID gefunden wurde.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             aufgetritt.
     * @see BaseDao#getBusinessPartnerById(Long)
     */
    public BusinessPartner getBusinessPartnerById(Long id) {
        return (BusinessPartner) getHibernateTemplate().load(BusinessPartner.class, id);
    }

    /**
     * Speichert den <code>BusinessPartner</code> falls dieser in der Datenbank noch nicht existiert
     * andernfalls wird dieser geändert.
     * <br/>
     * -> ausschlaggebend ist die ID
     * - nicht vorhanden bedeutet speichern
     * - vorhanden und existiert in der Datenbank bedeutet ändern.
     *
     * @param bp Der <code>BusinessPartner</code> der in der Datenbank bespeichert oder geändert
     *           werden soll.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             aufgetritt.
     * @see BaseDao#saveOrUpdateBusinessPartner(ecobill.module.base.domain.BusinessPartner)
     */
    public void saveOrUpdateBusinessPartner(BusinessPartner bp) throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(bp);
    }

    /**
     * Gibt den <code>Article</code>, dessen ID der Parameter ID entspricht, zurück.
     *
     * @param id Die ID unter der ein <code>Article</code> in der Datenbank abgelegt
     *           ist.
     * @return Der <code>Article</code> der unter dieser ID gefunden wurde.
     * @throws org.springframework.dao.DataAccessException
     *          Diese wird geworfen falls ein Fehler beim Datenzugriff
     *          aufgetritt.
     * @see BaseDao#getArticleById(Long)
     */
    public Article getArticleById(Long id) throws DataAccessException {
        return (Article) getHibernateTemplate().load(Article.class, id);
    }

    /**
     * Speichert einen <code>Article</code> falls dieser in der Datenbank noch nicht vorhanden ist,
     * andernfalls wird dieser geändert.
     * <br/>
     * -> ausschlaggebend ist die ID
     * - nicht vorhanden bedeutet speichern
     * - vorhanden und existiert in der Datenbank bedeutet ändern.
     *
     * @param article Der <code>Article</code> der in der Datenbank gespeichert oder geändert werden
     *                soll.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             auftritt.
     * @see BaseDao#saveOrUpdateArticle(ecobill.module.base.domain.Article)
     */
    public void saveOrUpdateArticle(Article article) {
        getHibernateTemplate().saveOrUpdate(article);
    }

    /**
     * Gibt eine <code>List</code> mit allen <code>Article</code> die in der Datenbank verfügbar
     * sind zurück.
     *
     * @return Eine <code>List</code> mit allen <code>Article</code> in der Datenbank.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             auftritt.
     * @see ecobill.module.base.dao.BaseDao#getAllArticles()
     */
    public List getAllArticles() throws DataAccessException {
        return getHibernateTemplate().loadAll(Article.class);
    }
}
