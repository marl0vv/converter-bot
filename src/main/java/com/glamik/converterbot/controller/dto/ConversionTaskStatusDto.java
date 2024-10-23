package com.glamik.converterbot.controller.dto;

import com.glamik.converterbot.enums.ConversionTaskStatus;
import com.glamik.converterbot.enums.ErrorMessage;
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
