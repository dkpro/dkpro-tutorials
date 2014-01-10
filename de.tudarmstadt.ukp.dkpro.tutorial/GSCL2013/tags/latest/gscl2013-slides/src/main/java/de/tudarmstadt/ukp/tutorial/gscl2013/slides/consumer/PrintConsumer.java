package de.tudarmstadt.ukp.tutorial.gscl2013.slides.consumer;

import static org.apache.uima.fit.util.CasUtil.select;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceInitializationException;

public class PrintConsumer
	extends CasConsumer_ImplBase
{
	private Type annotationType;
	private int count;

	@Override
	public void initialize(UimaContext aContext)
		throws ResourceInitializationException
	{
		super.initialize(aContext);
		count = 0;
	}

	@Override
	public void typeSystemInit(TypeSystem aTypeSystem)
		throws AnalysisEngineProcessException
	{
		annotationType = aTypeSystem.getType(CAS.TYPE_NAME_ANNOTATION);
	}

	@Override
	public void process(CAS aCas)
		throws AnalysisEngineProcessException
	{
		System.out.println("=== METADATA ========================================");
		System.out.println("Language: " + aCas.getDocumentLanguage());
		System.out.println("=== TEXT ============================================");
		System.out.println(aCas.getDocumentText());
		System.out.println("=== ANNOTATIONS =====================================");
		for (AnnotationFS a : select(aCas, annotationType)) {
			System.out.println(a.getType().getName() + "(" + a.getBegin() + "," + a.getEnd()
					+ ") [" + a.getCoveredText() + "]");
		}

		count++;
	}

	@Override
	public void collectionProcessComplete()
		throws AnalysisEngineProcessException
	{
		System.out.println("Processed in total "+count+" CASes");
	}
}
