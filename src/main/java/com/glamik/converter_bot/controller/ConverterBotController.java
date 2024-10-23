package com.glamik.converter_bot.controller;

import com.glamik.converter_bot.controller.dto.ConversionTaskStatusDto;
import com.glamik.converter_bot.exception.ConversionTaskNotFoundException;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@RestController
public class ConverterBotController {

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    public ConverterBotController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
        this.discoveryClient = discoveryClient;
        restClient = restClientBuilder.build();
    }

    @GetMapping("/bot/{taskId}/status")
    public ConversionTaskStatusDto getTaskStatus(@PathVariable UUID taskId) {
        ServiceInstance serviceInstance = discoveryClient.getInstances("webpconverter").get(0);
        try {
            return restClient.get()
                    .uri(serviceInstance.getUri() + "/convert-to-webp/async/" + taskId + "/status")
                    .retrieve()
                    .body(ConversionTaskStatusDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ConversionTaskNotFoundException("Conversion task with id " + taskId + " was not found");
        }
    }

}
