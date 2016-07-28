package com.bartash.dice;

public class Main
{

    public static void main(String[] args)
    {
        int[] dice = {12, 10, 8, 6, 4};

        for (int size : dice)
        {
            double total = 0;
            double chance = (double) (size - 1) / (double) size;
            for (int j = 0; j < 100; j++)
            {
                double delta = Math.pow(chance, j);
                total += delta;
                if (delta < 0.01)
                {
                    break;
                }
            }
            // confirm result at https://math.stackexchange.com/questions/698177/expected-value-of-rolling-dice-until-getting-a-3
            System.out.println("total for d" + size + " = " + total);
        }
    }
}
