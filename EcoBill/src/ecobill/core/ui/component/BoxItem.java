package ecobill.core.ui.component;

import ecobill.core.system.WorkArea;

import java.util.Properties;

/**
 * ***************************************************************************
 * Aufgabenzettel 5
 * Aufgabe 5.1, 5.2
 * Gruppe: Arbeitsgruppe 7
 * Gruppenmitglieder: Karin Schäuble (605261); Tilo Nietzschmann (605259); Andreas Weiler (560182); Roman Rädle (546759);
 * Datum: 09.07.2005
 * <p/>
 * Das <code>BoxItem</code> wird für die <code>JComboBox</code> verwendet und
 * beinhaltet den Schlüssel der über i18n in den landesspezifischen Namen
 * umgewandelt wird.
 * ****************************************************************************
 */
public class BoxItem {

    /**
     * Der anzuzeigende Name des BoxItem.
     */
    private String name;

    /**
     * Das länderspezifische Properties File.
     */
    private static Properties props;

    /**
     * Ein neues <code>BoxItem</code> dem zugleich der
     * Name übergeben werden muss.
     *
     * @param name Der Name des BoxItem.
     */
    public BoxItem(String name) {
        this.name = name;
    }

    /**
     * Gibt den Namen des BoxItem zurück.
     *
     * @return Der Name des BoxItem.
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Name des BoxItem.
     *
     * @param name Der Name des BoxItem.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @see Object#toString()
     */
    public String toString() {
        return WorkArea.getMessage(getName());
    }
}