# Live Scoreboard

## Overview
This project is a simple **Live Scoreboard** library that tracks ongoing matches and their scores. The scoreboard supports starting a new match, updating the score, finishing a match, and providing a summary of all ongoing matches sorted by the total score and the start time.
## Features
- **Start a New Match**: Adds a match to the scoreboard with an initial score of 0-0.
- **Update Match Score**: Updates the score of a specific match with provided home and away scores.
- **Finish Match**: Ends a match and removes it from the scoreboard.
- **Get Summary**: Returns a summary of all ongoing matches, ordered by:
    - Total score in descending order.
    - For matches with the same total score, they are sorted by start time (most recently started first).
## Assumptions
- **No Negative Scores**: Matches cannot have negative scores. An `IllegalArgumentException` will be thrown if an attempt is made to set negative scores.
- **UUID for Match Identification**: Each match is identified uniquely by a UUID, which is returned when a match is started.
- **Thread Safety**: The scoreboard is designed to be thread-safe using `ConcurrentHashMap` for match storage and `Synchronized` for fine-grained synchronization.
- **Date Representation**: The start time of a match is represented in epoch milliseconds (`long`) to ensure efficient comparisons and sorting.
- **Concurrency Handling**: The solution is built to handle concurrent operations (e.g., updating scores or starting/finishing matches from multiple threads).
## Technologies Used
- **Java 17**
- **Lombok**: To simplify the creation of model classes (e.g., `@Data`, `@Builder`, `@NonNull`).
- **JUnit 5**: For unit testing.
- **Maven**: For project management and dependency resolution.
## Example Usage
```java
public class Main {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();
        // Start matches
        String match1 = scoreboard.startMatch("Italy", "Argentina");
        String match2 = scoreboard.startMatch("Brazil", "Germany");
        // Update scores
        scoreboard.updateScore(match1, 3, 2);
        scoreboard.updateScore(match2, 1, 4);
        // Get summary
        List<Match> summary = scoreboard.getSummary();
        summary.forEach(match -> System.out.println(match.toString()));
        // Finish a match
        scoreboard.finishMatch(match1);
    }
}
