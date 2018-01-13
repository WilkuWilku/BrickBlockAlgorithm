package mainPackage;

import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-12-25.
 */
public class Tree {
    private static final long GROW_TREE_TIME_LIMIT = 400;             //maksymalny czas generowania drzewa (w milisekundach)
    private static final long GET_MOVE_TIME_LIMIT = 460;              //maksymalny czas znalezienia ruchu (w milisekundach)
    private BoardState board;
    private Node root;
    private BlocksData blocksData;
    private ControlState controlState;
    private MovesData movesData;

    public Tree(BoardState boardWithoutBlocks, BlocksData blocksData, MovesData movesData){
        this.board = boardWithoutBlocks;
        this.blocksData = blocksData;
        this.movesData = movesData;
        controlState = blocksData.checkControlState();
        root = Node.createRootNode(this.board, movesData, controlState);
        root.createChildren();
    }

    public void growTree(int maxLevel, long initTime) {
        growTree(maxLevel, root, initTime);
    }

    private boolean growTree(int maxLevel, Node currentNode, long initTime) {
        long delta;
        if((delta = System.currentTimeMillis() - initTime) > GROW_TREE_TIME_LIMIT) {
            System.err.println("Waited too long ["+delta+" /LIMIT="+GROW_TREE_TIME_LIMIT+"]");
            return false;
        }
        if (currentNode.getLevel() < maxLevel) {
            if(currentNode.getChildren() == null) {
                currentNode.createChildren();
            }
            boolean success;
            for (int i = 0; i < currentNode.getChildren().size(); i++) {
                success = growTree(maxLevel, currentNode.getChildren().get(i), initTime);
                if(!success)
                    return false;
            }
        }
        return true;
    }

    public BrickBlock getMatchingMove(long initTime){
        long delta;
        //BrickBlock emergencyMove = root.getChildren().get(0).getMove();
        //TODO rozwiązać null pointer exception przy przekroczeniu czasu w growTree
        if (root.getNodeControl() == ControlState.EVEN) {
            for (Node child : root.getChildren()) {
                if(child.getChildren() != null || (delta = System.currentTimeMillis() - initTime) < GET_MOVE_TIME_LIMIT) {
                    if (child.getChildren().size() == 0 && child.getNodeControl() == ControlState.EVEN) {
                        System.out.println("EVEN -> EVEN -> x");
                        return child.getMove();
                    } else if (child.getNodeControl() == ControlState.ODD) {
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
                /* zmniejsz drzewo do następnej tury */
                else{
                    System.out.println("> no grandchildren [t="+delta+"]");
                    return root.getChildren().get(0).getMove();
                }
            }
            System.out.println("EVEN -> emergency");
            //dawne ewentualności

            if(blocksData.getBlocksType1().size() > 0)
                return blocksData.getBlocksType1().get(0).nextMove(board);
            if(blocksData.getBlocksType2().size() > 0)
                return blocksData.getBlocksType2().get(0).nextMove(board);
            if(blocksData.getBlocksType2or1().size() > 0)
                return blocksData.getBlocksType2or1().get(0).leaveZeroMoves(board);
        }
        else if(root.getNodeControl() == ControlState.ODD){
            for (Node child : root.getChildren()) {
                if (child.getChildren() != null || (delta = System.currentTimeMillis() - initTime) < GET_MOVE_TIME_LIMIT) {
                    if (child.getChildren().size() == 0 && child.getNodeControl() == ControlState.ODD) {
                        System.out.println("ODD -> ODD -> x");
                        return child.getMove();
                    } else if (child.getNodeControl() == ControlState.EVEN) {
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
                /* zmniejsz drzewo do następnej tury */

                else {
                    System.out.println("> no grandchildren [t="+delta+"]");
                    return root.getChildren().get(0).getMove();
                }
            }
            System.out.println("ODD -> emergency");
            //dawne ewentualności

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
