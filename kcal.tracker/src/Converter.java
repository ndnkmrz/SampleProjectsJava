public class Converter {
    public double stepsToKm(Integer stepsCount){
        double sm = stepsCount * 75.00;
        double km = sm/100000;
        return km;
    }

    public double stepsToKcal(Integer stepsCount){
        double cal = stepsCount * 50;
        double kcal = cal/1000;
        return kcal;
    }
}
