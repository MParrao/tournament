package org.tournament;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Representation of a tournament execution where multiple {@link  org.tournament.IMatch  matches}
 * are executed
 */
public class Tournament {
    TreeSet<Team> teams = new TreeSet<Team>();
    static String resultFormat = "%s. %s, %s %s";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("This project just supports 1 parameter. It should be the absolute file location");
            System.exit(2);
        }
        if (Files.exists(Path.of(args[0]))) {
            try {
                Tournament tournament = new Tournament();
                String tournamentInfo = readFile(Paths.get(args[0]));
                tournament.startTournament(tournamentInfo);
            } catch (IOException e) {
                System.err.println("There was an issue reading file error=" + e.getLocalizedMessage());
            }
        } else {
            System.err.println("The specified location does not exist or is not accesible");
            System.exit(2);
        }
    }

    /**
     * Updates the score set with the results of the match. If the team didn't existed in this set it gets added,
     * if it previously existed it gets updated.
     * @param iMatch match that was run and has the match results
     */
    protected void updateTree(IMatch iMatch) {
        Team team1 = iMatch.getTeam1().get();
        Team team2 = iMatch.getTeam2().get();
        Optional<Team> maybeTeam1 = teams.stream().filter(team -> team.equals(team1)).findFirst();
        Optional<Team> maybeTeam2 = teams.stream().filter(team -> team.equals(team2)).findFirst();
        if (maybeTeam1.isPresent()) {
            Team setTeam1 = maybeTeam1.get();
            teams.remove(setTeam1);
            setTeam1.setPoints(setTeam1.getPoints() + team1.getPoints());
            teams.add(setTeam1);
        } else {
            teams.add(team1);
        }
        if (maybeTeam2.isPresent()) {
            Team setTeam2 = maybeTeam2.get();
            teams.remove(setTeam2);
            setTeam2.setPoints(setTeam2.getPoints() + team2.getPoints());
            teams.add(setTeam2);
        } else {
            teams.add(team2);
        }

    }

    /**
     * Parses all the matches from the input string, where each line represents a match between 2 teams
     * separated by coma.
     * @param tournamentInfo information of the tournament matches
     * @return List of matches already parsed
     */
    protected List<IMatch> getMatches(String tournamentInfo) {
        return Arrays.stream(tournamentInfo.split(System.lineSeparator()))
                .map(Tournament::parseMatchFromLine)
                .collect(Collectors.toList());
    }

    /**
     * Read file content into a String
     * @param path file path to be read
     * @return string content of the specified file
     * @throws IOException
     */
    protected static String readFile(Path path) throws IOException {
        return Files.readString(path);
    }

    /**
     * Parses a {@link  org.tournament.IMatch  match} from a single line.
     * @param line containing the match of 2 teams, separated by coma
     * @return {@link  org.tournament.IMatch  Match} representation of this line
     */
    protected static IMatch parseMatchFromLine(String line) {
        String[] teams = line.split(",");
        Team team1 = getTeam(teams[0].trim());
        Team team2 = getTeam(teams[1].trim());

        return new MatchImpl(team1, team2);
    }

    /**
     * Retrieves a team from the specific string where the expected format is
     * Team name space score i.e "Team name 5"
     * @param line
     * @return {@link  org.tournament.Team  team} representation from string
     */
    protected static Team getTeam(String line) {
        for (int i = line.length() - 1; i >= 0; i--) {
            if (line.charAt(i) == ' ') {
                String name = line.substring(0, i);
                int score = Integer.parseInt(line.substring(i + 1));
                return new Team(name, score);
            }
        }
        return null;
    }

    /**
     * Executes the tournament to print the results
     * @param tournamentInfo tournament information from all team matches
     */
    protected void startTournament(String tournamentInfo) {
        List<IMatch> matches = getMatches(tournamentInfo);
        matches.forEach(IMatch::match);
        matches.forEach(this::updateTree);
        Iterator<Team> it = this.teams.iterator();
        int previousPoints = -1;
        for (int i = 0, ranking = 1; it.hasNext(); i++, ranking++) {
            Team team = it.next();

            if (i != 0)
                if (team.getPoints() == previousPoints)
                    ranking--;
                else
                    ranking = i + 1;
            System.out.printf((resultFormat) + "%n", ranking, team.getName(), team.getPoints(), (team.getPoints() != 1 ? "pts" : "pt"));
            previousPoints = team.getPoints();
        }

    }
}
