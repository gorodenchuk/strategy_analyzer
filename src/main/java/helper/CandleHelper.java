package helper;

import groovyjarjarantlr4.v4.misc.OrderedHashMap;
import pojo.model.candles.Candle;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class CandleHelper extends BaseHelper {

    public Map<Candle, String> getCandlesDirectionMap(List<Candle> candles) {
        Map<Candle, String> vector = new OrderedHashMap<>();

        for (Candle candle : candles) {
            double candleOpen = Double.parseDouble(candle.getMid().getO());
            double candleClose = Double.parseDouble(candle.getMid().getC());

            if (candleOpen > candleClose) {
                vector.put(candle, "short");
            } else {
                vector.put(candle, "long");
            }
        }
        return vector;
    }

    public String getCandleDirection(Candle candle) {
        double candleOpen = Double.parseDouble(candle.getMid().getO());
        double candleClose = Double.parseDouble(candle.getMid().getC());

        if (candleOpen > candleClose) {
            return "short";
        } else {
            return "long";
        }
    }

    public int getLowFractalIndexBeforeSweep(List<Candle> fractals, Candle reBalanceCandle) {
        Instant target = Instant.parse(reBalanceCandle.getTime());
        long minDiff = Long.MAX_VALUE;
        int closestIndex = -1;

        for (int i = 0; i < fractals.size(); i++) {
            Instant fractal = Instant.parse(fractals.get(i).getTime());
            double fractalLow = Double.parseDouble(fractals.get(i).getMid().getL());
            double reBalanceCandleHigh = Double.parseDouble(reBalanceCandle.getMid().getH());

            if (fractal.isBefore(target) && reBalanceCandleHigh > fractalLow) {
                long diff = Math.abs(Duration.between(fractal, target).toMillis());

                if (diff < minDiff) {
                    minDiff = diff;
                    closestIndex = i;
                }
            }
        }
        return closestIndex;
    }

    public int getHighFractalIndexBeforeSweep(List<Candle> fractals, Candle reBalanceCandle) {
        Instant target = Instant.parse(reBalanceCandle.getTime());
        long minDiff = Long.MAX_VALUE;
        int closestIndex = -1;

        for (int i = 0; i < fractals.size(); i++) {
            Instant fractal = Instant.parse(fractals.get(i).getTime());
            double fractalHigh = Double.parseDouble(fractals.get(i).getMid().getH());
            double reBalanceCandleLow = Double.parseDouble(reBalanceCandle.getMid().getL());

            if (fractal.isBefore(target) && reBalanceCandleLow < fractalHigh) {
                long diff = Math.abs(Duration.between(fractal, target).toMillis());

                if (diff < minDiff) {
                    minDiff = diff;
                    closestIndex = i;
                }
            }
        }
        return closestIndex;
    }

    public int getLowFractalIndexBeforeSweep(List<Candle> fractals, Candle reBalanceCandle, List<Candle> fvg) {
        Instant target = Instant.parse(reBalanceCandle.getTime());
        long minDiff = Long.MAX_VALUE;
        int closestIndex = -1;

        for (int i = fractals.size() - 1; i >= 0; i--) {
            Instant fractal = Instant.parse(fractals.get(i).getTime());
            double fractalLow = Double.parseDouble(fractals.get(i).getMid().getL());
            double reBalanceCandleHigh = Double.parseDouble(reBalanceCandle.getMid().getH());
            double fvgThirdCandleLow = Double.parseDouble(fvg.get(2).getMid().getL());

            if (fractal.isBefore(target) && reBalanceCandleHigh > fractalLow && fractalLow < fvgThirdCandleLow) {
                long diff = Math.abs(Duration.between(fractal, target).toMillis());

                if (diff < minDiff) {
                    minDiff = diff;
                    closestIndex = i;
                }
            }
        }
        return closestIndex;
    }

    public int getHighFractalIndexBeforeSweep(List<Candle> fractals, Candle reBalanceCandle, List<Candle> fvg) {
        Instant target = Instant.parse(reBalanceCandle.getTime());
        long minDiff = Long.MAX_VALUE;
        int closestIndex = -1;

        for (int i = fractals.size() - 1; i >= 0; i--) {
            Instant fractal = Instant.parse(fractals.get(i).getTime());
            double fractalHigh = Double.parseDouble(fractals.get(i).getMid().getH());
            double reBalanceCandleLow = Double.parseDouble(reBalanceCandle.getMid().getL());
            double fvgThirdCandleHigh = Double.parseDouble(fvg.get(2).getMid().getH());

            if (fractal.isBefore(target) && reBalanceCandleLow < fractalHigh && fractalHigh > fvgThirdCandleHigh) {
                long diff = Math.abs(Duration.between(fractal, target).toMillis());

                if (diff < minDiff) {
                    minDiff = diff;
                    closestIndex = i;
                }
            }
        }
        return closestIndex;
    }

    public Candle getCandleByHigh(List<Candle> candlesOnMinusTF, Candle candleParent) {
        return candlesOnMinusTF.stream()
                .filter(candle -> candle.getMid().getH().equals(candleParent.getMid().getH()))
                .findFirst()
                .get();
    }

    public Candle getCandleByLow(List<Candle> candlesOnMinusTF, Candle candleParent) {
        return candlesOnMinusTF.stream()
                .filter(candle -> candle.getMid().getL().equals(candleParent.getMid().getL()))
                .findFirst()
                .get();
    }

    public boolean isInClosedRangeLow(double fractal, double close, double open) {
        return (close <= fractal && fractal <= open || open <= fractal && fractal <= close);
    }

    public boolean isInClosedRangeHigh(double fractal, double close, double open) {
        return (close >= fractal && fractal >= open || open >= fractal && fractal >= close);
    }
}
