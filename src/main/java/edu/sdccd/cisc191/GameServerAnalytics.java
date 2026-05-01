package edu.sdccd.cisc191;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameServerAnalytics {

    public static List<String> findTopNUsernamesByRating(Collection<PlayerAccount> players, int n) {
        // TODO: use a stream pipeline
        List<String> topPlayers = players.stream() //Create stream
                .sorted(Comparator.comparingInt(PlayerAccount :: rating).reversed()) //Order them by descending rating
                .limit(n) //Limit the amount of PlayerAccounts on the stream
                .map(PlayerAccount :: username) //Add them by name
                .toList(); //Make it a list
        return topPlayers;
    }

    public static Map<String, Double> averageRatingByRegion(Collection<PlayerAccount> players) {
        // TODO: use groupingBy + averagingInt
        Map<String, Double> ratingByRegion = players.stream() //Makes the stream
                .collect(Collectors.groupingBy( PlayerAccount:: region, //Groups by region (key)
                        Collectors.averagingInt(PlayerAccount :: rating))); //Average of ratings per region (value)
        return ratingByRegion;
    }

    public static Set<String> findDuplicateUsernames(Collection<PlayerAccount> players) {
        // TODO: use collections and/or streams
        Set<String> duplicateUsernames = players.stream().collect(
                Collectors.groupingBy(
                        PlayerAccount::username, //Key
                        Collectors.counting())//Value
                ) //Map<String,Long>
                .entrySet()//Set<Entry<>>
                .stream()//Stream<Entry<>>
                .filter(playerCount -> playerCount.getValue() > 1)
                //Checks if the Value mapped is higher than 1
                .map(Map.Entry::getKey)//Stream<String>
                //Gets the keys (usernames) of the players with repeating usernames
                .collect(Collectors.toSet());
        return duplicateUsernames;
    }

    public static Map<String, List<String>> groupUsernamesByTier(Collection<PlayerAccount> players) {
        // TODO: use groupingBy and mapping
        return players.stream().collect(
                Collectors.groupingBy(
                        GameServerAnalytics :: tierFor,
                        Collectors.mapping(
                                PlayerAccount::username, Collectors.toList())));
    }

    public static Map<String, List<String>> buildRecentMatchSummariesByPlayer(Collection<MatchRecord> matches) {
        // TODO: use a Map + collection logic or a stream-based approach

        //Map of the matches for playerTwo
        Map<String, List<String>> matchSum1 = matches.stream()
                .collect(
                        Collectors.groupingBy( match -> match.playerOne().username(),
                        Collectors.mapping(MatchRecord::summary,
                                            Collectors.toList())));

        //Map of the matches for playerTwo
        Map<String, List<String>> matchSum2 = matches.stream()
                .collect(
                        Collectors.groupingBy( match -> match.playerTwo().username(),
                        Collectors.mapping(MatchRecord::summary,
                                            Collectors.toList())));

        //Merging both Maps
        Map<String, List<String>> mergedMap = Stream.concat(
                        matchSum1.entrySet().stream(),
                        matchSum2.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, //Key (stays the same)
                        entry -> new ArrayList<>(entry.getValue()),
                        (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        }
                ));

        return mergedMap;


    }

    public static <T> T pickHigherRated(T first, T second, Comparator<T> comparator) {
        // TODO: implement using the comparator
        /*
         * compare() returns 0 if the numbers are equal and a positive number if
         * the first number is bigger than the second. If the second one is bigger,
         * it returns a negative number.
         */
         if (comparator.compare(first, second) >= 0){
             return first;
         } else return second;
    }

    public static String tierFor(PlayerAccount player) {
        if (player.rating() < 1000) return "Bronze";
        if (player.rating() < 1400) return "Silver";
        return "Gold";
    }
}
