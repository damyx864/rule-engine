import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import com.rulesengine.calculator.FeeCalculator;
import com.rulesengine.calculator.FeeCalculatorImpl;
import com.rulesengine.products.ProductDefinition;
import com.rulesengine.products.ThomasTravelProductDefinition;
import com.rulesengine.products.TiscoFinanceProductDefinition;
import com.rulesengine.types.Transaction;
import com.rulesengine.types.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.rulesengine.types.TransactionType.REDEMPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeeCalculatorTest {

    @Test
    void testThomasTravelWithRedemptionWithForeignExchangeFee() {

        Transaction txnTT = new Transaction(REDEMPTION, BigDecimal.valueOf(79), LocalDateTime.now(), true);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition thomasTravelProductDefinition = new ThomasTravelProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(BigDecimal.valueOf(2185, 3), fc.calculateFee(txnTT, thomasTravelProductDefinition));
        assertEquals("THOMAS TRAVEL", thomasTravelProductDefinition.getName());
    }

    @Test
    void testThomasTravelWithRedemptionWithoutForeignExchangeFee() {

        Transaction txnTT = new Transaction(REDEMPTION, BigDecimal.valueOf(79), LocalDateTime.now(), false);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition thomasTravelProductDefinition = new ThomasTravelProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(BigDecimal.ONE, fc.calculateFee(txnTT, thomasTravelProductDefinition));
        assertEquals("THOMAS TRAVEL", thomasTravelProductDefinition.getName());
    }

    @ParameterizedTest
    @EnumSource(
            value = TransactionType.class,
            names = {"REDEMPTION"},
            mode = EnumSource.Mode.EXCLUDE)
    void testThomasTravelWithoutRedemptionWithForeignExchangeFee(TransactionType transactionType) {

        Transaction txnTT = new Transaction(transactionType, BigDecimal.valueOf(79), LocalDateTime.now(), true);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition thomasTravelProductDefinition = new ThomasTravelProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(BigDecimal.valueOf(1185, 3), fc.calculateFee(txnTT, thomasTravelProductDefinition));
        assertEquals("THOMAS TRAVEL", thomasTravelProductDefinition.getName());
    }

    @ParameterizedTest
    @EnumSource(
            value = TransactionType.class,
            names = {"REDEMPTION"},
            mode = EnumSource.Mode.EXCLUDE)
    void testThomasTravelWithoutRedemptionWithoutForeignExchangeFee(TransactionType transactionType) {

        Transaction txnTT = new Transaction(transactionType, BigDecimal.valueOf(79), LocalDateTime.now(), false);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition thomasTravelProductDefinition = new ThomasTravelProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(BigDecimal.ZERO, fc.calculateFee(txnTT, thomasTravelProductDefinition));
        assertEquals("THOMAS TRAVEL", thomasTravelProductDefinition.getName());

    }

    @ParameterizedTest
    @EnumSource(TransactionType.class)
    void testTiscoFinanceBefore10PMFeeWithForeignExchange(TransactionType transactionType) {

        LocalDateTime before10PM = LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 59, 59, 999999999));
        Transaction txnTT = new Transaction(transactionType, 7900, before10PM, true);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition tiscoFinanceProductDefinition = new TiscoFinanceProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(0, fc.calculateFee(txnTT, tiscoFinanceProductDefinition));
        assertEquals("TISCO FINANCE", tiscoFinanceProductDefinition.getName());
    }

    @ParameterizedTest
    @EnumSource(TransactionType.class)
    void testTiscoFinanceBefore10PMFeeWithoutForeignExchange(TransactionType transactionType) {

        LocalDateTime before10PM = LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 59, 59, 999999999));
        Transaction txnTT = new Transaction(transactionType, BigDecimal.valueOf(79), before10PM, false);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition tiscoFinanceProductDefinition = new TiscoFinanceProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(BigDecimal.ZERO, fc.calculateFee(txnTT, tiscoFinanceProductDefinition));
        assertEquals("TISCO FINANCE", tiscoFinanceProductDefinition.getName());
    }

    @ParameterizedTest
    @EnumSource(TransactionType.class)
    void testTiscoFinanceAfter10PMFeeWithForeignExchange(TransactionType transactionType) {

        LocalDateTime after10PM = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0, 0));

        Transaction txnTT = new Transaction(transactionType, 7900, after10PM, true);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition tiscoFinanceProductDefinition = new TiscoFinanceProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(395, fc.calculateFee(txnTT, tiscoFinanceProductDefinition));
        assertEquals("TISCO FINANCE", tiscoFinanceProductDefinition.getName());
    }

    @ParameterizedTest
    @EnumSource(TransactionType.class)
    void testTiscoFinanceAfter10PMFeeWithoutForeignExchange(TransactionType transactionType) {

        LocalDateTime after10PM = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0, 0));

        Transaction txnTT = new Transaction(transactionType, BigDecimal.valueOf(79), after10PM, false);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition tiscoFinanceProductDefinition = new TiscoFinanceProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(BigDecimal.valueOf(395, 2), fc.calculateFee(txnTT, tiscoFinanceProductDefinition));
        assertEquals("TISCO FINANCE", tiscoFinanceProductDefinition.getName());
    }

}
