package real.player;

import java.util.ArrayList;
import java.util.Timer;
import real.item.ItemDAO;

public class PlayerManger {

    private static PlayerManger instance;

    private ArrayList<Player> players;
    
    private Timer timer;

    public PlayerManger() {
        this.players = new ArrayList<>();
        this.timer = new Timer();
    }
    
    public static PlayerManger gI(){
        if (instance == null){
            instance = new PlayerManger();
        }
        return instance;
    }
    
    public Player load(int userId){
        Player player = PlayerDAO.load(userId);
        ItemDAO.loadPlayerItems(player);
        return player;
    }

    public Player getPlayerByUserID(int _userID) {
        for (Player player : players) {
            if (player.session.userId == _userID){
                return player;
            }
        }
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public int size(){
        return players.size();
    }

}
