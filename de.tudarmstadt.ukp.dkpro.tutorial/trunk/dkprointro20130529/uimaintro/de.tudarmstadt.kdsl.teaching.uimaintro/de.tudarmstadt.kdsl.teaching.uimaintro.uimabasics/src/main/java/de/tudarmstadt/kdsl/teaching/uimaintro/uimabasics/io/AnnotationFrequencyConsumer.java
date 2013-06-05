package de.tudarmstadt.kdsl.teaching.uimaintro.uimabasics.io;

import org.apache.commons.math3.stat.Frequency;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.uimafit.component.JCasConsumer_ImplBase;
import org.uimafit.util.ExtendedLogger;
import org.uimafit.util.JCasUtil;

/**
 * @author Roland Kluge
 */
public class AnnotationFrequencyConsumer
    extends JCasConsumer_ImplBase
{

    private Frequency frequencyDistributionOfAnnotations;
    private ExtendedLogger logger;

    @Override
    public void initialize(final UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);

        this.frequencyDistributionOfAnnotations = new Frequency();
        this.logger = this.getLogger();
    }

    @Override
    public void process(final JCas aJCas)
        throws AnalysisEngineProcessException
    {
        for (final Annotation annotation : JCasUtil.select(aJCas, Annotation.class)) {
            this.frequencyDistributionOfAnnotations.addValue(annotation.getType().toString());
        }
    }

    @Override
    public void collectionProcessComplete()
    {
        this.logger.log(Level.INFO, this.frequencyDistributionOfAnnotations.toString());
    }

}
