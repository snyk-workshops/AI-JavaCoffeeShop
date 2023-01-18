package nl.brianvermeer.workshop.coffee.service;

import nl.brianvermeer.workshop.coffee.domain.Product;
import nl.brianvermeer.workshop.coffee.domain.ProductType;
import nl.brianvermeer.workshop.coffee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).get();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findByTextSearch(String criteria) {
        if (!criteria.contains("%")) {
            criteria = "%" + criteria + "%";
        }
        return productRepository.findByProductNameLikeOrDescriptionLikeAllIgnoreCase(criteria, criteria);
    }

    public List<Product> findByPrice(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> findByProductType(ProductType productType) {
        return productRepository.findByProductType(productType);
    }

}
