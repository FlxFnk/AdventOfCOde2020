package day13;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day13Puzzle2 {
	private static class Bus {
		public int id;
		public int index;
		public int modulo;

		public Bus(int id, int index, int modulo) {
			this.id = id;
			this.index = index;
			this.modulo = modulo;
		}

		@Override
		public String toString() {
			return "Bus [id=" + id + ", index=" + index + ", modulo=" + modulo + "]";
		}
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day13Puzzle2.class.getResource("input.txt").toURI()));

		List<Bus> busses = new ArrayList<>();
		int index = 0;
		for (String bus : lines.get(1).split(",")) {
			if (!bus.equals("x")) {
				int id = Integer.parseInt(bus);
				int modulo = (id - index) % id;
				while (modulo < 0) {
					modulo += id;
				}
				busses.add(new Bus(id, index, modulo));
			}
			index++;
		}

		System.out.println(busses);

		long skip = 1L;
		long t = busses.get(0).id;
		for (Bus bus : busses) {
			while (t % bus.id != bus.modulo) {
				t += skip;
			}

			System.out.println(bus + " t: " + t + "  skip=" + skip);
			skip *= bus.id;
		}

		System.out.println(t);
	}
}
