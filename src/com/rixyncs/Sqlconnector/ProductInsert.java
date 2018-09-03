package com.rixyncs.Sqlconnector;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
// fetching the data from sql Database and pushing the into zoho crm  
public class ProductInsert {
    Properties mainProperties = new Properties();
	static String todayDate = getTodaydate().toString();
	
	//prints the current date when the jar is run on console
	  	public static String getTodaydate() {
		    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		    Date date = new Date();
		    System.out.println(dateFormat.format(date));
		    return dateFormat.format(date);
	    }
	  	
	  //loading the property life 
		public void loadProperties() throws IOException{
	  		    String path = "./Rix.properties";
			    FileInputStream file = new FileInputStream(path);
			    mainProperties.load(file);
			    file.close();
		 }
		
	 //fetching the sql data in arraylist format 
  	    public ArrayList<ProductGetSet> getAllProducts()throws ClassNotFoundException, SQLException, IOException, ParseException {
		      Connection myConn = null;
		      PreparedStatement myStmt = null;
		      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		      String sqlurl = mainProperties.getProperty("SqlURL");
		      myConn = DriverManager.getConnection(sqlurl);
		      Statement stm = myConn.createStatement();
		      String sql =mainProperties.getProperty("sqlQuery");
		      ResultSet rs = stm.executeQuery(sql);
		      ArrayList<ProductGetSet> ProductList = new ArrayList();
		      
		      while (rs.next()) {
			      ProductGetSet product = new ProductGetSet();
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
			      product = null;
		      }
	     return ProductList;
  	    }
	  	
	  //zoho xml format
		public String[] formZohoXml( ArrayList<ProductGetSet> product ) throws ClassNotFoundException, SQLException, IOException, ParseException{
	  				  
		  //converting the sql arraylist into zoho xml data
		    String[] s = new String[product.size()];
		    int count=0;
		    int i = 0;
		  // String row = "<Products>";
		    String row="";
		    for (i = 0; i < product.size(); i++) {
		      int index = i + 1;
		      row = row.concat("<row no=\"" + index + "\"><FL val=\"Product Name\"><![CDATA[" + ((ProductGetSet)product.get(i)).getproductName() + "]]></FL>");
		      row = row.concat("<FL val=\"Product Description\"><![CDATA[" + ((ProductGetSet)product.get(i)).getProductDescription() + "]]></FL>");
		      row = row.concat("<FL val=\"Item Brand Desc\"><![CDATA[" + ((ProductGetSet)product.get(i)).getItemBrandDesc() + "]]></FL>");
		      row = row.concat("<FL val=\"Country of Origin\"><![CDATA[" + ((ProductGetSet)product.get(i)).getCountryofOrigin() + "]]></FL>");
		      row = row.concat("<FL val=\"Barcode\"><![CDATA[" + ((ProductGetSet)product.get(i)).getBarcode() + "]]></FL>");
		      row = row.concat("<FL val=\"Item Size\"><![CDATA[" + ((ProductGetSet)product.get(i)).getItemsize() + "]]></FL>");
		      row = row.concat("<FL val=\"Unit Length\"><![CDATA[" + ((ProductGetSet)product.get(i)).getUnitlength() + "]]></FL>");
		      row = row.concat("<FL val=\"Unit Width\"><![CDATA[" + ((ProductGetSet)product.get(i)).getUnitwidth() + "]]></FL>");
		      row = row.concat("<FL val=\"Unit Height\"><![CDATA[" + ((ProductGetSet)product.get(i)).getUnitheight() + "]]></FL>");
		      row = row.concat("<FL val=\"Unit Weight\"><![CDATA[" + ((ProductGetSet)product.get(i)).getUnitweight() + "]]></FL>");
		      row = row.concat("<FL val=\"Unit Volume\"><![CDATA[" + ((ProductGetSet)product.get(i)).getUnitvolume() + "]]></FL>");
		      row = row.concat("<FL val=\"HS Commodity Code\"><![CDATA[" + ((ProductGetSet)product.get(i)).getHscommoditycode() + "]]></FL>");
		      row = row.concat("<FL val=\"Product Active\"><![CDATA[" + ((ProductGetSet)product.get(i)).getProductactive() + "]]></FL>");
		      row = row.concat("<FL val=\"Reference\"><![CDATA[" + ((ProductGetSet)product.get(i)).getReference() + "]]></FL>");
		      row = row.concat("<FL val=\"CAT Brand Desc\"><![CDATA[" + ((ProductGetSet)product.get(i)).getCatbranddesc() + "]]></FL>");
		      row = row.concat("<FL val=\"CAT Purchase Desc\"><![CDATA[" + ((ProductGetSet)product.get(i)).getCatpurchasedesc() + "]]></FL>");
		      row = row.concat("<FL val=\"CAT Sales Desc\"><![CDATA[" + ((ProductGetSet)product.get(i)).getCatsalesdesc() + "]]></FL>");
		      row = row.concat("<FL val=\"REF CSGP Desc\"><![CDATA[" + ((ProductGetSet)product.get(i)).getRefcsgpdesc() + "]]></FL>");
		      row = row.concat("<FL val=\"REF CSGS Desc\"><![CDATA[" + ((ProductGetSet)product.get(i)).getRefcsgsdesc() + "]]></FL>");
		      row = row.concat("<FL val=\"REF CSSC Desc\"><![CDATA[" + ((ProductGetSet)product.get(i)).getRefcsscdesc() + "]]></FL>");
		      row=  row.concat("<FL val=\"Last Update Date\"><![CDATA[" + ((ProductGetSet)product.get(i)).getItemlostupdatedate() + "]]></FL>");
		      row = row.concat("<FL val=\"Product Creation Date\"><![CDATA[" + ((ProductGetSet)product.get(i)).getCreationdate() + "]]></FL>");
		      row = row.concat("<FL val=\"KWD Price\"><![CDATA[" + ((ProductGetSet)product.get(i)).getKwdprice() + "]]></FL>");
		      row = row.concat("<FL val=\"BHD Price\"><![CDATA[" + ((ProductGetSet)product.get(i)).getBhdprice() + "]]></FL>");
		      row = row.concat("<FL val=\"OMR Price\"><![CDATA[" + ((ProductGetSet)product.get(i)).getOmanprice() + "]]></FL>");
		      row = row.concat("<FL val=\"Taxable\"><![CDATA[" + ((ProductGetSet)product.get(i)).getVatcategory() + "]]></FL>");
		      row = row.concat("<FL val=\"Tax\"><![CDATA[" + ((ProductGetSet)product.get(i)).getTaxrate() + "]]></FL>");
		      row = row.concat("<FL val=\"Market Price\"><![CDATA[" + ((ProductGetSet)product.get(i)).getMarketPrice() + "]]></FL></row>");
	     /* if (i == product.size() - 1){
		        row = "</Products>";
		      }*/
		      s[i] = row;
		      row = "";
		  }
		    return s;
	}
	    
