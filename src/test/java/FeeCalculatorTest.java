import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import com.rule_engine.calculator.FeeCalculator;
import com.rule_engine.calculator.FeeCalculatorImpl;
import com.rule_engine.products.ProductDefinition;
import com.rule_engine.products.ThomasTravelProductDefinition;
import com.rule_engine.products.TiscoFinanceProductDefinition;
import com.rule_engine.types.Transaction;
import com.rule_engine.types.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.rule_engine.types.TransactionType.REDEMPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeeCalculatorTest {

    @Test
    void testThomasTravelWithRedemptionWithForeignExchangeFee() {

        Transaction txnTT = new Transaction(REDEMPTION, 7900, LocalDateTime.now(), true);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition thomasTravelProductDefinition = new ThomasTravelProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(218, fc.calculateFee(txnTT, thomasTravelProductDefinition));
        assertEquals("THOMAS TRAVEL", thomasTravelProductDefinition.getName());
    }

    @Test
    void testThomasTravelWithRedemptionWithoutForeignExchangeFee() {

        Transaction txnTT = new Transaction(REDEMPTION, 7900, LocalDateTime.now(), false);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition thomasTravelProductDefinition = new ThomasTravelProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(100, fc.calculateFee(txnTT, thomasTravelProductDefinition));
        assertEquals("THOMAS TRAVEL", thomasTravelProductDefinition.getName());
    }

    @ParameterizedTest
    @EnumSource(
            value = TransactionType.class,
            names = {"REDEMPTION"},
            mode = EnumSource.Mode.EXCLUDE)
    void testThomasTravelWithoutRedemptionWithForeignExchangeFee(TransactionType transactionType) {

        Transaction txnTT = new Transaction(transactionType, 7900, LocalDateTime.now(), true);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition thomasTravelProductDefinition = new ThomasTravelProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(118, fc.calculateFee(txnTT, thomasTravelProductDefinition));
        assertEquals("THOMAS TRAVEL", thomasTravelProductDefinition.getName());
    }

    @ParameterizedTest
    @EnumSource(
            value = TransactionType.class,
            names = {"REDEMPTION"},
            mode = EnumSource.Mode.EXCLUDE)
    void testThomasTravelWithoutRedemptionWithoutForeignExchangeFee(TransactionType transactionType) {

        Transaction txnTT = new Transaction(transactionType, 7900, LocalDateTime.now(), false);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition thomasTravelProductDefinition = new ThomasTravelProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(0, fc.calculateFee(txnTT, thomasTravelProductDefinition));
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
        Transaction txnTT = new Transaction(transactionType, 7900, before10PM, false);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition tiscoFinanceProductDefinition = new TiscoFinanceProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(0, fc.calculateFee(txnTT, tiscoFinanceProductDefinition));
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

        Transaction txnTT = new Transaction(transactionType, 7900, after10PM, false);
        // 79 monetary units (pounds, dollars, euros... with subdivisions)

        ProductDefinition tiscoFinanceProductDefinition = new TiscoFinanceProductDefinition(txnTT);

        FeeCalculator fc = new FeeCalculatorImpl();

        assertEquals(395, fc.calculateFee(txnTT, tiscoFinanceProductDefinition));
        assertEquals("TISCO FINANCE", tiscoFinanceProductDefinition.getName());
    }

}
