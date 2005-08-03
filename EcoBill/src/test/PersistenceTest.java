package test;

import junit.framework.TestCase;
import junit.framework.Assert;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import ecobill.module.base.domain.*;
import ecobill.module.base.service.BaseService;
import ecobill.util.exception.LocalizerException;

/**
 * Der <code>PersistenceTest</code> überprüft mit ausgewählten Tests das System, Hibernate und die
 * Datenbank auf Korrektheit.
 * <p/>
 * User: rro
 * Date: 14.07.2005
 * Time: 23:45:45
 *
 * @author Roman R&auml;dle
 * @version $Id: PersistenceTest.java,v 1.2 2005/08/03 13:06:09 raedler Exp $
 * @since EcoBill 1.0
 */
public class PersistenceTest extends TestCase {

    /**
     * Der <code>ApplicationContext</code> der beim Hochfahren der Anwendung erzeugt wird.
     */
    protected ApplicationContext ac = null;

    /**
     * Der <code>BaseService</code> ist die Business Logik.
     */
    protected BaseService baseService = null;

    /**
     * Wird vor allen Tests ausgeführt.
     *
     * @throws Exception Diese wird bei einem Fehler der Ausführung des Testes
     *                   geworfen.
     */
    protected void setUp() throws Exception {
        super.setUp();

        /*
         * Hole den Application Context mit all seinen erzeugten Beans.
         */
        ac = new ClassPathXmlApplicationContext("ecobill/config/applicationContext.xml");

        baseService = (BaseService) ac.getBean("baseService");
    }

    /**
     * Wird nach allen Tests ausgeführt.
     *
     * @throws Exception Diese wird bei einem Fehler der Ausführung des Testes
     *                   geworfen.
     */
    protected void tearDown() throws Exception {
        resetAll();
    }

