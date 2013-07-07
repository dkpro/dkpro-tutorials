package de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring;

import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.io.NameAnnotator;
import de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.io.NamePrintingConsumer;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

/**
 * @author Roland Kluge
 */
public class NameDetectionPipeline
{
    public static void main(String[] args)
    {
        try {
            /*
             * Step 1: Implement/use reader which produces one CAS per document
             */
            // import static org.uimafit.factory.CollectionReaderFactory.createDescription;
            // CollectionReaderDescription reader = createDescription(...);

            // Tokenize the text
            AnalysisEngineDescription segmenter = createPrimitiveDescription(BreakIteratorSegmenter.class);

            /*
             * Step 2: Implement NameAnnotator and configure it with the names dictionary
             */
            AnalysisEngineDescription nameAnnotator = createPrimitiveDescription(NameAnnotator.class);

            /*
             * Step 3: Write some debugging code which outputs the names your NameAnnotator has
             * detected
             */
            AnalysisEngineDescription namePrintingConsumer = createPrimitiveDescription(NamePrintingConsumer.class);

            /*
             * Step 4: Comment in and run the pipeline
             */
            // SimplePipeline.runPipeline(reader, segmenter, nameAnnotator, namePrintingConsumer);
        }
        catch (final ResourceInitializationException e) {
            e.printStackTrace();
        }
        catch (final UIMAException e) {
            e.printStackTrace();
        }

    }
}
