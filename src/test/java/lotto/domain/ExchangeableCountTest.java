package lotto.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ExchangeableCountTest {
    @Test
    public void create() {
        assertThat(new ExchangeableCount(0)).isEqualTo(new ExchangeableCount(0));
    }

    @Test
    public void create_lessThanZero() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new ExchangeableCount(-1);
        });
    }

    @ParameterizedTest
    @CsvSource(value = {"0:true", "1:true", "10:true", "11:false"}, delimiter = ':')
    public void exchangeable(String countInput, String expectedInput) {
        final int count = Integer.parseInt(countInput);
        final boolean expected = Boolean.parseBoolean(expectedInput);

        final ExchangeableCount exchangeableCount = new ExchangeableCount(10);

        assertThat(exchangeableCount.exchangeable(new ExchangeableCount(count))).isEqualTo(expected);
    }
}
