package org.tournament;

import java.util.Optional;

public interface IMatch {

    void match();

     Optional<Team> getTeam1();

     Optional<Team> getTeam2();
}
