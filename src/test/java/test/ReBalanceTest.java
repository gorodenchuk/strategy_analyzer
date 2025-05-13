package test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.model.candles.Candle;
import pojo.model.candles.InputCandle;
import stat.ReBalanceStatistic;

import java.io.IOException;
import java.util.List;

public class ReBalanceTest extends BaseTest {

    @BeforeMethod
    private void beforeMethod() {
        reBalanceStatistic = new ReBalanceStatistic();
    }

    @Test(enabled = true, description = "Price reversal after FVG Test by First Candle with Target at same TF with SL below the Last Candle of the FVG")
    public void reversalAfterSweep() throws IOException {
        String data = fileRoutine.readResourceAsString("response/", "EUR_USD_D_500_candles.json");
        InputCandle inputCandle = baseStrategy.getMappedObject(data, InputCandle.class);
        List<Candle> candles = inputCandle.getCandles();

        List<Candle> fractalsHigh = sweep.getFractalsHigh(inputCandle.getCandles());
        List<Candle> fractalsLow = sweep.getFractalsLow(inputCandle.getCandles());

        List<List<Candle>> fvgListLong = reBalance.getLongFvgList(inputCandle.getCandles());
        List<List<Candle>> fvgListShort = reBalance.getShortFvgList(inputCandle.getCandles());

        List<Candle> listOfReBalanceLongFvg =  reBalance.reBalancesOfLongFvg(candles, fvgListLong);
        List<Candle> listOfReBalanceShortFvg =  reBalance.reBalancesOfShortFvg(candles, fvgListShort);

        reBalanceStatistic.firstHighFractalTarget(candles, listOfReBalanceLongFvg, fvgListLong, fractalsHigh);
        reBalanceStatistic.firstLowFractalTarget(candles, listOfReBalanceShortFvg, fvgListShort, fractalsLow);
    }

    @AfterMethod
    private void afterMethod() {
        reBalanceStatistic.getResults();
    }
}
