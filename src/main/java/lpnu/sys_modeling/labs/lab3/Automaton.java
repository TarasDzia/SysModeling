package lpnu.sys_modeling.labs.lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Automaton {
    List<float[]> statesAndSignals = new ArrayList<>();
    private int[][] b;
    private float possibility;
    private final int tacts;
    private int initState;

    private String transitionLog = "";

    public Automaton(int tacts) {
        this.tacts = tacts;
        this.initState = 0;
        this.b = new int[7][7];

        //                                s | z0 | z1   | z2   | z3  | z4  | z5  | z6
        statesAndSignals.add(new float[]{ 0 , 0  , 0.2F , 0.3F , 0   , 0   , 0   , 0.5F });    // z0
        statesAndSignals.add(new float[]{ 0 , 0  , 0.5F , 0    , 0   , 0.2F, 0   , 0.3F });    // z1
        statesAndSignals.add(new float[]{ 1 , 0  , 0    , 0.2F , 0   , 0   , 0.8F, 0    });    // z2
        statesAndSignals.add(new float[]{ 1 , 0  , 0    , 0.5F , 0   , 0   , 0.5F, 0    });    // z3
        statesAndSignals.add(new float[]{ 0 , 0  , 0    , 0.2F , 0.8F, 0   , 0   , 0    });    // z4
        statesAndSignals.add(new float[]{ 1 , 0  , 0    , 0    , 0   , 0.5F, 0   , 0.5F });    // z5
        statesAndSignals.add(new float[]{ 0 , 0  , 0    , 0.7F , 0   , 0   , 0   , 0.3F });    // z6

        this.possibility = getNewPossibility();
    }

    public void start() {
        for (int i = 0; i < tacts; i++) {
            System.out.println("Значення випадково числа =" + possibility);
            initState = findTransition(statesAndSignals, initState, possibility);
            possibility = getNewPossibility();

            System.out.println();
        }
    }

    public int findTransition(List<float[]> statesAndSignals , int previousState, float possibility) {
        float sumOfPossible = 0;
        for (int i = 1; i < statesAndSignals.get(previousState).length; i++) {
            sumOfPossible += statesAndSignals.get(previousState)[i];
            if (possibility <= sumOfPossible && (statesAndSignals.get(previousState)[i] != 0)){
                System.out.println("Перехід із стану z"+ previousState + " в стан z" + (i-1));
                transitionLog += "Перехід із стану z"+ previousState + " в стан z" + (i-1) + "\n";
                b[previousState][(i-1)] += 1;
                return (i-1);
            }
        }
        throw new RuntimeException("There are no acceptable transition!");
    }
    private float getNewPossibility() {
        return new Random().nextInt(10) / 10f;
    }
    
    public void displayB(){
        System.out.println("  | z0| z1| z2| z3| z4| z5| z6|");
        for (int i = 0; i < b.length; i++) {
            System.out.print("z"+i+ "| ");
            for (int j = 0; j < b[i].length; j++) {
                System.out.print(b[i][j] + " | ");
            }
            System.out.println();
        }
    }

    public float[] calculateLikelihoodInState(){
        float[] likelihoodBeingInState = new float[7];
        for (int i = 0; i < statesAndSignals.size(); i++) {
            likelihoodBeingInState[i] = (float) calculateSumBeingInState(i)/tacts;
        }
        return likelihoodBeingInState;
    }

    public int calculateSumBeingInState(int stateNumber){
        int sum = 0;
        for (int i = 0; i < b.length; i++) {
            sum += b[i][stateNumber];
        }
        return sum;
    }

    public float calculateLikelihoodOfInputOneSignal(float[] stateLikelihood){
        float sum = 0f;
        for (int i = 0; i < statesAndSignals.size(); i++) {
            if(statesAndSignals.get(i)[0] == 1){
                sum += stateLikelihood[i];
            }
        }
        return sum;
    }

    public String getTransitionLog() {
        return transitionLog;
    }
}
