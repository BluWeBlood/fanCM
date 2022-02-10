package com.jsc.fanCM.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class BoardModifyForm {
    private Long id;

    @NotBlank
    private String name;
    private String detail;
}
