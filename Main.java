import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) {
		Map<String, Integer> playersList = new HashMap<>();
		playersList = startGame(playersList);
	}

	private static Map<String, Integer> startGame(Map<String, Integer> playersList) {

		System.out.println("""

				Welcome to The Sea Battle!
				(type 'q' to quit the game and 'r' to restart the game at any moment)
				Choose the game mode:
				1 player - type 1
				2 players - type 2
				""");

		int gameMode = getValidGameModeFromUser(playersList);
		switch (gameMode) {
			case 1:
				playersList = startOnePlayerMode(playersList);
				break;
			case 2:
				startTwoPlayersMode(playersList);
				break;
		}

		return playersList;
	}

	private static Map<String, Integer> startOnePlayerMode(Map<String, Integer> playersList) {
		Scanner scanner = new Scanner(System.in);

		String[][] defaultTable = new String[8][7];
		String ver = "ver";
		String hor = "hor";
		String nickname = "";
		int kills = 0;
		int shots = 0;

		Map<String, ArrayList<ArrayList<Integer>>> ships = new HashMap<String, ArrayList<ArrayList<Integer>>>();

		System.out.println("Enter your nickname: ");
		nickname = getNicknameFromUser(playersList);
		System.out.println("\nHello " + nickname + "!");

		defaultTable = fillTable(defaultTable);

		ships = createShips(defaultTable, ships, ver, hor);

		displayTable(defaultTable);

		while (kills < 7) {
			defaultTable = shoot(defaultTable, ships, kills, shots, playersList);
			kills = Integer.parseInt(defaultTable[7][0]);
			shots = Integer.parseInt(defaultTable[7][1]);
		}

		playersList.put(nickname, shots);

		System.out.println("Congratulations, you have won!");

		displayPlayersList(playersList);

		System.out.println("""
				Starting a new game in 1 player mode
				(type 'q' to quit the game and 'r' to restart the game at any moment)""");
		startOnePlayerMode(playersList);

		return playersList;
	}

	private static void startTwoPlayersMode(Map<String, Integer> playersList) {

	}

	private static void handleUserQuitOrRestart(String userEnter, Map<String, Integer> playersList) {
		if (userEnter.equals("q")) {
			System.out.println("\nQuitting...");
			System.exit(0);
		} else if (userEnter.equals("r")) {
			System.out.println("\n\n\nRestarting...");
			startGame(playersList);
		}
	}

	private static void displayPlayersList(Map<String, Integer> playersList) {
		System.out.println("\nList of the best players:");
		Map<String, Integer> sortedPlayersList = playersList.entrySet().stream()
				.sorted(Comparator.comparingInt(e -> e.getValue()))
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(a, b) -> {
							throw new AssertionError();
						},
						LinkedHashMap::new));

		Set<String> sortedPlayersNames = sortedPlayersList.keySet();
		Object[] sortedPlayersNamesArr = sortedPlayersNames.toArray();

		for (int i = 0; i < sortedPlayersNamesArr.length; i++) {
			System.out.println(
					i + 1 + ") " + sortedPlayersNamesArr[i] + " - " + sortedPlayersList.get(sortedPlayersNamesArr[i])
							+ " shots");
		}
	}

	private static String getNicknameFromUser(Map<String, Integer> playersList) {
		Scanner scanner = new Scanner(System.in);
		String nickname = "";

		while (nickname.length() == 0) {
			String nicknameFromUser = scanner.nextLine().trim();

			handleUserQuitOrRestart(nicknameFromUser, playersList);

			if (nicknameFromUser.length() > 0) {
				if (!playersList.containsKey(nicknameFromUser)) {
					nickname = nicknameFromUser;
				} else {
					System.out.println("This name is already taken, enter another one:");
				}
			} else {
				System.out.println("Your nickname cannot be empty, enter you nickname:");
			}
		}

		return nickname;
	}

	private static int getValidGameModeFromUser(Map<String, Integer> playersList) {
		Scanner scanner = new Scanner(System.in);
		boolean isGameModeValid = false;
		int gameMode = 1;

		while (!isGameModeValid) {
			String gameModeFromUser = scanner.nextLine().trim();

			handleUserQuitOrRestart(gameModeFromUser, playersList);

			isGameModeValid = Pattern.matches("[1-2]", gameModeFromUser);

			if (isGameModeValid) {
				gameMode = Integer.parseInt(gameModeFromUser);
			} else {
				System.out.println("Please, enter valid data(type '1' for 1 player mode, '2' - for 2 players mode): ");
			}
		}

		return gameMode;
	}

	private static Map<String, ArrayList<ArrayList<Integer>>> createShips(String[][] table,
			Map<String, ArrayList<ArrayList<Integer>>> ships, String ver, String hor) {

		ships = createThreeBlockShip(table, ships, ver, hor);

		ships = createTwoBlockShip(table, ships, ver, hor);
		ships = createTwoBlockShip(table, ships, ver, hor);

		ships = createOneBlockShip(table, ships, ver, hor);
		ships = createOneBlockShip(table, ships, ver, hor);
		ships = createOneBlockShip(table, ships, ver, hor);
		ships = createOneBlockShip(table, ships, ver, hor);

		return ships;
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

	private static String[][] shoot(String[][] table, Map<String, ArrayList<ArrayList<Integer>>> ships, int kills,
			int shots, Map<String, Integer> playersList) {

		int[] coordinates = getValidCoordinatesFromUser(playersList);
		if (table[coordinates[0]][coordinates[1]] == "X"
				|| table[coordinates[0]][coordinates[1]] == "." || table[coordinates[0]][coordinates[1]] == "D") {
			System.out.println("You have already shot here, try other coordinates!");
			shoot(table, ships, kills, shots, playersList);
		} else {
			if (isShipThere(ships, coordinates[0], coordinates[1])) {
				table[coordinates[0]][coordinates[1]] = "X";
				table = checkShipDamageLevel(table, ships, coordinates[0], coordinates[1]);
				displayTable(table);
				if (table[coordinates[0]][coordinates[1]] == "D") {
					kills++;
					System.out.println("Kill!!!");
				} else {
					System.out.println("It's a hit!");
				}
			} else {
				table[coordinates[0]][coordinates[1]] = ".";
				displayTable(table);
				System.out.println("You missed");
			}
			shots++;
		}

		table[7][0] = Integer.toString(kills);
		table[7][1] = Integer.toString(shots);

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

	private static int[] getValidCoordinatesFromUser(Map<String, Integer> playersList) {

		Scanner scanner = new Scanner(System.in);
		int[] coordinates = new int[2];
		String coordinatesString = "";
		boolean areCoordinatesValid = false;

		System.out.println("Enter the coordinates for the shot(e.g. A5 or a5): ");

		while (!areCoordinatesValid) {
			String coordinatesFromUser = scanner.nextLine().trim();

			handleUserQuitOrRestart(coordinatesFromUser, playersList);

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

	private static String[][] fillTable(String[][] table) {

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