package stat;

import pojo.model.candles.Candle;

import java.util.List;

public class ReBalanceStatistic extends BaseStatistic {

    public void firstHighFractalTarget(List<Candle> candles, List<Candle> listOfReBalanceLongFvg, List<List<Candle>> fvgList, List<Candle> fractalsHigh) {
        for (Candle reBalancedCandle : listOfReBalanceLongFvg) {
            List<Candle> fvg = fvgHelper.getReBalancedLongFvgByCandle(fvgList, reBalancedCandle);
            Candle candleInvl = fvg.get(0);
            int index = candleHelper.getHighFractalIndexBeforeSweep(fractalsHigh, reBalancedCandle, fvg);

            if (index != -1) {
                Candle takeProfit = fractalsHigh.get(index);
                String targetResult = target.getReachedHighFractalTarget(candles, takeProfit, candleInvl);

                if (targetResult.equals("TP")) wins++;
                else if (targetResult.equals("SL")) losses++;
                else be++;
            }
        }
    }

    public void firstLowFractalTarget(List<Candle> candles, List<Candle> listOfReBalanceShortFvg, List<List<Candle>> fvgList, List<Candle> fractalsLow) {
        for (Candle reBalancedCandle : listOfReBalanceShortFvg) {
            List<Candle> fvg = fvgHelper.getReBalancedShortFvgByCandle(fvgList, reBalancedCandle);
            Candle candleInvl = fvg.get(0);
            int index = candleHelper.getLowFractalIndexBeforeSweep(fractalsLow, reBalancedCandle, fvg);

            if (index != -1) {
                Candle takeProfit = fractalsLow.get(index);
                String targetResult = target.getReachedLowFractalTarget(candles, takeProfit, candleInvl);

                if (targetResult.equals("TP")) wins++;
                else if (targetResult.equals("SL")) losses++;
                else be++;
            }
        }
    }

}
