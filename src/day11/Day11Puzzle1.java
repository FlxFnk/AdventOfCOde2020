package day11;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import day10.Day10Puzzle1;

public class Day11Puzzle1 {
	private static class Map {
		public static final char FLOOR = '.';
		public static final char SEAT = 'L';
		public static final char PERSON = '#';

		private char[][] map;
		private int width;
		private int height;

		public Map(List<String> lines) {
			map = new char[lines.size()][];

			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				map[i] = line.toCharArray();
			}

			width = map[0].length;
			height = map.length;
		}

		public Map(Map other) {
			map = new char[other.map.length][];

			for (int y = 0; y < map.length; y++) {
				map[y] = new char[other.map[y].length];
				System.arraycopy(other.map[y], 0, map[y], 0, other.map[y].length);
			}

			width = other.width;
			height = other.height;
		}

		public int count(int x, int y, char s) {
			int pos[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
			int count = 0;

			for (int[] row : pos) {
				char c = trace(x, y, row[0], row[1]);
				if (c == s) {
					count++;
				}
			}

			return count;
		}

		private char trace(int x, int y, int dx, int dy) {
			int cy = y + dy;
			int cx = x + dx;
			while ((cx >= 0) && (cx < width) && (cy >= 0) && (cy < height)) {
				char c = map[cy][cx];
				if (c != FLOOR) {
					return c;
				}
				
				cx += dx;
				cy += dy;
			}

			return FLOOR;
		}

		public char getSeat(int x, int y) {
			return map[y][x];
		}

		public void setSeat(int x, int y, char c) {
			map[y][x] = c;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public Map nextMap() {
			Map newMap = new Map(this);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					char c = getSeat(x, y);
					if (c == SEAT) {
						int persons = count(x, y, PERSON);
						if (persons == 0) {
							newMap.setSeat(x, y, PERSON);
						}
					} else if (c == PERSON) {
						int persons = count(x, y, PERSON);
						if (persons >= 5) {
							newMap.setSeat(x, y, SEAT);
						}
					}
				}
			}

			return newMap;
		}

		public int count(char s) {
			int count = 0;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (map[y][x] == s) {
						count++;
					}
				}
			}

			return count;
		}

		public String toString() {
			StringBuilder b = new StringBuilder();
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					b.append(map[y][x]);
				}
				b.append('\n');
			}

			return b.toString();
		}

		public boolean equals(Map other) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (map[y][x] != other.map[y][x]) {
						return false;
					}
				}
			}

			return true;
		}
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day11Puzzle1.class.getResource("input.txt").toURI()));

		Map map = new Map(lines);
		boolean found = false;
		int count = 1;
		while (!found) {
			Map newMap = map.nextMap();
			if (map.equals(newMap)) {
				found = true;
				System.out.println(" --> " + count + "  " + map.count(Map.PERSON));
			}
			count++;
			map = newMap;

			System.out.println(count + "  " + map.count(Map.PERSON));
		}
	}

}
