package com.xiwang;

import java.util.*;

public class Main {

    private static boolean isSolve = false;
    private static List<List<Integer>> sudokuList = new ArrayList<>();
    private static List<Set<Integer>> rowNumList = new ArrayList<>();
    private static List<Set<Integer>> colNumList = new ArrayList<>();
    private static List<Set<Integer>> mapNumList = new ArrayList<>();
    private static Map<String, List<Integer>> guessMap = new HashMap<>();
    private static final Set<Integer> ALL_NUM = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    public static void main(String[] args) {
        sudokuList = getSudoku();
        init();

        boolean needRefreshGuess = guess();
        while (needRefreshGuess) {
            needRefreshGuess = guess();
            if (needRefreshGuess) {
                printSudoku();
                continue;
            }
            needRefreshGuess = solve();
            printSudoku();
        }
        if (guessMap.isEmpty()) {
            System.out.println("solved easily...");
        }
        //        isSolve = guessMap.isEmpty();
        //        while (!isSolve) {
        //
        //        }
    }

    private static boolean solve() {
        return solveMapUnique() || solveRowUnique() || solveColUnique();
    }

    private static boolean solveRowUnique() {
        boolean needRefreshGuess = false;

        Map<Integer, Map<Integer, Integer>> numCountMap = buildRowNumCountMap();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Map<Integer, Integer> integerIntegerMap = numCountMap.get(y);
                if (integerIntegerMap.containsValue(1)) {
                    List<Integer> integers = guessMap.get(String.valueOf(x) + y);

                    for (Integer value : integers) {
                        if (integerIntegerMap.containsKey(value) && integerIntegerMap.get(value) == 1) {
                            sudokuList
                                    .get(x)
                                    .set(y, value);
                            rowNumList
                                    .get(y)
                                    .add(value);
                            colNumList
                                    .get(x)
                                    .add(value);
                            mapNumList
                                    .get(x / 3 + y / 3 * 3)
                                    .add(value);
                            needRefreshGuess = true;
                        }
                    }
                    ;
                }
            }
        }

        return needRefreshGuess;
    }

    private static boolean solveColUnique() {
        boolean needRefreshGuess = false;

        Map<Integer, Map<Integer, Integer>> numCountMap = buildColNumCountMap();
        for (int x = 0; x < 9; x++) {
            Map<Integer, Integer> integerIntegerMap = numCountMap.get(x);
            for (int y = 0; y < 9; y++) {
                if (integerIntegerMap.containsValue(1)) {
                    List<Integer> integers = guessMap.get(String.valueOf(x) + y);

                    for (Integer value : integers) {
                        if (integerIntegerMap.containsKey(value) && integerIntegerMap.get(value) == 1) {
                            sudokuList
                                    .get(x)
                                    .set(y, value);
                            rowNumList
                                    .get(y)
                                    .add(value);
                            colNumList
                                    .get(x)
                                    .add(value);
                            mapNumList
                                    .get(x / 3 + y / 3 * 3)
                                    .add(value);
                            needRefreshGuess = true;
                        }
                    }
                    ;
                }
            }
        }

        return needRefreshGuess;
    }

    private static Map<Integer, Map<Integer, Integer>> buildRowNumCountMap() {
        Map<Integer, Map<Integer, Integer>> numCountMap = new HashMap<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                numCountMap.computeIfAbsent(y, k -> new HashMap<>());
                Map<Integer, Integer> integerIntegerMap = numCountMap.get(y);
                List<Integer> integers = guessMap.get(String.valueOf(x) + y);
                if (integers != null) {
                    integers.forEach(integer -> {
                        integerIntegerMap.merge(integer, 1, Integer::sum);
                    });
                }
            }
        }
        return numCountMap;
    }

    private static Map<Integer, Map<Integer, Integer>> buildColNumCountMap() {
        Map<Integer, Map<Integer, Integer>> numCountMap = new HashMap<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                numCountMap.computeIfAbsent(x, k -> new HashMap<>());
                Map<Integer, Integer> integerIntegerMap = numCountMap.get(x);
                List<Integer> integers = guessMap.get(String.valueOf(x) + y);
                if (integers != null) {
                    integers.forEach(integer -> {
                        integerIntegerMap.merge(integer, 1, Integer::sum);
                    });
                }
            }
        }
        return numCountMap;
    }

    private static boolean solveMapUnique() {
        boolean needRefreshGuess = false;
        Map<Integer, Map<Integer, Integer>> numCountMap = buildMapNumCountMap();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                int mapIndex = x / 3 + y / 3 * 3;
                Map<Integer, Integer> integerIntegerMap = numCountMap.get(mapIndex);
                if (integerIntegerMap.containsValue(1)) {
                    List<Integer> integers = guessMap.get(String.valueOf(x) + y);
                    if (integers == null) {
                        continue;
                    }
                    for (Integer value : integers) {
                        if (integerIntegerMap.containsKey(value) && integerIntegerMap.get(value) == 1) {
                            sudokuList
                                    .get(x)
                                    .set(y, value);
                            rowNumList
                                    .get(y)
                                    .add(value);
                            colNumList
                                    .get(x)
                                    .add(value);
                            mapNumList
                                    .get(x / 3 + y / 3 * 3)
                                    .add(value);
                            needRefreshGuess = true;
                        }
                    }
                    ;
                }
            }
        }
        return needRefreshGuess;
    }

    private static Map<Integer, Map<Integer, Integer>> buildMapNumCountMap() {
        Map<Integer, Map<Integer, Integer>> numCountMap = new HashMap<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                int mapIndex = x / 3 + y / 3 * 3;
                numCountMap.computeIfAbsent(mapIndex, k -> new HashMap<>());
                //                if (numCountMap.get(mapIndex) == null) {
                //                    numCountMap.put(mapIndex, new HashMap<>());
                //                }
                Map<Integer, Integer> integerIntegerMap = numCountMap.get(mapIndex);
                List<Integer> integers = guessMap.get(String.valueOf(x) + y);
                if (integers != null) {
                    integers.forEach(integer -> {
                        integerIntegerMap.merge(integer, 1, Integer::sum);
                    });
                }
            }
        }
        return numCountMap;
    }

    private static void printSudoku() {
        System.out.println("—————————————");
        for (int i = 0; i < 9; i++) {
            List<Integer> rows = sudokuList.get(i);

            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                System.out.print(rows.get(j) == null ? "_ " : rows.get(j) + " ");
                if (j == 8) {
                    System.out.print("|");
                }
            }
            if (i != 8) {
                if ((i + 1) % 3 == 0) {
                    System.out.println("\n—————————————");
                } else {
                    System.out.println("\n------------------------");
                }
            }
        }
        System.out.println("\n—————————————");
    }

    private static boolean guess() {
        boolean needRefreshGuess = false;
        guessMap.clear();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Integer val = sudokuList
                        .get(x)
                        .get(y);
                if (val == null) {
                    Set<Integer> existNumSet = new HashSet<>();
                    existNumSet.addAll(rowNumList.get(y));
                    existNumSet.addAll(colNumList.get(x));
                    existNumSet.addAll(mapNumList.get(x / 3 + y / 3 * 3));
                    List<Integer> collect = ALL_NUM
                            .stream()
                            .filter(e -> !existNumSet.contains(e))
                            .toList();
                    int size = collect.size();
                    switch (size) {
                        case 0 -> throw new RuntimeException("Wrong...");
                        case 1 -> {
                            Integer value = collect.get(0);
                            sudokuList
                                    .get(x)
                                    .set(y, value);
                            rowNumList
                                    .get(y)
                                    .add(value);
                            colNumList
                                    .get(x)
                                    .add(value);
                            mapNumList
                                    .get(x / 3 + y / 3 * 3)
                                    .add(value);
                            needRefreshGuess = true;
                        }
                        default -> {
                            guessMap.put(String.valueOf(x) + y, collect);
                        }
                    }
                }
            }
        }

        System.out.println("guess finish....");
        return needRefreshGuess;
    }

    private static List<List<Integer>> getSudoku() {
        List<List<Integer>> sudoku = new ArrayList<>();
        sudoku.add(Arrays.asList(null, 8, 2, null, null, null, 7, 5, null));
        sudoku.add(Arrays.asList(null, null, null, null, 8, null, null, null, null));
        sudoku.add(Arrays.asList(4, 6, null, null, null, null, null, 3, 9));
        sudoku.add(Arrays.asList(null, null, null, 7, null, 8, null, null, null));
        sudoku.add(Arrays.asList(9, 7, null, null, null, null, null, 2, 8));
        sudoku.add(Arrays.asList(null, null, null, 5, null, 6, null, null, null));
        sudoku.add(Arrays.asList(1, 3, null, null, null, null, null, 6, 4));
        sudoku.add(Arrays.asList(null, null, null, null, 4, null, null, null, null));
        sudoku.add(Arrays.asList(null, 4, 7, null, null, null, 2, 1, null));
        return sudoku;
    }

    private static void init() {
        initList();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Integer val = sudokuList
                        .get(x)
                        .get(y);
                if (val != null) {
                    rowNumList
                            .get(y)
                            .add(val);
                    colNumList
                            .get(x)
                            .add(val);
                    mapNumList
                            .get(x / 3 + y / 3 * 3)
                            .add(val);
                }
            }
        }
        printSudoku();
        System.out.println("init finish...");
    }

    private static void initList() {
        rowNumList.clear();
        colNumList.clear();
        mapNumList.clear();
        for (int i = 0; i < 9; i++) {
            rowNumList.add(new HashSet<>());
            colNumList.add(new HashSet<>());
            mapNumList.add(new HashSet<>());
        }
    }
}
