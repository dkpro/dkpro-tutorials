package de.tudarmstadt.ukp.tutorial.gscl2013.slides.annotator;

import static org.apache.commons.io.FileUtils.readLines;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.tutorial.gscl2013.slides.Name;
import de.tudarmstadt.ukp.tutorial.gscl2013.slides.Token;

public class NameAnnotator
	extends JCasAnnotator_ImplBase
{
	public static final String PARAM_DICTIONARY_FILE = "dictionaryFile";
	@ConfigurationParameter(name = PARAM_DICTIONARY_FILE, mandatory = true)
	private File dictionaryFile;

	private Set<String> names;

	@Override
	public void initialize(UimaContext aContext)
		throws ResourceInitializationException
	{
		super.initialize(aContext);
		try {
			names = new HashSet<String>(readLines(dictionaryFile));
		}
		catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
	}

	@Override
	public void process(JCas jcas)
		throws AnalysisEngineProcessException
	{
		// Annotate tokens contained in the dictionary as name
		for (Token token : select(jcas, Token.class)) {
			if (names.contains(token.getCoveredText())) {
				new Name(jcas, token.getBegin(), token.getEnd()).addToIndexes();
			}
		}
	}

	@Override
	public void destroy()
	{
		// Nothing to clean up
	}
}
