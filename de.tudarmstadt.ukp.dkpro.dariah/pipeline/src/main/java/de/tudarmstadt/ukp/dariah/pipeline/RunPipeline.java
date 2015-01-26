package de.tudarmstadt.ukp.dariah.pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.*;
import static org.apache.uima.fit.factory.CollectionReaderFactory.*;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.CasDumpWriter;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.io.conll.Conll2006Writer;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.matetools.MateLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.matetools.MateMorphTagger;
import de.tudarmstadt.ukp.dkpro.core.matetools.MateParser;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.tudarmstadt.ukp.dkpro.core.tokit.ParagraphSplitter;
import de.tudarmstadt.ukp.dkpro.core.tokit.PatternBasedTokenSegmenter;


public class RunPipeline {

	public static void main(String[] args) throws Exception {
		if(args.length < 2) {
			System.out.println("Usage: java -jar pipeline.jar <Input File> <Output Folder> [Starting Quote String]");
			return;
		}	
		
		
		String startQuote = (args.length >= 3) ? args[2] : "»\"„";
		
		CollectionReaderDescription reader = createReaderDescription(
				TextReader.class,
				TextReader.PARAM_SOURCE_LOCATION, args[0],
				TextReader.PARAM_LANGUAGE, "de");

		AnalysisEngineDescription paragraph = createEngineDescription(ParagraphSplitter.class);	
		AnalysisEngineDescription seg = createEngineDescription(OpenNlpSegmenter.class);	
		AnalysisEngineDescription patternSeg = createEngineDescription(PatternBasedTokenSegmenter.class,
			    PatternBasedTokenSegmenter.PARAM_PATTERNS, "+|[»«]");
		AnalysisEngineDescription tagger = createEngineDescription(MatePosTagger.class);	     
		AnalysisEngineDescription lemma = createEngineDescription(MateLemmatizer.class);	     
		AnalysisEngineDescription morph = createEngineDescription(MateMorphTagger.class);	     
		AnalysisEngineDescription parser = createEngineDescription(MateParser.class); 
		AnalysisEngineDescription ner = createEngineDescription(StanfordNamedEntityRecognizer.class); 
		AnalysisEngineDescription directSpeech =createEngineDescription(
				DirectSpeechAnnotator.class,
				DirectSpeechAnnotator.PARAM_START_QUOTE, startQuote
		);

		AnalysisEngineDescription writer = createEngineDescription(
				DARIAHWriter.class,
				DARIAHWriter.PARAM_TARGET_LOCATION, args[1]);
		
		AnalysisEngineDescription annWriter = createEngineDescription(
				AnnotationWriter.class
				);
		
		SimplePipeline.runPipeline(reader, 
				paragraph,
				seg, 
				patternSeg,
				tagger, 
				lemma,
				morph,
				directSpeech,
				parser,
				ner,
				writer
//				annWriter
		);
		System.out.println("DONE");

	}

}
