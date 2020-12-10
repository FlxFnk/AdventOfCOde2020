package day10;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Day10Puzzle2 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day10Puzzle2.class.getResource("input.txt").toURI()));
		List<Integer> numbers = lines.stream().map(s -> Integer.parseInt(s)).sorted().collect(Collectors.toList());

		int start = 0;
		int destination = numbers.get(numbers.size() - 1) + 3;
		numbers.add(0, start);
		numbers.add(destination);

		long[] f = new long[numbers.size()];
		f[numbers.size()-1] = 1;
		
		for (int i = numbers.size()-2; i >= 0; i--) {
			int number = numbers.get(i);
			
			for (int j = i+1; j < numbers.size(); j++) {
				int nextNumber = numbers.get(j);
				if (nextNumber - number > 3) {
					break;
				}
				
				f[i] += f[j];
			}
		}

		System.out.println(Arrays.toString(f));
	}
}
