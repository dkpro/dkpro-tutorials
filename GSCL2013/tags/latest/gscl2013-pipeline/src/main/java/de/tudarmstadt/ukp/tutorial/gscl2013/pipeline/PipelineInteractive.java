package de.tudarmstadt.ukp.tutorial.gscl2013.pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Progress;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

/**
 * Simple pipeline using a custom reader and a writer. The processed text is tokenizer and tagger
 * with part-of-speech information.
 */
public class PipelineInteractive
{
    public static void main(String[] args) throws Exception
    {
        SimplePipeline.runPipeline(
                createReaderDescription(ConsoleReader.class,
                        TextReader.PARAM_LANGUAGE, "en"),
                createEngineDescription(OpenNlpSegmenter.class),
                createEngineDescription(OpenNlpPosTagger.class),
                createEngineDescription(ConsoleWriter.class));
    }
    
    /**
     * This writer write to the console, but it could write elsewhere, e.g. to a file, database,
     * etc.
     */
    public static class ConsoleReader extends JCasCollectionReader_ImplBase
    {
        public static final String PARAM_LANGUAGE = "language";
        @ConfigurationParameter(name = PARAM_LANGUAGE)
        private String language;
        
        private boolean quit = false;
        
        @Override
        public void getNext(JCas aJCas)
            throws IOException, CollectionException
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
            String text = br.readLine();
            
            if (text.equals("quit")) {
                quit = true;
                text = ""; // Avoid analyzing "quit"
            }
            
            aJCas.setDocumentText(text);
            aJCas.setDocumentLanguage(language);
        }
        
        public boolean hasNext()
            throws IOException, CollectionException
        {
            if (!quit) {
                System.out.println("Enter text and press <ENTER> to analyze. Type 'quit' on a line "
                        + "by itself to leave.");
            }
            else {
                System.out.println("See you next time.");
            }
            return !quit;
        }

        public Progress[] getProgress()
        {
            return new Progress[0];
        }
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
