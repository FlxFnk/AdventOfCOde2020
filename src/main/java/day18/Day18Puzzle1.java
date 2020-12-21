package day18;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;

public final class Day18Puzzle1 {

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day18Puzzle1.class.getResource("input.txt").toURI()));
		
		long sum = 0;
		for (String line : lines) {
			System.out.println(line);
			LinkedList<Character> l = new LinkedList<>();
			for (char c : line.toCharArray()) {
				l.add(c);
			}
			long result = parseInput(l);
			sum += result;
			System.out.println("Result = " + result);
		}
		
		System.out.println("Sum: " + sum);
	}
	
	private static long parseInput(Queue<Character> input) {
		Long value = null;
		BiFunction<Long, Long, Long> operator = null;
		
		while (! input.isEmpty()) {
			char c = input.poll().charValue();
			Long currentValue = null;
			if (c == '(') {
				currentValue = parseInput(input);
			} else if (c == ')') {
				return value;
			} else if (c == '+') {
				operator = Day18Puzzle1::add;
			} else if (c == '*') {
				operator = Day18Puzzle1::mul;
			} else if (c == ' ') {
				continue;
			} else {
				currentValue = Long.parseLong("" + c);
			}
			
			if (currentValue != null) {
				if (value == null) {
					value = currentValue;
				} else {
					value = operator.apply(value, currentValue);
				}
			}
		}
		
		return value;
	}
	
	private static long add(long x, long y) {
		return x+y;
	}
	
	private static long mul(long x, long y) {
		return x*y;
	}
}
