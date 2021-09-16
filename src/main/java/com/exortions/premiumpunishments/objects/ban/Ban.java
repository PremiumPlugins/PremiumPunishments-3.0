package com.exortions.premiumpunishments.objects.ban;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.enums.BanType;
import lombok.Getter;

@Getter
public class Ban {

    private String uuid;
    private String username;
    private BanType type;
    private String reason;
    private String admin;
    private int id;

    public Ban(String uuid, String username, BanType type, String reason, String admin, int id) {
        this.uuid = uuid;
        this.username = username;
        this.type = type;
        this.reason = reason;
        this.admin = admin;
        this.id = id;
    }

    public void updateSql() {
        String sql = "UPDATE `" + PremiumPunishments.getPlugin().getDatabase().getDatabase() + "`.`bans` SET" +
                "uuid='" + uuid + "'," +
                "username='" + username + "'," +
                "ban-type='" + type.getType() + "'," +
                "reason='" + reason + "'," +
                "admin='" + admin + "'," +
                "id='" + id + "' WHERE uuid='" + uuid + "'";
        PremiumPunishments.getPlugin().getDatabase().execute(sql);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        updateSql();
    }

    public void setUsername(String username) {
        this.username = username;
        updateSql();
    }

    public void setType(BanType type) {
        this.type = type;
        updateSql();
    }

    public void setReason(String reason) {
        this.reason = reason;
        updateSql();
    }

    public void setAdmin(String admin) {
        this.admin = admin;
        updateSql();
    }

    public void setId(int id) {
        this.id = id;
        updateSql();
    }

    @Override
    public String toString() {
        return "Ban{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", type=" + type +
                ", reason='" + reason + '\'' +
                ", admin='" + admin + '\'' +
                ", id=" + id +
                '}';
    }
}
