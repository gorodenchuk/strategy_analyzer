package test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.model.candles.Candle;
import pojo.model.candles.InputCandle;
import stat.EntryModelsStatistic;
import stat.SweepStatistic;

import java.io.IOException;
import java.util.List;

public class EntryModelsTest extends BaseTest {

    @BeforeMethod
    private void beforeMethod() {
        entryModelsStatistic = new EntryModelsStatistic();
    }


    @Test(enabled = true, description = "Price reversal after Sweep with with entry model on -2 TF with SL below the Context")
    public void entryAfterSweepWithEntryModelOnMinus2TF() throws IOException {
        String data = fileRoutine.readResourceAsString("response/", "EUR_USD_D_210_candles.json");
        InputCandle inputCandle = baseStrategy.getMappedObject(data, InputCandle.class);
        List<Candle> candles = inputCandle.getCandles();

        List<Candle> fractalsHigh = sweep.getFractalsHigh(inputCandle.getCandles());
        List<Candle> fractalsLow = sweep.getFractalsLow(inputCandle.getCandles());

//        TODO For better stat need to be taken only unique sweeps
        List<Candle> listOfSweepsHigh =  sweep.sweepHigh(candles, fractalsHigh);
        List<Candle> listOfSweepsLow =  sweep.sweepLow(candles, fractalsLow);

        String dataMinus1TF = fileRoutine.readResourceAsString("response/", "EUR_USD_H4_1250_candles.json");
        InputCandle inputCandleMinus1TF = baseStrategy.getMappedObject(dataMinus1TF, InputCandle.class);
        List<Candle> candlesMinus1TF = inputCandleMinus1TF.getCandles();

        List<Candle> fractalsLowMinus1TF = sweep.getFractalsLow(inputCandleMinus1TF.getCandles());
        List<Candle> fractalsHighMinus1TF = sweep.getFractalsHigh(inputCandleMinus1TF.getCandles());


        String dataMinus2TF = fileRoutine.readResourceAsString("response/", "EUR_USD_H1_5000_candles.json");
        InputCandle inputCandleMinus2TF = baseStrategy.getMappedObject(dataMinus2TF, InputCandle.class);
        List<Candle> candlesMinus2TF = inputCandleMinus2TF.getCandles();

        List<Candle> fractalsLowMinus2TF = sweep.getFractalsLow(inputCandleMinus2TF.getCandles());
        List<Candle> fractalsHighMinus2TF = sweep.getFractalsHigh(inputCandleMinus2TF.getCandles());

        entryModelsStatistic.smrHighFractalTarget(listOfSweepsLow, fractalsLow, candlesMinus1TF, fractalsHighMinus1TF, candlesMinus2TF, fractalsHighMinus2TF, fractalsLowMinus2TF);
        entryModelsStatistic.smrLowFractalTarget(listOfSweepsHigh, fractalsHigh, candlesMinus1TF, fractalsLowMinus1TF, candlesMinus2TF, fractalsLowMinus2TF, fractalsHighMinus2TF);
    }

    @AfterMethod
    private void afterMethod() {
        entryModelsStatistic.getResults();
    }
}
