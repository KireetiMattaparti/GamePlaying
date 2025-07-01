import java.util.*;
import java.io.*;

public class RedBlueNim{
	
	public static float evaluate(int [] state){
		return state[0] * 2 + state[1] * 3;
	}
	
	public static float[] minMaxAlphaBeta(int[] state, int depth, float alpha, float beta, boolean isMax, int maxDepth)
	{
		if(depth == maxDepth)
		{
			float[] eval = new float[2];
			eval[0] = evaluate(state);
			eval[1] = 0;
			return eval;
		}
		
		if(state[0] == 0 || state[1] == 0)
		{
			float[] eval = new float[2];
			eval[0] = evaluate(state);
			eval[1] = 0;
			return eval;
		}
		
		if(isMax)
		{
			float maxEval = Float.NEGATIVE_INFINITY;
			float bestmove = 0;
			float[] eval = new float[2];
			for(int i=0;i<2;i++)
			{
				if(state[i] > 0)
				{
					state[i] = state[i] - 1;
					eval = minMaxAlphaBeta(state, depth+1, alpha, beta, !isMax, maxDepth);
					if(eval[0] > maxEval)
					{
						maxEval = eval[0];
						bestmove = i;
					}
					alpha = Math.max(alpha,eval[0]);
					state[i] = state[i] + 1;
					if(beta <= alpha)
						break;
				}
			}
			eval[0] = maxEval;
			eval[1] = bestmove;
			return eval;
		}
		else
		{
			float minEval = Float.POSITIVE_INFINITY;
			float bestmove = 0;
			float[] eval = new float[2];
			for(int i=0;i<2;i++)
			{
				if(state[i] > 0)
				{
					state[i] = state[i] - 1;
					eval = minMaxAlphaBeta(state, depth+1, alpha, beta, !isMax, maxDepth);
					if(eval[0] < minEval)
					{
						minEval = eval[0];
						bestmove = i;
					}
					beta = Math.min(beta,eval[0]);
					state[i] = state[i] + 1;
					if(beta <= alpha)
						break;
				}
			}
			eval[0] = minEval;
			eval[1] = bestmove;
			return eval;
		}
		
	}		
	
	public static void redBlueNim(int numRed, int numBlue, String version, String firstPlayer, int depth){
	    int[] state = {numRed,numBlue};
	    String currentPlayer = firstPlayer;
	    while(true)
		{
			if (state[0] == 0 || state[1] == 0)
			{
				if(version.equals("standard"))
				{
					if(currentPlayer.equals("human"))
					{
						System.out.println("Sorry, You lost the Game!");
					}
					else
					{
						System.out.println("Congratulations! You won the Game!");
					}
					int score = state[0] * 2 + state[1] * 3;
					System.out.println("Final Score:" + score);
					break;
				}
				else
				{
					if(currentPlayer.equals("human"))
					{
						System.out.println("Congratulations! You won the Game!");
					}
					else
					{
						System.out.println("Sorry, You lost the Game!");
					}
					int score = state[0] * 2 + state[1] * 3;
					System.out.println("Final Score:" + score);
					break;
				}
			}
			System.out.println("Current State:[" + state[0] + "," + state[1] + "]");
			if(currentPlayer.equals("human"))
			{
				Scanner sc = new Scanner(System.in);
				System.out.print("Choose a pile (red/blue): ");
				String pileChoise = sc.next();
				while(!pileChoise.toLowerCase().equals("red") & !pileChoise.toLowerCase().equals("blue"))
				{
					System.out.print("Invalid input. Please enter Red or Blue: ");
					pileChoise = sc.next();
				}
				if(pileChoise.toLowerCase().equals("red"))
				{
					if(state[0] == 0)
					{
						System.out.println("Sorry, that pile is empty. Please choose the other pile.");
						continue;
					}
					state[0] = state[0] - 1;
				}
				else
				{
                    if(state[1] == 0)
					{
						System.out.println("Sorry, that pile is empty. Please choose the other pile.");
						continue;
					}
					state[1] = state[1] - 1;
				}
				System.out.println("You removed 1 marble from the " + pileChoise + " pile.");
				currentPlayer = "computer";
			}
			else
			{
				System.out.println("Computer's turn...");
				float[] bestmove = minMaxAlphaBeta(state, 0, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, true, depth);
				if(!(version.equals("standard")))
				{
					if(bestmove[1] == 0)
					{
						state[0] = state[0] - 1;
						System.out.println("Computer removed 1 marble from the red pile.");
					}
					else
					{
						state[1] = state[1] - 1;
						System.out.println("Computer removed 1 marble from the blue pile.");
					}
				}
				else
				{
					if(bestmove[1] == 0)
					{
						state[1] = state[1] - 1;
						System.out.println("Computer removed 1 marble from the blue pile.");
					}
					else
					{
						state[0] = state[0] - 1;
						System.out.println("Computer removed 1 marble from the red pile.");
					}
				}
				currentPlayer = "human";
			}
		}
		return;
	}
	
	
    public static void main(String[] args) throws IOException {
		if (args.length < 2 || args.length > 5) {
			System.out.println("Usage: RedBlueNim <num-red> <num-blue> <version=standard> <first-player=computer> <depth>");
			System.exit(1);
		}
		
		int numRed = Integer.parseInt(args[0]);
		int numBlue = Integer.parseInt(args[1]);
		
		String version = args.length >= 3 ? args[2].toLowerCase() : "standard";
		String firstPlayer = args.length >= 4 ? args[3].toLowerCase() : "computer";
		int depth = args.length == 5 ? Integer.parseInt(args[4]) : 0;
		
		redBlueNim(numRed, numBlue, version, firstPlayer, depth);
	}

}