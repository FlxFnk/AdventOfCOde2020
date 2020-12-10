package day10;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day10Puzzle2 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day10Puzzle2.class.getResource("input.txt").toURI()));
		List<Long> numbers = lines.stream().map(Long::parseLong).sorted().collect(Collectors.toList());
		
		long last= 0;
		int c1 = 0;
		int c3 = 0;
		for (long n : numbers) {
			if (n-last == 1) {
				c1++;
			} else if (n-last == 3) {
				c3++;
			} else {
				System.err.println("Invalid input: " + n);
			}
			
			last=n;
		}
		
		c3++;
		
		System.out.println("c1=" + c1 + "  c3=" + c3 + "   c1*c3=" + (c1*c3));
	}
}
