package uby.tutorial.queries;

import java.util.Collection;

import de.tudarmstadt.ukp.lmf.api.Uby;
import de.tudarmstadt.ukp.lmf.model.core.LexicalEntry;
import de.tudarmstadt.ukp.lmf.model.core.Lexicon;
import de.tudarmstadt.ukp.lmf.model.core.Sense;
import de.tudarmstadt.ukp.lmf.model.enums.ELanguageIdentifier;
import de.tudarmstadt.ukp.lmf.model.enums.EPartOfSpeech;
import de.tudarmstadt.ukp.lmf.model.meta.SemanticLabel;
import de.tudarmstadt.ukp.lmf.model.mrd.Equivalent;
import de.tudarmstadt.ukp.lmf.model.multilingual.SenseAxis;
import de.tudarmstadt.ukp.lmf.model.semantics.SenseExample;
import de.tudarmstadt.ukp.lmf.transform.DBConfig;

public class CrossResourceQueries {
		
		private static final String uby_url =  "localhost/uby_open_0_3_0";
		private static final String uby_user = "root";
		private static final String uby_pass = "pass";


		public static void main(String[] args) throws Exception
	{
	    DBConfig db = new DBConfig(
	            uby_url,
	            "com.mysql.jdbc.Driver",
	            "mysql",
	            uby_user ,
	            uby_pass,
	            false
	    );

	    Uby uby = new Uby(db);

	    for (LexicalEntry lexEntry : uby.getLexicalEntries("sing", null, null)) {
	    	System.out.println(lexEntry.getLemmaForm() +" with Id " +lexEntry.getId() +" :");
	    	for (Sense sense:lexEntry.getSenses()) {
				System.out.println("- Sense Definition: "+sense.getDefinitionText());
				// the following can throw an exception
			   Collection<SenseExample> senseExamples = sense.getSenseExamples();
			   if (!senseExamples.isEmpty()) {
				   System.out.println("- Sense Example: "+sense.getSenseExamples().get(0).getTextRepresentations().get(0).getWrittenText());
			   }
			   Collection<SemanticLabel> semanticLabels = sense.getSemanticLabels();
			   if (!semanticLabels.isEmpty()) {
				   System.out.println("- Semantic Label: "+sense.getSemanticLabels().get(0).getType() +": "
						   +sense.getSemanticLabels().get(0).getLabel());
					
			   }

	    		for (Equivalent eq: sense.getEquivalents()){
	                if
	(eq.getLanguageIdentifier().equals(ELanguageIdentifier.GERMAN)){
	                    
	                    System.out.println("German translation : " +eq.getWrittenForm());
	                }
	            }
	    		
	    		Collection<SenseAxis> sas = uby.getSenseAxesBySense(sense);
				if (!sas.isEmpty()) {
			       	for (SenseAxis sa: sas) {
			       		System.out.print(sa.getSenseOne().getId() +" " +sa.getSenseOne().getLexicalEntry().getLemmaForm() +" is linked to ");
			       		System.out.println(sa.getSenseTwo().getId() +" " +sa.getSenseTwo().getLexicalEntry().getLemmaForm());
			       	}
				}

	    	}
	    }

	    Lexicon wordNet = uby.getLexiconByName("WordNet");
	    String lemma = "album";
	    for(LexicalEntry le : uby.getLexicalEntries(lemma, EPartOfSpeech.noun, wordNet)) {
	    	for (Sense sense:le.getSenses()) {
	    		Collection<SenseAxis> sas = uby.getSenseAxesBySense(sense);
				if (!sas.isEmpty()) {
			       	for (SenseAxis sa: sas) {
						if (sa.getSenseOne().getId().matches("WktEN.*")) {
							Sense wktSense = sa.getSenseOne();
							System.out.println("WordNet sense " +sense.getId() +" of " +lemma +" is linked to the Wiktionary sense " +wktSense.getLexicalEntry().getLemmaForm()
									+" " +wktSense.getId());
						} else if (sa.getSenseTwo().getId().matches("WktEN.*")) {
							Sense wktSense = sa.getSenseTwo();
							System.out.println("WordNet sense " +sense.getId() +" of " +lemma +" is linked to the Wiktionary sense " +wktSense.getLexicalEntry().getLemmaForm()
									+" " +wktSense.getId());
						}
			       	}
				}
	
	    	}

	    } // for le

	}



	}
