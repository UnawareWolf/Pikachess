package com.example.pikachess.chess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pikachess.chess.pieces.Bishop;
import com.example.pikachess.chess.pieces.Empty;
import com.example.pikachess.chess.pieces.King;
import com.example.pikachess.chess.pieces.Knight;
import com.example.pikachess.chess.pieces.Pawn;
import com.example.pikachess.chess.pieces.Queen;
import com.example.pikachess.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.abs;

public class Board {

    private List<ChessSquare> boardSquares = new ArrayList<>();
    private Map<Integer, LinkedList<ChessSquare>> pieceColumns = new HashMap<>();
    private Map<Integer, LinkedList<ChessSquare>> pieceRows = new HashMap<>();
    private Map<Integer, LinkedList<ChessSquare>> pieceUpDiagonals = new HashMap<>();
    private Map<Integer, LinkedList<ChessSquare>> pieceDownDiagonals = new HashMap<>();
    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();
    private int squareSize;
    private LinkedList<ChessMove> chessMoves = new LinkedList<>();
    private boolean playAsWhite;
    private PieceColour turnToPlay = PieceColour.White;

    public Board(ChessView chessView, boolean playAsWhite) {
        this.squareSize = chessView.getSquareSize();
        this.playAsWhite = playAsWhite;
        assembleBoard();
        assembleBoardGroups();
    }

    public Board(Board board) {
        for (ChessSquare chessSquare : board.getBoardSquares()) {
            boardSquares.add(new ChessSquare(chessSquare)); // use copy method maybe
        }
        playAsWhite = board.playAsWhite;
        turnToPlay = board.turnToPlay;
        mRect = board.mRect;
        mPaint = board.mPaint;
        squareSize = board.squareSize;
        chessMoves = board.chessMoves;
        assembleBoardGroups();
    }

    private void assembleBoard() {
        boolean fillSquare = false;
        for (int i = 0; i < 8 ; i++){
            for (int j = 0; j < 8; j++){
                int squareLeft = ChessView.OFFSET + ChessView.BORDER_WIDTH + i*squareSize;
                int squareRight = ChessView.OFFSET + ChessView.BORDER_WIDTH + (i + 1)*squareSize;
                int squareTop = ChessView.OFFSET + ChessView.BORDER_WIDTH + j*squareSize;
                int squareBottom = ChessView.OFFSET + ChessView.BORDER_WIDTH + (j + 1)*squareSize;
                int xCoordinate;
                int yCoordinate;
                if (playAsWhite) {
                    xCoordinate = i + 1;
                    yCoordinate = 8 - j;
                }
                else {
                    xCoordinate = 8 - i;
                    yCoordinate = j + 1;
                }

                SquareBounds squareBounds = new SquareBounds(squareLeft, squareTop, squareRight, squareBottom);
                ChessPiece piece = getPieceFromCoordinates(xCoordinate, yCoordinate);
                ChessSquare chessSquare = new ChessSquare(squareBounds, piece, xCoordinate, yCoordinate);
                if (fillSquare){
                    chessSquare.setColour(PieceColour.Black);
                } else {
                    chessSquare.setColour(PieceColour.White);
                }
                boardSquares.add(chessSquare);
                fillSquare = !fillSquare;
            }
            fillSquare = !fillSquare;
        }
    }

    private ChessPiece getPieceFromCoordinates(int xCoordinate, int yCoordinate) {
        ChessPiece piece = null;
        PieceColour pieceColour = null;
        if (yCoordinate == 1 || yCoordinate == 2){
            pieceColour = PieceColour.White;
        }
        else if (yCoordinate == 7 || yCoordinate == 8) {
            pieceColour = PieceColour.Black;
        }

        if (yCoordinate == 2 || yCoordinate == 7) {
            piece = new Pawn(pieceColour);
        }
        else if(yCoordinate == 1 || yCoordinate == 8) {
            switch (xCoordinate) {
                case 1:
                case 8:
                    piece = new Rook(pieceColour);
                    break;
                case 2:
                case 7:
                    piece = new Knight(pieceColour);
                    break;
                case 3:
                case 6:
                    piece = new Bishop(pieceColour);
                    break;
                case 4: piece = new Queen(pieceColour);
                    break;
                case 5: piece = new King(pieceColour);
                    break;
            }
        }
        else {
            piece = new Empty();
        }
        return piece;
    }

