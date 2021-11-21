package org.playwing.game.matchmaking.service.impl;

import org.playwing.game.core.commons.config.DefaultConfig;
import org.playwing.game.core.commons.dependency.DependencyManager;
import org.playwing.game.matchmaking.domain.Match;
import org.playwing.game.matchmaking.domain.Player;
import org.playwing.game.matchmaking.service.MatchmakingService;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MatchmakingServiceImpl implements MatchmakingService {

    @Override
    public List<Match> findMatchesForPlayers(List<Player> players) {
        Map<Integer, Long> sortedByCountSquadIds = getSquadsSortedByCount(players);

        List<Match> matches = createMatches(players, sortedByCountSquadIds);

        if (!players.isEmpty()) {
            DependencyManager.getInstance()
                    .getHoldersProvider()
                    .getSearchingPlayersHolder()
                    .addPlayersToSearchingMatch(players);
        }

        return matches;
    }

    private Map<Integer, Long> getSquadsSortedByCount(List<Player> players) {

        return players.stream()
                .map(Player::getSquadId)
                .filter(squadId -> squadId != -1)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private List<Match> createMatches(List<Player> players, Map<Integer, Long> sortedByCountSquadIds) {
        List<Match> matches = createMatchesForSquads(players, sortedByCountSquadIds);

        fillMatchesWithMissingNonSquadPlayers(matches, players);

        removeNotFullMatches(matches, players);

        DependencyManager.getInstance()
                .getHoldersProvider()
                .getSearchingPlayersHolder()
                .addPlayersToSearchingMatch(players);

        return matches;
    }

    private void removeNotFullMatches(List<Match> matches, List<Player> players) {
        List<Match> notFullMatches = matches.stream().filter(this::isMatchNotFull)
                .collect(Collectors.toList());

        notFullMatches.forEach(match -> movePlayersFromMatchToList(match, players));
        notFullMatches.forEach(matches::remove);
    }

    private void fillMatchesWithMissingNonSquadPlayers(List<Match> matches, List<Player> players) {
        players.forEach(player -> createNewMatchOrAddSquadToExisting(matches, Collections.singletonList(player)));
        players.clear();
    }

    private List<Match> createMatchesForSquads(List<Player> players, Map<Integer, Long> sortedByCountSquadIds) {
        List<Match> matches = new LinkedList<>();

        Iterator<Integer> iterator = sortedByCountSquadIds.keySet().iterator();

        while (iterator.hasNext()) {
            Integer firstSquadId = iterator.next();

            if (iterator.hasNext()) {
                Integer secondSquadId = iterator.next();
                List<Player> firstSquad = createTeamFromOneSquad(firstSquadId, players);
                createNewMatchOrAddSquadToExisting(matches, firstSquad);

                List<Player> secondSquad = createTeamFromOneSquad(secondSquadId, players);
                createNewMatchOrAddSquadToExisting(matches, secondSquad);
            } else {
                List<Player> teamPlayers = createTeamFromOneSquad(firstSquadId, players);
                createNewMatchOrAddSquadToExisting(matches, teamPlayers);
            }
        }

        return matches;
    }

    private void createNewMatchOrAddSquadToExisting(List<Match> matches, List<Player> squadPlayers) {
        Integer squadAverageSkill = calculateSquadAverageSkill(squadPlayers);

        if (tryToSetSquadPerfectly(matches, squadPlayers, squadAverageSkill).isPresent()) {
            return;
        }
        if (tryToSetSquad(matches, squadPlayers, squadAverageSkill).isPresent()) {
            return;
        }

        Match match = createMatchForSquadPlayers(squadPlayers, squadAverageSkill, matches.size() + 1);
        matches.add(match);
    }

    private Optional<Match> tryToSetSquadPerfectly(List<Match> matches, List<Player> squadPlayers, Integer squadAverageSkill) {
        Optional<Match> matchOptional = Optional.empty();

        for (Match match : matches) {
            if (match.tryToSetTeamPerfectly(squadPlayers, squadAverageSkill)) {
                matchOptional = Optional.of(match);
                break;
            }
        }
        return matchOptional;
    }

    private Optional<Match> tryToSetSquad(List<Match> matches, List<Player> squadPlayers, Integer squadAverageSkill) {
        Optional<Match> matchOptional = Optional.empty();

        for (Match match : matches) {
            if (match.tryToSetTeam(squadPlayers, squadAverageSkill)) {
                matchOptional = Optional.of(match);
                break;
            }
        }
        return matchOptional;
    }

    private Match createMatchForSquadPlayers(List<Player> squadPlayers, Integer squadAverageSkill, Integer matchNumber) {
        Match match = new Match(matchNumber);
        match.tryToSetTeam(squadPlayers, squadAverageSkill);
        return match;
    }

    private List<Player> createTeamFromOneSquad(Integer squadId, List<Player> players) {
        List<Player> playersFromSquad = players.stream()
                .filter(player -> player.getSquadId().equals(squadId))
                .collect(Collectors.toList());

        players.removeAll(playersFromSquad);

        return playersFromSquad;
    }

    private Integer calculateSquadAverageSkill(List<Player> squadPlayers) {
        AtomicReference<Integer> averageValue = new AtomicReference<>(0);
        squadPlayers.forEach(player -> averageValue.updateAndGet(v -> v + player.getSkill()));

        return averageValue.get() / squadPlayers.size();
    }

    private boolean isMatchNotFull(Match match) {
        if (match.getTeamA().getPlayers().size() < DefaultConfig.DEFAULT_TEAM_PLAYERS_COUNT) {
            return true;
        }
        return match.getTeamB().getPlayers().size() < DefaultConfig.DEFAULT_TEAM_PLAYERS_COUNT;
    }

    private void movePlayersFromMatchToList(Match match, List<Player> players) {
        if (match.getTeamA() != null) {
            players.addAll(match.getTeamA().getPlayers());
        }
        if (match.getTeamB() != null) {
            players.addAll(match.getTeamB().getPlayers());
        }
    }
}
