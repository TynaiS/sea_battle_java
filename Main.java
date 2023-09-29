import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int dimension = 7;
		String[][] defaultTable = new String[7][7];
		String ver = "ver";
		String hor = "hor";

		Map<String, ArrayList<ArrayList<Integer>>> ships = new HashMap<String, ArrayList<ArrayList<Integer>>>();

		defaultTable = createTable(dimension);
		displayTable(defaultTable, dimension);

		while (true) {
			defaultTable = shoot(defaultTable);
			displayTable(defaultTable, dimension);
		}
	}

	private static String[][] shoot(String[][] table) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter a row and column separated by a space: ");
		String col = scanner.nextLine();
		int row = scanner.nextInt();
		table[row - 1][letterToNum(col) - 1] = "X";

		return table;
	}

	private static void displayTable(String[][] table, int dimension) {
		System.out.println("  A B C D E F G");
		for (int i = 0; i < dimension; i++) {
			System.out.print(i + 1 + " ");
			for (int j = 0; j < dimension; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static String[][] createTable(int dimension) {

		String[][] table = new String[7][7];

		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				table[i][j] = "#";
			}
		}

		return table;
	}

	private static int letterToNum(String letter) {
		Map<String, Integer> lettersAndNums = new HashMap<>();
		{
			lettersAndNums.put("A", 1);
			lettersAndNums.put("B", 2);
			lettersAndNums.put("C", 3);
			lettersAndNums.put("D", 4);
			lettersAndNums.put("E", 5);
			lettersAndNums.put("F", 6);
			lettersAndNums.put("G", 7);

		}

		System.out.println(lettersAndNums.get(letter) + " " + letter);

		return lettersAndNums.get(letter);
	}

	private static String setShipDirection(String ver, String hor) {
		String ans = "";
		if (Math.random() > 0.5) {
			ans = ver;
		} else {
			ans = hor;
		}

		return ans;
	}
}