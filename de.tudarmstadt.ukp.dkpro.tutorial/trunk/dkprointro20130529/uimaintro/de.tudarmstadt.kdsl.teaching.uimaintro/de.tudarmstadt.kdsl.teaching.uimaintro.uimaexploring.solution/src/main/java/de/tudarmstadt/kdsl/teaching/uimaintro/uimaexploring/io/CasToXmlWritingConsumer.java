package de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.io;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.uimafit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.impl.XCASSerializer;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.JCasConsumer_ImplBase;
import org.uimafit.descriptor.ConfigurationParameter;
import org.xml.sax.SAXException;

public class CasToXmlWritingConsumer
    extends JCasConsumer_ImplBase
{

    public static final String PARAM_TARGET_DIRECTORY = "targetDirectory";
    @ConfigurationParameter(name = PARAM_TARGET_DIRECTORY, mandatory = true)
    private File targetDirectory;

    private int casCounter;

    @Override
    public void initialize(final UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        this.casCounter = 0;

        try {
            final String targetFile = this.targetDirectory
                    + "/TypeSystem.xml";
            final FileOutputStream typeOut = new FileOutputStream(targetFile);
            createTypeSystemDescription().toXML(typeOut);
            closeQuietly(typeOut);
        }
        catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (final SAXException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(final JCas aJCas)
        throws AnalysisEngineProcessException
    {
        this.targetDirectory.mkdirs();
        try {
            final String targetFile = this.targetDirectory.getAbsolutePath() + "/SerializedCas"
                    + this.casCounter + ".xml";
            final FileOutputStream out = new FileOutputStream(targetFile);
            XCASSerializer.serialize(aJCas.getCas(), out, true);
            IOUtils.closeQuietly(out);

        }
        catch (final SAXException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        ++this.casCounter;
    }

}
