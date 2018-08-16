package com.rixyncs.Sqlconnector;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class ProductInsert {
	
	  static String TodayDate = getTodaydate().toString();
	  
	    public static String getTodaydate() {
	    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
	    Date date = new Date();
	    System.out.println(dateFormat.format(date));
	    return dateFormat.format(date);
	  }
	  
	  static Properties mainProperties = new Properties();
	  
	  public static void main(String[] args) throws ClassNotFoundException, SQLException, NullPointerException, IOException
	  {
	    ProductInsert pi = new ProductInsert();
	    ArrayList<Product> product = pi.getAllProducts();
	    String[] s = new String[product.size()];
	    int count=0;
	    int i = 0;
	    String row = "<Products>";
	    for (i = 0; i < product.size(); i++) {
	      int index = i + 1;
	      row = row.concat("<row no=\"" + index + "\"><FL val=\"Product Name\"><![CDATA[" + ((Product)product.get(i)).getproductName() + "]]></FL>");
	      row = row.concat("<FL val=\"Product Description\"><![CDATA[" + ((Product)product.get(i)).getProductDescription() + "]]></FL>");
	      row = row.concat("<FL val=\"Item Brand Desc\"><![CDATA[" + ((Product)product.get(i)).getItemBrandDesc() + "]]></FL>");
	      row = row.concat("<FL val=\"Country of Origin\"><![CDATA[" + ((Product)product.get(i)).getCountryofOrigin() + "]]></FL>");
	      row = row.concat("<FL val=\"Barcode\"><![CDATA[" + ((Product)product.get(i)).getBarcode() + "]]></FL>");
	      row = row.concat("<FL val=\"Item Size\"><![CDATA[" + ((Product)product.get(i)).getItemsize() + "]]></FL>");
	      row = row.concat("<FL val=\"Unit Length\"><![CDATA[" + ((Product)product.get(i)).getUnitlength() + "]]></FL>");
	      row = row.concat("<FL val=\"Unit Width\"><![CDATA[" + ((Product)product.get(i)).getUnitwidth() + "]]></FL>");
	      row = row.concat("<FL val=\"Unit Height\"><![CDATA[" + ((Product)product.get(i)).getUnitheight() + "]]></FL>");
	      row = row.concat("<FL val=\"Unit Weight\"><![CDATA[" + ((Product)product.get(i)).getUnitweight() + "]]></FL>");
	      row = row.concat("<FL val=\"Unit Volume\"><![CDATA[" + ((Product)product.get(i)).getUnitvolume() + "]]></FL>");
	      row = row.concat("<FL val=\"HS Commodity Code\"><![CDATA[" + ((Product)product.get(i)).getHscommoditycode() + "]]></FL>");
	      row = row.concat("<FL val=\"Product Active\"><![CDATA[" + ((Product)product.get(i)).getProductactive() + "]]></FL>");
	      row = row.concat("<FL val=\"Reference\"><![CDATA[" + ((Product)product.get(i)).getReference() + "]]></FL>");
	      row = row.concat("<FL val=\"CAT Brand Desc\"><![CDATA[" + ((Product)product.get(i)).getCatbranddesc() + "]]></FL>");
	      row = row.concat("<FL val=\"CAT Purchase Desc\"><![CDATA[" + ((Product)product.get(i)).getCatpurchasedesc() + "]]></FL>");
	      row = row.concat("<FL val=\"CAT Sales Desc\"><![CDATA[" + ((Product)product.get(i)).getCatsalesdesc() + "]]></FL>");
	      row = row.concat("<FL val=\"REF CSGP Desc\"><![CDATA[" + ((Product)product.get(i)).getRefcsgpdesc() + "]]></FL>");
	      row = row.concat("<FL val=\"REF CSGS Desc\"><![CDATA[" + ((Product)product.get(i)).getRefcsgsdesc() + "]]></FL>");
	      row = row.concat("<FL val=\"REF CSSC Desc\"><![CDATA[" + ((Product)product.get(i)).getRefcsscdesc() + "]]></FL>");
	     // row = row.concat("<FL val=\"Last Update Date\"><![CDATA[" + ((Product)product.get(i)).getItemlostupdatedate() + "]]></FL>");
	     //row = row.concat("<FL val=\"Product Creation Date\"><![CDATA[" + ((Product)product.get(i)).getCreationdate() + "]]></FL>");
	      row = row.concat("<FL val=\"KWD Price\"><![CDATA[" + ((Product)product.get(i)).getKwdprice() + "]]></FL>");
	      row = row.concat("<FL val=\"BHD Price\"><![CDATA[" + ((Product)product.get(i)).getBhdprice() + "]]></FL>");
	      row = row.concat("<FL val=\"OMR Price\"><![CDATA[" + ((Product)product.get(i)).getOmanprice() + "]]></FL>");
	      row = row.concat("<FL val=\"Taxable\"><![CDATA[" + ((Product)product.get(i)).getVatcategory() + "]]></FL>");
	      row = row.concat("<FL val=\"Tax\"><![CDATA[" + ((Product)product.get(i)).getTaxrate() + "]]></FL>");
	      row = row.concat("<FL val=\"Market Price\"><![CDATA[" + ((Product)product.get(i)).getMarketPrice() + "]]></FL></row>");
	      
	      if (i == product.size() - 1)
	      {
	        row = "</Products>";
	      }
	      s[i] = row;
	      row = "";
	      
	    }
	   
	    
	    ArrayList<Product> products = null;
	    try {
	      products = pi.getAllProducts();
	    }
	    catch (IOException ioe) {
	      ioe.printStackTrace();
	    }
	   for(i=0;i<product.size();i++){
		   if(count==100){
			   count=0;
			   pi.ProductsInsert(s);
		   }
	   }
	   if(count>0)
	   {
		   pi.ProductsInsert(s);
	   }
	   // pi.getProductDetails();
	    //pi.ProductsInsert(s);
	   
	  }
	  
public ArrayList<Product> getAllProducts()
	    throws ClassNotFoundException, SQLException, IOException
	  {
	    String path = "./Rix.properties";
	    FileInputStream file = new FileInputStream(path);
	    mainProperties.load(file);
	    file.close();
	    Connection myConn = null;
	    PreparedStatement myStmt = null;
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    String sqlurl = mainProperties.getProperty("SqlURL");
	    myConn = DriverManager.getConnection(sqlurl);
	    Statement stm = myConn.createStatement();
	    String sql = "SELECT TOP 1000 [ITEM_CODE],[BARCODE],[ITEM_DESC],[ITEM_BRAND_DESC],[ITEM_SIZE],[UNIT_LENGTH] ,[UNIT_WIDTH] ,[UNIT_HEIGHT] ,[UNIT_WEIGHT] ,[UNIT_VOLUME],[COUNTRY_OF_ORIGIN],[HS_COMMODITY_CODE],[PRODUCT_ACTIVE],[REFERENCE],[MARKET_PRICE_AED],[CAT_BRAND_DESC] ,[CAT_PURCHASE_DESC],[CAT_SALES_DESC],[REF_CSGP_DESC],[REF_CSGS_DESC],[REF_CSSC_DESC] ,[ITEM_LAST_UPDATE_DATE],[CREATION_DATE] ,[KWD_PRICE],[BHD_PRICE] ,[OMAN_PRICE],[VAT_CATEGORY],[TAX_RATE]FROM [dwsdev].[dbo].[JncdmItemDimCrmVW]";
	    ResultSet rs = stm.executeQuery(sql);
	    ArrayList<Product> ProductList = new ArrayList();
	    while (rs.next()) {
	      Product product = new Product();
	      product.setproductName(rs.getString("ITEM_CODE"));
	      product.setProductDescription(rs.getString("ITEM_DESC"));
	      product.setItemBrandDesc(rs.getString("ITEM_BRAND_DESC"));
	      product.setCountryofOrigin(rs.getString("COUNTRY_OF_ORIGIN"));
	      product.setMarketPrice(rs.getString("MARKET_PRICE_AED"));
	      product.setBarcode(rs.getString("BARCODE"));
	      product.setItemsize(rs.getString("ITEM_SIZE"));
	      product.setUnitlength(rs.getString("UNIT_LENGTH"));
	      product.setUnitwidth(rs.getString("UNIT_WIDTH"));
	      product.setUnitheight(rs.getString("UNIT_HEIGHT"));
	      product.setUnitweight(rs.getString("UNIT_WEIGHT"));
	      product.setUnitvolume(rs.getString("UNIT_VOLUME"));
	      product.setHscommoditycode(rs.getString("HS_COMMODITY_CODE"));
	      product.setProductactive(rs.getString("PRODUCT_ACTIVE"));
	      product.setReference(rs.getString("REFERENCE"));
	      product.setCatbranddesc(rs.getString("CAT_BRAND_DESC"));
	      product.setCatpurchasedesc(rs.getString("CAT_PURCHASE_DESC"));
	      product.setCatsalesdesc(rs.getString("CAT_SALES_DESC")); 
	      product.setRefcsgpdesc(rs.getString("REF_CSGP_DESC"));
	      product.setRefcsgsdesc(rs.getString("REF_CSGS_DESC"));
	      product.setRefcsscdesc(rs.getString("REF_CSSC_DESC"));
	      product.setItemlostupdatedate(rs.getString("ITEM_LAST_UPDATE_DATE"));
	      product.setCreationdate(rs.getString("CREATION_DATE"));
	      product.setKwdprice(rs.getString("KWD_PRICE"));
	      product.setBhdprice(rs.getString("BHD_PRICE"));
	      product.setOmanprice(rs.getString("OMAN_PRICE"));
	      product.setVatcategory(rs.getString("VAT_CATEGORY"));
	      product.setTaxrate(rs.getString("TAX_RATE"));
	      ProductList.add(product);
	    }
	    return ProductList;
	  }
	  
     public String ProductsInsert(String[] s)
	  {
	    String updateDetails = null;
	    String authtoken = mainProperties.getProperty("authtoken");
	    String scope = "crmapi";
	    String newFormat = "2";
	    String version = "4";
	    String duplicatecheck="1";
	    String fromIndex = "1";
	    String toIndex = "20";
	    String targetURL = mainProperties.getProperty("targetURL");
	    String paramname = "content";
	    
	    PostMethod post = new PostMethod(targetURL);
	    
	    post.setParameter("authtoken", authtoken);
	    post.setParameter("scope", scope);
	    post.setParameter("newFormat", newFormat);
	    String xmlData = "";
	    for (int i = 0; i < s.length; i++) {
	      xmlData = xmlData.concat(s[i]);
	    }
	    
	    post.setParameter("xmlData", xmlData);
	    post.setParameter("version", version);
	    post.setParameter("duplicate", duplicatecheck);
	    System.out.println(xmlData);
	    
	    HttpClient httpclient = new HttpClient();
	    



	    try
	    {
	      int result = httpclient.executeMethod(post);
	      updateDetails = post.getResponseBodyAsString();

	    }
	    catch (Exception e)
	    {

	      e.printStackTrace();
	    }
	    finally {
	      post.releaseConnection();
	    }
	    
	    return updateDetails;
	  }
	  

	  public String getProductDetails()
	  {
	    String productDetails = null;
	    
	    String authtoken = mainProperties.getProperty("authtoken");
	    String scope = "crmapi";
	    String selectColumns = "Products(Product Name)";
	    String newFormat = "2";
	    String fromIndex = "2";
	    String toIndex = "20";
	    String wfTrigger = "true";
	    String criteria = "(Trigger Date:" + TodayDate + ")";
	    
	    String targetURL = mainProperties.getProperty("targetURL1");
	    System.out.println("Successful");
	    
	    String paramname = "content";
	    PostMethod post = new PostMethod(targetURL);
	    post.setParameter("authtoken", authtoken);
	    post.setParameter("scope", scope);
	    post.setParameter("newFormat", newFormat);
	    post.setParameter("selectColumns", selectColumns);
	    post.setParameter("fromIndex", fromIndex);
	    post.setParameter("toIndex", toIndex);
	    post.setParameter("wfTrigger", wfTrigger);
	    post.setParameter("criteria", criteria);
	    

	    HttpClient httpclient = new HttpClient();
	    try {
	      long t1 = System.currentTimeMillis();
	      int result = httpclient.executeMethod(post);
	      
	      productDetails = post.getResponseBodyAsString();
	      
	      post.getParameter("Product Name");



	    }
	    catch (Exception localException) {}finally
	    {



	      post.releaseConnection();
	    }
	    
	    return productDetails;
	  }
	}




