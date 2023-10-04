package com.random.security.account.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Integer id;
    private String customerName;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime exhibitionDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime dueDate;
    private String itemName;
    private String comment;
    private Integer price;
}
