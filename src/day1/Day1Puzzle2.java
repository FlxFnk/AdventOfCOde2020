package day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day1Puzzle2 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day1Puzzle2.class.getResource("input.txt").toURI()));
		List<Integer> ints = lines.stream().map(Integer::parseInt).collect(Collectors.toList());

		for (int x : ints) {
			for (int y : ints) {
				for (int z : ints) {
					if (x + y +z  == 2020) {
						System.out.println(x + " + " + y + " + " + z + " = 2020");
						System.out.println(x + " * " + y + " * " + z + " = " + (x*y*z));
					}
				}
			}
		}
	}
}
