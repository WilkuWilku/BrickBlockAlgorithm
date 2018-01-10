package mainPackage;

import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Inf on 2017-12-25.
 */
public class Node {
    private static final int SEARCH_AREA_WIDTH = 12;
    private static final int SEARCH_AREA_HEIGHT = 12;
    private BrickBlock move;
    private BoardState board;
    private BoardAnalyzer analyzer;
    private ArrayList<Node> children;
    private int level;
    private MovesData nodeMovesData;
    private ControlState nodeControl; // stan kontroli nowych bloków po wykonaniu ruchu z węzła (dla level+1)



    public Node(BrickBlock move, BoardState board, int level, MovesData nodeMovesData, ControlState nodeControl) {
        this.level = level;
        this.move = move;
        this.nodeMovesData = nodeMovesData;
        this.board = new BoardState(board);
        this.nodeControl = nodeControl;
        analyzer = new BoardAnalyzer(this.board);
    }

    /* Node jako korzeń */
    private Node(BoardState board, MovesData nodeMovesData, ControlState mainControl){
        this(null, board, -1, nodeMovesData, mainControl);
    }

    public static Node createRootNode(BoardState board, MovesData movesData, ControlState controlState){
        return new Node(board, movesData, controlState );
    }

    public ArrayList<Node> createChildren(){
        this.children = new ArrayList<>();
        HashSet<BrickBlock> movesSet = BoardAnalyzer.mapValuesToSet(nodeMovesData.getMovesMap());
        for(BrickBlock move : movesSet){
            BoardState childBoard = new BoardState(this.board);
            move.markOnBoard(childBoard);
            BlocksData childBlocksData = BlockFinder.searchForBlocksInArea(childBoard, move.getReferenceCellIndex(), SEARCH_AREA_WIDTH, SEARCH_AREA_HEIGHT);           //nowe bloki znalezione po wykonaniu ruchu move
            BoardState childBoardWithoutBlocks = childBoard.getBoardWithoutBlocks(childBlocksData);
            BoardAnalyzer childBoardAnalyzer = new BoardAnalyzer(childBoardWithoutBlocks);
            MovesData childMovesData = childBoardAnalyzer.findAllMoves();
            Node childMove = new Node(move, childBoardWithoutBlocks, level+1, childMovesData,
                    childBlocksData.checkControlState());
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

    public int getLevel() {
        return level;
    }

    public BoardAnalyzer getAnalyzer() {
        return analyzer;
    }

    public ControlState getNodeControl() {
        return nodeControl;
    }
}
