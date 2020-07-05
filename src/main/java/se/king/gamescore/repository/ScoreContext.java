package se.king.gamescore.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import se.king.gamescore.config.Configs;

/**
 * Create and manage scores
 *
 * @author Sándor Németh
 * @date 01.07.2020
 */
public class ScoreContext
{
    private static ScoreContext instance;
    // levelId, map of user with the highest score on the given level
    private Map<Integer, ConcurrentSkipListMap<Integer, Integer>> scoreMap;

    /**
     * Constructor is private to hide the public one
     * because this class is a Singleton
     */
    private ScoreContext()
    {
        this.scoreMap = new ConcurrentHashMap<>();
    }

    /**
     * Gets or creates an instance of the ScoreContext.
     *
     * @return {@link ScoreContext} instance.
     */
    public static synchronized ScoreContext getInstance()
    {
        if (instance == null)
        {
            instance = new ScoreContext();
        }
        return instance;
    }

    public synchronized void addScore(int levelId, int userId, int newScore)
    {
        ConcurrentSkipListMap<Integer, Integer> levelMap =
            scoreMap.computeIfAbsent(levelId, k -> new ConcurrentSkipListMap<>());

        if (levelMap.containsKey(userId))
        {
            // User has already a score on the given level
            // Add new score only if it is greater than the stored one
            if (newScore > levelMap.get(userId))
            {
                levelMap.replace(userId, newScore);
            }
        }
        else
        {
            // New score for user
            levelMap.put(userId, newScore);
        }
    }

    public String getHighScores(int levelId)
    {
        ConcurrentSkipListMap<Integer, Integer> levelMap = scoreMap.get(levelId);

        if (levelMap != null)
        {
            return levelMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .map(s -> s.getValue() + "=" + s.getKey())
                .limit(Configs.HIGH_SCORE_LIMIT)
                .collect(Collectors.joining(","));
        }

        return "";
    }
}
