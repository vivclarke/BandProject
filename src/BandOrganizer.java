
import java.util.Scanner;
/**
 * This program organizes the position of
 * musicians in a band in a given stadium.
 * @author Vivienne Clarke
 * @version 1.0
 */
public class BandOrganizer {
    //---------------------------------------------------------------
    // declare constants
    private static final int MAX_ROWS = 10;
    private static final int MAX_POSITIONS = 8;
    private static final int MAX_WEIGHT = 100;
//---------------------------------------------------------------
    /**
     * Displays menu for function selection.
     * @param args command-line arguments
     */

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        // display first prompt
        System.out.println("Welcome to the Band of the Hour");
        System.out.println("-------------------------------");

        // declare the array
        int[][] stadiumSize = declareSize(keyboard);

        displayMenu(keyboard, stadiumSize);

    } //end of the main method
//---------------------------------------------------------------
    /**
     * Creates the dimensions of the stadium.
     * @param keyboard dimension inputs
     * @return stadiumSize
     */
    private static int[][] declareSize(Scanner keyboard) {
        int numRows;

        do {
            System.out.println("Please enter the number of rows               : ");
            numRows = keyboard.nextInt();
            if (numRows < 1 || numRows > MAX_ROWS) {
                System.out.println("ERROR: Out of range, try again            : ");
                numRows = keyboard.nextInt();
            }
        } while (numRows < 1 || numRows > MAX_ROWS);
        // end of do-while loop

        int[][] stadiumSize = new int[numRows][];
        int[] rowPositions = new int[numRows];

        for (int input = 0; input < numRows; input++) {
            int numPositions;
            char rowLabel = (char) ('A' + input);
            do {
                System.out.print("Please enter number of positions in row " + rowLabel + ": ");
                numPositions = keyboard.nextInt();
                if (numPositions <= 0 || numPositions > MAX_POSITIONS) {
                    System.out.println("ERROR: Out of range, try again");
                }
            } while (numPositions <= 0 || numPositions > MAX_POSITIONS);

            stadiumSize[input] = new int[numPositions];
            rowPositions[input] = numPositions;

        } // end of for loop

        return stadiumSize;

    } //end of declareSize method
//---------------------------------------------------------------
    /**
     * Displays menu for function selection.
     * @param keyboard inputs from user
     * @param stadiumSize gives array
     */
    private static void displayMenu(Scanner keyboard, int[][] stadiumSize) {
        char option;
        do {
            System.out.print("\n(A)dd, (R)emove, (P)rint, e(X)it : ");
            option = Character.toUpperCase(keyboard.next().charAt(0));

            switch (option) {
                case 'A':
                    addMusician(keyboard, stadiumSize);
                    break;
                case 'R':
                    removeMusician(keyboard, stadiumSize);
                    break;
                case 'P':
                    printAssignment(stadiumSize);
                    break;
                case 'X':
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("ERROR: Invalid option, try again");
            }
        } while (option != 'X');

    } // end of displayMenu method

// ---------------------------------------------------------------
    /**
     * Prints the current status of musicians, total, and average weight per row.
     * @param stadiumSize gives array
     */
    private static void printAssignment(int[][] stadiumSize) {
        System.out.println();
        for (int input = 0; input < stadiumSize.length; input++) {
            char rowLabel = (char) ('A' + input);
            System.out.print(rowLabel + ":");
            for (int j = 0; j < stadiumSize[input].length; j++) {
                System.out.printf("%6d ", stadiumSize[input][j]);
            }
            System.out.printf("[ %6.1f, %7.1f ]\n",
                    calculateTotalWeight(stadiumSize[input]),
                    calculateAverageWeight(stadiumSize[input]));
        }
    }

// ---------------------------------------------------------------
    /**
     * Adds musician to band, checks weight constrain.
     * @param keyboard allows input
     * @param stadiumSize gives array
     */
    private static void addMusician(Scanner keyboard, int[][] stadiumSize) {
        System.out.print("Please enter row letter: ");
        char rowLetter = Character.toUpperCase(keyboard.next().charAt(0));
        int row = rowLetter - 'A';

        if (row < 0 || row >= stadiumSize.length) {
            System.out.println("ERROR: Row letter out of range");
            rowLetter = Character.toUpperCase(keyboard.next().charAt(0));
            row = rowLetter - 'A';
        }

        int numRows = stadiumSize[row].length;
        System.out.print("Please enter position number (1 to " + numRows + "): ");
        int position = keyboard.nextInt();

        if (position <= 0 || position > numRows) {
            System.out.println("ERROR: Position number out of range");
            position = keyboard.nextInt();
        }

        System.out.print("Please enter weight (45 to 200): ");
        int weight = keyboard.nextInt();

        if (weight < 45 || weight > 200) {
            System.out.println("ERROR: Weight out of range");
            weight = keyboard.nextInt();
            if (weight < 45 || weight > 200) {
                System.out.println("ERROR: Weight out of range");
                weight = keyboard.nextInt();
            }
        }

        // Check if adding this musician will exceed the maximum allowed weight in the row
        double totalWeight = calculateTotalWeight(stadiumSize[row]);
        double averageWeight = (totalWeight + weight) / numRows; // Include the weight of the musician being added
        if (averageWeight > MAX_WEIGHT) {
            System.out.println("ERROR: Adding this musician will exceed the maximum allowed weight in the row");
            return;
        }

        if (stadiumSize[row][position - 1] != 0) {
            System.out.println("ERROR: There is already a musician there.");
            return;
        }

        stadiumSize[row][position - 1] = weight;
        System.out.println("****** Musician added.");


    } // end of addMusician method
// ---------------------------------------------------------------
    /**
     * Removes a musician from a specified position.
     * @param keyboard allows input
     * @param stadiumSize gives array
     */
    private static void removeMusician(Scanner keyboard, int[][] stadiumSize) {
        System.out.print("Please enter row letter: ");
        char rowLetter = Character.toUpperCase(keyboard.next().charAt(0));
        int row = rowLetter - 'A';

        if (row < 0 || row >= stadiumSize.length) {
            System.out.println("ERROR: Row letter out of range");
            rowLetter = Character.toUpperCase(keyboard.next().charAt(0));
            row = rowLetter - 'A';
        }

        int numRows = stadiumSize[row].length;
        System.out.print("Please enter position number (1 to " + numRows + "): ");
        int position = keyboard.nextInt();

        if (position <= 0 || position > numRows) {
            System.out.println("ERROR: Position number out of range");
            position = keyboard.nextInt();
        }

        if (stadiumSize[row][position - 1] == 0) {
            System.out.println("ERROR: That position is vacant.");
            return;
        }

        stadiumSize[row][position - 1] = 0;
        System.out.println("****** Musician removed.");
    } // end of removeMusician method

// ---------------------------------------------------------------
    /**
     * Calculates the total weight of musicians in a row of the band.
     * @param rowWeights The array containing the weights in a row
     * @return The total weight of musicians in the specified row
     */
    private static double calculateTotalWeight(int[] rowWeights) {
        double total = 0;
        for (int weight : rowWeights) {
            total += weight;
        }
        return total;
    }

// ---------------------------------------------------------------
    /**
     * Calculates the average weight of musicians in a row of the band.
     * @param rowWeights array with the weights of musicians in a row
     * @return The average weight of musicians in the specified row
     */
    private static double calculateAverageWeight(int[] rowWeights) {
        return calculateTotalWeight(rowWeights) / rowWeights.length;
    } // end of calculateAverageWeight method


} //end of the BandOrganizer class