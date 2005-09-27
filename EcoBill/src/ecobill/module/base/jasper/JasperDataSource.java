package ecobill.module.base.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.Iterator;
import java.util.Collection;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * Die <code>JasperDataSource</code> ist eine für EcoBill allgemeine DataSource, die in der Lage
 * ist eine <code>Collection</code> aufzunehmen und diese im Report auszugeben.
 * <p/>
 * User: basti
 * Date: 26.09.2005
 * Time: 15:54:10
 *
 * @author Sebastian Gath
 * @version $Id: JasperDataSource.java,v 1.1 2005/09/27 12:48:20 raedler Exp $
 * @since EcoBill 1.0
 */
public class JasperDataSource implements JRDataSource {

    /**
     * Diese <code>Collection</code> beinhaltet die einzelnen Datensätze die im Report
     * angezeigt werden sollen.
     */
    private Collection<Object> datasets;

    /**
     * Der <code>Iterator</code> wird beim Erzeugen der DataSource oder beim neuen Setzen
     * der Datensätze aus der <code>Collection</code> geholt und ist somit in der Lage
     * über alle Elemente der <code>Collection</code> zu iterieren.
     */
    private Iterator iterator;

    /**
     * Ein Objekt aus der <code>Collection</code>, d.h. es entspricht einem Datensatz
     * des Reports.
     */
    private Object dataset;

    /**
     * Erzeugt eine neue <code>JasperDataSource</code>, die alle Datensätze in einer
     * <code>Collection</code> bereitstellt.
     *
     * @param datas Die Datensätze die im Report ausgegeben werden sollen.
     */
    public JasperDataSource(Collection<Object> datasets) {
        this.datasets = datasets;
        this.iterator = this.datasets.iterator();
    }

    /**
     * Gibt die <code>Collection</code>, die alle Datensätze des Reports
     * enthält, zurück.
     *
     * @return Die <code>Collection</code> die alle Datensätze enthält.
     */
    public Collection<Object> getDatasets() {
        return datasets;
    }

    /**
     * Setzt eine <code>Collection</code>, die alle Datensätze enthält, die im
     * Report ausgegeben werden sollen.
     *
     * @param datasets Die <code>Collection</code> die alle Datensätze enthält.
     */
    public void setDatasets(Collection<Object> datasets) {
        this.datasets = datasets;
        this.iterator = this.datasets.iterator();
    }

    /**
     * @see JRDataSource#next()
     */
    public boolean next() throws JRException {
        if (iterator.hasNext()) {
            dataset = iterator.next();
            return true;
        }
        return false;
    }

    /**
     * @see JRDataSource#getFieldValue(JRField)
     */
    public Object getFieldValue(JRField jrField) throws JRException {

        // Der Name des Feldes im Report.
        String fieldName = jrField.getName();

        try {
            return Ognl.getValue(fieldName, dataset);
        }
        catch (OgnlException e) {
            e.printStackTrace();
            return "Error!!";
        }
    }
}
