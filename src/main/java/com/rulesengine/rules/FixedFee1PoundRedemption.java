package com.rulesengine.rules;

import com.rulesengine.types.Fee;
import com.rulesengine.types.Transaction;
import com.rulesengine.types.TransactionType;

import java.math.BigDecimal;

public class FixedFee1PoundRedemption implements FeeRule {

    private final Transaction transaction;

    public FixedFee1PoundRedemption(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public Fee applyFee(Transaction txn) {
        BigDecimal amt = transaction.type().equals(TransactionType.REDEMPTION) ?
                BigDecimal.ONE
                : BigDecimal.ZERO;

        return new Fee(amt, txn);
    }
}
