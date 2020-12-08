package day2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day2Puzzle1 {

	public static class ParsedLine {
		int min;
		int max;
		char letter;
		String password;
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day2Puzzle1.class.getResource("input.txt").toURI()));
		List<ParsedLine> parsedLines = lines.stream().map(Day2Puzzle1::splitLine).collect(Collectors.toList());

		System.out.println("Total: " + parsedLines.size());
		System.out.println("Valid: " + parsedLines.stream().filter(Day2Puzzle1::checkLine).count());
	}

	private static ParsedLine splitLine(String line) {
		Pattern pattern = Pattern.compile("^(\\d+?)-(\\d+?) (\\w): (\\w+?)$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.matches()) {
			ParsedLine parsedLine = new ParsedLine();
			parsedLine.min = Integer.parseInt(matcher.group(1));
			parsedLine.max = Integer.parseInt(matcher.group(2));
			parsedLine.letter = matcher.group(3).charAt(0);
			parsedLine.password = matcher.group(4);

			return parsedLine;
		} else {
			System.out.println("Line \"" + line + "\" does not match.");
			return null;
		}
	}
	
	private static boolean checkLine(ParsedLine line) {
		long count = line.password.chars().filter(c -> c == line.letter).count();
		
		return ((count >= line.min) && (count <= line.max));
	}
}
