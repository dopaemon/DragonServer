package real.map;

import real.player.Player;
import real.player.PlayerManger;
import server.io.Message;
import server.io.Session;

public class MapService {

    private static MapService instance;

    public static MapService gI() {
        if (instance == null) {
            instance = new MapService();
        }
        return instance;
    }

    public void joinMap(Session _session, Map _map) {
        Player player = PlayerManger.gI().getPlayerByUserID(_session.userId);
        Message msg;
        try {
            for (Player pl : _map.getPlayers()) {
                if (player == pl) {
                    return;
                }
                infoPlayer(pl.session, player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPlayers(Session _session, Map _map) {
        Player player = PlayerManger.gI().getPlayerByUserID(_session.userId);
        Message msg;
        try {
            for (Player pl : _map.getPlayers()) {
                if (player != pl) {
                    infoPlayer(_session, pl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void infoPlayer(Session _session, Player _player) {
        Message msg;
        try {
            msg = new Message(-5);
            msg.writer().writeInt(_player.id);
            if (_player.clan != null) {
                msg.writer().writeInt(_player.clan.id);
            } else {
                msg.writer().writeInt(-1);
            }
            msg.writer().writeByte(10);
            msg.writer().writeBoolean(false);
            msg.writer().writeByte(_player.typePk);
            msg.writer().writeByte(_player.gender);
            msg.writer().writeByte(_player.gender);
            msg.writer().writeShort(_player.getHead());
            msg.writer().writeUTF(_player.name);
            msg.writer().writeInt(_player.hpGoc);
            msg.writer().writeInt(_player.getHpFull());
            msg.writer().writeShort(_player.getBody());
            msg.writer().writeShort(_player.getLeg());
            msg.writer().writeByte(8);
            msg.writer().writeByte(-1);
            msg.writer().writeShort(_player.x);
            msg.writer().writeShort(_player.y);
            msg.writer().writeShort(0);
            msg.writer().writeShort(0);
            msg.writer().writeByte(0);
            msg.writer().writeByte(0);
            msg.writer().writeByte(0);
            msg.writer().writeShort(_player.getMount());
            msg.writer().writeByte(0);
            msg.writer().writeByte(0);
            _session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitMap(Player _player, Map _map) {
        Message msg;
        try {
            for (int i = 0; i < _map.getPlayers().size(); i++) {
                Player player = _map.getPlayers().get(i);
                if (_player != player) {
                    msg = new Message(-6);
                    msg.writer().writeInt(_player.id);
                    player.session.sendMessage(msg);
                    msg.cleanup();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playerMove(Player _player) {
        Map map = _player.map;
        Message msg;
        try {
            for (Player player : map.getPlayers()) {
                if (player != _player) {
                    msg = new Message(-7);
                    msg.writer().writeInt(_player.id);
                    msg.writer().writeShort(_player.x);
                    msg.writer().writeShort(_player.y);
                    player.session.sendMessage(msg);
                    msg.cleanup();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chat(Session _session, String _text) {
        Player player = PlayerManger.gI().getPlayerByUserID(_session.userId);
        Map map = player.map;
        Message msg;
        try {
            for (Player pl : map.getPlayers()) {
                msg = new Message(44);
                msg.writer().writeInt(player.id);
                msg.writer().writeUTF(_text);
                pl.session.sendMessage(msg);
                msg.cleanup();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
