package uby.tutorial.uima;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createExternalResourceDescription;
import static org.uimafit.factory.CollectionReaderFactory.createDescription;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.pipeline.SimplePipeline;


import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.dictionaryannotator.semantictagging.UbySemanticFieldAnnotator;
import de.tudarmstadt.ukp.dkpro.core.dictionaryannotator.semantictagging.UbySemanticFieldResource;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.uby.resource.UbyResourceLocator;

public class LinguisticAnnotationPipeline {
	
	public static void main(String[] args)
		    throws UIMAException, IOException
		{
				
				CollectionReaderDescription reader = createDescription(
						TextReader.class,
						TextReader.PARAM_PATH, "src/main/resources",
						TextReader.PARAM_PATTERNS, new String[] { "[+]*.txt", "[-]broken.txt" },
						TextReader.PARAM_LANGUAGE, "en"
						);
				
				
				AnalysisEngineDescription tokenizer = createEngineDescription(ClearNlpSegmenter.class);
				AnalysisEngineDescription posTagger = createEngineDescription(ClearNlpPosTagger.class);
				AnalysisEngineDescription lemmatizer = createEngineDescription(ClearNlpLemmatizer.class);
				
			    AnalysisEngineDescription semanticFieldAnnotator = createEngineDescription(

			    	        createEngineDescription(
			    	                UbySemanticFieldAnnotator.class,
			    	                UbySemanticFieldAnnotator.PARAM_UBY_SEMANTIC_FIELD_RESOURCE,
			    	                createExternalResourceDescription(UbySemanticFieldResource.class,
			    	                        UbySemanticFieldResource.PARAM_URL, "localhost/uby_open_0_3_0",
			    	                        UbySemanticFieldResource.PARAM_DRIVER, "com.mysql.jdbc.Driver",
			    	                        UbySemanticFieldResource.PARAM_DRIVER_NAME, "mysql",
			    	                        UbySemanticFieldResource.PARAM_USERNAME, "root",
			    	                        UbySemanticFieldResource.PARAM_PASSWORD, "pass")));



				AnalysisEngineDescription semanticFieldWriter =
						AnalysisEngineFactory.createPrimitiveDescription(
							SemanticFieldConsumer.class,
							SemanticFieldConsumer.PARAM_OUTPUT, "target/tokensSemanticFields.txt");

				
				
				SimplePipeline.runPipeline(reader, tokenizer, posTagger, lemmatizer, semanticFieldAnnotator, semanticFieldWriter);
				
				
		}
}
