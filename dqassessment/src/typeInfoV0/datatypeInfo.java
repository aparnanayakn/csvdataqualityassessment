package typeInfoV0;

import java.util.Iterator;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.ValidityReport.Report;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import org.apache.jena.reasoner.rulesys.Rule;

public class datatypeInfo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = ModelFactory.createDefaultModel();
		model.read( "agri.rdf" );
		
		Reasoner reasoner = new GenericRuleReasoner( Rule.rulesFromURL("rules.n3") );
		
		InfModel infModel = ModelFactory.crea	teInfModel( reasoner, model );

		
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
/*		StmtIterator it = infModel.listStatements(); //lists all the statements mentioned in dataset. 
		
		while ( it.hasNext() )
		{
			Statement stmt = it.nextStatement();
			
			Resource subject = stmt.getSubject();
			Property predicate = stmt.getPredicate();
			RDFNode object = stmt.getObject();
 
			System.out.println( subject.toString() + " " + predicate.toString() + " " + object.toString() );
	}  */
		}

}
