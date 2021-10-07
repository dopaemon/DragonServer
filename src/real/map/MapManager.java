package real.map;

import java.util.ArrayList;

public class MapManager implements Runnable {

    private static MapManager instance;

    private ArrayList<Map> maps;

    public MapManager() {
        this.maps = new ArrayList<>();
        new Thread(this).start();
    }

    public static MapManager gI() {
        if (instance == null) {
            instance = new MapManager();
        }
        return instance;
    }

    public void init() {
        maps = MapData.loadMap();
    }

    public Map getMapById(int _id) {
        for (Map map : maps) {
            if (map.id == _id) {
                return map;
            }
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            long l1 = System.currentTimeMillis();

            for (Map map : maps) {
                map.update();
            }

            long l2 = System.currentTimeMillis() - l1;
            if (l2 < 1000) {
                try {
                    Thread.sleep(1000 - l2);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
