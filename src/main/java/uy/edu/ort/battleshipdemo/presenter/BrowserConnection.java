package uy.edu.ort.battleshipdemo.presenter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
@Scope("session")
public class BrowserConnection {

    private static final long TIME_OUT = 30 * 60 * 1000; // 30 minutos, igual que la sesión

    private SseEmitter sseConnection;

    public void connectSSE() {
        if (sseConnection != null) {
            closeConnection();
        }
        sseConnection = new SseEmitter(TIME_OUT);
    }

    public void closeConnection() {
        try {
            if (sseConnection != null) {
                sseConnection.complete();
                sseConnection = null;
            }
        } catch (Exception e) {
            // la conexión ya estaba cerrada
        }
    }

    public SseEmitter getSseConnection() {
        return sseConnection;
    }

    public void sendJSON(Object information) {
        try {
            sendMessage(new ObjectMapper().writeValueAsString(information));
        } catch (JacksonException e) {
            System.out.println("Error al convertir a JSON: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        if (sseConnection == null) {
            return;
        }
        try {
            sseConnection.send(message);
        } catch (Throwable e) {
            System.out.println("Error al enviar mensaje: " + e.getMessage());
            closeConnection();
        }
    }
}