  	 //pushing data into zoho crm
  	    public String productsInsert(String[] s){
		   
		    String xmlData = "";
		   
		  //inserting in batches of 100 records
		    String updateDetails="";
		    int count=0;
		    int insertedCount =0;
		    xmlData = "<Products>";
		    for(int i=0;i<s.length;i++){
		    	  xmlData = xmlData.concat(s[i]);
				   if(count==100){
					   
					   xmlData =xmlData.concat("</Products>");
					   System.out.println("count values "+count+"   "+i);
 	                   updateDetails=insertBatch(xmlData);
         			   System.out.println(xmlData);
         			   count=0;
           			   insertedCount=insertedCount+100;
         			   xmlData="";
         			   xmlData="<Products>";
				   }
	  			  count=count+1;
	  			 
		    }
		    System.out.println("count "+count+" insertedCount "+insertedCount);
		    if(count>0){
		    	xmlData=("<Products>");
		    	for(int i=insertedCount;i<s.length;i++){
		    	   xmlData = xmlData.concat(s[i]);
		    	}
				   xmlData =xmlData.concat("</Products>");
				   System.out.println("count values for less than 100 records");
				   updateDetails=insertBatch(xmlData);
				   
			}
			return updateDetails;
		    
	       
		  }
  	  //inserting more than 100 records 
  	  public String insertBatch(String xmlData){
			String updateDetails = null;
			String authtoken = mainProperties.getProperty("authtoken");
			String scope = "crmapi";
			String newFormat = "2";
			String version = "4";
			String duplicatecheck="2";
			String fromIndex = "1";
			String toIndex = "20";
			String targetURL = mainProperties.getProperty("targetURL1");
			String paramname = "content";
			PostMethod post = new PostMethod(targetURL);
			post.setParameter("authtoken", authtoken);
			post.setParameter("scope", scope);
			post.setParameter("newFormat", newFormat);
			post.setParameter("duplicateCheck",duplicatecheck);
			post.setParameter("xmlData", xmlData);
			post.setParameter("version", version);
			  
			   //System.out.println(xmlData);
			    HttpClient httpclient = new HttpClient();
		   	    try{
				      int result = httpclient.executeMethod(post);
				      updateDetails = post.getResponseBodyAsString();
				      System.out.println(updateDetails);
				    }
			    catch (Exception e){
				      e.printStackTrace();
			    }
			    finally {
			      post.releaseConnection();
			    }
		   	    post = null;
		   	    httpclient = null;
		   	    return updateDetails;
  	  }
  	  
  	/*public ArrayList<ProductGetSet> testData(int x) {
		  ArrayList<ProductGetSet> p=new ArrayList();
		  for(int i=0;i<x;i++)
		  {
			  ProductGetSet p1=new ProductGetSet();
			  p1.setproductName(""+i);
			  p.add(p1);
		  }
		  return p;
	  }*/
	
  //Main method 
	  public static void main(String[] args) throws ClassNotFoundException, SQLException, NullPointerException, IOException, ParseException{
		  try{
		    	ProductInsert pi=new ProductInsert();
		    	pi.loadProperties();
		    	ArrayList<ProductGetSet> product = pi.getAllProducts();
		         //ArrayList<ProductGetSet> product=pi.testData(1160);		    	
		    	String[] als=pi.formZohoXml(product);		    	
		    	pi.productsInsert(als);		    	
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
   	  }
	 
}




