package strategy.elements;

import pojo.model.candles.Candle;

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
}
