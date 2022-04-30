package org.tournament;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test for simple App.
 */
public class TournamentTest {
    Tournament tournament;

    @BeforeEach
    public void setup() {
        tournament = new Tournament();
    }

    @Test
    public void testGetTeam() {
        Team team = Tournament.getTeam("Lions 1");
        assertNotNull(team);
        assertEquals(team.getName(), "Lions");
        assertEquals(team.getScore(), 1);
    }

    @Test
    public void testParseTeamFromLine() {
        IMatch match = Tournament.parseMatchFromLine("Tarantulas 3, Snakes 1");
        assertNotNull(match);
        assertTrue(match.getTeam1().isPresent());
        assertTrue(match.getTeam2().isPresent());
        assertEquals(match.getTeam1().get().getName(), "Tarantulas");
        assertEquals(match.getTeam1().get().getScore(), 3);
        assertEquals(match.getTeam2().get().getName(), "Snakes");
        assertEquals(match.getTeam2().get().getScore(), 1);
    }

    @Test
    public void testReadFile() {
        try {
            String input = Tournament.readFile(Paths.get("src", "test", "resources", "tournament.txt"));
            assertEquals(TestData.sampleInput, input);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testGetMatches() {
        List<IMatch> matches = tournament.getMatches(TestData.sampleInput);
        assertNotNull(matches);
        assertEquals(5, matches.size());
    }

    @Test
    public void testUpdateTree() {
        Team t1 = new Team("myName", 1);
        Team t2 = new Team("myName2", 2);
        Team t3 = new Team("myName3", 2);
        tournament.updateTree(new IMatch() {

            @Override
            public void match() {
                fail();
            }

            @Override
            public Optional<Team> getTeam1() {
                return Optional.of(t1);
            }

            @Override
            public Optional<Team> getTeam2() {
                return Optional.of(t2);
            }
        });
        assertTrue(tournament.teams.contains(t1));
        assertTrue(tournament.teams.contains(t2));
        assertFalse(tournament.teams.contains(t3));
    }

    @Test
    public void testTournament() {
        assertDoesNotThrow(() -> tournament.startTournament(TestData.sampleInput));
    }
}
