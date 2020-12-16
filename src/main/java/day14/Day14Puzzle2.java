package day14;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14Puzzle2 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day14Puzzle2.class.getResource("input.txt").toURI()));

		String currentMask = "";
		Map<Long, Long> memory = new TreeMap<>();
		for (String line : lines) {
			if (line.startsWith("mask = ")) {
				currentMask = line.substring(7);
				if (currentMask.length() != 36) {
					System.err.println("Wrong mask: " + currentMask + " " + currentMask.length());
				}
			} else {
				Pattern pattern = Pattern.compile("mem\\[(\\d+?)\\] = (\\d+?)");
				Matcher matcher = pattern.matcher(line);

				if (matcher.matches()) {
					long value = Long.parseLong(matcher.group(2));
					String  address = padLeftZeros(Long.toBinaryString(Long.parseLong(matcher.group(1))),
							currentMask.length());
					List<Long> modifiedAddresses= applyMask(address, currentMask);
					modifiedAddresses.stream().forEach(a -> memory.put(a, value));
				} else {
					System.err.println("Wrong pattern: " + line);
				}
			}
		}

		long sum = memory.entrySet().stream().mapToLong(e -> {
			System.out.println(e.getKey() + "=" + e.getValue());
			return e.getValue();
		}).sum();
		
		System.out.println("sum: " + sum);
	}

	public static String padLeftZeros(String inputString, int length) {
		if (inputString.length() >= length) {
			return inputString;
		}
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length - inputString.length()) {
			sb.append('0');
		}
		sb.append(inputString);

		return sb.toString();
	}

	private static List<Long> applyMask(String value, String mask) {
		if (value.length() != mask.length()) {
			System.err.println("Different lengths:" + value + "  " + mask);
		}

		List<char[]> result = new LinkedList<>();
		result.add(value.toCharArray());
		for (int i = 0; i < value.length(); i++) {
			if (mask.charAt(i) == '1') {
				for (int j = 0; j < result.size(); j++) {
					result.get(j)[i] = '1';
				}
			} else if (mask.charAt(i) == 'X') {
				List<char[]> newResult = new LinkedList<>();
				for (int j = 0; j < result.size(); j++) {
					char[] a = result.get(j);
					char[] a0 = new char[a.length];
					char[] a1 = new char[a.length];
					System.arraycopy(a, 0, a0, 0, a.length);
					System.arraycopy(a, 0, a1, 0, a.length);
					a0[i] = '0';
					a1[i] = '1';
					newResult.add(a0);
					newResult.add(a1);
				};
				
				result.clear();
				result.addAll(newResult);
			}
		}
		
		return result.stream().map(String::new).map(s -> Long.parseLong(s, 2)).collect(Collectors.toList());
	}
}
