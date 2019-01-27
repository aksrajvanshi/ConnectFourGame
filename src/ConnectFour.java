import java.util.Arrays;
import java.util.Scanner;

public class ConnectFour {
	final int boardWidth = 7;
	final int boardHeight = 6;
	private String [] [] myBoard;
	private final String EMPTY = " ";
	static int totalMoves;
	static int playerSwitchFlag = 1;
	
	/* constructor to initialize the vertical board.
	 * 
	 */
	ConnectFour(){
		myBoard = new String [boardHeight] [boardWidth];
		for (String [] row: myBoard){
			Arrays.fill(row, EMPTY);
		}
		totalMoves = 0;
	}
	
	/** method to print the whole vertical board.
	 * 
	 */
	private void printBoard(){
		
		for(int i=0; i< boardHeight; i++){
			for(int j=0; j< boardWidth; j++){
				System.out.print("  |  " + myBoard[i][j]);
			}
			System.out.print(" | ");
			System.out.println();
		}
	}
	

	/** method to check if the whole board is filled or not.
	 * 
	 * @return boolean value to tell if the board is full or not.
	 */
	private boolean checkFull(){
		return totalMoves == (boardWidth * boardHeight);
	}
	
	/** method to check if a column is full of discs or not.
	 * 
	 * @param column
	 * @return boolean variable
	 */
	private boolean checkColumnFull(int column){
		
		if(column == 0){
			if(!(myBoard[0][column].equals(EMPTY))){
				return true;
			}
		}
		if(!(myBoard[0][column-1].equals(EMPTY))){
			return true;
		}
		return false;
	}
	
	/** method to check winner by checking pegs horizontally, vertically and diagonally
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean checkWinner(int row, int column){
		String pegValue = myBoard [row] [column];
		
        if(checkHorizontalPattern(row, column, pegValue)){
        	return true;
        }
        
        if(checkVerticalPattern(row, column, pegValue)){
        	return true;
        }
        
        if(checkDiagonal(row, column, pegValue)){
        	return true;
        }
        
		return false;
	}
	/** method to check if sequence of similar pegs horizontally
	 * 
	 * @param row
	 * @param column
	 * @param peg
	 * @return
	 */
	private boolean checkHorizontalPattern(int row, int column, String peg){
		
		int i = column;
		int count=0;
		
		while(i < boardWidth && myBoard[row][i].equals(peg)){
            count++;
            i++;
        }
        i= column-1;
        while(i >= 0 && myBoard[row][i].equals(peg)){
            count++;
            i--;
        }
        if(count >= 4){
            return true;
        }
        else{
        	return false;
        }
	}
	
	/**
	 *  method to check sequence of similar pegs vertically
	 * @param row
	 * @param column
	 * @param peg
	 * @return
	 */
	private boolean checkVerticalPattern(int row, int column, String peg){
		
		int count=0;
        int j= row;
        
        while(j < boardHeight && myBoard[j][column] == peg){
            count++;
            j++;
        }
        if(count >= 4)
            return true;
        else
        	return false;
	}
	
	/** method to check sequence of similar pegs in diagonals across the board.
	 * 
	 * @param row
	 * @param column
	 * @param peg
	 * @return
	 */
	private boolean checkDiagonal(int row, int column, String peg){
		
		if(checkForwardDiagonal(row, column, peg)){
			return true;
		}
		
		if(checkBackwardDiagonal(row, column, peg)){
			return true;
		}
		
		return false;
	}
	
	/** method to check similar pegs across the forward diagonal (\)
	 * 
	 * @param row
	 * @param column
	 * @param peg
	 * @return
	 */
	private boolean checkForwardDiagonal(int row, int column, String peg){
		
		int count = 0;
		int i=row;
        int j= column;
        
        while(i < boardHeight && j < boardWidth && myBoard[i][j] == peg){
            count++;
            i++;
            j++;
        }
        i=row-1;
        j=column-1;
        while(i >=0 && j >=0 && myBoard[i][j] == peg){
            count++;
            i--;
            j--;
        }
        if(count >= 4)
            return true;
        else
        	return false;
	}
	
