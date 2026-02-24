package com.rulesengine.calculator;

import com.rulesengine.products.ProductDefinition;
import com.rulesengine.types.Transaction;

import java.math.BigDecimal;

public class FeeCalculatorImpl implements FeeCalculator {

    @Override
    public BigDecimal calculateFee(Transaction txn, ProductDefinition product) {

        return product.getFeeAmount(txn);
    }
}
