package stat;

import pojo.model.candles.Candle;

import java.util.List;

public class SweepStatistic extends BaseStatistic {

    public void firstHighFractalTarget(List<Candle> candles, List<Candle> sweepLiquidityCandles, List<Candle> fractalsHigh) {
        for (Candle candleInvl : sweepLiquidityCandles) {
            int index = candleHelper.getHighFractalIndexBeforeSweep(fractalsHigh, candleInvl);

            if (index != -1) {
                Candle takeProfit = fractalsHigh.get(index);
                String targetResult = target.getReachedHighFractalTarget(candles, takeProfit, candleInvl);

                if (targetResult.equals("TP")) wins++;
                else if (targetResult.equals("SL")) losses++;
                else be++;
            }
        }
    }

    public void firstLowFractalTarget(List<Candle> candles, List<Candle> sweepLiquidityCandles, List<Candle> fractalsLow) {
        for (Candle candleInvl : sweepLiquidityCandles) {
            int index = candleHelper.getLowFractalIndexBeforeSweep(fractalsLow, candleInvl);

            if (index != -1) {
                Candle takeProfit = fractalsLow.get(index);
                String targetResult = target.getReachedLowFractalTarget(candles, takeProfit, candleInvl);

                if (targetResult.equals("TP")) wins++;
                else if (targetResult.equals("SL")) losses++;
                else be++;
            }
        }
    }

    public void firstLowFractalTargetOnMinusTF(List<Candle> candlesOnMinusTF, List<Candle> sweepLiquidityCandles, List<Candle> fractalsLowMinusTF) {

        for (Candle candleParent : sweepLiquidityCandles) {
            Candle candleInvl = candleHelper.getCandleByHigh(candlesOnMinusTF, candleParent);
            int index = candleHelper.getLowFractalIndexBeforeSweep(fractalsLowMinusTF, candleInvl);

            if (index != -1) {
                Candle takeProfit = fractalsLowMinusTF.get(index);
                String targetResult = target.getReachedLowFractalTarget(candlesOnMinusTF, takeProfit, candleInvl);

                if (targetResult.equals("TP")) wins++;
                else if (targetResult.equals("SL")) losses++;
                else be++;
            }
        }
    }

    public void firstHighFractalTargetOnMinusTF(List<Candle> candlesOnMinusTF, List<Candle> sweepLiquidityCandles, List<Candle> fractalsHighMinusTF) {

        for (Candle candleParent : sweepLiquidityCandles) {
            Candle candleInvl = candleHelper.getCandleByLow(candlesOnMinusTF, candleParent);
            int index = candleHelper.getHighFractalIndexBeforeSweep(fractalsHighMinusTF, candleInvl);

            if (index != -1) {
                Candle takeProfit = fractalsHighMinusTF.get(index);
                String targetResult = target.getReachedHighFractalTarget(candlesOnMinusTF, takeProfit, candleInvl);

                if (targetResult.equals("TP")) wins++;
                else if (targetResult.equals("SL")) losses++;
                else be++;
            }
        }
    }
}