    /**
     * Setzt alle Objekte wieder auf ihren Urzustand.
     */
    private void resetAll() {
        try {
            resetArticle();
            resetBusinessPartner();
        }
        catch (LocalizerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Testet die Artikel Persistenz.
     * <br/>
     * Hier wird ein Artikel geladen, verglichen, gändert, gespeichert,
     * wieder verglichen.
     *
     * @throws ecobill.util.exception.LocalizerException Diese wird geworfen falls das Localizable Objekt nicht
     *                            gefiltert werden kann.
     */
    public void testArticle() throws LocalizerException {

        /*
         * Der Artikel mit der ID 1 geladen.
         */
        Article article = baseService.getArticleById(1L);

        /*
         * Der erste Vergleich.
         */
        Assert.assertNotNull(article);
        Assert.assertTrue("bag".equals(article.getSystemUnit().getKey()));
        Assert.assertTrue(article.getPrice() == 24.95);
        Assert.assertTrue(article.getInStock() == 43.);
        Assert.assertTrue(article.getBundleCapacity() == 25.);
        Assert.assertTrue("weight".equals(article.getBundleSystemUnit().getKey()));
        Assert.assertTrue("Fliesurit Flex (deutsch)".equals(article.getLocalizedDescription()));

        /*
         * Ändern des Artikels.
         */
        article.setInStock(40);
        article.getLocalizedArticleDescription().setDescription("Eine neue Beschreibung");

        /*
         * Speichern des geänderten Artikels.
         */
        baseService.saveOrUpdateArticle(article);

        /*
         * Laden des veränderten und gespeicherten Artikels.
         */
        Article savedArticle = baseService.getArticleById(1L);

        /*
         * Vergleichen des veränderten und geladenen Artikels.
         */
        Assert.assertEquals(article, savedArticle);
    }

    /**
     * Setzt den veränderten Artikel wieder auf den Urzustand.
     *
     * @throws ecobill.util.exception.LocalizerException Diese wird geworfen falls das Localizable Objekt nicht
     *                            gefiltert werden kann.
     */
    private void resetArticle() throws LocalizerException {

        /*
        * Der Artikel mit der ID 1 geladen.
        */
        Article article = baseService.getArticleById(1L);

        /*
         * Zurücksetzen der Werte.
         */
        article.setInStock(43);
        article.getLocalizedArticleDescription().setDescription("Fliesurit Flex (deutsch)");

        /*
        * Speichern des geänderten Artikels.
        */
        baseService.saveOrUpdateArticle(article);
    }

    /**
     * Testet die Geschäftspartner Persistenz.
     * <br/>
     * Hier wird ein Geschäftspartner geladen, verglichen, gändert, gespeichert,
     * wieder verglichen.
     */
    public void testBusinessPartner() {

        /*
         * Der Geschäftspartner mit der ID 1 wird geladen.
         */
        BusinessPartner bp = baseService.getBusinessPartnerById(1L);

        /*
         * Der erste Vergleich.
         */
        Assert.assertNotNull(bp);
        Assert.assertTrue("firm".equals(bp.getCompanyTitleKey()));
        Assert.assertTrue("JF 08/15".equals(bp.getCompanyName()));

        Person person = bp.getPerson();
        Assert.assertNotNull(person);
        Assert.assertTrue("mr".equals(person.getTitleKey()));
        Assert.assertNull(person.getAcademicTitleKey());
        Assert.assertTrue("Sebastian".equals(person.getFirstname()));
        Assert.assertTrue("Gath".equals(person.getLastname()));
        Assert.assertNull(person.getPhone());
        Assert.assertNull(person.getFax());
        Assert.assertTrue("sgath@gmx.de".equals(person.getEmail()));

        Address bpAddress = bp.getAddress();
        Address personAddress = person.getAddress();

        Assert.assertEquals(bpAddress, personAddress);
        Assert.assertTrue("Obere Laube 51".equals(personAddress.getStreet()));
        Assert.assertTrue("78462".equals(personAddress.getZipCode()));
        Assert.assertTrue("Konstanz".equals(personAddress.getCity()));
        Assert.assertTrue("Baden-Württemberg".equals(personAddress.getCounty()));
        Assert.assertTrue("Deutschland".equals(personAddress.getCountry()));

        Banking banking = bp.getBanking();
        Assert.assertNotNull(banking);
        Assert.assertTrue("Sparkasse Bodensee".equals(banking.getBankEstablishment()));
        Assert.assertTrue("123456".equals(banking.getAccountNumber()));
        Assert.assertTrue("65351050".equals(banking.getBankIdentificationNumber()));

        /*
         * Ändern des Gesschäftspartners.
         */
        bp.setCompanyName("JF 90-60-90");
        bp.getPerson().setFirstname("Basti");
        bp.getAddress().setCity("Mengen");
        bp.getBanking().setAccountNumber("654321");

        /*
         * Speichern des geänderten Gschäftspartners.
         */
        baseService.saveOrUpdateBusinessPartner(bp);

        /*
         * Laden des veränderten und gespeicherten Geschäftspartners.
         */
        BusinessPartner savedBp = baseService.getBusinessPartnerById(1L);

        /*
         * Vergleichen des veränderten und geladenen Geschäftspartners.
         */
        // @todo Sobald es einen HibernateSessionInterceptor gibt.
        //Assert.assertTrue(bp.equals(savedBp));
    }

    /**
     * Setzt den veränderten Geschäftspartner wieder auf den Urzustand.
     */
    private void resetBusinessPartner() {

        /*
         * Der Geschäftspartner mit der ID 1 geladen.
         */
        BusinessPartner bp = baseService.getBusinessPartnerById(1L);

        /*
         * Zurücksetzen der Werte.
         */
        bp.setCompanyName("JF 08/15");
        bp.getPerson().setFirstname("Sebastian");
        bp.getAddress().setCity("Konstanz");
        bp.getBanking().setAccountNumber("123456");

        /*
        * Speichern des geänderten Artikels.
        */
        baseService.saveOrUpdateBusinessPartner(bp);
    }
}
