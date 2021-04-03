package lotto.domain;

import lotto.factories.LottoTicketFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LottoCoupon {
    private static final int MIN_EXCHANGEABLE_TICKETS_COUNT = 0;
    private static final String MIN_EXCHANGEABLE_TICKETS_COUNT_ERROR_MESSAGE = String.format(
            "exchangeableTicketsCount의 값은 %d 이상이어야 합니다.", MIN_EXCHANGEABLE_TICKETS_COUNT);
    private static final String EXCHANGEABLE_ERROR_MESSAGE = "%d ~ %d 값을 입력해주세요.";

    private final int exchangeableTicketsCount;

    public LottoCoupon(int exchangeableTicketsCount) {
        if (exchangeableTicketsCount < MIN_EXCHANGEABLE_TICKETS_COUNT) {
            throw new IllegalArgumentException(MIN_EXCHANGEABLE_TICKETS_COUNT_ERROR_MESSAGE);
        }
        this.exchangeableTicketsCount = exchangeableTicketsCount;
    }

    public void validateExchangeable(int count) {
        if (count < MIN_EXCHANGEABLE_TICKETS_COUNT || count > exchangeableTicketsCount) {
            throw new IllegalArgumentException(
                    String.format(EXCHANGEABLE_ERROR_MESSAGE, MIN_EXCHANGEABLE_TICKETS_COUNT, exchangeableTicketsCount)
            );
        }
    }

    public LottoBuyer lottoBuyer() {
        return lottoBuyer(new ArrayList<>());
    }

    public LottoBuyer lottoBuyer(List<LottoTicket> manualLottoTickets) {
        return new LottoBuyer(
                manualLottoTickets,
                Stream.generate(LottoTicketFactory::createAutoLottoTicket)
                        .limit(exchangeableTicketsCount - manualLottoTickets.size())
                        .collect(Collectors.toList())
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LottoCoupon that = (LottoCoupon) o;
        return exchangeableTicketsCount == that.exchangeableTicketsCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeableTicketsCount);
    }

}
