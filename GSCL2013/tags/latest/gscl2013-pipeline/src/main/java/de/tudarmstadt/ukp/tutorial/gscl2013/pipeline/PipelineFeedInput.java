package de.tudarmstadt.ukp.tutorial.gscl2013.pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

/**
 * Simple pipeline without a reader but with a writer, as it could be used when exporting data
 * from an application. The processed text is tokenizer and tagger with part-of-speech information.
 */
public class PipelineFeedInput
{
    public static void main(String[] args) throws Exception
    {
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentText("The quick brown fox jumps over a lazy dog.");
        jcas.setDocumentLanguage("en");
        
        AnalysisEngineDescription pipeline = createEngineDescription(
                createEngineDescription(OpenNlpSegmenter.class),
                createEngineDescription(OpenNlpPosTagger.class),
                createEngineDescription(ConsoleWriter.class));
        
        SimplePipeline.runPipeline(jcas, pipeline);
    }
    
    /**
     * This writer write to the console, but it could write elsewhere, e.g. to a file, database,
     * etc.
     */
    public static class ConsoleWriter extends JCasConsumer_ImplBase
    {
        @Override
        public void process(JCas aJCas)
            throws AnalysisEngineProcessException
        {
            for (Token token : select(aJCas, Token.class)) {
                System.out.printf("%s\t%s%n", token.getCoveredText(), token.getPos().getPosValue());
            }
        }
    }
}
