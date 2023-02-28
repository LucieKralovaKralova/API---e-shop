package com.engeto.projektAPI;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;


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
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
       // super (message);
        return new ErrorResponse(e.getMessage(), LocalDateTime.now());
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
    public Product saveItem(@RequestBody Product product) throws SQLException, NotFoundException {
        if (productService.validationName(product.getName())) {
            productService.saveNewItem(product);
        } else {
            throw new NotFoundException("Zadejte prosím název produktu! ");
        }
        return product;
    }

    @PutMapping("/productPrice/{id}")
    public void updatePriceById(@PathVariable("id") int id, @RequestParam("price") BigDecimal newPrice) throws SQLException{
        productService.updatePriceItem(id, newPrice);
    }

    @PutMapping("/product")
    public void updateProduct(@RequestBody Product product) throws SQLException, NotFoundException {
        if (productService.validationName(product.getName())) {
            productService.updateItem(product);
        } else {
            throw new NotFoundException("Zadejte prosím název produktu! ");
        }
    }

    @PutMapping("/product/{id}")
    public void updateProductById(@RequestBody Product product) throws SQLException, NotFoundException {
        if (productService.validationName(product.getName())) {
            productService.updateItem(product);
        } else {
            throw new NotFoundException("Zadejte prosím název produktu! ");
        }
    }

    @DeleteMapping("/product")
    public void deleteOutOfSaleItem () throws SQLException {
        productService.deleteNotForSaleItem();
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct (@PathVariable("id") int id) throws SQLException {
        productService.deleteItem(id);
    }
}
