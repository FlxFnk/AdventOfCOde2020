package day12;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day12Puzzle2 {
	private static int x = 0;
	private static int y = 0;
	private static int wx = 10;
	private static int wy = 1;

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day12Puzzle2.class.getResource("input.txt").toURI()));

		for (String line : lines) {
			char direction = line.charAt(0);
			int amount = Integer.parseInt(line.substring(1));

			if (direction == 'L') {
				for (int i = 0; i < amount / 90; i++) {
					rotateWaypointLeft();
				}
			} else if (direction == 'R') {
				for (int i = 0; i < amount / 90; i++) {
					rotateWaypointRight();
				}
			} else if (direction == 'F') {
				x += wx * amount;
				y += wy * amount;
			} else {
				move(direction, amount);
			}

			System.out.println(x + "/" + y + "  " + wx + "/" + wy );
		}

		System.out.println(Math.abs(x) + Math.abs(y));
	}

	private static void rotateWaypointLeft() {
		int ox = wx;
		int oy = wy;

		wx = -oy;
		wy = ox;
	}

	private static void rotateWaypointRight() {
		int ox = wx;
		int oy = wy;

		wx = oy;
		wy = -ox;
	}

	private static void move(char direction, int amount) {
		switch (direction) {
		case 'N':
			wy += amount;
			break;
		case 'S':
			wy -= amount;
			break;
		case 'E':
			wx += amount;
			break;
		case 'W':
			wx -= amount;
			break;
		default:
			System.err.println("Unknown direction: " + direction);
		}
	}
}
