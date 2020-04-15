package com.example.gravity.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.example.gravity.R;
import com.example.gravity.chess.pieces.Bishop;
import com.example.gravity.chess.pieces.Empty;
import com.example.gravity.chess.pieces.King;
import com.example.gravity.chess.pieces.Knight;
import com.example.gravity.chess.pieces.Pawn;
import com.example.gravity.chess.pieces.Queen;
import com.example.gravity.chess.pieces.Rook;

import java.util.ArrayList;
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
    private SquareBounds squareBounds = new SquareBounds();
    private ChessSquare mSquare;
    public static List<ChessSquare> allChessSquares = new ArrayList<>(); // dont think this should be static. pretty sure
    private String squareBoardLocation;
    boolean touched = false;
    float xTouch, yTouch;
    private Rect selectRect = new Rect();
    private ChessSquare selectedSquare;
    boolean secondTouched = false;
    private ChessSquare secondSelectedSquare;
    boolean greyedOut = true;
    private SquareBounds confirmMoveButtonBounds = new SquareBounds();
    boolean boardSetup = true;
    //private Map<String, Bitmap> chessPieceImages = new HashMap<>();
    private Bitmap whitePawnImage;
    private Bitmap whiteRookImage;
    private Bitmap whiteKnightImage;
    private Bitmap whiteBishopImage;
    private Bitmap whiteKingImage;
    private Bitmap whiteQueenImage;
    private Bitmap blackPawnImage;
    private Bitmap blackRookImage;
    private Bitmap blackKnightImage;
    private Bitmap blackBishopImage;
    private Bitmap blackKingImage;
    private Bitmap blackQueenImage;
    int canvasWidth;
    int canvasHeight;
    int squareSize;
    private Canvas canvas;


    public ChessView(Context context) {
        super(context);
        totodileBackground = BitmapFactory.decodeResource(getResources(), R.drawable.totodile);
        blackPawnImage = BitmapFactory.decodeResource(getResources(), R.drawable.pawn);
        blackRookImage = BitmapFactory.decodeResource(getResources(), R.drawable.rook);
        blackKnightImage = BitmapFactory.decodeResource(getResources(), R.drawable.knight);
        blackBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.bishop);
        blackKingImage = BitmapFactory.decodeResource(getResources(), R.drawable.king);
        blackQueenImage = BitmapFactory.decodeResource(getResources(), R.drawable.queen);
        whitePawnImage = BitmapFactory.decodeResource(getResources(), R.drawable.white_pawn);
        whiteRookImage = BitmapFactory.decodeResource(getResources(), R.drawable.white_rook);
        whiteKnightImage = BitmapFactory.decodeResource(getResources(), R.drawable.white_knight);
        whiteBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.white_bishop);
        whiteKingImage = BitmapFactory.decodeResource(getResources(), R.drawable.white_king);
        whiteQueenImage = BitmapFactory.decodeResource(getResources(), R.drawable.white_queen);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        canvas.drawBitmap(totodileBackground, 0, 0, null);
        canvasWidth = getWidth();
        canvasHeight = getHeight();
        int boardSize = canvasWidth - 2*OFFSET - 2*BORDER_WIDTH;
        squareSize = boardSize/8;
        if (boardSetup) {
            initialisePieceBitmaps();
        }

        mPaint.setColor(getResources().getColor(R.color.chessBrown));
        mPaint.setStrokeWidth(BORDER_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);


        mRect.set(OFFSET + BORDER_WIDTH/2, OFFSET + BORDER_WIDTH/2, canvasWidth - OFFSET - BORDER_WIDTH/2, canvasWidth - OFFSET - BORDER_WIDTH/2);
        canvas.drawRect(mRect, mPaint);
        boolean fillSquare = true;
        for (int i = 0; i < 8 ; i++){
            for (int j = 0; j < 8; j++){
                int squareLeft = OFFSET + BORDER_WIDTH + i*squareSize;
                int squareRight = OFFSET + BORDER_WIDTH + (i + 1)*squareSize;
                int squareTop = OFFSET + BORDER_WIDTH + j*squareSize;
                int squareBottom = OFFSET + BORDER_WIDTH + (j + 1)*squareSize;
                if (boardSetup){
                    squareBounds = new SquareBounds(squareLeft, squareTop, squareRight, squareBottom);
                    squareBoardLocation = getSquareBoardLocation(i, j);
                    //ChessPieceId pieceId = getChessPieceIdFromBoardLocation(squareBoardLocation);
                    ChessPiece piece = getChessPieceFromBoardLocation(squareBoardLocation);
                    mSquare = new ChessSquare(squareBounds, piece, squareBoardLocation);
                    allChessSquares.add(mSquare);
                }
                if (fillSquare){

                    mRect.set(squareLeft, squareTop, squareRight, squareBottom);
                    mPaint.setStyle(Paint.Style.FILL);
                    mPaint.setColor(getResources().getColor(R.color.chessBrown));
                    mPaint.setAlpha(140);
                    canvas.drawRect(mRect, mPaint);

                    fillSquare = false;
                } else {
                    fillSquare = true;
                }
            }
            fillSquare = !fillSquare;
        }
        boardSetup = false;
        if (touched) {
            highlightChessSquare(canvas, selectedSquare);
        }
        if (secondTouched) {
            highlightChessSquare(canvas, secondSelectedSquare);
            greyedOut = false;
        }

        /*
        Pawn pawn = new Pawn();
        ChessSquare hi = new ChessSquare();
        hi.setPiece(pawn);
        */

        drawConfirmMoveButton(canvas);

        drawAllChessPieces(canvas, ChessView.allChessSquares);

    }

    private LinkedList<ChessSquare> assembleSquares() {
        boolean fillSquare = true;
        for (int i = 0; i < 8 ; i++){
            for (int j = 0; j < 8; j++){
                int squareLeft = OFFSET + BORDER_WIDTH + i*squareSize;
                int squareRight = OFFSET + BORDER_WIDTH + (i + 1)*squareSize;
                int squareTop = OFFSET + BORDER_WIDTH + j*squareSize;
                int squareBottom = OFFSET + BORDER_WIDTH + (j + 1)*squareSize;
                if (boardSetup){
                    squareBounds = new SquareBounds(squareLeft, squareTop, squareRight, squareBottom);
                    squareBoardLocation = getSquareBoardLocation(i, j);
                    ChessPiece piece = getChessPieceFromBoardLocation(squareBoardLocation);
                    mSquare = new ChessSquare(squareBounds, piece, squareBoardLocation);
                    allChessSquares.add(mSquare);
                }
                if (fillSquare){

                    mRect.set(squareLeft, squareTop, squareRight, squareBottom);
                    mPaint.setStyle(Paint.Style.FILL);
                    mPaint.setColor(getResources().getColor(R.color.chessBrown));
                    mPaint.setAlpha(140);
                    canvas.drawRect(mRect, mPaint);

                    fillSquare = false;
                } else {
                    fillSquare = true;
                }
            }
            fillSquare = !fillSquare;
        }
    }

    private void drawAllChessPieces(Canvas canvas, List<ChessSquare> chessSquares) {
        for (ChessSquare chessSquare : chessSquares){
            if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece){
                chessSquare.getPiece().drawPiece(canvas);
            }
        }
    }

    private void initialisePieceBitmaps() {
        whitePawnImage = Bitmap.createScaledBitmap(whitePawnImage, squareSize, squareSize, true);
        whiteRookImage = Bitmap.createScaledBitmap(whiteRookImage, squareSize, squareSize, true);
        whiteKnightImage = Bitmap.createScaledBitmap(whiteKnightImage, squareSize, squareSize, true);
        whiteBishopImage = Bitmap.createScaledBitmap(whiteBishopImage, squareSize, squareSize, true);
        whiteKingImage = Bitmap.createScaledBitmap(whiteKingImage, squareSize, squareSize, true);
        whiteQueenImage = Bitmap.createScaledBitmap(whiteQueenImage, squareSize, squareSize, true);
        blackPawnImage = Bitmap.createScaledBitmap(blackPawnImage, squareSize, squareSize, true);
        blackRookImage = Bitmap.createScaledBitmap(blackRookImage, squareSize, squareSize, true);
        blackKnightImage = Bitmap.createScaledBitmap(blackKnightImage, squareSize, squareSize, true);
        blackBishopImage = Bitmap.createScaledBitmap(blackBishopImage, squareSize, squareSize, true);
        blackKingImage = Bitmap.createScaledBitmap(blackKingImage, squareSize, squareSize, true);
        blackQueenImage = Bitmap.createScaledBitmap(blackQueenImage, squareSize, squareSize, true);
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

    /*
    private ChessPieceId getChessPieceIDFromBoardLocation(String squareBoardLocation) {
        ChessPieceId pieceId = null;
        if (squareBoardLocation.charAt(1) == '2') {
            pieceId = ChessPieceId.WhitePawn;
        }
        else if(squareBoardLocation.charAt(1) == '7') {
            pieceId = ChessPieceId.BlackPawn;
        }
        else if(squareBoardLocation.charAt(1) == '1') {
            if (squareBoardLocation.charAt(0) == 'a' || squareBoardLocation.charAt(0) == 'h') {
                pieceId = ChessPieceId.WhiteRook;
            }
            if (squareBoardLocation.charAt(0) == 'b' || squareBoardLocation.charAt(0) == 'g') {
                pieceId = ChessPieceId.WhiteKnight;
            }
            if (squareBoardLocation.charAt(0) == 'c' || squareBoardLocation.charAt(0) == 'f') {
                pieceId = ChessPieceId.WhiteBishop;
            }
            if (squareBoardLocation.charAt(0) == 'd') {
                pieceId = ChessPieceId.WhiteQueen;
            }
            if (squareBoardLocation.charAt(0) == 'e') {
                pieceId = ChessPieceId.WhiteKing;
            }
        }
        else if(squareBoardLocation.charAt(1) == '8') {
            if (squareBoardLocation.charAt(0) == 'a' || squareBoardLocation.charAt(0) == 'h') {
                pieceId = ChessPieceId.BlackRook;
            }
            if (squareBoardLocation.charAt(0) == 'b' || squareBoardLocation.charAt(0) == 'g') {
                pieceId = ChessPieceId.BlackKnight;
            }
            if (squareBoardLocation.charAt(0) == 'c' || squareBoardLocation.charAt(0) == 'f') {
                pieceId = ChessPieceId.BlackBishop;
            }
            if (squareBoardLocation.charAt(0) == 'd') {
                pieceId = ChessPieceId.BlackQueen;
            }
            if (squareBoardLocation.charAt(0) == 'e') {
                pieceId = ChessPieceId.BlackKing;
            }
        }
        else {
            pieceId = ChessPieceId.NoPiece;
        }
        return pieceId;
    }
    */

    private ChessPiece getChessPieceFromBoardLocation(String squareBoardLocation) {
        ChessPiece piece = null;
        if (squareBoardLocation.charAt(1) == '2' || squareBoardLocation.charAt(1) == '7') {
            piece = new Pawn();
        }
        else if(squareBoardLocation.charAt(1) == '1' || squareBoardLocation.charAt(1) == '8') {
            if (squareBoardLocation.charAt(0) == 'a' || squareBoardLocation.charAt(0) == 'h') {
                piece = new Rook();
            }
            if (squareBoardLocation.charAt(0) == 'b' || squareBoardLocation.charAt(0) == 'g') {
                piece = new Knight();
            }
            if (squareBoardLocation.charAt(0) == 'c' || squareBoardLocation.charAt(0) == 'f') {
                piece = new Bishop();
            }
            if (squareBoardLocation.charAt(0) == 'd') {
                piece = new Queen();
            }
            if (squareBoardLocation.charAt(0) == 'e') {
                piece = new King();
            }
        }
        else {
            piece = new Empty();
        }
        if (squareBoardLocation.charAt(1) == '1' || squareBoardLocation.charAt(1) == '2'){
            assert piece != null;
            piece.setColour(PieceColour.White);
        }
        else if (squareBoardLocation.charAt(1) == '7' || squareBoardLocation.charAt(1) == '8') {
            assert piece != null;
            piece.setColour(PieceColour.White);
        }
        return piece;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            xTouch = event.getX();
            yTouch = event.getY();
            for (ChessSquare chessSquare : ChessView.allChessSquares) {
                if (chessSquare.getBoundary().squareContainsCoordinates(chessSquare.getBoundary(), xTouch, yTouch)){
                    if (chessSquare.getPiece().getId() != ChessPieceId.NoPiece) {
                        selectedSquare = chessSquare;
                        secondTouched = false;
                        touched = true;
                    }
                    if(touched && chessSquare.getPiece().getId() == ChessPieceId.NoPiece) {
                        secondSelectedSquare = chessSquare;
                        secondTouched = true;
                    }
                    invalidate();
                }
            }
            if(secondTouched && confirmMoveButtonBounds.squareContainsCoordinates(confirmMoveButtonBounds, xTouch, yTouch)) {
                secondSelectedSquare.setPiece(selectedSquare.getPiece());
                selectedSquare.setPiece(new Empty());
                touched = false;
                secondTouched = false;
                greyedOut = true;
                invalidate();
            }
        }

        return true;
    }

    private String getSquareBoardLocation(int xNum, int yNum) {
        String loc;
        switch(xNum) {
            case 0:
                loc = "a" + Integer.toString(8 - yNum);
                break;
            case 1:
                loc = "b" + Integer.toString(8 - yNum);
                break;
            case 2:
                loc = "c" + Integer.toString(8 - yNum);
                break;
            case 3:
                loc = "d" + Integer.toString(8 - yNum);
                break;
            case 4:
                loc = "e" + Integer.toString(8 - yNum);
                break;
            case 5:
                loc = "f" + Integer.toString(8 - yNum);
                break;
            case 6:
                loc = "g" + Integer.toString(8 - yNum);
                break;
            case 7:
                loc = "h" + Integer.toString(8 - yNum);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + xNum);
        }
        return loc;
    }
}
