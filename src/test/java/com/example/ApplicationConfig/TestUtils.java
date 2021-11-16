package com.example.ApplicationConfig;

import Model.Cliente;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class TestUtils {
    public static final String CLIENTES = "clientes";
    public static final String RESPONSE_STATE = "$.status";

    public static Cliente createClient(){
        return  new Cliente(1,"santiago", "Alvarez", "cc", 24, "Medellin");
    }

    public static MultipartFile createFile(){

        return new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
    }
}
