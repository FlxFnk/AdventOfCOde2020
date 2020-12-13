package day13;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import day12.Day12Puzzle1;

public class Day13Puzzle1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day13Puzzle1.class.getResource("input_test.txt").toURI()));
		
		long timestamp = Long.parseLong(lines.get(0));
		List<Long> ids = Stream.of(lines.get(1).split(",")).filter(c -> ! c.equals("x")).map(Long::parseLong).collect(Collectors.toList());
		
		long shortestWait = Integer.MAX_VALUE;
		Long takenId = null; 
		for (long id : ids) {
			long x = (timestamp / id) * id;
			long y = x+id;
			long wait = y % timestamp;
			if (wait < shortestWait) {
				shortestWait = wait;
				takenId = id;
			}
			System.out.println(id + ": " + x + "  " + y + "  " + wait);
		}
		
		System.out.println(" --> " + takenId + ": " + shortestWait + "  --> " + (takenId*shortestWait));
	}
}
