package uby.tutorial.uima;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.uby.resource.UbyResourceLocator;
import de.tudarmstadt.ukp.uby.resource.UbySemanticFieldResource;
import de.tudarmstadt.ukp.uby.uima.annotator.UbySemanticFieldAnnotator;

public class LinguisticAnnotationPipeline {
	
	public static void main(String[] args)
		    throws UIMAException, IOException
		{
				CollectionReaderDescription reader = createReaderDescription(
						TextReader.class,
						TextReader.PARAM_SOURCE_LOCATION, "input/Wikipedia_English.txt",
						TextReader.PARAM_LANGUAGE, "en"
						);
				
				
				AnalysisEngineDescription tokenizer = createEngineDescription(ClearNlpSegmenter.class);
				AnalysisEngineDescription posTagger = createEngineDescription(ClearNlpPosTagger.class);
				AnalysisEngineDescription lemmatizer = createEngineDescription(ClearNlpLemmatizer.class);
				
			    AnalysisEngineDescription semanticFieldAnnotator = 
			    	        createEngineDescription(
			    	                UbySemanticFieldAnnotator.class,
			    	                UbySemanticFieldAnnotator.PARAM_UBY_SEMANTIC_FIELD_RESOURCE,
			    	                createExternalResourceDescription(UbySemanticFieldResource.class,
			    	                        UbySemanticFieldResource.PARAM_URL, "localhost/uby_open_0_3_0",
			    	                        UbySemanticFieldResource.PARAM_DRIVER, "com.mysql.jdbc.Driver",
			    	                        UbySemanticFieldResource.PARAM_DRIVER_NAME, "mysql",
			    	                        UbySemanticFieldResource.PARAM_USERNAME, "root",
			    	                        UbySemanticFieldResource.PARAM_PASSWORD, "pass"));
			    
			    
			    // this analysis engine writes some linguistic annotations to a file
			    // this is also an example of an analysis engine that calls UBY as an external resource in order to 
			    // look up semantic labels of common nouns, which are then written to the console
				AnalysisEngineDescription semanticFieldWriter =
						createEngineDescription(
							SemanticFieldConsumer.class,
							SemanticFieldConsumer.PARAM_OUTPUT, "output/exampleTextWithSemanticFields.txt",
							SemanticFieldConsumer.PARAM_UBY_RESOURCE,
	    	                createExternalResourceDescription(UbyResourceLocator.class,
	    	                		UbyResourceLocator.PARAM_URL, "localhost/uby_open_0_3_0",
	    	                		UbyResourceLocator.PARAM_DRIVER, "com.mysql.jdbc.Driver",
	    	                		UbyResourceLocator.PARAM_DRIVER_NAME, "mysql",
	    	                		UbyResourceLocator.PARAM_USERNAME, "root",
	    	                		UbyResourceLocator.PARAM_PASSWORD, "pass")
								);

							
				SimplePipeline.runPipeline(reader, 
											tokenizer, 
											posTagger, 
											lemmatizer, 
											semanticFieldAnnotator, 
											semanticFieldWriter);
								
		}
}
