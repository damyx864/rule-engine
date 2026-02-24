package com.rulesengine.products;

import com.rulesengine.rules.FeeRule;
import com.rulesengine.types.Fee;
import com.rulesengine.types.Transaction;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProductDefinition {

    @Getter
    private final String name;
    private final Set<FeeRule> feeChain = new LinkedHashSet<>();

    public ProductDefinition(String name) {
        this.name = name;
    }

    protected void addFee(FeeRule feeRule) {
        feeChain.add(feeRule);
    }

    public BigDecimal getFeeAmount(Transaction transaction) {
        if(transaction == null)
            return BigDecimal.ZERO;

        return this.feeChain
                .stream()
                .map(fr -> fr.applyFee(transaction))
                .map(Fee::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
