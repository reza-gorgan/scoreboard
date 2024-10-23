package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Scoreboard {
    private final Map<String, Match> matchMap = new ConcurrentHashMap<>();

    public String startMatch(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        matchMap.put(match.getId(), match);
        return match.getId();
    }

    public void updateScore(String matchId, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }

        matchMap.computeIfPresent(matchId, (key, match) -> {
            match.setHomeScore(homeScore);
            match.setAwayScore(awayScore);
            return match;
        });
    }

    public void finishMatch(String matchId) {
        matchMap.remove(matchId);
    }

    public Map<String, Match> getMatches() {
        return matchMap;
    }

    public List<Match> getSummary() {
        List<Match> matches;
        synchronized (matchMap) {  // to create a consistent snapshot of the current state of the board
            matches = new ArrayList<>(matchMap.values());
        }
        matches.sort((m1, m2) -> {
            int scoreComparison = Integer.compare(m2.getTotalScore(), m1.getTotalScore());
            if (scoreComparison != 0) {
                return scoreComparison;
            }
            return Long.compare(m2.getStartTime(), m1.getStartTime());
        });
        return matches;
    }
}