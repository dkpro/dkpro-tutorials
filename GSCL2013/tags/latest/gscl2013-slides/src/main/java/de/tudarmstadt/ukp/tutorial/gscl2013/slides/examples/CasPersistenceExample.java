package de.tudarmstadt.ukp.tutorial.gscl2013.slides.examples;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;
import static org.apache.uima.util.CasCreationUtils.createCas;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.impl.XCASSerializer;
import org.apache.uima.cas.impl.XmiCasDeserializer;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.XmlCasDeserializer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.tudarmstadt.ukp.tutorial.gscl2013.slides.Token;
import de.tudarmstadt.ukp.tutorial.gscl2013.slides.consumer.PrintConsumer;

public class CasPersistenceExample
{
	@Test
	public void persistLoadXCasExample()
		throws Exception
	{
		// Create and populate CAS
		CAS cas = createCas(createTypeSystemDescription(), null, null);
		populateCas(cas);

		// Write CAS to XMI file
		FileOutputStream out = new FileOutputStream("target/SerializedCas.xml");
		XCASSerializer.serialize(cas, out, true);
		closeQuietly(out);

		// Load CAS from XMI file
		CAS loadedCas = createCas(createTypeSystemDescription(), null, null);
		FileInputStream in = new FileInputStream("target/SerializedCas.xml");
		XmlCasDeserializer.deserialize(in, loadedCas);
		closeQuietly(in);

		// Print CAS contents
		createEngine(PrintConsumer.class).process(loadedCas);
	}

	@Test
	public void persistLoadXmiExample()
		throws Exception
	{
		// Create and populate CAS
		CAS cas = createCas(createTypeSystemDescription(), null, null);
		populateCas(cas);

		// Write CAS to XMI file
		FileOutputStream out = new FileOutputStream("target/SerializedCas.xmi");
		XmiCasSerializer.serialize(cas, out);
		closeQuietly(out);

		// Load CAS from XMI file
		CAS loadedCas = createCas(createTypeSystemDescription(), null, null);
		FileInputStream in = new FileInputStream("target/SerializedCas.xmi");
		XmiCasDeserializer.deserialize(in, loadedCas);
		closeQuietly(in);

		// Print CAS contents
		createEngine(PrintConsumer.class).process(loadedCas);
	}

	@Test
	public void persistTypeSytem()
		throws Exception
	{
		// Save type system for CAS Editor
		FileOutputStream typeOut = new FileOutputStream("TypeSystem.xml");
		createTypeSystemDescription().toXML(typeOut);
		closeQuietly(typeOut);
	}

	private void populateCas(CAS cas)
		throws Exception
	{
		JCas jcas = cas.getJCas();
		jcas.setDocumentLanguage("en");
		jcas.setDocumentText("This is a test.");

		// Add some annotations
		new Token(jcas, 0, 4).addToIndexes();
		new Token(jcas, 5, 7).addToIndexes();
		new Token(jcas, 8, 9).addToIndexes();
		new Token(jcas, 10, 14).addToIndexes();
		new Token(jcas, 14, 15).addToIndexes();
	}

	@Rule
	public TestName name = new TestName();

	@Before
	public void printSeparator()
	{
		System.out.println("\n=== " + name.getMethodName() + " =====================");
	}
}
