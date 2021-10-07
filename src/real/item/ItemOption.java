package real.item;

import server.Util;

public class ItemOption {

    public short param;

    public ItemOptionTemplate optionTemplate;

    public ItemOption() {
    }

    public ItemOption(int tempId, short param) {
        this.optionTemplate = ItemData.iOptionTemplates[tempId];
        this.param = param;
    }

    public String getOptionString() {
        return Util.replace(this.optionTemplate.name, "#", String.valueOf(this.param));
    }

}
