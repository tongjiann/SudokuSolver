package com.xiwang.reader;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import com.xiwang.bean.Board;

import java.util.List;

/**
 * @author xiwang
 * @apiNote
 * @since 2023-01-11 14:07
 */
public class SudokuNameUrlReader implements Reader<String> {
    @Override
    public void doRead(Board board, String sudokuNameUrl) {
        String res = HttpUtil.get(sudokuNameUrl);
        List<String> numList = ReUtil.findAll("<input style=\"color: #000;\".*?>", res, 0);
        if (CollectionUtil.size(numList) != 81) {
            throw new RuntimeException("未找到81个元素");
        }
        int index;
        String numStr;
        int val;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                index = row * 9 + col;
                numStr = numList.get(index);
                if (numStr.contains("value")) {
                    val = Integer.parseInt(numStr.split("value=\"")[1].substring(0, 1));
                    board.setVal(row, col, val);
                }
            }
        }
        board.setInInitial(false);
    }
}
