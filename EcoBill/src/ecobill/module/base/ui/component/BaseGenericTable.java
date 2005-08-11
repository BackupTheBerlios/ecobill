package ecobill.module.base.ui.article;

import ecobill.core.ui.component.GenericTable;
import ecobill.core.system.Constants;
import ecobill.core.system.WorkArea;
import ecobill.core.system.service.Service;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.SystemUnit;
import ecobill.module.base.domain.ArticleDescription;
import ecobill.module.base.domain.SystemLocale;
import ecobill.module.base.service.BaseService;
import ecobill.util.exception.LocalizerException;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.util.Vector;
import java.util.Collection;

// @todo document me!

/**
 * BaseGenericTable.
 * <p/>
 * User: rro
 * Date: 09.08.2005
 * Time: 19:38:34
 *
 * @author Roman R&auml;dle
 * @version $Id: BaseGenericTable.java,v 1.1 2005/08/11 18:10:31 raedler Exp $
 * @since EcoBill 1.0
 */
public abstract class BaseGenericTable extends GenericTable {

    /**
     * Der <code>BaseService</code> ist die Business Logik.
     */
    private BaseService baseService;

    /**
     * Erzeugen einer neuen Artikeltabelle mit einer festen Tabellen
     * Header Reihenfolge und einem view <code>Object</code> das die
     * initialen Daten der Tabelle hat.
     *
     * @param keyHeaderOrder Die Reihenfolge der Tabellenheader.
     * @param view           Die Daten die in der Tabelle angezeigt werden sollen.
     * @param service        Dieser <code>Service</code> wird evtl.
     *                       benötigt um Daten von der Datenbank zu laden
     *                       bzw. wieder dort abzulegen.
     * @see GenericTable#GenericTable(String[], Object)
     */
    public BaseGenericTable(String[] keyHeaderOrder, Object view, Service service) {
        super(keyHeaderOrder, view, null, service);
    }

    /**
     * In dieser Methode wird der Service auf den <code>BaseService</code> gecastet.
     *
     * @see ecobill.core.ui.component.GenericTable#initServices()
     */
    protected void initServices() {
        if (getService() instanceof BaseService) {
            baseService = (BaseService) getService();
        }
    }

    /**
     * Gibt den gecasteten <code>BaseService</code> zurück. Dieser
     * <code>Service</code> wird im gesamten Base Modul verwendet.
     *
     * @return Der <code>BaseService</code> ist die Business Logik
     *         des Basis Modules.
     */
    protected BaseService getBaseService() {
        return baseService;
    }
}
