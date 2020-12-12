package day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day3Puzzle2 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day3Puzzle2.class.getResource("input.txt").toURI()));
		
		int c;
		int m = 1;
		c = getNumberOfTreeHits(1, 1, lines);
		m *= c;
		System.out.println(c);
		c = getNumberOfTreeHits(3, 1, lines);
		m *= c;
		System.out.println(c);
		c = getNumberOfTreeHits(5, 1, lines);
		m *= c;
		System.out.println(c);
		c = getNumberOfTreeHits(7, 1, lines);
		m *= c;
		System.out.println(c);
		c = getNumberOfTreeHits(1, 2, lines);
		m *= c;
		System.out.println(c);
		
		System.out.println("m: " + m);
	}

	private static int getNumberOfTreeHits(int deltaX, int deltaY, List<String> lines) {
		int y = 0;
		int x = 0;
		int treesHit = 0;
		do {
			x += deltaX;
			y += deltaY;
			treesHit += isTree(y, x, lines) ? 1 : 0;
		} while (y < lines.size() - 1);

		return treesHit;
	}

	private static boolean isTree(int y, int x, List<String> lines) {
		String line = lines.get(y);
		return line.charAt(x % line.length()) == '#';
	}
}
