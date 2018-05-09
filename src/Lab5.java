import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDFS;

/**
 * Created by kiko_c on 12/18/16.
 */
public class Lab5 {
    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();

        model.read("https://www.dropbox.com/s/gz70kh4h6fzl21f/foaf.ttl?dl=1", "TTL");

        model.listObjects().forEachRemaining(System.out::println);
    }
}
