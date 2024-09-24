package ru.Timur;


import java.util.*;

public class Main {
    static Double sigmaN = 0D;
    static Double avg = 0D;
    public static void main(String[] args) {
        List<Double> input = new ArrayList<>();
        List<Double> diff = new ArrayList<>();
        List<Double> diffSqrt = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 50; i++) {
            input.add(scanner.nextDouble());
        }
        avg = 0D;
        for(Double d : input){
            avg += d;
        }
        avg /= 50;
        avg = roundToN(avg, 3);
        System.out.println("\n\nAVG");
        System.out.println(avg);



//        System.out.println("\n\n\n");
        for (Double d : input){
            diff.add(roundToN(d-avg, 3));
            diffSqrt.add(roundToN(Math.pow(d-avg, 2), 3));
//            System.out.printf("%2.3f\n", d-avg);
        }
//        System.out.println("\n\n\n");

        Double sumDiff = roundToN(diff.stream().mapToDouble(Double::doubleValue).sum(), 3);
        Double sumDiffSqrt = roundToN(diffSqrt.stream().mapToDouble(Double::doubleValue).sum(),3);

        System.out.println("\n\nsumDiffs");
        System.out.println(sumDiff);
        System.out.println(sumDiffSqrt);

        sigmaN = roundToN(Math.sqrt(sumDiffSqrt/49),3);
        Double sigmaT = roundToN(Math.sqrt(sumDiffSqrt/(50*49)),3);

        System.out.println("\n\nSigmas");
        System.out.println(sigmaN);
        System.out.println(sigmaT);

        Double minT = roundToN(input.stream().min(Double::compareTo).orElse(0D), 3);
        Double maxT = roundToN(input.stream().max(Double::compareTo).orElse(0D), 3);

        System.out.println("\n\nMinMax");
        System.out.println(minT);
        System.out.println(maxT);

        Double intervalT = roundToN((maxT-minT)/7, 3);
        System.out.println(intervalT);

        List<Double> ps = new ArrayList<>();


        System.out.println("\n\nIntervals");
        Double tempMin = minT;
        for (int i = 0; i < 7; i++) {
            Double tForP = tempMin;
            System.out.print(tempMin + " ");
            tempMin += intervalT;
            tempMin = roundToN(tempMin,3);
            tForP += tempMin;
            tForP /= 2;
            System.out.println(tempMin);
            tempMin += 0.001D;
            tempMin = roundToN(tempMin,3);


        }

        for (Double d:input){
            ps.add(roundToN(p(d), 3));
        }

        System.out.println("\n\nPs");
        ps.forEach(System.out::println);


    }


    private static Double roundToN(Double value, int power){
        return (Double)(Math.round(value * Math.pow(10, power))/Math.pow(10, power));
    }

    private static Double p(Double t){
        return (1.0/(sigmaN * Math.sqrt(2*Math.PI)))  *   Math.exp(-(Math.pow(t-avg, 2))/(2*sigmaN*sigmaN));
    }
}
