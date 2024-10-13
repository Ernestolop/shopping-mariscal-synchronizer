package com.elopez.mariscal.synchronizer.modules.sender.gateway;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class HttpClientMariscal {

    private static String token = "539|RhlUeOztBStuluGgVt4WsRqaqZWymbfcPhJvdVAZ";
    private static final String BASE_URL = "https://sistema.mariscal.com.py/api/contrato";

    public static HttpResponse<String> post(String endpoint, String body) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}