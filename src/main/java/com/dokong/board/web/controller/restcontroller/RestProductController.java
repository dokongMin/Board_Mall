//package com.dokong.board.web.controller.restcontroller;
//
//import com.dokong.board.web.dto.product.SaveProductDto;
//import com.dokong.board.web.service.ProductService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/items")
//public class RestProductController {
//
//    private final ProductService productService;
//    @PostMapping
//    public ResponseEntity<?> saveProduct(@Validated @RequestBody SaveProductDto saveProductDto) {
//        SaveProductDto saveProduct = productService.saveProduct(saveProductDto);
//        
//    }
//}
