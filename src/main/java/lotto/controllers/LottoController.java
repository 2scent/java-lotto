package lotto.controllers;

import lotto.domain.*;
import lotto.factories.LottoDiscriminatorFactory;
import lotto.factories.LottoTicketFactory;
import lotto.utils.StringUtil;
import lotto.views.InputView;
import lotto.views.ResultView;

import java.util.List;
import java.util.stream.Collectors;

public class LottoController {
    public void run() {
        final int payment = InputView.payment();
        final LottoCoupon lottoCoupon = new LottoStore().lottoCoupon(payment);

        final int manualLottoTicketsCount = InputView.manualLottoTicketsCount();

        if (lottoCoupon.exchangeable(manualLottoTicketsCount)) {
            final List<String> manualLottoTicketsInput = InputView.manualLottoTickets(manualLottoTicketsCount);

            final LottoBuyer lottoBuyer = lottoCoupon.lottoBuyer(
                    manualLottoTicketsInput.stream()
                            .map(StringUtil::splitCommas)
                            .map(LottoTicketFactory::from)
                            .collect(Collectors.toList())
            );

            ResultView.print(lottoBuyer);

            final String winnerNumbersInput = InputView.winnerNumbers();
            final String bonusNumberInput = InputView.bonusNumber();

            final LottoDiscriminator lottoDiscriminator = LottoDiscriminatorFactory.from(
                    StringUtil.splitCommas(winnerNumbersInput), bonusNumberInput);
            final LottoStatistics lottoStatistics = new LottoStatistics(lottoDiscriminator, lottoBuyer.allLottoTickets());

            ResultView.print(lottoStatistics, payment);
        }
    }
}
