package ecobill.module.base.dao;

import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.Person;
import ecobill.module.base.domain.BusinessPartner;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Das <code>BaseDao<code> stellt Methoden zur Verfügung um Daten aus einer Datenbank zu
 * laden und abzulegen.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 12:29:36
 *
 * @author Roman R&auml;dle
 * @version $Id: BaseDao.java,v 1.1 2005/07/28 21:03:47 raedler Exp $
 * @since EcoBill 1.0
 */
public interface BaseDao {

    /**
     * Gibt den <code>BusinessPartner</code>, dessen ID der Parameter ID entspricht, zurück.
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
     * andernfalls wird dieser geändert.
     * <br/>
     * -> ausschlaggebend ist die ID
     * - nicht vorhanden bedeutet speichern
     * - vorhanden und existiert in der Datenbank bedeutet ändern.
     *
     * @param bp Der <code>BusinessPartner</code> der in der Datenbank bespeichert oder geändert
     * werden soll.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             aufgetritt.
     */
    public void saveOrUpdateBusinessPartner(BusinessPartner bp) throws DataAccessException;

    /**
     * Gibt den <code>Article</code>, dessen ID der Parameter ID entspricht, zurück.
     *
     * @param id Die ID unter der ein <code>Article</code> in der Datenbank abgelegt
     *           ist.
     * @return Der <code>Article</code> der unter dieser ID gefunden wurde.
     * @throws DataAccessException Diese wird geworfen falls ein Fehler beim Datenzugriff
     *                             aufgetritt.
     */
    public Article getArticleById(Long id) throws DataAccessException;

    /**
     * Speichert einen <code>Article</code> falls dieser in der Datenbank noch nicht existiert,
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
     */
    public void saveOrUpdateArticle(Article article) throws DataAccessException;

    public List getAllArticles() throws DataAccessException;
}
