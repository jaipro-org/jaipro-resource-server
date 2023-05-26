package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.service.specialist.impl.MyService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "${service.ingress.context-path}/experimental")
@AllArgsConstructor
public class FileUploadExperimentalController {

    private MyService myService;

    @RequestMapping(value = "/simple", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Mono<String>> processPost(@RequestParam String input) {
        String response = myService.doSomething(input);
        return ResponseEntity.ok(Mono.just(response));
    }

    @PostMapping(value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<CustomDto> retrieveFileName(@RequestPart FilePart filePart,
                                            @RequestPart CustomDto custom) {
        custom.setInputImageName(filePart.filename());
        return Mono.just(custom);
    }

    @Setter
    @Getter
    public static class CustomDto {
        private UUID id;
        private List<String> imageURLs;
        private String inputImageName;
    }
}
