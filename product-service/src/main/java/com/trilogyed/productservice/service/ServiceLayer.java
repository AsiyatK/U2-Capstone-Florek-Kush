package com.trilogyed.productservice.service;

import com.trilogyed.productservice.dao.ProductDao;
import com.trilogyed.productservice.model.Product;
import com.trilogyed.productservice.model.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {

    private ProductDao dao;

    @Autowired
    public ServiceLayer(ProductDao dao) {
        this.dao = dao;
    }

    @Transactional
    public ProductViewModel saveProduct(Product product){
        product = dao.addProduct(product);
        return buildProductViewModel(product);
    }

    public ProductViewModel findProductById(int productId){
        Product product = dao.getProduct(productId);
        if(product == null) {
            return null;
        } else {
            return buildProductViewModel(product);
        }
    }

    public List<ProductViewModel> findAllProducts(){
        List<Product> products = dao.getAllProducts();
        List<ProductViewModel> productViewModels = new ArrayList<>();
        products.stream()
                .forEach(product -> productViewModels.add(buildProductViewModel(product)));
        return productViewModels;
    }

    @Transactional
    public void updateProduct(Product product){
        dao.updateProduct(product);
    }

    @Transactional
    public void removeProduct(int productId){
        dao.deleteProduct(productId);
    }




    //to build product view model
    private ProductViewModel buildProductViewModel(Product product){
        ProductViewModel pvm = new ProductViewModel();
        pvm.setProductId(product.getProductId());
        pvm.setProductName(product.getProductName());
        pvm.setProductDescription(product.getProductDescription());
        pvm.setListPrice(product.getListPrice());
        pvm.setUnitCost(product.getUnitCost());

        return pvm;
    }
}
