package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.data.dto.request.DepartamentoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DepartamentoResponseDTO;
import com.sistema_escolar.sistema.escolar.integrationtests.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import io.restassured.RestAssured.*;

import java.util.Base64;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class DepartamentoControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static String token;
    private static DepartamentoResponseDTO responseDTO;
    private static DepartamentoRequestDTO requestDTO;
    private static String code;

    private static String basic;

    private static String client_id = "client123";
    private static String client_secret = "client123";
    private static String redirect_uri = "http://localhost:8080/authorized";


    @BeforeAll
    static void setUp() {
        token = "234ewdd";
        requestDTO = mockRequest();
    }

    public static String encoder(String user, String password) {
        return new String(Base64.getEncoder().encode((user + ":" + password).getBytes()));
    }

    @Test
    @Order(1)
    void getCode() {
        basic = encoder(client_id, client_secret);

        specification = new RequestSpecBuilder()
                .setPort(TestConfigs.DEFINED_PORT)
                .build();

        token = given(specification)
                .contentType(ContentType.URLENC)
                .auth()
                .preemptive()
                .basic("client123", "john123")
                .formParam("grant_type", "client_credentials")
                .when()
                .post("/oauth2/token")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                        .response().jsonPath().getString("access_token");


        System.out.println("token: " + token);
        assertThat(token).isNotNull();
    }

    @Test
    @Order(2)
    void salvar() {
        specification = new RequestSpecBuilder()
                .setPort(TestConfigs.DEFINED_PORT)
                .setBasePath("/departamentos")
                .addHeader("Authorization", token)
                .build();

        responseDTO = given(specification)
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestDTO)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .as(DepartamentoResponseDTO.class);


        assertThat(responseDTO.getId()).isNotNull();
        assertThat(responseDTO.getId()).isGreaterThan(0);
        assertThat(responseDTO.getNome()).isEqualTo("Física");
    }

    @Test
    @Order(3)
    void atualizar() {

        requestDTO.setNome("Matemática");

        responseDTO = given(specification)
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestDTO)
                .when()
                .pathParam("id", responseDTO.getId())
                .put("/{id}")
                .then()
                .statusCode(200)
                .extract()
                .as(DepartamentoResponseDTO.class);

        assertThat(responseDTO.getId()).isNotNull();
        assertThat(responseDTO.getId()).isGreaterThan(0);
        assertThat(responseDTO.getNome()).isEqualTo("Matemática");
    }

    @Test
    void obterPeloId() {
    }

    @Test
    void listar() {
    }

    @Test
    void deletarPeloId() {
    }

    private static DepartamentoRequestDTO mockRequest() {
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTO();
        requestDTO.setNome("Física");
        requestDTO.setSigla("iFT");
        requestDTO.setBloco("Bloco 2A");

        return requestDTO;
    }
}