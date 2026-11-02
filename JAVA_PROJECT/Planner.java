package JAVA_PROJECT;
 
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
 
public class Planner {
 
    private Team[] teams;
    private ArrayList<Game> games;
 
    public Planner() {
        teams = new Team[4];
        games = new ArrayList<Game>();
    }
 
    public Team[] getTeams() {
        return teams;
    }
 
    public ArrayList<Game> getGames() {
        return games;
    }
 
    public void startSeason() {
        Scanner keyboard = new Scanner(System.in);
        Random random = new Random();
 
        // Limits of the temperature, in degrees Celsius
        final int MIN_TEMPERATURE = 0;
        final int MAX_TEMPERATURE = 45;
        final int COLD_LIMIT = 20; // 20 or below: too cold to play
 
        // Ask for the names of the 4 teams
        System.out.println("Enter the names of the 4 teams:");
        for (int i = 0; i < teams.length; i++) {
            String name = "";
            boolean validName = false;
            while (!validName) {
                System.out.print("Name of team " + (i + 1) + ": ");
                name = keyboard.nextLine().trim();
                validName = true;
                if (name.isEmpty()) {
                    System.out.println("The name cannot be empty.");
                    validName = false;
                }
                for (int c = 0; c < name.length(); c++) {
                    if (Character.isDigit(name.charAt(c))) {
                        System.out.println("The name cannot contain numbers.");
                        validName = false;
                        break;
                    }
                }
                for (int k = 0; k < i; k++) {
                    if (teams[k].getName().equalsIgnoreCase(name)) {
                        System.out.println("This name is already used.");
                        validName = false;
                    }
                }
            }
            teams[i] = new Team(name);
        }
        System.out.println();
 
        // Temperature of every week (played or not), used in the final report
        ArrayList<Integer> weekTemperatures = new ArrayList<Integer>();
 
        int coldWeeks = 0;
        boolean seasonOver = false;
 
        int hottest = 0;
        int totalTemperatures = 0;
        int weeksPlayed = 0;
        int week = 0;
        int[] pairingOrder = {0, 1, 2}; // order of the 3 possible pairings
 
        while (!seasonOver) {
            week++;
 
            // Safe reading of the temperature
            int temperature = 0;
            boolean validTemperature = false;
            while (!validTemperature) {
                try {
                    System.out.print("Enter the temperature in Celsius for week " + week + ": ");
                    temperature = keyboard.nextInt();
                    if (temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
                        System.out.println("The temperature must be between "
                                + MIN_TEMPERATURE + " and " + MAX_TEMPERATURE + " °C.");
                    } else {
                        validTemperature = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a whole number.");
                    keyboard.next(); // throw away the wrong word
                }
            }
 
            weekTemperatures.add(temperature);
 
            if (temperature <= COLD_LIMIT) {
                // Cold week
                coldWeeks++;
                System.out.println("Too cold to play. Match skipped until next week.");
                if (coldWeeks == 3) {
                    seasonOver = true;
                    System.out.println("Season over");
                }
                System.out.println();
            } else {
                // Playable week
                coldWeeks = 0;
                weeksPlayed++;
                totalTemperatures += temperature;
                if (temperature > hottest) {
                    hottest = temperature;
                }
 
                // Maximum number of goals depends on the temperature (kept small to stay realistic)
                int maxGoals = temperature / 10;
                if (maxGoals > 4) {
                    maxGoals = 4;
                }
 
                // Pairings: team 0 plays a random opponent,
                // the two other teams play each other
                // Every 3 played weeks, the 3 possible pairings are each used once,
                // in a random order, so every team meets every other team equally often
                int position = (weeksPlayed - 1) % 3;
                if (position == 0) {
                    for (int i = 2; i > 0; i--) {
                        int j = random.nextInt(i + 1);
                        int swap = pairingOrder[i];
                        pairingOrder[i] = pairingOrder[j];
                        pairingOrder[j] = swap;
                    }
                }
                int opponent = pairingOrder[position] + 1;
                int[] firsts = new int[2];
                int[] seconds = new int[2];
                firsts[0] = 0;
                seconds[0] = opponent;
                if (opponent == 1) {
                    firsts[1] = 2;
                    seconds[1] = 3;
                } else if (opponent == 2) {
                    firsts[1] = 1;
                    seconds[1] = 3;
                } else {
                    firsts[1] = 1;
                    seconds[1] = 2;
                }
 
                // The two games of the week
                System.out.println("SCORE");
                for (int m = 0; m < 2; m++) {
 
                    // Coin flip to decide who plays at home
                    int home;
                    int away;
                    if (random.nextInt(2) == 0) {
                        home = firsts[m];
                        away = seconds[m];
                    } else {
                        home = seconds[m];
                        away = firsts[m];
                    }
 
                    // Two draws, keep the smaller one: big scores become rarer
                    int homeScore = Math.min(random.nextInt(maxGoals + 1), random.nextInt(maxGoals + 1));
                    int awayScore = Math.min(random.nextInt(maxGoals + 1), random.nextInt(maxGoals + 1));
 
                    Team homeTeam = teams[home];
                    Team awayTeam = teams[away];
 
                    // Goals scored and conceded
                    homeTeam.setGoalsScored(homeTeam.getGoalsScored() + homeScore);
                    homeTeam.setGoalsConceded(homeTeam.getGoalsConceded() + awayScore);
                    awayTeam.setGoalsScored(awayTeam.getGoalsScored() + awayScore);
                    awayTeam.setGoalsConceded(awayTeam.getGoalsConceded() + homeScore);
 
                    // Win, loss or draw
                    if (homeScore > awayScore) {
                        homeTeam.setWins(homeTeam.getWins() + 1);
                        awayTeam.setLosses(awayTeam.getLosses() + 1);
                    } else if (homeScore < awayScore) {
                        awayTeam.setWins(awayTeam.getWins() + 1);
                        homeTeam.setLosses(homeTeam.getLosses() + 1);
                    } else {
                        homeTeam.setDraws(homeTeam.getDraws() + 1);
                        awayTeam.setDraws(awayTeam.getDraws() + 1);
                    }
 
                    // Show the score of the game
                    System.out.println(homeTeam.getName() + " " + homeScore
                            + " - " + awayScore + " " + awayTeam.getName());
 
                    // Keep a record of the game
                    games.add(new Game(week, homeTeam.getName(), homeScore,
                            awayTeam.getName(), awayScore, temperature));
                }
                System.out.println();
            }
        }
 
        // ----- Display the results -----
        System.out.println("*********RESULTS*********");
        System.out.println();
 
        for (int i = 0; i < teams.length; i++) {
            Team team = teams[i];
            System.out.println(team.getName());
            System.out.println("Wins: " + team.getWins()
                    + ", Losses: " + team.getLosses()
                    + ", Draws: " + team.getDraws());
            System.out.println("Goals scored: " + team.getGoalsScored()
                    + ", Goals conceded: " + team.getGoalsConceded());
            System.out.println();
        }
 
        for (int w = 1; w <= week; w++) {
            System.out.println("Week " + w);
            System.out.println("Temperature: " + weekTemperatures.get(w - 1) + " °C");
            System.out.println("SCORE");
            boolean gamePlayed = false;
            for (Game g : games) {
                if (g.getWeek() == w) {
                    System.out.println(g.getHomeTeamName() + " " + g.getHomeScore()
                            + " - " + g.getAwayScore() + " " + g.getAwayTeamName());
                    gamePlayed = true;
                }
            }
            if (!gamePlayed) {
                System.out.println("Temperature not favorable to play a game");
            }
            System.out.println();
        }
 
        if (weeksPlayed > 0) {
            double average = (double) totalTemperatures / weeksPlayed;
            average = Math.round(average * 10) / 10.0;
            System.out.println("Hottest temperature: " + hottest + " °C");
            System.out.println("Average temperature: " + average + " °C");
        } else {
            System.out.println("No game was played this season.");
        }
    }
}
 