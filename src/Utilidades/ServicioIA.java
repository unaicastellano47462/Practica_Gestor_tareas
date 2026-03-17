package Utilidades;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServicioIA {

    private static final String API_KEY = "AIzaSyAJ6GI53vitLroe-FG9s5mhCy80EGvjVuY";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;
    public static int obtenerPrioridad(String descripcion) {
        try {
            String prompt = "Actúa como un gestor de tareas. Analiza la siguiente descripción de una tarea y asígnale una prioridad del 1 (menor urgencia) al 5 (mayor urgencia). Responde ÚNICAMENTE con el número, sin texto adicional. Descripción de la tarea: " + descripcion;

            String requestBody = "{\"contents\": [{\"parts\":[{\"text\": \"" + prompt + "\"}]}]}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            if (response.statusCode() != 200) {
                System.err.println(" (Error IA) Google rechazó la conexión (HTTP " + response.statusCode() + ").");
                throw new Exception("Conexión rechazada por Google.");
            }

            Pattern pattern = Pattern.compile("\"text\"\\s*:\\s*\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(responseBody);

            if (matcher.find()) {
                String textoIA = matcher.group(1);
                String numeroStr = textoIA.replaceAll("[^1-5]", "");

                if (!numeroStr.isEmpty()) {
                    return Integer.parseInt(numeroStr.substring(0, 1));
                }
            }

            throw new Exception("La IA no devolvió ningún número.");

        } catch (Exception e) {
            System.out.println(" (Aviso) No se pudo obtener la prioridad de la IA: " + e.getMessage());
            System.out.println(" (Aviso) Se asignará una prioridad media (3) por defecto.");
            return 3;
        }
    }
}