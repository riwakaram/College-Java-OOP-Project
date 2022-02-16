
/**
 * Title of the Project: Sudoku
 *
 * Date: Saturday, February 27, 2021
 *
 * @author riwakaram
 *
 * Purpose of this Project:
 * To let the user play the Sudoku game while having a maximum
 * of 5 mistakes allowed
 *
 * List of the methods in this program:
 *
 * 1. displaySudoku:
 * This  method displays a 2D array, the Sudoku grid, with the number of the
 * rows and columns, and the zeros are displayed as spaces
 *
 * 2. generateEmptyElementsArray:
 * This method takes a 2D array and returns a 2D array with elements equal to 1
 * if its corresponding elements in the parameter is different than 0
 * Otherwise, the elements are equal to 0
 *
 * 3. countMissedValues:
 * This method takes a 2D array and returns the number of the values that are
 * equal to zero in the parameter
 * (the return value is the number of values that must be guessed by the player)
 *
 * 4. addValueInACell:
 * This method receives a value to be added and the coordinates of the cell of
 * the Sudoku grid in which the value is to be stored
 * It calls the methods existInRow, existInCol and existInBlock that check the
 * uniqueness of this value in the related row, column and block (3 x 3)
 * If the value doesn't exist in the same row, column and block, it is stored in
 * the cell
 * Otherwise, no change occurs to the grid
 * The return value will be explained in detail later on
 *
 * 5. existInRow:
 * This method checks if its parameter num exists or not in the row (row) of the
 * parameter sud (2D array)
 *
 * 6. existInCol:
 * This method checks if its parameter num exists or not in the column (col) of
 * the parameter sud (2D array)
 *
 * 7. existInBlock:
 * This method checks if its parameter num exists or not in the block related to
 * the parameters row and col
 *
 * 8. runGame:
 * This method is the method that connects all the other methods in order to run
 * the game properly; the game ends when the user wins or loses all of his/her
 * attempts
 *
 * 9. the main method:
 * It contains the 2D array that represents the grid of the Sudoku
 * It calls the method runGame using that array
 *
 */
import java.util.Scanner;

public class Sudoku {

    /*
    Method displaySudoku:
    
    - Purpose:
    This method displays a 2D array, the Sudoku grid, with the number of the 
    rows and columns
    The zeros are displayed as spaces
    
    - Pre-conditions:
    It takes a 2D array as parameter
    The array should (logically) be of dimension [9][9] since it represents a
    Sudoku grid
    
    - Post-conditions:
    This method has no return value
    It will only display the array
     */
    public static void displaySudoku(int sud[][]) {

        /*
        Printing empty spaces to align and indent the display result
        The first line printed will consist of "col" + col number
        The rest will have "Row" + row number before the values
        This is why we need empty spaces for the first line
        (It doesn't have "Row" + row number displayed in it)
         */
        System.out.printf("%8s", "");

        /*
        This loop displays "col" + the column number until the counter col
        reaches the length of the array sud (column length)
         */
        for (int col = 1; col <= sud[0].length; col++) {

            System.out.printf("%-8s", "col" + col);

        }

        //This loop displays the array sud (Sudoku grid) and the row number
        for (int row = 0; row < sud.length; row++) {

            System.out.println(""); //new line for each iteration

            System.out.print("Row" + (row + 1) + "\t"); //row number for each line

            //This loop displays each row
            for (int col = 0; col < sud[0].length; col++) {

                /*
                if the element of index [row][col] is different than 0, print it
                else (equal to 0), print empty spaces
                 */
                if (sud[row][col] != 0) {
                    System.out.printf("%-8d", sud[row][col]);
                } else {
                    System.out.printf("%8s", "");
                }

            }

        }

        System.out.println(""); //new line after the display is done

    }

