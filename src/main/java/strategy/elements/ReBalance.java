package strategy.elements;

import pojo.model.candles.Candle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class ReBalance extends Instruments {


    public List<Candle> reBalancesOfLongFvg(List<Candle> candles, List<List<Candle>> fvgListLong) {
        List<Candle> reBalanceCandles = new ArrayList<>();
        List<Candle> candlesPerFvg;

        for (List<Candle> fvg : fvgListLong) {
            double firstCandleHigh = Double.parseDouble(fvg.get(0).getMid().getH());
            double thirdCandleLow = Double.parseDouble(fvg.get(2).getMid().getL());
            int index = candles.indexOf(fvg.get(2)) + 1;

            candlesPerFvg = new ArrayList<>();

            for (int i = index; i < candles.size(); i++) {
                Candle candle = candles.get(i);
                double low = Double.parseDouble(candle.getMid().getL());

                if (low > firstCandleHigh && low < thirdCandleLow) {
                    candlesPerFvg.add(candle);
                    thirdCandleLow = low;
                } else if (low < firstCandleHigh) {
                    break;
                }
            }

            try {
                Candle lowestCandle = candlesPerFvg.stream()
                        .min(Comparator.comparing(c -> Double.parseDouble(c.getMid().getL())))
                        .orElseThrow(() -> new NoSuchElementException("No candles found"));

                reBalanceCandles.add(lowestCandle);
            } catch (NoSuchElementException e) {
                System.out.println("No Low candles found for this FVG: " + fvg);
            }
        }
        return reBalanceCandles;
    }

    public List<Candle> reBalancesOfShortFvg(List<Candle> candles, List<List<Candle>> fvgListShort) {
        List<Candle> reBalanceCandles = new ArrayList<>();
        List<Candle> candlesPerFvg;

        for (List<Candle> fvg : fvgListShort) {
            double firstCandleLow = Double.parseDouble(fvg.get(0).getMid().getL());
            double thirdCandleHigh = Double.parseDouble(fvg.get(2).getMid().getH());
            int index = candles.indexOf(fvg.get(2)) + 1;

            candlesPerFvg = new ArrayList<>();

            for (int i = index; i < candles.size(); i++) {
                Candle candle = candles.get(i);
                double high = Double.parseDouble(candles.get(i).getMid().getH());

                if (high < firstCandleLow && high > thirdCandleHigh) {
                    candlesPerFvg.add(candle);
                    thirdCandleHigh = high;
                } else if (high > firstCandleLow) {
                    break;
                }
            }

            try {
                Candle highestCandle = candlesPerFvg.stream()
                        .min(Comparator.comparing(c -> Double.parseDouble(c.getMid().getH())))
                        .orElseThrow(() -> new NoSuchElementException("No candles found"));

                reBalanceCandles.add(highestCandle);
            } catch (NoSuchElementException e) {
                System.out.println("No High candles found for this FVG: " + fvg);
            }
        }
        return reBalanceCandles;
    }

}
