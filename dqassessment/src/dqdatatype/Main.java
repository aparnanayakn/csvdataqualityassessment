package dqdatatype;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.vocabulary.ReasonerVocabulary;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.ValidityReport.Report;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;



public class  Main{
	
	protected static List<Quad> streamingQuads = new ArrayList<Quad>();
	
	public static void main(String[] args) {
		Model ontology = ModelFactory.createDefaultModel();
		ontology.read("/home/d19125691/Experiments/Experiments/csv2rdf/csvtordfgit/csvdataqualityassessment/preprocessing/csvtordf.owl");

		Model model = ModelFactory.createDefaultModel();
		model.read( "/home/d19125691/Experiments/Experiments/csv2rdf/csvtordfgit/csvdataqualityassessment/preprocessing/TTL files/randomData1.ttl" );
		
		Model combinedModel = ModelFactory.createUnion(ontology, model);
				
		Reasoner owlReasoner = ReasonerRegistry.getOWLReasoner();
		owlReasoner = owlReasoner.bindSchema(ontology);

		Reasoner combinedReasoner = new GenericRuleReasoner(Rule.rulesFromURL( "rules.n3" ));
		//((GenericRuleReasoner) reasoner).setOWLTranslation(true);               // not needed in RDFS case
		//((GenericRuleReasoner) reasoner).setTransitiveClosureCaching(true);
		
		combinedReasoner = combinedReasoner.bindSchema(ontology);


		InfModel infModel = ModelFactory.createInfModel(combinedReasoner, combinedModel);

		StmtIterator it = infModel.listStatements(); //lists all the statements mentioned in dataset. 
		
		while ( it.hasNext() )
		{
			 Statement stmt = it.nextStatement();
             if (!model.contains(stmt)) {
                Resource subject = stmt.getSubject();
                Property predicate = stmt.getPredicate();
                RDFNode object = stmt.getObject();
                System.out.println(subject.toString() + " " + predicate.toString() + " " + object.toString());
            }
		}  
		ValidityReport validityReport = infModel.validate();
		if ( !validityReport.isValid() ) {
		    System.out.println("Inconsistent");
		    Iterator<Report> iter = validityReport.getReports();
		    while ( iter.hasNext() ) {
		      Report report = iter.next();
		      System.out.println(report);
		    }            
		  } else {
		    System.out.println("Valid");
		  }
}
}
