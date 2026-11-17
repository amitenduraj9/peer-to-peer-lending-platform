package com.p2plending.entity;

/** Lifecycle status of a loan application. */
public enum LoanStatus {
    PENDING,   // waiting for admin verification
    APPROVED,  // admin approved, waiting for a lender to fund it
    REJECTED,  // admin rejected the request
    FUNDED,    // a lender has funded the loan; repayment schedule generated
    CLOSED     // all EMIs repaid
}
