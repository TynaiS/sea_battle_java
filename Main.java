import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int dimension = 7;
		String[][] defaultTable = new String[7][7];
		String ver = "ver";
		String hor = "hor";

		Map<String, ArrayList<ArrayList<Integer>>> ships = new HashMap<String, ArrayList<ArrayList<Integer>>>();

		System.out.println(ships);

		defaultTable = createTable(dimension);
		createThreeBlockShip(defaultTable, ships, ver, hor);

		// while (true) {
		// defaultTable = shoot(defaultTable);
		// displayTable(defaultTable, dimension);
		// }
	}

	private static void createShips() {
	}

	private static ArrayList createThreeBlockShip(String[][] table, Map<String, ArrayList<ArrayList<Integer>>> ships,
			String ver, String hor) {

		ArrayList response = new ArrayList<>();
		boolean isLocationValid = false;
		Set<String> keys = ships.keySet();

		while (!isLocationValid) {
			boolean flag = true;
			String direction = setShipDirection(ver, hor);

			int[][] shipCooridnates = new int[3][2];

			int[] startingCoodinates = getRandomCoordinates();
			int[] shipFront = new int[2];
			int[] shipBack = new int[2];

			if (direction == ver) {
				if (startingCoodinates[0] == 0 || startingCoodinates[0] == 6) {
					flag = false;
				}

				shipFront = new int[] { startingCoodinates[0] - 1, startingCoodinates[1] };
				shipBack = new int[] { startingCoodinates[0] + 1, startingCoodinates[1] };

				ships.put("ship" + startingCoodinates[0] + startingCoodinates[1],
						convertShipCoordinatesToArrayList(startingCoodinates, shipFront, shipBack));
			} else if (direction == hor) {
				if (startingCoodinates[1] == 0 || startingCoodinates[1] == 6) {
					flag = false;
				}

				shipFront = new int[] { startingCoodinates[0], startingCoodinates[1] - 1 };
				shipBack = new int[] { startingCoodinates[0], startingCoodinates[1] + 1 };

				ships.put("ship" + startingCoodinates[0] + startingCoodinates[1],
						convertShipCoordinatesToArrayList(startingCoodinates, shipFront, shipBack));
			}

			if (flag) {
				isLocationValid = true;
				shipCooridnates[0] = shipFront;
				shipCooridnates[1] = startingCoodinates;
				shipCooridnates[2] = shipBack;
				displayTableWithShips(table, shipCooridnates, 7);
			}
		}

		response.add(table);
		response.add(ships);

		return response;

	}

	private static ArrayList<ArrayList<Integer>> convertShipCoordinatesToArrayList(int[] firstCoordinates,
			int[] secondCoordinates, int[] thirdCoordinates) {

		ArrayList<ArrayList<Integer>> ship = new ArrayList<ArrayList<Integer>>();
		int[][] shipCoordinates = new int[][] { firstCoordinates, secondCoordinates, thirdCoordinates };

		for (int[] i : shipCoordinates) {
			ArrayList<Integer> row = new ArrayList<Integer>();
			row.add(i[0]);
			row.add(i[1]);
			ship.add(row);
		}

		return ship;
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

	private static void displayTableWithShips(String[][] table, int[][] shipCoordinates, int dimension) {
		System.out.println("  A B C D E F G");
		for (int i = 0; i < dimension; i++) {
			System.out.print(i + 1 + " ");
			for (int j = 0; j < dimension; j++) {

				boolean isShipThere = false;

				for (int[] k : shipCoordinates) {
					if (k[0] == i && k[1] == j) {
						isShipThere = true;
					}

				}

				if (isShipThere) {
					System.out.print("O ");
				} else {
					System.out.print(table[i][j] + " ");
				}

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

	private static int[] getRandomCoordinates() {

		int[] coordinates = new int[2];
		coordinates[0] = (int) (Math.random() * 7);
		coordinates[1] = (int) (Math.random() * 7);

		return coordinates;
	}
}