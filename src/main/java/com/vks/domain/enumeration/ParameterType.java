package com.vks.domain.enumeration;

/**
 * The ParameterType enumeration.
 */
public enum ParameterType {
    ACCOUNT("Account"),
    RELIGION("Religion"),
    CASTE("Caste"),
    CATEGORY("Category"),
    FARMER("Farmer"),
    RESOLUTION("Resolution"),
    EXECUTIVE_TITLE("Executive_Title"),
    BELONGING("Belonging"),
    INVESTMENT("Investment"),
    MEASURING_UNIT("Measuring_Unit");

    private final String value;

    ParameterType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
