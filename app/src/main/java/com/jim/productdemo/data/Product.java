package com.jim.productdemo.data;

/**
 * The representation of Product
 */
public class Product {
  private String productId;
  private String productName;
  private String shortDescription;
  private String longDescription;
  private String price;
  private String productImage;
  private String reviewRating;
  private int reviewCount;
  private boolean inStock;

  public String getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public String getShortDescription() {
    return null != shortDescription ? shortDescription : "";
  }

  public String getLongDescription() {
    return null != longDescription ? longDescription : "";
  }

  public String getPrice() {
    return price;
  }

  public String getProductImage() {
    return productImage;
  }

  public String getReviewRating() {
    return reviewRating;
  }

  /**
   * To make disaplay simple just round the review to int
   * @return
   */
  public int getReviewRatingRounded() {
    Float floatValue = Float.parseFloat(reviewRating);
    return  Math.round(floatValue);
  }

  public int getReviewCount() {
    return reviewCount;
  }

  public boolean isInStock() {
    return inStock;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Product product = (Product) o;

    return productId.equals(product.productId);
  }

  @Override public int hashCode() {
    return productId.hashCode();
  }

  @Override public String toString() {
    return "Product{" +
        "productId='" + productId + '\'' +
        ", productName='" + productName + '\'' +
        ", shortDescription='" + shortDescription + '\'' +
        ", longDescription='" + longDescription + '\'' +
        ", price='" + price + '\'' +
        ", productImage='" + productImage + '\'' +
        ", reviewRating='" + reviewRating + '\'' +
        ", reviewCount=" + reviewCount +
        ", inStock=" + inStock +
        '}';
  }
}
