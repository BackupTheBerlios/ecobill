package ecobill.core.netupdate;

import org.dom4j.io.SAXReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import ecobill.core.netupdate.ui.NetUpdater;

// @todo document me!

/**
 * <code>NetUpdateProcessor</code>.
 * <p/>
 * User: rro
 * Date: 03.02.2006
 * Time: 14:06:50
 *
 * @author Roman R&auml;dle
 * @version $Id: NetUpdateProcessor.java,v 1.2 2006/02/04 18:01:02 raedler Exp $
 * @since EcoBill 1.1
 */
public class NetUpdateProcessor implements Runnable {

    /**
     * Logger to log several messages.
     */
    private static final Log LOG = LogFactory.getLog(NetUpdateProcessor.class);

    private NetUpdateFeedback feedback;
    private Document docRemote;
    private Document docLocal;
    private File appDir;
    private File localFile;

    public NetUpdateProcessor(NetUpdateFeedback feedback, URL remote, File local, File appDir) throws NetUpdateException {
        this.feedback = feedback;
        this.localFile = local;

        if (LOG.isDebugEnabled()) {
            LOG.debug("The local netupdate xml file [" + local.getAbsolutePath() + "]");
        }

        this.appDir = appDir;

        if (LOG.isDebugEnabled()) {
            LOG.debug("The application directory [" + appDir.getAbsolutePath() + "]");
        }

        prepareDocument(remote, true);

        URL localURL;
        try {
            localURL = local.toURI().toURL();
        }
        catch (MalformedURLException murle) {
            throw new NetUpdateException(murle.getMessage(), murle);
        }

        prepareDocument(localURL, false);
    }

    private void prepareDocument(URL url, boolean remote) throws NetUpdateException {

        SAXReader saxReader = new SAXReader();

        try {
            InputStream stream = null;
            try {
                stream = url.openStream();
            }
            catch (IOException ioe) {
                if (LOG.isErrorEnabled()) {
                    LOG.error(ioe.getMessage(), ioe);
                    feedback.finished();
                }
            }

            if (remote) {
                docRemote = saxReader.read(stream);

                if (docRemote == null) {
                    throw new NetUpdateException("Couldn't prepare NetUpdate for remote document [URL@" + url + "]");
                }
            }
            else {
                docLocal = saxReader.read(stream);

                if (docLocal == null) {
                    throw new NetUpdateException("Couldn't prepare NetUpdate for local document [URL@" + url + "]");
                }
            }

            if (stream != null) stream.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (DocumentException de) {
            de.printStackTrace();
        }
    }

    private List<String> createNecessaryUpdates() {
        List<String> remoteNetUpdateNames = getRemoteNetUpdateNames();
        List<String> localNetUpdateNames = getLocalNetUpdateNames();

        remoteNetUpdateNames.removeAll(localNetUpdateNames);

        return remoteNetUpdateNames;
    }

    private List<String> getRemoteNetUpdateNames() {
        List attributes = docRemote.selectNodes("/netupdates-remote/netupdate-remote/@name");
        return extractValueOfAttributes(attributes);
    }

    private List<String> getLocalNetUpdateNames() {
        List attributes = docLocal.selectNodes("/netupdates-local/netupdate-local/@name");
        return extractValueOfAttributes(attributes);
    }

    /**
     * Extracts the values of a <code>List</code> containing
     * <code>Attribute</code> values.
     *
     * @param attributes The <code>List</code> which contains
     *                   <code>Attribute</code> values.
     * @return The extracted value of each attribute store in
     *         a <code>List</code>.
     */
    private List<String> extractValueOfAttributes(List attributes) {

        List<String> values = new ArrayList<String>();

        for (Object o : attributes) {
            if (o instanceof Attribute) {
                Attribute attr = (Attribute) o;

                values.add(attr.getValue());
            }
        }

        return values;
    }

    public void update() throws NetUpdateException {
        new Thread(this).start();
    }

    /**
     * The update process get its own thread to avoid
     * restricting the application speed.
     *
     * @see Runnable#run()
     */
    public void run() {

        List<String> necessaryUpdates = createNecessaryUpdates();

        // If there is no necessary update, the update will
        // marked as up-to-date.
        if (necessaryUpdates.size() == 0) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("All files are up-to-date.");
            }

            feedback.upToDate();
        }

