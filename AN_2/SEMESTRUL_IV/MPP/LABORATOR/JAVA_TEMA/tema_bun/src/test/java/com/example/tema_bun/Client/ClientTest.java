package com.example.tema_bun.Client;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void testAll() throws IOException {
        var client = new Client("http://localhost:12500");
        client.addProba("123");
        client.deleteProba("123");
        client.getAllProba();
        client.findByIdProba("123");
    }
}