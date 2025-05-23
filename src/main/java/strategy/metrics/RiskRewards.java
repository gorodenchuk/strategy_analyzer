package strategy.metrics;

public class RiskRewards {

    private double entryPoint;
    private double takeProfit;
    private double stopLoss;
    private double riskPercent;
    private double rrMin;

    public RiskRewards(double entryPoint, double takeProfit, double stopLoss, double riskPercent, double rrMin) {
        this.entryPoint = entryPoint;
        this.takeProfit = takeProfit;
        this.stopLoss = stopLoss;
        this.riskPercent = riskPercent;
        this.rrMin = rrMin;
    }

    public double getRiskReward(String targetResult) {

        if (targetResult.equals("TP")) {
            double profit = takeProfit - entryPoint;
            double loss = entryPoint - stopLoss;
            return Math.abs((profit / loss) * riskPercent);

        } else if (targetResult.equals("SL")) {
            return riskPercent * -1;

        } else {
            return 0.0;
        }
    }

    public boolean isRrCorrespondMinimumValue() {
       double profit = takeProfit - entryPoint;
       double loss = entryPoint - stopLoss;

       return Math.abs((profit / loss) * riskPercent) >= rrMin;
    }
}
