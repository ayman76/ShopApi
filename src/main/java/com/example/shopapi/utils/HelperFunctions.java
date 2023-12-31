package com.example.shopapi.utils;

import com.example.shopapi.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public class HelperFunctions {
    public static <T, E> ApiResponse<T> getApiResponse(int pageNo, int pageSize, List<T> content, Page<E> films) {
        ApiResponse<T> filmDtoApiResponse = new ApiResponse<>();
        filmDtoApiResponse.setContent(content);
        filmDtoApiResponse.setPageNo(pageNo);
        filmDtoApiResponse.setPageSize(pageSize);
        filmDtoApiResponse.setTotalElements(films.getTotalElements());
        filmDtoApiResponse.setTotalPages(films.getTotalPages());
        filmDtoApiResponse.setLast(films.isLast());
        return filmDtoApiResponse;
    }

    public static ResponseEntity<StringBuilder> checkIfThereErrorsAndReturn(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(".\n"));
            return ResponseEntity.badRequest().body(errorMessage);
        }
        return null;
    }
}
