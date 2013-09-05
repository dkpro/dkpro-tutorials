package uby.tutorial.uima;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.descriptor.ConfigurationParameter;
import org.uimafit.util.JCasUtil;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.V;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.semantics.type.SemanticField;

/**
 * @author Eckle-Kohler
 *
 */
public class SemanticFieldConsumer 

extends org.uimafit.component.JCasAnnotator_ImplBase{

    public static final String PARAM_OUTPUT = "outputParam";
    @ConfigurationParameter(name = PARAM_OUTPUT, mandatory=true, description="name of the output file")
    private String outputParam;
    
    private BufferedWriter writer;	
    
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		try{
			writer = new BufferedWriter(new FileWriter(outputParam));

		}catch(IOException ex){
			throw new ResourceInitializationException(ex);
		}	

	}
	
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException{
		for (Sentence sentence : JCasUtil.select(jcas, Sentence.class)) {
			List<SemanticField> semanticFieldAnnotations = JCasUtil.selectCovered(jcas, SemanticField.class, sentence);
			for (int j = 0; j < semanticFieldAnnotations.size(); j++) {
				SemanticField semanticField = semanticFieldAnnotations.get(j);
				
				writeTokenAndSemanticField(semanticField.getCoveredText() +" " 
						+semanticField.getValue() +" " 
						+"\n");
			}

/*			List<Token> sentenceTokens = JCasUtil.selectCovered(jcas, Token.class, sentence);			
			for (int i = 0; i < sentenceTokens.size(); i++) {
				Token token = sentenceTokens.get(i);
				
				List<SemanticField> semanticFieldAnnotations = JCasUtil.selectCovered(jcas, SemanticField.class, token);
				for (int j = 0; j < semanticFieldAnnotations.size(); j++) {
					SemanticField semanticField = semanticFieldAnnotations.get(i);
					
					writeTokenAndSemanticField(token.getCoveredText() +" " 
							+token.getLemma().getValue() +" " 
							+semanticField.getValue() +" " 
							+"\n");
				}
				
			}
			*/
			
		}				

    }

	private void writeTokenAndSemanticField(String string)
	{
		try {
			writer.write(string);
			writer.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}


}
