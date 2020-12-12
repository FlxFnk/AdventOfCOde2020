package day12;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day12Puzzle1 {
	private static int x = 0;
	private static int y = 0;
	private static int d = 0;

	private static char[] directions = { 'E', 'S', 'W', 'N' };

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day12Puzzle1.class.getResource("input.txt").toURI()));

		for (String line : lines) {
			char direction = line.charAt(0);
			int amount = Integer.parseInt(line.substring(1));

			if (direction == 'L') {
				d -= amount / 90;
			} else if (direction == 'R') {
				d += amount / 90;
			} else if (direction == 'F') {
				move(directions[d], amount);
			} else {
				move(direction, amount);
			}
			
			while (d < 0) {
				d += directions.length;
			}
			while (d >= directions.length) {
				d -= directions.length;
			}
			
			System.out.println(x + "/" + y + "/" + directions[d]);
		}
		
		System.out.println(Math.abs(x)+Math.abs(y));
	}

	private static void move(char direction, int amount) {
		switch (direction) {
		case 'N':
			y += amount;
			break;
		case 'S':
			y -= amount;
			break;
		case 'E':
			x += amount;
			break;
		case 'W':
			x -= amount;
			break;
		default:
			System.err.println("Unknown direction: " + direction);
		}
	}
}
