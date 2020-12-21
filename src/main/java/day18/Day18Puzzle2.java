package day18;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public final class Day18Puzzle2 {

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day18Puzzle2.class.getResource("input.txt").toURI()));

		long sum = 0;
		for (String line : lines) {
			System.out.println(line);
			LinkedList<Character> l = new LinkedList<>();
			for (char c : line.toCharArray()) {
				l.add(c);
			}
			Queue<Character> prn = transformInput(l);
			System.out.println("PRN: " + prn);
			
			long result = calculatePrn(prn);;
			sum += result;
			System.out.println("Result = " + result);
		}

		System.out.println("Sum: " + sum);
	}

	private static Queue<Character> transformInput(Queue<Character> input) {
		Queue<Character> output = new LinkedList<>();
		Stack<Character> operators = new Stack<>();

		while (!input.isEmpty()) {
			char c = input.poll().charValue();

			if (c == ' ') {
				continue;
			} else if ((c >= '0') && (c <= '9')) {
				output.add(c);
			} else if (c == '(') {
				operators.push(c);
			} else if (c == ')') {
				while (operators.peek() != '(') {
					output.add(operators.pop());
				}

				if (operators.peek() == '(') {
					operators.pop();
				}
			} else {
				while (!operators.isEmpty()) {
					char o = operators.peek();
					if ((o == '+') && (c == '*') && (o != '(')) {
						output.add(operators.pop());
					} else {
						break;
					}
				}

				operators.push(c);
			}
		}
		
		while (!operators.isEmpty()) {
			output.add(operators.pop());
		}

		return output;
	}
	
	private static long calculatePrn(Queue<Character> input) {
		Stack<Long> values = new Stack<>();
		
		while(!input.isEmpty()) {
//			System.out.println("******");
//			System.out.println(input);
//			System.out.println(values);
			char c = input.poll();
			if ((c >= '0') && (c <= '9')) {
				values.push(Long.parseLong(String.valueOf(c)));
			} else if (c == '+') {
				Long op1 = values.pop();
				Long op2 = values.pop();
				values.push(op1+op2);
			} else if (c == '*') {
				Long op1 = values.pop();
				Long op2 = values.pop();
				values.push(op1*op2);
			}
		}
		
		return values.pop();
	}
}
