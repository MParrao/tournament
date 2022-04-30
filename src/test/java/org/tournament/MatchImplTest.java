package org.tournament;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchImplTest {

    @Test
    public void testNoMatchRun() {
        Team t1 = new Team("Chivas", 2);
        Team t2 = new Team("Koras", 3);
        MatchImpl match = new MatchImpl(t1, t2);
        assertFalse(match.getLooser().isPresent());
        assertFalse(match.getWinner().isPresent());
    }

    @Test
    public void testMatchRun() {
        Team t1 = new Team("Chivas", 2);
        Team t2 = new Team("Koras", 3);
        MatchImpl match = new MatchImpl(t1, t2);
        match.match();
        assertTrue(match.getLooser().isPresent());
        assertTrue(match.getWinner().isPresent());
        assertEquals(match.getLooser().get(),t1);
        assertEquals(match.getWinner().get(),t2);
        assertEquals(match.getWinner().get().getPoints(),3);
        assertEquals(match.getLooser().get().getPoints(),0);
    }

    @Test
    public void testDraw() {
        Team t1 = new Team("Chivas", 2);
        Team t2 = new Team("Koras", 2);
        MatchImpl match = new MatchImpl(t1, t2);
        match.match();
        assertFalse(match.getLooser().isPresent());
        assertFalse(match.getWinner().isPresent());
        assertTrue(match.isDraw());
        assertEquals(match.getTeam1().get().getPoints(),1);
        assertEquals(match.getTeam2().get().getPoints(),1);
    }
}
