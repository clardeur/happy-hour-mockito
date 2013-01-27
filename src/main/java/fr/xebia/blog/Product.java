package fr.xebia.blog;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:clardeur@xebia.fr">Clement Lardeur</a>
 */
public class Product {

    private final BigDecimal price;

    public Product(BigDecimal price) {
        this.price = price != null ? price : BigDecimal.ZERO;
    }

    public BigDecimal getPrice() throws Exception {
        return price;
    }
}
