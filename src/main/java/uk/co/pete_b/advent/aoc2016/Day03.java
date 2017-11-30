package uk.co.pete_b.advent.aoc2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day03 {
	public int numberValid(final boolean readByRow, final String content) {
		int numValid = 0;
		if (readByRow) {
			String[] triangles = StringUtils.split(content, "\r?\n");

			for (String triangle : triangles) {
				String[] sides = StringUtils.split(triangle);
				Triangle tri = new Triangle(sides[0], sides[1], sides[2]);
				if (tri.validTriangle()) {
					numValid++;
				}
			}
		} else {
			List<List<String>> table = new ArrayList<>();
			String[] rows = StringUtils.split(content, "\r?\n");

			for (String row : rows) {
				String[] cols = StringUtils.split(row);
				table.add(Arrays.asList(cols));
			}

			for (int i = 0; i < table.size() / 3; i++) {
				for (int j = 0; j < 3; j++) {
					Triangle tri = new Triangle(table.get(i * 3 + 0).get(j), table.get(i * 3 + 1).get(j),
							table.get(i * 3 + 2).get(j));
					if (tri.validTriangle()) {
						numValid++;
					}
				}
			}
		}

		return numValid;
	}

	private static class Triangle {
		String sideA;
		String sideB;
		String sideC;

		public Triangle(String sideA, String sideB, String sideC) {
			this.sideA = sideA;
			this.sideB = sideB;
			this.sideC = sideC;
		}

		private boolean validTriangle() {
			int[] sides = new int[] { Integer.valueOf(sideA), Integer.valueOf(sideB), Integer.valueOf(sideC) };
			Arrays.sort(sides);

			return (sides[0] + sides[1]) > sides[2];
		}
	}
}
