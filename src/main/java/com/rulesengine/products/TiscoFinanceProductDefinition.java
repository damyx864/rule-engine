package com.rulesengine.products;

import com.rulesengine.types.Transaction;
import com.rulesengine.rules.FixedFee5PerCentAfter10PM;

public class TiscoFinanceProductDefinition extends ProductDefinition {

    public TiscoFinanceProductDefinition(Transaction transaction) {
        super("TISCO FINANCE");
        this.addFee(new FixedFee5PerCentAfter10PM(transaction));
    }
}
