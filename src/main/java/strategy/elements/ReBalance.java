package strategy.elements;

import pojo.model.candles.Candle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class ReBalance extends Instruments {


    public List<Candle> reBalancesOfLongFvg(List<Candle> candles, List<List<Candle>> fvgListLong) {
        List<Candle> reBalanceCandles = new ArrayList<>();

        for (List<Candle> fvg : fvgListLong) {
            double firstCandleHigh = Double.parseDouble(fvg.get(0).getMid().getH());
            double thirdCandleLow = Double.parseDouble(fvg.get(2).getMid().getL());
            int index = candles.indexOf(fvg.get(2)) + 1;

            for (int i = index; i < candles.size(); i++) {
                Candle candle = candles.get(i);
                double low = Double.parseDouble(candle.getMid().getL());

                if (low > firstCandleHigh && low < thirdCandleLow) {
                    reBalanceCandles.add(candle);
                    break;
                } else if (low < firstCandleHigh) {
                    break;
                }
            }
        }
        return reBalanceCandles;
    }

    public List<Candle> reBalancesOfShortFvg(List<Candle> candles, List<List<Candle>> fvgListShort) {
        List<Candle> reBalanceCandles = new ArrayList<>();

        for (List<Candle> fvg : fvgListShort) {
            double firstCandleLow = Double.parseDouble(fvg.get(0).getMid().getL());
            double thirdCandleHigh = Double.parseDouble(fvg.get(2).getMid().getH());
            int index = candles.indexOf(fvg.get(2)) + 1;

            for (int i = index; i < candles.size(); i++) {
                Candle candle = candles.get(i);
                double high = Double.parseDouble(candles.get(i).getMid().getH());

                if (high < firstCandleLow && high > thirdCandleHigh) {
                    reBalanceCandles.add(candle);
                    break;
                } else if (high > firstCandleLow) {
                    break;
                }
            }
        }
        return reBalanceCandles;
    }

}
