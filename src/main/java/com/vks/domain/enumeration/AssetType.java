package com.vks.domain.enumeration;

/**
 * The AssetType enumeration.
 */
public enum AssetType {
    FARM_MACHINERY("Farm_Machinery"),
    HOUSE("House"),
    OTHERS("Others");

    private final String value;

    AssetType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
