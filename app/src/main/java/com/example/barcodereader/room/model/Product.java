package com.example.barcodereader.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "products")
public class Product {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("p_cost")
    @Expose
    private Integer pCost;
    @SerializedName("p_sale")
    @Expose
    private Integer pSale;
    @SerializedName("internal_code")
    @Expose
    private String internalCode;
    @SerializedName("m_expiration")
    @Expose
    private String mExpiration;
    @SerializedName("bar_code")
    @Expose
    private String barCode;
    @SerializedName("stock_client")
    @Expose
    private Integer stockClient;
    @SerializedName("business")
    @Expose
    private Integer business;
    @SerializedName("branch_office")
    @Expose
    private Integer branchOffice;
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

    public Integer getPCost() {
        return pCost;
    }

    public void setPCost(Integer pCost) {
        this.pCost = pCost;
    }

    public Integer getPSale() {
        return pSale;
    }

    public void setPSale(Integer pSale) {
        this.pSale = pSale;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getMExpiration() {
        return mExpiration;
    }

    public void setMExpiration(String mExpiration) {
        this.mExpiration = mExpiration;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getStockClient() {
        return stockClient;
    }

    public void setStockClient(Integer stockClient) {
        this.stockClient = stockClient;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
