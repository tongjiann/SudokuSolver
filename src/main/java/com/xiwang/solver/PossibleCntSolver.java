package com.xiwang.solver;

import com.xiwang.bean.Board;
import com.xiwang.bean.Lat;
import com.xiwang.bean.LatGroup;

import java.util.List;
import java.util.Map;

/**
 * @author xiwang
 * @apiNote
 * @since 2023-01-10 11:31
 */
public class PossibleCntSolver implements Solver {
    @Override
    public boolean solve(Board board) {
        if (board == null) {
            throw new RuntimeException("board is null");
        }
        boolean f = innerSolve(board.getRowLatList(), board) || innerSolve(board.getColLatList(), board) ||
                    innerSolve(board.getMapLatList(), board);
        //        board.print();
        return f;
    }

    private static boolean innerSolve(List<LatGroup> latGroupList, Board board) {
        boolean flag = false;
        for (LatGroup latGroup : latGroupList) {
            Map<Integer, Integer> possibleCnt = latGroup.getPossibleCnt();
            List<Integer> keySet = possibleCnt
                    .keySet()
                    .stream()
                    .toList();
            for (Integer k : keySet) {
                if (!possibleCnt.containsKey(k)) {
                    continue;
                }
                Integer v = possibleCnt.get(k);
                if (v != 1) {
                    continue;
                }
                for (Lat lat : latGroup.getLatList()) {
                    if (lat
                            .getPossibleVal()
                            .contains(k)) {
                        board.setVal(lat, k);
                        System.out.println("通过PossibleCntSolver算得值" + lat);
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }
}
