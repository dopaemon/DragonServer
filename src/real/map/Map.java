package real.map;

import java.util.ArrayList;
import real.player.Player;
import server.Service;
import sun.print.resources.serviceui;

public class Map implements Runnable {

    public int id;

    public byte planetId;

    public byte tileId;

    public byte bgId;

    public byte bgType;

    public byte type;

    public String name;

    public ArrayList<Player> players;

    public ArrayList<WayPoint> wayPoints;

    public ArrayList<Npc> npcs;

    public ArrayList<Mob> mobs;

    public ArrayList<ItemMap> items;

    public Map(int _id) {
        this.id = _id;
        this.players = new ArrayList<>();
        this.wayPoints = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.mobs = new ArrayList<>();
        this.items = new ArrayList<>();
        new Thread(this).start();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getPlanetId() {
        return planetId;
    }

    public void setPlanetId(byte planetId) {
        this.planetId = planetId;
    }

    public byte getBgId() {
        return bgId;
    }

    public void setBgId(byte bgId) {
        this.bgId = bgId;
    }

    public byte getBgType() {
        return bgType;
    }

    public void setBgType(byte bgType) {
        this.bgType = bgType;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<WayPoint> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(ArrayList<WayPoint> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public ArrayList<Npc> getNpcs() {
        return npcs;
    }

    public void setNpcs(ArrayList<Npc> npcs) {
        this.npcs = npcs;
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    public void setMobs(ArrayList<Mob> mobs) {
        this.mobs = mobs;
    }

    public ArrayList<ItemMap> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemMap> items) {
        this.items = items;
    }

    public void update() {
    }

    @Override
    public void run() {
        while (true) {
            long l1 = System.currentTimeMillis();
            for (Mob m : mobs) {
                m.update();
            }
            long l2 = System.currentTimeMillis() - l1;
            if (l2 < 200) {
                try {
                    Thread.sleep(200 - l2);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
