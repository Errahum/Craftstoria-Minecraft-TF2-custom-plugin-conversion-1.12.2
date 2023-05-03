package com.chaseoes.tf2;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class GameUtilities {

    public TF2 plugin;
    static GameUtilities instance = new GameUtilities();
    public HashMap<String, Game> games = new HashMap<String, Game>();
    public HashMap<String, GamePlayer> players = new HashMap<String, GamePlayer>();

    private GameUtilities() {

    }

    public static GameUtilities getUtilities() {
        return instance;
    }

    public void setup(TF2 p) {
        plugin = p;
    }

    public void addGame(Map map, Game g) {
        games.put(map.getName(), g);
    }

    public Game getGame(Map map) {
        return games.get(map.getName());
    }

    public GamePlayer getGamePlayer(Player player) {
        if (player == null) {
            return null;
        }

        for (Game g : games.values()) {
            for (GamePlayer gp : g.playersInGame.values()) {
                if (gp.getName().equalsIgnoreCase(player.getName())) {
                    return gp;
                }
            }
        }

        if (!players.containsKey(player.getName())) {
            players.put(player.getName(), new GamePlayer(player));
        }

        return players.get(player.getName());
    }
    public GamePlayer getGamePlayer2(Player player2) { ////////2
        if (player2 == null) {
            return null;
        }

        for (Game g2 : games.values()) {
            for (GamePlayer gp2 : g2.playersInGame.values()) {
                if (gp2.getName().equalsIgnoreCase(player2.getName())) {
                    return gp2;
                }
            }
        }

        if (!players.containsKey(player2.getName())) {
            players.put(player2.getName(), new GamePlayer(player2));
        }

        return players.get(player2.getName());
    } ////////////

    public void playerJoinServer(Player player) {
        players.put(player.getName(), new GamePlayer(player));
    }

    public void playerLeaveServer(Player player) {
        players.remove(player.getName());
    }

    public Game removeGame(Map m) {
        return games.remove(m.getName());
    }

    public Game getNextGame(Game g) {
        for (Game game : games.values()) {
            if (!game.getMapName().equalsIgnoreCase(g.getMapName())) {
                return game;
            }
        }
        return null;
    }
}