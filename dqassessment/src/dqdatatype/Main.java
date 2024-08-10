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
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.ValidityReport.Report;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

public class  Main{
	
	protected static List<Quad> streamingQuads = new ArrayList<Quad>();
	
	public static void main(String[] args) {
		Model model = ModelFactory.createDefaultModel();
		model.read( "/home/d19125691/Experiments/Experiments/csv2rdf/csv2rdfworking/preprocessing/TTL files/randomData2.ttl" );
		
		Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL( "rules.n3" ));
		//((GenericRuleReasoner) reasoner).setOWLTranslation(true);               // not needed in RDFS case
		//((GenericRuleReasoner) reasoner).setTransitiveClosureCaching(true);

		InfModel infModel = ModelFactory.createInfModel( reasoner, model );
 
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
		
		ValidityReport validity = infModel.validate();
		if (validity.isValid()) {
		    System.out.println("OK");
		} else {
		    System.out.println("Conflicts");
		    for (Iterator i = validity.getReports(); i.hasNext(); ) {
		        System.out.println(" - " + i.next());
		    }
		}
}
}
