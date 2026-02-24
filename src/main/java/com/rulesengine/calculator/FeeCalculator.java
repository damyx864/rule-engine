package com.rulesengine.calculator;

import com.rulesengine.products.ProductDefinition;
import com.rulesengine.types.Transaction;

import java.math.BigDecimal;

public interface FeeCalculator {

    BigDecimal calculateFee(Transaction txn, ProductDefinition product);
}
