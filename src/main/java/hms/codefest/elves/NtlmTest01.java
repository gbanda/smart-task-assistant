package hms.codefest.elves;

import hms.codefest.elves.connector.BasicUpdateTaskRequest;
import hms.codefest.elves.connector.DefaultTasksRequest;
import hms.codefest.elves.connector.TasksContainerResponse;
import hms.codefest.elves.connector.impl.ProjectsServerConnectorImpl;
import hms.codefest.elves.domain.BasicTask;
import hms.codefest.elves.domain.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

public class NtlmTest01 {

    private static String result = "{\n" +
            "\t\"d\": {\n" +
            "\t\t\"results\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a158787f-c917-e811-85df-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a158787f-c917-e811-85df-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 110.6,\n" +
            "\t\t\t\t\"Start\": \"2018-02-22T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 80\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('ab594370-c917-e811-85df-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('ab594370-c917-e811-85df-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-23T08:48:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('8d93c86a-ca17-e811-85df-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('8d93c86a-ca17-e811-85df-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 121.91,\n" +
            "\t\t\t\t\"Start\": \"2018-02-22T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 97\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('0a4307b5-fb17-e811-9ef2-08002709d716')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('0a4307b5-fb17-e811-9ef2-08002709d716')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-23T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('f9d65111-fc17-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('f9d65111-fc17-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-22T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 75\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('ef8228fd-0118-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('ef8228fd-0118-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 80.44,\n" +
            "\t\t\t\t\"Start\": \"2018-02-22T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 64\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a96865f5-0118-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a96865f5-0118-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 31.42,\n" +
            "\t\t\t\t\"Start\": \"2018-02-23T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 25\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a76865f5-0118-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a76865f5-0118-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-26T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a56865f5-0118-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a56865f5-0118-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-27T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a36865f5-0118-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a36865f5-0118-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-28T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a1e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a1e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-03-02T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a3e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a3e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 74.15,\n" +
            "\t\t\t\t\"Start\": \"2018-02-22T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 59\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a5e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a5e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-23T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a7e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a7e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-26T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a9e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('a9e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-27T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('abe82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('abe82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-28T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('ade82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('ade82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-03-02T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('afe82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('afe82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-03-05T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('b1e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('b1e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-22T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('b3e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('b3e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-23T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('b5e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('b5e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-26T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('b7e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('b7e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-27T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('b9e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('b9e82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-02-28T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('bbe82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('bbe82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-03-02T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"__metadata\": {\n" +
            "\t\t\t\t\t\"id\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('bde82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"uri\": \"http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')" +
            "/Assignments('bde82604-0218-e811-85e0-3464a9bf110d')\",\n" +
            "\t\t\t\t\t\"type\": \"PS.PublishedAssignment\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"ActualCostWorkPerformed\": 0,\n" +
            "\t\t\t\t\"Start\": \"2018-03-05T08:00:00\",\n" +
            "\t\t\t\t\"PercentWorkComplete\": 0\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";

    public static void main(String[] args) throws Exception {

        String urlStr =
                "http://projectserver/PWA/_api/ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')/Tasks" +
                        "('ee8228fd-0118-e811-85e0-3464a9bf110d')/Assignments";
        String domain = "";
        String userName = "ranjana";
        String password = "R@njanaH745";

        ProjectsServerConnectorImpl projectsServerConnector = new ProjectsServerConnectorImpl();
        DefaultTasksRequest request = DefaultTasksRequest.newDefaultTasksRequest()
                .adminPassword(password)
                .adminUsername(userName)
                .projectId("17a0176a-c917-e811-85df-3464a9bf110d")
                .userName("aroshar")
                .userPassword("A#rosha@456").build();
        TasksContainerResponse tasks = (TasksContainerResponse) projectsServerConnector.getTasks(request);
        Task task = tasks.getBasicTasks().get(0);
        BasicUpdateTaskRequest updateTaskRequest = new BasicUpdateTaskRequest();
        updateTaskRequest.setAdminPassword(password);
        updateTaskRequest.setAdminUsername(userName);
        updateTaskRequest.setAssignmentUrl(((BasicTask) task).getMetadata().getUri());
        updateTaskRequest.setProjectId("17a0176a-c917-e811-85df-3464a9bf110d");
        updateTaskRequest.setUpdatedPercentage(99);
//        JSONPObject jsonpObject = new JSONPObject();
        projectsServerConnector.updateTask(updateTaskRequest);
        System.out.println(tasks);
    }


    private static String getAuthenticatedResponse(final String urlStr, final String domain, final String userName,
                                                   final String password) throws IOException {

        StringBuilder response = new StringBuilder();

        Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(domain + "\\" + userName, password.toCharArray());
            }
        });

        URL urlRequest = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) urlRequest.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("GET");

        InputStream stream = conn.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String str = "";
        while ((str = in.readLine()) != null) {
            response.append(str);
        }
        in.close();

        return response.toString();
    }

}