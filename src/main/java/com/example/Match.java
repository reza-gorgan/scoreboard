package com.example;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class Match {

    private final String id;
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private final long startTime;

    public Match(String homeTeam, String awayTeam) {
        this.id = UUID.randomUUID().toString();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = Instant.now().toEpochMilli();
    }

    public int getTotalScore() {
        return homeScore + awayScore; // Total score of the match
    }}
