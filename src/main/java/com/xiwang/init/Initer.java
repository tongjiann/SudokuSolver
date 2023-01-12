package com.xiwang.init;

import com.xiwang.bean.Board;
import com.xiwang.bean.Lat;
import com.xiwang.bean.LatGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiwang
 * @apiNote
 * @since 2023-01-10 09:22
 */
public class Initer {

    public static Board initBoard() {
        Board board = new Board();
        List<LatGroup> rowLatList = new ArrayList<>();
        List<LatGroup> colLatList = new ArrayList<>();
        List<LatGroup> mapLatList = new ArrayList<>();
        LatGroup rowLat;
        LatGroup colLat;
        LatGroup mapLat;
        Lat lat;
        int mapIndex;
        board.setRowLatList(rowLatList);
        board.setColLatList(colLatList);
        board.setMapLatList(mapLatList);
        for (int index = 0; index < 9; index++) {
            rowLatList.add(new LatGroup("ROW",index));
            colLatList.add(new LatGroup("COL",index));
            mapLatList.add(new LatGroup("MAP",index));
        }
        for (int row = 0; row < 9; row++) {
            rowLat = rowLatList.get(row);
            for (int col = 0; col < 9; col++) {
                mapIndex = col / 3 + row / 3 * 3;
                colLat = colLatList.get(col);
                mapLat = mapLatList.get(mapIndex);
                lat = new Lat(row, col, board);
                rowLat.addLat(lat);
                colLat.addLat(lat);
                mapLat.addLat(lat);
            }
        }
        return board;
    }
}
