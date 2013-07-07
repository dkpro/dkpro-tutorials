package de.tudarmstadt.kdsl.teaching.dkprocore.intro.german;


import static org.uimafit.factory.CollectionReaderFactory.createDescription;


import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;


/**
 * @author Eckle-Kohler
 *
 */
public class RunLinguisticAnnotationPipeline
{
	
	// adapt the CORPUS_PATH according to your directory layout
	// Examples of CORPUS_PATH:
	// Linux file system: "/home/eckle/corpora/kdsl";
	// Windows file system: "C:/Users/Eckle-Kohler/workspace/kdsl"	
	final static String CORPUS_PATH = "/home/username/corpora/kdsl";

	// adapt the absolute path of the output file according to your directory layout
	final static String TOKEN_LEMMA_FILE = "/home/username/kdsl/analysis/tokensLemmas.txt";
	final static String SENT_CHUNK_FILE = "/home/username/kdsl/analysis/sentencesChunks.txt";
	final static String VERB_POS_FILE = "/home/username/kdsl/analysis/verbPos.txt";
	
	
	public static void main(String[] args)
    throws UIMAException, IOException
{
		
		CollectionReaderDescription reader = createDescription(
				TextReader.class,
				TextReader.PARAM_PATH, CORPUS_PATH,
				TextReader.PARAM_PATTERNS, new String[] { "[+]*.txt", "[-]broken.txt" },
				TextReader.PARAM_LANGUAGE, "de"
				);
		
		AnalysisEngineDescription tokenizer = 
			AnalysisEngineFactory.createPrimitiveDescription(
				StanfordSegmenter.class,
				StanfordSegmenter.PARAM_STRICT_ZONING, false,
				StanfordSegmenter.PARAM_CREATE_SENTENCES, true,
				StanfordSegmenter.PARAM_CREATE_TOKENS, true);
		
		AnalysisEngineDescription tagger =
			AnalysisEngineFactory.createPrimitiveDescription(
					OpenNlpPosTagger.class,		
					OpenNlpPosTagger.PARAM_PRINT_TAGSET, false);
		
		
		//AnalysisEngineDescription chunker = 
			//AnalysisEngineFactory.createPrimitiveDescription(TreeTaggerChunkerTT4J.class,
			//		TreeTaggerChunkerTT4J.PARAM_PRINT_TAGSET, true);

		AnalysisEngineDescription lemmaPostprocessor =
			AnalysisEngineFactory.createPrimitiveDescription(
				GermanSeparatedParticleAnnotator.class);
		
		
		
		// this section lists different consumers that write linguistic annotations into a text file
		
		// the TokenLemmaWriter writes tokens and lemmas
		AnalysisEngineDescription tokenLemmaWriter =
			AnalysisEngineFactory.createPrimitiveDescription(
				TokenLemmaWriter.class,
				TokenLemmaWriter.PARAM_OUTPUT, TOKEN_LEMMA_FILE);
		
		// the SentenceChunkWriter writes sentences and chunks in these sentences
		AnalysisEngineDescription sentenceChunkWriter =
			AnalysisEngineFactory.createPrimitiveDescription(
					SentenceChunkWriter.class,
					SentenceChunkWriter.PARAM_OUTPUT, SENT_CHUNK_FILE);
		
		// the FineGrainedPosTagWriter writes tokens that are tagged with the DKPro type "V" along with the original tag
		AnalysisEngineDescription fineGrainedPosTagWriter =
				AnalysisEngineFactory.createPrimitiveDescription(
						FineGrainedPosTagWriter.class,
						FineGrainedPosTagWriter.PARAM_OUTPUT, VERB_POS_FILE);

		
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
