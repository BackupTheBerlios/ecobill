package ecobill.core.netupdate;

// @todo document me!

/**
 * <code>NetUpdateFeedback</code>.
 * <p/>
 * User: rro
 * Date: 03.02.2006
 * Time: 15:55:30
 *
 * @author Roman R&auml;dle
 * @version $Id: NetUpdateFeedback.java,v 1.1 2006/02/04 00:46:52 raedler Exp $
 * @since EcoBill 1.1
 */
public interface NetUpdateFeedback {
    public void upToDate();
    public void start(String name);
    public void startResource(String info, String action);
    public void process(String name, int total, int part, int rate);
    public void doneResource();
    public void done();
    public void finished();
}