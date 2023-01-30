package com.engeto.projektAPI;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
 //@CrossOrigin
// @CrossOrigin(origins = "http://example.com")
// http://localhost:3000
// https://www.test-cors.org/
 // nginx nebo apache - reverzni proxy

@CrossOrigin
@RestController
public class ProductController {

    ProductService productService;

    public ProductController() throws SQLException {
        productService = new ProductService();
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleError(Exception e){
        return new ErrorResponse(e.getMessage(), LocalDateTime.now());
//
    }

    @GetMapping("/error")
    public Product testError(@RequestParam(value = "hodnota", required = false) String hodnota) throws Exception {
        throw new Exception("Chyba "+hodnota);
    }

    @GetMapping("/products")
    public Collection<Product> loadAllItems() throws SQLException {
        return productService.getAllItems();
    }

    @GetMapping("/productsForSale")
    public Collection<Product> loadAllAvailableItems() throws SQLException {
        return productService.getAllAvailableItems();
    }

    @GetMapping("/product/{id}")
    public Product loadProductById(@PathVariable("id") int id) throws SQLException {
        return productService.getItemById(id);
    }

    @GetMapping("/productPart/{partNo}")
    public Product loadProductByPartNo(@PathVariable("partNo") Long partNo) throws SQLException {
        return productService.getItemByPartNo(partNo);
    }

    @PostMapping("/product")
    public Product saveItem(@RequestBody Product product) throws SQLException {
        Integer generatedId = productService.saveNewItem(product);
        product.setId(generatedId);

        return product;
    }

    @PutMapping("/product/{id}/{price}")
    public void updatePriceById(@PathVariable("id") int id, @PathVariable("price")BigDecimal newPrice) throws SQLException{
        productService.updatePriceItem(id, newPrice);
    }

    @DeleteMapping("/product")
    public void deleteOutOfSaleItem () throws SQLException {
        productService.deleteNotForSaleItem();
    }
}