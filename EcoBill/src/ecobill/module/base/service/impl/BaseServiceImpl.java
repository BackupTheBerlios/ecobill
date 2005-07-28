package ecobill.module.base.service.impl;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.dao.BaseDao;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.Person;
import ecobill.module.base.domain.BusinessPartner;

import java.util.List;

/**
 * Das <code>BaseServiceImpl</code> ist eine Implementation des Interfaces <code>BaseService</code>.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 12:31:05
 *
 * @author Roman R&auml;dle
 * @version $Id: BaseServiceImpl.java,v 1.1 2005/07/28 21:03:52 raedler Exp $
 * @see BaseService
 * @since EcoBill 1.0
 */
public class BaseServiceImpl implements BaseService {

    /**
     * Das <code>BaseDao</code> ermöglicht den Zugriff auf die "einfachen/flachen" Objekte die
     * in der Datenbank abgelegt sind.
     */
    private BaseDao baseDao;

    /**
     * Gibt das <code>BaseDao</code>, das direkten Zugriff auf die Datenbank besitzt,
     * zurück.
     *
     * @return Das <code>BaseDao</code> mit direktem Zugriff auf die Datenbank.
     * @see ecobill.module.base.service.BaseService#getBaseDao()
     */
    public BaseDao getBaseDao() {
        return baseDao;
    }

    /**
     * Setzt das <code>BaseDao</code>, das einen direkten Zugriff zur Datenbank ermöglichen
     * soll.
     *
     * @param baseDao Das <code>BaseDao</code> mit direktem Zugriff auf die Datenbank.
     * @see BaseService#setBaseDao(ecobill.module.base.dao.BaseDao)
     */
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    /**
     * @see BaseService#getBusinessPartnerById(Long)
     */
    public BusinessPartner getBusinessPartnerById(Long id) {
        return baseDao.getBusinessPartnerById(id);
    }

    /**
     * @see BaseService#saveOrUpdateBusinessPartner(ecobill.module.base.domain.BusinessPartner)
     */
    public void saveOrUpdateBusinessPartner(BusinessPartner bp) {
        baseDao.saveOrUpdateBusinessPartner(bp);
    }

    /**
     * @see BaseService#getArticleById(Long)
     */
    public Article getArticleById(Long id) {
        return baseDao.getArticleById(id);
    }

    /**
     * @see BaseService#saveOrUpdateArticle(ecobill.module.base.domain.Article)
     */
    public void saveOrUpdateArticle(Article article) {
        baseDao.saveOrUpdateArticle(article);
    }

    public List getAllArticles() {
        return baseDao.getAllArticles();
    }
}
