package de.tudarmstadt.ukp.tutorial.gscl2013.types;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovering;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.testing.factory.TokenBuilder;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

public class LocationAnalyzer
{
    public static void main(String[] args) throws Exception
    {
        // Prepare document
        JCas jcas = JCasFactory.createJCas();
        TokenBuilder<Token, Sentence> builder = TokenBuilder.create(Token.class, Sentence.class);
        builder.buildTokens(jcas, "I woke up in Rio , had lunch in Venice , and went to be in Berlin .");

        // Perform analysis
        AnalysisEngine locationAnnotator = createEngine(LocationAnnotator.class);
        locationAnnotator.process(jcas);

        // Show results
        for (Token token : select(jcas, Token.class)) {
            System.out.printf("%s\t", token.getCoveredText());
            if (!selectCovering(Location.class, token).isEmpty()) {
                System.out.printf("Location");
            }
            System.out.printf("%n");
        }
    }
    
    public static class LocationAnnotator extends JCasAnnotator_ImplBase
    {
        private Set<String> locations;
        
        @Override
        public void initialize(UimaContext aContext)
            throws ResourceInitializationException
        {
            super.initialize(aContext);
            try {
                locations = new HashSet<String>(
                        FileUtils.readLines(new File("input/locations.txt")));
            }
            catch (IOException e) {
                throw new ResourceInitializationException(e);
            }
        }
        
        @Override
        public void process(JCas aJCas)
            throws AnalysisEngineProcessException
        {
            for (Token token : select(aJCas, Token.class)) {
                if (locations.contains(token.getCoveredText())) {
                    new Location(aJCas, token.getBegin(), token.getEnd()).addToIndexes();
                }
            }
        }
    }
}
