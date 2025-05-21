package strategy.elements;

import pojo.model.candles.Candle;

import java.util.ArrayList;
import java.util.List;

public class Target extends Instruments {

    public String getReachedHighFractalTarget(List<Candle> candles, Candle fractalTP, Candle fractalSL) {
        String reachedTarget = null;

        int index = candles.indexOf(fractalTP);

        for (int i = index + 2; i < candles.size(); i++) {
            double takeProfit = Double.parseDouble(fractalTP.getMid().getH());
            double stopLoss = Double.parseDouble(fractalSL.getMid().getL());

            Candle candle = candles.get(i);
            double candleHigh = Double.parseDouble(candle.getMid().getH());
            double candleLow = Double.parseDouble(candle.getMid().getL());


            if (takeProfit < candleHigh) {
                reachedTarget = "TP";
                break;
            } else if (stopLoss > candleLow) {
                reachedTarget = "SL";
                break;
            } else {
                reachedTarget = "BE";
            }
        }
        return reachedTarget;
    }

    public String getReachedLowFractalTarget(List<Candle> candles, Candle fractalTP, Candle fractalSL) {
        String reachedTarget = null;

        int index = candles.indexOf(fractalTP);

        for (int i = index + 2; i < candles.size(); i++) {
            double takeProfit = Double.parseDouble(fractalTP.getMid().getL());
            double stopLoss = Double.parseDouble(fractalSL.getMid().getH());

            Candle candle = candles.get(i);
            double candleHigh = Double.parseDouble(candle.getMid().getH());
            double candleLow = Double.parseDouble(candle.getMid().getL());


            if (takeProfit > candleHigh) {
                reachedTarget = "TP";
                break;
            } else if (stopLoss < candleLow) {
                reachedTarget = "SL";
                break;
            } else {
                reachedTarget = "BE";
            }
        }
        return reachedTarget;
    }

    public boolean isTargetHighLevelValidated(List<Candle> candlesOnMinus2TF, List<Candle> fractalsHighMinus2TF, Candle rebalancedFractalMinus2TF, Candle candleInvl) {
        boolean isValidated = false;
        List<Candle> candlesSubList = new ArrayList<>(candlesOnMinus2TF);

        Candle candleInvlMinus2TF = candleHelper.getCandleByLow(candlesOnMinus2TF, candleInvl);
        int indexCandleInvlMinus2TF = candleHelper.getIndex(candlesSubList, candleInvlMinus2TF);
        int indexBosLevel = candleHelper.getHighFractalIndexBeforeSweep(fractalsHighMinus2TF, candleInvlMinus2TF);

        candlesSubList.subList(0, indexCandleInvlMinus2TF).clear();

        if (indexBosLevel != -1) {
            Candle bosLevelCandle = fractalsHighMinus2TF.get(indexBosLevel);
            double bosLevelHigh = Double.parseDouble(bosLevelCandle.getMid().getH());
            double bosLevelLow = Double.parseDouble(candleInvlMinus2TF.getMid().getL());
            int index = candlesSubList.indexOf(bosLevelCandle);

            for (int i = index + 2; i < candlesSubList.size(); i++) {

                Candle candle = candlesSubList.get(i);
                String direction = candleHelper.getCandleDirection(candle);

                double candleLow = Double.parseDouble(candle.getMid().getL());
                double candleOpen = Double.parseDouble(candle.getMid().getO());
                double candleClose = Double.parseDouble(candle.getMid().getC());

                if (candleHelper.isInClosedRangeHigh(bosLevelHigh, candleClose, candleOpen)) {
                    isValidated = true;
                    break;
                } else if (direction.equals("short") && bosLevelLow > candleLow && bosLevelLow < candleClose) {
                    break;
                } else if (direction.equals("long") && bosLevelLow > candleLow && bosLevelLow < candleOpen) {
                    break;
                }
            }
        }
        return isValidated;
    }

