package com.example;

import java.util.*;

public class Scoreboard {
    public String startMatch(String homeTeam, String awayTeam) {
        // To be implemented
        return UUID.randomUUID().toString();
    }
    public void updateScore(String matchId, int homeScore, int awayScore) {
        // To be implemented
    }
    public void finishMatch(String matchId) {
        // To be implemented
    }
    public Map<String, Match> getMatches() {
        // To be implemented
        return Collections.emptyMap();
    }

    public List<Match> getSummary() {
        // To be implemented
        return Collections.emptyList();
    }
}
