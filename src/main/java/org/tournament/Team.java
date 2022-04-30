package org.tournament;

import java.util.Objects;

public class Team implements Comparable<Team> {

    private final String name;
    private int score;
    private int points;

    public Team(String name) {
        this.name = name;
    }
    public Team(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int compareTo(Team team) {
        if (this.points == team.points)
            return  this.name.compareTo(team.name);
        return Integer.compare(team.points, this.getPoints());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return getName().equals(team.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", points=" + points +
                '}';
    }
}
