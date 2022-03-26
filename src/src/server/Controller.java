package server;
//share by chibikun

import server.io.Message;
import server.io.Session;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import real.clan.ClanService;
import real.item.ItemDAO;
import real.map.Map;
import real.map.MapManager;
import real.map.MapService;
import real.map.WayPoint;
import real.player.Player;
import real.player.PlayerDAO;
import real.player.PlayerManger;

public class Controller {

    public Session session;
    private static Controller instance;

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void onMessage(Session _session, Message _msg) {
        try {
            Player player = PlayerManger.gI().getPlayerByUserID(_session.userId);
            byte cmd = _msg.command;
            switch (cmd) {
                case -101:
                    login2(_session);
                    break;
                case -7:
                    byte b = _msg.reader().readByte();
//                    if (b == 0) {
//                        player.move(msg.reader().readShort(), player.getY());
//                    } else {
//                        player.move(msg.reader().readShort(), msg.reader().readShort());
//                    }
                    try {
                        player.x = _msg.reader().readShort();
                        player.y = _msg.reader().readShort();
                    } catch (Exception e) {
                    }
                    MapService.gI().playerMove(player);
                    break;
                case -74:
                    byte type = _msg.reader().readByte();
                    if (type == 1) {
                        Service.getInstance().sizeImageSource(_session);
                    } else if (type == 2) {
                        Service.getInstance().imageSource(_session);
                    }
                    break;
                case -80:
                    Service.getInstance().sendMessage(_session, -80, "1630679754715_-80_r");
                    break;
                case -87:
                    Service.getInstance().updateData(_session);
                    break;
                case -67:
                    int id = _msg.reader().readInt();
                    System.out.println("icon " + id);
                    Service.getInstance().requestIcon(_session, id);
                    break;
                case -66:
                    int effId = _msg.reader().readShort();
                    Service.getInstance().effData(_session, effId);
                    break;
                case -63:
                    // id image logo clan
                    Service.getInstance().sendMessage(_session, -63, "1630679755147_-63_r");
                    break;
                case -32:
                    int bgId = _msg.reader().readShort();
                    Service.getInstance().bgTemp(_session, bgId);
                    break;
                case -33:
                case -23:
                    WayPoint wp = player.isInWaypoint();
                    if (wp != null) {
                        if (wp.goMap == 111) {
                            Service.getInstance().resetPoint(_session, player.x - 50, player.y);
                            break;
                        }
                        Map map = MapManager.gI().getMapById(wp.goMap);
                        player.gotoMap(map);
                        player.x = wp.goX;
                        player.y = wp.goY;
                        Service.getInstance().clearMap(_session);
                        Service.getInstance().tileSet(_session, wp.goMap);
                        Service.getInstance().mapInfo(_session, player);
                        MapService.gI().joinMap(_session, map);
                        MapService.gI().loadPlayers(_session, player.map);
                        Util.log("go map " + wp.goMap);
                    } else {
                        Service.getInstance().serverMessage(_session, "Không thể vào map");
                        Service.getInstance().resetPoint(_session, player.x - 50, player.y);
                    }
                    Util.log("change map");
                    break;
                case -46:
                    byte action = _msg.reader().readByte();
                    Util.log("Clan action: " + action);
                    if (action == 4) {
                        _msg.reader().readByte();
                        Service.getInstance().updateSloganClan(_session, _msg.reader().readUTF());
                    }
                    break;
                case -50:
                    int clanId = _msg.reader().readInt();
                    ClanService.gI().clanMember(_session, clanId);
                    break;
                case -47:
                    String clanName = _msg.reader().readUTF();
                    ClanService.gI().searchClan(_session, clanName);
                    break;
                case -55:
                    //leaveClan
                    Service.getInstance().serverMessage(_session, "leaveClan");
                    Util.log("leaveClan");
                    break;
                case -40:
                    ReadMessage.gI().getItem(_session, _msg);
                    break;
                case -41:
                    //UPDATE_CAPTION
                    Service.getInstance().sendMessage(_session, -41, "1630679754812_-41_r");
                    break;
                case -39:
                    //finishLoadMap
                    Util.log("finishLoadMap");
                    break;
                case 11:
                    byte modId = _msg.reader().readByte();
                    Service.getInstance().requestModTemplate(_session, modId);
                    break;
                case 44:
                    String text = _msg.reader().readUTF();
                    if (text.contains("m ")) {
                        int mapId = Integer.parseInt(text.replace("m ", ""));
                        Map map = MapManager.gI().getMapById(mapId);
                        if (map != null) {
                            player.gotoMap(map);
                            Service.getInstance().clearMap(_session);
                            Service.getInstance().tileSet(_session, mapId);
                            Service.getInstance().mapInfo(_session, player);
                            MapService.gI().joinMap(_session, map);
                            MapService.gI().loadPlayers(_session, player.map);
                        }

                    }
                    if (text.equals("hp")) {
                        player.update();
                        player.updatehp();
                    } else if (text.equals("shop")) {
                        Service.getInstance().sendMessage(_session, -44, "1632921172115_-44_r");
                    } else {
                        MapService.gI().chat(_session, text);
                    }
                    break;
                case -71:
                    break;
                case 33:
                    int npcId = _msg.reader().readShort();
                    Service.getInstance().serverMessage(_session, "npc " + npcId);
                    break;
                case 34:
                    short selectSkill = _msg.reader().readShort();
                    player.selectSkill = null;
                    Util.log("skill select temp " + selectSkill + " - skillId " + player.selectSkill.skillId);
                case 54:
                    //  for (Player pl : session.player.nearPlayers) {
                    //        Service.getInstance().attackMob(session.player, msg.reader().readByte());
                    //     }
                    break;
                case -27:
                    _session.sendSessionKey();
                    Service.getInstance().sendMessage(_session, -111, "1630679748814_-111_r");
                    Service.getInstance().versionImageSource(_session);
                    Service.getInstance().sendMessage(_session, -29, "1630679748828_-29_2_r");
                    break;
                case -28:
                    messageNotMap(_session, _msg);
                    break;
                case -29:
                    messageNotLogin(_session, _msg);
                    break;
                case -30:
                    messageSubCommand(_session, _msg);
                    break;
                //      case -71:
                //            Service.getInstance().serverMessage(this.session, "Chức Năng Chưa Hoàn Thiện .share by chibikun");
                //           break;
                default:
                    Util.log("CMD: " + cmd);
                    break;
            }
        } catch (Exception e) {
        }
    }

