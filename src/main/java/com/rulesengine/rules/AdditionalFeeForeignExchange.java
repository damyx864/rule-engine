package com.rulesengine.rules;

import com.rulesengine.types.Fee;
import com.rulesengine.types.Transaction;

import java.math.BigDecimal;

public class AdditionalFeeForeignExchange implements FeeRule {

    private final BigDecimal ONE_POINT_FIVE_PERCENT = BigDecimal.valueOf(15, 3);
    private final Transaction transaction;

    public AdditionalFeeForeignExchange(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public Fee applyFee(Transaction txn) {
        BigDecimal amt = transaction.foreignExchange() ?
                txn.value().multiply(ONE_POINT_FIVE_PERCENT) :
                BigDecimal.ZERO;

        return new Fee(amt, txn);
    }
}
