package day21;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Set;

public class Day21Puzzle2 {

	private static class Rule {
		Set<String> ingredients = new HashSet<>();
		Set<String> allergens = new HashSet<>();
		@Override
		public String toString() {
			return "Rule [ingredients=" + ingredients + ", allergens=" + allergens + "]";
		}
	}
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day21Puzzle2.class.getResource("input.txt").toURI()));
		
		List<Rule> rules = parseInput(lines);
		rules.forEach(System.out::println);
		
		Set<String> allIngredients = rules.stream().map(r->r.ingredients).flatMap(i -> i.stream()).collect(Collectors.toSet());
		System.out.println("All: " + allIngredients);
		
		Map<String, String> m = filterRules(rules);
		allIngredients.removeAll(m.values());
		System.out.println("Unknown: " + allIngredients);
		
		System.out.println(rules.stream().map(r->r.ingredients).flatMap(i -> i.stream()).filter(allIngredients::contains).count());
		
		List<String> cdil = new ArrayList(m.keySet());
		Collections.sort(cdil);
		System.out.println(cdil.stream().map(m::get).collect(Collectors.joining(",")));
	}
	
	private static List<Rule> parseInput(List<String> lines) {
		List<Rule> rules = new LinkedList<>();
		
		for (String line : lines) {
			String[] l = line.substring(0, line.length()-1).split(" \\(contains ");
			String[] ingredients = l[0].split(" ");
			String[] allergens = l[1].split(", ");
			
			Rule rule = new Rule();
			rule.ingredients.addAll(Arrays.asList(ingredients));
			rule.allergens.addAll(Arrays.asList(allergens));
			
			rules.add(rule);
		}
		
		return rules;
	}
	
	private static Map<String, String> filterRules(List<Rule>rules) {
		Map<String, List<String>> allergenToIngredients = new HashMap<>();
		
		for (Rule rule : rules) {
			for (String allergen : rule.allergens) {
				List<String> possibleIngredients = allergenToIngredients.get(allergen);
				if (possibleIngredients == null) {
					possibleIngredients = new LinkedList<>(rule.ingredients);
					allergenToIngredients.put(allergen, possibleIngredients);
				} else {
					possibleIngredients.retainAll(rule.ingredients);
				}
			}
		}
		
		System.out.println(allergenToIngredients);
		
		Map<String, String> result = new HashMap<>();
		while (!allergenToIngredients.isEmpty()) {
			Optional<Entry<String, List<String>>> allergenWithOneIngredient = allergenToIngredients.entrySet().stream().filter(e -> e.getValue().size() == 1).findFirst();
			if (allergenWithOneIngredient.isEmpty()) {
				System.out.println("No single allergen found.");
				System.exit(0);
			} else {
				String allergen = allergenWithOneIngredient.get().getKey();
				String ingredient = allergenWithOneIngredient.get().getValue().get(0);
				result.put(allergen, ingredient);
				allergenToIngredients.remove(allergen);
				
				allergenToIngredients.forEach((k,v) -> v.remove(ingredient));
			}
		}
		
		System.out.println(result);
		return result;
	}

}
