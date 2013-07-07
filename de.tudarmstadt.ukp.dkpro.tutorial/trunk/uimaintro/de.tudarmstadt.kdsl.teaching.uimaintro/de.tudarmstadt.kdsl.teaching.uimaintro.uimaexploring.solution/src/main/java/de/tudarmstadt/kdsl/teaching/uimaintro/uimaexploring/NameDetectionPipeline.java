package de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring;

import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.CollectionReaderFactory.createDescription;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.pipeline.SimplePipeline;

import de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.io.CasToXmlWritingConsumer;
import de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.io.NameAnnotator;
import de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.io.NamePrintingConsumer;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

/**
 * @author Roland Kluge
 */
public class NameDetectionPipeline
{
    public static void main(String[] args)
    {
        try {
            CollectionReaderDescription reader = createDescription(TextReader.class,
                    TextReader.PARAM_PATH, "src/test/resources/txt", //
                    TextReader.PARAM_LANGUAGE, "de", //
                    TextReader.PARAM_PATTERNS, new String[] { "[+]*.txt" });

            AnalysisEngineDescription segmenter = createPrimitiveDescription(BreakIteratorSegmenter.class);
            AnalysisEngineDescription nameAnnotator = createPrimitiveDescription(
                    NameAnnotator.class, //
                    NameAnnotator.PARAM_DICTIONARY_FILE,
                    "src/main/resources/dictionaries/names.txt");

            AnalysisEngineDescription namePrintingConsumer = createPrimitiveDescription(NamePrintingConsumer.class);

            /*
             * This annotator is OPTIONAL. It produces an XML serialized version of the CASes
             * which may be explored using one of the GUI tools provided by the UIMA SDK
             * (see http://uima.apache.org/downloads/releaseDocs/2.3.0-incubating/docs/html/tools/tools.html)
             */
            AnalysisEngineDescription casSerializingConsumer = createPrimitiveDescription(
                    //
                    CasToXmlWritingConsumer.class, CasToXmlWritingConsumer.PARAM_TARGET_DIRECTORY,
                    "target/cas2xml");
            
            SimplePipeline.runPipeline(reader, segmenter, nameAnnotator, namePrintingConsumer,
                    casSerializingConsumer);
        }
        catch (final ResourceInitializationException e) {
            e.printStackTrace();
        }
        catch (final UIMAException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }

    }
}
