package com.exortions.premiumpunishments.enums;

public enum BanType {
    ban("ban"),
    perm("perm")
    ;

    private final String type;

    BanType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