    /*
    Method generateEmptyElementsArray:
    
    - Purpose:
    Generate a binary (0 and 1) array based on the parameter array
    This method will be used to store emptyElementsArray in the method runGame
    It will be useful to check whether an element of the sudoku grid was already
    assigned or not and if the user already assigned an element or not
    
    - Pre-conditions:
    It takes a 2D array as parameter
    The array should (logically) be of dimension [9][9] since it represents a
    Sudoku grid
    
    - Post-conditions:
    It returns a 2D binary array, containing only zeros and ones (int[][])
    The zeros represent an empty element and the ones mean that the element was
    initially assigned
     */
    public static int[][] generateEmptyElementsArray(int[][] sud) {

        int numRow = sud.length; //the number of rows of the parameter 2D array
        int numCol = sud[0].length; //the number of columns of the parameter 2D array

        //binary sudoku array with same dimension as the parameter array:
        int[][] binSud = new int[numRow][numCol];

        /*
        These nested for loops store zeros and ones in the binary sudoku array
        If an element of the parameter array is different than 0, we store 1 in
        the corresponding element of binSud
        If it was 0 (else), we store 0
         */
        for (int row = 0; row < numRow; row++) {

            for (int col = 0; col < numCol; col++) {

                if (sud[row][col] != 0) {
                    binSud[row][col] = 1;
                } else {
                    binSud[row][col] = 0;
                }

            }

        }

        return binSud;

    }

    /*
    Method countMissedValues:
    
    - Purpose:
    This method counts the number of empty elements (0) in the Sudoku grid
    This number will be the number of values the player must input into the grid
    
    - Pre-conditions;
    It takes a 2D array as parameter
    The array should (logically) be of dimension [9][9] since it represents a
    Sudoku grid
    
    - Post-conditions:
    It returns the number of guesses (integer)
     */
    public static int countMissedValues(int[][] sud) {

        int numGuess = 0; //the number of empty values, of guesses

        /*
        These nested for loops will search the parameter array for elements with
        value 0
        If it finds any, it increments numGuess by 1 each time
         */
        for (int row = 0; row < sud.length; row++) {

            for (int col = 0; col < sud[0].length; col++) {

                if (sud[row][col] == 0) {
                    numGuess++;
                }

            }

        }

        return numGuess;

    }

    /*
    Method addValueInACell:
    
    - Purpose:
    This method will add a value entered by the user in a certain element, with
    the row and column chosen by the user, under the conditions of methods 
    existInRow, existInColumn and existInBlock
    
    - Pre-conditions:
    It takes the integer value the user wants to add, the row and column where
    the value must be added and the 2D array (sudoku grid)
    The array should (logically) be of dimension [9][9] since it represents a
    Sudoku grid
    
    - Post-conditions:
    It will return:
    0 if the value num is stored in a cell that wasn’t assigned yet by the user
    1 if the value num is stored in a cell that was previously assigned by the
    user and that the user wants to change its value
    2 if the value num exists already in another cell of the same row
    3 if the value num exists already in another cell of the same column
    4 if the value num exists already in another cell of the same block
     */
    public static int addValueInACell(int num, int row, int col, int[][] sud) {

        if (existInRow(num, row, sud)) {
            return 2;
        }

        if (existInCol(num, col, sud)) {
            return 3;
        }

        if (existInBlock(num, row, col, sud)) {
            return 4;
        }

        /*
        If the method runs until here, it means that the value num does not exist
        in the same row, column or block, and that means that we can store it in
        the array
        This is why there is no need to put these conditions on num anymore
         */
        if (sud[row][col] == 0) { //if it was zero, it means it was empty

            sud[row][col] = num;
            return 0;

        } else {                  //else, it is being reassigned by the user

            sud[row][col] = num;
            return 1;

        }

    }

    /*
    Method existInRow:
    
    - Purpose:
    To check whether a certain value entered by the user already exists in the
    same row as the row where the user wants to insert that value
    
    - Pre-conditions:
    It takes the integer value num, the row where num may be stored and the 2D
    array (sudoku grid)
    The array should (logically) be of dimension [9][9] since it represents a
    Sudoku grid
    
    -Post-conditions:
    It returns a boolean expression
    If the value num already exists in the same row, it returns true
    If not, it returns false
     */
    public static boolean existInRow(int num, int row, int[][] sud) {

        /*
        This for loop searches, in the row of the value num, for an element
        equal to num
         */
        for (int col = 0; col < sud[0].length; col++) {

            if (num == sud[row][col]) {
                return true;
            }

        }

        return false;

    }

    /*
    Method existInCol:
    
    - Purpose:
    To check whether a certain value entered by the user already exists in the
    same column as the column where the user wants to insert that value
    
    - Pre-conditions:
    It takes the integer value num, the column where num may be stored and the
    2D array (sudoku grid)
    The array should (logically) be of dimension [9][9] since it represents a
    Sudoku grid
    
    -Post-conditions:
    It returns a boolean expression
    If the value num already exists in the same column, it returns true
    If not, it returns false
     */
    public static boolean existInCol(int num, int col, int[][] sud) {

        /*
        This for loop searches, in the column of the value num, for an element
        equal to num
         */
        for (int row = 0; row < sud.length; row++) {

            if (num == sud[row][col]) {
                return true;
            }

        }

        return false;

    }

