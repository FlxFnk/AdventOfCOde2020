package day9;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import day8.Day8Puzzle1;

public class Day9Puzzle2 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day9Puzzle2.class.getResource("input.txt").toURI()));
		List<Long> numbers = lines.stream().map(Long::parseLong).collect(Collectors.toList());

		int index = checkInput(numbers, 26);
		long number = numbers.get(index);
		System.out.println(index + "->" + number);

		int startIndex = 0;
		while (addNumbers(startIndex, number, numbers) == false) {
			startIndex++;
		}
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

	private static boolean addNumbers(int start, long target, List<Long> values) {
		long sum = 0;
		for (int i = start; i < values.size(); i++) {
			sum += values.get(i);
			if (sum == target) {
				List<Long> sumList = values.subList(start, i+1);
				long min = sumList.stream().mapToLong(l -> l).min().getAsLong();
				long max = sumList.stream().mapToLong(l -> l).max().getAsLong();
				long result = min + max;
				System.out.println(
						"Found: start=" + start + "(" + values.get(start) + ") end= " + i + "(" + values.get(i) + ") result= " + result);
				return true;
			} else if (sum > target) {
				return false;
			}
		}

		return false;
	}
}
