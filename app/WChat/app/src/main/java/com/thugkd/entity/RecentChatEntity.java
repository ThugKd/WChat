package com.thugkd.entity;

/**
 * Created by thugkd on 06/04/2017.
 */

public class RecentChatEntity {
    private String id;
    private String img;
    private int count;
    private String name;
    private String time;
    private String msg;

    public RecentChatEntity() {
        // TODO Auto-generated constructor stub
    }

    public RecentChatEntity(String id, String img, int count, String name,
                            String time, String msg) {
        super();
        this.id = id;
        this.img = img;
        this.count = count;
        this.name = name;
        this.time = time;
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean equals(Object object) {
        if (object == null)// å¦‚æžœæ˜¯ç©ºå¯¹è±¡ï¼Œè‚¯å®šæ˜¯ä¸åŒçš?
            return false;

        if (object == this)// å¦‚æžœæ˜¯åŒä¸?ä¸ªå¯¹è±¡ï¼Œè‚¯å®šæ˜¯ç›¸åŒçš„
            return true;

        if (object instanceof RecentChatEntity) {// å¦‚æžœidç›¸åŒï¼Œæˆ‘ä»¬å°±è®¤ä¸ºæ˜¯åŒä¸?ä¸ªå¯¹è±?,å› ä¸ºidæ˜¯å”¯ä¸?çš„ï¼Œå¯¹äºŽæˆ‘è¿™ä¸ªå°é¡¹ç›®æ¥è¯´
            RecentChatEntity entity = (RecentChatEntity) object;
            if (entity.id == this.id)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "RecentChatEntity [id=" + id + ", img=" + img + ", count="
                + count + ", name=" + name + ", time=" + time + ", msg=" + msg
                + "]";
    }
}
