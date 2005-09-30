package ecobill.core.util;

import java.io.File;

// @todo document me!

/**
 * FileUtils.
 * <p/>
 * User: rro
 * Date: 30.09.2005
 * Time: 15:12:24
 *
 * @author Roman R&auml;dle
 * @version $Id: FileUtils.java,v 1.1 2005/09/30 14:13:16 raedler Exp $
 * @since EcoBill 1.0
 */
public class FileUtils {

    public static File createPathForFile(String pathAndFilename) {

        pathAndFilename = convertFileSeparators(pathAndFilename);

        String path = pathAndFilename.substring(0, pathAndFilename.lastIndexOf("/"));

        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        return new File(pathAndFilename);
    }

    public static String convertFileSeparators(String s) {
        if (s == null)
            return null;
        s = s.replace('\\', '/');
        String separator = System.getProperty("file.separator");
        if (separator.equals("/"))
            return s;
        else
            return replace(s, separator, "/");
    }

    /**
     * Ersetzt alle in diesem <code>String</code> auftretenden Zeichen durch das neue Zeichen.
     *
     * @param inString   Der zu untersuchende <code>String</code>.
     * @param oldPattern Die Zeichen die ersetzt werden sollen.
     * @param newPattern Die Zeichen die eingesetzt werden sollen.
     * @return Der <code>String</code> mit allen ersetzten Zeichen.
     */
    public static String replace(String inString, String oldPattern, String newPattern) {

        if (inString == null)
            return null;
        if (oldPattern == null || newPattern == null)
            return inString;

        StringBuffer sbuf = new StringBuffer();
        int pos = 0;
        int index = inString.indexOf(oldPattern);
        int patLen = oldPattern.length();
        while (index >= 0) {
            sbuf.append(inString.substring(pos, index));
            sbuf.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sbuf.append(inString.substring(pos));

        return sbuf.toString();
    }
}
