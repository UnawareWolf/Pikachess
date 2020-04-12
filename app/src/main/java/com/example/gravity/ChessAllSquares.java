package com.example.gravity;

import java.util.Set;

public class ChessAllSquares {

    private Set<ChessSquare> squares;

    public void addSquare(ChessSquare square) {
        squares.add(square);
    }

    public ChessAllSquares(){
    }

    public Set<ChessSquare> getAllSquares() {
        return squares;
    }
}
