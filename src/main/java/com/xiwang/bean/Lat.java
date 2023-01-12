package com.xiwang.bean;

import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xiwang
 * @apiNote
 * @since 2023-01-05 13:56
 */
@Data
public class Lat {
    private Board board;
    private boolean isInitial;
    private boolean isStable;
    private Integer val = null;
    private Set<Integer> possibleVal = new HashSet<>(9);
    private Set<Integer> existInRow = new HashSet<>();
    private Set<Integer> existInCol = new HashSet<>();
    private Set<Integer> existInMap = new HashSet<>();
    private Integer row;
    private Integer col;
    private Integer mapIndex;

    public Lat(int row, int col, Board board) {
        this.board = board;
        this.row = row;
        this.col = col;
        this.mapIndex = col / 3 + row / 3 * 3;
        this.possibleVal.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        this.isInitial = false;
        this.isStable = false;
    }

    public Lat(int row, int col, int val, Board board) {
        this.board = board;
        this.row = row;
        this.col = col;
        this.val = val;
        this.mapIndex = col / 3 + row / 3 * 3;
        this.isInitial = false;
        this.isStable = false;
    }

    public void setVal(Integer val) {

        // fixme 这里的赋值和修改可能值的顺序可能存在问题
        removePossibleVal(val);

        decreasePossibleValCnt(val);

        this.val = val;
        this.isStable = true;
        this.possibleVal.clear();
        this.existInRow.clear();
        this.existInCol.clear();
        this.existInMap.clear();
        if (board.isInInitial()) {
            this.isInitial = true;
        }
    }

    private void decreasePossibleValCnt(Integer val) {
        Map<Integer, Integer> rowPossibleCnt = this
                .getRowLat()
                .getPossibleCnt();
        Map<Integer, Integer> colPossibleCnt = this
                .getColLat()
                .getPossibleCnt();
        Map<Integer, Integer> mapPossibleCnt = this
                .getMapLat()
                .getPossibleCnt();


        for (Integer integer : possibleVal) {
            rowPossibleCnt.computeIfPresent(integer, (k, v) -> v - 1);
            colPossibleCnt.computeIfPresent(integer, (k, v) -> v - 1);
            mapPossibleCnt.computeIfPresent(integer, (k, v) -> v - 1);
        }

    }

    private void removePossibleVal(Integer val) {
        this
                .getRowLat()
                .getPossibleCnt()
                .remove(val);
        this
                .getColLat()
                .getPossibleCnt()
                .remove(val);
        this
                .getMapLat()
                .getPossibleCnt()
                .remove(val);
    }

    private void decreasePossibleVal(Integer val) {
        this
                .getRowLat()
                .getPossibleCnt()
                .computeIfPresent(val, (k, v) -> v - 1);
        this
                .getColLat()
                .getPossibleCnt()
                .computeIfPresent(val, (k, v) -> v - 1);
        this
                .getMapLat()
                .getPossibleCnt()
                .computeIfPresent(val, (k, v) -> v - 1);

    }

    public void onSetVal(int row, int col, int val) {
        if (isStable) {
            return;
        }
        if (possibleVal.remove(val)) {
            decreasePossibleVal(val);
        }
        if (possibleVal.size() == 1 && !board.isInInitial()) {
            int value = possibleVal
                    .stream()
                    .toList()
                    .get(0);
            board.setVal(this.row, this.col, value);
            System.out.println("通过唯一值算法算得值" + this);

        }
    }

    @Override
    public String toString() {
        return "Lat{" + "isInitial=" + isInitial + ", isStable=" + isStable + ", val=" + val + ", row=" + row +
               ", col=" + col + ", mapIndex=" + mapIndex + ", possibleVal=" + possibleVal + '}';
    }

    public LatGroup getRowLat() {
        return board
                .getRowLatList()
                .get(row);
    }

    public LatGroup getColLat() {
        return board
                .getColLatList()
                .get(col);
    }

    public LatGroup getMapLat() {
        return board
                .getMapLatList()
                .get(mapIndex);
    }
}
