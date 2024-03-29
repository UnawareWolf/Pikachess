package com.example.pikachess.chess;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.pikachess.ChessActivity;
import com.example.pikachess.GameActivity;
import com.example.pikachess.R;
import com.example.pikachess.chess.pieces.Bishop;
import com.example.pikachess.chess.pieces.Empty;
import com.example.pikachess.chess.pieces.Knight;
import com.example.pikachess.chess.pieces.Pika;
import com.example.pikachess.chess.pieces.Queen;
import com.example.pikachess.chess.pieces.Rook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ChessView extends View {

    public static final int OFFSET = 60;
    public static final int BORDER_WIDTH = 8;
    private boolean touched = false;
    private boolean secondTouched = false;
    private boolean newBoard = true;
    private boolean loadBoard;
    private boolean playAsWhite;
    private boolean opponentIsAI;
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
    private SquareBounds saveGameButtonBounds = new SquareBounds();
    private List<ChessSquare> promotionPieces = new ArrayList<>();
    private ChessMove promotionMove = new ChessMove();
    private GameState gameState = GameState.Normal;

    public ChessView(Context context) {
        super(context);
        assignSettings();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setupBoardIfNewBoard();
        loadSavedBoard();
        canvas.drawBitmap(backgroundImage, 0, 0, null);
        drawBoardBorder(canvas);
        chessBoard.drawBoard(this.getContext(), canvas);
        setGameOverIfValidConditions();
        highlightLastMove(canvas);
        highlightTouchedChessSquares(canvas);
        drawConfirmMoveButton(canvas);
        drawSaveGameButton(canvas);
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
                ChessMove humanMove = new ChessMove(selectedSquare, secondSelectedSquare);
                executeMoveAndSetAttrsIfValidConditions(humanMove);
                makeAIMoveIfOpponentIsAIAndGameStateNormal();
                invalidate();
            }
            if (saveGameButtonBounds.squareContainsCoordinates(saveGameButtonBounds, xTouch, yTouch)) {
                saveBoard();
            }
        }
        if (gameState == GameState.PromotionMenu && event.getAction() == MotionEvent.ACTION_DOWN) {
            ChessSquare promotionSquare = getTouchedPromotionSquare();
            if (promotionSquare != null) {
                if (promotionSquare.getPiece().getId() == ChessPieceId.NoPiece) {
                    startGameActivityIfPikaPromotion(promotionSquare.getPiece());
                }
                else {
                    promoteSquare(promotionSquare.getPiece());
                    gameState = GameState.Normal;
                    makeAIMoveIfOpponentIsAIAndGameStateNormal();
                    invalidate();
                }
            }
        }
        return true;
    }

    private void saveBoard() {
        try {
            FileOutputStream fos = null;
            fos = this.getContext().openFileOutput("chessSave.ser", Context.MODE_PRIVATE);
            ObjectOutputStream os = null;
            os = new ObjectOutputStream(fos);
            os.writeObject(chessBoard);
            os.close();
            fos.close();
        }
        catch (Exception ignore){
        }
    }

    private void startGameActivityIfPikaPromotion(ChessPiece piece) {
        if (piece.getId() == ChessPieceId.NoPiece) {
            Intent gameIntent = new Intent(this.getContext(), GameActivity.class);
            gameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.getContext().startActivity(gameIntent);
        }
    }

    private void setupBoardIfNewBoard() {
        if (newBoard && !loadBoard) {
            canvasWidth = getWidth();
            boardSize = canvasWidth - 2*OFFSET - 2*BORDER_WIDTH;
            squareSize = boardSize/8;
            chessBoard = new Board(this, playAsWhite);
            chessBoard.initialisePieceBitmaps(this.getContext());
            chessBoard.resizePieceBitmaps();
            if (!playAsWhite) {
                makeAIMoveIfOpponentIsAIAndGameStateNormal();
            }
            chessBoard.setOpponentIsAI(opponentIsAI);
            newBoard = false;
        }
    }

    private void loadSavedBoard() {
        if (loadBoard) {
            canvasWidth = getWidth();
            boardSize = canvasWidth - 2*OFFSET - 2*BORDER_WIDTH;
            squareSize = boardSize/8;
            try {
                FileInputStream fis = this.getContext().openFileInput("chessSave.ser");
                ObjectInputStream is = new ObjectInputStream(fis);
                chessBoard = (Board) is.readObject();
                is.close();
                fis.close();
            }
            catch (Exception ignored) {
            }
            chessBoard.setOpponentIsAI(opponentIsAI);
            chessBoard.setAttsOfLoadGame(mPaint, mRect);
            chessBoard.initialisePieceBitmaps(this.getContext());
            chessBoard.resizePieceBitmaps();
            newBoard = false;
            loadBoard = false;
        }
    }

    private void assignSettings() {
        Bundle chessBundle = ((ChessActivity) this.getContext()).getIntent().getExtras();
        if (chessBundle != null) {
            opponentIsAI = chessBundle.getString(String.valueOf(R.id.opponent_spinner)).equals("Computer");
            playAsWhite = chessBundle.getString(String.valueOf(R.id.player_colour_spinner)).equals("White");
            loadBoard = !chessBundle.getString(String.valueOf(R.id.load_game_spinner)).equals("New Game");
            if (chessBundle.getString(String.valueOf(R.id.computer_level_spinner)).equals("Easy")) {
            }
            else {
            }
            switch (chessBundle.getString(String.valueOf(R.id.background_spinner))) {
                case "Totodile":
                    backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);
                    break;
                case "Shine":
                    backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.fox_shine);
                    break;
                case "Sky":
                    backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.fox_shine);
                    break;
            }
        }
    }

    private void promoteSquare(ChessPiece promotionPiece) {
        ChessSquare lastMoveSquareTo = chessBoard.getLastMove().getSquareTo();
        ChessSquare squareToPromote = chessBoard.getChessSquare(lastMoveSquareTo.getXCoordinate(), lastMoveSquareTo.getYCoordinate());
        squareToPromote.setPiece(promotionPiece);
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

    private void executeMoveAndSetAttrsIfValidConditions(ChessMove confirmedMove) {
        ChessMove chessMoveStore = new ChessMove(confirmedMove);
        chessBoard.storeMove(chessMoveStore);
        chessBoard.storeBoardPosition();
        chessBoard.executeMove(confirmedMove);
        chessBoard.removePawnIfEnPassant();
        chessBoard.moveRookIfCastle();
        promotionMove = chessBoard.checkForPromotion();
        if (promotionMove != null) {
            if (computerToPlayNext()) {
                Queen newQueen = new Queen(chessBoard.getTurnToPlay());
                newQueen.setPieceImage(this.getContext());
                promoteSquare(newQueen);
            }
            else {
                gameState = GameState.PromotionMenu;
            }
        }
        //chessBoard.storeBoardPosition();
        chessBoard.changeTurn();
        touched = false;
        secondTouched = false;
        greyOutConfirmMoveButton = true;
    }

    private boolean computerToPlayNext() {
        boolean computerTurn = false;
        if (opponentIsAI) {
            if ((chessBoard.getTurnToPlay() == PieceColour.White && !playAsWhite) || (chessBoard.getTurnToPlay() == PieceColour.Black && playAsWhite))  {
                computerTurn = true;
            }
        }
        return computerTurn;
    }

    private void makeAIMoveIfOpponentIsAIAndGameStateNormal() {
        if (opponentIsAI && gameState == GameState.Normal) {
            ChessMove computerMove = chessBoard.getBestAIMove();
            if (computerMove != null) {
                executeMoveAndSetAttrsIfValidConditions(computerMove);
            }
//            else {
//                gameState = GameState.GameOver;
//            }
        }
    }

    private void setGameOverIfValidConditions() {
        if (chessBoard.isGameCheckmate() || chessBoard.isGameDraw()) {
            gameState = GameState.GameOver;
        }
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
            double promotionMenuScaleFactor = 3/2.0;
            int menuWidth = (int) (squareSizeDub*promotionMenuScaleFactor);
            int menuHeight = menuWidth*4;
            int menuLeft = promotionMove.getSquareTo().getBoundary().getLeft();
            int squareTop = promotionMove.getSquareTo().getBoundary().getTop();
            int menuRight = menuLeft + menuWidth;
            int menuBottom = squareTop + menuHeight;
            mRect.set(menuLeft, squareTop, menuRight, menuBottom);
            mPaint.setColor(getResources().getColor(R.color.chessBrown));
            canvas.drawRect(mRect, mPaint);
            promotionPieces = new ArrayList<>();
            PieceColour colourLastMoved = chessBoard.getAllMoves().getLast().getPieceColourMoved();
            promotionPieces.add(new ChessSquare(new Queen(colourLastMoved)));
            promotionPieces.add(new ChessSquare(new Rook(colourLastMoved)));
            promotionPieces.add(new ChessSquare(new Bishop(colourLastMoved)));
            promotionPieces.add(new ChessSquare(new Knight(colourLastMoved)));
            promotionPieces.add(new ChessSquare(new Pika()));
            for (ChessSquare promotionPiece : promotionPieces) {
                promotionPiece.getPiece().setPieceImage(this.getContext());
                promotionPiece.getPiece().resizePieceImage(menuWidth);
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
        int buttonTop = canvasWidth + BORDER_WIDTH/2;
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

    private void drawSaveGameButton(Canvas canvas) {
        int buttonWidth = canvasWidth/4;
        int buttonHeight = buttonWidth/3;
        int buttonLeft = OFFSET + BORDER_WIDTH/2;
        int buttonTop = canvasWidth + OFFSET + BORDER_WIDTH/2 + buttonHeight;
        int buttonRight = buttonLeft + buttonWidth;
        int buttonBottom = buttonTop + buttonHeight;
        saveGameButtonBounds.set(buttonLeft, buttonTop, buttonRight, buttonBottom);
        mRect.set(buttonLeft, buttonTop, buttonRight, buttonBottom);
        mPaint.setColor(getResources().getColor(R.color.buttonGrey));
        mPaint.setAlpha(255);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mRect, mPaint);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(BORDER_WIDTH);
        canvas.drawRect(mRect, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(66 - 2*BORDER_WIDTH);
        canvas.drawText("Save Game", buttonLeft + BORDER_WIDTH, buttonBottom - 2*BORDER_WIDTH, mPaint);
    }

    private void highlightTouchedChessSquares(Canvas canvas) {
        if (touched && gameState == GameState.Normal) {
            highlightChessSquare(canvas, selectedSquare);
        }
        if (secondTouched && gameState == GameState.Normal) {
            highlightChessSquare(canvas, secondSelectedSquare);
            greyOutConfirmMoveButton = false;
        }
    }

    private void highlightLastMove(Canvas canvas) {
        if (chessBoard.getLastMove() != null) {
            highlightChessSquare(canvas, chessBoard.getLastMove().getSquareTo(), R.color.lastMove);
        }
    }

    private void highlightChessSquare(Canvas canvas, ChessSquare squareToHighlight) {
        highlightChessSquare(canvas, squareToHighlight, R.color.selectedSquareColour);
    }

    private void highlightChessSquare(Canvas canvas, ChessSquare squareToHighlight, int colourId) {
        mRect.set(squareToHighlight.getBoundary().getLeft(), squareToHighlight.getBoundary().getTop(), squareToHighlight.getBoundary().getRight(), squareToHighlight.getBoundary().getBottom());
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(colourId));
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

    public int getSquareSize() {
        return this.squareSize;
    }

}
