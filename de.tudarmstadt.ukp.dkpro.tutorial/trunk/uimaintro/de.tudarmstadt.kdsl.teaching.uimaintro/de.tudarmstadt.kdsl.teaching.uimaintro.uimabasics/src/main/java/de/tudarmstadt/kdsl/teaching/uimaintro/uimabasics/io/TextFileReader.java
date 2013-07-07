package de.tudarmstadt.kdsl.teaching.uimaintro.uimabasics.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.uimafit.component.JCasCollectionReader_ImplBase;
import org.uimafit.descriptor.ConfigurationParameter;

/**
 * @author Roland Kluge
 */
public class TextFileReader
    extends JCasCollectionReader_ImplBase
{

    /*
     * It is best practice to use the field name as name
     * of the configuration paramter's name.
     */
    public static final String PARAM_PATH = "directory";

    @ConfigurationParameter(name = PARAM_PATH, mandatory = true, description = "Path to dataset")
    private File directory;

    private Logger logger;
    private List<String> documentTexts;

    private int nextIndex;

    @Override
    public void initialize(final UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);

        this.logger = this.getLogger();
        this.documentTexts = new ArrayList<String>();

        if (!this.directory.isDirectory())
            throw new ResourceInitializationException(new IOException("Dataset path "
                    + this.directory + " is expected to be a directory!"));

        final String[] files = this.directory.list();

        for (final String filename : files) {

            final File file = new File(this.directory, filename);

            try {
                final String documentText = FileUtils.readFileToString(file);
                this.documentTexts.add(documentText);
            }
            catch (final IOException e) {
                this.logger.log(Level.WARNING, "Could not read file: " + filename + ". Skipping.");
            }
        }

    }

    public int getNumDocuments()
    {
        return this.documentTexts.size();
    }

    @Override
    public void getNext(final JCas nextCas)
        throws IOException, CollectionException
    {
        final String documentText = this.documentTexts.get(this.nextIndex);
        nextCas.setDocumentText(documentText);

        ++this.nextIndex;
    }

    @Override
    public void close()
        throws IOException
    {
        // nop
    }

    @Override
    public Progress[] getProgress()
    {
        return new Progress[] { new ProgressImpl(this.nextIndex, this.getNumDocuments(),
                Progress.ENTITIES) };
    }

    @Override
    public boolean hasNext()
        throws IOException, CollectionException
    {
        return this.nextIndex < this.getNumDocuments();
    }

}
