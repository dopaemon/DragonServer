package real.clan;

import real.clan.Clan;
import real.clan.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import server.DBService;

public class ClanManager {

    private ArrayList<Clan> clans;

    private static ClanManager instance;

    public static ClanManager gI() {
        if (instance == null) {
            instance = new ClanManager();
        }
        return instance;
    }

    public void init() {
        clans = ClanDAO.load();
    }

    public Clan getClanById(int id) {
        for (Clan clan : clans) {
            if (clan.id == id) {
                return clan;
            }
        }
        return null;
    }
    
    public ArrayList<Clan> search(String text){
        ArrayList<Clan> listClan = new ArrayList<>();
        for (Clan clan : clans) {
            if (clan.name.startsWith(text)){
                listClan.add(clan);
            }
        }
        return listClan;
    }
    
    public ArrayList<Member> getMemberByIdClan(int id){
        for (Clan clan : clans) {
            if (clan.id == id) {
                return clan.members;
            }
        }
        return null;
    }
}
