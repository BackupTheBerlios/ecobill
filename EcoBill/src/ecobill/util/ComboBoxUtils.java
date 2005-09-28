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
}
