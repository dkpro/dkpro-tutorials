package de.tudarmstadt.ukp.tutorial.gscl2013.slides.annotator;

import java.text.BreakIterator;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import de.tudarmstadt.ukp.tutorial.gscl2013.slides.Token;

public class TokenAnnotator
	extends JCasAnnotator_ImplBase
{
	@Override
	public void process(JCas aJCas)
		throws AnalysisEngineProcessException
	{
		BreakIterator bi = BreakIterator.getWordInstance();
		bi.setText(aJCas.getDocumentText());
		int begin = bi.first();
		int end;
		for (end = bi.next(); end != BreakIterator.DONE; end = bi.next()) {
			addToken(aJCas, begin, end);
			begin = end;
		}
		addToken(aJCas, begin, aJCas.getDocumentText().length());
	}

	private void addToken(JCas aJCas, int begin, int end)
	{
		if (aJCas.getDocumentText().substring(begin, end).trim().length() > 0) {
			new Token(aJCas, begin, end).addToIndexes();
		}
	}
}