        for (String name : necessaryUpdates) {

            String name1 = docRemote.selectSingleNode("/netupdates-remote/netupdate-remote[@name=\"" + name + "\"]/@name").getText();

            System.out.println("NAME: " + name);
            System.out.println("NAME1: " + name1);

            if (LOG.isDebugEnabled()) {
                //LOG.debug()
            }

            feedback.start(name1);

            int size = docRemote.selectNodes("/netupdates-remote/netupdate-remote[@name=\"" + name + "\"]/resource").size();

            for (int i = 1; i <= size; i++) {

                Node infoNode = docRemote.selectSingleNode("/netupdates-remote/netupdate-remote[@name=\"" + name + "\"]/resource[" + i + "]/@info");
                Node remoteUrlNode = docRemote.selectSingleNode("/netupdates-remote/netupdate-remote[@name=\"" + name + "\"]/resource[" + i + "]/remote/@url");
                Node subDirNode = docRemote.selectSingleNode("/netupdates-remote/netupdate-remote[@name=\"" + name + "\"]/resource[" + i + "]/local/@subdir");
                Node fileNode = docRemote.selectSingleNode("/netupdates-remote/netupdate-remote[@name=\"" + name + "\"]/resource[" + i + "]/local/@file");
                Node actionNode = docRemote.selectSingleNode("/netupdates-remote/netupdate-remote[@name=\"" + name + "\"]/resource[" + i + "]/local/@action");

                String info = infoNode.getText();

                String remoteUrl = null;
                if (remoteUrlNode != null) {
                    remoteUrl = remoteUrlNode.getText();
                }

                String subDir = "/";
                if (subDirNode != null) {
                    subDir = subDirNode.getText();
                }

                String file = fileNode.getText();

                String action = actionNode.getText();

                feedback.startResource(info, subDir + file, action);

                if ("update".equals(action)) updateAction(remoteUrl, subDir, file);
                else if ("delete".equals(action)) deleteAction(subDir, file);
                else throw new NetUpdateException("The action [" + action + "] isn't supported by NetUpdate.");

                feedback.doneResource();
            }

            feedback.done();

            Element element = (Element) docLocal.selectSingleNode("/netupdates-local");
            Element netUpdateElement = element.addElement("netupdate-local");
            netUpdateElement.addAttribute("name", name);
            netUpdateElement.addElement("timestamp").addText(new Date().toString());

            try {
                serializetoXML(new FileOutputStream(localFile), "UTF-8");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        feedback.finished();
    }

    private void updateAction(String remoteUrl, String subDir, String fileName) {
        try {

            URL url = new URL(remoteUrl);

            int contentLength = url.openConnection().getContentLength();
            int absoluteLength = 0;

            String outputFileName = appDir.getAbsolutePath() + subDir + fileName;
            outputFileName = outputFileName.replace('\\', '/');

            if (LOG.isDebugEnabled()) {
                LOG.debug("The target file [" + outputFileName + "] will get an update.");
            }

            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFileName));

            byte[] buffer = new byte[16384];
            int length;
            int bytesPerSecond = 0;

            long deltaTime = 0;
            long time = -System.currentTimeMillis();
            boolean firstRun = true;

            while ((length = bis.read(buffer)) > 0) {

                bos.write(buffer, 0, length);
                bos.flush();

                bytesPerSecond += length;
                absoluteLength += length;

                if (firstRun) {
                    firstRun = false;
                    feedback.process(contentLength, absoluteLength, bytesPerSecond);
                }

                if (absoluteLength >= contentLength) {
                    feedback.process(contentLength, absoluteLength, bytesPerSecond);
                }

                if (deltaTime > 1000) {
                    feedback.process(contentLength, absoluteLength, bytesPerSecond);
                    bytesPerSecond = 0;
                    deltaTime = 0;
                    time = -System.currentTimeMillis();
                }

                deltaTime += time + System.currentTimeMillis();
            }

            bos.flush();
            bos.close();
            bis.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void deleteAction(String subDir, String fileName) {
        File file = new File(appDir + subDir + fileName);

        feedback.process(100, 0, 0);

        if (file.exists()) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("The file [" + file + "] will be deleted");
            }

            file.delete();
        }

        feedback.process(100, 100, 0);
    }

    public void serializetoXML(OutputStream out, String aEncodingScheme) throws Exception {
        OutputFormat outformat = OutputFormat.createPrettyPrint();
        outformat.setEncoding(aEncodingScheme);
        XMLWriter writer = new XMLWriter(out, outformat);
        writer.write(docLocal);
        writer.flush();
    }

    public static void main(String[] args) {
        try {
            new NetUpdateProcessor(new NetUpdater(), new URL("http://ecobill.raedle.info/update/netupdate_remote_v1.xml"), new File("C:/Dokumente und Einstellungen/Romsl/Desktop/EcoBill-1.1/netupdate_local_v1.xml"), new File("C:/Dokumente und Einstellungen/Romsl/Desktop/EcoBill-1.1")).update();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (NetUpdateException nue) {
            nue.printStackTrace();
        }
    }
}