    private void assembleBoardGroups() {
        for (int num = 1; num < 9; num++) {
            LinkedList<ChessSquare> verticalBoardGroup = new LinkedList<>();
            LinkedList<ChessSquare> horizontalBoardGroup = new LinkedList<>();
            for (ChessSquare chessSquare : boardSquares) {
                if (chessSquare.getXCoordinate() == num) {
                    verticalBoardGroup.add(chessSquare);
                }
                if (chessSquare.getYCoordinate() == num) {
                    horizontalBoardGroup.add(chessSquare);
                }
            }
            if (playAsWhite) {
                Collections.reverse(verticalBoardGroup);
            }
            else {
                Collections.reverse(horizontalBoardGroup);
            }
            pieceColumns.put(num, verticalBoardGroup);
            pieceRows.put(num, horizontalBoardGroup);
        }
        for (int i = 1; i < 16; i++) {
            LinkedList<ChessSquare> diagonalUpBoardGroup = new LinkedList<>();
            LinkedList<ChessSquare> diagonalDownBoardGroup = new LinkedList<>();
            for (ChessSquare chessSquare : boardSquares) {
                int xCoordinate = chessSquare.getXCoordinate();
                int yCoordinate = chessSquare.getYCoordinate();
                if (xCoordinate + yCoordinate == i + 1) {
                    diagonalDownBoardGroup.add(chessSquare);
                }
                if ((9 - xCoordinate) + yCoordinate == i + 1) {
                    diagonalUpBoardGroup.add(chessSquare);
                }
            }
            Collections.reverse(diagonalDownBoardGroup);
            pieceUpDiagonals.put(i, diagonalUpBoardGroup);
            pieceDownDiagonals.put(i, diagonalDownBoardGroup);
        }
    }

    public void drawBoard(Context context, Canvas canvas) {
        for (ChessSquare chessSquare : boardSquares) {
            chessSquare.drawSquare(context, canvas, mRect, mPaint);
        }
    }

    public List<ChessSquare> getBoardSquares() {
        return this.boardSquares;
    }

    public void initialisePieceBitmaps(Context context) {
        for (ChessSquare chessSquare : boardSquares) {
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                chessSquare.getPiece().setPieceImage(context); //not ideal because if there are multiple instances of each piece, there will be multiple images stored.
                int x = 0;
            } // maybe do something like a set of pieces to avoid duplicates.
        }
    }

    public void resizePieceBitmaps() {
        for (ChessSquare chessSquare : boardSquares) {
            ChessPiece newPiece = chessSquare.getPiece();
            ChessPieceId newID = chessSquare.getPiece().getId();
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                chessSquare.getPiece().resizePieceImage(squareSize); //not ideal because if there are multiple instances of each piece, there will be multiple images stored.
            } // maybe do something like a set of pieces to avoid duplicates.
        }
    }

    public Map<Integer, LinkedList<ChessSquare>> getPieceColumns() {
        return this.pieceColumns;
    }

    public Map<Integer, LinkedList<ChessSquare>> getPieceRows() {
        return this.pieceRows;
    }
    public Map<Integer, LinkedList<ChessSquare>> getPieceUpDiagonals() {
        return this.pieceUpDiagonals;
    }

    public Map<Integer, LinkedList<ChessSquare>> getPieceDownDiagonals() {
        return this.pieceDownDiagonals;
    }

    public List<ChessSquare> getSquaresUnderAttack() {
        List<ChessSquare> squaresUnderAttack = new ArrayList<>(); // refactor into multiple methods for readability
        for (ChessSquare chessSquare : boardSquares) {
            chessSquare.getPiece().setParentSquare(chessSquare); // this isn't ideal
            List<ChessSquare> possibleMoves = chessSquare.getPiece().getPieceSpecificAttackingMoves(this);
            if (possibleMoves != null) {
                for (ChessSquare possibleMove : possibleMoves) {
                    if (possibleMove.getPiece().getId() != ChessPieceId.NoPiece && possibleMove.getPiece().getColour() != chessSquare.getPiece().getColour()) {
                        squaresUnderAttack.add(possibleMove);
                    }
                }
            }
        }
        return squaresUnderAttack;
    }

    public boolean isSquareUnderAttack(ChessSquare chessSquare) {
        boolean underAttack = false;
        for (ChessSquare boardSquare : getSquaresUnderAttack()) {// get squares under attack usually uses a copy of the board.
            if (chessSquare == boardSquare) {
                underAttack = true;
                break;
            }
        }
        return underAttack;
    }
