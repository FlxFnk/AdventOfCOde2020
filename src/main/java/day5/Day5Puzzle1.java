package day5;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

public class Day5Puzzle1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day5Puzzle1.class.getResource("input.txt").toURI()));

		Set<Integer> seats = new HashSet<>();
		for (int i = 0; i < 127*8; i++) {
			seats.add(i);
		}
		
		lines.stream().mapToInt(s -> {
			System.out.println(s);
			int row = binarySearch(s.substring(0, 7), 0, 127, "F");
			int col = binarySearch(s.substring(7), 0, 7, "L");
			return row*8+col;
		}).forEach(seats::remove);
		
		System.out.println(seats);
	}
	
	private static int binarySearch(String input, int min, int max, String leftDelimiter) {
		if ((input.isEmpty()) && (min == max)) {
			return min;
		}
		
		int m = (min + max) / 2;
		if (input.startsWith(leftDelimiter)) {
			return binarySearch(input.substring(1), min, m, leftDelimiter);
		} else {
			return binarySearch(input.substring(1), m+1, max, leftDelimiter);
		}
	}
	
}
