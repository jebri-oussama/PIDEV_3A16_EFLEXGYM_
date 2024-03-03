package API;
import Gestion_planing.entities.planning;
import Gestion_planing.service.PlanningService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.stream.Collectors;
public class CalendrierAPI {


    public void startServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/addPlanningHandler", new AddPlanningHandler());
        server.createContext("/getAllPlanningsHandler", new GetAllPlanningsHandler());
        server.createContext("/updatePlanningHandler", new UpdatePlanningHandler());
        server.createContext("/deletePlanningHandler", new DeletePlanningHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server is running on port " + port);
    }

    static class GetAllPlanningsHandler implements HttpHandler {
        private final PlanningService planningService =new PlanningService();
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestBody = getRequestBody(exchange);
            // Process the GET request body
            String response = planningService.readAll().toString() + requestBody.toString();
            sendResponse(exchange, 200, response);
        }
    }


    static class AddPlanningHandler implements HttpHandler {
        private final PlanningService planningService =new PlanningService();
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestBody = getRequestBody(exchange);
            planning plan = planning.fromJson(requestBody);
            // Process the POST request body
            planningService.add(plan);
            String response = "Planning added successfully";
            sendResponse(exchange, 200, response);
        }
    }

    static class UpdatePlanningHandler implements HttpHandler {
        private final PlanningService planningService =new PlanningService();
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestBody = getRequestBody(exchange);
            planning plan = planning.fromJson(requestBody);
            // Process the PUT request body
            planningService.update(plan);
            String response = "Planning Updated successfully";
            sendResponse(exchange, 200, response);
        }
    }

    static class DeletePlanningHandler implements HttpHandler {
        private final PlanningService planningService =new PlanningService();
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestBody = getRequestBody(exchange);
            planning plan = planning.fromJson(requestBody);
            // Process the DELETE request body
            planningService.delete(plan);
            String response = "Planning deleted successfully";
            sendResponse(exchange, 200, response);
        }
    }


    private static String getRequestBody(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        return br.lines().collect(Collectors.joining("\n"));
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(response.getBytes());
        output.flush();
        exchange.close();
    }
}
