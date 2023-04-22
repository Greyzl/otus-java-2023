package ru.otus.entity.enums;

public enum Permission {
    GRANTED(true),
    DENIED(false);

    private final boolean permissionFlag;

    private Permission(boolean permissionFlag){
        this.permissionFlag = permissionFlag;
    }

    public boolean isPermissionFlag(){
        return permissionFlag;
    }
}
