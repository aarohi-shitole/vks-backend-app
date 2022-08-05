package com.vks.domain.enumeration;

/**
 * The Status enumeration.
 */
public enum Status {
    CREATED("Created"),
    DOCUMENT_VERIFIED("Document_Verified"),
    KYC_GENREATED("KYC_Genreated"),
    KMP_GENREATED("KMP_Genreated"),
    LOAN_DEMAND("loan_Demand"),
    LOAN_ACTIVE("Loan_Active");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
