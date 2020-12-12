package day9;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import day8.Day8Puzzle1;

public class Day9Puzzle1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day9Puzzle1.class.getResource("input.txt").toURI()));
		List<Long> numbers = lines.stream().map(Long::parseLong).collect(Collectors.toList());
		
		int result = checkInput(numbers, 26);
		System.out.println(result + "->" + numbers.get(result));
	}

	private static int checkInput(List<Long> values, int preamble) {
		for (int i = preamble; i < values.size(); i++) {
			if (!validateNumber(values.get(i), values.subList(i - preamble, i))) {
				return i;
			}
		}
		return -1;
	}

	private static boolean validateNumber(long number, List<Long> values) {
		for (long x : values) {
			for (long y : values) {
				if ((x + y == number) && (x != y)) {
					return true;
				}
			}
		}

		return false;
	}

}
