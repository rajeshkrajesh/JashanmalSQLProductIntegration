package com.rixyncs.Sqlconnector;

public class LineItemGetSet {
	
	private String Invoicename;
	private String ProductName;
	private String Quantity;
	private String SoldPrice;
	private String Tax;
	private String FullPrice;
	private String ArabicDescription;
	private String salesRepName;
//	private String Subject;
	
	public String getInvoicename() {
		return Invoicename;
	}
	public void setInvoicename(String invoicename) {
		Invoicename = invoicename;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	public String getSoldPrice() {
		return SoldPrice;
	}
	public void setSoldPrice(String soldPrice) {
		SoldPrice = soldPrice;
	}
	public String getTax() {
		return Tax;
	}
	public void setTax(String tax) {
		Tax = tax;
	}
	public String getFullPrice() {
		return FullPrice;
	}
	public void setFullPrice(String fullPrice) {
		FullPrice = fullPrice;
	}
	public String getArabicDescription() {
		return ArabicDescription;
	}
	public void setArabicDescription(String arabicDescription) {
		ArabicDescription = arabicDescription;
	}
	public String getSalesRepName() {
		return salesRepName;
	}
	public void setSalesRepName(String salesRepName) {
		this.salesRepName = salesRepName;
	}
	

}
