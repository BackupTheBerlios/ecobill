package ecobill.core.ui.component;

import ecobill.core.system.WorkArea;

import java.util.Properties;

/**
 * ***************************************************************************
 * Aufgabenzettel 5
 * Aufgabe 5.1, 5.2
 * Gruppe: Arbeitsgruppe 7
 * Gruppenmitglieder: Karin Sch�uble (605261); Tilo Nietzschmann (605259); Andreas Weiler (560182); Roman R�dle (546759);
 * Datum: 09.07.2005
 * <p/>
 * Das <code>BoxItem</code> wird f�r die <code>JComboBox</code> verwendet und
 * beinhaltet den Schl�ssel der �ber i18n in den landesspezifischen Namen
 * umgewandelt wird.
 * ****************************************************************************
 */
public class BoxItem {

    /**
     * Der anzuzeigende Name des BoxItem.
     */
    private String name;

    /**
     * Das l�nderspezifische Properties File.
     */
    private static Properties props;

    /**
     * Ein neues <code>BoxItem</code> dem zugleich der
     * Name �bergeben werden muss.
     *
     * @param name Der Name des BoxItem.
     */
    public BoxItem(String name) {
        this.name = name;
    }

    /**
     * Gibt den Namen des BoxItem zur�ck.
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