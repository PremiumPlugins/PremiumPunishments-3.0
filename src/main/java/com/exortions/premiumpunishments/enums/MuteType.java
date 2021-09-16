package com.exortions.premiumpunishments.enums;

public enum MuteType {

    mute("mute"),
    perm("perm"),
    ;

    private final String type;

    MuteType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
