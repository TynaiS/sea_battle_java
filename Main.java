import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.*;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int dimension = 7;
		String[][] defaultTable = new String[7][7];
		String ver = "ver";
		String hor = "hor";

		Map<String, ArrayList<ArrayList<Integer>>> ships = new HashMap<String, ArrayList<ArrayList<Integer>>>();

		System.out.println(ships);

		defaultTable = createTable();

		ships = createThreeBlockShip(defaultTable, ships, ver, hor);

		ships = createTwoBlockShip(defaultTable, ships, ver, hor);
		ships = createTwoBlockShip(defaultTable, ships, ver, hor);

		ships = createOneBlockShip(defaultTable, ships, ver, hor);
		ships = createOneBlockShip(defaultTable, ships, ver, hor);
		ships = createOneBlockShip(defaultTable, ships, ver, hor);
		ships = createOneBlockShip(defaultTable, ships, ver, hor);

		System.out.println(ships);

		// displayTableWithShips(defaultTable, ships);
		displayTable(defaultTable);

		while (true) {
			defaultTable = shoot(defaultTable, ships);
		}
	}

	private static void createShips() {

	}

	private static Map<String, ArrayList<ArrayList<Integer>>> createOneBlockShip(String[][] table,
			Map<String, ArrayList<ArrayList<Integer>>> ships,
			String ver, String hor) {

		boolean isLocationValid = false;
		Set<String> keys = ships.keySet();
		Object[] keysArr = keys.toArray();

		ArrayList<ArrayList<Integer>> ship = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> shipInner = new ArrayList<Integer>();

		while (!isLocationValid) {

			int[] shipCoodinates = getRandomCoordinates();

			boolean flag = true;

			for (int i = 0; i < keysArr.length; i++) {
				if (!flag) {
					break;
				}
				for (ArrayList<Integer> arr : ships.get(keysArr[i])) {
					if (arr.get(0) == shipCoodinates[0] && arr.get(1) == shipCoodinates[1] ||
							arr.get(0) == shipCoodinates[0] - 1 && arr.get(1) == shipCoodinates[1] - 1 ||
							arr.get(0) == shipCoodinates[0] - 1 && arr.get(1) == shipCoodinates[1]
							||
							arr.get(0) == shipCoodinates[0] - 1 && arr.get(1) == shipCoodinates[1] + 1 ||
							arr.get(0) == shipCoodinates[0] + 1 && arr.get(1) == shipCoodinates[1] - 1
							||
							arr.get(0) == shipCoodinates[0] + 1 && arr.get(1) == shipCoodinates[1]
							||
							arr.get(0) == shipCoodinates[0] + 1 && arr.get(1) == shipCoodinates[1] + 1 ||
							arr.get(0) == shipCoodinates[0] && arr.get(1) == shipCoodinates[1] - 1
							||
							arr.get(0) == shipCoodinates[0] && arr.get(1) == shipCoodinates[1] + 1) {

						flag = false;
						break;
					}
				}
			}

			if (flag) {

				shipInner.add(shipCoodinates[0]);
				shipInner.add(shipCoodinates[1]);
				ship.add(shipInner);

				ships.put("ship" + shipCoodinates[0] + shipCoodinates[1], ship);

				isLocationValid = true;
			}
		}

		return ships;
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

	private static String[][] shoot(String[][] table, Map<String, ArrayList<ArrayList<Integer>>> ships) {

		int[] coordinates = getValidCoordinatesFromUser();
		if (table[coordinates[0]][coordinates[1]] == "X"
				|| table[coordinates[0]][coordinates[1]] == ".") {
			System.out.println("You have already shot here, try other coordinates!");
			shoot(table, ships);
		} else {
			if (isShipThere(ships, coordinates[0], coordinates[1])) {
				table[coordinates[0]][coordinates[1]] = "X";
				table = checkShipDamageLevel(table, ships, coordinates[0], coordinates[1]);
				displayTable(table);
				if (table[coordinates[0]][coordinates[1]] == "D") {
					System.out.println("Kill!!!");
				} else {
					System.out.println("It's a hit!");
				}
			} else {
				table[coordinates[0]][coordinates[1]] = ".";
				displayTable(table);
				System.out.println("You missed");
			}
		}
		return table;
	}

	private static String[][] checkShipDamageLevel(String[][] table, Map<String, ArrayList<ArrayList<Integer>>> ships,
			int row, int col) {

		Set<String> keys = ships.keySet();
		Object[] keysArr = keys.toArray();

		boolean flag = true;

		for (int i = 0; i < keysArr.length; i++) {
			if (!flag) {
				break;
			}
			for (ArrayList<Integer> arr : ships.get(keysArr[i])) {
				if (arr.get(0) == row && arr.get(1) == col) {
					if (ships.get(keysArr[i]).size() == 3
							&& table[ships.get(keysArr[i]).get(0).get(0)][ships.get(keysArr[i]).get(0).get(1)] == "X"
							&& table[ships.get(keysArr[i]).get(1).get(0)][ships.get(keysArr[i]).get(1).get(1)] == "X"
							&& table[ships.get(keysArr[i]).get(2).get(0)][ships.get(keysArr[i]).get(2).get(1)] == "X") {

						table[ships.get(keysArr[i]).get(0).get(0)][ships.get(keysArr[i]).get(0).get(1)] = "D";
						table[ships.get(keysArr[i]).get(1).get(0)][ships.get(keysArr[i]).get(1).get(1)] = "D";
						table[ships.get(keysArr[i]).get(2).get(0)][ships.get(keysArr[i]).get(2).get(1)] = "D";

					} else if (ships.get(keysArr[i]).size() == 2
							&& table[ships.get(keysArr[i]).get(0).get(0)][ships.get(keysArr[i]).get(0).get(1)] == "X"
							&& table[ships.get(keysArr[i]).get(1).get(0)][ships.get(keysArr[i]).get(1).get(1)] == "X") {

						table[ships.get(keysArr[i]).get(0).get(0)][ships.get(keysArr[i]).get(0).get(1)] = "D";
						table[ships.get(keysArr[i]).get(1).get(0)][ships.get(keysArr[i]).get(1).get(1)] = "D";

					} else if (ships.get(keysArr[i]).size() == 1
							&& table[ships.get(keysArr[i]).get(0).get(0)][ships.get(keysArr[i]).get(0).get(1)] == "X") {
						table[ships.get(keysArr[i]).get(0).get(0)][ships.get(keysArr[i]).get(0).get(1)] = "D";
					}
					flag = false;
					break;
				}
			}
		}

		return table;
	}

	private static int[] getValidCoordinatesFromUser() {

		Scanner scanner = new Scanner(System.in);
		int[] coordinates = new int[2];
		String coordinatesString = "";
		boolean areCoordinatesValid = false;

		System.out.println("Enter the coordinates for the shot(e.g. A5 or a5): ");

		while (!areCoordinatesValid) {
			String coordinatesFromUser = scanner.nextLine();

			areCoordinatesValid = Pattern.matches("[a-gA-G][1-7]", coordinatesFromUser);

			if (areCoordinatesValid) {
				coordinatesString = coordinatesFromUser;
			} else {
				System.out.println("Please, enter valid coordinates for the shot(e.g. A5 or a5): ");
			}
		}

		int row = Integer.parseInt(Character.toString(coordinatesString.charAt(1)));
		int col = letterToNum(Character.toString(coordinatesString.charAt(0)).toUpperCase());
		coordinates[0] = row - 1;
		coordinates[1] = col - 1;

		return coordinates;
	}

	private static boolean isShipThere(Map<String, ArrayList<ArrayList<Integer>>> ships, int row, int col) {

		Set<String> keys = ships.keySet();
		Object[] keysArr = keys.toArray();

		boolean isShipThere = false;

		for (int i = 0; i < keysArr.length; i++) {
			for (ArrayList<Integer> arr : ships.get(keysArr[i])) {
				if (arr.get(0) == row && arr.get(1) == col) {
					isShipThere = true;
					break;
				}
			}
		}

		return isShipThere;
	}

	private static void displayTable(String[][] table) {
		System.out.println("  A B C D E F G");
		for (int i = 0; i < 7; i++) {
			System.out.print(i + 1 + " ");
			for (int j = 0; j < 7; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static void displayTableWithShips(String[][] table, Map<String, ArrayList<ArrayList<Integer>>> ships) {
		System.out.println("  A B C D E F G");
		for (int i = 0; i < 7; i++) {
			System.out.print(i + 1 + " ");
			for (int j = 0; j < 7; j++) {

				if (isShipThere(ships, i, j)) {
					System.out.print("O ");
				} else {
					System.out.print(table[i][j] + " ");
				}
			}
			System.out.println();
		}
	}

	private static String[][] createTable() {

		String[][] table = new String[7][7];

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
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