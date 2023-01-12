package com.xiwang.solver;

import com.xiwang.bean.Board;
import com.xiwang.bean.Lat;
import com.xiwang.bean.LatGroup;

import java.util.List;
import java.util.Set;

/**
 * @author xiwang
 * @apiNote 将经过行、列、图的排除后只能一个可能数字的格子写值
 * @since 2023-01-05 15:38
 */
public class SimpleSolver implements Solver {


    @Override
    public boolean solve(Board board) {
        boolean flag = false;
        List<LatGroup> colLatList = board.getColLatList();
        for (LatGroup latGroup : colLatList) {
            for (Lat lat : latGroup.getLatList()) {
                Set<Integer> possibleVal = lat.getPossibleVal();
                if (!lat.isStable() && possibleVal.size() == 1) {
                    board.setVal(lat.getRow(), lat.getCol(), possibleVal
                            .stream()
                            .toList()
                            .get(0));
                    System.out.println("通过SimpleSolver算得值" + lat);
                    flag = true;
                }
            }
        }
        //        board.print();
        return flag;
    }
}
