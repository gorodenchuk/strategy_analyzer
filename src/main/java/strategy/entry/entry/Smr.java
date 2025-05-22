package strategy.entry.entry;

import pojo.model.candles.Candle;
import strategy.elements.Rebalance;
import strategy.elements.Sweep;
import strategy.elements.Target;

import java.util.List;

public class Smr {

    private final List<Candle> fractalsLow;
    private final List<Candle> fractalsHigh;
    private final List<Candle> candles;
    private final Candle candleInvl;
    private final Candle rebalancedFractal;
    private final Candle targetVl;


    private Sweep sweep = new Sweep();                       // need to be replaced
    private Target target = new Target();                    // need to be replaced
    private Rebalance reBalance = new Rebalance();           // need to be replaced

    public Smr(List<Candle> fractalsLow, List<Candle> fractalsHigh, List<Candle> candles, Candle candleInvl, Candle rebalancedFractal, Candle targetVl) {
        this.fractalsLow = fractalsLow;
        this.fractalsHigh = fractalsHigh;
        this.candles = candles;
        this.candleInvl = candleInvl;
        this.rebalancedFractal = rebalancedFractal;
        this.targetVl = targetVl;
    }

    public boolean isSmrLong() {
        boolean sweepOnLTF = !sweep.sweepLow(candles, fractalsLow, rebalancedFractal,
            candleInvl).isEmpty();
        boolean isBosLevelValidated = target.isTargetHighLevelValidated(candles,
            fractalsHigh, candleInvl);
        boolean isFvgPresent = false;

        if (isBosLevelValidated) {
            isFvgPresent = !reBalance.getLongFvgList(candles, candleInvl, targetVl).isEmpty();
        }
        return sweepOnLTF && isBosLevelValidated && isFvgPresent;
    }

    public boolean isSmrShort() {
        boolean sweepOnLtf = !sweep.sweepHigh(candles, fractalsHigh, rebalancedFractal, candleInvl).isEmpty();
        boolean isBosLevelValidated = target.isTargetLowLevelValidated(candles, fractalsLow, candleInvl);
        boolean isFvgPresent = false;

        if (isBosLevelValidated) {
            isFvgPresent = !reBalance.getShortFvgList(candles, candleInvl, targetVl).isEmpty();
        }
        return sweepOnLtf && isBosLevelValidated && isFvgPresent;
    }

}
