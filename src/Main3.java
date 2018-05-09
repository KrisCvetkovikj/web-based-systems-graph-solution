import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDFS;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Kristijan Cvetkovikj on 10/23/16.
 */
public class Main3 {
	public static void main(String[] args) {
		Model model = ModelFactory.createDefaultModel();
		model.read("hifm-dataset.ttl", "TURTLE");

		ResIterator resIterator = model.listSubjects();
		List<String> list = new ArrayList<>();

		while (resIterator.hasNext()) {
			Resource res = resIterator.nextResource();
			if (res.hasProperty(RDFS.label)) {
				list.add(res.getProperty(RDFS.label).getString());
			}
		}

		Set<String> noDuplicates = new HashSet<>(list);
		list.clear();
		list.addAll(noDuplicates);
		list.sort(String::compareToIgnoreCase);

		//list.forEach(System.out::println);

		Resource lek = model.getResource("http://purl.org/net/hifm/data#29262");
		lek.listProperties().forEachRemaining(property -> {
			//System.out.printf("%s - %s\n", property.getPredicate(), property.getObject());
		});

		List<String> similarResources = new ArrayList<>();
		lek.listProperties().forEachRemaining(property -> {
			if (property.getPredicate().getLocalName().equals("similarTo")) {
				similarResources.add(property.getSubject().toString());
				//System.out.printf("%s - %s\n", property.getPredicate(), property.getObject());
			}
		});

		final double[] price = {0};

		lek.listProperties().forEachRemaining(property -> {
			if (property.getPredicate().getLocalName().equals("refPriceWithVAT")) {
				price[0] = property.getObject().asLiteral().getDouble();
				System.out.println(property.getObject().asLiteral().getDouble());
			}
		});

		while (resIterator.hasNext()) {
			Resource res = resIterator.nextResource();
			if (res.hasProperty(RDFS.label)) {
				if (!res.getLocalName().equals(lek.getLocalName())) {
					res.listProperties().forEachRemaining(property -> {
						if (property.getPredicate().getLocalName().equals("refPriceWithVAT")) {
							if (property.getObject().asLiteral().getDouble() == price[0]) {
								System.out.println(res);
							}
						}
					});
				}
			}
		}
	}
}
