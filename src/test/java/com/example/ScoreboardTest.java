package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class ScoreboardTest {
    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void testStartMatch() {
        String matchId = scoreboard.startMatch("Mexico", "Canada");
        assertNotNull(matchId);
        Map<String, Match> matches = scoreboard.getMatches();
        assertEquals(1, matches.size());
        assertTrue(matches.containsKey(matchId));
        Match match = matches.get(matchId);
        assertEquals("Mexico", match.getHomeTeam());
        assertEquals("Canada", match.getAwayTeam());
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void testUpdateScore_validZeroOrPositiveScore() {
        String matchId = scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore(matchId, 2, 1);
        Match match = scoreboard.getMatches().get(matchId);
        assertEquals(2, match.getHomeScore());
        assertEquals(1, match.getAwayScore());
    }

    @Test
    void testUpdateScore_inValidNegativeScore() {
        String matchId = scoreboard.startMatch("Spain", "Brazil");
        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore(matchId, -1, -2)
        );
    }

    @Test
    void testFinishMatch() {
        String matchId = scoreboard.startMatch("Germany", "France");
        scoreboard.finishMatch(matchId);
        Map<String, Match> matches = scoreboard.getMatches();
        assertEquals(0, matches.size());
        assertFalse(matches.containsKey(matchId));
    }

    @Test
    void testConcurrentUpdates() throws InterruptedException {
        String matchId = scoreboard.startMatch("Italy", "Argentina");
        Runnable updateTask1 = () -> scoreboard.updateScore(matchId, 3, 2);
        Runnable updateTask2 = () -> scoreboard.updateScore(matchId, 1, 4);
        Thread thread1 = new Thread(updateTask1);
        Thread thread2 = new Thread(updateTask2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Match match = scoreboard.getMatches().get(matchId);
        assertTrue(match.getHomeScore() >= 0); // Ensure the score is not negative
        assertTrue(match.getAwayScore() >= 0);
        assertTrue((match.getHomeScore() == 3 && match.getAwayScore() == 2) ||
                (match.getHomeScore() == 1 && match.getAwayScore() == 4));
    }

    @Test
    void testMatchSummaryOrder() {
        Scoreboard scoreboard = new Scoreboard();
        // Start new matches
        String match1 = scoreboard.startMatch("Mexico", "Canada");
        String match2 = scoreboard.startMatch("Spain", "Brazil");
        String match3 = scoreboard.startMatch("Germany", "France");
        String match4 = scoreboard.startMatch("Uruguay", "Italy");
        String match5 = scoreboard.startMatch("Argentina", "Australia");
        // Update scores
        scoreboard.updateScore(match1, 0, 5);
        scoreboard.updateScore(match2, 10, 2);
        scoreboard.updateScore(match3, 2, 2);
        scoreboard.updateScore(match4, 6, 6);
        scoreboard.updateScore(match5, 3, 1);
        // Get summary and assert the order
        List<Match> summary = scoreboard.getSummary();
        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Mexico", summary.get(2).getHomeTeam());
        assertEquals("Argentina", summary.get(3).getHomeTeam());
        assertEquals("Germany", summary.get(4).getHomeTeam());
    }
}

