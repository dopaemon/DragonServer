package real.item;

import java.util.ArrayList;

public class Item {

    public int id;

    public ItemTemplate template;

    public String info;

    public String content;

    public int quantity;

    public ArrayList<ItemOption> itemOptions = new ArrayList<>();

    public String getInfo() {
        String strInfo = "";
        for (ItemOption itemOption : itemOptions) {
            strInfo += itemOption.getOptionString();
        }
        return strInfo;
    }

    public String getContent() {
        return "Yêu cầu sức mạnh " + this.template.strRequire + " trở lên";
    }

}
