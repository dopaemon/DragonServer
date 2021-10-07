package server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Scanner;
import real.clan.Clan;
import real.clan.Member;
import real.clan.ClanManager;
import real.item.Item;
import real.item.ItemOption;
import real.map.Map;
import real.map.Mob;
import real.map.Npc;
import real.map.WayPoint;
import real.player.Player;
import real.player.PlayerManger;
import server.io.Session;
import real.skill.Skill;
import server.io.Message;

public class Service {

    private static Service instance;

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public void serverMessage(Session session, String text) {
        Message msg;
        try {
            msg = new Message(-26);
            msg.writer().writeUTF(text);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void loginDe(Session session, short second) {
        Message msg;
        try {
            msg = new Message(122);
            msg.writer().writeShort(second);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void requestIcon(Session session, int id) {
        Message msg;
        try {
            byte[] icon = FileIO.readFile("data/icon/" + id);
            msg = new Message(-67);
            msg.writer().writeInt(id);
            msg.writer().writeInt(icon.length);
            msg.writer().write(icon);
            session.doSendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void versionImageSource(Session session) {
        Message msg;
        try {
            msg = new Message(-74);
            msg.writer().writeByte(0);
            msg.writer().writeInt(5714013);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void sizeImageSource(Session session) {
        Message msg;
        try {
            msg = new Message(-74);
            msg.writer().writeByte(1);
            msg.writer().writeInt(958);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void imageSource(Session session) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg;
                try {
                    File myObj = new File("data/res/5714013.txt");
                    Scanner sc = new Scanner(myObj);
                    if (sc.hasNextLine()) {
                        String original = sc.nextLine();
                        byte[] res = FileIO.readFile("data/res/5714013" + original);
                        msg = new Message(-74);
                        msg.writer().writeByte(2);
                        msg.writer().writeUTF(original);
                        msg.writer().writeInt(res.length);
                        msg.writer().write(res);

                        session.sendMessage(msg);
                        msg.cleanup();
                        Thread.sleep(500);
                    }
                    sc.close();

//                    msg = new Message(-74);
//                    msg.writer().writeByte(3);
//                    msg.writer().writeInt(5714013);
//                    session.sendMessage(msg);
//                    msg.cleanup();
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public void itemBg(Session session, int id) {
        Message msg;
        try {
            byte[] item_bg = FileIO.readFile("data/map/item_bg/" + id);
            msg = new Message(-31);
            msg.writer().write(item_bg);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void bgTemp(Session session, int id) {
        Message msg;
        try {
            byte[] bg_temp = FileIO.readFile("data/bg_temp/" + id);
            msg = new Message(-32);
            msg.writer().write(bg_temp);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void effData(Session session, int id) {
        Message msg;
        try {
            byte[] eff_data = FileIO.readFile("data/eff_data/" + id);
            msg = new Message(-66);
            msg.writer().write(eff_data);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void requestModTemplate(Session session, int id) {
        Message msg;
        try {
            byte[] mob = FileIO.readFile("data/mob/" + id);
            msg = new Message(11);
//            msg.writer().writeInt(id);
//            msg.writer().writeInt(mob.length);
            msg.writer().write(mob);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void sendMessage(Session session, int cmd, String filename) {
        Message msg;
        try {
            msg = new Message(cmd);
            msg.writer().write(FileIO.readFile("data/msg/" + filename));
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void updateVersion(Session session) {
        Message msg;
        try {
//            msg = messageNotMap((byte) 4);
//            msg.writer().writeByte(ServerManager.vsData);
//            msg.writer().writeByte(ServerManager.vsMap);
//            msg.writer().writeByte(ServerManager.vsSkill);
//            msg.writer().writeByte(ServerManager.vsItem);
//            msg.writer().writeByte(0);
//            msg.writer().write(FileIO.readFile("data/NRexp"));
//            session.sendMessage(msg);
//            msg.cleanup();
            msg = new Message(-28);
            msg.writer().write(FileIO.readFile("data/1632811838304_-28_4_r"));
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void updateData(Session session) {
        Message msg;
        try {
            msg = new Message(-87);
//            msg.writer().write(FileIO.readFile("data/NRdata_v47"));
            msg.writer().write(FileIO.readFile("data/1632811838531_-87_r"));
            session.doSendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
        System.out.println("update data");
    }

    public void updateMap(Session session) {
        Message msg;
        try {
            //msg = messageNotMap((byte) 6);
            msg = new Message(-28);
//            msg.writer().write(FileIO.readFile("data/NRmap_v25"));
            msg.writer().write(FileIO.readFile("data/1632811838538_-28_6_r"));
            session.doSendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
        System.out.println("update map");
    }

    public void updateSkill(Session session) {
        Message msg;
        try {
            //msg = messageNotMap((byte) 7);
            msg = new Message(-28);
//            msg.writer().write(FileIO.readFile("data/NRskill_v5"));
            msg.writer().write(FileIO.readFile("data/1632811838545_-28_7_r"));
            session.doSendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
        System.out.println("update skill");
    }

    public void updateItem(Session session) {
        Message msg;
        try {
            //msg = messageNotMap((byte) 8);
            msg = new Message(-28);
            //msg.writer().write(FileIO.readFile("data/NRitem_v90_0"));
            msg.writer().write(FileIO.readFile("data/1632811838554_-28_8_r"));
            session.doSendMessage(msg);
            msg.cleanup();

            msg = new Message(-28);
            //msg.writer().write(FileIO.readFile("data/NRitem_v90_1"));
            msg.writer().write(FileIO.readFile("data/1632811838561_-28_8_r"));
            session.doSendMessage(msg);
            msg.cleanup();

            msg = new Message(-28);
            //msg.writer().write(FileIO.readFile("data/NRitem_v90_2"));
            msg.writer().write(FileIO.readFile("data/1632811838570_-28_8_r"));
            session.doSendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
        System.out.println("update item");
    }

    public void tileSet(Session session, int id) {
        Message msg;
        try {
            //msg = messageNotMap((byte) 6);
            msg = new Message(-82);
            msg.writer().write(FileIO.readFile("data/map/tile_set/" + id));
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void mapInfo(Session session, Player pl) {
        Message msg;
        try {
            Map map = pl.map;
            msg = new Message(-24);
            msg.writer().writeByte(map.id);
            msg.writer().writeByte(map.planetId);
            msg.writer().writeByte(map.tileId);
            msg.writer().writeByte(map.bgId);
            msg.writer().writeByte(map.type);
            msg.writer().writeUTF(map.name);
            msg.writer().writeByte(0);
            msg.writer().writeShort(pl.x);
            msg.writer().writeShort(pl.y);

            // waypoint
            ArrayList<WayPoint> wayPoints = map.wayPoints;
            msg.writer().writeByte(wayPoints.size());
            for (WayPoint wp : wayPoints) {
                msg.writer().writeShort(wp.minX);
                msg.writer().writeShort(wp.minY);
                msg.writer().writeShort(wp.maxX);
                msg.writer().writeShort(wp.maxY);
                msg.writer().writeBoolean(wp.isEnter);
                msg.writer().writeBoolean(wp.isOffline);
                msg.writer().writeUTF(wp.name);
            }
            // mob
            ArrayList<Mob> mobs = map.mobs;
            msg.writer().writeByte(mobs.size());
            for (Mob mob : mobs) {
                msg.writer().writeBoolean(false);
                msg.writer().writeBoolean(false);
                msg.writer().writeBoolean(false);
                msg.writer().writeBoolean(false);
                msg.writer().writeBoolean(false);
                msg.writer().writeByte(mob.tempId);
                msg.writer().writeByte(0);
                msg.writer().writeInt(mob.hp);
                msg.writer().writeByte(mob.level);
                msg.writer().writeInt((mob.maxHp));
                msg.writer().writeShort(mob.pointX);
                msg.writer().writeShort(mob.pointY);
                msg.writer().writeByte(mob.status);
                msg.writer().writeByte(0);
                msg.writer().writeBoolean(false);
            }

            msg.writer().writeByte(0);

            // npc
            ArrayList<Npc> npcs = map.npcs;
            msg.writer().writeByte(npcs.size());
            for (Npc npc : npcs) {
                msg.writer().writeByte(npc.status);
                msg.writer().writeShort(npc.cx);
                msg.writer().writeShort(npc.cy);
                msg.writer().writeByte(npc.tempId);
                msg.writer().writeShort(npc.avartar);
            }

            msg.writer().writeByte(0);

            // bg item
            byte[] bgItem = FileIO.readFile("data/map/bg/" + map.id);
            msg.writer().write(bgItem);

            // eff item
            byte[] effItem = FileIO.readFile("data/map/eff/" + map.id);
            msg.writer().write(effItem);

            msg.writer().writeByte(map.bgType);
            msg.writer().writeByte(0);
            msg.writer().writeByte(0);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void addPlayer(Session session, Player player) {
        Message msg;
        try {
            msg = new Message(-5);
            msg.writer().writeInt(player.id);
            msg.writer().writeInt(-1);
            msg.writer().writeByte(10);
            msg.writer().writeBoolean(false);
            msg.writer().writeByte(0);
            msg.writer().writeByte(2);
            msg.writer().writeByte(2);
            msg.writer().writeShort(27);
            msg.writer().writeUTF(player.name);
            msg.writer().writeInt(54760);
            msg.writer().writeInt(54760);
            msg.writer().writeShort(player.getBody());
            msg.writer().writeShort(player.getLeg());
            msg.writer().writeByte(8);
            msg.writer().writeByte(-1);
            msg.writer().writeShort(player.x);
            msg.writer().writeShort(player.y);
            msg.writer().writeShort(0);
            msg.writer().writeShort(0);
            msg.writer().writeByte(0);
            msg.writer().writeByte(0);
            msg.writer().writeByte(0);
            msg.writer().writeShort(348);
            msg.writer().writeByte(0);
            msg.writer().writeByte(0);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removePlayer(Session session, Player player) {
        Message msg;
        try {
            msg = new Message(-6);
            msg.writer().writeInt(player.id);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void attackMob(Session session, Player pl, int id) {
        Message msg;
        try {
            msg = new Message(54);
            msg.writer().writeInt(pl.id);
            msg.writer().writeByte(pl.selectSkill.skillId);
            msg.writer().writeByte(id);
            session.sendMessage(msg);
            msg.cleanup();

        } catch (Exception e) {
        }
    }

    public void resetPoint(Session session, int x, int y) {
        Message msg;
        try {
            msg = new Message(46);
            msg.writer().writeShort(x);
            msg.writer().writeShort(y);
            session.sendMessage(msg);
            msg.cleanup();

        } catch (Exception e) {
        }
    }

    public void login2(Session session, String user) {
        Message msg;
        try {
            msg = new Message(-101);
            msg.writer().writeUTF(user);
            session.sendMessage(msg);
            msg.cleanup();

        } catch (Exception e) {
        }
    }

    public void mapTemp(Session session, int id) {
        Message msg;
        try {
            //msg = messageNotMap((byte) 6);
            msg = new Message(-28);
            msg.writer().write(FileIO.readFile("data/map/temp/" + id));
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void updateBag(Session session) {
        Message msg;
        try {
            msg = new Message(-64);
            msg.writer().writeInt(0);// id char
            msg.writer().writeByte(0);// id bag
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void updateBody(Session session) {
        Message msg;
        try {
            msg = new Message(-90);
            msg.writer().writeByte(1);
            msg.writer().writeInt(1);
            msg.writer().writeShort(177);
            msg.writer().writeShort(178);
            msg.writer().writeShort(179);
            msg.writer().writeByte(0);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void updateSloganClan(Session session, String text) {
//        ClanDB.updateSlogan(session.player.clanId, text);
//        Message msg;
//        try {
//            msg = new Message(-46);
//            msg.writer().writeByte(4);
//            msg.writer().writeByte(0);
//            msg.writer().writeUTF(text);
//            session.sendMessage(msg);
//            msg.cleanup();
//        } catch (Exception e) {
//        }
    }

    public void clearMap(Session session) {
        Message msg;
        try {
            msg = new Message(-22);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void chat(Session session, int playerId, String text) {
        Message msg;
        try {
            msg = new Message(44);
            msg.writer().writeInt(playerId);
            msg.writer().writeUTF(text);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void playerMove(Session session, Player pl) {
        Message msg;
        try {
            msg = new Message(-7);
            msg.writer().writeInt(pl.id);
            msg.writer().writeShort(pl.x);
            msg.writer().writeShort(pl.y);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void stamina(Session session) {
        Message msg;
        try {
            msg = new Message(-68);
            msg.writer().writeShort(10000);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void maxStamina(Session session) {
        Message msg;
        try {
            msg = new Message(-69);
            msg.writer().writeShort(10000);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void activePoint(Session session) {
        Message msg;
        try {
            msg = new Message(-97);
            msg.writer().writeInt(1000);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void point(Session session, Player pl) {
        Message msg;
        try {
            msg = new Message(-42);
            msg.writer().writeInt(pl.hpGoc);
            msg.writer().writeInt(pl.mpGoc);
            msg.writer().writeInt(pl.damGoc);
            msg.writer().writeInt(pl.getHpFull());// hp full
            msg.writer().writeInt(pl.getMpFull());// mp full
            msg.writer().writeInt(pl.hp);// hp
            msg.writer().writeInt(pl.mp);// mp
            msg.writer().writeByte(pl.getSpeed());// speed
            msg.writer().writeByte(20);
            msg.writer().writeByte(20);
            msg.writer().writeByte(1);
            msg.writer().writeInt(pl.getDamFull());// dam full
            msg.writer().writeInt(pl.getDefFull());// def full
            msg.writer().writeByte(pl.getCritFull());// crit full
            msg.writer().writeLong(pl.tiemNang);
            msg.writer().writeShort(100);
            msg.writer().writeShort(pl.defGoc);
            msg.writer().writeByte(pl.critGoc);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void player(Session session, Player pl) {
        Message msg;
        try {
            msg = messageSubCommand((byte) 0);
            msg.writer().writeInt(pl.id);
            msg.writer().writeByte(pl.taskId);
            msg.writer().writeByte(pl.gender);
            msg.writer().writeShort(pl.head);
            msg.writer().writeUTF(pl.name);
            msg.writer().writeByte(0);
            msg.writer().writeByte(0);
            msg.writer().writeLong(pl.power);
            msg.writer().writeShort(0);
            msg.writer().writeShort(0);
            msg.writer().writeByte(pl.gender);
            //--------skill---------
            ArrayList<Skill> skills = pl.skills;
            msg.writer().writeByte(skills.size());
            for (Skill skill : skills) {
                msg.writer().writeShort(skill.skillId);
            }
            //---vang---luong--luongKhoa
            msg.writer().writeInt(pl.vang);
            msg.writer().writeInt(pl.luong);
            msg.writer().writeInt(pl.luongKhoa);

            //--------itemBody---------
            ArrayList<Item> itemsBody = pl.itemsBody;
            msg.writer().writeByte(itemsBody.size());
            for (Item item : itemsBody) {
                if (item.id == -1) {
                    msg.writer().writeShort(-1);
                } else {
                    msg.writer().writeShort(item.template.id);
                    msg.writer().writeInt(item.quantity);
                    msg.writer().writeUTF(item.getInfo());
                    msg.writer().writeUTF(item.getContent());
                    ArrayList<ItemOption> itemOptions = item.itemOptions;
                    msg.writer().writeByte(itemOptions.size());
                    for (ItemOption itemOption : itemOptions) {
                        msg.writer().writeByte(itemOption.optionTemplate.id);
                        msg.writer().writeShort(itemOption.param);
                    }
                }

            }

            //--------itemBag---------
            ArrayList<Item> itemsBag = pl.itemsBag;
            msg.writer().writeByte(itemsBag.size());
            for (int i = 0; i < itemsBag.size(); i++) {
                Item item = itemsBag.get(i);
                if (item.id == -1) {
                    msg.writer().writeShort(-1);
                } else {
                    msg.writer().writeShort(item.template.id);
                    msg.writer().writeInt(item.quantity);
                    msg.writer().writeUTF(item.getInfo());
                    msg.writer().writeUTF(item.getContent());
                    ArrayList<ItemOption> itemOptions = item.itemOptions;
                    msg.writer().writeByte(itemOptions.size());
                    for (ItemOption itemOption : itemOptions) {
                        msg.writer().writeByte(itemOption.optionTemplate.id);
                        msg.writer().writeShort(itemOption.param);
                    }
                }

            }

            //--------itemBox---------
            ArrayList<Item> itemsBox = pl.itemsBox;
            msg.writer().writeByte(itemsBox.size());
            for (int i = 0; i < itemsBox.size(); i++) {
                Item item = itemsBox.get(i);
                if (item.id == -1) {
                    msg.writer().writeShort(-1);
                } else {
                    msg.writer().writeShort(item.template.id);
                    msg.writer().writeInt(item.quantity);
                    msg.writer().writeUTF(item.getInfo());
                    msg.writer().writeUTF(item.getContent());
                    ArrayList<ItemOption> itemOptions = item.itemOptions;
                    msg.writer().writeByte(itemOptions.size());
                    for (ItemOption itemOption : itemOptions) {
                        msg.writer().writeByte(itemOption.optionTemplate.id);
                        msg.writer().writeShort(itemOption.param);
                    }
                }
            }
            //-----------------
            msg.writer().write(FileIO.readFile("data/head"));
            //-----------------
            msg.writer().writeShort(514);
            msg.writer().writeShort(515);
            msg.writer().writeShort(537);
            msg.writer().writeByte(0);
            msg.writer().writeInt(1632811835);
            msg.writer().writeByte(0);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            System.out.print("info player: ");
            e.printStackTrace();
        }
    }

    public void updateItemBody(Session session, Player player) {
        Message msg;
        try {
            ArrayList<Item> itemsBody = player.itemsBody;
            msg = new Message(-37);
            msg.writer().writeByte(0);
            msg.writer().writeShort(player.getHead());
            msg.writer().writeByte(itemsBody.size());
            for (Item item : itemsBody) {
                if (item.id == -1) {
                    msg.writer().writeShort(-1);
                } else {
                    msg.writer().writeShort(item.template.id);
                    msg.writer().writeInt(item.quantity);
                    msg.writer().writeUTF(item.getInfo());
                    msg.writer().writeUTF(item.getContent());
                    ArrayList<ItemOption> itemOptions = item.itemOptions;
                    msg.writer().writeByte(itemOptions.size());
                    for (ItemOption itemOption : itemOptions) {
                        msg.writer().writeByte(itemOption.optionTemplate.id);
                        msg.writer().writeShort(itemOption.param);
                    }

                }
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            System.out.println("-37 " + e.toString());
        }
    }

    public void updateItemBag(Session session, Player player) {
        Message msg;
        try {
            ArrayList<Item> itemsBody = player.itemsBag;
            msg = new Message(-36);
            msg.writer().writeByte(0);
            msg.writer().writeByte(itemsBody.size());
            for (Item item : itemsBody) {
                if (item.id == -1) {
                    msg.writer().writeShort(-1);
                } else {
                    msg.writer().writeShort(item.template.id);
                    msg.writer().writeInt(item.quantity);
                    msg.writer().writeUTF(item.getInfo());
                    msg.writer().writeUTF(item.getContent());
                    ArrayList<ItemOption> itemOptions = item.itemOptions;
                    msg.writer().writeByte(itemOptions.size());
                    for (ItemOption itemOption : itemOptions) {
                        msg.writer().writeByte(itemOption.optionTemplate.id);
                        msg.writer().writeShort(itemOption.param);
                    }

                }
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            System.out.print("-36 ");
            e.printStackTrace();
        }
    }

    public void updateItemBox(Session session, Player player) {
        Message msg;
        try {
            ArrayList<Item> itemsBody = player.itemsBox;
            msg = new Message(-35);
            msg.writer().writeByte(0);
            msg.writer().writeByte(itemsBody.size());
            for (Item item : itemsBody) {
                if (item.id == -1) {
                    msg.writer().writeShort(-1);
                } else {
                    msg.writer().writeShort(item.template.id);
                    msg.writer().writeInt(item.quantity);
                    msg.writer().writeUTF(item.info);
                    msg.writer().writeUTF(item.content);
                    ArrayList<ItemOption> itemOptions = item.itemOptions;
                    msg.writer().writeByte(itemOptions.size());
                    for (ItemOption itemOption : itemOptions) {
                        msg.writer().writeByte(itemOption.optionTemplate.id);
                        msg.writer().writeShort(itemOption.param);
                    }

                }
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            System.out.println("-35 " + e.toString());
        }
    }

    private Message messageNotLogin(byte command) throws IOException {
        Message ms = new Message(-29);
        ms.writer().writeByte(command);
        return ms;
    }

    private Message messageNotMap(byte command) throws IOException {
        Message ms = new Message(-28);
        ms.writer().writeByte(command);
        return ms;
    }

    private Message messageSubCommand(byte command) throws IOException {
        Message ms = new Message(-30);
        ms.writer().writeByte(command);
        return ms;
    }

}
