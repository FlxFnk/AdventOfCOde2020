package day6;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import day5.Day5Puzzle1;

public class Day6Puzzle1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day6Puzzle1.class.getResource("input.txt").toURI()));
		
		List<Set<Integer>> answers = parseInput(lines);
		System.out.println(answers.stream().mapToInt(Set::size).sum());
	}
	
	private static List<Set<Integer>> parseInput(List<String> lines) {
		Set<Integer> currentSet = createFullSet();
		List<Set<Integer>> result = new LinkedList<>();
		
		for (String line : lines) {
			if (! line.strip().isEmpty()) {
				Set<Integer> s = new HashSet<>();
				line.chars().forEach(s::add);
				currentSet.retainAll(s);
			} else {
				result.add(currentSet);
				currentSet = createFullSet();
			}
		}
		
		if (!currentSet.isEmpty()) {
			result.add(currentSet);
		}
		
		return result;
	}
	
	private static Set<Integer> createFullSet() {
		Set<Integer> set = new HashSet<>();
		for (char c = 'a'; c <= 'z'; c++) {
			set.add((int) c);
		}
		
		return set;
	}

}
