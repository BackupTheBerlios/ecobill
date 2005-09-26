package ecobill.module.base.service.impl;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.dao.BaseDao;
import ecobill.module.base.dao.exception.NoSuchSystemLocaleException;
import ecobill.module.base.dao.exception.NoSuchArticleException;
import ecobill.module.base.domain.*;
import ecobill.util.LocalizerUtils;
import ecobill.util.exception.LocalizerException;

import java.util.List;
import java.util.Locale;

import org.springframework.dao.DataAccessException;

/**
 * Das <code>BaseServiceImpl</code> ist eine Implementation des Interfaces <code>BaseService</code>.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 12:31:05
 *
 * @author Roman R&auml;dle
 * @version $Id: BaseServiceImpl.java,v 1.8 2005/09/26 15:27:40 gath Exp $
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
     * @see BaseService#getSystemLocaleBySystemLocaleKey(String)
     */
    public SystemLocale getSystemLocaleBySystemLocaleKey(String systemLocaleKey) {
        return baseDao.getSystemLocaleBySystemLocaleKey(systemLocaleKey);
    }

    /**
     * Gibt die <code>SystemLocale</code>, die der <code>Locale</code> am ähnlichsten ist,
     * zurück.
     *
     * @param locale Eine <code>Locale</code> um die <code>SystemLocale</code> zu erhalten.
     * @return Die <code>SystemLocale</code> die der <code>Locale</code> am ähnlichsten ist.
     * @see BaseService#getSystemLocaleByLocale(java.util.Locale)
     */
    public SystemLocale getSystemLocaleByLocale(Locale locale) throws NoSuchSystemLocaleException {
        List systemLocaleList = getAllSystemLocales();

        /*
         * Es wird versucht aus der <code>List</code> mit <code>SystemLocale</code> die <code>SystemLocale</code>
         * herauszufiltern, die der <code>Locale</code> am ähnlichsten ist.
         * -> Ähnlich bedeutet, dass die Priorität in <code>LocalizerUtils</code> gegen 1 gehen muss.
         */
        Object o;
        try {
            o = LocalizerUtils.getLocalizedObject(systemLocaleList, locale);
        }
        catch (LocalizerException e) {
            throw new NoSuchSystemLocaleException("Es wurde keine SystemLocale gefunden die der Locale [language = " + locale.getLanguage() + " | country = " + locale.getCountry() + " | variant = " + locale.getVariant() + "] ähnelt.");
        }

        /*
         * Erneuter Test, um sicher zu gehen, dass das zurückgelieferte <code>Object</code> auch wirklich
         * eine <code>SystemLocale</code> ist. Falls es sich nicht um eine <code>SystemLocale</code>
         * gehandelt hat wird abschließend eine erneute <code>NoSuchSystemLocaleException</code> geworfen.
         */
        SystemLocale systemLocale = null;
        if (o instanceof SystemLocale) {
            systemLocale = (SystemLocale) o;
        }

        if (systemLocale == null) {
            throw new NoSuchSystemLocaleException("Es wurde keine SystemLocale gefunden die der Locale [language = " + locale.getLanguage() + " | country = " + locale.getCountry() + " | variant = " + locale.getVariant() + "] ähnelt.");
        }

        return systemLocale;
    }

    /**
     * @see BaseService#getAllSystemLocales()
     */
    public List getAllSystemLocales() {
        return baseDao.getAllSystemLocales();
    }


    /**
     * @see ecobill.module.base.service.BaseService#getAllBusinessPartnerIds()
     */
    public List getAllBusinessPartnerIds() {
        return baseDao.getAllBusinessPartnerIds();
    }

    /**
     * @see ecobill.module.base.service.BaseService#getAllSystemUnit()
     */
    public List getAllSystemUnits() {
        return baseDao.getAllSystemUnits();
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

        System.out.println("BusinessPartner: " + bp.getId());

        /*
         * @todo Dokumentation ändern!!!
         * Bei einem neuen BusinessPartner (ID ist nicht gesetzt bzw "-1") wird überprüft ob
         * sich schon ein BusinessPartner mit dieser BusinessPartnernummer in der Datenbank befindet.
         * Sollte schon ein BusinessPartner vorhanden sein werden dessen Daten mit den Daten
         * des Parameter BusinessPartner überschrieben.
         */
        //if (article.getId() != null) {
            BusinessPartner savedBusinessPartner = null;
            try {
                savedBusinessPartner = baseDao.getBusinessPartnerById(bp.getId());
            }
            catch (DataAccessException e) {
                // Unternehme nichts, da savedArticle ja dann sowieso null ist.
            }

            Person savedPerson = null;
            try {
                savedPerson = baseDao.getPersonById(bp.getPerson().getId());
        }
        catch (DataAccessException e) {
            // Unternehme nichts, da savedPerson ja dann sowieso null ist.
        }


            /*
             * Hier werden die Werte des Parameter <code>Article</code> in den vorhandenen
             * Artikel in der Datenbank gesetzt und dieser dann wieder gespeichert.
             * Hier wird das Problem mit zwei Objekten und der selben ID umgangen.
             */
            if (savedBusinessPartner != null && savedPerson != null) {
                savedBusinessPartner.setCompanyName(bp.getCompanyName());
                savedBusinessPartner.setCompanyTitleKey(bp.getCompanyTitleKey());
                savedPerson.setTitleKey(bp.getPerson().getTitleKey());
                savedPerson.setAcademicTitleKey(bp.getPerson().getAcademicTitleKey());
                savedPerson.setFirstname(bp.getPerson().getFirstname());
                savedPerson.setLastname(bp.getPerson().getLastname());
                savedPerson.setPhone(bp.getPerson().getPhone());
                savedPerson.setEmail(bp.getPerson().getEmail());

            }
        //}

        baseDao.saveOrUpdateBusinessPartner(savedBusinessPartner);
    }

    /**
     * @see BaseService#getAllReduplicatedArticleByDOId(Long)
     */
    public List getAllReduplicatedArticleByDOId(Long id) {
        return baseDao.getAllReduplicatedArticleByDOId(id);
    }

    /**
     * @see BaseService#getArticleById(Long)
     */
    public Article getArticleById(Long id) {
        return baseDao.getArticleById(id);
    }
    /**
     * @see BaseService#getArticleByArticleNumber(String)
     */
    public Article getArticleByArticleNumber(String articleNumber) {
        return baseDao.getArticleByArticleNumber(articleNumber);
    }


    /**
     * @see BaseService#getPersonById(Long)
     */
    public Person getPersonById(Long id) {
        return baseDao.getPersonById(id);
    }


    /**
     * @see BaseService#saveOrUpdateArticle(ecobill.module.base.domain.Article)
     */
    public void saveOrUpdateArticle(Article article) {

        System.out.println("ARTICLE: " + article.getId());

        /*
         * @todo Dokumentation ändern!!!
         * Bei einem neuen Artikel (ID ist nicht gesetzt bzw "-1") wird überprüft ob
         * sich schon ein Artikel mit dieser Artikelnummer in der Datenbank befindet.
         * Sollte schon ein Artikel vorhanden sein werden dessen Daten mit den Daten
         * des Parameter Artikel überschrieben.
         */
        //if (article.getId() != null) {
            Article savedArticle = null;
            try {
                savedArticle = baseDao.getArticleByArticleNumber(article.getArticleNumber());
            }
            catch (NoSuchArticleException nsae) {
                // Unternehme nichts, da savedArticle ja dann sowieso null ist.
            }

            /*
             * Hier werden die Werte des Parameter <code>Article</code> in den vorhandenen
             * Artikel in der Datenbank gesetzt und dieser dann wieder gespeichert.
             * Hier wird das Problem mit zwei Objekten und der selben ID umgangen.
             */
            if (savedArticle != null) {
                savedArticle.setArticleNumber(article.getArticleNumber());
                savedArticle.setSystemUnit(article.getSystemUnit());
                savedArticle.setPrice(article.getPrice());
                savedArticle.setInStock(article.getInStock());
                savedArticle.setBundleSystemUnit(article.getBundleSystemUnit());
                savedArticle.setBundleCapacity(article.getBundleCapacity());

                // @todo Evtl muss man sich hier um die restlichen (schon vorhandenen) Artikelbeschreibungen kümmern.
                savedArticle.setDescriptions(article.getDescriptions());
            }
        //}

        baseDao.saveOrUpdateArticle(savedArticle);
    }

    /**
     * @see ecobill.module.base.dao.BaseDao#getAllArticles()
     */
    public List getAllArticles() {
        return baseDao.getAllArticles();
    }

    /**
     * @see ecobill.module.base.dao.BaseDao#getAllDeliveryOrderByBPID(Long)
     */
    public List getAllDeliveryOrderByBPID(Long id) {
        return baseDao.getAllDeliveryOrderByBPID(id);
    }


}
