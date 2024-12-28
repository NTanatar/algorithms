package com.nata.advent.race;

import com.nata.advent.Position;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Printer {

    public static void printPath(Integer[][] pathValues, int width, int height) {
        for (int y = 0; y < height; y++) {
            System.out.println();
            for (int x = 0; x < width; x++) {
                Integer pathValue = pathValues[x][y];
                if (pathValue != null)
                    System.out.print(String.format("%02d", pathValue));
                else {
                    System.out.print("  ");
                }
            }
        }
        System.out.println();
    }

    public static void printPath(List<Position> path) {
        System.out.println("--------------------------");
        path.forEach(p -> System.out.print(p + "  "));
        System.out.println();
    }

    public static void printCheats(Map<Integer, Set<Cheat>> cheatMap) {
        System.out.println("--------------------------");
        cheatMap.keySet().stream()
            .sorted()
            .forEach(win -> System.out.println("win: " + win + ", " + cheatMap.get(win).size() + " cheats"));
    }
}
