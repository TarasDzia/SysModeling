package lpnu.sys_modeling.labs.lab3;

public class Main {
    public static void main(String[] args) {
        int tacts = 100;
        Automaton automaton1 = new Automaton(tacts, 0);
        automaton1.start();
        automaton1.displayB();
        automaton1.calculateLikelihoodInState(7);
    }
}
