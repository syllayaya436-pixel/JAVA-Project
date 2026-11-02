package JAVA_PROJECT;
 
public class Team {
 
    private String name;
    private int wins;
    private int losses;
    private int draws;
    private int goalsScored;
    private int goalsConceded;
 
    public Team(String name) {
        this.name = name;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
    }
 
    public String getName() {
        return name;
    }
 
    public int getWins() {
        return wins;
    }
 
    public void setWins(int wins) {
        this.wins = wins;
    }
 
    public int getLosses() {
        return losses;
    }
 
    public void setLosses(int losses) {
        this.losses = losses;
    }
 
    public int getDraws() {
        return draws;
    }
 
    public void setDraws(int draws) {
        this.draws = draws;
    }
 
    public int getGoalsScored() {
        return goalsScored;
    }
 
    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }
 
    public int getGoalsConceded() {
        return goalsConceded;
    }
 
    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }
}
 