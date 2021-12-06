package fr.pokepixel.legendaryplus;

import java.util.List;
import java.util.UUID;

public class PokemonInfo {

    private final List<Info> infoList;

    public PokemonInfo(List<Info> infoList){
        this.infoList = infoList;
    }

    public List<Info> getInfoList() {
        return infoList;
    }

    public static class Info{

        private final String name;
        private String state,player;
        private final UUID uuid;
        private final long ms;

        public Info(String name, long ms, UUID uuid, String state, String player){
            this.name = name;
            this.ms = ms;
            this.uuid = uuid;
            this.state = state;
            this.player = player;
        }

        public String getName() {
            return name;
        }

        public long getMs() {
            return ms;
        }

        public UUID getUuid() {
            return uuid;
        }

        public String getState() {
            return state;
        }

        public String getPlayer() {
            return player;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setPlayer(String player) {
            this.player = player;
        }
    }

}
