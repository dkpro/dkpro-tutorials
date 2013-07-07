package de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.io;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Logger;
import org.uimafit.component.JCasConsumer_ImplBase;

/**
 * @author Roland Kluge
 */
public class NamePrintingConsumer
    extends JCasConsumer_ImplBase
{

    private Logger logger;

    @Override
    public void initialize(final UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        this.logger = this.getLogger();
    }

    @Override
    public void process(final JCas aJCas)
        throws AnalysisEngineProcessException
    {
        /*
         * Step 1:
         * Add your output code here 
         * (e.g. start, end of annotation, covered text, etc.)
         */
    }
}
