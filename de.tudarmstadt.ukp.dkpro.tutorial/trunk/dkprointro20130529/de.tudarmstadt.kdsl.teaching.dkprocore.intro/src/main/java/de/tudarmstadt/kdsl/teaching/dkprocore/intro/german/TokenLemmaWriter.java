package de.tudarmstadt.kdsl.teaching.dkprocore.intro.german;

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

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * @author Eckle-Kohler
 *
 */
public class TokenLemmaWriter
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
			List<Token> sentenceTokens = JCasUtil.selectCovered(jcas, Token.class, sentence);
			for (int i = 0; i < sentenceTokens.size(); i++) {
				Token token = sentenceTokens.get(i);
				writeTokenAndLemma(token.getCoveredText() +" " +token.getLemma().getValue() +"\n");
			}
			
		}				

    }

	private void writeTokenAndLemma(String string)
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
