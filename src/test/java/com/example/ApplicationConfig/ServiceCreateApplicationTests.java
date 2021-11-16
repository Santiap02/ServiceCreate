package com.example.ApplicationConfig;

import Model.Cliente;
import Model.Photo;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.ServiceCreateException;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import repository.ClientRepository;
import repository.PhotoRepository;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class ServiceCreateApplicationTests {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc restMock;
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    PhotoRepository photoRepository;

    @Test
    @WithMockUser(username = "admin", password = "admin" ,authorities = { "ADMIN", "USER" })
    void createClientTest() throws Exception {
        var cliente = TestUtils.createClient();
        restMock.perform(post("/clientes/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsBytes(cliente)))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(TestUtils.RESPONSE_STATE).value(HttpStatus.CREATED.value()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin" ,authorities = { "ADMIN", "USER" })
    void createClientTestExist() throws Exception {
        Mockito.when(this.clientRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(TestUtils.createClient()));
        var cliente = TestUtils.createClient();
        restMock.perform(post("/clientes/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsBytes(cliente)))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(TestUtils.RESPONSE_STATE).value(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin" ,authorities = { "ADMIN", "USER" })
    void createClientTestServiceException() throws Exception {
        restMock.perform(post("/clientes/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsBytes(new Cliente())))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(TestUtils.RESPONSE_STATE).value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin" ,authorities = { "ADMIN", "USER" })
    void createClientTestGeneralException() throws Exception {
        Mockito.when(this.clientRepository.findById(Mockito.anyInt())).thenThrow(NullPointerException.class);
        var cliente = TestUtils.createClient();
        restMock.perform(post("/clientes/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsBytes(cliente)))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(TestUtils.RESPONSE_STATE).value(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin" ,authorities = { "ADMIN", "USER" })
    void createPhotoTest() throws Exception {
        restMock.perform(multipart("/photos/add").file("image", TestUtils.createFile().getBytes())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("clientId", "12"))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(TestUtils.RESPONSE_STATE).value(HttpStatus.CREATED.value()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin" ,authorities = { "ADMIN", "USER" })
    void createPhotoTestExist() throws Exception {
        Mockito.when(this.photoRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(new Photo(5)));
        restMock.perform(multipart("/photos/add").file("image", TestUtils.createFile().getBytes())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("clientId", "12"))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(TestUtils.RESPONSE_STATE).value(HttpStatus.CREATED.value()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin" ,authorities = { "ADMIN", "USER" })
    void createPhotoTestGeneralException() throws Exception {
        Mockito.when(this.photoRepository.findByClientId(Mockito.anyInt())).thenThrow(NullPointerException.class);
        restMock.perform(multipart("/photos/add").file("image", TestUtils.createFile().getBytes())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("clientId", "12"))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(TestUtils.RESPONSE_STATE).value(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin" ,authorities = { "ADMIN", "USER" })
    void createPhotoTestServiceException() throws Exception {
        Mockito.when(this.photoRepository.findByClientId(Mockito.anyInt())).thenThrow(new ServiceCreateException(HttpStatus.BAD_REQUEST.value(),"code", "Bad request"));
        restMock.perform(multipart("/photos/add").file("image", TestUtils.createFile().getBytes())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("clientId", "1"))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(TestUtils.RESPONSE_STATE).value(HttpStatus.BAD_REQUEST.value()));
    }


}
