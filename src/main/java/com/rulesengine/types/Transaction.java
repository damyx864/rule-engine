package com.rulesengine.types;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(TransactionType type, BigDecimal value, LocalDateTime time, boolean foreignExchange) {

}
