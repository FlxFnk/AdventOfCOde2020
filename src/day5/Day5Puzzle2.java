package day5;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.OptionalInt;

public class Day5Puzzle2 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day5Puzzle2.class.getResource("input.txt").toURI()));

		OptionalInt max = lines.stream().mapToInt(s -> {
			System.out.println(s);
			int row = binarySearch(s.substring(0, 7), 0, 127, "F");
			int col = binarySearch(s.substring(7), 0, 7, "L");
			return row*8+col;
		}).max();
		
		System.out.println(max);
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
