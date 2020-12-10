package day10;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day10Puzzle1 {
	private static Set<String> validSolutions = new HashSet<String>();
	private static Set<String> invalidSolutions = new HashSet<String>();

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day10Puzzle1.class.getResource("input.txt").toURI()));
		List<Long> numbers = lines.stream().map(Long::parseLong).sorted().collect(Collectors.toList());

		numbers.add(0,0L);
		numbers.add(numbers.get(numbers.size()-1)+3);
		
		int[] ways = new int[numbers.size()];
		for (int i = 0; i < ways.length; i++) {
			ways[i] = 0;
		}
		ways[0] = 1;
		for (int i = 0; i < numbers.size(); ++i) {
			for (int j = i+1; j < numbers.size(); ++j) {
				if (numbers.get(j)-numbers.get(i) > 3) {
					break;
				}
				ways[j] += ways[i];
			}
		}
		
		System.out.println(numbers);
		System.out.println(Arrays.toString(ways));
		System.out.println(ways[ways.length-1]);

//		numbers.add(numbers.get(numbers.size() - 1) + 3);
//		calculate(numbers, 0);
//		validSolutions.forEach(System.out::println);
//		System.out.println(validSolutions.size());
	}

	private static void calculate(List<Long> numbers, int index) {
		System.out.println(index + "(" + numbers.size() + "): " + numbers);
		String solution = solutionToString(numbers);
		if (validateSolution(numbers)) {
			validSolutions.add(solution);
		} else {
			invalidSolutions.add(solution);
			return;
		}

		for (int i = index; i < numbers.size()-1; i++) {
			List<Long> nextList = new LinkedList<Long>(numbers);
			nextList.remove(i);
			String nextListAsString = solutionToString(nextList);
			if ((validateSolution(nextList) && (!validSolutions.contains(nextListAsString)))) {
				calculate(nextList, i);
			}
		}
	}

	private static String solutionToString(List<Long> numbers) {
		return numbers.stream().map(l -> Long.toString(l)).collect(Collectors.joining(","));
	}

	private static boolean validateSolution(List<Long> numbers) {
		long last = 0;
		for (long n : numbers) {
			if (n - last <= 3) {
				last = n;
			} else {
				return false;
			}
		}

		return true;
	}
}
