package ecobill.module.base.dao;

import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.Person;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.SystemLocale;
import ecobill.module.base.dao.exception.NoSuchSystemLocaleException;
import ecobill.module.base.dao.exception.NonUniqueHibernateResultException;
import ecobill.module.base.dao.exception.NoSuchArticleException;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.io.Serializable;

/**
 * Das <code>BaseDao<code> stellt Methoden zur Verf�gung um Daten aus einer Datenbank zu
 * laden und abzulegen.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 12:29:36
 *
 * @author Roman R&auml;dle
 * @version $Id: BaseDao.java,v 1.8 2005/09/28 15:57:15 raedler Exp $
 * @since EcoBill 1.0
 */
public interface BaseDao {

    /**
     * L�dt das <code>Object</code> mit der entsprechenden id von der Datenbank.
     *
     * @param clazz Die Klasse des <code>Object</code>.
     * @param id    Die id des <code>Object</code>.
     * @return Gibt das gefundene <code>Object</code> zur�ck, ansonsten eine neu
     *         erzeugte Instanz der Klasse.
     */
    public Object load(Class clazz, Serializable id);

    /**
     * L�dt alle Objekte der persistenten Klasse und gibt diese in einer <code>List</code>
     * zur�ck.
     *
     * @param clazz Die Klasse zu der diese Objekte geh�ren.
     * @return Die <code>List</code> die alle persistenten Objekte beinhaltet.
     */
    public List loadAll(Class clazz);

    /**
     * L�st eine Entit�t von der aktuellen <code>Session</code>.
     *
     * @param entity Die zu l�sende Entit�t.
     */
    public void evict(Object entity);

    /**
     * Gibt die <code>SystemLocale</code>, deren localeKey des Parameter localeKey entspricht,
     * zur�ck.
     *
     * @param systemLocaleKey Der Schl�ssel unter dem eine <code>SystemLocale</code> gefunden werden
     *                        soll.
     * @return Die <code>SystemLocale</code> die unter diesem localeKey gefunden wurde.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             aufgetritt.
     * @throws ecobill.module.base.dao.exception.NoSuchSystemLocaleException
     *                             Diese wird geworfen falls keine <code>SystemLocale</code>
     *                             unter diesem Schl�ssel gefunden wurde.
     * @throws ecobill.module.base.dao.exception.NonUniqueHibernateResultException
     *                             Diese wird geworfen falls mehr als eine <code>SystemLocale</code>
     *                             zur�ckgeliefert wird und das Ergebnis somit nicht eindeutig ist.
     * @see NoSuchArticleException
     * @see NonUniqueHibernateResultException
     */
    public SystemLocale getSystemLocaleBySystemLocaleKey(String systemLocaleKey) throws DataAccessException, NoSuchSystemLocaleException, NonUniqueHibernateResultException;

    /**
     * Gibt eine <code>List</code> mit allen <code>SystemLocale</code> die in der Datenbank verf�gbar
     * sind zur�ck.
     *
     * @return Eine <code>List</code> mit allen <code>SystemLocale</code> in der Datenbank.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             auftritt.
     */
    public List getAllSystemLocales() throws DataAccessException;

    /**
     * Gibt eine <code>List</code> mit allen <code>Businesspartnerids</code> die in der Datenbank verf�gbar
     * sind zur�ck.
     *
     * @return Eine <code>List</code> mit allen <code>Businesspartnerids</code> in der Datenbank.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             auftritt.
     */
    public List getAllBusinessPartnerIds() throws DataAccessException;

    /**
     * Gibt eine <code>List</code> mit allen <code>SystemUnit</code> die in der Datenbank verf�gbar
     * sind zur�ck.
     *
     * @return Eine <code>List</code> mit allen <code>SystemUnit</code> in der Datenbank.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             auftritt.
     */
    public List getAllSystemUnits() throws DataAccessException;


    /**
     * Gibt den <code>BusinessPartner</code>, dessen ID der Parameter ID entspricht, zur�ck.
     *
     * @param id Die ID unter der ein <code>BusinessPartner</code> in der Datenbank abegelegt
     *           ist.
     * @return Der <code>BusinessPartner</code> der unter dieser ID gefunden wurde.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             aufgetritt.
     */
    public BusinessPartner getBusinessPartnerById(Long id) throws DataAccessException;

