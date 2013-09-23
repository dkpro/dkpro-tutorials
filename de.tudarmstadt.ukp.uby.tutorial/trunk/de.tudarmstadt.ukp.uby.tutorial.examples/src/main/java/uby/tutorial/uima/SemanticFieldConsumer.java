package uby.tutorial.uima;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.semantics.type.SemanticField;

/**
 * @author Eckle-Kohler
 *
 */
public class SemanticFieldConsumer 

extends org.apache.uima.fit.component.JCasAnnotator_ImplBase{

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
			
			List<Token> sentenceTokens = JCasUtil.selectCovered(jcas, Token.class, sentence);			
			for (int i = 0; i < sentenceTokens.size(); i++) {
				Token token = sentenceTokens.get(i);
				
				
				List<SemanticField> semanticFieldAnnotations = JCasUtil.selectCovering(jcas, SemanticField.class, token.getBegin(), token.getEnd());
				for (int j = 0; j < semanticFieldAnnotations.size(); j++) {
					SemanticField semanticField = semanticFieldAnnotations.get(j);
					if (semanticField.getValue().equals("UNKNOWN")) {
						writeTokenAndSemanticField(token.getCoveredText() +"\t" 
								+token.getLemma().getValue() +"\t" 
								+token.getPos().getType().getShortName()+"\t" 
								+"---" 
								+"\n");
					} else {
						writeTokenAndSemanticField(token.getCoveredText() +"\t" 
								+token.getLemma().getValue() +"\t" 
								+token.getPos().getType().getShortName()+"\t" 
								+semanticField.getValue() +" " 
								+"\n");
					}
				}
				
			}
						
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
