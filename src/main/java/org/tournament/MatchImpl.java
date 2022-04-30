package org.tournament;

import java.util.Optional;

public class MatchImpl implements IMatch {

    private final Team team1;
    private final Team team2;
    private Team looser;
    private Team winner;
    private boolean draw;

    public MatchImpl(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public void match() {
        if (team1.getScore() == team2.getScore()) {
            draw = true;
            team1.setPoints(1);
            team2.setPoints(1);
        }
        if (team1.getScore() > team2.getScore()) {
            winner = team1;
            looser = team2;
            team1.setPoints(3);
        }
        if (team1.getScore() < team2.getScore()) {
            winner = team2;
            looser = team1;
            team2.setPoints(3);
        }
    }

    public Optional<Team> getLooser() {
        return Optional.ofNullable(looser);
    }

    public Optional<Team> getWinner() {
        return Optional.ofNullable(winner);
    }

    public boolean isDraw() {
        return draw;
    }

    public Optional<Team> getTeam1() {
        return Optional.ofNullable(team1);
    }

    public Optional<Team> getTeam2() {
        return Optional.ofNullable(team2);
    }
}
