package strategy.elements;

import pojo.model.candles.Candle;
import pojo.model.candles.InputCandle;
import utils.FileRoutine;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Instruments extends BaseStrategy {

    public static void main(String[] args) throws IOException {
        String data = new FileRoutine().readResourceAsString("response/", "EUR_USD_M15.json");
        InputCandle inputCandle = new BaseStrategy().getMappedObject(data, InputCandle.class);

//        List<List<Candle>> bearishFvgList = new Sweep().getBullishFvgList(inputCandle.getCandles());
//        bearishFvgList.forEach(fvg -> System.out.println(fvg.get(1).getTime()));
//
//        List<Candle> fractalsLow = new Sweep().getfractalsLow(inputCandle.getCandles());

//        fractalsLow.forEach(element -> System.out.println(element.getTime() + " " + element.getMid().getH()));
//        Map<Candle, String> candlesVector = new Sweep().getCandlesDirectionMap(inputCandle.getCandles());
//        candlesVector.entrySet().forEach(candle -> System.out.println(candle.getValue()));
    }

    public List<List<Candle>> getShortFvgList(List<Candle> candles) {
        List<List<Candle>> fvgList = new ArrayList<>();

        for (int i = 1; i < candles.size() - 1; i++) {
            double firstElement = Double.parseDouble(candles.get(i - 1).getMid().getL());
            double thirdElement = Double.parseDouble(candles.get(i + 1).getMid().getH());

            if (firstElement > thirdElement) {
                List<Candle> fvg = new ArrayList<>();
                fvg.add(candles.get(i - 1));
                fvg.add(candles.get(i));
                fvg.add(candles.get(i + 1));

                fvgList.add(fvg);
            }
        }
        return fvgList;
    }

    public List<List<Candle>> getLongFvgList(List<Candle> candles) {
        List<List<Candle>> fvgList = new ArrayList<>();

        for (int i = 1; i < candles.size() - 1; i++) {
            double firstElement = Double.parseDouble(candles.get(i - 1).getMid().getH());
            double thirdElement = Double.parseDouble(candles.get(i + 1).getMid().getL());

            if (firstElement < thirdElement) {
                List<Candle> fvg = new ArrayList<>();
                fvg.add(candles.get(i - 1));
                fvg.add(candles.get(i));
                fvg.add(candles.get(i + 1));

                fvgList.add(fvg);
            }
        }
        return fvgList;
    }

    public List<Candle> getFractalsLow(List<Candle> candles) {
        List<Candle> fractals = new ArrayList<>();

        for (int i = 1; i < candles.size() - 1; i++) {
            double firstElement = Double.parseDouble(candles.get(i - 1).getMid().getL());
            double secondElement = Double.parseDouble(candles.get(i).getMid().getL());
            double thirdElement = Double.parseDouble(candles.get(i + 1).getMid().getL());

            if (firstElement > secondElement && secondElement < thirdElement) {
                fractals.add(candles.get(i));
            }
        }
        return fractals;
    }

    public List<Candle> getFractalsHigh(List<Candle> candles) {
        List<Candle> fractals = new ArrayList<>();

        for (int i = 1; i < candles.size() - 1; i++) {
            double firstElement = Double.parseDouble(candles.get(i - 1).getMid().getH());
            double secondElement = Double.parseDouble(candles.get(i).getMid().getH());
            double thirdElement = Double.parseDouble(candles.get(i + 1).getMid().getH());

            if (firstElement < secondElement && secondElement > thirdElement) {
                fractals.add(candles.get(i));
            }
        }
        return fractals;
    }

    public List<Candle> getReBalancedLongFvgByCandle(List<List<Candle>> fvgList, Candle candle) {
        double low = Double.parseDouble(candle.getMid().getL());
        Instant candleInstant = Instant.parse(candle.getTime());
        List<Candle> fvgRebalanced = null;

        for (int i = 0; i < fvgList.size(); i++) {
            List<Candle> fvg = fvgList.get(i);
            Instant fvgLastCandleInstant = Instant.parse(fvg.get(2).getTime());
            double firstCandleHigh = Double.parseDouble(fvg.get(0).getMid().getH());
            double thirdCandleLow = Double.parseDouble(fvg.get(2).getMid().getL());

            if (fvgLastCandleInstant.isBefore(candleInstant) && low > firstCandleHigh && low < thirdCandleLow) {
                fvgRebalanced = fvg;
            }
//            else if (low < firstCandleHigh) {
//                break;
//            }
        }
        return fvgRebalanced;
    }

    public List<Candle> getLowFractalsInLongFvg(List<List<Candle>> fvgListLong, List<Candle> fractalsLow) {
        List<Candle> fractals = new ArrayList<>();

        for (Candle fractal : fractalsLow) {
            double low = Double.parseDouble(fractal.getMid().getL());
            Instant candleInstant = Instant.parse(fractal.getTime());

            for (int i = 0; i < fvgListLong.size(); i++) {
                List<Candle> fvg = fvgListLong.get(i);
                Instant fvgLastCandleInstant = Instant.parse(fvg.get(2).getTime());
                double firstCandleHigh = Double.parseDouble(fvg.get(0).getMid().getH());
                double thirdCandleLow = Double.parseDouble(fvg.get(2).getMid().getL());

                if (fvgLastCandleInstant.isBefore(candleInstant) && low > firstCandleHigh && low < thirdCandleLow) {
                    fractals.add(fractal);
//                } else if (low < firstCandleHigh) {
//                    break;
                }
            }
        }
        return fractals;
    }

    public List<Candle> getHighFractalsInShortFvg(List<List<Candle>> fvgListShort, List<Candle> fractalsHigh) {
        List<Candle> fractals = new ArrayList<>();

        for (Candle fractal : fractalsHigh) {
            double high = Double.parseDouble(fractal.getMid().getH());
            Instant candleInstant = Instant.parse(fractal.getTime());

            for (int i = 0; i < fvgListShort.size(); i++) {
                List<Candle> fvg = fvgListShort.get(i);
                Instant fvgLastCandleInstant = Instant.parse(fvg.get(2).getTime());
                double firstCandleLow = Double.parseDouble(fvg.get(0).getMid().getL());
                double thirdCandleHigh = Double.parseDouble(fvg.get(2).getMid().getH());

                if (fvgLastCandleInstant.isBefore(candleInstant) && high < firstCandleLow && high > thirdCandleHigh) {
                    fractals.add(fractal);
//                } else if (low < firstCandleHigh) {
//                    break;
                }
            }
        }
        return fractals;
    }
}
