import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDFS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiko_c on 12/4/16.
 */
public class Lab4Zad2 {
    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        // added to project
        model.read("hifm-dataset.ttl", "NTRIPLES_UTF8");

        String resourceURI = "http://wifo5-04.informatik.uni-mannheim.de/drugbank/resource/drugbank/";

        Property brandName = model.createProperty(resourceURI, "brandName");
        Property genericName = model.createProperty(resourceURI, "genericName");
        Property pharmacology = model.createProperty(resourceURI, "pharmacology");

        List<RDFNode> subjects = new ArrayList<>();
        model.listSubjects().forEachRemaining(subjects::add);
        //subjects.forEach(System.out::println);

        Resource resource = model.getResource("http://purl.org/net/hifm/data#988006");

        StmtIterator stmtIterator = resource.listProperties(RDFS.seeAlso);
        StringBuilder sb = new StringBuilder();

        while(stmtIterator.hasNext()) {
            Statement stmt = stmtIterator.nextStatement();
            RDFNode object = stmt.getObject();
            if(object instanceof Resource) {
                String drugURI = object.toString();
                drugURI = drugURI.replace("resource", "data");
                sb.append(drugURI).append('\n');
                System.out.print("\t" + drugURI);

                Model drugModel = ModelFactory.createDefaultModel();
                drugModel.read(drugURI, "TTL");

                sb.append("Brand name: ").append(resource.getProperty(brandName).getString());
                sb.append("Generic name: ").append(resource.getProperty(genericName).getString());
                sb.append("Pharmacology name: ").append(resource.getProperty(pharmacology).getString());
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
