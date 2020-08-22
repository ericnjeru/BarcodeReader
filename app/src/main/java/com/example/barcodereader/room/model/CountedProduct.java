package com.example.barcodereader.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "counted_products")
public class CountedProduct implements Serializable {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("counted_quantity")
    @Expose
    private Integer countedQuantity;
    @SerializedName("identification_card")
    @Expose
    private String identificationCard;
    @SerializedName("business")
    @Expose
    private Integer business;
    @SerializedName("branch_office")
    @Expose
    private Integer branchOffice;

    private String product_id;

    private int uploaded = 0;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getCountedQuantity() {
        return countedQuantity;
    }

    public void setCountedQuantity(Integer countedQuantity) {
        this.countedQuantity = countedQuantity;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public Integer getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(Integer branchOffice) {
        this.branchOffice = branchOffice;
    }

    public String getProduct_id() {
        return product_id;
    }

    public int getUploaded() {
        return uploaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


}
