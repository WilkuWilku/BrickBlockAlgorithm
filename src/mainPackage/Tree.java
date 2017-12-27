package mainPackage;

import mainPackage.blocks.BlockFinder;

/**
 * Created by Inf on 2017-12-25.
 */
public class Tree {

    /* board nie powinno zawierać bloków */
    private BoardState board;
    private Node root;


    public Tree(BoardState board, BoardStatistics statistics){
        this.board = new BoardState(board);
        root = Node.createRootNode(this.board, statistics);
        root.createChildren(statistics);
    }

    public void growTree(int maxLevel){
        growTree(maxLevel, root);
    }

    private void growTree(int maxLevel, Node currentNode) {
        if (currentNode.getLevel() < maxLevel) {
            if(currentNode.getChildren() == null) {
                BoardAnalyzer analyzer = currentNode.getAnalyzer();
                BoardStatistics statistics = analyzer.findAllMoves();
                currentNode.createChildren(statistics);
            }
            for (int i = 0; i < currentNode.getChildren().size(); i++)
                growTree(maxLevel, currentNode.getChildren().get(i));

        }
    }


}
