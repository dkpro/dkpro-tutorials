package de.tudarmstadt.ukp.tutorial.gscl2013.slides.examples;

import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.factory.TypePrioritiesFactory.*;
import static org.apache.uima.util.CasCreationUtils.*;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.metadata.TypePriorities;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.tudarmstadt.ukp.tutorial.gscl2013.slides.Paragraph;
import de.tudarmstadt.ukp.tutorial.gscl2013.slides.Sentence;
import de.tudarmstadt.ukp.tutorial.gscl2013.slides.Token;

/**
 * Examples for the effect of using type priorities.
 * 
 * @author Richard Eckart de Castilho
 */
public class TypePrioritiesExample
{
    @Test
    public void bigToSmallExample()
        throws Exception
    {
        TypePriorities prio = createTypePriorities(Paragraph.class, Sentence.class, Token.class);
        JCas jcas = createCas(createTypeSystemDescription(), prio, null).getJCas();
        populateCas(jcas);

        for (Annotation a : select(jcas, Annotation.class)) {
            System.out.println("[" + a.getType().getShortName() + "|" + a.getBegin() + "-"
                    + a.getEnd() + "] " + a.getCoveredText());
        }
    }

    @Test
    public void smallToBigExample()
        throws Exception
    {
        TypePriorities prio = createTypePriorities(Token.class, Sentence.class, Paragraph.class);
        JCas jcas = createCas(createTypeSystemDescription(), prio, null).getJCas();
        populateCas(jcas);

        for (Annotation a : select(jcas, Annotation.class)) {
            System.out.println("[" + a.getType().getShortName() + "|" + a.getBegin() + "-"
                    + a.getEnd() + "] " + a.getCoveredText());
        }
    }

    @Test
    public void withoutPriosExample()
        throws Exception
    {
        JCas jcas = createCas(createTypeSystemDescription(), null, null).getJCas();
        populateCas(jcas);

        for (Annotation a : select(jcas, Annotation.class)) {
            System.out.println("[" + a.getType().getName() + "|" + a.getBegin() + "-" + a.getEnd()
                    + "] " + a.getCoveredText());
        }
    }

    private void populateCas(JCas jcas)
    {
        jcas.setDocumentText("This is a test.");

        // Add some annotations
        new Token(jcas, 0, 4).addToIndexes();
        new Token(jcas, 5, 7).addToIndexes();
        new Token(jcas, 8, 9).addToIndexes();
        new Token(jcas, 10, 14).addToIndexes();
        new Token(jcas, 14, 15).addToIndexes();
        new Sentence(jcas, 0, 15).addToIndexes();
        new Paragraph(jcas, 0, 15).addToIndexes();
    }

    @Rule
    public TestName name = new TestName();

    @Before
    public void printSeparator()
    {
        System.out.println("\n=== " + name.getMethodName() + " =====================");
    }
}
