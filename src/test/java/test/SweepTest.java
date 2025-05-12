package test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.model.candles.Candle;
import pojo.model.candles.InputCandle;
import stat.SweepStatistic;

import java.io.IOException;
import java.util.List;

public class SweepTest extends BaseTest {

    @BeforeMethod
    private void beforeMethod() {
        sweepStatistic = new SweepStatistic();
    }

    @Test(enabled = false, description = "Price reversal after Sweep with Target at same TF with SL below the Swing")
    public void reversalAfterSweep() throws IOException {
        String data = fileRoutine.readResourceAsString("response/", "EUR_USD_D_500_candles.json");
        InputCandle inputCandle = baseStrategy.getMappedObject(data, InputCandle.class);
        List<Candle> candles = inputCandle.getCandles();

        List<Candle> fractalsHigh = sweep.getFractalsHigh(inputCandle.getCandles());
        List<Candle> fractalsLow = sweep.getFractalsLow(inputCandle.getCandles());

        List<Candle> listOfSweepsHigh =  sweep.sweepHigh(candles, fractalsHigh);
        List<Candle> listOfSweepsLow =  sweep.sweepLow(candles, fractalsLow);

        sweepStatistic.firstLowFractalTarget(candles, listOfSweepsHigh, fractalsLow);
        sweepStatistic.firstHighFractalTarget(candles, listOfSweepsLow, fractalsHigh);
    }

    @Test(enabled = false, description = "Price reversal after Sweep with Target at -1 TF with SL below the Swing")
    public void reversalWithTargetOnLowestTF() throws IOException {
        String data = fileRoutine.readResourceAsString("response/", "EUR_USD_D_500_candles.json");
        InputCandle inputCandle = baseStrategy.getMappedObject(data, InputCandle.class);
        List<Candle> candles = inputCandle.getCandles();

        List<Candle> fractalsHigh = sweep.getFractalsHigh(inputCandle.getCandles());
        List<Candle> fractalsLow = sweep.getFractalsLow(inputCandle.getCandles());

        List<Candle> listOfSweepsHigh =  sweep.sweepHigh(candles, fractalsHigh);
        List<Candle> listOfSweepsLow =  sweep.sweepLow(candles, fractalsLow);

        String dataMinusTF = fileRoutine.readResourceAsString("response/", "EUR_USD_H4_3050_candles.json");
        InputCandle inputCandleMinusTF = baseStrategy.getMappedObject(dataMinusTF, InputCandle.class);
        List<Candle> candlesMinusTF = inputCandleMinusTF.getCandles();

        List<Candle> fractalsLowMinusTF = sweep.getFractalsLow(inputCandleMinusTF.getCandles());
        List<Candle> fractalsHighMinusTF = sweep.getFractalsHigh(inputCandleMinusTF.getCandles());

        sweepStatistic.firstLowFractalTargetOnMinusTF(candlesMinusTF, listOfSweepsHigh, fractalsLowMinusTF);
        sweepStatistic.firstHighFractalTargetOnMinusTF(candlesMinusTF, listOfSweepsLow, fractalsHighMinusTF);
    }

    @Test(enabled = true, description = "Price reversal after Sweep with FTA as Target at -1 TF with SL below the Swing")
    public void reversalWithFtaAsTargetOnLowestTF() throws IOException {
        String data = fileRoutine.readResourceAsString("response/", "EUR_USD_M_30_candles.json");
        InputCandle inputCandle = baseStrategy.getMappedObject(data, InputCandle.class);
        List<Candle> candles = inputCandle.getCandles();

        List<Candle> fractalsHigh = sweep.getFractalsHigh(inputCandle.getCandles());
        List<Candle> fractalsLow = sweep.getFractalsLow(inputCandle.getCandles());

        List<Candle> listOfSweepsHigh =  sweep.sweepHigh(candles, fractalsHigh);
        List<Candle> listOfSweepsLow =  sweep.sweepLow(candles, fractalsLow);

        String dataMinusTF = fileRoutine.readResourceAsString("response/", "EUR_USD_W_128_candles.json");
        InputCandle inputCandleMinusTF = baseStrategy.getMappedObject(dataMinusTF, InputCandle.class);
        List<Candle> candlesMinusTF = inputCandleMinusTF.getCandles();

        List<Candle> fractalsLowMinusTF = sweep.getFractalsLow(inputCandleMinusTF.getCandles());
        List<Candle> fractalsHighMinusTF = sweep.getFractalsHigh(inputCandleMinusTF.getCandles());

        sweepStatistic.firstLowFractalTargetOnMinusTF(candlesMinusTF, listOfSweepsHigh, fractalsLowMinusTF);
        sweepStatistic.firstHighFractalTargetOnMinusTF(candlesMinusTF, listOfSweepsLow, fractalsHighMinusTF);
    }

    @AfterMethod
    private void afterMethod() {
        sweepStatistic.getResults();
    }
}
