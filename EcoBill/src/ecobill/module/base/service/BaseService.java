package ecobill.module.base.service;

import ecobill.module.base.dao.BaseDao;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.Person;
import ecobill.module.base.domain.BusinessPartner;

import java.util.List;

/**
 * Der <code>BaseService</code> ermöglicht es mit Hilfe von DataAccessObject komplexere Daten
 * von der Datenbank zu laden und auch wieder dorthin abzulegen.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 12:30:55
 *
 * @author Roman R&auml;dle
 * @version $Id: BaseService.java,v 1.2 2005/07/29 20:59:07 raedler Exp $
 * @since EcoBill 1.0
 */
public interface BaseService {

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
     * @see BaseDao#saveOrUpdateArticle(ecobill.module.base.domain.Article)
     */
    public void saveOrUpdateArticle(Article article);

    /**
     * @see ecobill.module.base.dao.BaseDao#getAllArticles() 
     */
    public List getAllArticles();
}
