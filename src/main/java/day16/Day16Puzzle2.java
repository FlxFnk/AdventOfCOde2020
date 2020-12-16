package day16;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day16Puzzle2 {
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

		@Override
		public String toString() {
			return "Rule [lower=" + lower + ", upper=" + upper + "]";
		}

	}

	public static class TicketRule {
		public String name;
		public Rule rule1;
		public Rule rule2;

		public TicketRule(String name, Rule rule1, Rule rule2) {
			this.name = name;
			this.rule1 = rule1;
			this.rule2 = rule2;
		}

		public boolean validate(int x) {
			return rule1.validate(x) || rule2.validate(x);
		}

		@Override
		public String toString() {
			return "TicketRule [name=" + name + ", rule1=" + rule1 + ", rule2=" + rule2 + "]";
		}

	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day16Puzzle2.class.getResource("input.txt").toURI()));

		Pattern rulePattern = Pattern.compile("(.+?): (\\d+?)-(\\d+?) or (\\d+?)-(\\d+?)");
		List<TicketRule> rules = new ArrayList<>();
		List<Integer> ownTicket = new ArrayList<>();
		List<List<Integer>> tickets = new LinkedList<>();
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
					String name = matcher.group(1);
					int lb1 = Integer.parseInt(matcher.group(2));
					int ub1 = Integer.parseInt(matcher.group(3));
					int lb2 = Integer.parseInt(matcher.group(4));
					int ub2 = Integer.parseInt(matcher.group(5));
					rules.add(new TicketRule(name, new Rule(lb1, ub1), new Rule(lb2, ub2)));
				} else {
					System.err.println("Wrong input: " + line);
				}
			} else if (mode == 1) {
				String[] s = line.split(",");
				for (int i = 0; i < s.length; i++) {
					int value = Integer.parseInt(s[i]);
					ownTicket.add(value);
				}
			} else if (mode == 2) {
				String[] s = line.split(",");
				boolean valid = true;
				List<Integer> ticket = new ArrayList<>(s.length);
				for (int i = 0; i < s.length; i++) {
					int value = Integer.parseInt(s[i]);
					valid &= rules.stream().anyMatch(r -> r.validate(value));
					ticket.add(value);
				}

				if (valid) {
					tickets.add(ticket);
				}
			}
		}

		System.out.println(invalidValues);
		System.out.println(invalidValues.stream().mapToInt(Integer::intValue).sum());

		System.out.println(tickets);
		System.out.println(ownTicket);
		System.out.println(rules);

		tickets.add(ownTicket);

		int[][] candiates = new int[rules.size()][rules.size()];
		for (int i = 0; i < rules.size(); i++) {
			for (int j = 0; j < rules.size(); j++) {
				candiates[i][j] = j;
			}
		}

		printArray(candiates);

		for (List<Integer> ticket : tickets) {
			for (int i = 0; i < ticket.size(); i++) {
				int[] c = candiates[i];
				int value = ticket.get(i);
				for (int j = 0; j < c.length; j++) {
					if (c[j] >= 0) {
						TicketRule rule = rules.get(c[j]);
						if (!rule.validate(value)) {
							c[j] = -1;
						}
					}
				}
			}
		}

		printArray(candiates);

		int[] assignedRules = new int[rules.size()];
		boolean moreWork = true;
		while (moreWork) {
			System.out.println("---");
			moreWork = simplify(candiates, assignedRules);
			printArray(candiates);
		}

		System.out.println(Arrays.toString(assignedRules));
		
		long result = 1;
		for (int i = 0; i < assignedRules.length; i++) {
			int ruleIndex = assignedRules[i];
			TicketRule rule = rules.get(ruleIndex);
			if (rule.name.startsWith("departure")) {
				result *= ownTicket.get(i);
			}
		}
		
		System.out.println(result);
	}

	private static void printArray(int[][] a) {
		for (int[] line : a) {
			System.out.println(Arrays.toString(line));
		}
	}

	private static boolean simplify(int[][] candidates, int[] assignedRules) {
		int colIndex = -1;
		int ruleIndex = -1;

		for (int i = 0; i < candidates.length; i++) {
			int count = 0;
			for (int j = 0; j < candidates[i].length; j++) {
				if (candidates[i][j] >= 0) {
					colIndex = j;
					count++;
				}
			}
			if (count == 1) {
				ruleIndex = colIndex;
				assignedRules[i] = candidates[i][ruleIndex];
			}
		}

		if (ruleIndex >= 0) {
			for (int i = 0; i < candidates.length; i++) {
				candidates[i][ruleIndex] = -1;
			}
			return true;
		} else {
			return false;
		}
	}

}
