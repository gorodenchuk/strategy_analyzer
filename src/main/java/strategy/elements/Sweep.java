package strategy.elements;

import pojo.model.candles.Candle;
import pojo.model.candles.InputCandle;
import utils.FileRoutine;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Sweep extends Instruments {

    public static void main(String[] args) throws IOException {
        String data = new FileRoutine().readResourceAsString("response/", "EUR_USD_M15.json");
        InputCandle inputCandle = new BaseStrategy().getMappedObject(data, InputCandle.class);
        List<Candle> candles = inputCandle.getCandles();

        List<Candle> sweepCandles = new Sweep().sweepLow(candles, null);
        sweepCandles.forEach(element -> System.out.println(element.getTime() + " " + element.getMid().getL()));
    }

    public List<Candle> sweepHigh(List<Candle> candles, List<Candle> fractalsHigh) {
        List<Candle> sweepCandles = new ArrayList<>();

        for (Candle fractal : fractalsHigh) {
            int index = candles.indexOf(fractal);

            for (int i = index + 2; i < candles.size(); i++) {
                double fractalHigh = Double.parseDouble(fractal.getMid().getH());
                Candle candle = candles.get(i);
                String direction = candleHelper.getCandleDirection(candle);

                double candleHigh = Double.parseDouble(candle.getMid().getH());
                double candleOpen = Double.parseDouble(candle.getMid().getO());
                double candleClose = Double.parseDouble(candle.getMid().getC());

                if (direction.equals("short") && fractalHigh < candleHigh && fractalHigh > candleOpen) {
                    sweepCandles.add(candle);
                    break;
                } else if (direction.equals("long") && fractalHigh < candleHigh && fractalHigh > candleClose) {
                    sweepCandles.add(candle);
                    break;
                } else if (candleHelper.isInClosedRangeHigh(fractalHigh, candleClose, candleOpen)) {
                    break;
                }
            }
        }
        return sweepCandles;
    }

    public List<Candle> sweepLow(List<Candle> candles, List<Candle> fractalsLow) {
        List<Candle> sweepCandles = new ArrayList<>();

        for (Candle fractal : fractalsLow) {
            int index = candles.indexOf(fractal);

            for (int i = index + 2; i < candles.size(); i++) {
                double fractalLow = Double.parseDouble(fractal.getMid().getL());
                Candle candle = candles.get(i);
                String direction = candleHelper.getCandleDirection(candle);

                double candleLow = Double.parseDouble(candle.getMid().getL());
                double candleOpen = Double.parseDouble(candle.getMid().getO());
                double candleClose = Double.parseDouble(candle.getMid().getC());


                if (direction.equals("short") && fractalLow > candleLow && fractalLow < candleClose) {
                    sweepCandles.add(candle);
                    break;
                } else if (direction.equals("long") && fractalLow > candleLow && fractalLow < candleOpen) {
                    sweepCandles.add(candle);
                    break;
                } else if (candleHelper.isInClosedRangeLow(fractalLow, candleClose, candleOpen)) {
                    break;
                }
            }
        }
        return sweepCandles;
    }

    public List<Candle> sweepLow(List<Candle> candlesOnMinus2TF, List<Candle> fractalsLowMinus2TF, Candle rebalancedFractalMinus2TF, Candle candleInvl) {
        List<Candle> sweepCandles = new ArrayList<>();
        List<Candle> candlesSubList = new ArrayList<>(candlesOnMinus2TF);

        int indexOfRebalancedFractal = candlesOnMinus2TF.indexOf(rebalancedFractalMinus2TF);

        candlesSubList.subList(0, indexOfRebalancedFractal).clear();
        Candle candleInvlMinus2TF = candleHelper.getCandleByLow(candlesSubList, candleInvl);
        candlesSubList.subList(candlesSubList.indexOf(candleInvlMinus2TF) + 1, candlesSubList.size()).clear();

        double rebalancedCandleLow = Double.parseDouble(rebalancedFractalMinus2TF.getMid().getL());
        double candleInvlMinus2TfLow = Double.parseDouble(candleInvlMinus2TF.getMid().getL());

        fractalsLowMinus2TF = fractalsLowMinus2TF.stream()
                .filter(fractal -> Double.parseDouble(fractal.getMid().getL()) <= rebalancedCandleLow)
                .filter(fractal -> Double.parseDouble(fractal.getMid().getL()) > candleInvlMinus2TfLow)
                .filter(fractal -> {
                    Instant rebalancedFractalInstant = Instant.parse(candlesSubList.get(candlesSubList.indexOf(rebalancedFractalMinus2TF)).getTime());
                    Instant candleInvlMinus2TFInstant = Instant.parse(candleInvlMinus2TF.getTime());
                    Instant targetInstant = Instant.parse(fractal.getTime());

                   return (targetInstant.isAfter(rebalancedFractalInstant) || targetInstant.equals(rebalancedFractalInstant)) && (targetInstant.isBefore(candleInvlMinus2TFInstant) || targetInstant.equals(candleInvlMinus2TFInstant));
                })
                .toList();

        for (Candle fractal : fractalsLowMinus2TF) {
            int index = candlesSubList.indexOf(fractal);

            for (int i = index + 2; i < candlesSubList.size(); i++) {
                double fractalLow = Double.parseDouble(fractal.getMid().getL());
                Candle candle = candlesSubList.get(i);
                String direction = candleHelper.getCandleDirection(candle);

                double candleLow = Double.parseDouble(candle.getMid().getL());
                double candleOpen = Double.parseDouble(candle.getMid().getO());
                double candleClose = Double.parseDouble(candle.getMid().getC());


                if (direction.equals("short") && fractalLow > candleLow && fractalLow < candleClose && fractalLow <= rebalancedCandleLow) {
                    sweepCandles.add(candle);
                    break;
                } else if (direction.equals("long") && fractalLow > candleLow && fractalLow < candleOpen && fractalLow <= rebalancedCandleLow) {
                    sweepCandles.add(candle);
                    break;
                } else if (candleHelper.isInClosedRangeLow(fractalLow, candleClose, candleOpen)) {
                    break;
                }
            }
        }
        return sweepCandles;
    }

    public List<Candle> sweepHigh(List<Candle> candlesOnMinus2TF, List<Candle> fractalsHighMinus2TF, Candle rebalancedFractalMinus2TF, Candle candleInvl) {
        List<Candle> sweepCandles = new ArrayList<>();
        List<Candle> candlesSubList = new ArrayList<>(candlesOnMinus2TF);

        int indexOfRebalancedFractal = candlesOnMinus2TF.indexOf(rebalancedFractalMinus2TF);

        candlesSubList.subList(0, indexOfRebalancedFractal).clear();
        Candle candleInvlMinus2TF = candleHelper.getCandleByHigh(candlesSubList, candleInvl);
        candlesSubList.subList(candlesSubList.indexOf(candleInvlMinus2TF) + 1, candlesSubList.size()).clear();

        double rebalancedCandleHigh = Double.parseDouble(rebalancedFractalMinus2TF.getMid().getH());
        double candleInvlMinus2TfHigh = Double.parseDouble(candleInvlMinus2TF.getMid().getH());

        fractalsHighMinus2TF = fractalsHighMinus2TF.stream()
                .filter(fractal -> Double.parseDouble(fractal.getMid().getH()) >= rebalancedCandleHigh)
                .filter(fractal -> Double.parseDouble(fractal.getMid().getH()) > candleInvlMinus2TfHigh)
                .filter(fractal -> {
                    Instant rebalancedFractalInstant = Instant.parse(candlesSubList.get(candlesSubList.indexOf(rebalancedFractalMinus2TF)).getTime());
                    Instant candleInvlMinus2TFInstant = Instant.parse(candleInvlMinus2TF.getTime());
                    Instant targetInstant = Instant.parse(fractal.getTime());

                    return (targetInstant.isAfter(rebalancedFractalInstant) || targetInstant.equals(rebalancedFractalInstant)) && (targetInstant.isBefore(candleInvlMinus2TFInstant) || targetInstant.equals(candleInvlMinus2TFInstant));
                })
                .toList();

        for (Candle fractal : fractalsHighMinus2TF) {
            int index = candlesSubList.indexOf(fractal);

            for (int i = index + 2; i < candlesSubList.size(); i++) {
                double fractalHigh = Double.parseDouble(fractal.getMid().getH());
                Candle candle = candlesSubList.get(i);
                String direction = candleHelper.getCandleDirection(candle);

                double candleHigh = Double.parseDouble(candle.getMid().getH());
                double candleOpen = Double.parseDouble(candle.getMid().getO());
                double candleClose = Double.parseDouble(candle.getMid().getC());


                if (direction.equals("short") && fractalHigh < candleHigh && fractalHigh > candleOpen && fractalHigh >= rebalancedCandleHigh) {
                    sweepCandles.add(candle);
                    break;
                } else if (direction.equals("long") && fractalHigh < candleHigh && fractalHigh > candleClose && fractalHigh >= rebalancedCandleHigh) {
                    sweepCandles.add(candle);
                    break;
                } else if (candleHelper.isInClosedRangeHigh(fractalHigh, candleClose, candleOpen)) {
                    break;
                }
            }
        }
        return sweepCandles;
    }
}
