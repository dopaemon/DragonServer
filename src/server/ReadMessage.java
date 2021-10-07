package server;

import real.player.Player;
import real.player.PlayerManger;
import server.io.Message;
import server.io.Session;

public class ReadMessage {

    private static ReadMessage instance;

    public static ReadMessage gI() {
        if (instance == null) {
            instance = new ReadMessage();
        }
        return instance;
    }

    public void getItem(Session session, Message msg) {
        Player player = PlayerManger.gI().getPlayerByUserID(session.userId);
        try {
            int type = msg.reader().readByte();
            int id = msg.reader().readByte();
            switch (type){
                case 4:
                    player.itemBagToBody(id);
                    break;
                case 5:
                    player.itemBodyToBag(id);
                    break;
            }
        } catch (Exception e) {
        }
    }
}
