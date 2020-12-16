package day16;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day16Puzzle1 {
	public static class Rule {
		public int lower;
		public int upper;

		public Rule(int lower, int upper) {
			this.lower = lower;
			this.upper = upper;
		}

		public boolean validate(int x) {
			return (x >= lower) && (x <= upper);
		}
	}

	public static class TicketRule {
		public Rule rule1;
		public Rule rule2;

		public TicketRule(Rule rule1, Rule rule2) {
			super();
			this.rule1 = rule1;
			this.rule2 = rule2;
		}

		public boolean validate(int x) {
			return rule1.validate(x) || rule2.validate(x);
		}
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day16Puzzle1.class.getResource("input.txt").toURI()));

		Pattern rulePattern = Pattern.compile("(.+?): (\\d+?)-(\\d+?) or (\\d+?)-(\\d+?)");
		List<TicketRule> rules = new ArrayList<>();
		String ownTicket = "";
		List<Integer> tickets = new LinkedList<>();
		List<Integer> invalidValues = new LinkedList<>();

		int mode = 0;
		for (String line : lines) {
			if (line.isEmpty()) {
				continue;
			} else if (line.equals("your ticket:")) {
				System.out.println(rules);
				mode++;
				continue;
			} else if (line.equals("nearby tickets:")) {
				mode++;
				continue;
			}

			if (mode == 0) {
				Matcher matcher = rulePattern.matcher(line);
				if (matcher.matches()) {
					int lb1 = Integer.parseInt(matcher.group(2));
					int ub1 = Integer.parseInt(matcher.group(3));
					int lb2 = Integer.parseInt(matcher.group(4));
					int ub2 = Integer.parseInt(matcher.group(5));
					rules.add(new TicketRule(new Rule(lb1, ub1), new Rule(lb2, ub2)));
				} else {
					System.err.println("Wrong input: " + line);
				}
			} else if (mode == 1) {
			} else if (mode == 2) {
				String[] s = line.split(",");
				for (int i = 0; i < s.length; i++) {
					int value = Integer.parseInt(s[i]);
					if (!rules.stream().anyMatch(r -> r.validate(value))) {
						invalidValues.add(value);
					}
				}
			}
		}

		System.out.println(invalidValues);
		System.out.println(invalidValues.stream().mapToInt(Integer::intValue).sum());
	}

}
