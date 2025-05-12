package stat;

import helper.CandleHelper;
import helper.FvgHelper;
import strategy.elements.Sweep;
import strategy.elements.Target;

public class BaseStatistic {

    protected CandleHelper candleHelper = new CandleHelper();
    protected FvgHelper fvgHelper = new FvgHelper();
    protected Sweep sweep = new Sweep();
    protected Target target = new Target();

    int wins = 0, losses = 0, be = 0;

    public void getResults() {
        System.out.printf("Total: %d | Wins: %d | Losses: %d | BE: %d |Win Rate: %.2f%%\n",
                wins + losses, wins, losses, be, 100.0 * wins / (wins + losses));
    }

}
