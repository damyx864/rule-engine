package com.rulesengine.products;

import com.rulesengine.rules.FixedFee1PoundRedemption;
import com.rulesengine.types.Transaction;
import com.rulesengine.rules.AdditionalFeeForeignExchange;

public class ThomasTravelProductDefinition extends ProductDefinition {

    public ThomasTravelProductDefinition(Transaction transaction) {
        super("THOMAS TRAVEL");
        this.addFee(new FixedFee1PoundRedemption(transaction));
        this.addFee(new AdditionalFeeForeignExchange(transaction));
    }
}
