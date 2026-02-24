package com.rulesengine.types;

import java.math.BigDecimal;

public record Fee(BigDecimal amount, Transaction transaction) {
}
