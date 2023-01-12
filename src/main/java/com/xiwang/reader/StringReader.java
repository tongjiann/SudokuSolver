package com.xiwang.reader;

import com.xiwang.bean.Board;
import com.xiwang.init.Initer;

/**
 * @author xiwang
 * @apiNote
 * @since 2023-01-10 09:20
 */
public class StringReader implements Reader<String> {
    @Override
    public void doRead(Board board,String source) {
        String[] s = source
                .replace(" ", "")
                .split("\n");
        if (s.length != 9) {
            throw new RuntimeException("字符串长度异常");
        }
        for (int row = 0; row < 9; row++) {
            String substring = s[row];
            for (int col = 0; col < 9; col++) {
                int value = Integer.parseInt(String.valueOf(substring.charAt(col)));
                if (value == 0) {
                    continue;
                }
                board.setVal(row, col, value);
            }
        }
    }
}
