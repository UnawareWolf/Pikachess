package com.example.gravity.chess;

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
import com.example.gravity.chess.pieces.Knight;
import com.example.gravity.chess.pieces.Queen;
import com.example.gravity.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessView extends View {

    public static final int OFFSET = 60;
    public static final int BORDER_WIDTH = 8;
    private boolean opponentIsAI;
    private boolean playAsWhite;
    private boolean touched = false;
    private boolean secondTouched = false;
    private boolean newBoard = true;
    private boolean greyOutConfirmMoveButton = true;
    private int canvasWidth;
    private int squareSize;
    private int boardSize;
    private float xTouch, yTouch;
    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();
    private Bitmap backgroundImage;
    private Board chessBoard;
    private ChessSquare selectedSquare;
    private ChessSquare secondSelectedSquare;
    private SquareBounds confirmMoveButtonBounds = new SquareBounds();
    private List<ChessSquare> promotionPieces = new ArrayList<>();
    private ChessMove promotionMove = new ChessMove();
    private GameState gameState = GameState.Normal;
    private Context context;

    public ChessView(Context context) {
        super(context);
        this.context = context;
        assignSettings();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setupBoardIfNewBoard();
        canvas.drawBitmap(backgroundImage, 0, 0, null);
        drawBoardBorder(canvas);
        chessBoard.drawBoard(context, canvas);
        highlightTouchedChessSquares(canvas);
        drawConfirmMoveButton(canvas);
        chessBoard.drawAllChessPieces(canvas);
        drawPromotionMenuIfPromotionState(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        xTouch = event.getX();
        yTouch = event.getY();
        if (gameState == GameState.Normal && event.getAction() == MotionEvent.ACTION_DOWN){
            ChessSquare touchedSquare = getTouchedSquare();
            if (touchedSquare != null) {
                setTouchedAttrsIfValidConditions(touchedSquare);
                setSecondTouchedAttrsIfValidConditions(touchedSquare);
                invalidate();
            }
            if(secondTouched && confirmMoveButtonBounds.squareContainsCoordinates(confirmMoveButtonBounds, xTouch, yTouch)) {
                executeMoveAndSetAttrsIfValidConditions();
                makeAIMoveIfOpponentIsAIAndGameStateNormal();
                invalidate();
            }
        }
        if (gameState == GameState.PromotionMenu && event.getAction() == MotionEvent.ACTION_DOWN) {
            ChessSquare promotionSquare = getTouchedPromotionSquare();
            if (promotionSquare != null) {
                promoteSquare(promotionSquare);
                gameState = GameState.Normal;
                makeAIMoveIfOpponentIsAIAndGameStateNormal();
                invalidate();
            }
        }
        return true;
    }

    private void setupBoardIfNewBoard() {
        if (newBoard) {
            canvasWidth = getWidth();
            boardSize = canvasWidth - 2*OFFSET - 2*BORDER_WIDTH;
            squareSize = boardSize/8;
            chessBoard = new Board(this, playAsWhite);
            chessBoard.initialisePieceBitmaps(context);
            chessBoard.resizePieceBitmaps();
            if (!playAsWhite) {
                makeAIMoveIfOpponentIsAIAndGameStateNormal();
            }
            newBoard = false;
        }
    }

    private void assignSettings() {
        Bundle chessBundle = ((Gravitactivity) context).getIntent().getExtras();
        if (chessBundle != null) {
            opponentIsAI = chessBundle.getString(String.valueOf(R.id.opponent_spinner)).equals("Computer");
            playAsWhite = chessBundle.getString(String.valueOf(R.id.player_colour_spinner)).equals("White");
            if (chessBundle.getString(String.valueOf(R.id.computer_level_spinner)).equals("Easy")) {
            }
            else {
            }
            if (chessBundle.getString(String.valueOf(R.id.background_spinner)).equals("Totodile")) {
                backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);
            }
            else {
                backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.fox_shine);
            }
        }
    }

    private void promoteSquare(ChessSquare promotionSquare) {
        ChessSquare lastMoveSquareTo = chessBoard.getLastMove().getSquareTo();
        ChessSquare squareToPromote = chessBoard.getChessSquare(lastMoveSquareTo.getXCoordinate(), lastMoveSquareTo.getYCoordinate());
        squareToPromote.setPiece(promotionSquare.getPiece());
        squareToPromote.getPiece().resizePieceImage(squareSize);
        squareToPromote.getPiece().setParentSquare(squareToPromote);
        squareToPromote.getPiece().setHasMoved();
    }

    private ChessSquare getTouchedPromotionSquare() {
        ChessSquare touchedSquare = null;
        for (ChessSquare promotionSquare : promotionPieces) {
            if (promotionSquare.getBoundary().squareContainsCoordinates(promotionSquare.getBoundary(), xTouch, yTouch)) {
                touchedSquare = promotionSquare;
            }
        }
        return touchedSquare;
    }

    private void executeMoveAndSetAttrsIfValidConditions() {
        ChessMove chessMoveStore = new ChessMove(new ChessSquare(selectedSquare), new ChessSquare(secondSelectedSquare));
        ChessMove confirmedMove = new ChessMove(selectedSquare, secondSelectedSquare);
        chessBoard.storeMove(chessMoveStore);
        chessBoard.executeMove(confirmedMove);
        chessBoard.removePawnIfEnPassant();
        chessBoard.moveRookIfCastle();
        promotionMove = chessBoard.checkForPromotion();
        if (promotionMove != null) {
            gameState = GameState.PromotionMenu;
        }
        chessBoard.changeTurn();
        touched = false;
        secondTouched = false;
        greyOutConfirmMoveButton = true;
    }

    private void drawBoardBorder(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.chessBrown));
        mPaint.setStrokeWidth(BORDER_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        mRect.set(OFFSET + BORDER_WIDTH/2, OFFSET + BORDER_WIDTH/2, canvasWidth - OFFSET - BORDER_WIDTH/2, canvasWidth - OFFSET - BORDER_WIDTH/2);
        canvas.drawRect(mRect, mPaint);
    }

    private void drawPromotionMenuIfPromotionState(Canvas canvas) {
        if (gameState == GameState.PromotionMenu) {
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
        if (greyOutConfirmMoveButton) {
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

    private void highlightTouchedChessSquares(Canvas canvas) {
        if (touched) {
            highlightChessSquare(canvas, selectedSquare);
        }
        if (secondTouched) {
            highlightChessSquare(canvas, secondSelectedSquare);
            greyOutConfirmMoveButton = false;
        }
    }

    private void highlightChessSquare(Canvas canvas, ChessSquare squareToHighlight) {
        mRect.set(squareToHighlight.getBoundary().getLeft(), squareToHighlight.getBoundary().getTop(), squareToHighlight.getBoundary().getRight(), squareToHighlight.getBoundary().getBottom());
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.selectedSquareColour));
        canvas.drawRect(mRect, mPaint);
    }

    private void setSecondTouchedAttrsIfValidConditions(ChessSquare touchedSquare) {
        if(touched) {
            selectedSquare.getPiece().setParentSquare(selectedSquare);
            boolean legalMove = false;
            for (ChessSquare legalSquare : selectedSquare.getPiece().getLegalMoves(chessBoard)) {
                if (touchedSquare == legalSquare) {
                    legalMove = true;
                    break;
                }
            }
            if (legalMove) {
                secondSelectedSquare = touchedSquare;
                secondTouched = true;
            }
        }
    }

    private void setTouchedAttrsIfValidConditions(ChessSquare touchedSquare) {
        if (touchedSquare.getPiece().getId() != ChessPieceId.NoPiece && touchedSquare.getPiece().getColour() == chessBoard.getTurnToPlay()) {
            selectedSquare = touchedSquare;
            secondTouched = false;
            touched = true;
            greyOutConfirmMoveButton = true;
        }
    }

    private ChessSquare getTouchedSquare() {
        ChessSquare touchedSquare = null;
        for (ChessSquare chessSquare : chessBoard.getBoardSquares()) {
            if (chessSquare.getBoundary().squareContainsCoordinates(chessSquare.getBoundary(), xTouch, yTouch)) {
                touchedSquare = chessSquare;
            }
        }
        return touchedSquare;
    }

    private void makeAIMoveIfOpponentIsAIAndGameStateNormal() {
        if (opponentIsAI && gameState == GameState.Normal) {
            chessBoard.calculateAndExecuteAIMove();
            chessBoard.changeTurn();
        }
    }

    public int getSquareSize() {
        return this.squareSize;
    }

}
