package com.engeto.projektAPI;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


@CrossOrigin

@RestController
public class ProductController {

    ProductService productService;

    private static final String ERROR_MESSAGE_NOT_NAME = "Zadejte prosím název produktu! ";


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
    public List<Product> loadAllItems() throws SQLException {
        return productService.getAllItems();
    }

    @GetMapping("/products-for-sale")
    public List<Product> loadAllAvailableItems() throws SQLException {
        return productService.getAllAvailableItems();
    }

    @GetMapping("/products/{id}")
    public Product loadProductById(@PathVariable("id") int id) throws SQLException, NotFoundException, NullPointerException {
            return productService.getItemById(id);
    }

    @GetMapping("/products-part/{partNo}")
    public Product loadProductByPartNo(@PathVariable("partNo") Long partNo) throws SQLException, NotFoundException, NullPointerException {
            return productService.getItemByPartNo(partNo);
    }

    @PostMapping("/products")
    public Product saveItem(@RequestBody Product product) throws SQLException, NotFoundException {
        if (productService.validationName(product.getName())) {
            productService.saveNewItem(product);
        } else {
            throw new NotFoundException(ERROR_MESSAGE_NOT_NAME);
        }
        return product;
    }

    @PutMapping("/products-price/{id}")
    public void updatePriceById(@PathVariable("id") int id, @RequestParam("price") BigDecimal newPrice) throws SQLException, NotFoundException, NullPointerException {
            productService.updatePriceItem(id, newPrice);
    }

    @PutMapping("/products")
    public void updateProduct(@RequestBody Product product) throws SQLException, NotFoundException, NullPointerException {
            if (productService.validationName(product.getName())) {
                productService.updateItem(product);
            } else {
                throw new NotFoundException(ERROR_MESSAGE_NOT_NAME);
            }
       // productService.updateItem(product);
    }

    @PutMapping("/products/{id}")
    public void updateProductById(@PathVariable("id") int id, @RequestBody Product product) throws SQLException, NotFoundException, NullPointerException {
            if (productService.validationName(product.getName())) {
                productService.updateById(id, product);
            } else {
                throw new NotFoundException(ERROR_MESSAGE_NOT_NAME);
            }
      //  productService.updateById(id, product);
    }

    @DeleteMapping("/products")
    public void deleteOutOfSaleItem () throws SQLException, NotFoundException {
        productService.deleteNotForSaleItem();
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct (@PathVariable("id") int id) throws SQLException, NotFoundException, NullPointerException {
            productService.deleteItem(id);
    }



}
