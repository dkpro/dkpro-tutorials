package uby.tutorial.queries;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import de.tudarmstadt.ukp.lmf.api.Uby;
import de.tudarmstadt.ukp.lmf.model.core.LexicalEntry;
import de.tudarmstadt.ukp.lmf.model.core.Lexicon;
import de.tudarmstadt.ukp.lmf.model.core.Sense;
import de.tudarmstadt.ukp.lmf.model.enums.ELabelTypeSemantics;
import de.tudarmstadt.ukp.lmf.model.enums.EPartOfSpeech;
import de.tudarmstadt.ukp.lmf.model.meta.SemanticLabel;
import de.tudarmstadt.ukp.lmf.model.semantics.SemanticArgument;
import de.tudarmstadt.ukp.lmf.model.semantics.SynSemArgMap;
import de.tudarmstadt.ukp.lmf.model.syntax.SubcategorizationFrame;
import de.tudarmstadt.ukp.lmf.model.syntax.SyntacticArgument;
import de.tudarmstadt.ukp.lmf.model.syntax.SyntacticBehaviour;
import de.tudarmstadt.ukp.lmf.transform.DBConfig;

public class SingleResourceQueries {

	private static final String uby_url =  "localhost/uby_open_0_3_0";
	private static final String uby_user = "root";
	private static final String uby_pass = "pass";
	
	private static Session session;
	private static HashMap<SyntacticArgument, SemanticArgument> SynargSemargMap  = new HashMap<SyntacticArgument, SemanticArgument>();

	private static String verbLemma = "prefer";


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
		session = uby.getSession();
		
		
		Criteria criteriaSynSem = session.createCriteria(SynSemArgMap.class);
		List<SynSemArgMap> SynSemArgMaps = criteriaSynSem.list(); // retrieve complete SynSemArgMap table
		for (SynSemArgMap synSem : SynSemArgMaps) {
			SynargSemargMap.put(synSem.getSyntacticArgument(), synSem.getSemanticArgument());
		}

		Lexicon wordNet = uby.getLexiconByName("WordNet");
	    String lemma = "student";
	    for(LexicalEntry le : uby.getLexicalEntries(lemma, EPartOfSpeech.noun, wordNet)) {
	    	System.out.println(le.getLemmaForm() +" in WordNet:");
	    	for (Sense sense:le.getSenses()) {
	    		System.out.println("- WordNet sense ID: "+sense.getMonolingualExternalRefs().get(0).getExternalReference());	    	}

	    } // for le

	    
		Lexicon verbNet = uby.getLexiconByName("VerbNet");
		for(LexicalEntry le : uby.getLexicalEntries(verbLemma, EPartOfSpeech.verb, verbNet)){
			System.out.println("LexicalEntry: "+le.getId());
			String lemma = le.getLemmaForm();
			System.out.println("Lemma: "+lemma);
			for(Sense sense : le.getSenses()){				
				System.out.println("- Sense: "+sense.getId());
				System.out.println("- Example: "+sense.getSenseExamples().get(0).getTextRepresentations().get(0).getWrittenText());
				
				for (SyntacticBehaviour syntBeh : le.getSyntacticBehaviours()) {
					if (syntBeh.getSense().equals(sense)) {
						SubcategorizationFrame scf = syntBeh.getSubcategorizationFrame();
						for (SyntacticArgument synArg: scf.getSyntacticArguments()) {
							if (SynargSemargMap.containsKey(synArg)) {							
								SemanticArgument semArg = SynargSemargMap.get(synArg);
								System.out.println("\t" +synArg.getSyntacticCategory() +", " +semArg.getSemanticRole());
								
								Collection<SemanticLabel> semanticLabels = semArg.getSemanticLabels();							   
								if (!semanticLabels.isEmpty()) {
								   for (SemanticLabel semLabel : semanticLabels) {
									   if (semLabel.getType().equals(ELabelTypeSemantics.selectionalPreference)) {										
										   System.out.println("Selectional preference: " +semLabel.getLabel());
										}
								   }									
								}
							}
						}
					}
				}//for
			}//for			
		}
	}

}
