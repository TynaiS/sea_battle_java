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
		ships = createThreeBlockShip(defaultTable, ships, ver, hor);
		ships = createTwoBlockShip(defaultTable, ships, ver, hor);
		ships = createTwoBlockShip(defaultTable, ships, ver, hor);
		System.out.println(ships);

		displayTableWithShips(defaultTable, ships, dimension);

		// while (true) {
		// defaultTable = shoot(defaultTable);
		// displayTable defaultTable, dimension)
		// }
	}

	private static void createShips() {
	}

	private static Map<String, ArrayList<ArrayList<Integer>>> createTwoBlockShip(String[][] table,
			Map<String, ArrayList<ArrayList<Integer>>> ships,
			String ver, String hor) {

		boolean isLocationValid = false;
		Set<String> keys = ships.keySet();
		Object[] keysArr = keys.toArray();
		int[] shipFront = new int[2];

		while (!isLocationValid) {
			String direction = setShipDirection(ver, hor);

			int[] startingCoodinates = getRandomCoordinates();

			if (direction == ver) {
				if (startingCoodinates[0] == 0) {
					continue;
				} else {
					boolean flag = true;

					for (int i = 0; i < keysArr.length; i++) {
						if (!flag) {
							break;
						}
						for (ArrayList<Integer> arr : ships.get(keysArr[i])) {
							if (arr.get(0) == startingCoodinates[0] && arr.get(1) == startingCoodinates[1] ||
									arr.get(0) == startingCoodinates[0] - 1 && arr.get(1) == startingCoodinates[1] ||
									arr.get(0) == startingCoodinates[0] + 1 && arr.get(1) == startingCoodinates[1] - 1
									||
									arr.get(0) == startingCoodinates[0] && arr.get(1) == startingCoodinates[1] - 1 ||
									arr.get(0) == startingCoodinates[0] - 1 && arr.get(1) == startingCoodinates[1] - 1
									||
									arr.get(0) == startingCoodinates[0] - 2 && arr.get(1) == startingCoodinates[1] - 1
									||
									arr.get(0) == startingCoodinates[0] - 2 && arr.get(1) == startingCoodinates[1] ||
									arr.get(0) == startingCoodinates[0] - 2 && arr.get(1) == startingCoodinates[1] + 1
									||
									arr.get(0) == startingCoodinates[0] - 1 && arr.get(1) == startingCoodinates[1] + 1
									||
									arr.get(0) == startingCoodinates[0] && arr.get(1) == startingCoodinates[1] + 1 ||
									arr.get(0) == startingCoodinates[0] + 1 && arr.get(1) == startingCoodinates[1] + 1
									||
									arr.get(0) == startingCoodinates[0] + 1 && arr.get(1) == startingCoodinates[1]) {

								flag = false;
								break;
							}
						}
					}

					if (flag) {
						shipFront[0] = startingCoodinates[0] - 1;
						shipFront[1] = startingCoodinates[1];

						ships.put("ship" + startingCoodinates[0] + startingCoodinates[1],
								convertShipCoordinatesToArrayList(startingCoodinates, shipFront));

						isLocationValid = true;
					}
				}

			} else if (direction == hor) {
				if (startingCoodinates[1] == 6) {
					continue;
				} else {
					boolean flag = true;

					for (int i = 0; i < keysArr.length; i++) {
						if (!flag) {
							break;
						}
						for (ArrayList<Integer> arr : ships.get(keysArr[i])) {
							if (arr.get(0) == startingCoodinates[0] && arr.get(1) == startingCoodinates[1] ||
									arr.get(0) == startingCoodinates[0] && arr.get(1) == startingCoodinates[1] + 1 ||
									arr.get(0) == startingCoodinates[0] - 1 && arr.get(1) == startingCoodinates[1] - 1
									||
									arr.get(0) == startingCoodinates[0] - 1 && arr.get(1) == startingCoodinates[1] ||
									arr.get(0) == startingCoodinates[0] - 1 && arr.get(1) == startingCoodinates[1] + 1
									||
									arr.get(0) == startingCoodinates[0] - 1 && arr.get(1) == startingCoodinates[1] + 2
									||
									arr.get(0) == startingCoodinates[0] + 1 && arr.get(1) == startingCoodinates[1] - 1
									||
									arr.get(0) == startingCoodinates[0] + 1 && arr.get(1) == startingCoodinates[1]
									||
									arr.get(0) == startingCoodinates[0] + 1 && arr.get(1) == startingCoodinates[1] + 1
									||
									arr.get(0) == startingCoodinates[0] + 1 && arr.get(1) == startingCoodinates[1] + 2
									||
									arr.get(0) == startingCoodinates[0] && arr.get(1) == startingCoodinates[1] - 1
									||
									arr.get(0) == startingCoodinates[0] && arr.get(1) == startingCoodinates[1] + 2) {

								flag = false;
								break;
							}
						}
					}

					if (flag) {
						shipFront[0] = startingCoodinates[0];
						shipFront[1] = startingCoodinates[1] + 1;

						ships.put("ship" + startingCoodinates[0] + startingCoodinates[1],
								convertShipCoordinatesToArrayList(startingCoodinates, shipFront));

						isLocationValid = true;
					}
				}

			}
		}

		return ships;

	}

	private static Map<String, ArrayList<ArrayList<Integer>>> createThreeBlockShip(String[][] table,
			Map<String, ArrayList<ArrayList<Integer>>> ships,
			String ver, String hor) {

		boolean isLocationValid = false;
		// ArrayList<int[]> shipsCooridnates = new ArrayList<int[]>();

		int[] shipFront = new int[2];
		int[] shipBack = new int[2];

		while (!isLocationValid) {
			boolean flag = true;
			String direction = setShipDirection(ver, hor);

			int[] startingCoodinates = getRandomCoordinates();

			if (direction == ver) {
				if (startingCoodinates[0] == 0 || startingCoodinates[0] == 6) {
					flag = false;
				} else {
					shipFront = new int[] { startingCoodinates[0] - 1, startingCoodinates[1] };
					shipBack = new int[] { startingCoodinates[0] + 1, startingCoodinates[1] };

					ships.put("ship" + startingCoodinates[0] + startingCoodinates[1],
							convertShipCoordinatesToArrayList(startingCoodinates, shipFront, shipBack));
				}
			} else if (direction == hor) {
				if (startingCoodinates[1] == 0 || startingCoodinates[1] == 6) {
					flag = false;
				} else {
					shipFront = new int[] { startingCoodinates[0], startingCoodinates[1] - 1 };
					shipBack = new int[] { startingCoodinates[0], startingCoodinates[1] + 1 };

					ships.put("ship" + startingCoodinates[0] + startingCoodinates[1],
							convertShipCoordinatesToArrayList(startingCoodinates, shipFront, shipBack));
				}
			}

			if (flag) {
				isLocationValid = true;
				// shipsCooridnates.add(shipFront);
				// shipsCooridnates.add(startingCoodinates);
				// shipsCooridnates.add(shipBack);
				// displayTableWithShips(table, shipsCooridnates, 7);
			}
		}

		return ships;

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

	private static ArrayList<ArrayList<Integer>> convertShipCoordinatesToArrayList(int[] firstCoordinates,
			int[] secondCoordinates) {

		ArrayList<ArrayList<Integer>> ship = new ArrayList<ArrayList<Integer>>();
		int[][] shipCoordinates = new int[][] { firstCoordinates, secondCoordinates };

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

	private static void displayTableWithShips(String[][] table, Map<String, ArrayList<ArrayList<Integer>>> ships,
			int dimension) {

		Set<String> keys = ships.keySet();

		System.out.println("  A B C D E F G");
		for (int i = 0; i < dimension; i++) {
			System.out.print(i + 1 + " ");
			for (int j = 0; j < dimension; j++) {

				boolean isShipThere = false;
				for (String key : keys) {
					for (ArrayList<Integer> arr : ships.get(key)) {
						if (arr.get(0) == i && arr.get(1) == j) {
							isShipThere = true;
						}
					}
				}

				// for (int[] k : shipsCoordinates) {
				// if (k[0] == i && k[1] == j) {
				// isShipThere = true;
				// }
				// }

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
		Map<String, Integer> lettersAndNums = new HashMap<String, Integer>();
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