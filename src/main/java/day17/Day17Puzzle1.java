package day17;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

public class Day17Puzzle1 {
	private static class Map {
		private char[][][] map;
		private int sizeX;
		private int sizeY;
		private int sizeZ;

		public Map(List<String> lines) {
			map = new char[1][lines.size()][];

			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				map[0][i] = line.toCharArray();
			}

			sizeX = map[0][0].length;
			sizeY = map[0].length;
			sizeZ = 1;
		}

		public Map(Map other) {
			this.sizeX = other.sizeX + 2;
			this.sizeY = other.sizeY + 2;
			this.sizeZ = other.sizeZ + 2;

			map = new char[sizeZ][sizeY][sizeX];
			for (int z = 0; z < sizeZ; z++) {
				for (int y = 0; y < sizeY; y++) {
					for (int x = 0; x < sizeX; x++) {
						map[z][y][x] = '.';
					}
				}
			}

			for (int z = 0; z < other.sizeZ; z++) {
				for (int y = 0; y < other.sizeY; y++) {
					System.arraycopy(other.map[z][y], 0, map[z + 1][y + 1], 1, other.sizeX);
				}
			}
		}

		public int count(int x, int y, int z) {
			int count = 0;

			for (int dz = z - 1; dz <= z + 1; dz++) {
				for (int dy = y - 1; dy <= y + 1; dy++) {
					for (int dx = x - 1; dx <= x + 1; dx++) {
						if ((dz >= 0) && (dz < sizeZ) && (dy >= 0) && (dy < sizeY) && (dx >= 0) && (dx < sizeX)) {
							if ((dz == z) && (dy == y) && (dx == x)) {
								continue;
							}

							if (map[dz][dy][dx] == '#') {
								count++;
							}
						}
					}
				}
			}
			return count;
		}

		public void forEach(Consumer<Character> f) {
			for (int z = 0; z < sizeZ; z++) {
				for (int y = 0; y < sizeY; y++) {
					for (int x = 0; x < sizeX; x++) {
						f.accept(map[z][y][x]);
					}
				}
			}
		}

		public int countActive() {
			AtomicInteger count = new AtomicInteger();
			forEach(c -> {
				if (c == '#') {
					count.incrementAndGet();
				}
			});

			return count.get();
		}

		public void set(int x, int y, int z, char c) {
			map[z][y][x] = c;
		}

		public void step() {
			char[][][] stepMap = new char[sizeZ][sizeY][sizeX];

			for (int z = 0; z < sizeZ; z++) {
				for (int y = 0; y < sizeY; y++) {
					for (int x = 0; x < sizeX; x++) {
						char c = map[z][y][x];
						int count = count(x, y, z);
						if (c == '#') {
							if ((count < 2) || (count > 3)) {
								c = '.';
							}
						} else {
							if (count == 3) {
								c = '#';
							}
						}
						stepMap[z][y][x] = c;
					}
				}
			}

			map = stepMap;
		}

		public String toString() {
			StringBuilder b = new StringBuilder();

			for (int z = 0; z < sizeZ; z++) {
				b.append("z=");
				b.append(z);
				b.append('\n');
				b.append(toString(z));
				b.append("\n\n");
			}

			return b.toString();
		}

		private String toString(int z) {
			StringBuilder b = new StringBuilder();
			for (int y = 0; y < sizeY; y++) {
				for (int x = 0; x < sizeX; x++) {
					b.append(map[z][y][x]);
				}
				b.append('\n');
			}

			return b.toString();
		}

	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day17Puzzle1.class.getResource("input.txt").toURI()));

		Map map = new Map(lines);
		System.out.println(map);
		for (int i = 0; i < 6; i++) {
			map = new Map(map);
			map.step();
			System.out.println(map);
			System.out.println(map.countActive());

		}
	}
}
