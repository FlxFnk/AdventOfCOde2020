package day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day3Puzzle1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day3Puzzle1.class.getResource("input.txt").toURI()));
		
		int y = 0;
		int x = 0;
		int treesHit = 0;
		do {
			x += 3;
			y += 1;
			treesHit += isTree(y, x, lines) ? 1 : 0;
		} while (y < lines.size()-1);
		
		System.out.println(treesHit);
	}
	
	private static boolean isTree(int y, int x, List<String> lines) {
		String line = lines.get(y);
		return line.charAt(x % line.length()) == '#';
	}
}
