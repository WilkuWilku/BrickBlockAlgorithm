package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks2or1type.Block2or1Types;
import mainPackage.blocks.blocks2type.Block2Types;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by Inf on 2017-11-30.
 */
public class DatabaseHandler {

    private static DatabaseHandler instance = null;
    private Connection connection;
    private Statement statement;

    private DatabaseHandler(){
    }

    public static DatabaseHandler getInstance() {
        if(instance == null){
            instance = new DatabaseHandler();
        }
        return instance;
    }

    public void connect(){
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void testQuery(){
        try {
            /*String query = "DROP TABLE testTable"; */
            String query1 = "SELECT * FROM testTable";
            //statement.executeQuery(query1);
            String query2 = "DROP TABLE testTable";
            statement.executeUpdate(query2);
            statement.executeUpdate("CREATE TABLE testTable(id INT, opis VARCHAR)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable(String name){
        try {
            String query = "DROP TABLE "+name;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create2or1Table(){
        try {
            String query = "CREATE TABLE table2or1(block VARCHAR, rotation VARCHAR, shapeId DECIMAL(21,0) NOT NULL, PRIMARY KEY (shapeId)) ";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*
    public void addValues(){
        try {
            statement.executeUpdate("INSERT INTO testTable(id, opis) VALUES (1, 'aaaa')");
            statement.executeUpdate("INSERT INTO testTable(id, opis) VALUES (2, 'daa')");
            statement.executeUpdate("INSERT INTO testTable(id, opis) VALUES (3, 'afsda')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
    public ResultSet getAllValuesFromDB(){
        ResultSet results = null;
        try {
            String query = "SELECT * FROM table2or1";
            results = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public ResultSet getFilteredResults(BigDecimal areaStateId){
        String query = "SELECT block, rotation FROM table2or1 WHERE "+areaStateId+"%shapeId = 0";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void printResults(ResultSet resultSet){
        try {
            while(resultSet.next()){
                System.out.println(resultSet.getRow()+" | "+resultSet.getString("block")+" | "+resultSet.getString("rotation") + " | ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAllBlockTypes(){
        create2or1Table();

        for(Block2or1Types types : Block2or1Types.values()){
            for(BlockRotation rot : BlockRotation.values()) {
                Class tClass = types.getBlockClass();
                try {
                    Constructor constructor = tClass.getConstructor(int.class, BlockRotation.class);
                    AbstractBlock block = (AbstractBlock) constructor.newInstance(0, rot);
                    int[][] shape = block.getShape();
                    if(shape != null){
                        int[] shapeIndexes = new int[shape.length];
                        System.out.print(block.getClass().getSimpleName() + ": ");
                        BigDecimal shapeId = new BigDecimal(1);
                        for(int i=0; i<shape.length; i++){
                            shapeIndexes[i] = IndexConverter.xyToIndex(shape[i][0], shape[i][1], 4);
                            System.out.print(BoardAnalyzer.primes[shapeIndexes[i]] + " | ");
                            shapeId = shapeId.multiply(new BigDecimal(BoardAnalyzer.primes[shapeIndexes[i]]));
                        }
                        System.out.print(": shapeID ="+shapeId.toString()+ "\n");
                        add2or1Values(tClass.getSimpleName(), rot.name(), shapeId.toPlainString());
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void add2or1Values(String blockType, String rotation, String shapeId){
        String query = "INSERT INTO table2or1(block, rotation, shapeId) VALUES ('" + blockType + "', '" + rotation + "', '" + shapeId+"')";
        System.out.println("executing query " + query);
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
