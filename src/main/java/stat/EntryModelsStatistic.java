package stat;

import pojo.model.candles.Candle;
import strategy.entry.entry.EntryModels;
import strategy.entry.entry.Smr;
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

    public void smrHighFractalTarget(double risk, double rrMin, List<Candle> sweepLiquidityCandles, List<Candle> fractalsLow, List<Candle> candlesOnMinus1TF, List<Candle> fractalsHighMinus1TF, List<Candle> candlesOnMinus2TF, List<Candle> fractalsHighMinus2TF, List<Candle> fractalsLowMinus2TF) {

        for (Candle candleParent : sweepLiquidityCandles) {
            Candle candleInvlMinus1TF = candleHelper.getCandleByLow(candlesOnMinus1TF, candleParent);
            int index = candleHelper.getHighFractalIndexBeforeSweep(fractalsHighMinus1TF, candleInvlMinus1TF);

            if (index != -1) {
                /*
                 take profit is higher than the first fractal on -1TF after sweep. Could be changed in the future.
                */
                Candle takeProfitMinus1TF = fractalsHighMinus1TF.get(index);
                Candle takeProfitMinus2TF = candleHelper.getCandleByHigh(candlesOnMinus2TF, takeProfitMinus1TF);
                Candle candleInvlMinus2TF = candleHelper.getCandleByLow(candlesOnMinus2TF, candleInvlMinus1TF);

                Candle rebalancedFractalMinus2TF = candleHelper.getLowRebalancedFractalMinus2TF(candlesOnMinus2TF, fractalsLow, candleInvlMinus1TF);
                Candle smrVlCandle = candleHelper.getTargetHighLevelValidation(candlesOnMinus2TF, fractalsHighMinus2TF, candleInvlMinus2TF);

                double entry = Double.parseDouble(entryPoint.getBosHighLevel(fractalsHighMinus2TF, candleInvlMinus2TF).getMid().getH());
                double takeProfit = Double.parseDouble(takeProfitMinus2TF.getMid().getH());
                double stopLoss = Double.parseDouble(candleInvlMinus2TF.getMid().getL());
                String targetResult = target.getReachedHighFractalTarget(candlesOnMinus2TF, takeProfitMinus2TF, candleInvlMinus2TF);

                smr = new Smr(fractalsLowMinus2TF, fractalsHighMinus2TF, candlesOnMinus2TF, candleInvlMinus2TF, rebalancedFractalMinus2TF, smrVlCandle);
                riskRewards = new RiskRewards(entry, takeProfit, stopLoss, risk, rrMin);

                boolean isSnr = smr.isSmrLong();
                boolean isRrCorrespondMin = riskRewards.isRrCorrespondMinimumValue(targetResult);
                boolean isSnrEntry;

                if (isSnr && isRrCorrespondMin) {
                    isSnrEntry = smr.entryModelLongTest();
                    entryModel++;

                    if (isSnrEntry) {
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

    public void smrLowFractalTarget(double risk, double rrMin, List<Candle> sweepLiquidityCandles, List<Candle> fractalsHigh, List<Candle> candlesOnMinus1TF, List<Candle> fractalsLowMinus1TF, List<Candle> candlesOnMinus2TF, List<Candle> fractalsLowMinus2TF, List<Candle> fractalsHighMinus2TF) {
        for (Candle candleParent : sweepLiquidityCandles) {
            Candle candleInvlMinus1TF = candleHelper.getCandleByHigh(candlesOnMinus1TF,
                candleParent);
            int index = candleHelper.getLowFractalIndexBeforeSweep(fractalsLowMinus1TF,
                candleInvlMinus1TF);

            if (index != -1) {
                /*
                 take profit is higher than first fractal on -1TF after sweep. Could be changed in the future.
                */
                Candle takeProfitMinus1TF = fractalsLowMinus1TF.get(index);
                Candle takeProfitMinus2TF = candleHelper.getCandleByLow(candlesOnMinus2TF,
                    takeProfitMinus1TF);
                Candle candleInvlMinus2TF = candleHelper.getCandleByHigh(candlesOnMinus2TF,
                    candleInvlMinus1TF);

                Candle rebalancedFractalMinus2TF = candleHelper.getHighRebalancedFractalMinus2TF(
                    candlesOnMinus2TF, fractalsHigh, candleInvlMinus1TF);
                Candle smrVlCandle = candleHelper.getTargetLowLevelValidation(candlesOnMinus2TF,
                    fractalsLowMinus2TF, candleInvlMinus2TF);

                double entry = Double.parseDouble(entryPoint.getBosLowLevel(fractalsLowMinus2TF, candleInvlMinus2TF).getMid().getL());
                double takeProfit = Double.parseDouble(takeProfitMinus2TF.getMid().getL());
                double stopLoss = Double.parseDouble(candleInvlMinus2TF.getMid().getH());
                String targetResult = target.getReachedLowFractalTarget(candlesOnMinus2TF, takeProfitMinus2TF, candleInvlMinus2TF);

                smr = new Smr(fractalsLowMinus2TF, fractalsHighMinus2TF, candlesOnMinus2TF, candleInvlMinus2TF, rebalancedFractalMinus2TF, smrVlCandle);
                riskRewards = new RiskRewards(entry, takeProfit, stopLoss, risk, rrMin);

                boolean isSnr = smr.isSmrShort();
                boolean isRrCorrespondMin = riskRewards.isRrCorrespondMinimumValue(targetResult);
                boolean isSnrEntry;

                if (isSnr && isRrCorrespondMin) {
                    isSnrEntry = smr.entryModelShortTest();
                    entryModel++;

                    if (isSnrEntry) {
                        entryModelTest++;
                        rr += riskRewards.getRiskReward(targetResult);
                    }

                    if (targetResult.equals("TP"))
                        wins++;
                    else if (targetResult.equals("SL"))
                        losses++;
                    else
                        be++;
                }
            }
        }
    }

        public void smrHighFractalTarget(double risk, double rrMin, List<Candle> sweepLiquidityCandles, List<Candle> fractalsLow, List<Candle> fractalsHigh, List<Candle> candlesOnMinus1TF, List<Candle> fractalsHighMinus1TF, List<Candle> candlesOnMinus2TF, List<Candle> fractalsHighMinus2TF, List<Candle> fractalsLowMinus2TF) {

            for (Candle candleParent : sweepLiquidityCandles) {
                Candle candleInvlMinus1TF = candleHelper.getCandleByLow(candlesOnMinus1TF, candleParent);
                int index = candleHelper.getHighFractalIndexBeforeSweep(fractalsHigh, candleParent);

                if (index != -1) {
                /*
                 take profit is higher than the first fractal on -1TF after sweep. Could be changed in the future.
                */
                    Candle takeProfitContextTF = fractalsHigh.get(index);
                    Candle takeProfitMinus1TF = candleHelper.getCandleByHigh(candlesOnMinus1TF, takeProfitContextTF);
                    Candle takeProfitMinus2TF = candleHelper.getCandleByHigh(candlesOnMinus2TF, takeProfitMinus1TF);
                    Candle candleInvlMinus2TF = candleHelper.getCandleByLow(candlesOnMinus2TF, candleInvlMinus1TF);

                    Candle rebalancedFractalMinus2TF = candleHelper.getLowRebalancedFractalMinus2TF(candlesOnMinus2TF, fractalsLow, candleInvlMinus1TF);
                    Candle smrVlCandle = candleHelper.getTargetHighLevelValidation(candlesOnMinus2TF, fractalsHighMinus2TF, candleInvlMinus2TF);

                    double entry = Double.parseDouble(entryPoint.getBosHighLevel(fractalsHighMinus2TF, candleInvlMinus2TF).getMid().getH());
                    double takeProfit = Double.parseDouble(takeProfitMinus2TF.getMid().getH());
                    double stopLoss = Double.parseDouble(candleInvlMinus2TF.getMid().getL());
                    String targetResult = target.getReachedHighFractalTarget(candlesOnMinus2TF, takeProfitMinus2TF, candleInvlMinus2TF);

                    smr = new Smr(fractalsLowMinus2TF, fractalsHighMinus2TF, candlesOnMinus2TF, candleInvlMinus2TF, rebalancedFractalMinus2TF, smrVlCandle);
                    riskRewards = new RiskRewards(entry, takeProfit, stopLoss, risk, rrMin);

                    boolean isSnr = smr.isSmrLong();
                    boolean isRrCorrespondMin = riskRewards.isRrCorrespondMinimumValue(targetResult);
                    boolean isSnrEntry;

                    if (isSnr && isRrCorrespondMin) {
                        isSnrEntry = smr.entryModelLongTest();
                        entryModel++;

                        if (isSnrEntry) {
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

        public void smrLowFractalTarget(double risk, double rrMin, List<Candle> sweepLiquidityCandles, List<Candle> fractalsHigh, List<Candle> fractalsLow, List<Candle> candlesOnMinus1TF, List<Candle> fractalsLowMinus1TF, List<Candle> candlesOnMinus2TF, List<Candle> fractalsLowMinus2TF, List<Candle> fractalsHighMinus2TF) {
            for (Candle candleParent : sweepLiquidityCandles) {
                Candle candleInvlMinus1TF = candleHelper.getCandleByHigh(candlesOnMinus1TF,
                    candleParent);
                int index = candleHelper.getLowFractalIndexBeforeSweep(fractalsLow, candleParent);

                if (index != -1) {
                /*
                 take profit is higher than first fractal on -1TF after sweep. Could be changed in the future.
                */
                    Candle takeProfitContextTF = fractalsLow.get(index);
                    Candle takeProfitMinus1TF = candleHelper.getCandleByLow(candlesOnMinus1TF, takeProfitContextTF);
                    Candle takeProfitMinus2TF = candleHelper.getCandleByLow(candlesOnMinus2TF,
                        takeProfitMinus1TF);
                    Candle candleInvlMinus2TF = candleHelper.getCandleByHigh(candlesOnMinus2TF,
                        candleInvlMinus1TF);

                    Candle rebalancedFractalMinus2TF = candleHelper.getHighRebalancedFractalMinus2TF(
                        candlesOnMinus2TF, fractalsHigh, candleInvlMinus1TF);
                    Candle smrVlCandle = candleHelper.getTargetLowLevelValidation(candlesOnMinus2TF,
                        fractalsLowMinus2TF, candleInvlMinus2TF);

                    double entry = Double.parseDouble(entryPoint.getBosLowLevel(fractalsLowMinus2TF, candleInvlMinus2TF).getMid().getL());
                    double takeProfit = Double.parseDouble(takeProfitMinus2TF.getMid().getL());
                    double stopLoss = Double.parseDouble(candleInvlMinus2TF.getMid().getH());
                    String targetResult = target.getReachedLowFractalTarget(candlesOnMinus2TF, takeProfitMinus2TF, candleInvlMinus2TF);

                    smr = new Smr(fractalsLowMinus2TF, fractalsHighMinus2TF, candlesOnMinus2TF, candleInvlMinus2TF, rebalancedFractalMinus2TF, smrVlCandle);
                    riskRewards = new RiskRewards(entry, takeProfit, stopLoss, risk, rrMin);

                    boolean isSnr = smr.isSmrShort();
                    boolean isRrCorrespondMin = riskRewards.isRrCorrespondMinimumValue(targetResult);
                    boolean isSnrEntry;

                    if (isSnr && isRrCorrespondMin) {
                        isSnrEntry = smr.entryModelShortTest();
                        entryModel++;

                        if (isSnrEntry) {
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


}
