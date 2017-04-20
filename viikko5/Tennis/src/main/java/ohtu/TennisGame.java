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

        if (p1score == p2score) {
            if (p1score < 4) {
              return scoreToText.get(p1score) + "-All";
            }
            return "Deuce";
        }
        else if (p1score >= 4 || p2score >= 4) {
          int result = p1score - p2score;
          if (result == 1) {
            return "Advantage " + player1Name;
          }
          if (result == -1) {
            return "Advantage " + player2Name;
          }
          if (result >= 2) {
            return "Win for " + player1Name;
          }
          if (result <= -2) {
            return "Win for " + player2Name;
          }
        }
          return scoreToText.get(p1score) + "-" + scoreToText.get(p2score);
    }
}