    /**
     * Speichert den <code>BusinessPartner</code> falls dieser in der Datenbank noch nicht existiert
     * andernfalls wird dieser ge�ndert.
     * <br/>
     * -> ausschlaggebend ist die ID
     * - nicht vorhanden bedeutet speichern
     * - vorhanden und existiert in der Datenbank bedeutet �ndern.
     *
     * @param bp Der <code>BusinessPartner</code> der in der Datenbank bespeichert oder ge�ndert
     *           werden soll.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             aufgetritt.
     */
    public void saveOrUpdateBusinessPartner(BusinessPartner bp) throws DataAccessException;

    /**
     * Gibt den <code>ReduolicatedArticle</code>, dessen ID der Parameter ID entspricht, zur�ck.
     *
     * @param id Die ID unter der ein <code>Article</code> in der Datenbank abgelegt
     *           ist.
     * @return Eine <code>List</code> der unter dieser DeliverOrderID gefunden Artikel wurde.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             aufgetritt.
     */
    public List getAllReduplicatedArticleByDOId(Long id) throws DataAccessException;

    /**
     * Gibt den <code>Article</code>, dessen ID der Parameter ID entspricht, zur�ck.
     *
     * @param id Die ID unter der ein <code>ReduplicateArticle</code> in der Datenbank abgelegt
     *           ist.
     * @return Der <code>ReduplicateArticle</code> der unter dieser ID gefunden wurde.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             aufgetritt.
     */
    public Article getArticleById(Long id) throws DataAccessException;


    /**
     * Gibt den <code>Person</code>, dessen ID der Parameter ID entspricht, zur�ck.
     *
     * @param id Die ID unter der ein <code>Person</code> in der Datenbank abgelegt
     *           ist.
     * @return Der <code>Person</code> der unter dieser ID gefunden wurde.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             aufgetritt.
     */
    public Person getPersonById(Long id) throws DataAccessException;

    /**
     * Gibt den <code>Article</code>, dessen Artikelnummer dem <code>String</code> articleNumber
     * entspricht zur�ck.
     *
     * @param articleNumber Die eindeutitge Artikelnummer unter der ein Artikel in der
     *                      Datenbank abgelegt ist.
     * @return Der <code>Article</code> der unter dieser Artikelnummer gefunden wurde.
     * @throws DataAccessException    org.springframework.dao.DataAccessException
     *                                Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                                aufgetritt.
     * @throws NoSuchArticleException Diese wird geworfen falls kein <code>Article</code>
     *                                unter dieser articleNumber gefunden wurde.
     * @throws NonUniqueHibernateResultException
     *                                Diese wird geworfen falls mehr als ein <code>Article</code>
     *                                zur�ckgeliefert wird und das Ergebnis somit nicht eindeutig ist.
     * @see NoSuchArticleException
     * @see NonUniqueHibernateResultException
     */
    public Article getArticleByArticleNumber(String articleNumber) throws DataAccessException, NoSuchArticleException, NonUniqueHibernateResultException;

    /**
     * Speichert einen <code>Article</code> falls dieser in der Datenbank noch nicht existiert,
     * andernfalls wird dieser ge�ndert.
     * <br/>
     * -> ausschlaggebend ist die ID
     * - nicht vorhanden bedeutet speichern
     * - vorhanden und existiert in der Datenbank bedeutet �ndern.
     *
     * @param article Der <code>Article</code> der in der Datenbank gespeichert oder ge�ndert werden
     *                soll.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             auftritt.
     */
    public void saveOrUpdateArticle(Article article) throws DataAccessException;

    /**
     * Gibt eine <code>List</code> mit allen <code>Article</code> die in der Datenbank verf�gbar
     * sind zur�ck.
     *
     * @return Eine <code>List</code> mit allen <code>Article</code> in der Datenbank.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             auftritt.
     */
    public List getAllArticles() throws DataAccessException;

    /**
     * Gibt eine <code>List</code> mit allen <code>DerliveryOrder</code> die in der Datenbank verf�gbar
     * sind zur�ck.
     *
     * @return Eine <code>List</code> mit allen <code>DerliveryOrder</code> in der Datenbank.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             auftritt.
     */
    public List getAllDeliveryOrderByBPID(Long id) throws DataAccessException;

    /**
     * Gibt eine <code>List</code> mit allen <code>Bills</code> die in der Datenbank verf�gbar
     * sind zur�ck.
     *
     * @return Eine <code>List</code> mit allen <code>Bills</code> in der Datenbank.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             auftritt.
     */
    public List getAllBillsByBPID(Long id) throws DataAccessException;
}
