package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.service.specialist.impl.MyService;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.PathResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

import static java.util.UUID.fromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadExperimentalControllerTest {

    public static final String FILE_NAME_FOR_TESTS = "image-jaipro-demo.jpg";
    public static final String UUID_STR = "4fbe22ad-f876-4893-816b-42011d10c770";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MyService myService;

    @Value("${service.ingress.context-path}")
    private String basePathURL;

    @Test
    public void testPost() throws Exception {
        when(myService.doSomething(anyString())).thenAnswer((Answer<String>) invocation -> {
            String input = invocation.getArgument(0);
            return input + " mocked";
        });

        String response = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path(basePathURL + "/experimental/simple")
                        .queryParam("input", "is")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(response, "is mocked");
    }

    @Test
    public void testUploadFile() throws Exception {
        FileUploadExperimentalController.CustomDto customDto = new FileUploadExperimentalController.CustomDto();
        customDto.setId(fromString(UUID_STR));
        customDto.setImageURLs(List.of("image1", "image2", "image3"));

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("filePart", retrieveDemoImage());
        multipartBodyBuilder.part("custom", customDto);

        FileUploadExperimentalController.CustomDto response = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path(basePathURL + "/experimental/upload")
                        .build())
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(FileUploadExperimentalController.CustomDto.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(response.getInputImageName(), FILE_NAME_FOR_TESTS);
        assertEquals(response.getId().toString(), UUID_STR);
        assertEquals(response.getImageURLs().size(), 3);
    }

    private PathResource retrieveDemoImage() {
        try {
            String path = "src/test/resources/images/" + "image-jaipro-demo.jpg";
            return new PathResource(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
