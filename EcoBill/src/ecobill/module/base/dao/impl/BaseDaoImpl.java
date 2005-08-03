package ecobill.module.base.dao.impl;

import ecobill.module.base.dao.BaseDao;
import ecobill.module.base.dao.exception.NoSuchSystemLocaleException;
import ecobill.module.base.dao.exception.NonUniqueHibernateResultException;
import ecobill.module.base.dao.exception.NoSuchArticleException;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.SystemLocale;
import ecobill.module.base.domain.SystemUnit;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Das <code>BaseDaoImpl</code> ist eine Implementation des Interfaces <code>BaseDao</code>.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 12:29:43
 *
 * @author Roman R&auml;dle
 * @version $Id: BaseDaoImpl.java,v 1.4 2005/08/03 13:06:09 raedler Exp $
 * @see BaseDao
 * @since EcoBill 1.0
 */
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

    /**
     * Gibt die <code>SystemLocale</code>, deren localeKey des Parameter localeKey entspricht,
     * zurück.
     *
     * @param systemLocaleKey Der Schlüssel unter dem eine <code>SystemLocale</code> gefunden werden
     *                        soll.
     * @return Die <code>SystemLocale</code> die unter diesem localeKey gefunden wurde.
     * @throws DataAccessException         Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                                     aufgetritt.
     * @throws NoSuchSystemLocaleException Diese wird geworfen falls keine <code>SystemLocale</code>
     *                                     unter diesem Schlüssel gefunden wurde.
     * @throws NonUniqueHibernateResultException
     *                                     Diese wird geworfen falls mehr als eine <code>SystemLocale</code>
     *                                     zurückgeliefert wird und das Ergebnis somit nicht eindeutig ist.
     * @see NoSuchSystemLocaleException
     * @see NonUniqueHibernateResultException
     * @see BaseDao#getSystemLocaleBySystemLocaleKey(String)
     */
    public SystemLocale getSystemLocaleBySystemLocaleKey(String systemLocaleKey) throws DataAccessException, NoSuchSystemLocaleException, NonUniqueHibernateResultException {
        List systemLocaleList = getHibernateTemplate().find("from " + SystemLocale.class.getName() + " as systemLocale where systemLocale.key = ?", new Object[]{systemLocaleKey});

        if (systemLocaleList.size() < 1) throw new NoSuchSystemLocaleException("Es wurde keine SystemLocale mit dem Schlüssel [key = " + systemLocaleKey + "] gefunden.");
        if (systemLocaleList.size() > 1) throw new NonUniqueHibernateResultException("Es wurde keine eideutige SystemLocale mit dem Schlüssel [key = " + systemLocaleKey + "] gefunden. Bitte überprüfen Sie Ihren Datenbankbestand.");

        return (SystemLocale) systemLocaleList.get(0);
    }

    /**
     * Gibt eine <code>List</code> mit allen <code>SystemLocale</code> die in der Datenbank verfügbar
     * sind zurück.
     *
     * @return Eine <code>List</code> mit allen <code>SystemLocale</code> in der Datenbank.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             auftritt.
     * @see ecobill.module.base.dao.BaseDao#getAllSystemLocales()
     */
    public List getAllSystemLocales() throws DataAccessException {
        return getHibernateTemplate().loadAll(SystemLocale.class);
    }

    /**
     * Gibt eine <code>List</code> mit allen <code>SystemUnit</code> die in der Datenbank verfügbar
     * sind zurück.
     *
     * @return Eine <code>List</code> mit allen <code>SystemUnit</code> in der Datenbank.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             auftritt.
     * @see ecobill.module.base.dao.BaseDao#getAllSystemUnits()
     */
    public List getAllSystemUnits() throws DataAccessException {
        return getHibernateTemplate().loadAll(SystemUnit.class);
    }

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
     * Gibt den <code>Article</code>, dessen Artikelnummer dem <code>String</code> articleNumber
     * entspricht zurück.
     *
     * @param articleNumber Die eindeutitge Artikelnummer unter der ein Artikel in der
     *                      Datenbank abgelegt ist.
     * @return Der <code>Article</code> der unter dieser Artikelnummer gefunden wurde.
     * @throws DataAccessException org.springframework.dao.DataAccessException
     *                             Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             aufgetritt.
     * @throws NoSuchArticleException Diese wird geworfen falls kein <code>Article</code>
     *                                     unter dieser articleNumber gefunden wurde.
     * @throws NonUniqueHibernateResultException
     *                                     Diese wird geworfen falls mehr als ein <code>Article</code>
     *                                     zurückgeliefert wird und das Ergebnis somit nicht eindeutig ist.
     * @see NoSuchArticleException
     * @see NonUniqueHibernateResultException
     * @see BaseDao#getArticleByArticleNumber(String)
     */
    public Article getArticleByArticleNumber(String articleNumber) throws DataAccessException, NoSuchArticleException, NonUniqueHibernateResultException {
        List articleList = getHibernateTemplate().find("from " + Article.class.getName() + " as article where article.articleNumber = ?", new Object[]{articleNumber});

        if (articleList.size() < 1) throw new NoSuchArticleException("Es wurde kein Article mit der Artikelnummer [articleNumber = " + articleNumber + "] gefunden.");
        if (articleList.size() > 1) throw new NonUniqueHibernateResultException("Es wurde kein eideutiger Article mit der Artikelnummer [articleNumber = " + articleNumber + "] gefunden. Bitte überprüfen Sie Ihren Datenbankbestand.");

        return (Article) articleList.get(0);
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
