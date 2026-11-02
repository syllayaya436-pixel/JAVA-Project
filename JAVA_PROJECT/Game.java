package JAVA_PROJECT;
 
public class Game {
 
    // One counter shared by all games
    private static int counter = 0;
 
    private int id;
    private int week;
    private String homeTeamName;
    private int homeScore;
    private String awayTeamName;
    private int awayScore;
    private int temperature;
 
    public Game(int week, String homeTeamName, int homeScore, String awayTeamName, int awayScore, int temperature) {
        counter++;
        this.id = counter;
        this.week = week;
        this.homeTeamName = homeTeamName;
        this.homeScore = homeScore;
        this.awayTeamName = awayTeamName;
        this.awayScore = awayScore;
        this.temperature = temperature;
    }
 
    public int getId() {
        return id;
    }
 
    public int getWeek() {
        return week;
    }
 
    public String getHomeTeamName() {
        return homeTeamName;
    }
 
    public int getHomeScore() {
        return homeScore;
    }
 
    public String getAwayTeamName() {
        return awayTeamName;
    }
 
    public int getAwayScore() {
        return awayScore;
    }
 
    public int getTemperature() {
        return temperature;
    }
}
 