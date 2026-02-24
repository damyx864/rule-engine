package com.rulesengine.rules;

import com.rulesengine.types.Fee;
import com.rulesengine.types.Transaction;

public interface FeeRule {

    Fee applyFee(Transaction transaction);
}
