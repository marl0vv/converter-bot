package com.glamik.converter_bot.controller.dto;

import com.glamik.converter_bot.enums.ConversionTaskStatus;
import com.glamik.converter_bot.enums.ErrorMessage;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ConversionTaskStatusDto {
    private ConversionTaskStatus status;
    private ErrorMessage errorMessage;
}
