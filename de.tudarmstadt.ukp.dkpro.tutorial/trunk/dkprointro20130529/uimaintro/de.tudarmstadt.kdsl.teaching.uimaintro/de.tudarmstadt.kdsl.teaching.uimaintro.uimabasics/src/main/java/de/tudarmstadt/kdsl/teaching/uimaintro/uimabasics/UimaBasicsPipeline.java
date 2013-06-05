package de.tudarmstadt.kdsl.teaching.uimaintro.uimabasics;

import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.CollectionReaderFactory.createDescription;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.xwriter.CASDumpWriter;
import org.uimafit.pipeline.SimplePipeline;

import de.tudarmstadt.kdsl.teaching.uimaintro.uimabasics.io.AnnotationFrequencyConsumer;
import de.tudarmstadt.kdsl.teaching.uimaintro.uimabasics.io.TextFileReader;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

/**
 * This pipeline presents two pipelines:
 * <ol>
 * <li>The "custom" pipeline consists solely of self-implemented components and demonstrates the use
 * of collection reader, analysis engines and consumers.</li>
 * <li>The "standard" pipeline shows which standard components are viable replacements for each
 * custom component.</li>
 * </ol>
 * 
 * @author Roland Kluge
 */
public class UimaBasicsPipeline
{
    public static void main(final String[] args)
    {

        runCustomPipeline();
        runStandardPipeline();

    }

    private static void runCustomPipeline()
    {
        try {

            /*
             *  Text file reader produces one CAS per text file in the directory
             * This is not "DKPro-style" as normally every "PATH" variable should be accompanied
             * with a "PATTERN" variable for fine-grained file selection (readers).
             */
            CollectionReaderDescription reader = createDescription(TextFileReader.class, //
                    TextFileReader.PARAM_PATH, "src/test/resources/txt");

            // Custom segmentation component which only produces word tokens (no sentences)
            AnalysisEngineDescription segmenter = createPrimitiveDescription(BreakIteratorTokenizer.class);

            // Prints a frequency distribution of the annotations in use
            AnalysisEngineDescription consumer = createPrimitiveDescription(AnnotationFrequencyConsumer.class);

            SimplePipeline.runPipeline(reader, segmenter, consumer);
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

    private static void runStandardPipeline()
    {
        try {

            /*
             * DKPro text file reader component
             * 
             * Important general notes: Never put large collections of text in the resource
             * folders as these folders are under version control! There exists the environment
             * variable DKPRO_HOME, which allows you to refer to your corpora via relative paths,
             * i.e., say DKPR_HOME=/data and you have a corpus located at /data/corpu01, then the
             * TextReader.PARAM_PATH should be set to corpus01. Besides the "DKPRO_HOME" mechanism
             * of referring to your data, you may as well use absolute paths.
             */
            CollectionReaderDescription reader = createDescription(TextReader.class, //
                    TextReader.PARAM_PATH, "src/test/resources/txt", //
                    TextReader.PARAM_LANGUAGE, "de", //
                    TextReader.PARAM_PATTERNS, new String[] { "[+]*.txt" });

            // Produces sentence and token annotations
            AnalysisEngineDescription segmenter = createPrimitiveDescription(BreakIteratorSegmenter.class);

            // CASDumpWriter dumps the structure of the whole CAS to standard output
            AnalysisEngineDescription consumer = createPrimitiveDescription(CASDumpWriter.class);

            SimplePipeline.runPipeline(reader, segmenter, consumer);
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
