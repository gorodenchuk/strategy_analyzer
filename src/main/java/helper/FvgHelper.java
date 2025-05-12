package helper;

import pojo.model.candles.Candle;

import java.time.Instant;
import java.util.List;

public class FvgHelper extends BaseHelper {

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

    public List<Candle> getReBalancedShortFvgByCandle(List<List<Candle>> fvgList, Candle candle) {
        double high = Double.parseDouble(candle.getMid().getH());
        Instant candleInstant = Instant.parse(candle.getTime());
        List<Candle> fvgRebalanced = null;

        for (int i = 0; i < fvgList.size(); i++) {
            List<Candle> fvg = fvgList.get(i);
            Instant fvgLastCandleInstant = Instant.parse(fvg.get(2).getTime());
            double firstCandleLow = Double.parseDouble(fvg.get(0).getMid().getL());
            double thirdCandleHigh = Double.parseDouble(fvg.get(2).getMid().getH());

            if (fvgLastCandleInstant.isBefore(candleInstant) && high < firstCandleLow && high > thirdCandleHigh) {
                fvgRebalanced = fvg;
            }
//            else if (low < firstCandleHigh) {
//                break;
//            }
        }
        return fvgRebalanced;
    }

}
