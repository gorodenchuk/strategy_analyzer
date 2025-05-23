package test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.model.candles.Candle;
import pojo.model.candles.InputCandle;
import stat.EntryModelsStatistic;

import java.io.IOException;
import java.util.List;

public class EntryModelsTest extends BaseTest {

    private final static double RISK = 1.0;

    @BeforeMethod
    private void beforeMethod() {
        entryModelsStatistic = new EntryModelsStatistic();
    }


    @Test(enabled = false, description = "Price reversal after Sweep with with entry model on -2 TF with SL below the Context")
    public void entryAfterSweepWithEntryModelOnMinus2TF() throws IOException {
        String data = fileRoutine.readResourceAsString("response/eur-usd/4h-1h-15m/", "EUR_USD_H4_320_candles.json");
        InputCandle inputCandle = baseStrategy.getMappedObject(data, InputCandle.class);
        List<Candle> candles = inputCandle.getCandles();

        List<Candle> fractalsHigh = sweep.getFractalsHigh(candles);
        List<Candle> fractalsLow = sweep.getFractalsLow(candles);

        List<Candle> listOfSweepsHigh = sweep.sweepHigh(candles, fractalsHigh).stream().distinct().toList();
        List<Candle> listOfSweepsLow = sweep.sweepLow(candles, fractalsLow).stream().distinct().toList();

        String dataMinus1TF = fileRoutine.readResourceAsString("response/eur-usd/4h-1h-15m/", "EUR_USD_H1_1250_candles.json");
        InputCandle inputCandleMinus1TF = baseStrategy.getMappedObject(dataMinus1TF, InputCandle.class);
        List<Candle> candlesMinus1TF = inputCandleMinus1TF.getCandles();

        List<Candle> fractalsLowMinus1TF = sweep.getFractalsLow(candlesMinus1TF);
        List<Candle> fractalsHighMinus1TF = sweep.getFractalsHigh(candlesMinus1TF);

        String dataMinus2TF = fileRoutine.readResourceAsString("response/eur-usd/4h-1h-15m/", "EUR_USD_M15_5000_candles.json");
        InputCandle inputCandleMinus2TF = baseStrategy.getMappedObject(dataMinus2TF, InputCandle.class);
        List<Candle> candlesMinus2TF = inputCandleMinus2TF.getCandles();

        List<Candle> fractalsLowMinus2TF = sweep.getFractalsLow(candlesMinus2TF);
        List<Candle> fractalsHighMinus2TF = sweep.getFractalsHigh(candlesMinus2TF);

        entryModelsStatistic.smrHighFractalTarget(RISK, listOfSweepsLow, fractalsLow, candlesMinus1TF, fractalsHighMinus1TF, candlesMinus2TF, fractalsHighMinus2TF, fractalsLowMinus2TF);
        entryModelsStatistic.smrLowFractalTarget(RISK,listOfSweepsHigh, fractalsHigh, candlesMinus1TF, fractalsLowMinus1TF, candlesMinus2TF, fractalsLowMinus2TF, fractalsHighMinus2TF);
    }

    @Test(enabled = true, description = "Price reversal after Sweep with with entry model on -2 TF with SL below the Context TP on same with Context TF")
    public void entryWithEntryModelAndTarketOnContextTf() throws IOException {
        String data = fileRoutine.readResourceAsString("response/eur-usd/4h-1h-15m/", "EUR_USD_H4_320_candles.json");
        InputCandle inputCandle = baseStrategy.getMappedObject(data, InputCandle.class);
        List<Candle> candles = inputCandle.getCandles();

        List<Candle> fractalsHigh = sweep.getFractalsHigh(candles);
        List<Candle> fractalsLow = sweep.getFractalsLow(candles);

        List<Candle> listOfSweepsHigh = sweep.sweepHigh(candles, fractalsHigh).stream().distinct().toList();
        List<Candle> listOfSweepsLow = sweep.sweepLow(candles, fractalsLow).stream().distinct().toList();

        String dataMinus1TF = fileRoutine.readResourceAsString("response/eur-usd/4h-1h-15m/", "EUR_USD_H1_1250_candles.json");
        InputCandle inputCandleMinus1TF = baseStrategy.getMappedObject(dataMinus1TF, InputCandle.class);
        List<Candle> candlesMinus1TF = inputCandleMinus1TF.getCandles();

        List<Candle> fractalsLowMinus1TF = sweep.getFractalsLow(candlesMinus1TF);
        List<Candle> fractalsHighMinus1TF = sweep.getFractalsHigh(candlesMinus1TF);

        String dataMinus2TF = fileRoutine.readResourceAsString("response/eur-usd/4h-1h-15m/", "EUR_USD_M15_5000_candles.json");
        InputCandle inputCandleMinus2TF = baseStrategy.getMappedObject(dataMinus2TF, InputCandle.class);
        List<Candle> candlesMinus2TF = inputCandleMinus2TF.getCandles();

        List<Candle> fractalsLowMinus2TF = sweep.getFractalsLow(candlesMinus2TF);
        List<Candle> fractalsHighMinus2TF = sweep.getFractalsHigh(candlesMinus2TF);

        entryModelsStatistic.smrHighFractalTarget(RISK, listOfSweepsLow, fractalsLow, fractalsHigh, candlesMinus1TF, fractalsHighMinus1TF, candlesMinus2TF, fractalsHighMinus2TF, fractalsLowMinus2TF);
        entryModelsStatistic.smrLowFractalTarget(RISK,listOfSweepsHigh, fractalsHigh, fractalsLow, candlesMinus1TF, fractalsLowMinus1TF, candlesMinus2TF, fractalsLowMinus2TF, fractalsHighMinus2TF);
    }

    @AfterMethod
    private void afterMethod() {
        entryModelsStatistic.getResults();
    }
}
