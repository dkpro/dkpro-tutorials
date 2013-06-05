package de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.io;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.util.JCasUtil;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * @author Roland Kluge
 */
public class NameAnnotator
    extends JCasAnnotator_ImplBase
{
    /*
     * Step 1:
     * Declare a configuration parameter in order to externally set
     * the location of the dictionary file 
     * (src/main/resources/dictionaries/names.txt)
     */

    @Override
    public void initialize(UimaContext aContext)
        throws ResourceInitializationException
    {
        super.initialize(aContext);
        /*
         * Step 1:
         * Read in names file here. 
         * Lines starting with '%' are comment lines
         * You may use or ignore the "type" column
         */
    }

    @Override
    public void process(final JCas jcas)
        throws AnalysisEngineProcessException
    {
        for (final Token token : JCasUtil.select(jcas, Token.class)) {

            /*
             * Step 3:
             * Check whether the token matches a known name 
             * and, if yes, annotate it with an appropriate annotation
             * (you may define your own annotations in the type system descriptor file
             * src/main/resources/desc/types/TypeSystem.xml)
             */
        }
    }
}
