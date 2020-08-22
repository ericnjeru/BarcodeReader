package com.example.barcodereader.room.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "products")
public class Product implements Serializable {

    @PrimaryKey()
    @NonNull
    @SerializedName("id")
    @Expose
    private String id = "";

    @SerializedName("producto")
    @Expose
    private String producto;
    @SerializedName("p_costo")
    @Expose
    private String pCosto;
    @SerializedName("p_venta")
    @Expose
    private String pVenta;
    @SerializedName("cod_interno")
    @Expose
    private String codInterno;
    @SerializedName("m_vencimiento")
    @Expose
    private String mVencimiento;
    @SerializedName("a_vencimiento")
    @Expose
    private String aVencimiento;
    @SerializedName("cod_barra")
    @Expose
    private String codBarra;
    @SerializedName("stock_cliente")
    @Expose
    private String stockCliente;
    @SerializedName("empresa")
    @Expose
    private String empresa;
    @SerializedName("sucursal")
    @Expose
    private String sucursal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPCosto() {
        return pCosto;
    }

    public void setPCosto(String pCosto) {
        this.pCosto = pCosto;
    }

    public String getPVenta() {
        return pVenta;
    }

    public void setPVenta(String pVenta) {
        this.pVenta = pVenta;
    }

    public String getCodInterno() {
        return codInterno;
    }

    public void setCodInterno(String codInterno) {
        this.codInterno = codInterno;
    }

    public String getMVencimiento() {
        return mVencimiento;
    }

    public void setMVencimiento(String mVencimiento) {
        this.mVencimiento = mVencimiento;
    }

    public String getAVencimiento() {
        return aVencimiento;
    }

    public void setAVencimiento(String aVencimiento) {
        this.aVencimiento = aVencimiento;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }

    public String getStockCliente() {
        return stockCliente;
    }

    public void setStockCliente(String stockCliente) {
        this.stockCliente = stockCliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
}
