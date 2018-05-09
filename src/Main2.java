import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.vocabulary.VCARD;

import java.io.*;

/**
 * Created by Kristijan Cvetkovikj on 10/23/16.
 */
public class Main2 {
	public static void main(String[] args) throws IOException {

		Model model = ModelFactory.createDefaultModel();
		model.read("hifm-dataset.ttl", "NTRIPLES_UTF8");
		model.listStatements().forEachRemaining(element -> {
			System.out.print(element.getSubject());
			System.out.print(" - ");
			System.out.print("\"" + element.getPredicate() + "\"");
			System.out.print(" - ");
			System.out.println(element.getObject());
		});

		Resource vcard = model.getResource("http://faceburn.com/k1ko.mk");

		System.out.println(vcard.getProperty(VCARD.FN).getObject());
		System.out.println(vcard.getProperty(VCARD.Given).getObject());
		System.out.println(vcard.getProperty(VCARD.Family).getObject());
		System.out.println(vcard.getProperty(VCARD.UID).getLong());
	}
}
