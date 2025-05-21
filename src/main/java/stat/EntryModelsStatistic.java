package stat;

import pojo.model.candles.Candle;

import java.util.List;

public class EntryModelsStatistic extends BaseStatistic {

    int entryModel = 0, entryModelTest = 0;

    @Override
    public void getResults() {
        System.out.printf("Total: %d | Wins: %d | Losses: %d | BE: %d |Win Rate: %.2f%% | Entry Model presents: %d | Entry Model test: %d\n", wins + losses, wins, losses, be, 100.0 * wins / (wins + losses), entryModel, entryModelTest);
    }

    public void smrHighFractalTarget(List<Candle> sweepLiquidityCandles, List<Candle> fractalsLow, List<Candle> candlesOnMinus1TF, List<Candle> fractalsHighMinus1TF, List<Candle> candlesOnMinus2TF, List<Candle> fractalsHighMinus2TF, List<Candle> fractalsLowMinus2TF) {

        for (Candle candleParent : sweepLiquidityCandles) {
            Candle candleInvl = candleHelper.getCandleByLow(candlesOnMinus1TF, candleParent);
            int index = candleHelper.getHighFractalIndexBeforeSweep(fractalsHighMinus1TF, candleInvl);

            if (index != -1) {
                /*
                 take profit is higher than first fractal on -1TF after sweep. Could be changed in the future.
                */
                Candle takeProfitMinus1TF = fractalsHighMinus1TF.get(index);
                Candle takeProfitMinus2TF = candleHelper.getCandleByHigh(candlesOnMinus2TF, takeProfitMinus1TF);
                boolean isSnr = entryModels.isSnrLong(fractalsLow, fractalsLowMinus2TF, fractalsHighMinus2TF, candlesOnMinus2TF, takeProfitMinus2TF, candleInvl);
                boolean isSnrEntry;

                String targetResult = target.getReachedHighFractalTarget(candlesOnMinus2TF, takeProfitMinus2TF, candleInvl);

                if (isSnr) {
                    isSnrEntry = entryModels.entryModelLongTest(candlesOnMinus2TF, fractalsHighMinus2TF, candleInvl);
                    entryModel++;
                    if (isSnrEntry) entryModelTest++;
                    if (targetResult.equals("TP")) {
                        System.out.println("TP" + " " + candleInvl.getTime() + " " + candleInvl.getMid().getL());
                        wins++;
                    }
                    else if (targetResult.equals("SL")) losses++;
                    else be++;
                }
            }
        }
    }

    public void smrLowFractalTarget(List<Candle> sweepLiquidityCandles, List<Candle> fractalsHigh, List<Candle> candlesOnMinus1TF, List<Candle> fractalsLowMinus1TF, List<Candle> candlesOnMinus2TF, List<Candle> fractalsLowMinus2TF, List<Candle> fractalsHighMinus2TF) {
        for (Candle candleParent : sweepLiquidityCandles) {
            Candle candleInvl = candleHelper.getCandleByHigh(candlesOnMinus1TF, candleParent);
            int index = candleHelper.getLowFractalIndexBeforeSweep(fractalsLowMinus1TF, candleInvl);

            if (index != -1) {
                /*
                 take profit is higher than first fractal on -1TF after sweep. Could be changed in the future.
                */
                Candle takeProfitMinus1TF = fractalsLowMinus1TF.get(index);
                Candle takeProfitMinus2TF = candleHelper.getCandleByLow(candlesOnMinus2TF, takeProfitMinus1TF);
                boolean isSnr = entryModels.isSnrShort(fractalsHigh, fractalsHighMinus2TF, fractalsLowMinus2TF, candlesOnMinus2TF, takeProfitMinus2TF, candleInvl);
                boolean isSnrEntry;

                String targetResult = target.getReachedLowFractalTarget(candlesOnMinus2TF, takeProfitMinus2TF, candleInvl);

                if (isSnr) {
                    isSnrEntry = entryModels.entryModelShortTest(candlesOnMinus2TF, fractalsLowMinus2TF, candleInvl);
                    entryModel++;
                    if (isSnrEntry) entryModelTest++;
                    if (targetResult.equals("TP")) {
                        System.out.println("TP" + " " + candleInvl.getTime() + " " + candleInvl.getMid().getL());
                        wins++;
                    }
                    else if (targetResult.equals("SL")) losses++;
                    else be++;
                }
            }
        }


    }
}
