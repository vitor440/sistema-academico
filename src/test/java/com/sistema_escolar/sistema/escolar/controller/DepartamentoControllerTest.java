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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class DepartamentoControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static String token;
    private static DepartamentoResponseDTO responseDTO;
    private static DepartamentoRequestDTO requestDTO;


    @BeforeAll
    static void setUp() {
        token = "234ewdd";
        requestDTO = mockRequest();
    }

    @Test
    @Order(0)
    void getToken() {
        specification = new RequestSpecBuilder()
                .setPort(TestConfigs.DEFINED_PORT)
                .setBasePath("/departamentos")
                .addHeader("Authorization", token)
                .build();

        responseDTO = given(specification)
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
    @Order(1)
    void salvar() {
        specification = new RequestSpecBuilder()
                .setPort(TestConfigs.DEFINED_PORT)
                .setBasePath("/departamentos")
                .addHeader("Authorization", token)
                .build();

        responseDTO = given(specification)
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
    @Order(2)
    void atualizar() {

        requestDTO.setNome("Matemática");

        responseDTO = given(specification)
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