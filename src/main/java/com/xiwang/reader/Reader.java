package com.xiwang.reader;

import com.xiwang.bean.Board;
import com.xiwang.initer.Initer;

/**
 * @author xiwang
 * @apiNote
 * @since 2023-01-10 09:19
 */
public interface Reader<T> {
    default Board read(T source) {
        Board board = Initer.initBoard();
        board.setInInitial(true);
        doRead(board, source);
        board.setInInitial(false);
        return board;
    }

    void doRead(Board board, T source);
}
