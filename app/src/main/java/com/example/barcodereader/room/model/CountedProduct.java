package com.example.barcodereader.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "counted_products")
public class CountedProduct {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("counted_quantity")
    @Expose
    private String countedQuantity;
    @SerializedName("identification_card")
    @Expose
    private String identificationCard;
    @SerializedName("business")
    @Expose
    private String business;
    @SerializedName("branch_office")
    @Expose
    private String branchOffice;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

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

    public String getCountedQuantity() {
        return countedQuantity;
    }

    public void setCountedQuantity(String countedQuantity) {
        this.countedQuantity = countedQuantity;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(String branchOffice) {
        this.branchOffice = branchOffice;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }


}
