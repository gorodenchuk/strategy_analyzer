package helper;

import pojo.model.candles.Candle;
import utils.DateTimeUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class BaseHelper {

    protected DateTimeUtils dateTimeUtils = new DateTimeUtils();

    public <T> int getIndex(List<T> list, T target) {
        Candle targetCandle = (Candle) target;
        Instant targetInstant = Instant.parse(targetCandle.getTime());
        long minDiff = Long.MAX_VALUE;
        int closestIndex = -1;

        for (int i = 0; i < list.size(); i++) {
            Candle fractal = (Candle) list.get(i);
            Instant fractalInstant = Instant.parse(fractal.getTime());

            if (list.get(i).equals(target)) {
                long diff = Math.abs(Duration.between(fractalInstant, targetInstant).toMillis());

                if (diff < minDiff) {
                    minDiff = diff;
                    closestIndex = i;
                }
            }
        }

        return closestIndex;
    }
}
