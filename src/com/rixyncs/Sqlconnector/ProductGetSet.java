package com.rixyncs.Sqlconnector;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.TimeZone;
//getter and setter method of arraylist Product sqldata
public class ProductGetSet {
	
	  private String productName;
	  private String ProductDescription;
	  private String ItemBrandDesc;
	  private String CountryofOrigin;
	  private String MarketPrice;
	  private String barcode;
	  private String itemsize;
	  private String unitlength;
	  private String unitwidth;
	  private String unitheight;
	  private String unitweight;
	  private String unitvolume;
	  private String countryoforigin;
	  private String hscommoditycode;
	  private String productactive;
	  private String reference;
	  private String catbranddesc;
	  private String catpurchasedesc;
	  private String catsalesdesc;
	  private String refcsgpdesc;
	  private String refcsgsdesc;
	  private String refcsscdesc;
	  private String itemlostupdatedate;
	  private String creationdate;
	  private String kwdprice;
	  private String bhdprice;
	  private String omanprice;
	  private String vatcategory;
	  private String taxrate;
    
		  public ProductGetSet() {}
		  public String getproductName() {
			  return productName; 
			  }
		  public void setproductName(String productName) {
		      this.productName = productName;
		  }
		  public String getProductDescription() { 
			  return ProductDescription; 
			  }
		  public void setProductDescription(String ProductDescription) {
		      this.ProductDescription = ProductDescription;
		  }
		  public String getItemBrandDesc() { 
			  return ItemBrandDesc; 
			  }
		  public void setItemBrandDesc(String ItemBrandDesc) {
		      this.ItemBrandDesc = ItemBrandDesc;
		  }
		  public String getCountryofOrigin() { 
			  return CountryofOrigin; 
			  }
		  public void setCountryofOrigin(String CountryofOrigin) {
		      this.CountryofOrigin = CountryofOrigin;
		  }
		  public String getMarketPrice() { 
			  return MarketPrice; 
			  }
		  public void setMarketPrice(String MarketPrice) {
		      this.MarketPrice = MarketPrice;
		  }
	      public String getBarcode() {
			  return barcode;
		    }
		 public void setBarcode(String barcode) {
			  this.barcode = barcode;
		  }
		 public String getItemsize() {
			return itemsize;
		    }
		 public void setItemsize(String itemsize) {
			this.itemsize = itemsize;
		  }
		 public String getUnitlength() {
			return unitlength;
		    }
		 public void setUnitlength(String unitlength) {
			this.unitlength = unitlength;
		}
		public String getUnitwidth() {
			return unitwidth;
		    }
	    public void setUnitwidth(String unitwidth) {
			this.unitwidth = unitwidth;
		}
		public String getUnitheight() {
			return unitheight;
		    }
		public void setUnitheight(String unitheight) {
			this.unitheight = unitheight;
		}
		public String getUnitweight() {
			return unitweight;
		    }
		public void setUnitweight(String unitweight) {
			this.unitweight = unitweight;
		}
		public String getUnitvolume() {
			return unitvolume;
		    }
		public void setUnitvolume(String unitvolume) {
			this.unitvolume = unitvolume;
		}
		public String getCountryoforigin() {
			return countryoforigin;
		    }
		public void setCountryoforigin(String countryoforigin) {
			this.countryoforigin = countryoforigin;
		}
		public String getHscommoditycode() {
			return hscommoditycode;
		    }
		public void setHscommoditycode(String hscommoditycode) {
			this.hscommoditycode = hscommoditycode;
		}
		public String getProductactive() {
			return productactive;
		    }
		public void setProductactive(String productactive) {
			this.productactive = productactive;
		}
		public String getCatbranddesc() {
			return catbranddesc;
		  	}
		public void setCatbranddesc(String catbranddesc) {
			this.catbranddesc = catbranddesc;
		}
		public String getReference() {
			return reference;
			}
		public void setReference(String reference) {
			this.reference = reference;
		}
		public String getCatpurchasedesc() {
			return catpurchasedesc;
			}
		public void setCatpurchasedesc(String catpurchasedesc) {
			this.catpurchasedesc = catpurchasedesc;
		}
		public String getCatsalesdesc() {
			return catsalesdesc;
			}
		public void setCatsalesdesc(String catsalesdesc) {
			this.catsalesdesc = catsalesdesc;
		}
		public String getRefcsgpdesc() {
			return refcsgpdesc;
			}
		public void setRefcsgpdesc(String refcsgpdesc) {
			this.refcsgpdesc = refcsgpdesc;
		}
		public String getRefcsgsdesc() {
			return refcsgsdesc;
			}
		public void setRefcsgsdesc(String refcsgsdesc) {
			this.refcsgsdesc = refcsgsdesc;
		}
		public String getRefcsscdesc() {
			return refcsscdesc;
			}
		public void setRefcsscdesc(String refcsscdesc) {
			this.refcsscdesc = refcsscdesc;
		}
		public String getItemlostupdatedate() {
			return itemlostupdatedate;
			}
		public void setItemlostupdatedate(String itemlostupdatedate) throws ParseException {
			SimpleDateFormat inFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date inDate = inFormatter.parse(itemlostupdatedate);
		    SimpleDateFormat outFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		    String output = outFormatter.format(inDate);
		    this.itemlostupdatedate = output;
				}
		public String getCreationdate() {
			return creationdate;
			}
		public void setCreationdate(String creationdate) throws ParseException  {
			SimpleDateFormat inFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date inDate = inFormatter.parse(creationdate);
		    SimpleDateFormat outFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		    String output = outFormatter.format(inDate);
			this.creationdate = output;
			 // System.out.println(output);
		}
		public String getKwdprice() {
			return kwdprice;
			}
		public void setKwdprice(String kwdprice) {
			this.kwdprice = kwdprice;
		}
		public String getBhdprice() {
			return bhdprice;
			}
		public void setBhdprice(String bhdprice) {
			this.bhdprice = bhdprice;
		}
		public String getOmanprice() {
			return omanprice;
			}
		public void setOmanprice(String omanprice) {
			this.omanprice = omanprice;
		}
		public String getVatcategory() {
			return vatcategory;
			}
		public void setVatcategory(String vatcategory) {
			this.vatcategory = vatcategory;
		}
		public String getTaxrate() {
			return taxrate;
			}
		public void setTaxrate(String taxrate) {
			this.taxrate = taxrate;
		}
}
		
