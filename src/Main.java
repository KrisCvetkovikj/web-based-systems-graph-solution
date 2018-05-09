import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD;

public class Main {

	public static void main(String[] args) {
		Model m = ModelFactory.createDefaultModel();

		String personUri = "http://faceburn.com/k1ko.mk";
		String firstName = "Kristijan";
		String lastName = "Cvetkovikj";
		String fullName = firstName + " " + lastName;
		String address = "Nace Dimov 96a";
		String city = "Veles";
		String country = "Makedonija";
		String occupation = "Student";
		int studentID = 121002;
		String faculty = "FINKI";

		Resource r = m.createResource(personUri)
				.addProperty(VCARD.FN, fullName)
				.addProperty(VCARD.N, m.createResource(personUri)
						.addProperty(VCARD.Given, firstName).addProperty(VCARD.Family, lastName))
				.addProperty(VCARD.ADR, address)
				.addProperty(VCARD.Other, city)
				.addProperty(VCARD.Country, country)
				.addProperty(VCARD.Other, occupation)
				.addLiteral(VCARD.UID, studentID)
				.addProperty(VCARD.EMAIL, "cvetkovikj.kristijan@finki.ukim.mk")
				.addProperty(VCARD.Other, faculty);

		System.out.println("Printing with m.listStatements():");
		StmtIterator it = m.listStatements();

		it.forEachRemaining(element -> {
			System.out.print(element.getSubject());
			System.out.print(" - ");
			System.out.print("\"" + element.getPredicate() + "\"");
			System.out.print(" - ");
			System.out.println(element.getObject());
		});

		RDFFormat[] rdfFormats = {
				RDFFormat.RDFXML_PLAIN,
				RDFFormat.RDFXML_PRETTY,
				RDFFormat.NTRIPLES_UTF8,
				RDFFormat.TURTLE
		};

		for (RDFFormat rdfFormat : rdfFormats) {
			System.out.println("Printing with model.print(), in " + rdfFormat.toString());
			RDFDataMgr.write(System.out, m, rdfFormat);
		}
	}
}
