package de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.io;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
import org.uimafit.component.JCasConsumer_ImplBase;
import org.uimafit.util.JCasUtil;

import de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type.Name;

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
        for (final Name name : JCasUtil.select(aJCas, Name.class)) {
            final int begin = name.getBegin();
            final int end = name.getEnd();
            final String text = name.getCoveredText();
            final String type = name.getNameType().getValue();
            this.logger.log(
                    Level.INFO,
                    String.format("[%d, %d] %s (type=%s)", begin, end, text, type));
        }
    }
}
