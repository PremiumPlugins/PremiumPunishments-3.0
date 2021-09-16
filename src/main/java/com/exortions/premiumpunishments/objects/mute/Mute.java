package com.exortions.premiumpunishments.objects.mute;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.enums.MuteType;
import lombok.Getter;

@Getter
public class Mute {

    private String uuid;
    private String username;
    private MuteType type;
    private String reason;
    private String admin;
    private int id;

    public Mute(String uuid, String username, MuteType type, String reason, String admin, int id) {
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

    public void setType(MuteType type) {
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
        return "Mute{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", type=" + type +
                ", reason='" + reason + '\'' +
                ", admin='" + admin + '\'' +
                ", id=" + id +
                '}';
    }
}
