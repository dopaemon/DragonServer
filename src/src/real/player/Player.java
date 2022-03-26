package real.player;
//share by chibikun
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import real.clan.Clan;
import real.item.Item;
import real.map.Map;
import real.map.MapService;
import real.map.WayPoint;
import real.skill.NClass;
import real.skill.Skill;
import server.Service;
import server.io.Session;

public class Player {

    public Map map;

    public Session session;

    public int id;

    public short x;

    public short y;

    public String name;

    public short taskId;

    public byte taskIndex;

    public byte gender;

    public short head;

    public long power;

    public int vang;

    public int luongKhoa;

    public int luong;

    public int hpGoc;

    public int mpGoc;

    public int hp;

    public int mp;

    public int damGoc;

    public short defGoc;

    public byte critGoc;

    public byte typePk;

    public byte limitPower;

    public long tiemNang;

    public NClass nClass;

    public Clan clan;

    public Skill selectSkill;

    public ArrayList<Item> itemsBody;

    public ArrayList<Item> itemsBag;

    public ArrayList<Item> itemsBox;

    public ArrayList<Player> nearPlayers;

    public Timer timer;

    public ArrayList<Skill> skills;

    public static short[][] infoId = {{281, 361, 351}, {512, 513, 536}, {514, 515, 537}};

    public Player() {
        this.itemsBody = new ArrayList<>();
        this.itemsBag = new ArrayList<>();
        this.itemsBox = new ArrayList<>();
        this.nearPlayers = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.timer = new Timer();
    }

    public void active() {
        this.timer = new Timer();
        this.timer.schedule(new PlayerTask(), 10000L, 30000L);
    }

    public void gotoMap(Map _map) {
        if (this.map != null && _map != null) {
            this.map.getPlayers().remove(this);
            if (this.map != _map) {
                //send msg exit to player in map
                MapService.gI().exitMap(this, this.map);
            }
            this.map = _map;
            this.map.getPlayers().add(this);
        }
    }

    public short getHead() {
        return head;
    }

    public short getBody() {
        if (itemsBody.get(0).id != -1) {
            return itemsBody.get(0).template.part;
        }
        return -1;
    }

    public short getLeg() {
        if (itemsBody.get(1).id != -1) {
            return itemsBody.get(0).template.part;
        }
        return -1;
    }

    public short getMount() {
        for (Item item : itemsBag) {
            if (item.id != -1) {
                if (item.template.type == 23 || item.template.type == 24) {
                    return item.template.id;
                }
            }
        }
        return -1;
    }

    public int getHpFull() {
        return hpGoc;
    }

    public int getMpFull() {
        return mpGoc;
    }

    public int getDamFull() {
        return damGoc;
    }

    public short getDefFull() {
        return defGoc;
    }

    public byte getCritFull() {
        return critGoc;
    }

    public byte getSpeed() {
        return 7;
    }

    public void setItemBody(Item item) {
        int index = -1;
        if (item.id != -1) {
            if (item.template.gender == this.gender) {
                if (item.template.strRequire <= this.power) {
                    if (item.template.type >= 0 && item.template.type <= 5) {
                        index = item.template.type;
                    }
                    if (item.template.type == 32) {
                        index = 6;
                    }
                } else {
                    Service.getInstance().serverMessage(this.session, "Sức mạnh không đủ yêu cầu");
                }
            } else {
                Service.getInstance().serverMessage(this.session, "Sai hành tinh");
            }
        }
        if (index != -1) {
            this.itemsBody.set(index, item);
        }
    }

    public void itemBodyToBag(int index) {
        Item _item = this.itemsBody.get(index);
        for (int i = 0; i < this.itemsBag.size(); i++) {
            Item item = this.itemsBag.get(i);
            if (item.id == -1) {
                this.itemsBag.set(i, _item);
                removeItemBody(index);
                Service.getInstance().updateItemBody(session, this);
                Service.getInstance().updateItemBag(session, this);
                break;
            }
        }
    }

    public void itemBagToBody(int index) {
        Item item = this.itemsBag.get(index);
        setItemBody(item);
        removeItemBag(index);
        Service.getInstance().updateItemBag(session, this);
        Service.getInstance().updateItemBody(session, this);
    }
    
    public void removeItemBag(int index){
        Item item = new Item();
        item.id = -1;
        this.itemsBag.set(index, item);
    }
    
    public void removeItemBody(int index){
        Item item = new Item();
        item.id = -1;
        this.itemsBody.set(index, item);
    }
    
    public void removeItemBox(int index){
        Item item = new Item();
        item.id = -1;
        this.itemsBody.set(index, item);
    }

