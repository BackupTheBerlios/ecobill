package ecobill.module.base.service;

import ecobill.module.base.dao.BaseDao;
import ecobill.module.base.dao.exception.NoSuchArticleException;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.SystemLocale;
import ecobill.module.base.domain.Person;
import ecobill.core.system.service.Service;

import java.util.List;
import java.util.Locale;
import java.io.Serializable;

/**
 * Der <code>BaseService</code> ermöglicht es mit Hilfe von DataAccessObject komplexere Daten
 * von der Datenbank zu laden und auch wieder dorthin abzulegen.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 12:30:55
 *
 * @author Roman R&auml;dle
 * @version $Id: BaseService.java,v 1.13 2005/10/04 09:18:58 raedler Exp $
 * @since EcoBill 1.0
 */
public interface BaseService extends Service {

    /**
     * Gibt das <code>BaseDao</code>, das direkten Zugriff auf die Datenbank besitzt,
     * zurück.
     *
     * @return Das <code>BaseDao</code> mit direktem Zugriff auf die Datenbank.
     */
    public BaseDao getBaseDao();

    /**
     * Setzt das <code>BaseDao</code>, das einen direkten Zugriff zur Datenbank ermöglichen
     * soll.
     *
     * @param baseDao Das <code>BaseDao</code> mit direktem Zugriff auf die Datenbank.
     */
    public void setBaseDao(BaseDao baseDao);

    /**
     * @see BaseDao#load(Class, java.io.Serializable)
     */
    public Object load(Class clazz, Serializable id);

    /**
     * @see BaseDao#loadAll(Class)
     */
    public List loadAll(Class clazz);

    /**
     * @see BaseDao#evict(Object)
     */
    public void evict(Object entity);

    /**
     * @see BaseDao#saveOrUpdate(Object) 
     */
    public void saveOrUpdate(Object entity);

    /**
     * @see BaseDao#delete(Object)
     */
    public void delete(Object entity);

    /**
     * Lädt das <code>Object</code> mit der angegebenen Id aus der Datenbank
     * um es danach aus dieser zu löschen.
     *
     * @param clazz Die Klasse des <code>Object</code>.
     * @param id Die Id des <code>Object</code>.
     */
    public void delete(Class clazz, Serializable id);

    /**
     * @see BaseDao#getSystemLocaleBySystemLocaleKey(String)
     */
    public SystemLocale getSystemLocaleBySystemLocaleKey(String systemLocaleKey);

    /**
     * Gibt die <code>SystemLocale</code>, die der <code>Locale</code> am ähnlichsten ist,
     * zurück.
     *
     * @param locale Eine <code>Locale</code> um die <code>SystemLocale</code> zu erhalten.
     * @return Die <code>SystemLocale</code> die der <code>Locale</code> am ähnlichsten ist.
     */
    public SystemLocale getSystemLocaleByLocale(Locale locale);

    /**
     * @see ecobill.module.base.dao.BaseDao#getAllSystemLocales()
     */
    public List getAllSystemLocales();

    /**
     * @see ecobill.module.base.dao.BaseDao#getAllSystemUnits()
     */
    public List getAllSystemUnits();

    /**
     * @see BaseDao#getSystemUnitsByCategory(String) 
     */
    public List getSystemUnitsByCategory(String category);

    /**
     * @see ecobill.module.base.dao.BaseDao#getAllBusinessPartnerIds()
     */
    public List getAllBusinessPartnerIds();


    /**
     * @see BaseDao#getBusinessPartnerById(Long)
     */
    public BusinessPartner getBusinessPartnerById(Long id);

    /**
     * @see BaseDao#saveOrUpdateBusinessPartner(ecobill.module.base.domain.BusinessPartner)
     */
    public void saveOrUpdateBusinessPartner(BusinessPartner bp);

    /**
     * @see BaseDao#getArticleById(Long)
     */
    public Article getArticleById(Long id);

    /**
     * @see BaseDao#getAllReduplicatedArticleByDOId(Long)
     */
    public List getAllReduplicatedArticleByDOId(Long id);
    /**
     * @see BaseDao#getPersonById(Long)
     */
    public Person getPersonById(Long id);

    /**
     * @see BaseDao#getArticleByArticleNumber(String)
     */
    public Article getArticleByArticleNumber(String articleNumber) throws NoSuchArticleException;

    /**
     * @see BaseDao#saveOrUpdateArticle(ecobill.module.base.domain.Article)
     */
    public void saveOrUpdateArticle(Article article);

    /**
     * @see ecobill.module.base.dao.BaseDao#getAllArticles()
     */
    public List getAllArticles();

    /**
     * @see ecobill.module.base.dao.BaseDao#getAllDeliveryOrderByBPID(Long)
     */
    public List getAllDeliveryOrderByBPID(Long id);

    /**
     * @see ecobill.module.base.dao.BaseDao#getAllBillsByBPID(Long)
     */
    public List getAllBillsByBPID(Long id);

}
