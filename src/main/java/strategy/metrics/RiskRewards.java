package strategy.metrics;

public class RiskRewards {

    private final double entryPoint;
    private final double takeProfit;
    private final double stopLoss;
    private final double riskPercent;

    public RiskRewards(double entryPoint, double takeProfit, double stopLoss, double riskPercent) {
        this.entryPoint = entryPoint;
        this.takeProfit = takeProfit;
        this.stopLoss = stopLoss;
        this.riskPercent = riskPercent;
    }

    public double getRiskReward(String targetResult) {

        if (targetResult.equals("TP")) {
            double profit = takeProfit - entryPoint;
            double loss = entryPoint - stopLoss;
            return Math.abs((profit / loss) * riskPercent);

        } else if (targetResult.equals("SL")) {
            return riskPercent * -1;

        } else{
            return 0.0;
        }
    }
}
