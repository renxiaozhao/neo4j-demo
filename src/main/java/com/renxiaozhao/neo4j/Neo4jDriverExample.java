package com.renxiaozhao.neo4j;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Statement;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Values;

public class Neo4jDriverExample {
    Driver driver;

    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "neo4j"));
        try (Session session = driver.session();
                Transaction transaction = session.beginTransaction()) {
            Statement query = new Statement(
                    "CREATE (a:Greeting) SET a.message = $message RETURN a.message + ', from node ' + id(a)",
                    Values.parameters("message", "testDriver"));

            StatementResult result = transaction.run(query);
            transaction.success(); // mark success, actually commit will happen in transaction.close()
            String greeting = result.single().get(0).asString();
            System.out.println(greeting);
        }
        driver.close();
    }

}