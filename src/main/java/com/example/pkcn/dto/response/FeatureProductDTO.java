package com.example.pkcn.dto.response;

import java.math.BigDecimal;

public class FeatureProductDTO {
    private Integer id;
    private String name;
    private String subtitle;         // từ products.subtitle
    private BigDecimal minPrice;     // từ products.min_price
    private String mainImage;        // từ product_images.url_image where is_main=1
    private Double avgStar;          // AVG(reviews.num_star)

    public FeatureProductDTO(Integer id, String name, String subtitle, BigDecimal minPrice, String mainImage, Double avgStar) {
        this.id = id;
        this.name = name;
        this.subtitle = subtitle;
        this.minPrice = minPrice;
        this.mainImage = mainImage;
        this.avgStar = avgStar;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getSubtitle() { return subtitle; }
    public BigDecimal getMinPrice() { return minPrice; }
    public String getMainImage() { return mainImage; }
    public Double getAvgStar() { return avgStar; }
}
