package de.tudarmstadt.kdsl.teaching.dkprocore.intro.german;


import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.tokit.GermanSeparatedParticleAnnotator;


/**
 * @author Eckle-Kohler
 *
 */
public class RunLinguisticAnnotationPipeline
{
			
	public static void main(String[] args)
    throws Exception
{
		//******* IMPORTANT ***********************************************************
		// In this example, the small sample text is located in "src/main/resources/"
		// Generally, you should always use a location outside your eclipse workspace
		// for keeping large text corpora.
		// ****************************************************************************
		CollectionReaderDescription reader = createReaderDescription(
				TextReader.class,
				TextReader.PARAM_SOURCE_LOCATION, "src/main/resources/",
				TextReader.PARAM_PATTERNS, new String[] { "[+]*.txt", "[-]broken.txt" },
				TextReader.PARAM_LANGUAGE, "de"
				);
		
		AnalysisEngineDescription tokenizer = 
			createEngineDescription(
				StanfordSegmenter.class);
		
		AnalysisEngineDescription tagger =
			createEngineDescription(
					OpenNlpPosTagger.class,		
					OpenNlpPosTagger.PARAM_PRINT_TAGSET, false);
		
		
		//AnalysisEngineDescription chunker = 
			//AnalysisEngineFactory.createPrimitiveDescription(TreeTaggerChunkerTT4J.class,
			//		TreeTaggerChunkerTT4J.PARAM_PRINT_TAGSET, true);

		AnalysisEngineDescription lemmaPostprocessor =
			createEngineDescription(
				GermanSeparatedParticleAnnotator.class);
		
		
		
		// this section lists different consumers that write linguistic annotations into a text file
		
		// the TokenLemmaWriter writes tokens and lemmas
		AnalysisEngineDescription tokenLemmaWriter =
			createEngineDescription(
				TokenLemmaWriter.class,
				TokenLemmaWriter.PARAM_OUTPUT, "target/tokenLemma.txt");
		
		// the SentenceChunkWriter writes sentences and chunks in these sentences
		AnalysisEngineDescription sentenceChunkWriter =
				createEngineDescription(
					SentenceChunkWriter.class,
					SentenceChunkWriter.PARAM_OUTPUT, "target/sentenceChunks.txt");
		
		// the FineGrainedPosTagWriter writes tokens that are tagged with the DKPro type "V" along with the original tag
		AnalysisEngineDescription fineGrainedPosTagWriter =
				createEngineDescription(
						FineGrainedPosTagWriter.class,
						FineGrainedPosTagWriter.PARAM_OUTPUT, "target/verbPos.txt");

		
		// note that running the pipeline 3 times with the same reader is possible ONLY,
		// because we created a uimaFIT description of it - CollectionReaderDescription - 
		// so uimaFIT creates separate reader instances for us
		// if we had created a usual reader instance (CollectionReader), the reader would have been
		// empty after the first pipeline, because a reader instance passes on all CASes filled with text
		// to the subsequent components
		
		//SimplePipeline.runPipeline(reader, tokenizer, tagger, lemmaPostprocessor, tokenLemmaWriter);
		//SimplePipeline.runPipeline(reader, tokenizer, tagger, chunker, lemmaPostprocessor, sentenceChunkWriter);
		SimplePipeline.runPipeline(reader, tokenizer, tagger, fineGrainedPosTagWriter);
		
		
}

}
