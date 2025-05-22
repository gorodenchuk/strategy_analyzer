package strategy.entry.entry;

import java.util.List;
import pojo.model.candles.Candle;

public class Smr extends EntryModels {

  public Smr(List<Candle> fractalsLow, List<Candle> fractalsHigh, List<Candle> candles,
      Candle candleInvl, Candle rebalancedFractal, Candle smrVl) {
    super(fractalsLow, fractalsHigh, candles, candleInvl, rebalancedFractal, smrVl);
  }


  public boolean isSmrLong() {
    boolean sweepOnLTF = !sweep.sweepLow(candles, fractalsLow, rebalancedFractal, candleInvl).isEmpty();
    boolean isBosLevelValidated = target.isTargetHighLevelValidated(candles, fractalsHigh, candleInvl);
    boolean isFvgPresent = false;

    if (isBosLevelValidated) {
      isFvgPresent = !reBalance.getLongFvgList(candles, candleInvl, smrVl).isEmpty();
    }
    return sweepOnLTF && isBosLevelValidated && isFvgPresent;
  }

  public boolean isSmrShort() {
    boolean sweepOnLtf = !sweep.sweepHigh(candles, fractalsHigh, rebalancedFractal, candleInvl)
        .isEmpty();
    boolean isBosLevelValidated = target.isTargetLowLevelValidated(candles, fractalsLow,
        candleInvl);
    boolean isFvgPresent = false;

    if (isBosLevelValidated) {
      isFvgPresent = !reBalance.getShortFvgList(candles, candleInvl, smrVl).isEmpty();
    }
    return sweepOnLtf && isBosLevelValidated && isFvgPresent;
  }

}
