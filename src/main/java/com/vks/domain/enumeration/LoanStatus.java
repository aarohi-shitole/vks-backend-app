package com.vks.domain.enumeration;

/**
 * The LoanStatus enumeration.
 */
public enum LoanStatus {
    APPLIED("Applied"),
    PENDING("Pending"),
    CHART_GENRATED("Chart_Genrated"),
    APPROVED("Approved"),
    DISBURSED("Disbursed"),
    ACTIVE("Active"),
    CLOSED("Closed");

    private final String value;

    LoanStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
