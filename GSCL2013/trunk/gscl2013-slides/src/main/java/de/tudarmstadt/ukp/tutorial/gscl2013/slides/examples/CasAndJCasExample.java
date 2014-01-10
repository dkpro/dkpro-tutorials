package de.tudarmstadt.ukp.tutorial.gscl2013.slides.examples;

import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.util.CasCreationUtils.createCas;

import org.apache.uima.UIMAFramework;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.resource.metadata.impl.TypeSystemDescription_impl;
import org.apache.uima.util.XMLInputSource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.tudarmstadt.ukp.tutorial.gscl2013.slides.Token;

/**
 * Examples for creating a CAS or JCas using pure UIMA and uimaFIT.
 *
 * @author Richard Eckart de Castilho
 */
public class CasAndJCasExample
{
	@Test
	public void casExample()
		throws Exception
	{
		// Create a new type system from scratch
		TypeSystemDescription tsd = new TypeSystemDescription_impl();
		TypeDescription tokenTypeDesc = tsd.addType("Token", "", CAS.TYPE_NAME_ANNOTATION);
		tokenTypeDesc.addFeature("length", "", CAS.TYPE_NAME_INTEGER);

		// Create a CAS initialized with that type system and set the text
		CAS cas = createCas(tsd, null, null);
		cas.setDocumentText("This is a test.");

		System.out.println(cas.getDocumentAnnotation());

		// Add some annotations
		Type tokenType = cas.getTypeSystem().getType("Token");
		cas.addFsToIndexes(cas.createAnnotation(tokenType, 0, 4));
		cas.addFsToIndexes(cas.createAnnotation(tokenType, 5, 7));
		cas.addFsToIndexes(cas.createAnnotation(tokenType, 8, 9));
		cas.addFsToIndexes(cas.createAnnotation(tokenType, 10, 14));
		cas.addFsToIndexes(cas.createAnnotation(tokenType, 14, 15));

		// Iterate over the annotations and set a "length" feature on each
		Feature lengthFeat = tokenType.getFeatureByBaseName("length");
		AnnotationIndex<AnnotationFS> tokenIdx = cas.getAnnotationIndex(tokenType);
		for (AnnotationFS token : tokenIdx) {
			token.setIntValue(lengthFeat, token.getCoveredText().length());
		}

		// Print out the annotated text and the "length" feature value for each annotation
		for (AnnotationFS token : tokenIdx) {
			System.out.println(token.getCoveredText()+" - "+token.getFeatureValueAsString(lengthFeat));
		}
	}

	@Test
	public void jCasExample() throws Exception
	{
		// Load a type system from an XML descriptor
		TypeSystemDescription tsd = UIMAFramework.getXMLParser().parseTypeSystemDescription(
				new XMLInputSource("src/main/resources/de/tudarmstadt/ukp/tutorial/gscl2013/slides/typeSystemDescriptor.xml"));

		// Create a JCas initialized with that type system and set the text
		JCas jcas = createCas(tsd, null, null).getJCas();
		jcas.setDocumentText("This is a test.");

		// Add some annotations
		new Token(jcas, 0, 4).addToIndexes();
		new Token(jcas, 5, 7).addToIndexes();
		new Token(jcas, 8, 9).addToIndexes();
		new Token(jcas, 10, 14).addToIndexes();
		new Token(jcas, 14, 15).addToIndexes();

		// Iterate over the annotations and set a "length" feature on each
		for (Annotation token : jcas.getAnnotationIndex(Token.type)) {
			((Token) token).setLength(token.getCoveredText().length());
		}

		// Print out the annotated text and the "length" feature value for each annotation
		for (Annotation token : jcas.getAnnotationIndex(Token.type)) {
			Token t = (Token) token;
			System.out.println(token.getCoveredText()+" - "+t.getLength());
		}
	}

	@Test
	public void jCasUimafitExample() throws Exception
	{
		// Automatically detect types using uimaFIT's type detection mechanism
		// Create a JCas initialized with that type system and set the text
		JCas jcas = createCas(createTypeSystemDescription(), null, null).getJCas();
		jcas.setDocumentText("This is a test.");

		// Add some annotations
		new Token(jcas, 0, 4).addToIndexes();
		new Token(jcas, 5, 7).addToIndexes();
		new Token(jcas, 8, 9).addToIndexes();
		new Token(jcas, 10, 14).addToIndexes();
		new Token(jcas, 14, 15).addToIndexes();

		// Iterate over the annotations and set a "length" feature on each
		for (Token token : select(jcas, Token.class)) {
			token.setLength(token.getCoveredText().length());
		}

		// Print out the annotated text and the "length" feature value for each annotation
		for (Token token : select(jcas, Token.class)) {
			System.out.println(token.getCoveredText()+" - "+token.getLength());
		}
	}

	@Rule public TestName name = new TestName();
	@Before
	public void printSeparator()
	{
		System.out.println("\n=== "+name.getMethodName()+" =====================");
	}
}
