package day14;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Puzzle1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day14Puzzle1.class.getResource("input.txt").toURI()));

		String currentMask = "";
		Map<Integer, String> memory = new TreeMap<>();
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
					int address = Integer.parseInt(matcher.group(1));
					String value = padLeftZeros(Long.toBinaryString(Long.parseLong(matcher.group(2))),
							currentMask.length());
					String modifiedValue = applyMask(value, currentMask);
					memory.put(address, modifiedValue);
				} else {
					System.err.println("Wrong pattern: " + line);
				}
			}
		}

		long sum = memory.entrySet().stream().mapToLong(e -> {
			long value = Long.parseLong(e.getValue(), 2);
			System.out.println(e.getKey() + "=" + e.getValue() + " (" + value + ")");
			return value;
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

	private static String applyMask(String value, String mask) {
		if (value.length() != mask.length()) {
			System.err.println("Different lengths:" + value + "  " + mask);
		}

		char[] result = value.toCharArray();
		for (int i = 0; i < value.length(); i++) {
			if (mask.charAt(i) == '0') {
				result[i] = '0';
			} else if (mask.charAt(i) == '1') {
				result[i] = '1';
			}
		}
		return new String(result);
	}
}
