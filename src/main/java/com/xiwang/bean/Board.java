package com.xiwang.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiwang
 * @apiNote
 * @since 2023-01-05 14:03
 */
@Data
public class Board {
    private List<LatGroup> colLatList = new ArrayList<>(9);
    private List<LatGroup> rowLatList = new ArrayList<>(9);
    private List<LatGroup> mapLatList = new ArrayList<>(9);
    private Integer initialCnt;
    private Integer notStableCnt;
    private Integer stableCnt;
    private boolean inInitial;

    private boolean isSolved = false;

    public void setVal(Lat lat, int val) {
        setVal(lat.getRow(), lat.getCol(), val);
    }

    public void setVal(int row, int col, int val) {
        int mapIndex = col / 3 + row / 3 * 3;
        LatGroup rowLat = rowLatList.get(row);
        LatGroup colLat = colLatList.get(col);
        LatGroup mapLat = mapLatList.get(mapIndex);
        Lat lat = rowLat
                .getLatList()
                .get(col);
        if (lat.isStable()) {
            throw new RuntimeException("正在修改一个已固定的值");
        }
        lat.setVal(val);
        stableCnt++;
        notStableCnt--;
        rowLat.onSetVal(row, col, val);
        colLat.onSetVal(row, col, val);
        mapLat.onSetVal(row, col, val);
//        for (LatGroup latGroup : rowLatList) {
//            System.out.println(latGroup.getType() + "|" + latGroup.getIndex() + ":" + latGroup.getPossibleCntStr());
//        }
//        for (LatGroup latGroup : colLatList) {
//            System.out.println(latGroup.getType() + "|" + latGroup.getIndex() + ":" + rowLat.getPossibleCnt());
//        }
//        for (LatGroup latGroup : mapLatList) {
//            System.out.println(latGroup.getType() + "|" + latGroup.getIndex() + ":" + rowLat.getPossibleCnt());
//        }
    }

    public Board() {
        stableCnt = 0;
        notStableCnt = 81;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("—————————————\n");
        for (int row = 0; row < 9; row++) {
            LatGroup rowLat = rowLatList.get(row);
            for (int col = 0; col < 9; col++) {
                if (col % 3 == 0) {
                    sb.append("| ");
                }
                Integer val = rowLat
                        .getLatList()
                        .get(col)
                        .getVal();
                sb.append(val == null ? "_ " : val + " ");
                if (col == 8) {
                    sb.append("|");
                }
            }
            if (row != 8) {
                if ((row + 1) % 3 == 0) {
                    sb.append("\n—————————————\n");
                } else {
                    sb.append("\n------------------------\n");
                }
            }
        }
        sb.append("\n—————————————");
        return sb.toString();
    }

    public void setInInitial(boolean inInitial) {
        this.inInitial = inInitial;
        if (!inInitial) {
            print();
        }
    }

    public void print() {
        System.out.println("==========================================");
        System.out.println(this);
        System.out.println("==========================================");
    }

    public void printRow() {
        System.out.println("==========================================");
        LatGroup latGroup;
        List<Lat> latList;
        Lat lat;
        int index = 0;
        for (int row = 0; row < 9; row++) {
            latGroup = rowLatList.get(row);
            if (latGroup.isStable()) {
                continue;
            }
            latList = latGroup.getLatList();
            for (int col = 0; col < 9; col++) {
                lat = latList.get(col);
                if (lat.isStable()) {
                    continue;
                }
                System.out.println(
                        (index < 10 ? "0" + index : index) + " R:" + lat.getRow() + " C:" + lat.getCol() + " V:" +
                        String.join(" ", lat
                                .getPossibleVal()
                                .stream()
                                .map(String::valueOf)
                                .toList()));
                index++;
            }
            System.out.println("------------");
        }
        System.out.println("==========================================");
    }

}