    public void increasePoint(byte type, short point) {
        if (point <= 0) {
            return;
        }
        long tiemNangUse = 0;
        if (type == 0) {
            int pointHp = point * 20;
            tiemNangUse = point * (2 * (this.hpGoc + 1000) + pointHp - 20) / 2;
            if ((this.hpGoc + pointHp) <= getHpMpLimit()) {
                if (useTiemNang(tiemNangUse)) {
                    hpGoc += pointHp;
                }
            } else {
                Service.getInstance().serverMessage(this.session, "Vui lòng mở giới hạn sức mạnh");
                return;
            }
        }
        if (type == 1) {
            int pointMp = point * 20;
            tiemNangUse = point * (2 * (this.mpGoc + 1000) + pointMp - 20) / 2;
            if ((this.mpGoc + pointMp) <= getHpMpLimit()) {
                if (useTiemNang(tiemNangUse)) {
                    mpGoc += pointMp;
                }
            } else {
                Service.getInstance().serverMessage(this.session, "Vui lòng mở giới hạn sức mạnh");
                return;
            }
        }
        if (type == 2) {
            tiemNangUse = point * (2 * this.damGoc + point - 1) / 2 * 100;
            if ((this.damGoc + point) <= getDamLimit()) {
                if (useTiemNang(tiemNangUse)) {
                    damGoc += point;
                }
            } else {
                Service.getInstance().serverMessage(this.session, "Vui lòng mở giới hạn sức mạnh");
                return;
            }
        }
        if (type == 3) {
            tiemNangUse = 2 * (this.defGoc + 5) / 2 * 100000;
            if ((this.defGoc + point) <= getDefLimit()) {
                if (useTiemNang(tiemNangUse)) {
                    defGoc += point;
                }
            } else {
                Service.getInstance().serverMessage(this.session, "Vui lòng mở giới hạn sức mạnh");
                return;
            }
        }
        if (type == 4) {
            tiemNangUse = 50000000L;
            for (int i = 0; i < this.critGoc; i++) {
                tiemNangUse *= 5L;
            }
            if ((this.critGoc + point) <= getCrifLimit()) {
                if (useTiemNang(tiemNangUse)) {
                    critGoc += point;
                }
            } else {
                Service.getInstance().serverMessage(this.session, "Vui lòng mở giới hạn sức mạnh");
                return;
            }
        }
        Service.getInstance().point(this.session, this);
    }

    public boolean useTiemNang(long tiemNang) {
        if (this.tiemNang < tiemNang) {
            Service.getInstance().serverMessage(this.session, "Bạn không đủ tiềm năng");
            return false;
        }
        if (this.tiemNang >= tiemNang) {
            this.tiemNang -= tiemNang;
            return true;
        }
        return false;
    }

    public int getHpMpLimit() {
        if (limitPower == 0) {
            return 220000;
        }
        if (limitPower == 1) {
            return 240000;
        }
        if (limitPower == 2) {
            return 300000;
        }
        if (limitPower == 3) {
            return 350000;
        }
        if (limitPower == 4) {
            return 400000;
        }
        if (limitPower == 5) {
            return 450000;
        }
        return 0;
    }

    public int getDamLimit() {
        if (limitPower == 0) {
            return 11000;
        }
        if (limitPower == 1) {
            return 12000;
        }
        if (limitPower == 2) {
            return 15000;
        }
        if (limitPower == 3) {
            return 18000;
        }
        if (limitPower == 4) {
            return 20000;
        }
        if (limitPower == 5) {
            return 22000;
        }
        return 0;
    }

    public short getDefLimit() {
        if (limitPower == 0) {
            return 550;
        }
        if (limitPower == 1) {
            return 600;
        }
        if (limitPower == 2) {
            return 700;
        }
        if (limitPower == 3) {
            return 800;
        }
        if (limitPower == 4) {
            return 100;
        }
        if (limitPower == 5) {
            return 22000;
        }
        return 0;
    }

    public byte getCrifLimit() {
        if (limitPower == 0) {
            return 5;
        }
        if (limitPower == 1) {
            return 6;
        }
        if (limitPower == 2) {
            return 7;
        }
        if (limitPower == 3) {
            return 8;
        }
        if (limitPower == 4) {
            return 9;
        }
        if (limitPower == 5) {
            return 10;
        }
        return 0;
    }

    public void move(short _toX, short _toY) {
        if (_toX != this.x) {
            this.x = _toX;
        }
        if (_toY != this.y) {
            this.y = _toY;
        }
        MapService.gI().playerMove(this);
    }

    public WayPoint isInWaypoint() {
        for (WayPoint wp : map.wayPoints) {
            if (x >= wp.minX && x <= wp.maxX && y >= wp.minY && y <= wp.maxY) {
                return wp;
            }
        }
        return null;
    }

    public void update() {
        if (this.mp < this.getMpFull()) {
            this.mp = this.getMpFull();
            Service.getInstance().point(this.session, this);
        }
    }
    public void updatehp() {
        if (this.hp < this.getHpFull()) {
            this.hp = this.getHpFull();
            Service.getInstance().point(this.session, this);
        }
    }
    class PlayerTask extends TimerTask {

        @Override
        public void run() {
            Player.this.update();
        }

    }

}