    /*
    Method existInCol:
    
    - Purpose:
    To check whether a certain value entered by the user already exists in the
    same block (3x3) as the block where the user wants to insert that value
    
    - Pre-conditions:
    It takes the integer value num, the row and column where num may be stored
    and the 2D array (sudoku grid)
    The array should (logically) be of dimension [9][9] since it represents a
    Sudoku grid
    
    -Post-conditions:
    It returns a boolean expression
    If the value num already exists in the same block, it returns true
    If not, it returns false
     */
    public static boolean existInBlock(int num, int row, int col, int[][] sud) {

        /*
        We need to search in the block of a certain element in the array
        This is why we need to find the indexes of the element at the top left
        corner of the block where the value num may be stored
        For that, we will create two new integer values: newRow and newCol
        The formula row/col - (row/col % 3) was derived from the logic behind 
        what was explained previously
         */
        int newRow = row - (row % 3);
        int newCol = col - (col % 3);

        /*
        These nested for loops will search the block (3x3) where the value num
        may be added
        This is why we search from newRow to newRow + 2 and newCol to newCol + 2
         */
        for (int r = newRow; r <= (newRow + 2); r++) {

            for (int c = newCol; c <= (newCol + 2); c++) {

                if (num == sud[r][c]) {
                    return true;
                }

            }

        }

        return false;

    }

    /*
    Method runGame:
    
    - Purpose:
    This method will run the Sudoku game until the player wins or loses
    
    - Pre-conditions:
    It takes a 2D array as parameter
    The array should (logically) be of dimension [9][9] since it represents a
    Sudoku grid
    
    - Post-conditions:
    This method will interact with the player until the game ends (player wins
    or loses)
     */
    public static void runGame(int[][] sud) {

        Scanner input = new Scanner(System.in);

        int theValue; //value to be added to the grid
        int rowNumber; //row number (must be between 1 and 9)
        int colNumber; //column number (must be between 1 and 9)
        int resultOfAddValueInACell; //store the return value of the method addValueInACell
        int nbOfErrors = 0; //number of errors committed by the player (0 at first)
        int nbrOfAllowedErrors = 5; //number of errors allowed per game
        int[][] emptyElementsArray = new int[9][9]; //2D array to store the binary array
        int nbrOfMissingDigits; //number of digits to be guessed by the player

        //emptyElementsArray becomes the corresponding binary array to sud
        emptyElementsArray = generateEmptyElementsArray(sud);

        //nbrOfMissingDigits will be the number of elements to be entered by the user
        nbrOfMissingDigits = countMissedValues(sud);

        displaySudoku(sud);

        /*
        This do..while represents the interaction between the code and the user
        It breaks when the game ends (player wins or loses)
         */
        do {

            /*
            This do...while will prompt the user to enter a row and column
            number until valid values are entered (between 1 and 9)
            Unless they are valid, the loop will go on
            It breaks only when the values are accepted
             */
            do {

                /*
                This do...while will prompt the user to enter the row number
                until a valid value between 1 and 9 is entered
                If it isn't valid, a corresponding message will be printed and
                the loop will go on
                It breaks only when the values are accepted
                 */
                do {

                    System.out.println("Enter the row (between 1 and 9): ");
                    rowNumber = input.nextInt();

                    if (rowNumber < 1 || rowNumber > 9) {
                        System.out.println("Invalid row number.");
                    }

                } while (rowNumber < 1 || rowNumber > 9);

                /*
                This do...while will prompt the user to enter the column number
                until a valid value between 1 and 9 is entered
                If it isn't valid, a corresponding message will be printed and
                the loop will go on
                It breaks only when the values are accepted
                 */
                do {

                    System.out.println("Enter the column (between 1 and 9): ");
                    colNumber = input.nextInt();

                    if (colNumber < 1 || colNumber > 9) {
                        System.out.println("Invalid column number.");
                    }

                } while (colNumber < 1 || colNumber > 9);

                /*
                If the element, with indexes the row and column the user
                entered, is initially assigned, an appropriate message will
                be printed and the loop will go on
                Else, the loop will end
                 */
                if (emptyElementsArray[rowNumber - 1][colNumber - 1] == 1) {
                    System.out.println("The element [" + rowNumber + "][" + colNumber + "] is initially assigned.");
                }

            } while (emptyElementsArray[rowNumber - 1][colNumber - 1] == 1);

            /*
            This do...while will prompt the user to enter the value to be added
            until a valid value between 1 and 9 is entered
            If it isn't valid, a corresponding message will be printed and the
            loop will go on
            It breaks only when the values are accepted
             */
            do {

                System.out.println("Enter the value you want to add (between 1 and 9): ");
                theValue = input.nextInt();

                if (theValue < 1 || theValue > 9) {
                    System.out.println("Invalid number.");
                }

            } while (theValue < 1 || theValue > 9);

            /*
            If the code runs until here, it means that the row, column and value
            were valid and accepted
            This is why we can now call the method addValueInACell with theValue,
            rowNumber and colNumber bothe - 1(indexes) and the array sud, to check
            if it can be added or not
            The return value is stored in resultOfAddValueInACell
             */
            resultOfAddValueInACell = addValueInACell(theValue, rowNumber - 1, colNumber - 1, sud);

            /*
            This switch statement checks which case is the resultOfAddValueInACell
            For case 0, we decrement by 1 the nbrOfMissingDigits because the 
            value was added to the sudoku grid and one empty space was filled
            For case 1, nothing happens (didn't include it to the statement)
            For case 2, 3 and 4, appropriate messages will be printed according
            to the error found (exists in the same row, column or block) and 
            nbOfErrors will be incremented by 1 because the player made an error
             */
            switch (resultOfAddValueInACell) {
                case 0:
                    nbrOfMissingDigits--;
                    break;
                case 2:
                    System.out.println("The value exists in the same row.");
                    nbOfErrors++;
                    System.out.println("Number of errors is " + nbOfErrors + " out of 5.");
                    break;
                case 3:
                    System.out.println("The value exists in the same column.");
                    nbOfErrors++;
                    System.out.println("Number of errors is " + nbOfErrors + " out of 5.");
                    break;
                case 4:
                    System.out.println("The value exists in the same block.");
                    nbOfErrors++;
                    System.out.println("Number of errors is " + nbOfErrors + " out of 5.");
                    break;
            }

            /*
            This if statement checks whether the player lost the game or not by
            comparing nbOfErrors to the allowed number, initialized to 5
            If it is greater, an appropriate message will be printed and the
            loop will break
            If not, the game continues
             */
            if (nbOfErrors > nbrOfAllowedErrors) {
                System.out.println("You have exceeded the number of allowed errors. You lost.");
                break;
            }

            /*
            This if statement checks whether the player won the game or not by
            comparing nbrOfMissingDigits to 0
            If it is equal to 0, an appropriate message will be printed and the
            loop will break
            If not, the game continues
             */
            if (nbrOfMissingDigits == 0) {
                System.out.println("You have finished the game. You won.");
                break;
            }

            displaySudoku(sud); //diplay the grid for each iteration

        } while (true);

    }

