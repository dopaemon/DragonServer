package real.item;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import server.FileIO;
import server.Util;

public class ItemData {

    public static ItemOptionTemplate[] iOptionTemplates;

    public static void loadDataItem() {
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(FileIO.readFile("data/msg/1630679754440_-28_8_r"));
            DataInputStream dis = new DataInputStream(is);
            dis.readByte();
            dis.readByte();
            dis.readByte();
            int num = (int) dis.readShort();
            for (int j = 0; j < num; j++) {
                ItemTemplate it = new ItemTemplate();
                it.id = (short) j;
                it.type = dis.readByte();
                it.gender = dis.readByte();
                it.name = dis.readUTF();
                it.description = dis.readUTF();
                it.level = dis.readByte();
                it.strRequire = dis.readInt();
                it.iconID = dis.readShort();
                it.part = dis.readShort();
                it.isUpToUp = dis.readBoolean();
                ItemTemplates.add(it);
            }
            Util.log("finish load ItemTemplate");
            is = new ByteArrayInputStream(FileIO.readFile("data/msg/1630679754405_-28_8_r"));
            dis = new DataInputStream(is);
            dis.readByte();
            dis.readByte();
            dis.readByte();
            iOptionTemplates = new ItemOptionTemplate[(int) dis.readUnsignedByte()];
            for (int i = 0; i < iOptionTemplates.length; i++) {
                iOptionTemplates[i] = new ItemOptionTemplate();
                iOptionTemplates[i].id = i;
                iOptionTemplates[i].name = dis.readUTF();
                iOptionTemplates[i].type = (int) dis.readByte();
            }
            Util.log("finish load ItemOptionTemplate");
        } catch (Exception e) {
        }
    }
}
