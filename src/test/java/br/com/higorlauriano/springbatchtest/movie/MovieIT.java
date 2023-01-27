package br.com.higorlauriano.springbatchtest.movie;

import br.com.higorlauriano.springbatchtest.SpringBatchTestApplication;
import br.com.higorlauriano.springbatchtest.model.movie.Movie;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.apache.http.entity.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBatchTestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class MovieIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/movie";
    }

    @Test
    public void success_findOneExistent() {

        given()
                .when()
                .get("/{id}", 1L)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("id", Matchers.equalTo(1));
    }

    @Test
    public void success_findOneNonExistent() {

        given()
                .when()
                .get("/{id}", -1L)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void success_findPaged() {

        given()
                .when()
                .queryParams(Map.of("page", "0", "size", "1"))
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("content", hasSize(1));
    }

    @Test
    public void success_deleteExistent() {

        given()
                .when()
                .delete("/{id}", 1L)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void fail_deleteNonExistent() {

        given()
                .when()
                .delete("/{id}", -1L)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and()
                .body("message", equalTo("Object with ID -1 not found to delete"));
    }

    @Test
    public void sucess_save() {

        var movie = getMovieFromMockFile("./src/test/resources/mocks/movie.json");

        given()
                .when()
                .headers(Map.of(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()))
                .body(movie)
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void sucess_edit() {

        var movie = getMovieFromMockFile("./src/test/resources/mocks/movie.json");

        given()
                .when()
                .body(movie)
                .headers(Map.of(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()))
                .put("/{id}", 2L)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("title", equalTo(movie.getTitle()));
    }

    @Test
    public void fail_edit() {

        var movie = getMovieFromMockFile("./src/test/resources/mocks/movie.json");

        given()
                .when()
                .body(movie)
                .headers(Map.of(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()))
                .put("/{id}", -1L)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and()
                .body("message", equalTo("Object with ID -1 not found to update"));
    }

    private Movie getMovieFromMockFile(String filePath) {
        try {
            var string = new String(new FileSystemResource(filePath).getInputStream().readAllBytes());
            return new Gson().fromJson(string, Movie.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
