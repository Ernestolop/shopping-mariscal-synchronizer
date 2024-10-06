package com.elopez.mariscal.synchronizer.modules.sender.entity;

public enum Currency {

    GS(true),
    US(false);

    private boolean isLocal = false;

    Currency(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public boolean isLocal() {
        return isLocal;
    }

}