    public void messageNotLogin(Session session, Message msg) {
        if (msg != null) {
            try {
                byte cmd = msg.reader().readByte();
                switch (cmd) {
                    case 0:
                        login(session, msg);
                        break;
                    case 2:
                        session.setClientType(msg);
                        break;
                    default:
                        Util.log("messageNotLogin: " + cmd);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void messageNotMap(Session _session, Message _msg) {
        if (_msg != null) {
            try {
                Player player = PlayerManger.gI().getPlayerByUserID(_session.userId);
                byte cmd = _msg.reader().readByte();
                switch (cmd) {
                    case 2:
                        createChar(_session, _msg);
                        break;
                    case 6:
                        Service.getInstance().updateMap(_session);
                        break;
                    case 7:
                        Service.getInstance().updateSkill(_session);
                        break;
                    case 8:
                        Service.getInstance().updateItem(_session);
//                        Service.getInstance().sendMessage(session, -28, "1630679754405_-28_8_r");
//                        Service.getInstance().sendMessage(session, -28, "1630679754440_-28_8_r");
//                        Service.getInstance().sendMessage(session, -28, "1630679754451_-28_8_r");
                        break;
                    case 10:
                        Util.log("map temp");
                        // -28_10 REQUEST_MAPTEMPLATE
                        Service.getInstance().mapTemp(_session, player.map.getId());
                        //Service.getInstance().sendMessage(-28, "1630679754853_-28_10_r");
                        break;
                    case 13:
                        //client ok
                        Util.log("client ok");
                        player.update();
                        player.updatehp();
                        // Service.getInstance().serverMessage(this.session, "Source Share By chibikun");
                        break;
                    default:
                        Util.log("messageNotMap: " + cmd);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void messageSubCommand(Session _session, Message _msg) {
        if (_msg != null) {
            try {
                Player player = PlayerManger.gI().getPlayerByUserID(_session.userId);
                byte command = _msg.reader().readByte();
                switch (command) {
                    case 16:
                        byte type = _msg.reader().readByte();
                        short point = _msg.reader().readShort();
                        player.increasePoint(type, point);
                        break;
                    default:
                        Util.log("messageSubCommand: " + command);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void login(Session session, Message msg) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String user = msg.reader().readUTF();
            String pass = msg.reader().readUTF();
            msg.reader().readUTF();
            msg.reader().readByte();
            // -77 SMALLIMAGE_VERSION
            Service.getInstance().sendMessage(session, -77, "1630679752225_-77_r");
            // -93 BGITEM_VERSION
            Service.getInstance().sendMessage(session, -93, "1630679752231_-93_r");
            conn = DBService.gI().getConnection();
            pstmt = conn.prepareStatement("select * from account where username=? and password=? limit 1");
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            Util.log("user " + user + " - pass " + pass);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                session.userId = rs.getInt("id");
                if (rs.getBoolean("ban")) {
                    Service.getInstance().serverMessage(session, "Tài khoản đã bị khóa");
                } else if (PlayerManger.gI().getPlayerByUserID(session.userId) != null) {
                    Service.getInstance().serverMessage(session, "Bạn đang đăng nhập trên thiết bị khác");
                } else {
                    pstmt = conn.prepareStatement("select * from player where account_id=? limit 1");
                    pstmt.setInt(1, session.userId);
                    msg.cleanup();
                    rs = pstmt.executeQuery();
                    if (rs.first()) {
//                        long time1 = rs.getTimestamp("last_logout_time").getTime();
//                        long time2 = (System.currentTimeMillis() - time1) / 1000;
//                        if (time2 >= 0) {
                        Player player = PlayerDAO.load(session.userId);
                        ItemDAO.loadPlayerItems(player);
                        PlayerManger.gI().getPlayers().add(player);
                        player.active();
                        player.session = session;
                        //player.loadItems();
                        // -28_4 
                        Service.getInstance().updateVersion(session);
                        //Service.getInstance().sendMessage(session, -28, "1630679754226_-28_4_r");
                        // -31 ITEM_BACKGROUND
                        Service.getInstance().itemBg(session, 0);
                        sendInfo(session);
                        //last_login_time
//                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//                            pstmt = conn.prepareStatement("update player set last_login_time=? where id=?");
//                            pstmt.setTimestamp(1, timestamp);
//                            pstmt.setInt(2, 1);
//                            pstmt.executeUpdate();
//                        } else {
//                            //Service.getInstance().loginDe((short) (30 - time2));
//                        }
                    } else {
                        Service.getInstance().sendMessage(session, -28, "1630679754226_-28_4_r");
                        Service.getInstance().sendMessage(session, -31, "1631370772604_-31_r");
                        Service.getInstance().sendMessage(session, -82, "1631370772610_-82_r");
                        Service.getInstance().sendMessage(session, 2, "1631370772901_2_r");
                    }
                }
            } else {
                Service.getInstance().serverMessage(session, "Tài khoản mật khẩu không chính xác");
            }
            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void createChar(Session session, Message msg) {
        Connection conn = DBService.gI().getConnection();
        try {
            String name = msg.reader().readUTF();
            int gender = msg.reader().readByte();
            int hair = msg.reader().readByte();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `player` WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (!rs.first()) {
                if (PlayerDAO.create(hair, name, gender, hair)) {
                    Player player = PlayerDAO.load(session.userId);
                    player.session = session;
//                session.player.create(name, gender, hair);
//                session.player.load(session.id);
//                session.player.loadItems();
                    //Service.getInstance().updateItemBox();
                    sendInfo(session);
                }

            } else {
                Service.getInstance().serverMessage(session, "Tên đã tồn tại");
            }
            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void login2(Session session) {
        String user = "User" + Util.nextInt(2222222, 8888888);
        Connection conn = DBService.gI().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO account(username,password) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user);
            ps.setString(2, "");
            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.first()) {
                    int userId = rs.getInt(1);
                    Service.getInstance().login2(session, user);
                }
            } else {
                Service.getInstance().serverMessage(session, "Có lỗi vui lòng thử lại");
            }
        } catch (Exception e) {
        }
    }

    public void sendInfo(Session session) {
        Player player = PlayerManger.gI().getPlayerByUserID(session.userId);
        // -82 TILE_SET
        Service.getInstance().tileSet(session, player.map.id);
        // 112 SPEACIAL_SKILL
        Service.getInstance().sendMessage(session, 112, "1630679754607_112_r");
        // -42 ME_LOAD_POINT
        Service.getInstance().point(session, player);
        //Service.getInstance().sendMessage(-42, "1630679754614_-42_r");
        // 40 TASK_GET
        Service.getInstance().sendMessage(session, 40, "1630679754622_40_r");
        // -22 MAP_CLEAR
        Service.getInstance().clearMap(session);
        //Service.getInstance().sendMessage(-22, "1630679754629_-22_r");
        // -42 ME_LOAD_POINT
        //Service.getInstance().sendMessage(-42, "1630679754637_-42_r");
        // -30_0 ME_LOAD_ALL
        Service.getInstance().player(session, player);
        //Service.getInstance().sendMessage(session, -30, "1632838985276_-30_0_r");
        //Service.getInstance().sendMessage(-42, "1630679754652_-42_r");
        // -53 CLAN_INFO
        if (player.clan != null) {
            //ClanService.gI().clanInfo(session, player);
        }

        //Service.getInstance().sendMessage(-53, "1630679754659_-53_r");
        // -64 UPDATE_BAG
        //Service.getInstance().sendMessage(-64, "1630679754666_-64_r");
        // -90 UPDATE_BODY
        //Service.getInstance().sendMessage(-90, "1630679754673_-90_r");
        // -69 MAXSTAMINA
        Service.getInstance().sendMessage(session, -69, "1630679754701_-69_r");
        // -68 STAMINA
        Service.getInstance().sendMessage(session, -68, "1630679754708_-68_r");
        // -80 FRIEND
        Service.getInstance().sendMessage(session, -80, "1630679754715_-80_r");
        // -97 UPDATE_ACTIVEPOINT
        Service.getInstance().sendMessage(session, -97, "1630679754722_-97_r");
        // -107 PET_INFO
        Service.getInstance().sendMessage(session, -107, "1630679754733_-107_r");
        // -119 THELUC
        Service.getInstance().sendMessage(session, -119, "1630679754740_-119_r");
        // -113 CHANGE_ONSKILL
        Service.getInstance().sendMessage(session, -113, "1630679754747_-113_r");
        // 50 GAME_INFO
        //Service.getInstance().sendMessage(session, 50, "1630679754755_50_r");
        // -30_4 ME_LOAD_INFO
        //Service.getInstance().sendMessage(-68, "1630679754776_-68_r");
        //Service.getInstance().sendMessage(-30, "1630679754782_-30_4_r");
        // -24 MAP_INFO
        //session.player.map.join(session.player);
        Service.getInstance().mapInfo(session, player);
        MapService.gI().joinMap(session, player.map);
        MapService.gI().loadPlayers(session, player.map);
        //Service.getInstance().sendMessage(-24, "1630679754789_-24_r");
        //Service.getInstance().sendMessage(-30, "1630679754795_-30_4_r");
    }

    public void logout(Session session) {
        Player player = PlayerManger.gI().getPlayerByUserID(session.userId);
        if (player != null) {
            Map map = player.map;
            PlayerDAO.updateDB(player);
            player.timer.cancel();
            MapService.gI().exitMap(player, map);
            map.getPlayers().remove(player);
            player.map = null;
            PlayerManger.gI().getPlayers().remove(player);
        }
    }

}
