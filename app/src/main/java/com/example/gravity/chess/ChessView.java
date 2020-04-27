package com.example.gravity.chess;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.gravity.Gravitactivity;
import com.example.gravity.R;
import com.example.gravity.chess.pieces.Bishop;
import com.example.gravity.chess.pieces.Empty;
import com.example.gravity.chess.pieces.King;
import com.example.gravity.chess.pieces.Knight;
import com.example.gravity.chess.pieces.Pawn;
import com.example.gravity.chess.pieces.Queen;
import com.example.gravity.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ChessView extends View {

    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();
    private Bitmap totodileBackground;
    public static final int OFFSET = 60;
    public static final int BORDER_WIDTH = 8;
    boolean touched = false;
    float xTouch, yTouch;
    private Board chessBoard;
    private ChessSquare selectedSquare;
    boolean secondTouched = false;
    private ChessSquare secondSelectedSquare;
    boolean greyedOut = true;
    private SquareBounds confirmMoveButtonBounds = new SquareBounds();
    boolean newBoard = true;
    int canvasWidth;
    private int squareSize;
    int boardSize;
    private Context context;// only make variables private if they will be get or set by other classes. Otherwise package private (ie nothing) is good. wrong
    private PieceColour turnToPlay = PieceColour.White;
    private ChessMove promotionMove = new ChessMove();
    private List<ChessSquare> promotionPieces = new ArrayList<>();
    GameState gameState = GameState.Normal;
    private boolean opponentIsAI;

    public ChessView(Context context) {
        super(context);
        this.context = context;
        totodileBackground = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);
        Bundle chessBundle = ((Gravitactivity) context).getIntent().getExtras();
        if (chessBundle != null) {
            //opponentIsAI = chessBundle.getBoolean("Computer");
            if (chessBundle.getString(String.valueOf(R.id.opponent_spinner)).equals("Computer")) {
                opponentIsAI = true;
            }
            else {
                opponentIsAI = false;
                System.out.println(chessBundle.getString(String.valueOf(R.id.opponent_spinner)));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (newBoard) {
            canvasWidth = getWidth();
            boardSize = canvasWidth - 2*OFFSET - 2*BORDER_WIDTH;
            squareSize = boardSize/8;
            chessBoard = new Board(this);
            chessBoard.initialisePieceBitmaps(context);
            chessBoard.resizePieceBitmaps();
            newBoard = false;
        }
        canvas.drawBitmap(totodileBackground, 0, 0, null);
        drawBoardBorder(canvas);
        //drawBoard(canvas, allChessSquares);
        chessBoard.drawBoard(context, canvas);

        if (touched) {
            highlightChessSquare(canvas, selectedSquare);
        }
        if (secondTouched) {
            highlightChessSquare(canvas, secondSelectedSquare);
            greyedOut = false;
        }

        drawConfirmMoveButton(canvas);

        drawAllChessPieces(chessBoard.getBoardSquares(), canvas);
        if (gameState == GameState.PromotionMenu) {
            drawPawnPromotionMenu(canvas);
        }

    }

    private void drawBoardBorder(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.chessBrown));
        mPaint.setStrokeWidth(BORDER_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        mRect.set(OFFSET + BORDER_WIDTH/2, OFFSET + BORDER_WIDTH/2, canvasWidth - OFFSET - BORDER_WIDTH/2, canvasWidth - OFFSET - BORDER_WIDTH/2);
        canvas.drawRect(mRect, mPaint);
    }

    private void drawAllChessPieces(List<ChessSquare> chessSquares, Canvas canvas) {
        for (ChessSquare chessSquare : chessSquares){
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece){
                chessSquare.getPiece().drawPiece(canvas, chessSquare.getBoundary()); // maybe actually set location in piece
            }
        }
    }

    private void drawPawnPromotionMenu(Canvas canvas) {
        double squareSizeDub = squareSize;
        int menuWidth = (int) (squareSizeDub*(2/3.0));
        int menuHeight = menuWidth*4;

        int menuLeft = promotionMove.getSquareTo().getBoundary().getLeft();
        int squareTop = promotionMove.getSquareTo().getBoundary().getTop();
        int menuRight = menuLeft + menuWidth;
        int menuBottom = squareTop + menuHeight;
        mRect.set(menuLeft, squareTop, menuRight, menuBottom);
        mPaint.setColor(getResources().getColor(R.color.buttonGrey));
        canvas.drawRect(mRect, mPaint);
        promotionPieces = new ArrayList<>();
        PieceColour colourLastMoved = chessBoard.getAllMoves().getLast().getPieceColourMoved();
        promotionPieces.add(new ChessSquare(new Queen(colourLastMoved)));
        promotionPieces.add(new ChessSquare(new Rook(colourLastMoved)));
        promotionPieces.add(new ChessSquare(new Bishop(colourLastMoved)));
        promotionPieces.add(new ChessSquare(new Knight(colourLastMoved)));
        for (ChessSquare promotionPiece : promotionPieces) {
            promotionPiece.getPiece().setPieceImage(context);
            SquareBounds pieceBound = new SquareBounds(menuLeft, squareTop, menuRight, squareTop + menuWidth);
            promotionPiece.setBounds(pieceBound);
            promotionPiece.getPiece().drawPiece(canvas, pieceBound);
            squareTop = squareTop + menuWidth;
        }


    }

    private void drawConfirmMoveButton(Canvas canvas) {
        int buttonWidth = canvasWidth/4;
        int buttonHeight = buttonWidth/3;
        int buttonLeft = OFFSET + BORDER_WIDTH/2;
        int buttonTop = canvasWidth - BORDER_WIDTH/2;
        int buttonRight = buttonLeft + buttonWidth;
        int buttonBottom = buttonTop + buttonHeight;
        confirmMoveButtonBounds.set(buttonLeft, buttonTop, buttonRight, buttonBottom);
        mRect.set(buttonLeft, buttonTop, buttonRight, buttonBottom);
        mPaint.setColor(getResources().getColor(R.color.buttonGrey));
        if (greyedOut) {
            mPaint.setAlpha(40);
        }
        else {
            mPaint.setAlpha(255);
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mRect, mPaint);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(BORDER_WIDTH);
        canvas.drawRect(mRect, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(80 - 2*BORDER_WIDTH);
        canvas.drawText("Confirm", buttonLeft + BORDER_WIDTH, buttonBottom - 2*BORDER_WIDTH, mPaint);
    }

    private void highlightChessSquare(Canvas canvas, ChessSquare squareToHighlight) {
        mRect.set(squareToHighlight.getBoundary().getLeft(), squareToHighlight.getBoundary().getTop(), squareToHighlight.getBoundary().getRight(), squareToHighlight.getBoundary().getBottom());
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.selectedSquareColour));
        canvas.drawRect(mRect, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameState == GameState.Normal && event.getAction() == MotionEvent.ACTION_DOWN){
            xTouch = event.getX();
            yTouch = event.getY();
            for (ChessSquare chessSquare : chessBoard.getBoardSquares()) {
                if (chessSquare.getBoundary().squareContainsCoordinates(chessSquare.getBoundary(), xTouch, yTouch)){
                    if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece && chessSquare.getPiece().getColour() == turnToPlay) {
                        selectedSquare = chessSquare;
                        secondTouched = false;
                        touched = true;
                        greyedOut = true;
                    }
                    if(touched) {
                        selectedSquare.getPiece().setParentSquare(selectedSquare);
                        boolean legalMove = false;
                        for (ChessSquare legalSquare : selectedSquare.getPiece().getLegalMoves(chessBoard)) {
                            if (chessSquare == legalSquare) {
                                legalMove = true;
                                break;
                            }
                        }
                        if (legalMove) {
                            secondSelectedSquare = chessSquare;
                            secondTouched = true;
                        }
                    }
                    invalidate();
                }
            }
            if(secondTouched && confirmMoveButtonBounds.squareContainsCoordinates(confirmMoveButtonBounds, xTouch, yTouch)) {
                ChessMove chessMove = new ChessMove(new ChessSquare(selectedSquare), new ChessSquare(secondSelectedSquare));
                selectedSquare.getPiece().setHasMoved();
                secondSelectedSquare.setPiece(selectedSquare.getPiece());
                secondSelectedSquare.getPiece().setParentSquare(secondSelectedSquare);
                selectedSquare.setPiece(new Empty());
                chessBoard.storeMove(chessMove);
                chessBoard.removePawnIfEnPassant();
                chessBoard.moveRookIfCastle();
                promotionMove = chessBoard.checkForPromotion();
                if (promotionMove != null) {
                    gameState = GameState.PromotionMenu;
                }
                changeTurn();
                touched = false;
                secondTouched = false;
                greyedOut = true;

                if (opponentIsAI) {
                    chessBoard.calculateAndExecuteAIMove();
                    changeTurn();
                }
                invalidate();
            }
        }
        else if (gameState == GameState.PromotionMenu && event.getAction() == MotionEvent.ACTION_DOWN) {
            xTouch = event.getX();
            yTouch = event.getY();
            for (ChessSquare promotionPiece : promotionPieces) {
                if (promotionPiece.getBoundary().squareContainsCoordinates(promotionPiece.getBoundary(), xTouch, yTouch)) {
                    ChessSquare lastMoveSquareTo = chessBoard.getLastMove().getSquareTo();
                    ChessSquare squareToPromote = chessBoard.getChessSquare(lastMoveSquareTo.getXCoordinate(), lastMoveSquareTo.getYCoordinate());
                    squareToPromote.setPiece(promotionPiece.getPiece());
                    squareToPromote.getPiece().resizePieceImage(squareSize);
                    squareToPromote.getPiece().setParentSquare(squareToPromote);
                    squareToPromote.getPiece().setHasMoved();
                    gameState = GameState.Normal;
                    invalidate();
                }
            }
        }
        return true;
    }

    private void changeTurn() {
        if (this.turnToPlay == PieceColour.Black){
            this.turnToPlay = PieceColour.White;
        }
        else {
            this.turnToPlay = PieceColour.Black;
        }
    }

    public int getSquareSize() {
        return this.squareSize;
    }

}