    public boolean isTargetLowLevelValidated(List<Candle> candlesOnMinus2TF, List<Candle> fractalsLowMinus2TF, Candle rebalancedFractalMinus2TF, Candle candleInvl) {
        boolean isValidated = false;
        List<Candle> candlesSubList = new ArrayList<>(candlesOnMinus2TF);

        Candle candleInvlMinus2TF = candleHelper.getCandleByHigh(candlesOnMinus2TF, candleInvl);
        int indexCandleInvlMinus2TF = candleHelper.getIndex(candlesSubList, candleInvlMinus2TF);
        int indexBosLevel = candleHelper.getLowFractalIndexBeforeSweep(fractalsLowMinus2TF, candleInvlMinus2TF);

        candlesSubList.subList(0, indexCandleInvlMinus2TF).clear();

        if (indexBosLevel != -1) {
            Candle bosLevelCandle = fractalsLowMinus2TF.get(indexBosLevel);
            double bosLevelHigh = Double.parseDouble(bosLevelCandle.getMid().getH());
            double bosLevelLow = Double.parseDouble(candleInvlMinus2TF.getMid().getL());
            int index = candlesSubList.indexOf(bosLevelCandle);

            for (int i = index + 2; i < candlesSubList.size(); i++) {

                Candle candle = candlesSubList.get(i);
                String direction = candleHelper.getCandleDirection(candle);

                double candleHigh = Double.parseDouble(candle.getMid().getH());
                double candleOpen = Double.parseDouble(candle.getMid().getO());
                double candleClose = Double.parseDouble(candle.getMid().getC());

                if (candleHelper.isInClosedRangeLow(bosLevelLow, candleClose, candleOpen)) {
                    isValidated = true;
                    break;
                } else if (direction.equals("short") && bosLevelHigh < candleHigh && bosLevelHigh > candleOpen) {
                    break;
                } else if (direction.equals("long") && bosLevelHigh < candleHigh && bosLevelHigh > candleClose) {
                    break;
                }
            }
        }
        return isValidated;
    }

    public Candle getTargetHighLevelValidatedCandle(List<Candle> candles, List<Candle> fractalsHigh, Candle candleInvl) {
        Candle candleValidated = null;
        List<Candle> candlesSubList = new ArrayList<>(candles);
        Candle candleInvlMinus2TF = candleHelper.getCandleByLow(candles, candleInvl);
        int indexBosLevel = candleHelper.getHighFractalIndexBeforeSweep(fractalsHigh, candleInvlMinus2TF);

        candlesSubList.subList(0, candlesSubList.indexOf(candleInvlMinus2TF)).clear();

        if (indexBosLevel != -1) {
            Candle bosLevelCandle = fractalsHigh.get(indexBosLevel);
            double bosLevelHigh = Double.parseDouble(bosLevelCandle.getMid().getH());
            double bosLevelLow = Double.parseDouble(candleInvlMinus2TF.getMid().getL());
            int index = candlesSubList.indexOf(bosLevelCandle);

            for (int i = index + 2; i < candlesSubList.size(); i++) {

                Candle candle = candlesSubList.get(i);
                String direction = candleHelper.getCandleDirection(candle);

                double candleLow = Double.parseDouble(candle.getMid().getL());
                double candleOpen = Double.parseDouble(candle.getMid().getO());
                double candleClose = Double.parseDouble(candle.getMid().getC());

                if (candleHelper.isInClosedRangeHigh(bosLevelHigh, candleClose, candleOpen)) {
                    candleValidated = candle;
                    break;
                } else if (direction.equals("short") && bosLevelLow > candleLow && bosLevelLow < candleClose) {
                    break;
                } else if (direction.equals("long") && bosLevelLow > candleLow && bosLevelLow < candleOpen) {
                    break;
                }
            }
        }
        return candleValidated;
    }

    public Candle getTargetLowLevelValidatedCandle(List<Candle> candles, List<Candle> fractalsLow, Candle candleInvl) {
        Candle candleValidated = null;
        List<Candle> candlesSubList = new ArrayList<>(candles);
        Candle candleInvlMinus2TF = candleHelper.getCandleByHigh(candles, candleInvl);
        int indexBosLevel = candleHelper.getLowFractalIndexBeforeSweep(fractalsLow, candleInvlMinus2TF);

        candlesSubList.subList(0, candlesSubList.indexOf(candleInvlMinus2TF)).clear();

        if (indexBosLevel != -1) {
            Candle bosLevelCandle = fractalsLow.get(indexBosLevel);
            double bosLevelLow = Double.parseDouble(bosLevelCandle.getMid().getL());
            double bosLevelHigh = Double.parseDouble(candleInvlMinus2TF.getMid().getH());
            int index = candlesSubList.indexOf(bosLevelCandle);

            for (int i = index + 2; i < candlesSubList.size(); i++) {

                Candle candle = candlesSubList.get(i);
                String direction = candleHelper.getCandleDirection(candle);

                double candleHigh = Double.parseDouble(candle.getMid().getH());
                double candleOpen = Double.parseDouble(candle.getMid().getO());
                double candleClose = Double.parseDouble(candle.getMid().getC());

                if (candleHelper.isInClosedRangeHigh(bosLevelLow, candleClose, candleOpen)) {
                    candleValidated = candle;
                    break;
                } else if (direction.equals("short") && bosLevelHigh < candleHigh && bosLevelHigh > candleOpen) {
                    break;
                } else if (direction.equals("long") && bosLevelHigh < candleHigh && bosLevelHigh > candleClose) {
                    break;
                }
            }
        }
        return candleValidated;
    }
}
