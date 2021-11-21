package org.playwing.game;

import org.playwing.game.core.commons.dependency.DependencyManager;
import org.playwing.game.core.commons.exception.RequestHandlerNotFoundException;
import org.playwing.game.core.commons.handlers.Handlers;
import org.playwing.game.core.commons.handlers.RequestHandlers;
import org.playwing.game.matchmaking.handler.FindMatchHandler;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DependencyManager.getInstance()
                .init();

        addRequestHandlers();

        DependencyManager.getInstance()
                .getTaskStarterService()
                .startGlobalTasks();


        List<String> strList = new LinkedList<>();
        strList.add("{\"id\":\"0x4E3A12\",\"skill\":12,\"squadId\":5}");
        strList.add("{\"id\":\"0x4E3A13\",\"skill\":14,\"squadId\":5}");
        strList.add("{\"id\":\"0x4E3A14\",\"skill\":15,\"squadId\":5}");
        strList.add("{\"id\":\"0x4E3A15\",\"skill\":16,\"squadId\":5}");
        strList.add("{\"id\":\"0x4E3A16\",\"skill\":17,\"squadId\":5}");
        strList.add("{\"id\":\"0x4E3A17\",\"skill\":17,\"squadId\":5}");
        strList.add("{\"id\":\"0x4E3A18\",\"skill\":17,\"squadId\":6}");
        strList.add("{\"id\":\"0x4E3A19\",\"skill\":17,\"squadId\":6}");
        strList.add("{\"id\":\"0x4E3A20\",\"skill\":17,\"squadId\":6}");
        strList.add("{\"id\":\"0x4E3A21\",\"skill\":17,\"squadId\":6}");
        strList.add("{\"id\":\"0x4E3A22\",\"skill\":17,\"squadId\":6}");
        strList.add("{\"id\":\"0x4E3A23\",\"skill\":17,\"squadId\":6}");
        strList.add("{\"id\":\"0x4E3A24\",\"skill\":17,\"squadId\":7}");
        strList.add("{\"id\":\"0x4E3A25\",\"skill\":17,\"squadId\":7}");
        strList.add("{\"id\":\"0x4E3A26\",\"skill\":17,\"squadId\":7}");
        strList.add("{\"id\":\"0x4E3A27\",\"skill\":17,\"squadId\":7}");
        strList.add("{\"id\":\"0x4E3A28\",\"skill\":17,\"squadId\":8}");
        strList.add("{\"id\":\"0x4E3A29\",\"skill\":17,\"squadId\":8}");
        strList.add("{\"id\":\"0x4E3A30\",\"skill\":17,\"squadId\":8}");
        strList.add("{\"id\":\"0x4E3A31\",\"skill\":17,\"squadId\":8}");
        strList.add("{\"id\":\"0x4E3A32\",\"skill\":19,\"squadId\":9}");
        strList.add("{\"id\":\"0x4E3A33\",\"skill\":19,\"squadId\":9}");
        strList.add("{\"id\":\"0x4E3A34\",\"skill\":19,\"squadId\":9}");
        strList.add("{\"id\":\"0x4E3A35\",\"skill\":19,\"squadId\":10}");
        strList.add("{\"id\":\"0x4E3A36\",\"skill\":19,\"squadId\":10}");
        strList.add("{\"id\":\"0x4E3A37\",\"skill\":19,\"squadId\":10}");
        strList.add("{\"id\":\"0x4E3A38\",\"skill\":19,\"squadId\":11}");
        strList.add("{\"id\":\"0x4E3A39\",\"skill\":19,\"squadId\":11}");
        strList.add("{\"id\":\"0x4E3A40\",\"skill\":19,\"squadId\":12}");
        strList.add("{\"id\":\"0x4E3A41\",\"skill\":19,\"squadId\":12}");
        strList.add("{\"id\":\"0x4E3A42\",\"skill\":12,\"squadId\":13}");
        strList.add("{\"id\":\"0x4E3A43\",\"skill\":14,\"squadId\":13}");
        strList.add("{\"id\":\"0x4E3A44\",\"skill\":15,\"squadId\":13}");
        strList.add("{\"id\":\"0x4E3A45\",\"skill\":17,\"squadId\":15}");
        strList.add("{\"id\":\"0x4E3A46\",\"skill\":17,\"squadId\":16}");
        strList.add("{\"id\":\"0x4E3A47\",\"skill\":17,\"squadId\":16}");
        strList.add("{\"id\":\"0x4E3A48\",\"skill\":17,\"squadId\":16}");
        strList.add("{\"id\":\"0x4E3A49\",\"skill\":17,\"squadId\":16}");
        strList.add("{\"id\":\"0x4E3A50\",\"skill\":17,\"squadId\":16}");
        strList.add("{\"id\":\"0x4E3A51\",\"skill\":17,\"squadId\":16}");
        strList.add("{\"id\":\"0x4E3A52\",\"skill\":17,\"squadId\":-1}");
        strList.add("{\"id\":\"0x4E3A53\",\"skill\":17,\"squadId\":-1}");
        strList.add("{\"id\":\"0x4E3A54\",\"skill\":17,\"squadId\":-1}");
        strList.add("{\"id\":\"0x4E3A55\",\"skill\":17,\"squadId\":-1}");
        strList.add("{\"id\":\"0x4E3A56\",\"skill\":17,\"squadId\":-1}");
        strList.add("{\"id\":\"0x4E3A57\",\"skill\":17,\"squadId\":-1}");

        testApplication(Handlers.FIND_MATCH, strList);
    }

    private static void addRequestHandlers() {
        RequestHandlers requestHandlers = DependencyManager.getInstance()
                .getRequestHandlers();

        requestHandlers.addRequestHandler(Handlers.FIND_MATCH, new FindMatchHandler());
    }



    private static void testApplication(String handlerName, List<String> requests) {
        requests.forEach(request -> {
                Thread thread = new Thread(() -> sendRequest(handlerName, request));
                thread.start();
        });
    }

    private static void sendRequest(String handlerName, String request) {
        try {
            DependencyManager.getInstance()
                    .getRequestHandlers()
                    .handleRequest(handlerName, request);
        } catch (RequestHandlerNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
