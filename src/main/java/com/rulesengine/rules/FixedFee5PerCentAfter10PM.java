package com.rulesengine.rules;

import com.rulesengine.types.Fee;
import com.rulesengine.types.Transaction;

import java.math.BigDecimal;

public class FixedFee5PerCentAfter10PM implements FeeRule {

    private final BigDecimal FIVE_PERCENT = BigDecimal.valueOf(5, 2);
    private final Transaction transaction;

    public FixedFee5PerCentAfter10PM(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public Fee applyFee(Transaction txn) {
        int TEN_PM = 22;

        BigDecimal amt = transaction.time().getHour() >= TEN_PM ?
                (txn.value().multiply(FIVE_PERCENT))
                : BigDecimal.ZERO;

        return new Fee(amt, txn);
    }
}