    /*
    Main method:
    
    It contains the 2D array (sudoku grid) and it calls the method runGame using
    that array
     */
    public static void main(String[] args) {

        int[][] sudoku = {
            {9, 0, 3, 5, 6, 0, 7, 0, 4},
            {0, 5, 4, 1, 0, 7, 0, 8, 6},
            {6, 8, 7, 0, 0, 2, 5, 1, 0},
            {0, 0, 5, 9, 2, 0, 1, 7, 0},
            {7, 2, 0, 8, 0, 1, 9, 4, 3},
            {0, 9, 0, 0, 7, 3, 6, 0, 2},
            {1, 0, 2, 0, 3, 4, 8, 0, 0},
            {4, 0, 9, 6, 0, 0, 0, 0, 1},
            {5, 3, 8, 2, 0, 9, 0, 6, 7}};

        /*int [][] solutionGrid = {
		{9, 1, 3, 5, 6, 8, 7, 2, 4},
		{2, 5, 4, 1, 9, 7, 3, 8, 6},
		{6, 8, 7, 3, 4, 2, 5, 1, 9}, 
		{3, 4, 5, 9, 2, 6, 1, 7, 8},
		{7, 2, 6, 8, 5, 1, 9, 4, 3}, 
		{8, 9, 1, 4, 7, 3, 6, 5, 2}, 
		{1, 6, 2, 7, 3, 4, 8, 9, 5}, 
		{4, 7, 9, 6, 8, 5, 2, 3, 1}, 
		{5, 3, 8, 2, 1, 9, 4, 6, 7}};*/
        runGame(sudoku);

    }

}
