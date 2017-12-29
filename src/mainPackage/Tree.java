package mainPackage;

import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.logging.Logger;

/**
 * Created by Inf on 2017-12-25.
 */
public class Tree {

    private BoardState board;
    private Node root;
    private BlocksData blocksData;
    private ControlState controlState;
    private MovesData movesData;

    public Tree(BoardState board){
        blocksData = BlockFinder.searchForBlocks(board);
        this.board = board.getBoardWithoutBlocks(blocksData);
        BoardAnalyzer analyzer = new BoardAnalyzer(board);
        movesData = analyzer.findAllMoves();
        controlState = blocksData.checkControlState();
        root = Node.createRootNode(this.board, movesData, controlState);
        root.createChildren();
    }

    public void growTree(int maxLevel){
        growTree(maxLevel, root);
    }

    private void growTree(int maxLevel, Node currentNode) {
        if (currentNode.getLevel() < maxLevel) {
            if(currentNode.getChildren() == null) {
                currentNode.createChildren();
            }
            for (int i = 0; i < currentNode.getChildren().size(); i++)
                growTree(maxLevel, currentNode.getChildren().get(i));

        }
    }

    public BrickBlock getMatchingMove(){
        //TODO uwzględnić leadingState od obliczeń
        if (root.getNodeControl() == ControlState.EVEN) {
            for (Node child : root.getChildren()) {
                if (child.getChildren().size() == 0 && child.getNodeControl() == ControlState.EVEN) {
                    System.out.println("EVEN -> EVEN -> x");
                    return child.getMove();
                }
                else if (child.getNodeControl() == ControlState.ODD) {
                    boolean isGrandchildMatching = true;
                    for (Node grandchild : child.getChildren()) {
                        if (!(grandchild.getChildren().size() == 0 && grandchild.getNodeControl() == ControlState.EVEN))
                            isGrandchildMatching = false;
                    }
                    if (isGrandchildMatching) {
                        System.out.println("EVEN -> ODD -> EVENs -> x");
                        return child.getMove();
                    }
                }
            }
            System.out.println("EVEN -> emergency");
            if(blocksData.getBlocksType1().size() > 0)
                return blocksData.getBlocksType1().get(0).nextMove(board);
            if(blocksData.getBlocksType2().size() > 0)
                return blocksData.getBlocksType2().get(0).nextMove(board);
            if(blocksData.getBlocksType2or1().size() > 0)
                return blocksData.getBlocksType2or1().get(0).leaveZeroMoves(board);
        }
        else if(root.getNodeControl() == ControlState.ODD){
            for (Node child : root.getChildren()) {
                if (child.getChildren().size() == 0 && child.getNodeControl() == ControlState.ODD) {
                    System.out.println("ODD -> ODD -> x");
                    return child.getMove();
                }
                else if (child.getNodeControl() == ControlState.EVEN) {
                    boolean isGrandchildMatching = true;
                    for (Node grandchild : child.getChildren()) {
                        if (!(grandchild.getChildren().size() == 0 && grandchild.getNodeControl() == ControlState.ODD))
                            isGrandchildMatching = false;
                    }
                    if (isGrandchildMatching) {
                        System.out.println("ODD -> EVEN -> ODDs -> x");
                        return child.getMove();
                    }
                }
            }
            System.out.println("ODD -> emergency");
            if(blocksData.getBlocksType2().size() > 0)
                return blocksData.getBlocksType2().get(0).nextMove(board);
            if(blocksData.getBlocksType1().size() > 0)
                return blocksData.getBlocksType1().get(0).nextMove(board);
            if(blocksData.getBlocksType2or1().size() > 0)
                return blocksData.getBlocksType2or1().get(0).leaveOneMove(board);
        }
            /* weź cokolwiek jeśli nic już nie zostało */
        System.out.println("->> NO HOPE");
            return BoardAnalyzer.mapValuesToSet(movesData.getMovesMap()).iterator().next();
    }

    public BlocksData getBlocksData() {
        return blocksData;
    }

    public MovesData getMovesData() {
        return movesData;
    }
}
