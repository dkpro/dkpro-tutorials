package de.tudarmstadt.ukp.tutorial.gscl2013.pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

/**
 * Simple pipeline without a reader or a writer, as it could be used when embedding it into an
 * application. The processed text is tokenizer and tagger with part-of-speech information.
 */
public class PipelineFullyEmbedded
{
    public static void main(String[] args) throws Exception
    {
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentText("The quick brown fox jumps over a lazy dog.");
        jcas.setDocumentLanguage("en");
        
        AnalysisEngine pipeline = createEngine(createEngineDescription(
                createEngineDescription(OpenNlpSegmenter.class),
                createEngineDescription(OpenNlpPosTagger.class)));
        
        pipeline.process(jcas);
        
        for (Token token : select(jcas, Token.class)) {
            System.out.printf("%s\t%s%n", token.getCoveredText(), token.getPos().getPosValue());
        }
    }
}
