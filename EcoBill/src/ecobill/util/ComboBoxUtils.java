package ecobill.util;

import ecobill.core.util.I18NComboBoxItem;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Roman Georg Rädle
 * Date: 28.09.2005
 * Time: 19:00:26
 * To change this template use File | Settings | File Templates.
 */
public class ComboBoxUtils {

    public static Object[] getI18NTitles() {

        List<Object> l = new LinkedList<Object>();

        l.add(new I18NComboBoxItem("mr"));
        l.add(new I18NComboBoxItem("ms"));
        l.add(new I18NComboBoxItem("firm"));

        return l.toArray();
    }

    public static Object[] getI18NCountries() {

        List<Object> c = new LinkedList<Object>();

        c.add(new I18NComboBoxItem("ger"));
        c.add(new I18NComboBoxItem("eng"));
        c.add(new I18NComboBoxItem("usa"));

        return c.toArray();
    }

    public static Object[] getI18NATitles() {

        List<Object> c = new LinkedList<Object>();

        c.add(new I18NComboBoxItem("no"));
        c.add(new I18NComboBoxItem("dr"));
        c.add(new I18NComboBoxItem("prof"));
        c.add(new I18NComboBoxItem("profdr"));

        return c.toArray();
    }


}
