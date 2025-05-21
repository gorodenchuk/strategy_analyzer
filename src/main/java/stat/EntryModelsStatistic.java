package stat;

import pojo.model.candles.Candle;
import strategy.metrics.RiskRewards;

import java.util.List;

public class EntryModelsStatistic extends BaseStatistic {

    int entryModel = 0, entryModelTest = 0;
    double rr = 0.0;

    @Override
    public void getResults() {
        System.out.printf("Total: %d | Wins: %d | Losses: %d | BE: %d |Win Rate: %.2f%%\n", wins + losses, wins, losses, be, 100.0 * wins / (wins + losses));
        System.out.printf("Entry Model presents: %d | Entry Model test: %d\n", entryModel, entryModelTest);
        System.out.printf("Total RR : %f | Average RR: %f\n", rr, rr / (wins + losses));
    }

    public void smrHighFractalTarget(double risk, List<Candle> sweepLiquidityCandles, List<Candle> fractalsLow, List<Candle> candlesOnMinus1TF, List<Candle> fractalsHighMinus1TF, List<Candle> candlesOnMinus2TF, List<Candle> fractalsHighMinus2TF, List<Candle> fractalsLowMinus2TF) {

        for (Candle candleParent : sweepLiquidityCandles) {
            Candle candleInvl = candleHelper.getCandleByLow(candlesOnMinus1TF, candleParent);
            int index = candleHelper.getHighFractalIndexBeforeSweep(fractalsHighMinus1TF, candleInvl);

            if (index != -1) {
                /*
                 take profit is higher than the first fractal on -1TF after sweep. Could be changed in the future.
                */
                Candle takeProfitMinus1TF = fractalsHighMinus1TF.get(index);
                Candle takeProfitMinus2TF = candleHelper.getCandleByHigh(candlesOnMinus2TF, takeProfitMinus1TF);
                Candle candleInvlMinus2TF = candleHelper.getCandleByLow(candlesOnMinus2TF, candleInvl);

                double entry = Double.parseDouble(entryPoint.getBosHighLevel(fractalsHighMinus2TF, candleInvlMinus2TF).getMid().getH());
                double takeProfit = Double.parseDouble(takeProfitMinus2TF.getMid().getH());
                double stopLoss = Double.parseDouble(candleInvl.getMid().getL());

                boolean isSnr = entryModels.isSnrLong(fractalsLow, fractalsLowMinus2TF, fractalsHighMinus2TF, candlesOnMinus2TF, takeProfitMinus2TF, candleInvl);
                boolean isSnrEntry;

                String targetResult = target.getReachedHighFractalTarget(candlesOnMinus2TF, takeProfitMinus2TF, candleInvl);


                if (isSnr) {
                    isSnrEntry = entryModels.entryModelLongTest(candlesOnMinus2TF, fractalsHighMinus2TF, candleInvl);
                    entryModel++;

                    if (isSnrEntry) {
                        riskRewards = new RiskRewards(entry, takeProfit, stopLoss, risk);
                        entryModelTest++;
                        rr += riskRewards.getRiskReward(targetResult);
                    }

                    if (targetResult.equals("TP")) wins++;
                    else if (targetResult.equals("SL")) losses++;
                    else be++;
                }
            }
        }
    }

    public void smrLowFractalTarget(double risk, List<Candle> sweepLiquidityCandles, List<Candle> fractalsHigh, List<Candle> candlesOnMinus1TF, List<Candle> fractalsLowMinus1TF, List<Candle> candlesOnMinus2TF, List<Candle> fractalsLowMinus2TF, List<Candle> fractalsHighMinus2TF) {
        for (Candle candleParent : sweepLiquidityCandles) {
            Candle candleInvl = candleHelper.getCandleByHigh(candlesOnMinus1TF, candleParent);
            int index = candleHelper.getLowFractalIndexBeforeSweep(fractalsLowMinus1TF, candleInvl);

            if (index != -1) {
                /*
                 take profit is higher than first fractal on -1TF after sweep. Could be changed in the future.
                */
                Candle takeProfitMinus1TF = fractalsLowMinus1TF.get(index);
                Candle takeProfitMinus2TF = candleHelper.getCandleByLow(candlesOnMinus2TF, takeProfitMinus1TF);
                Candle candleInvlMinus2TF = candleHelper.getCandleByHigh(candlesOnMinus2TF, candleInvl);

                double entry = Double.parseDouble(entryPoint.getBosLowLevel(fractalsLowMinus2TF, candleInvlMinus2TF).getMid().getL());
                double takeProfit = Double.parseDouble(takeProfitMinus2TF.getMid().getL());
                double stopLoss = Double.parseDouble(candleInvl.getMid().getH());

                boolean isSnr = entryModels.isSnrShort(fractalsHigh, fractalsHighMinus2TF, fractalsLowMinus2TF, candlesOnMinus2TF, takeProfitMinus2TF, candleInvl);
                boolean isSnrEntry;

                String targetResult = target.getReachedLowFractalTarget(candlesOnMinus2TF, takeProfitMinus2TF, candleInvl);

                if (isSnr) {
                    isSnrEntry = entryModels.entryModelShortTest(candlesOnMinus2TF, fractalsLowMinus2TF, candleInvl);
                    entryModel++;

                    if (isSnrEntry) {
                        riskRewards = new RiskRewards(entry, takeProfit, stopLoss, risk);
                        entryModelTest++;
                        System.out.println(riskRewards.getRiskReward(targetResult));
                        rr += riskRewards.getRiskReward(targetResult);
                    }

                    if (targetResult.equals("TP")) wins++;
                    else if (targetResult.equals("SL")) losses++;
                    else be++;
                }
            }
        }


    }
}
