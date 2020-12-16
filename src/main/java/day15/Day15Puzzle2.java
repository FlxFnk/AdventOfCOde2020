package day15;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day15Puzzle2 {
	public static void main(String[] args) {
//		String input = "0,3,6";
//		String input = "2,1,3";
		String input = "1,17,0,10,18,11,6";

		LinkedList<Integer> turns = new LinkedList<>();
		Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).forEach(turns::add);
		
		for (int turn = turns.size()+1; turn <= 2020; turn++) {
			int number = turns.pollLast();
			int index = turns.lastIndexOf(number);
			turns.add(number);
			if (index < 0) {
				turns.add(0);
			} else {
				turns.add(turns.size()-index-1);
			}
			
			System.out.println(turns.peekLast());
		}
	}
	
	
}
