package com.renxiaozhao.neo4j;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo4jJavaApi {

    public static void main(String[] args) {

        GraphDatabaseService graphDb;
        Node firstNode;
        Node secondNode;
        Relationship relationship;
        graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabase(new File("D:\\soft\\neo4j-community-3.5.34\\data\\databases\\testdb"));
        try (Transaction tx = graphDb.beginTx()) {
            firstNode = graphDb.createNode();
            firstNode.setProperty("message", "Hello, ");
            secondNode = graphDb.createNode();
            secondNode.setProperty("message", "World!");

            relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
            relationship.setProperty("message", "brave Neo4j ");
            tx.success();
            System.out.print(firstNode.getProperty("message"));
            System.out.print(relationship.getProperty("message"));
            System.out.print(secondNode.getProperty("message"));
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            graphDb.shutdown();
        }
    }

    private enum RelTypes implements RelationshipType {
        KNOWS
    }
    
}
