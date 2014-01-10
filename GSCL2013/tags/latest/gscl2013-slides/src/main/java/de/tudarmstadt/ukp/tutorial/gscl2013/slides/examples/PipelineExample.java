package de.tudarmstadt.ukp.tutorial.gscl2013.slides.examples;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.io.FileOutputStream;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.tudarmstadt.ukp.tutorial.gscl2013.slides.annotator.NameAnnotator;
import de.tudarmstadt.ukp.tutorial.gscl2013.slides.annotator.TokenAnnotator;
import de.tudarmstadt.ukp.tutorial.gscl2013.slides.consumer.PrintConsumer;
import de.tudarmstadt.ukp.tutorial.gscl2013.slides.reader.TextFileReader;


public class PipelineExample
{
	@Test
	public void run()
		throws Exception
	{
		CollectionReaderDescription reader = createReaderDescription(
				TextFileReader.class,
				TextFileReader.PARAM_PATH, "input/textfiles",
				TextFileReader.PARAM_LANGUAGE, "la");

		AnalysisEngineDescription tokenizer = createEngineDescription(TokenAnnotator.class);

		AnalysisEngineDescription nameFinder = createEngineDescription(NameAnnotator.class,
				NameAnnotator.PARAM_DICTIONARY_FILE, "input/dictionaries/names.txt");

		AnalysisEngineDescription printConsumer = createEngineDescription(PrintConsumer.class);

		runPipeline(reader, tokenizer, nameFinder, printConsumer);

		AnalysisEngineDescription aggregate = createEngineDescription(tokenizer, nameFinder);
		aggregate.toXML(new FileOutputStream("output/PipelineBuiltWithUimaFit.xml"));
	}

	@Rule public TestName name = new TestName();
	@Before
	public void printSeparator()
	{
		System.out.println("\n=== "+name.getMethodName()+" =====================");
	}
}
