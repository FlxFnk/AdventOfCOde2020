package day8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import day7.Day7Puzzle1;

public class Day8Puzzle2 {
	private enum OpCode {
		NOP, JMP, ACC
	}

	private static class Instruction {
		private OpCode opCode;
		private int argument;

		public Instruction(OpCode opCode, int argument) {
			this.opCode = opCode;
			this.argument = argument;
		}
		
		public Instruction(Instruction instruction) {
			this.opCode = instruction.opCode;
			this.argument = instruction.argument;
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
		List<String> lines = Files.readAllLines(Paths.get(Day8Puzzle2.class.getResource("input.txt").toURI()));

		List<Instruction> program = lines.stream().map(Day8Puzzle2::parseInstruction).collect(Collectors.toList());
		System.out.println(program);

		for (int i = 0; i < program.size(); i++) {
			List<Instruction> program2 = repair(program, i);
			if (program2 == null) {
				System.err.println("Could not repair.");
				return;
			}
			
			if (run(program2)) {
				System.out.println("Repaired program on line #" + i + " worked.");
				return;
			}
		}
	}

	private static Instruction parseInstruction(String line) {
		String[] s = line.split(" ");
		return new Instruction(OpCode.valueOf(s[0].toUpperCase()), Integer.parseInt(s[1]));
	}

	private static boolean run(List<Instruction> program) {
		Set<Integer> lineCache = new HashSet<>();
		int ip = 0;
		int accumulator = 0;

		while (ip < program.size()) {
			if (lineCache.contains(ip)) {
				System.out.println("Infinite loop on line " + ip + ": " + accumulator);
				return false;
			}
			lineCache.add(ip);
			Instruction instruction = program.get(ip);
//			System.out.println(instruction.opCode + " " + instruction.argument);
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
		
		System.out.println("Program terminated successfully: " + accumulator);
		return true;
	}
	
	private static List<Instruction> repair(List<Instruction> program, int startLine) {
		List<Instruction> result = new ArrayList<>(program);
		for (int i = startLine; i < program.size(); i++) {
			Instruction instruction = program.get(i);
			if (instruction.getOpCode() == OpCode.NOP) {
				Instruction newInstruction = new Instruction(instruction);
				newInstruction.setOpCode(OpCode.JMP);
				result.set(i, newInstruction);
				System.out.println("Changed line " + i);
				return result;
			} else if (instruction.getOpCode() == OpCode.JMP) {
				Instruction newInstruction = new Instruction(instruction);
				newInstruction.setOpCode(OpCode.NOP);
				result.set(i, newInstruction);
				System.out.println("Changed line " + i);
				return result;
			}
		}
		
		return null;
	}
}
