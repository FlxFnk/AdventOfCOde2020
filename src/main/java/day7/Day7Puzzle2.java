package day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7Puzzle2 {
	private static class ParsedBag {
		public String color;
		public Map<String, Integer> children = new HashMap<>();

		@Override
		public String toString() {
			return "ParsedBag [color=" + color + ", children=" + children + "]";
		}
	}

	private static class Bag {
		public String color;
		public Set<Bag> parents = new HashSet<>();
		public Map<Bag, Integer> children = new HashMap<>();

		public boolean containsShinyGoldBag = false;

		@Override
		public String toString() {

			return "Bag [color=" + color + ", parent="
					+ parents.stream().map(b -> b.color).collect(Collectors.joining(",")) + ", children=["
					+ children.keySet().stream().map(b -> b.color).collect(Collectors.joining(","))
					+ "], containsShinyGoldBag=" + containsShinyGoldBag + "]";
		}
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day7Puzzle2.class.getResource("input.txt").toURI()));

		Map<String, ParsedBag> parsedBags = parseRules(lines);
		System.out.println(parsedBags);

		Map<String, Bag> bags = processParsedBags(parsedBags);
		System.out.println(bags);

		markShinyGoldBags(bags);
		System.out.println(bags);

		bags.values().stream().filter(b -> b.containsShinyGoldBag).forEach(System.out::println);
		System.out.println(bags.values().stream().filter(b -> b.containsShinyGoldBag).count());
		
		Bag shinyGoldBag = bags.get("shiny gold");
		System.out.println(getBagSize(shinyGoldBag, bags));
	}

	private static Map<String, ParsedBag> parseRules(List<String> lines) {
		Map<String, ParsedBag> bags = new HashMap<>();

		for (String line : lines) {
			ParsedBag bag = parseLine(line);
			bags.put(bag.color, bag);
		}

		return bags;
	}

	private static ParsedBag parseLine(String line) {
		String[] s = line.split(" bags contain ");
		String color = s[0];
		String allChildren = s[1];

		ParsedBag bag = new ParsedBag();
		bag.color = color;

		if (!allChildren.equals("no other bags.")) {
			String[] children = allChildren.substring(0, allChildren.length() - 1).split(", ");
			for (String child : children) {
				Pattern pattern = Pattern.compile("(\\d+?) (.+?) bags?");
				Matcher matcher = pattern.matcher(child);

				if (matcher.matches()) {
					int count = Integer.parseInt(matcher.group(1));
					String col = matcher.group(2);
					bag.children.put(col, count);
				} else {
					System.err.println("Pattern " + child + " does not match.");
				}
			}
		}

		return bag;
	}

	private static Map<String, Bag> processParsedBags(Map<String, ParsedBag> parsedBags) {
		Map<String, Bag> bags = new HashMap<>();

		for (ParsedBag parsedBag : parsedBags.values()) {
			Bag bag = processBag(parsedBag, bags, parsedBags);
			bags.put(bag.color, bag);
		}

		return bags;
	}

	private static Bag processBag(ParsedBag parsedBag, Map<String, Bag> bags, Map<String, ParsedBag> parsedBags) {
		Bag bag = bags.get(parsedBag.color);

		if (bag == null) {
			bag = new Bag();
			bag.color = parsedBag.color;

			for (Map.Entry<String, Integer> parsedChild : parsedBag.children.entrySet()) {
				ParsedBag parsedChildBag = parsedBags.get(parsedChild.getKey());
				Bag childBag = processBag(parsedChildBag, bags, parsedBags);

				childBag.parents.add(bag);
				bag.children.put(childBag, parsedChild.getValue());
			}

			bags.put(bag.color, bag);
		}

		return bag;
	}

	private static void markShinyGoldBags(Map<String, Bag> bags) {
		for (Bag bag : bags.values()) {
			if (bag.containsShinyGoldBag) {
				continue;
			}

			if (bag.children.keySet().stream().filter(b -> b.color.equals("shiny gold")).findFirst().isPresent()) {
				bag.containsShinyGoldBag = true;

				markShinyGoldBag(bag, bags);
			}
		}

	}

	private static void markShinyGoldBag(Bag bag, Map<String, Bag> bags) {
		bag.containsShinyGoldBag = true;
		
		bag.parents.stream().forEach(b -> markShinyGoldBag(b, bags));
	}
	
	private static int getBagSize(Bag bag, Map<String, Bag> bags) {
		int count = 0;
		for (Map.Entry<Bag, Integer> child : bag.children.entrySet()) {
			count += child.getValue() * (getBagSize(child.getKey(), bags) + 1);
		}
		
		return count;
	}
}
