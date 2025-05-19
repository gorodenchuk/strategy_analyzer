package helper;

import groovyjarjarantlr4.v4.misc.OrderedHashMap;
import pojo.model.candles.Candle;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
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
            double reBalanceCandleLow = Double.parseDouble(reBalanceCandle.getMid().getL());

            if (fractal.isBefore(target) && reBalanceCandleHigh > fractalLow && reBalanceCandleLow != fractalLow) {
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
            double reBalanceCandleHigh = Double.parseDouble(reBalanceCandle.getMid().getH());

            if (fractal.isBefore(target) && reBalanceCandleLow < fractalHigh && reBalanceCandleHigh != fractalHigh) {
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
        Instant targetInstant = Instant.parse(candleParent.getTime());
        long minDiff = Long.MAX_VALUE;
        Candle closestCandle = null;

        for (int i = 0; i < candlesOnMinusTF.size(); i++) {
            Candle fractal = candlesOnMinusTF.get(i);
            Instant fractalInstant = Instant.parse(fractal.getTime());

            if (candlesOnMinusTF.get(i).getMid().getH().equals(candleParent.getMid().getH())) {
                long diff = Math.abs(Duration.between(fractalInstant, targetInstant).toMillis());

                if (diff < minDiff) {
                    minDiff = diff;
                    closestCandle = candlesOnMinusTF.get(i);
                }
            }
        }
        return closestCandle;
    }

    public Candle getCandleByLow(List<Candle> candlesOnMinusTF, Candle candleParent) {
        Instant targetInstant = Instant.parse(candleParent.getTime());
        long minDiff = Long.MAX_VALUE;
        Candle closestCandle = null;

        for (int i = 0; i < candlesOnMinusTF.size(); i++) {
            Candle fractal = candlesOnMinusTF.get(i);
            Instant fractalInstant = Instant.parse(fractal.getTime());

            if (candlesOnMinusTF.get(i).getMid().getL().equals(candleParent.getMid().getL())) {
                long diff = Math.abs(Duration.between(fractalInstant, targetInstant).toMillis());

                if (diff < minDiff) {
                    minDiff = diff;
                    closestCandle = candlesOnMinusTF.get(i);
                }
            }
        }

        return closestCandle;
    }

    public boolean isInClosedRangeLow(double fractal, double close, double open) {
        return ((close <= fractal && fractal <= open) || (open <= fractal && fractal <= close));
    }

    public boolean isInClosedRangeHigh(double fractal, double close, double open) {
        return (close >= fractal && fractal >= open || open >= fractal && fractal >= close);
    }

    public Candle getTargetLevelValidation(List<Candle> candles, List<Candle> fractalsHigh, Candle candleInvl) {
        Candle candleValidated = null;
        List<Candle> candlesSubList = new ArrayList<>(candles);
        Candle candleInvlMinus2TF = getCandleByLow(candles, candleInvl);
        int indexBosLevel = getHighFractalIndexBeforeSweep(fractalsHigh, candleInvlMinus2TF);

        candlesSubList.subList(0, indexBosLevel).clear();

        if (indexBosLevel != -1) {
            Candle bosLevelCandle = fractalsHigh.get(indexBosLevel);
            double bosLevelHigh = Double.parseDouble(bosLevelCandle.getMid().getH());
            double bosLevelLow = Double.parseDouble(candleInvlMinus2TF.getMid().getL());
            int index = candlesSubList.indexOf(bosLevelCandle);

            for (int i = index + 2; i < candlesSubList.size(); i++) {

                Candle candle = candlesSubList.get(i);
                String direction = getCandleDirection(candle);

                double candleLow = Double.parseDouble(candle.getMid().getL());
                double candleOpen = Double.parseDouble(candle.getMid().getO());
                double candleClose = Double.parseDouble(candle.getMid().getC());

                if (isInClosedRangeHigh(bosLevelHigh, candleClose, candleOpen)) {
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

    public Candle getLowRebalancedFractalMinus2TF(List<Candle> candlesOnMinus2TF, List<Candle> fractalsLow, Candle candleInvl) {
        int index = getLowFractalIndexBeforeSweep(fractalsLow, candleInvl);
        Candle fractalContextSweep = fractalsLow.get(index);
        return getCandleByLow(candlesOnMinus2TF, fractalContextSweep);
    }

    public Candle getHighRebalancedFractalMinus2TF(List<Candle> candlesOnMinus2TF, List<Candle> fractalsHigh, Candle candleInvl) {
        int index = getHighFractalIndexBeforeSweep(fractalsHigh, candleInvl);
        Candle fractalContextSweep = fractalsHigh.get(index);
        return getCandleByHigh(candlesOnMinus2TF, fractalContextSweep);
    }

}
