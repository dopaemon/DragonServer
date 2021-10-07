package server;

import java.net.ServerSocket;
import java.net.Socket;
import real.clan.ClanManager;
import real.item.ItemData;
import real.map.MapManager;
import real.player.PlayerManger;
import real.skill.SkillData;
import server.io.Session;

public class ServerManager {

    public static final int port = 14445;
    
    private Controller controller;
    
    private static ServerManager instance;
    
    public static byte vsData = 47;

    public static byte vsMap = 25;
    
    public static byte vsSkill = 5;
    
    public static byte vsItem = 90;

    public void init() {
        MapManager.gI().init();
        ClanManager.gI().init();
        SkillData.createSkill();
        ItemData.loadDataItem();
        this.controller = new Controller();
    }

    public static ServerManager gI() {
        if (instance == null) {
            instance = new ServerManager();
            instance.init();
        }
        return instance;
    }
    
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                        Util.log("Player: " + PlayerManger.gI().size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        }).start();
        ServerManager.gI().run();
    }

    public void run() {
        ServerSocket listenSocket = null;
        try {
            Util.log("Start server...");
            listenSocket = new ServerSocket(port);
            while (true) {
                Socket sc = listenSocket.accept();
                Session session = new Session(sc, controller);
                session.start();
                Util.log("Accept socket listen " + sc.getPort());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
