package de.tudarmstadt.ukp.tutorial.gscl2013.posensemble;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;

/**
 * Running multiple POS taggers on the same text, then displaying which POS tags have been assigned
 * to each token and how often.
 */
public class PipelinePosEnsemble
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
                createEngineDescription(OpenNlpPosTagger.class),
                createEngineDescription(StanfordPosTagger.class),
                createEngineDescription(ClearNlpPosTagger.class),
                createEngineDescription(MatePosTagger.class)));
        
        // Process document with pipeline
        pipeline.process(jcas);
        
        // Display for each token which POS tags were created by the POS taggers and how often
        for (Token token : select(jcas, Token.class)) {
            FrequencyDistribution<String> dist = new FrequencyDistribution<String>();
            for (POS pos : selectCovered(POS.class, token)) {
                dist.inc(pos.getPosValue());
            }
            
            // Print tokens and POS tags with their frequency
            System.out.printf("%s", token.getCoveredText());
            for (String pos : dist.getKeys()) {
                System.out.printf("\t%s:%d", pos, dist.getCount(pos));
            }
            System.out.println();
        }
    }
}
