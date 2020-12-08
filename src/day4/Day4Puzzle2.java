package day4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4Puzzle2 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day4Puzzle2.class.getResource("input.txt").toURI()));

		List<Map<String, String>> records = parseInput(lines);

		List<Map<String, String>> validRecords = records.stream().filter(Day4Puzzle2::validateRecord).collect(Collectors.toList());
		validRecords.forEach(System.out::println);

		long count = validRecords.size();
		
		System.out.println("Total records: " + records.size());
		System.out.println("Valid records: " + count);
	}

	private static List<Map<String, String>> parseInput(List<String> lines) {
		Map<String, String> currentMap = new HashMap<>();
		List<Map<String, String>> result = new LinkedList<>();

		for (String line : lines) {
			if (!line.strip().isEmpty()) {
				String[] fields = line.split(" ");
				for (String field : fields) {
					String[] f = field.split(":");
					currentMap.put(f[0], f[1]);
				}
			} else {
				result.add(currentMap);
				currentMap = new HashMap<>();
			}
		}

		if (!currentMap.isEmpty()) {
			result.add(currentMap);
		}

		return result;
	}

	private static boolean validateRecord(Map<String, String> record) {
		Map<String, Predicate<String>> requiredFields = new HashMap<>();
		requiredFields.put("byr", Day4Puzzle2::checkByr);
		requiredFields.put("iyr", Day4Puzzle2::checkIyr);
		requiredFields.put("eyr", Day4Puzzle2::checkEyr);
		requiredFields.put("hgt", Day4Puzzle2::checkHgt);
		requiredFields.put("hcl", Day4Puzzle2::checkHcl);
		requiredFields.put("ecl", Day4Puzzle2::checkEcl);
		requiredFields.put("pid", Day4Puzzle2::checkPid);

		for (Map.Entry<String, Predicate<String>> entry : requiredFields.entrySet()) {
			String value = record.get(entry.getKey());
			if (value == null) {
				System.out.println("Record invalid: " + entry.getKey() + " missing. (" + record.size() + ") " + record);
				return false;
			}

			if (!entry.getValue().test(value)) {
				System.out.println("Record invalid: " + entry.getKey() + " invalid. (" + value + ")");
				return false;
			}
		}

		return true;
	}

	private static boolean checkYear(String year, int min, int max) {
		try {
			int y = Integer.parseInt(year);
			return ((y >= min) && (y <= max));
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static boolean checkByr(String s) {
		return checkYear(s, 1920, 2002);
	}

	private static boolean checkIyr(String s) {
		return checkYear(s, 2010, 2020);
	}

	private static boolean checkEyr(String s) {
		return checkYear(s, 2020, 2030);
	}

	private static boolean checkHgt(String s) {
		if (s.endsWith("cm")) {
			int size = Integer.parseInt(s.substring(0, s.length() - 2));
			return (size >= 150) && (size <= 193);
		} else if (s.endsWith("in")) {
			int size = Integer.parseInt(s.substring(0, s.length() - 2));
			return (size >= 59) && (size <= 76);
		}

		return false;
	}

	private static boolean checkHcl(String s) {
		Pattern pattern = Pattern.compile("#([0-9a-f]{6})");
		return pattern.matcher(s).matches();
	}

	private static boolean checkEcl(String s) {
		List<String> eyeColors = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
		return eyeColors.contains(s);
	}

	private static boolean checkPid(String s) {
		Pattern pattern = Pattern.compile("\\d{9}");
		return pattern.matcher(s).matches();
	}
	
}
