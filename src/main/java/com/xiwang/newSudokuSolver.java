package com.xiwang;

import com.xiwang.bean.Board;
import com.xiwang.bean.Lat;
import com.xiwang.bean.LatGroup;
import com.xiwang.reader.OneLineReader;
import com.xiwang.reader.Reader;
import com.xiwang.reader.StringReader;
import com.xiwang.reader.SudokuNameUrlReader;
import com.xiwang.solver.PossibleCntSolver;
import com.xiwang.solver.SimpleSolver;

import java.util.Collection;
import java.util.List;

/**
 * @author xiwang
 * @apiNote
 * @since 2023-01-05 14:10
 */
public class newSudokuSolver {

    public static void main33(String[] args) {
        String source = "810003290067000000900500006000408000604000809000209000700001008000000370053800042";
        Reader<String> reader = new OneLineReader();
        Board board = reader.read(source);
        solve(board);
    }

    /**
     * 通过SuddokuName生成board
     *
     * @param args
     */
    public static void main(String[] args) {
        Reader<String> reader = new SudokuNameUrlReader();
        String puzzleNum = "";
        String sudokuNameUrl = "https://www.sudoku.name/index.php?ln=cn&puzzle_num=" + puzzleNum +
                               "&play=1&difficult=4&timer=&time_limit=0&killer_sudoku=0";
        Board board = reader.read(sudokuNameUrl);
        solve(board);
    }

    private static void solve(Board board) {
        SimpleSolver simpleSolver = new SimpleSolver();
        simpleSolver.solve(board);
        PossibleCntSolver possibleCntSolver = new PossibleCntSolver();
        while (possibleCntSolver.solve(board)) {
            possibleCntSolver.solve(board);
        }
        List<Lat> latList = board
                .getRowLatList()
                .stream()
                .map(LatGroup::getLatList)
                .flatMap(Collection::stream)
                .toList();

        printResult(latList);

        printOneLine(latList);
    }

    private static void printOneLine(List<Lat> latList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Lat lat : latList) {
            if (lat.isStable()) {
                stringBuilder.append(lat.getVal());
            } else {
                stringBuilder.append("0");
            }
        }
        System.out.println("请在[http://www.sudokufans.org.cn/2008/index2.php?ti=" + stringBuilder + "]继续解答");
    }

    /**
     * 通过输入9*9的数字字符串产生board
     *
     * @param args
     */
    public static void main99(String[] args) {
        StringReader reader = new StringReader();

        String source = """
                970000000
                010563000
                006090004
                038000007
                000000000
                400000690
                500040800
                000916050
                000000021
                """;
        Board board = reader.read(source);
        SimpleSolver simpleSolver = new SimpleSolver();
        simpleSolver.solve(board);
        PossibleCntSolver possibleCntSolver = new PossibleCntSolver();
        while (possibleCntSolver.solve(board)) {
            possibleCntSolver.solve(board);
        }
        List<Lat> latList = board
                .getRowLatList()
                .stream()
                .map(LatGroup::getLatList)
                .flatMap(Collection::stream)
                .toList();

        printResult(latList);


        //        board.printRow();
        //        System.out.println("...");
    }

    private static void printResult(List<Lat> latList) {
        System.out.println("可能性计算得出的结果");
        latList
                .stream()
                .filter(e -> e.isStable() && !e.isInitial())
                .forEach(System.out::println);
        System.out.println("未得出结果的每个格子的可能值");
        latList
                .stream()
                .filter(e -> !e.isStable())
                .forEach(System.out::println);
    }

}