//
//    public List<ChessSquare> getAttackingSquares(ChessSquare defendingSquare) {
//        List<ChessSquare> attackingSquares = new ArrayList<>();
//        for (ChessSquare chessSquare : boardSquares) {
//            chessSquare.getPiece().setParentSquare(chessSquare); // this isn't ideal
//            List<ChessSquare> possibleMoves = chessSquare.getPiece().getPieceSpecificAttackingMoves(this);
//            if (possibleMoves != null) {
//                for (ChessSquare possibleMove : possibleMoves) {
//                    if (possibleMove == defendingSquare && possibleMove.getPiece().getColour() != chessSquare.getPiece().getColour()) {
//                        attackingSquares.add(chessSquare);
//                    }
//                }
//            }
//        }
//
//        return attackingSquares;
//    }

    public void storeMove(ChessMove chessMove) {
        this.chessMoves.add(chessMove);
    }

    public ChessMove getLastMove() {
        if (chessMoves.size() != 0) {
            return this.chessMoves.getLast();
        }
        else {
            return null;
        }
    }

    public LinkedList<ChessMove> getAllMoves() {
        return this.chessMoves;
    }

    public void removePawnIfEnPassant() {
        ChessMove lastMove = getLastMove();
        if (lastMove == null) {
            return;
        }
        if (lastMove.getPieceIdMoved() == ChessPieceId.Pawn && lastMove.getPieceIdTaken() == ChessPieceId.NoPiece && lastMove.getAbsXDistanceMoved() == 1) {
            for (ChessSquare chessSquare : this.boardSquares) {
                if (chessSquare.getXCoordinate() == lastMove.getSquareTo().getXCoordinate()) {
                    if (chessSquare.getYCoordinate() == lastMove.getSquareFrom().getYCoordinate()) {
                        chessSquare.setPiece(new Empty());
                    }
                }
            }
        }
    }

    public void moveRookIfCastle() {
        ChessMove lastMove = getLastMove();
        if (lastMove == null) {
            return;
        }
        if (lastMove.getPieceIdMoved() == ChessPieceId.King && lastMove.getAbsXDistanceMoved() == 2) {
            for (ChessSquare chessSquare : this.boardSquares) {
                if (chessSquare.getYCoordinate() == lastMove.getSquareTo().getYCoordinate() && chessSquare.getPiece().getId() == ChessPieceId.Rook && !chessSquare.getPiece().getHasMoved()) {
                    int xDistanceFromRookToKing = chessSquare.getXCoordinate() - lastMove.getSquareTo().getXCoordinate();
                    if (abs(xDistanceFromRookToKing) < 3 ) {
                        if (xDistanceFromRookToKing > 0) { // rook is to right of king
                            getChessSquare(lastMove.getSquareTo().getXCoordinate() - 1, lastMove.getSquareTo().getYCoordinate()).setPiece(chessSquare.getPiece());
                        }
                        else if(xDistanceFromRookToKing < 0) {
                            getChessSquare(lastMove.getSquareTo().getXCoordinate() + 1, lastMove.getSquareTo().getYCoordinate()).setPiece(chessSquare.getPiece());
                        }
                        chessSquare.setPiece(new Empty());
                        break;
                    }
                }
            }

        }
    }

    public ChessSquare getChessSquare(int xCoordinate, int yCoordinate) {
        ChessSquare newRookLocation = new ChessSquare();
        for (ChessSquare chessSquare : boardSquares) {
            if (chessSquare.getXCoordinate() == xCoordinate && chessSquare.getYCoordinate() == yCoordinate) {
                newRookLocation = chessSquare;
                break;
            }
        }
        return newRookLocation;
    }

    public ChessMove checkForPromotion() {
        ChessMove lastMove = getLastMove();
        if (lastMove == null) {
            return null;
        }
        if ((lastMove.getSquareTo().getYCoordinate() == 8 && lastMove.getPieceIdMoved() == ChessPieceId.Pawn && lastMove.getPieceColourMoved() == PieceColour.White) || (lastMove.getSquareTo().getYCoordinate() == 1 && lastMove.getPieceIdMoved() == ChessPieceId.Pawn && lastMove.getPieceColourMoved() == PieceColour.Black)) {
            return lastMove;
        }
        else {
            return null;
        }
    }

    public void calculateAndExecuteAIMove() {
        List<ChessMove> allLegalMoves = getAllLegalMoves();
        Random rand = new Random();
        List<ChessMove> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;
        for (ChessMove rankedMove : allLegalMoves) {
            Board boardCopy = new Board(this);
            ChessMove moveCopy = boardCopy.getMoveOnDuplicateBoard(rankedMove);
            int moveScore = boardCopy.executeMoveAndReturnScore(moveCopy);
            rankedMove.setMoveScore(moveScore);
            if (moveScore >= bestScore) {
                bestMoves.add(rankedMove);
                bestScore = moveScore;
            }
        }
        ChessMove randMove = bestMoves.get(rand.nextInt(bestMoves.size()));
        executeMove(randMove);
    }

    public ChessMove getBestAIMove() {
        List<ChessMove> allLegalMoves = getAllLegalMoves();
        Random rand = new Random();
        List<ChessMove> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;
        for (ChessMove rankedMove : allLegalMoves) {
            Board boardCopy = new Board(this);
            ChessMove moveCopy = boardCopy.getMoveOnDuplicateBoard(rankedMove);
            int moveScore = boardCopy.executeMoveAndReturnScore(moveCopy);
            rankedMove.setMoveScore(moveScore);
            if (moveScore >= bestScore) {
                bestMoves.add(rankedMove);
                bestScore = moveScore;
            }
        }
        ChessMove moveToExecute = null;
        if (bestMoves.size() > 0) {
            moveToExecute = bestMoves.get(rand.nextInt(bestMoves.size()));
        }
        return moveToExecute;
    }

    private ChessMove getMoveOnDuplicateBoard(ChessMove chessMove) {
        ChessSquare copySquareFrom = getChessSquare(chessMove.getSquareFrom().getXCoordinate(), chessMove.getSquareFrom().getYCoordinate());
        ChessSquare copySquareTo = getChessSquare(chessMove.getSquareTo().getXCoordinate(), chessMove.getSquareTo().getYCoordinate());
        return new ChessMove(copySquareFrom, copySquareTo);
    }

    private int executeMoveAndReturnScore(ChessMove moveToScore) {
        //ChessSquare squareFrom = getChessSquare(rankedMove.getSquareFrom().getXCoordinate(), rankedMove.getSquareFrom().getYCoordinate());

        ChessSquare squareFrom = moveToScore.getSquareFrom();
        ChessSquare squareTo = moveToScore.getSquareTo();
        int score = 0;
        if (squareTo.getPiece().getId() != ChessPieceId.NoPiece) {
            int takePiece = squareFrom.getPiece().getPieceScore();
            Board boardCopy = new Board(this);
            ChessMove moveCopy = boardCopy.getMoveOnDuplicateBoard(moveToScore);
            boardCopy.executeMove(moveCopy);
            for (ChessSquare chessSquare : boardCopy.getSquaresUnderAttack()) {
                if (chessSquare.getXCoordinate() == squareTo.getXCoordinate() && chessSquare.getXCoordinate() == squareTo.getXCoordinate()) {
                    takePiece -= chessSquare.getPiece().getPieceScore();
                }
            }
            if (takePiece > 0) {
                score += 20;
            }
        }

        executeMove(moveToScore);
        /*ChessSquare*/ squareTo = moveToScore.getSquareTo();
        List<ChessSquare> squaresUnderAttack = getSquaresUnderAttack();
        for (ChessSquare underAttackSquare : squaresUnderAttack) {
            if (underAttackSquare.getPiece().getColour() != squareTo.getPiece().getColour()) {

            }
        }

        for (ChessSquare underAttackSquare : squaresUnderAttack) {
            ChessPiece underAttackPiece = underAttackSquare.getPiece();
            if (underAttackPiece.getColour() == squareTo.getPiece().getColour()) {
                score -= underAttackPiece.getPieceScore();
            }
            else if (underAttackPiece.getColour() != squareTo.getPiece().getColour() && underAttackPiece.getId() != ChessPieceId.NoPiece) {
                score += underAttackPiece.getPieceScore();
            }
            if (score > 0) {
                System.out.println(score);
            }
        }
        return score;
    }

    public void executeMove(ChessMove move) {
        move.getSquareTo().setPiece(move.getSquareFrom().getPiece());
        move.getSquareTo().getPiece().setHasMoved();
        move.getSquareTo().getPiece().setParentSquare(move.getSquareTo());
        move.getSquareFrom().setPiece(new Empty());
    }

    private List<ChessMove> getAllLegalMoves() {
        List<ChessMove> allLegalMoves = new ArrayList<>();
        for (ChessSquare chessSquare : boardSquares) {
            if (chessSquare.getPiece().getColour() == turnToPlay && chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                List<ChessSquare> legalMoveSquares = chessSquare.getPiece().getLegalMoves(this);
                for (ChessSquare legalMoveSquare : legalMoveSquares) {
                    allLegalMoves.add(new ChessMove(chessSquare, legalMoveSquare));
                }
            }
        }
        return allLegalMoves;
    }

    public void changeTurn() {
        if (this.turnToPlay == PieceColour.Black){
            this.turnToPlay = PieceColour.White;
        }
        else {
            this.turnToPlay = PieceColour.Black;
        }
    }

    public PieceColour getTurnToPlay() {
        return this.turnToPlay;
    }

    public void drawAllChessPieces(Canvas canvas) {
        for (ChessSquare chessSquare : boardSquares){
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece){
                chessSquare.getPiece().drawPiece(canvas, chessSquare.getBoundary()); // maybe actually set location in piece
            }
        }
    }

    public boolean isGameCheckmate() { // this includes stalemate but they should probably be separated.
        boolean checkmate = true;
        PieceColour colourOfLastMove = PieceColour.Black;
        if (this.getAllMoves().size() > 0) {
            colourOfLastMove = this.getLastMove().getPieceColourMoved();
        }
        for (ChessSquare chessSquare : this.getBoardSquares()) {
            if (chessSquare.getPiece().getColour() != colourOfLastMove && chessSquare.getPiece().getColour() != PieceColour.NoColour && chessSquare.getPiece().getLegalMoves(this).size() != 0) {
                checkmate = false;
            }
        }
        return checkmate;
    }

    public boolean isInsufficientMaterial() {
        boolean insufficientMaterial = false;
        for (ChessPiece chessPiece : this.getAllPieces()) {

        }
        return insufficientMaterial;
    }

    public List<ChessPiece> getAllPieces() {
        List<ChessPiece> allPieces = new ArrayList<>();
        for (ChessSquare chessSquare : boardSquares) {
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                allPieces.add(chessSquare.getPiece());
            }
        }
        return allPieces;
    }
}