	/** method to check similar pegs across the backward facing diagonal. (/)
	 * 
	 * @param row
	 * @param column
	 * @param peg
	 * @return
	 */
	private boolean checkBackwardDiagonal(int row, int column, String peg){
		
		int count=0;
		int i=row;
        int j= column;
        while(i < boardHeight && j>=0 && myBoard[i][j] == peg){
            count++;
            i++;
            j--;
        }
        i=row-1;
        j=column+1;
        while(i>=0 && j < boardWidth && myBoard[i][j] == peg){
            count++;
            i--;
            j++;
        }
        if(count >= 4)
            return true;
        else
        	return false;
	}
	
	/**
	 * method to insert disc in a column
	 * @param column
	 * @param movePlayed
	 * @return
	 */
	public int insertDisc(int column, String movePlayed){
		int rowInserted = -1;
		if(checkFull()){
			return -2;
		}
		if(checkColumnFull(column)){
			return -1;
		}
		else{
			for(int i = 0; i < boardHeight; i++){
				
				if(!myBoard[i][column-1].equals(EMPTY)){
					myBoard[i-1][column-1] = movePlayed;
					rowInserted = i-1;
					break;
				}
				
				if(i == boardHeight-1){
					myBoard[i][column-1] = movePlayed;
					rowInserted = i;
				}
				
			}
			totalMoves++;
			return rowInserted;
		}
	}
	
	public static void main(String[] args) {
		
		ConnectFour con4 = new ConnectFour();
		Scanner in = new Scanner(System.in);
		
		while(true){
			
			System.out.println(" Welcome Player 1");
			do{
				System.out.println(" Your peg is RED(R). Choose column between 1 and 7!");
				int player1Column = 0;
				int player1Row;
				while(!in.hasNextInt()){
					System.out.println(" Please enter a integer value between 1 and 7! Try again!");
					in.next();
				}
				player1Column = in.nextInt();
				if(player1Column < 1 | player1Column > 7){
					continue;
				}
				
				else{
					player1Row = con4.insertDisc(player1Column, "R");
					if(player1Row == -1){
						System.out.println("This column number is full. Please try again with different column number!");
						continue;
					}
					playerSwitchFlag = 2;
					System.out.println(" Board after player 1 move :");
					con4.printBoard();
				}
				if(con4.checkWinner(player1Row, player1Column-1)){
					playerSwitchFlag = 3;
					System.out.println(" Player 1 Won! ");
					break;
				}
			} while(playerSwitchFlag == 1);
			
			if(playerSwitchFlag == 3){
				System.out.println(" GAME OVER! ");
				break;
			}
			
			System.out.println(" Welcome Player 2");
			do{
				System.out.println(" Your peg is GREEN(G). Choose column between 1 and 7!");
				int player2Column = 0;
				int player2Row;
				while(!in.hasNextInt()){
					System.out.println(" Please enter a integer value between 1 and 7! Try again!");
					in.next();
				}
				player2Column = in.nextInt();
				if(player2Column < 1 | player2Column > 7){
					continue;
				}
				else{
					player2Row = con4.insertDisc(player2Column, "G");
					if(player2Row == -1){
						System.out.println("This column number is full. Please try again with different column number!");
						continue;
					}
					playerSwitchFlag = 1;
					System.out.println(" Board after player 2 move :");
					con4.printBoard();
				}
				if(con4.checkWinner(player2Row, player2Column-1)){
					playerSwitchFlag = 3;
					System.out.println(" Player 2 Won! ");
					break;
				}
			} while(playerSwitchFlag == 2);
			
			if(playerSwitchFlag == 3){
				System.out.println(" GAME OVER! ");
				break;
			}
			
			if(con4.checkFull()){
				System.out.println(" GAME DRAWN! Game has exited! Please start a new game!");
				break;
			}
		}		
	}
}
