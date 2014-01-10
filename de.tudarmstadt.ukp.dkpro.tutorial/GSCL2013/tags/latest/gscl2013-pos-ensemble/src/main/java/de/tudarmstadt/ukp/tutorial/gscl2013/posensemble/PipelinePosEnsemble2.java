package de.tudarmstadt.ukp.tutorial.gscl2013.posensemble;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;

/**
 * Multiple POS taggers are used to create an ensemble-based POS tagger which assigns the POS tag
 * based on a majority vote from the ensemble.
 */
public class PipelinePosEnsemble2
{
    public static void main(String[] args)
        throws Exception
    {
        // Create document
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentText("Can I have a can of coke please?");
        jcas.setDocumentLanguage("en");

        // Assemble pipeline
        AnalysisEngine pipeline = createEngine(createEngineDescription(
                createEngineDescription(OpenNlpSegmenter.class),
                createEngineDescription(EnsemblePosTagger.class)));
        
        // Process document with pipeline
        pipeline.process(jcas);
        
        // Display token along with its POS tag
        for (Token token : select(jcas, Token.class)) {
            System.out.printf("%s\t%s%n", token.getCoveredText(), token.getPos().getPosValue());
        }
    }
    
    public static class EnsemblePosTagger extends JCasAnnotator_ImplBase {
        AnalysisEngine ensemble;
        
        @Override
        public void initialize(UimaContext aContext)
            throws ResourceInitializationException
        {
            super.initialize(aContext);
            
            ensemble = createEngine(createEngineDescription(
                    createEngineDescription(OpenNlpPosTagger.class),
                    createEngineDescription(StanfordPosTagger.class),
                    createEngineDescription(ClearNlpPosTagger.class),
                    createEngineDescription(MatePosTagger.class)));
        }
        
        @Override
        public void process(JCas aJCas)
            throws AnalysisEngineProcessException
        {
            // Let the ensemple process the text
            ensemble.process(aJCas);
            
            // Do a majority vote
            List<POS> toDelete = new ArrayList<POS>();
            for (Token token : select(aJCas, Token.class)) {
                // Vote on the POS tag for this token
                FrequencyDistribution<String> dist = new FrequencyDistribution<String>();
                List<POS> tokenPos = selectCovered(POS.class, token);
                for (POS pos : tokenPos) {
                    dist.inc(pos.getPosValue());
                }

                // Get vote result
                String best = dist.getSampleWithMaxFreq();
                
                // Purge POS tags that did not win the vote
                POS first = null;
                for (POS pos : tokenPos) {
                    if (pos.getPosValue().equals(best)) {
                        if (first == null) {
                            // Keep the first POS annotation which matches the majority vote
                            first = pos;
                        }
                        else {
                            toDelete.add(pos); // Delete the rest
                        }
                    }
                }
                
                // Update POS on token with the winning tag
                token.setPos(first);
            }
            
            // Delete all POS annotations but the winning one
            for (POS pos : toDelete) {
                pos.removeFromIndexes();
            }
        }
    }
}
