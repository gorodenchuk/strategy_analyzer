package strategy.elements;

import pojo.model.candles.Candle;

import java.util.List;

public class EntryPoint extends Instruments {

    public Candle getBosHighLevel(List<Candle> fractalsHigh, Candle candleInvl) {
        Candle bosLevel = null;
        int indexBosLevel = candleHelper.getHighFractalIndexBeforeSweep(fractalsHigh, candleInvl);

        if (indexBosLevel != -1) {
            bosLevel = fractalsHigh.get(indexBosLevel);
        }
        return bosLevel;
    }

    public Candle getBosLowLevel(List<Candle> fractalsLow, Candle candleInvl) {
        Candle bosLevel = null;
        int indexBosLevel = candleHelper.getLowFractalIndexBeforeSweep(fractalsLow, candleInvl);

        if (indexBosLevel != -1) {
            bosLevel = fractalsLow.get(indexBosLevel);
        }
        return bosLevel;
    }
}
