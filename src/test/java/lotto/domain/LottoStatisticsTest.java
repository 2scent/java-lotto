package lotto.domain;

import lotto.factories.LottoTicketFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LottoStatisticsTest {
    private LottoDiscriminator lottoDiscriminator;
    private List<LottoTicket> lottoTickets;

    @BeforeEach
    public void setUp() {
        final LottoTicket winningTicket = new LottoTicket(
                Arrays.asList(
                        LottoNumber.of(1),
                        LottoNumber.of(2),
                        LottoNumber.of(3),
                        LottoNumber.of(4),
                        LottoNumber.of(5),
                        LottoNumber.of(6)
                )
        );
        final LottoNumber bonusNumber = LottoNumber.of(7);

        lottoDiscriminator = new LottoDiscriminator(winningTicket, bonusNumber);
        lottoTickets = Stream.generate(LottoTicketFactory::createAutoLottoTicket)
                .limit(100)
                .collect(Collectors.toList());
    }

    @Test
    public void create() {
        final LottoStatistics expected = new LottoStatistics(lottoDiscriminator, lottoTickets);
        final LottoStatistics result = new LottoStatistics(lottoDiscriminator, lottoTickets);

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND", "THIRD", "FOURTH", "FIFTH", "MISS"})
    public void lottoTicketsCount(String LottoRankInput) {
        final LottoRank lottoRank = LottoRank.valueOf(LottoRankInput);
        final int expected = (int) lottoTickets.stream().filter(e -> lottoDiscriminator.lottoRank(e) == lottoRank).count();

        final LottoStatistics lottoStatistics = new LottoStatistics(lottoDiscriminator, lottoTickets);
        final int result = lottoStatistics.lottoTicketsCount(lottoRank);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void yield() {
        final LottoStatistics lottoStatistics = new LottoStatistics(lottoDiscriminator, lottoTickets);

        final int payment = 2000;
        final double expected = (double) totalPrize(lottoStatistics) / payment;

        final double result = lottoStatistics.yield(payment);

        assertThat(result).isEqualTo(expected);
    }


    private long totalPrize(LottoStatistics lottoStatistics) {
        return lottoTicketsPrize(lottoStatistics, LottoRank.FIRST) +
                lottoTicketsPrize(lottoStatistics, LottoRank.SECOND) +
                lottoTicketsPrize(lottoStatistics, LottoRank.THIRD) +
                lottoTicketsPrize(lottoStatistics, LottoRank.FOURTH) +
                lottoTicketsPrize(lottoStatistics, LottoRank.FIFTH);
    }

    private long lottoTicketsPrize(LottoStatistics lottoStatistics, LottoRank lottoRank) {
        return (long) lottoRank.winningPrize() * lottoStatistics.lottoTicketsCount(lottoRank);
    }
}
