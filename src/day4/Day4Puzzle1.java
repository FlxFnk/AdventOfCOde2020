package day4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day4Puzzle1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day4Puzzle1.class.getResource("input.txt").toURI()));
		
		List<Map<String,String>> records = parseInput(lines);
		
		long count = records.stream().filter(Day4Puzzle1::validateRecord).count();
		
		System.out.println("Total records: " + records.size());
		System.out.println("Valid records: " + count);
	}
	
	private static List<Map<String, String>> parseInput(List<String> lines) {
		Map<String, String> currentMap = new HashMap<>();
		List<Map<String, String>> result = new LinkedList<>();
		
		for (String line : lines) {
			if (! line.strip().isEmpty()) {
				String[] fields = line.split(" " );
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
		String[] requiredFields = {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};
		
		for (String field : requiredFields) {
			if (record.get(field) == null) {
				System.out.println("Record invalid: " + field + " missing. (" + record.size() + ") " + record);
				return false;
			}
		}
		
		return true;
	}
}

