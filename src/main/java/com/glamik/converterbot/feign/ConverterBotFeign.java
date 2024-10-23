package com.glamik.converterbot.feign;

import com.glamik.converterbot.feign.dto.ConversionTaskStatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "webpconverter")
public interface ConverterBotFeign {

    @GetMapping("/convert-to-webp/async/{taskId}/status")
    ConversionTaskStatusDto getTaskStatus(@PathVariable("taskId") UUID taskId);
}
