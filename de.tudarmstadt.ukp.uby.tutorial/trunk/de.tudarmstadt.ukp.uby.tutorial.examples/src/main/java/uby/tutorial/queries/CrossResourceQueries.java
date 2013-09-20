package uby.tutorial.queries;

import java.util.Collection;

import de.tudarmstadt.ukp.lmf.api.Uby;
import de.tudarmstadt.ukp.lmf.exceptions.UbyInvalidArgumentException;
import de.tudarmstadt.ukp.lmf.model.core.LexicalEntry;
import de.tudarmstadt.ukp.lmf.model.core.Lexicon;
import de.tudarmstadt.ukp.lmf.model.core.Sense;
import de.tudarmstadt.ukp.lmf.model.enums.EPartOfSpeech;
import de.tudarmstadt.ukp.lmf.model.meta.SemanticLabel;
import de.tudarmstadt.ukp.lmf.model.multilingual.SenseAxis;
import de.tudarmstadt.ukp.lmf.model.semantics.SenseExample;
import de.tudarmstadt.ukp.lmf.transform.DBConfig;

public class CrossResourceQueries {
		
		private static final String uby_url =  "localhost/uby_open_0_3_0";
		private static final String uby_user = "root";
		private static final String uby_pass = "pass";
		
		private static Uby uby;


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

	    uby = new Uby(db);
	    
	    String lemma = "exhibit";	
	    
	    showSenseLinks(lemma);
	    showSemanticLabels(lemma);
	    showWordNetWiktionaryLinks(lemma);
	    
	}


		private static void showWordNetWiktionaryLinks(String lemma) throws UbyInvalidArgumentException {
		    
		    Lexicon wordNet = uby.getLexiconByName("WordNet");
		    String noun = "album";
		    System.out.println("************************************");
		    System.out.println("* WordNet - Wiktionary Sense Links");
		    System.out.println("************************************");
		    
		    for(LexicalEntry le : uby.getLexicalEntries(noun, EPartOfSpeech.noun, wordNet)) {
		    	System.out.println("WordNet senses of " +noun +" are linked to the following Wiktionary senses: ");
		    	for (Sense sense:le.getSenses()) {		    			    		
		    		Collection<SenseAxis> sas = uby.getSenseAxesBySense(sense);
					if (!sas.isEmpty()) {
						printSenseInformation(sense);
						System.out.println("is linked to");
				       	for (SenseAxis sa: sas) {
							if (sa.getSenseOne().getId().matches("WktEN.*")) {
								Sense wktSense = sa.getSenseOne();
								printSenseInformation(wktSense);
							} else if (sa.getSenseTwo().getId().matches("WktEN.*")) {
								Sense wktSense = sa.getSenseTwo();
								printSenseInformation(wktSense);
							}
							
				       	}
				       	System.out.print("\n");
					}		
		    	}
		    } // for le		
		    System.out.print("\n");
		}


		private static void showSemanticLabels(String lemma) {
		    System.out.println("*******************");
		    System.out.println("* Semantic Labels");
		    System.out.println("*******************");

		    for (LexicalEntry lexEntry : uby.getLexicalEntries(lemma, null, null)) {
		    	
		    	System.out.println(lexEntry.getLemmaForm() +", POS = " +lexEntry.getPartOfSpeech() 
		    			+" (Id = " +lexEntry.getId() +"):");
		    	for (Sense sense:lexEntry.getSenses()) {	
		    				    		
		    		Collection<SemanticLabel> semanticLabels = sense.getSemanticLabels();			   
		    		if (!semanticLabels.isEmpty()) {
		    			printSenseInformation(sense);
		    			for (SemanticLabel semLabel : semanticLabels) {
			    			System.out.println("---- Semantic Label, type = "+semLabel.getType() +" and label = "
							   +semLabel.getLabel());
		    			}	
		    			System.out.print("\n");
		    		} else {
		    			printSenseInformation(sense);
		    			System.out.println("No semantic labels for " +sense.getId() +" of "
		    					+lemma +" in " +lexEntry.getLexicon().getName());
		    			System.out.print("\n");
		    		}
		    	}
		    }
		    System.out.print("\n");
		}


		private static void showSenseLinks(String lemma) {
		    System.out.println("********************************");
		    System.out.println("* Senses and their Sense Links");
		    System.out.println("********************************");

			int numberOfSenses = 0;		
		    for (LexicalEntry lexEntry : uby.getLexicalEntries(lemma, null, null)) {
		    	
		    	System.out.println(lexEntry.getLemmaForm() +", POS = " +lexEntry.getPartOfSpeech() 
		    			+", in " +lexEntry.getLexicon().getName()
		    			+" (Id = " +lexEntry.getId() +"):");

		    	for (Sense sense:lexEntry.getSenses()) {
		    		numberOfSenses++;
		    		printSenseInformation(sense);
		    			    		
		    		Collection<SenseAxis> sas = uby.getSenseAxesBySense(sense);
					if (!sas.isEmpty()) {						
						System.out.println("Senses that are linked to " +sense.getId() 
				    			+":");
				       	for (SenseAxis sa: sas) {
				       		if (sa.getSenseOne().getId().equals(sense.getId())) {
				       			printSenseInformation(sa.getSenseTwo());
				       		} else {
				       			printSenseInformation(sa.getSenseOne());
				       		}
				       	}
					} else {
						System.out.println("No sense links");
					}
					System.out.print("\n");
		    	}
		    }
		    System.out.println(lemma +" has " +numberOfSenses +" senses in " +uby_url);
		    System.out.print("\n");
		}


		private static void printSenseInformation(Sense sense) {
    		Collection<SenseExample> senseExamples = sense.getSenseExamples();
    		Collection<SemanticLabel> semanticLabels = sense.getSemanticLabels();	
    		
			System.out.println("- Sense Id: "+sense.getId());
			if (!(sense.getDefinitionText() == null)) {
				System.out.println("---- Sense Definition: "+sense.getDefinitionText());
			} else if (!(sense.getSynset() == null) && !(sense.getSynset().getDefinitionText() == null)) {
				System.out.println("---- Synset Definition: "+sense.getSynset().getDefinitionText());
			} else if (!senseExamples.isEmpty()) {
				System.out.println("---- Sense Example: "+sense.getSenseExamples().get(0).getTextRepresentations().get(0).getWrittenText());
			} else if (!semanticLabels.isEmpty()) {
				System.out.println("---- Semantic Label: "+sense.getSemanticLabels().get(0).getLabel());
			}			
		}



	}