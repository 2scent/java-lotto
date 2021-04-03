package lotto.domain;

import java.util.Objects;

public class ExchangeableCount {
    private static final int MIN_EXCHANGEABLE_COUNT = 0;
    private static final String MIN_EXCHANGEABLE_COUNT_ERROR_MESSAGE = String.format(
            "exchangeableCount의 값은 %d 이상이어야 합니다.", MIN_EXCHANGEABLE_COUNT);

    private final int exchangeableCount;

    public ExchangeableCount(int exchangeableCount) {
        if (exchangeableCount < MIN_EXCHANGEABLE_COUNT) {
            throw new IllegalArgumentException(MIN_EXCHANGEABLE_COUNT_ERROR_MESSAGE);
        }
        this.exchangeableCount = exchangeableCount;
    }

    public boolean exchangeable(ExchangeableCount that) {
        return exchangeableCount >= that.exchangeableCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeableCount that = (ExchangeableCount) o;
        return exchangeableCount == that.exchangeableCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeableCount);
    }
}
