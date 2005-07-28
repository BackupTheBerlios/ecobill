package ecobill.util;

import ecobill.core.ui.component.BoxItem;
// @todo document me!

/**
 * UnitUtils.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 20:09:09
 *
 * @author Roman R&auml;dle
 * @version $Id: UnitUtils.java,v 1.1 2005/07/28 21:03:53 raedler Exp $
 * @since DAPS INTRA 1.0
 */
public class UnitUtils {
    public static Object[] getAllUnits() {

        return new Object[] {
                new BoxItem("weight.mg"),
                new BoxItem("weight.g"),
                new BoxItem("weight.kg"),
                new BoxItem("weight.t"),
                new BoxItem("volume.ml"),
                new BoxItem("volume.cl"),
                new BoxItem("volume.l"),
                new BoxItem("scale.mm"),
                new BoxItem("scale.cm"),
                new BoxItem("scale.dm"),
                new BoxItem("scale.m"),
                new BoxItem("scale.km"),
                new BoxItem("scale.mm_quad"),
                new BoxItem("scale.cm_quad"),
                new BoxItem("scale.dm_quad"),
                new BoxItem("scale.m_quad"),
                new BoxItem("scale.km_quad"),
                new BoxItem("scale.ar"),
                new BoxItem("scale.hekta"),
                new BoxItem("scale.morgen")
        };
    }
}