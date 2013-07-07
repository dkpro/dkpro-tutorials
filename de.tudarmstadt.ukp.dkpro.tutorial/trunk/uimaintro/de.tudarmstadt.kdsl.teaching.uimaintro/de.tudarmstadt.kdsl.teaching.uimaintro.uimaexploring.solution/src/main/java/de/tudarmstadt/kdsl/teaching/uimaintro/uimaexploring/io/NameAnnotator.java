package de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.descriptor.ConfigurationParameter;
import org.uimafit.util.JCasUtil;

import de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type.Name;
import de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type.NameType;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * @author Roland Kluge
 */
public class NameAnnotator
    extends JCasAnnotator_ImplBase
{
    public static final String PARAM_DICTIONARY_FILE = "dictionaryFile";
    private static final String COMMENT_SIGN = "%";
    @ConfigurationParameter(name = PARAM_DICTIONARY_FILE, mandatory = true)
    private File dictionaryFile;

    private Map<String, String> nameToNameType;

    @Override
    public void initialize(UimaContext aContext)
        throws ResourceInitializationException
    {
        super.initialize(aContext);
        try {
            this.nameToNameType = new HashMap<String, String>();

            final List<String> lines = FileUtils.readLines(this.dictionaryFile,
                    Charset.forName("UTF-8"));
            for (final String line : lines) {
                if (!line.startsWith(COMMENT_SIGN)) {
                    final int lastSpaceIndex = line.lastIndexOf(' ');

                    final String name = line.substring(0, lastSpaceIndex);
                    final String nametypeString = line.substring(lastSpaceIndex + 1);

                    this.nameToNameType.put(this.normalize(name), nametypeString);
                }
            }
        }
        catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(final JCas jcas)
        throws AnalysisEngineProcessException
    {
        for (final Token token : JCasUtil.select(jcas, Token.class)) {

            final String text = this.normalize(token.getCoveredText());

            if (this.nameToNameType.keySet().contains(text)) {

                final Name nameAnno = new Name(jcas, token.getBegin(), token.getEnd());
                final NameType nameType = new NameType(jcas);
                nameType.setValue(this.nameToNameType.get(text));
                nameAnno.setNameType(nameType);
                nameAnno.addToIndexes();

            }

        }
    }

    /**
     * Produces a canonical form of the given string which may be used for lookup.
     * 
     * @param str
     *            the string to normalize
     * @return the normalized string
     */
    private String normalize(final String str)
    {
        return str.toLowerCase();
    }
}
