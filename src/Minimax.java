import java.util.*;
import java.io.IOException;

class Minimax
{
  static char userMove = 'O';
  static char sysMove = 'X';
  Scanner in;

  Minimax()
  {
    in = new Scanner(System.in);
    run();
  }


  public static void main(String[] args)
  {
    new Minimax();
  }

  public void run()
  {
    //initialize board
    char[] board = new char[9];
    for(int i = 0; i < 9; ++i)
    {
      board[i] = Character.forDigit(i+1, 10);
    }

    //start out game
    System.out.println("press Enter to accept the battle of a lifetime");
    try
    {
         System.in.read();
    }
    catch (IOException e) {
         e.printStackTrace();
    }
    System.out.println("Ladieeees and Gentlemennnnn... LETS GET READY TO RUMBLEEEEEEEEE");

      //pick who goes first
      int firstPlayer = 0;
      while(!(firstPlayer == 1 || firstPlayer == 2))
      {
        System.out.println("Who do you want to go first, you(press 1) or the computer(press 2)?");
        firstPlayer = in.nextInt();
        if(!(firstPlayer == 1 || firstPlayer == 2))
          System.out.println("Please enter 1 (for you to move first) or 2 (for the computer to move frist)");
      }
      System.out.println();

      //play game
      print(board);
      if(firstPlayer == 1)
      {
        while(!isBoardFull(board) && evaluateScore(board) == 0)
        {
          makeMove(board,0,userTurn(board));
          print(board);
          if(isBoardFull(board) || evaluateScore(board) != 0) break;
          System.out.println("Computer move");
          makeMove(board,1,bestMove(board));
          print(board);
        }
      }
      else
      {
        while(!isBoardFull(board) && evaluateScore(board) == 0)
        {
          System.out.println("Computer move");
          makeMove(board,1,bestMove(board));
          print(board);
          if(isBoardFull(board) || evaluateScore(board) != 0) break;
          makeMove(board,0,userTurn(board));
          print(board);
        }
      }

      //print the winner
      if(evaluateScore(board) == 10) System.out.println("Computer wins, sorry!");
      else if(evaluateScore(board) == -10) System.out.println("YOU WIN!");
      else System.out.println("Cats Game.");
  }




  public int minimax(char[] board, int depth, boolean isMax)
  {
    int score = evaluateScore(board);
    if(score ==10) return score - depth;
    else if(score == -10) return score + depth;
    else if(isBoardFull(board)) return 0;


    if(isMax)
    {
      int best = -10000;
      for(int i = 0; i < 9; ++i)
      {
        if(board[i] == Character.forDigit(i+1, 10))
        {
          board[i] = sysMove;
          best = Math.max(best, minimax(board, depth+1, !isMax));
          board[i] = Character.forDigit(i+1, 10);
        }
      }
      return best;
    }
    else
    {
      int best = 10000;
      for(int i = 0; i < 9; ++i)
      {
        if(board[i] == Character.forDigit(i+1, 10))
        {
          board[i] = userMove;
          best = Math.min(best, minimax(board, depth+1, !isMax));
          board[i] = Character.forDigit(i+1, 10);
        }
      }
      return best;
    }
  }


  public int bestMove(char[] board)
  {
    int maxUtility = -10000;
    int maxMove = -1;
    for(int i = 0; i < 9; ++i)
    {
      if(board[i] == Character.forDigit(i+1, 10))
      {
        board[i] = sysMove;
        int moveUtility = minimax(board, 0, false);
        //System.out.println("move: " + (i+1) + " utility: " + moveUtility);
        board[i] = Character.forDigit(i+1, 10);

        if(moveUtility > maxUtility)
        {
            maxMove = i;
            maxUtility = moveUtility;
        }
      }
    }
    return maxMove;
  }

  public int evaluateScore(char[] board)
  {
    //horizantal wins
    if(board[0] == board[1] && board[1] == board[2])
    {
      if(board[0] == sysMove) return 10;
      else return -10;
    }

    if(board[3] == board[4] && board[4] == board[5])
    {
      if(board[3] == sysMove) return 10;
      else return -10;
    }

    if(board[6] == board[7] && board[7] == board[8])
    {
      if(board[6] == sysMove) return 10;
      else return -10;
    }

    //vertical wins
    if(board[0] == board[3] && board[3] == board[6])
    {
      if(board[0] == sysMove) return 10;
      else return -10;
    }

    if(board[1] == board[4] && board[4] == board[7])
    {
      if(board[1] == sysMove) return 10;
      else return -10;
    }

    if(board[2] == board[5] && board[5] == board[8])
    {
      if(board[2] == sysMove) return 10;
      else return -10;
    }

    //diagonal wins
    if(board[0] == board[4] && board[4] == board[8])
    {
      if(board[0] == sysMove) return 10;
      else return -10;
    }

    if(board[2] == board[4] && board[4] == board[6])
    {
      if(board[2] == sysMove) return 10;
      else return -10;
    }
    return 0;
  }

  public boolean isBoardFull(char[] board)
  {
    for(int i = 0; i < 9; ++i)
    {
      if(board[i] == Character.forDigit(i+1, 10)) return false;
    }
    return true;
  }

  public int userTurn(char[] board)
  {
    System.out.print("Enter your move: ");
    int move = in.nextInt();
    move-=1;
    if(move < 0 || move > 8)
    {
      System.out.println("invalid move, please pick a value between 1 and 9");
      userTurn(board);
    }
    else if(board[move] == userMove || board[move] == sysMove)
    {
      System.out.println("That space is already taken, please pick again");
      userTurn(board);
    }

    return move;

  }

 //player =0 then human.......player = 1 then computer
  public void makeMove(char[] board, int player, int pos)
  {
    char move;
    if (player ==1) move = sysMove;
    else move = userMove;
    board[pos] = move;
  }

  public void print(char[] board)
  {
    System.out.println("---------------");
    System.out.println(" " + board[0] + " | " + board[1] + " | " + board[2]+" ");
    System.out.println("---+---+---");
    System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]+" ");
    System.out.println("---+---+---");
    System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8]+" ");
    System.out.println("---------------");
  }



}
