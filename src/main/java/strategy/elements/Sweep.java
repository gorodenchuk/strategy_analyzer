package strategy.elements;

import pojo.model.candles.Candle;
import pojo.model.candles.InputCandle;
import utils.FileRoutine;

import java.io.IOException;
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
                }
                else if (candleHelper.isInClosedRangeHigh(fractalHigh, candleClose, candleOpen)) {
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
}
