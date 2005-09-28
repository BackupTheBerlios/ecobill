package ecobill.core.util;

import ecobill.core.system.WorkArea;

/**
 * Created by IntelliJ IDEA.
 * User: Roman Georg Rädle
 * Date: 28.09.2005
 * Time: 18:59:15
 * To change this template use File | Settings | File Templates.
 */
public class I18NComboBoxItem {

    private String key;

    public I18NComboBoxItem(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        return WorkArea.getMessage(getKey());
    }
}
