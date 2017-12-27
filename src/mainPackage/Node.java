package mainPackage;

import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Inf on 2017-12-25.
 */
public class Node {
    private BrickBlock move;
    private BoardState board;
    private BoardAnalyzer analyzer;
    private ArrayList<Node> children;
    private int value = 0;
    private int level;
    private BoardStatistics stats;

    public Node(BrickBlock move, BoardState board, int level, BoardStatistics stats) {
        this.level = level;
        this.move = move;
        this.stats = stats;
        this.board = new BoardState(board);
        analyzer = new BoardAnalyzer(this.board);
    }

    /* Node jako korzeń */
    private Node(BoardState board, BoardStatistics statistics){
        this(null, board, -1, statistics);
    }

    public static Node createRootNode(BoardState board, BoardStatistics statistics){
        return new Node(board, statistics);
    }

    public ArrayList<Node> createChildren(BoardStatistics statistics){
        this.children = new ArrayList<>();
        HashSet<BrickBlock> movesSet = BoardAnalyzer.mapValuesToSet(statistics.getMovesMap());
        for(BrickBlock move : movesSet){
            BoardState childBoard = new BoardState(this.board);
            move.markOnBoard(childBoard);
            BoardStatistics childStats = new BoardStatistics();
            BlockFinder.searchForBlocks(childStats, childBoard);
            Node childMove = new Node(move, childBoard, level+1, childStats);
            children.add(childMove);
        }
        return children;
    }

    public BrickBlock getMove() {
        return move;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public int getValue() {
        return value;
    }

    public int getLevel() {
        return level;
    }

    public BoardAnalyzer getAnalyzer() {
        return analyzer;
    }
}
