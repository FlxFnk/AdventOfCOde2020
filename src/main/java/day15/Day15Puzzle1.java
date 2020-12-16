package day15;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day15Puzzle1 {
	public static void main(String[] args) {
//		String input = "0,3";
		String input = "2,1";
//		String input = "1,17,0,10,18,11";

		Map<Long, Long> turns = new HashMap<>();
		long turn = 1;
		long last = 0;
		for (String s : input.split(",")) {
			last = Long.parseLong(s); 
			turns.put(last, turn);
			turn++;
		}

		last = 3;
//		turns.put(last, 0L);
		while (turn < 30000000L) {
			Long lastIndex = turns.get(last);
			if (lastIndex == null) {
				turns.put(last, turn);
				last = 0;
			} else {
				turns.put(last, turn);
				last = turn-lastIndex;
			}
			
			turn++;
		}

		System.out.println(last);
	}

}
