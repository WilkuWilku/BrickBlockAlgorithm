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
        analyzer.findAllMoves();
        GameState gameState = checkGameState(stats, stats.n[2], stats.n[3], stats.n[4]);
        HashSet<BrickBlock> moves = BoardAnalyzer.mapValuesToSet(stats.getMovesMap());
        BoardStatistics resultStats;
        ArrayList<BrickBlock> enemyLosingMoves = new ArrayList<>();
        ArrayList<BrickBlock> enemyChangingMoves = new ArrayList<>();
        ArrayList<BrickBlock> enemyOtherMoves = new ArrayList<>();
        for(BrickBlock move : moves){
            BoardState boardAfterMove = analyzer.getBoard().copyBoard();
            BoardAnalyzer newAnalyzer = new BoardAnalyzer(boardAfterMove);
            boardAfterMove.addBrick(move);
            newAnalyzer.findAllMoves();
            resultStats = newAnalyzer.getStats();
            GameState gameStateOfResult = checkGameState(resultStats, resultStats.n[2], resultStats.n[3], resultStats.n[4]);
            switch (gameStateOfResult){
                case LOSE: enemyLosingMoves.add(move); break;
                case CHANGEABLE: enemyChangingMoves.add(move); break;
                case UNDEFINED: enemyOtherMoves.add(move); break;
            }
        }
        System.out.println("Stan gry dla programu: "+gameState.name());
        if(gameState == GameState.UNDEFINED && enemyOtherMoves.size() > 0){
            for (BrickBlock block : enemyOtherMoves){
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
        else if(enemyLosingMoves.size() > 0)
            return enemyLosingMoves.get(0);
        else if(gameState == GameState.CHANGEABLE && enemyChangingMoves.size() > 0)
            return enemyChangingMoves.get(0);
        else if(enemyOtherMoves.size() > 0)
            return enemyChangingMoves.get(0);
        return null;
    }

    /* whoPlays = z czyjego punktu widzenia ma być obliczany stan planszy (AI/ENEMY) */
    private static GameState checkGameState(BoardStatistics stats, int n2, int n3, int n4){
        int n1WithoutBrickBlocks = stats.n[1]-stats.nMR1*2;                 //liczba komórek z MR==1, które nie należą do BrickBlocka z MR==1
        int n2without212s = n2-n1WithoutBrickBlocks/2;        //liczba dwójek po usunięciu bloków 121 (MR==1)
        int certainMovesLeft = stats.nMR1 + n1WithoutBrickBlocks/2;
        if(n4>0 || n3>0)
            //TODO kontrola n3 i n4
            return GameState.UNDEFINED;
        else if(n2without212s==2 || n2without212s > 3)
            return GameState.CHANGEABLE;
        if((certainMovesLeft % 2 == 0 && n2without212s == 1) || (certainMovesLeft % 2 == 1 && n2without212s == 3))
            return GameState.WIN;
        else
            return GameState.LOSE;
    }

}
