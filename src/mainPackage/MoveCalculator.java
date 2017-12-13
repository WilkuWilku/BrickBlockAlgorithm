package mainPackage;

import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Inf on 2017-12-13.
 */
public final class MoveCalculator {
    private static final int ENEMY = 1;
    private static final int AI = 0;
    //private BoardStatistics stats;
    //private BoardAnalyzer analyzer;

    private MoveCalculator(){
        //this.stats = stats;
        //this.analyzer = analyzer;
    }

    public static BrickBlock nextMove(BoardAnalyzer analyzer){
        //TODO poprawić funkcję obliczającą ruchy
        BoardStatistics stats = analyzer.getStats();
        int n1WithoutBrickBlocks = stats.n[1]-stats.nMR1*2;                 //liczba komórek z MR==1, które nie należą do BrickBlocka z MR==1
        GameState gameState = checkGameState(stats, n1WithoutBrickBlocks, stats.n[2], stats.n[3], stats.n[4], AI);
        //HashMap<Integer, Duo<BrickBlock>> resultMap;
        HashSet<BrickBlock> moves = BoardAnalyzer.mapValuesToSet(stats.getMovesMap());
        BoardStatistics resultStats;
        ArrayList<BrickBlock> winningMoves = new ArrayList<>();
        ArrayList<BrickBlock> changingMoves = new ArrayList<>();
        ArrayList<BrickBlock> otherMoves = new ArrayList<>();
        for(BrickBlock move : moves){
            BoardState boardAfterMove = analyzer.getBoard().copyBoard();
            BoardAnalyzer newAnalyzer = new BoardAnalyzer(boardAfterMove);
            boardAfterMove.addBrick(move, newAnalyzer);
            analyzer.findAllMoves();
            resultStats = analyzer.getStats();
            int n1WithoutBrickBlocksInResult = resultStats.n[1] - resultStats.nMR1*2;
            GameState gameStateOfResult = checkGameState(stats, n1WithoutBrickBlocksInResult, resultStats.n[2], resultStats.n[3], resultStats.n[4], ENEMY);
            move.setResult(gameStateOfResult);
            switch (gameStateOfResult){
                case WIN: winningMoves.add(move); break;
                case CHANGEABLE: changingMoves.add(move); break;
                case UNDEFINED: otherMoves.add(move); break;
            }
        }
        if(winningMoves.size()!=0)
            return winningMoves.get(0);
        else if(gameState == GameState.CHANGEABLE && changingMoves.size() != 0)
            return changingMoves.get(0);
        else if(gameState == GameState.UNDEFINED && otherMoves.size() != 0){
            for (BrickBlock block : otherMoves){
                if(block.getMovesReduction() == 7)
                    return block;
                if(block.getMovesReduction() == 6)
                    return block;
                if(block.getMovesReduction() == 5)
                    return block;
                if(block.getMovesReduction() == 4)
                    return block;
                return block;
            }
        }
        return null;
    }

    /* whoPlays = z czyjego punktu widzenia ma być obliczany stan planszy (AI/ENEMY) */
    private static GameState checkGameState(BoardStatistics stats, int n1WithoutBrickBlocks, int n2, int n3, int n4, int whoPlays){
        int n2without212s = n2-n1WithoutBrickBlocks/2;        //liczba dwójek po usunięciu bloków 121 (MR==1)
        int certainMovesLeft = stats.nMR1 + n1WithoutBrickBlocks/2;
        if(n4>0 || n3>0)
            //TODO kontrola n3 i n4
            return GameState.UNDEFINED;
        else if(n2without212s==2 || n2without212s > 3)
            return GameState.CHANGEABLE;
        if((certainMovesLeft % 2 == (0 + whoPlays) && n2without212s == 1) || (certainMovesLeft % 2 == (1 - whoPlays) && n2without212s == 3))
            return GameState.WIN;
        else
            return GameState.LOSE;
    }

}
