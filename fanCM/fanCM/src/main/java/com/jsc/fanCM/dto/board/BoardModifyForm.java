package com.jsc.fanCM.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardModifyForm {
    private Long id;
    private String name;
    private String detail;
}
