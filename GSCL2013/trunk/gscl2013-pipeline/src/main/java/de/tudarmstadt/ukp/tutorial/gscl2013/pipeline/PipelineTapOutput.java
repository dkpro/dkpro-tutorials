package de.tudarmstadt.ukp.tutorial.gscl2013.pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

/**
 * Simple pipeline with a reader but no writer, as it could be used when embedding it into an
 * application. The processed text is tokenizer and tagger with part-of-speech information.
 */
public class PipelineTapOutput
{
    public static void main(String[] args) throws Exception
    {
        CollectionReaderDescription reader = createReaderDescription(TextReader.class,
                TextReader.PARAM_SOURCE_LOCATION, "input/example.txt",
                TextReader.PARAM_LANGUAGE, "en");
        
        AnalysisEngineDescription pipeline = createEngineDescription(
                createEngineDescription(OpenNlpSegmenter.class),
                createEngineDescription(OpenNlpPosTagger.class));
        
        for (JCas jcas : SimplePipeline.iteratePipeline(reader, pipeline)) {
            for (Token token : select(jcas, Token.class)) {
                System.out.printf("%s\t%s%n", token.getCoveredText(), token.getPos().getPosValue());
            }
        }
    }
}
