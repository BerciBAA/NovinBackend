package com.random.security.account.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    @NotNull(message = "The CUSTOMERNAME should be not null!")
    @NotEmpty(message = "The CUSTOMERNAME should be not empty!")
    @NotBlank(message = "The CUSTOMERNAME should be not blank!")
    private String customerName;
    @NotNull(message = "The EXHIBITIONDATE should be not null!")
    @NotEmpty(message = "The EXHIBITIONDATE should be not empty!")
    @NotBlank(message = "The EXHIBITIONDATE should be not blank!")
    private LocalDateTime exhibitionDate;
    @NotNull(message = "The DUEDATE should be not null!")
    @NotEmpty(message = "The DUEDATE should be not empty!")
    @NotBlank(message = "The DUEDATE should be not blank!")
    private LocalDateTime dueDate;
    @NotNull(message = "The ITEMNAME should be not null!")
    @NotEmpty(message = "The ITEMNAME should be not empty!")
    @NotBlank(message = "The ITEMNAME should be not blank!")
    private String itemName;
    @NotNull(message = "The COMMENT should be not null!")
    @NotEmpty(message = "The COMMENT should be not empty!")
    @NotBlank(message = "The COMMENT should be not blank!")
    private String comment;
    @NotNull(message = "The PRICE should be not null!")
    @NotEmpty(message = "The PRICE should be not empty!")
    @NotBlank(message = "The PRICE should be not blank!")
    private Integer price;
}
