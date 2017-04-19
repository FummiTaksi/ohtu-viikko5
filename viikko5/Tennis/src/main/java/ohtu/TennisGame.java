package ohtu;

import java.util.*;

public class TennisGame {

    private int p1score = 0;
    private int p2score = 0;
    private String player1Name;
    private String player2Name;
    private HashMap<Integer,String> scoreToText;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        scoreToText = new HashMap();
        scoreToText.put(0,"Love");
        scoreToText.put(1,"Fifteen");
        scoreToText.put(2,"Thirty");
        scoreToText.put(3,"Forty");

    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name)) {
          p1score++;
        }
        else if (playerName.equals(player2Name)) {
          p2score++;
        }
     }

    public String getScore() {
        String score = "";
        int tempScore=0;
        if (p1score == p2score) {
            if (p1score < 4) {
              return scoreToText.get(p1score) + "-All";
            }
            return "Deuce";
        }


        else if (p1score>=4 || p2score>=4)
        {
            int minusResult = p1score - p2score;
            if (minusResult==1) score ="Advantage player1";
            else if (minusResult ==-1) score ="Advantage player2";
            else if (minusResult>=2) score = "Win for player1";
            else score ="Win for player2";
        }
        else
        {
            for (int i=1; i<3; i++)
            {
                if (i==1) tempScore = p1score;
                else { score+="-"; tempScore = p2score;}
                switch(tempScore)
                {
                    case 0:
                        score+="Love";
                        break;
                    case 1:
                        score+="Fifteen";
                        break;
                    case 2:
                        score+="Thirty";
                        break;
                    case 3:
                        score+="Forty";
                        break;
                }
            }
        }
        return score;
    }
}
