package com.example.pikachess.chess;

import java.io.Serializable;

public enum ChessPieceId implements Serializable {
    Pawn(),
    Rook(),
    Knight(),
    Bishop(),
    Queen(),
    King(),
    NoPiece();
}
