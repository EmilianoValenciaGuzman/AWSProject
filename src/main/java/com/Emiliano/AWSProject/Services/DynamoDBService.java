package com.Emiliano.AWSProject.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Emiliano.AWSProject.Entities.Session;

import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.*;

@Service
public class DynamoDBService {
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final String tableName = "sesiones-alumnos";

    @Autowired
    public DynamoDBService(DynamoDbEnhancedClient dynamoDbEnhancedClient ){
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    }

    public void saveSession(Session session){
        DynamoDbTable<Session> sessionsTable = getSessionTable();
        sessionsTable.putItem(session);
    }

    public boolean logoutSession(int id, String sessionString){ 
        Session session = getSession(id, sessionString);
        if(session != null){
            session.setActive(false);
            saveSession(session);
            return true;
        }
        return false;
    }

    public boolean isValidSession(int id, String sessionString){
        Session session = getSession(id, sessionString);
        if(session != null){
            return session.getActive();
        }
        return false;
    }
    private DynamoDbTable<Session> getSessionTable(){
        return dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(Session.class));
    }

    private Session getSession(int id, String sessionString){
        DynamoDbTable<Session> sessionsTable = getSessionTable();
        PageIterable<Session> allSessions = sessionsTable.scan(createSessionStringScan(sessionString));
        findSession(id, sessionString, allSessions);
        return findSession(id, sessionString, allSessions);
    }

    private Session findSession(int id, String sessionString, PageIterable<Session> allSessions){
        Session session = null;
        for (Page<Session> page : allSessions) {
            for (Session sessionItem : page.items()) {
                if (sessionItem.getSessionString().equals(sessionString) && sessionItem.getAlumnoId() == id) {
                    session = sessionItem;
                    break;
                }
            }   
        }
        return session;
    }

    private ScanEnhancedRequest createSessionStringScan(String sessionString){
        return ScanEnhancedRequest.builder()
                .filterExpression(createSessionStringExpression(sessionString))
                .build();
    }
    private Expression createSessionStringExpression(String sessionString){
        return Expression.builder()
                .expression("#attr = :value")
                .putExpressionName("#attr", "sessionString")
                .putExpressionValue(":value", AttributeValue.builder().s(sessionString).build())
                .build();
    }
}