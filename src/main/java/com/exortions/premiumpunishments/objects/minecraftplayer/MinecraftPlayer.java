package com.exortions.premiumpunishments.objects.minecraftplayer;

import com.exortions.premiumpunishments.PremiumPunishments;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
public class MinecraftPlayer {

    private String uuid;
    private Player player;
    private String username;

    private Boolean banned;
    private Timestamp banexpirydate;

    private Boolean muted;
    private Timestamp muteexpirydate;

    private Integer warns;
    private Integer kicks;

    public MinecraftPlayer(String uuid, String username, Boolean banned, Timestamp banexpirydate, Boolean muted, Timestamp muteexpirydate, Integer warns, Integer kicks) {
        this.uuid = Objects.requireNonNullElse(uuid, "");
        this.player = Bukkit.getPlayer(uuid);
        this.username = Objects.requireNonNullElse(username, "");
        this.banned = Objects.requireNonNullElse(banned, false);
        this.banexpirydate = Objects.requireNonNullElse(banexpirydate, new Timestamp(System.currentTimeMillis()));
        this.muted = Objects.requireNonNullElse(muted, false);;
        this.muteexpirydate = Objects.requireNonNullElse(muteexpirydate, new Timestamp(System.currentTimeMillis()));;
        this.warns = Objects.requireNonNullElse(warns, 0);
        this.kicks = Objects.requireNonNullElse(kicks, 0);
    }

    public void updateSql() {
        String sql = "DELETE from players where uuid='" + uuid + "'";
        PremiumPunishments.getPlugin().getDatabase().execute(sql);

        PremiumPunishments.getPlugin().getDatabase().insertPlayer(uuid, username, banned, banexpirydate, muted, muteexpirydate, warns, kicks);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        updateSql();
    }

    public void setPlayer(Player player) {
        this.player = player;
        updateSql();
    }

    public void setUsername(String username) {
        this.username = username;
        updateSql();
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
        updateSql();
    }

    public void setBanexpirydate(Timestamp banexpirydate) {
        this.banexpirydate = banexpirydate;
        updateSql();
    }

    public void setMuted(Boolean muted) {
        this.muted = muted;
        updateSql();
    }

    public void setMuteexpirydate(Timestamp muteexpirydate) {
        this.muteexpirydate = muteexpirydate;
        updateSql();
    }

    public void setWarns(Integer warns) {
        this.warns = warns;
        updateSql();
    }

    public void setKicks(Integer kicks) {
        this.kicks = kicks;
        updateSql();
    }

    @Override
    public String toString() {
        return "MinecraftPlayer{" +
                "uuid='" + uuid + '\'' +
                ", player=" + player +
                ", username='" + username + '\'' +
                ", banned=" + banned +
                ", banexpirydate=" + banexpirydate +
                ", muted=" + muted +
                ", muteexpirydate=" + muteexpirydate +
                ", warns=" + warns +
                ", kicks=" + kicks +
                '}';
    }

}
