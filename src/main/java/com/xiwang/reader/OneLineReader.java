package com.xiwang.reader;

import com.xiwang.bean.Board;

/**
 * @author xiwang
 * @apiNote
 * @since 2023-01-11 17:32
 */
public class OneLineReader implements Reader<String> {
    @Override
    public void doRead(Board board, String source) {
        if (source == null || source.length() != 81) {
            throw new RuntimeException("error length...");
        }
        String rowStr;
        for (int row = 0; row < 9; row++) {
            rowStr = source.substring(0, 9);
            source = source.substring(9);
            int val;
            for (int col = 0; col < 9; col++) {
                val = Integer.parseInt(rowStr.substring(col, col + 1));
                if (val != 0) {
                    board.setVal(row, col, val);
                }
            }
        }
    }
}
