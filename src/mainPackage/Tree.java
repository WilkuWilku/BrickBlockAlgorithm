package mainPackage;

import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-12-25.
 */
public class Tree {
    private static final long GROW_TREE_TIME_LIMIT = 400;             //maksymalny czas generowania drzewa (w milisekundach)
    private static final long GET_MOVE_TIME_LIMIT = 420;              //maksymalny czas znalezienia ruchu (w milisekundach)
    private BoardState board;
    private Node root;
    private BlocksData blocksData;
    private ControlState controlState;
    private MovesData movesData;

    public Tree(BoardState boardWithoutBlocks, BlocksData blocksData, MovesData movesData, long initTime){
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

    /* utworzenie kolejnych dzieci drzewa */
    private boolean growTree(int maxLevel, Node currentNode, long initTime) {
        long delta;
        if((delta = System.currentTimeMillis() - initTime) > GROW_TREE_TIME_LIMIT) {
            System.err.println("Too much time taken ["+delta+"/LIMIT="+GROW_TREE_TIME_LIMIT+"]");
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

    /* funkcja wyszukująca w drzewie najlepszego możliwego ruchu */
    public BrickBlock getMatchingMove(long initTime){
        if (root.getNodeControl() == ControlState.EVEN) {
            if(root.getChildren() != null) {
                for (Node child : root.getChildren()) {
                    if (child.getChildren() != null && (System.currentTimeMillis() - initTime) <= GET_MOVE_TIME_LIMIT) {
                        if (child.getChildren().size() == 0 && child.getNodeControl() == ControlState.EVEN) {
                            /* ControlState: EVEN -> EVEN -> null */
                            return child.getMove();
                        } else if (child.getNodeControl() == ControlState.ODD) {
                            boolean isGrandchildMatching = true;
                            if (child.getChildren() != null) {
                                for (Node grandchild : child.getChildren()) {
                                    if(grandchild.getChildren() != null) {
                                        if (!(grandchild.getChildren().size() == 0 && grandchild.getNodeControl() == ControlState.EVEN))
                                            isGrandchildMatching = false;
                                    }
                                }
                                if (isGrandchildMatching) {
                                    /* ControlState: EVEN -> ODD -> wyłącznie EVENs -> null */
                                    return child.getMove();
                                }
                            }
                        }
                    }
                /* zmniejsz drzewo do następnej tury */
                    else {
                        return root.getChildren().get(0).getMove();
                    }
                }
            }
            /* brak idealnych ruchów */
            if(blocksData.getBlocksType1().size() > 0)
                return blocksData.getBlocksType1().get(0).nextMove(board);
            if(blocksData.getBlocksType2().size() > 0)
                return blocksData.getBlocksType2().get(0).nextMove(board);
            if(blocksData.getBlocksType2or1().size() > 0)
                return blocksData.getBlocksType2or1().get(0).leaveZeroMoves(board);
        }
        else if(root.getNodeControl() == ControlState.ODD){
            for (Node child : root.getChildren()) {
                if(root.getChildren() != null) {
                    if (child.getChildren() != null && (System.currentTimeMillis() - initTime) <= GET_MOVE_TIME_LIMIT) {
                        if (child.getChildren().size() == 0 && child.getNodeControl() == ControlState.ODD) {
                            /* ControlState: ODD -> ODD -> null */
                            return child.getMove();
                        } else if (child.getNodeControl() == ControlState.EVEN) {
                            boolean isGrandchildMatching = true;
                            if(child.getChildren() != null) {
                                for (Node grandchild : child.getChildren()) {
                                    if (grandchild.getChildren() != null) {
                                        if (!(grandchild.getChildren().size() == 0 && grandchild.getNodeControl() == ControlState.ODD))
                                            isGrandchildMatching = false;
                                    }
                                }
                                if (isGrandchildMatching) {
                                    /* ControlState: ODD -> EVEN -> wyłącznie ODDs -> null */
                                    return child.getMove();
                                }
                            }
                        }
                    }
                /* zmniejsz drzewo do następnej tury */
                    else {
                        return root.getChildren().get(0).getMove();
                    }
                }
            }
            /* brak idealnych ruchów */
            if(blocksData.getBlocksType2().size() > 0)
                return blocksData.getBlocksType2().get(0).nextMove(board);
            if(blocksData.getBlocksType1().size() > 0)
                return blocksData.getBlocksType1().get(0).nextMove(board);
            if(blocksData.getBlocksType2or1().size() > 0)
                return blocksData.getBlocksType2or1().get(0).leaveOneMove(board);
        }
            /* weź cokolwiek jeśli nic już nie pasuje */
            return BoardAnalyzer.mapValuesToSet(movesData.getMovesMap()).iterator().next();
    }
}
