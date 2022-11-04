package com.ohho.valetparking.global.common.enums;

public enum PermissionLevel {

    USER(1)
    , ADMIN(2);
    private final int label;

    PermissionLevel(int label) {
        this.label = label;
    }

    public int getLabel() {
        return label;
    }
}
