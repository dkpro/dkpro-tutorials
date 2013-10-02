package de.tudarmstadt.kdsl.teaching.dkprocore.intro.german;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.V;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

	/**
	 * @author Eckle-Kohler
	 *
	 */
	public class FineGrainedPosTagWriter 
	
		extends JCasAnnotator_ImplBase{

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
				List<V> verbPosList = JCasUtil.selectCovered(jcas, V.class, sentence);
				
				writeAnalysisResult(verbPosList.size()+" verbs in the sentence:\n");
				
				for (int i = 0; i < verbPosList.size(); i++) {
					V vPos = verbPosList.get(i);
					writeAnalysisResult("V  " +vPos.getCoveredText() +" " +vPos.getPosValue() +"\n");
				}
				
				// alternative way to get the original POS value: via tokens
				//List<Token> tokens = JCasUtil.selectCovered(jcas, Token.class, sentence);
				//for (int i = 0; i < tokens.size(); i++) {
				//	Token token = tokens.get(i);										
				//	writeAnalysisResult(token.getPos().getPosValue());
				//}
												
				writeAnalysisResult("\n");
			}				

	    }

		private void writeAnalysisResult(String string)
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
