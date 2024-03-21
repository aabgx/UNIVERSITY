package com.example.tema_bun.Client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;

public class Client {
    private String baseUrl;

    public Client(String baseUrl){
        this.baseUrl=baseUrl;
    }

    public void addProba(String id) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(baseUrl + "/concurs/addProba");

        // Setarea conținutului JSON în corpul cererii
        String jsonBody = "{\"id\": \""+id+"\"}";
        StringEntity entity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        try {
            // Executarea cererii HTTP
            HttpResponse response = httpClient.execute(request);

            // Verificarea răspunsului de la server
            int codRaspuns = response.getStatusLine().getStatusCode();
            if (codRaspuns == 200) {
                // Răspunsul este în regulă, puteți citi răspunsul aici
            } else {
                // Răspunsul a întors o eroare, puteți trata eroarea aici
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteProba(String id) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(baseUrl + "/concurs/deleteProba");

        // Setarea conținutului JSON în corpul cererii
        String jsonBody = "{\"id\": \""+id+"\"}";
        StringEntity entity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        try {
            // Executarea cererii HTTP
            HttpResponse response = httpClient.execute(request);

            // Verificarea răspunsului de la server
            int codRaspuns = response.getStatusLine().getStatusCode();
            if (codRaspuns == 200) {
                // Răspunsul este în regulă, puteți citi răspunsul aici
            } else {
                // Răspunsul a întors o eroare, puteți trata eroarea aici
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAllProba() throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(baseUrl + "/concurs/getAllProba");

        // Setarea conținutului JSON în corpul cererii
        String jsonBody = "{}";
        StringEntity entity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        try {
            // Executarea cererii HTTP
            HttpResponse response = httpClient.execute(request);

            // Verificarea răspunsului de la server
            int codRaspuns = response.getStatusLine().getStatusCode();
            if (codRaspuns == 200) {
                // Răspunsul este în regulă, puteți citi răspunsul aici
            } else {
                // Răspunsul a întors o eroare, puteți trata eroarea aici
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findByIdProba(String id) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(baseUrl + "/concurs/findByIdProba");

        // Setarea conținutului JSON în corpul cererii
        String jsonBody = "{\"id\": \""+id+"\"}";
        StringEntity entity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        try {
            // Executarea cererii HTTP
            HttpResponse response = httpClient.execute(request);

            // Verificarea răspunsului de la server
            int codRaspuns = response.getStatusLine().getStatusCode();
            if (codRaspuns == 200) {
                // Răspunsul este în regulă, puteți citi răspunsul aici
            } else {
                // Răspunsul a întors o eroare, puteți trata eroarea aici
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
