package day8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import day7.Day7Puzzle1;

public class Day8Puzzle1 {
	private enum OpCode {
		NOP, JMP, ACC
	}

	private static class Instruction {
		private OpCode opCode;
		private int argument;

		public Instruction(OpCode opCode, int argument) {
			super();
			this.opCode = opCode;
			this.argument = argument;
		}

		public OpCode getOpCode() {
			return opCode;
		}

		public void setOpCode(OpCode opCode) {
			this.opCode = opCode;
		}

		public int getArgument() {
			return argument;
		}

		public void setArgument(int argument) {
			this.argument = argument;
		}

		@Override
		public String toString() {
			return "Instruction [opCode=" + opCode + ", argument=" + argument + "]";
		}
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(Day8Puzzle1.class.getResource("input.txt").toURI()));

		List<Instruction> program = lines.stream().map(Day8Puzzle1::parseInstruction).collect(Collectors.toList());
		System.out.println(program);
		
		run(program);
	}

	private static Instruction parseInstruction(String line) {
		String[] s = line.split(" ");
		return new Instruction(OpCode.valueOf(s[0].toUpperCase()), Integer.parseInt(s[1]));
	}

	private static void run(List<Instruction> program) {
		Set<Integer> lineCache = new HashSet<>();
		int ip = 0;
		int accumulator = 0;

		while (!lineCache.contains(ip)) {
			lineCache.add(ip);
			Instruction instruction = program.get(ip);
			System.out.println(instruction.opCode + " " + instruction.argument);
			switch (instruction.opCode) {
			case NOP:
				ip++;
				break;
			case ACC:
				accumulator += instruction.argument;
				ip++;
				break;
			case JMP:
				ip += instruction.argument;
				break;
			default:
				System.err.println("Unknown opcode: " + instruction);
				break;
			}
		}
		
		System.out.println("Finished on line " + ip + ": " + accumulator);
	}
}
