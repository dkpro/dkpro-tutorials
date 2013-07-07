package de.tudarmstadt.kdsl.teaching.uimaintro.uimabasics;

import java.text.BreakIterator;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasAnnotator_ImplBase;

import de.tudarmstadt.kdsl.teaching.uimaintro.uimabasics.types.Token;

/**
 * @author Richard Eckart de Castilho
 */
public class BreakIteratorTokenizer extends JCasAnnotator_ImplBase {

	@Override
	public void process(final JCas jcas) throws AnalysisEngineProcessException {
		String document = jcas.getDocumentText();
		BreakIterator wordIterator = BreakIterator.getWordInstance();
		wordIterator.setText(document);
		int start = wordIterator.first();
		int end = wordIterator.next();

		while (end != BreakIterator.DONE) {
			if (Character.isLetterOrDigit(document.charAt(start))) {
				Token tokenAnnotation = new Token(jcas);
				tokenAnnotation.setBegin(start);
				tokenAnnotation.setEnd(end);
				tokenAnnotation.addToIndexes();
			}
			start = end;
			end = wordIterator.next();
		}
	}

}