package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Exposes multiple random generators for various part of the game
 */
public class RandomHelper {
    private static final int ROOM_SEED = 43;
    public static Random roomGenerator = new Random(ROOM_SEED);

    private static final int ENEMY_SEED = 42;
    public static Random enemyGenerator = new Random(ENEMY_SEED);

    private static final int CHOOSE_SEED = 43;
    private static final Random chooseGenerator = new Random(CHOOSE_SEED);


    /**
     * Choose k coordinates from a list of coordinates
     * @param k (int) the number of elements to pick
     * @param list (ArrayList<DiscreteCoordinates>) the list of coordinates
     * @return (ArrayList<DiscreteCoordinates>) a list with k randomly chosen coordinates
     */
    public static ArrayList<DiscreteCoordinates> chooseKInList(int k, ArrayList<DiscreteCoordinates> list) {
        if (k > list.size())
            throw new IllegalArgumentException();

        int remainingValues = k;
        ArrayList<DiscreteCoordinates> values = new ArrayList<>(list), res = new ArrayList<>();

        while (remainingValues > 0) {
            int ix = chooseGenerator.nextInt(values.size());
            res.add(values.get(ix));
            values.remove(ix);
            remainingValues -= 1;
        }

        return res;
    }
}