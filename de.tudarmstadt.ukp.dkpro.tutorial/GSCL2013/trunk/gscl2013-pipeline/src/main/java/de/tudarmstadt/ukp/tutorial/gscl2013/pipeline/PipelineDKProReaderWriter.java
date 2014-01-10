package de.tudarmstadt.ukp.tutorial.gscl2013.pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.io.conll.Conll2006Writer;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

/**
 * Simple pipeline using a reader and a writer from the DKPro Core component collection. The
 * processed text is tokenizer and tagger with part-of-speech information.
 */
public class PipelineDKProReaderWriter
{
    public static void main(String[] args) throws Exception
    {
        SimplePipeline.runPipeline(
                createReaderDescription(TextReader.class,
                        TextReader.PARAM_SOURCE_LOCATION, "input/example.txt",
                        TextReader.PARAM_LANGUAGE, "en"),
                createEngineDescription(OpenNlpSegmenter.class),
                createEngineDescription(OpenNlpPosTagger.class),
                createEngineDescription(Conll2006Writer.class,
                        Conll2006Writer.PARAM_TARGET_LOCATION, "output"));
    }
}
