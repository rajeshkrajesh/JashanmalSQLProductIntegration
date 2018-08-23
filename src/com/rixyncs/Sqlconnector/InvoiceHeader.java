package com.rixyncs.Sqlconnector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

//pushing the invoice header data into zoho transaction module 
public class InvoiceHeader {
						
		public  Properties mainProperties = new Properties();
		public InvoiceHeader() throws IOException{		
		mainProperties=Utility.loadProperties();
		}
		
	//reading the csv 
		public ArrayList<InvoiceGetSet> getAllInvoice(){
			ArrayList<InvoiceGetSet> invoice=new  ArrayList<InvoiceGetSet>();
			 int rowCount = -1;
		        try {	         
		            String customercsv=mainProperties.getProperty("customercsvpath");
		            BufferedReader csvFileReader;
	   	            csvFileReader = new BufferedReader(new FileReader(customercsv));
		            
		            /* Initialize the number of fields to 0 */
		            int fieldCount = 0;
		            String[] csvFields = null;
		            StringTokenizer stringTokenizer = null;
		           
		            /* Initialize the current line variable */
		            String currLine = csvFileReader.readLine();		            
		            		           		            
		            /* reading rows from the CSV file */
		            while((currLine = csvFileReader.readLine()) != null) {
		            	InvoiceGetSet in=new InvoiceGetSet();
		                stringTokenizer = new StringTokenizer(currLine, ",");
		                fieldCount = stringTokenizer.countTokens();
		                /* if rows exist in the CSV file*/
		                if(fieldCount > 0) {
		                	
		                    /* Create the row element*/		                	
		                	in.setTranscationNo(String.valueOf(stringTokenizer.nextElement()));
		                	in.setStoreCode(String.valueOf(stringTokenizer.nextElement()));
		                	in.setStorecityID(String.valueOf(stringTokenizer.nextElement()));
		                	in.setStoreUniqueReference(String.valueOf(stringTokenizer.nextElement()));
		                	in.setPurchaseDateTime(String.valueOf(stringTokenizer.nextElement()));
		                	in.setCashierName(String.valueOf(stringTokenizer.nextElement()));
		                	in.setCurrency(String.valueOf(stringTokenizer.nextElement()));
		                	in.setTrxTotal(String.valueOf(stringTokenizer.nextElement()));
		                	in.setVATTotal(String.valueOf(stringTokenizer.nextElement()));
		                	in.setTotalQuantity(String.valueOf(stringTokenizer.nextElement()));
		                	//in.setCustomerName(String.valueOf(stringTokenizer.nextElement()));
		                	invoice.add(in);	                               	    
		                }
		               
		            }
		            csvFileReader.close();
		            /* Finish reading the CSV file */
		           
		        }catch(IOException exp) {
		            System.err.println(exp.toString());
		        }catch(Exception exp) {
		            System.err.println(exp.toString());
		        }
		        /* Number of rows parsed into XML */
		    return invoice;
		}
		
	//csv data to zoho xml form
		public String[] formZohoXml( ArrayList<InvoiceGetSet> invoice ) throws ClassNotFoundException, SQLException, IOException, ParseException{
			  
			  //converting the sql arraylist into zoho xml data			  
			    String[] s = new String[invoice.size()];
			    int count=0;
			    int i = 0;
  			    String row="";
			    for (i = 0; i < invoice.size(); i++) {
			      int index = i + 1;			    
			      row = row.concat("<row no=\"" + index + "\">");
			      row = row.concat("<FL val=\"Transaction No\"><![CDATA[" + ((InvoiceGetSet)invoice.get(i)).getTranscationNo() + "]]></FL>");
			      row = row.concat("<FL val=\"Store Code\"><![CDATA[" + ((InvoiceGetSet)invoice.get(i)).getStoreCode() + "]]></FL>");
			      row = row.concat("<FL val=\"Storecity ID\"><![CDATA[" + ((InvoiceGetSet)invoice.get(i)).getStorecityID() + "]]></FL>");
			      row = row.concat("<FL val=\"Store Unique Reference\"><![CDATA[" + ((InvoiceGetSet)invoice.get(i)).getStoreUniqueReference() + "]]></FL>");
			      row = row.concat("<FL val=\"Purchase DateTime\"><![CDATA[" + ((InvoiceGetSet)invoice.get(i)).getPurchaseDateTime() + "]]></FL>");
			      row = row.concat("<FL val=\"Cashier Name\"><![CDATA[" + ((InvoiceGetSet)invoice.get(i)).getCashierName() + "]]></FL>");
			      row = row.concat("<FL val=\"Currency\"><![CDATA[" + ((InvoiceGetSet)invoice.get(i)).getCurrency() + "]]></FL>");
			      row = row.concat("<FL val=\"Trx Total\"><![CDATA[" + ((InvoiceGetSet)invoice.get(i)).getTrxTotal() + "]]></FL>");
			      row = row.concat("<FL val=\"VAT Total\"><![CDATA[" + ((InvoiceGetSet)invoice.get(i)).getVATTotal() + "]]></FL>");
			      row = row.concat("<FL val=\"Total Quantity\"><![CDATA[" + ((InvoiceGetSet)invoice.get(i)).getTotalQuantity() + "]]></FL>");
			      row = row.concat("<FL val=\"Customer Name\"><![CDATA[" + ((InvoiceGetSet)invoice.get(i)).getCustomerName() + "]]></FL>");
			      row = row.concat("</row>");
			      s[i] = row;
			      row = "";
			  }
			    return s;
		}
		
	//pushing data into zoho crm
  	    public String invoiceInsert(String[] s){
		   
		    String xmlData = "";		   
		    		  		    
		    //more than 100 records
		    String updateDetails="";
		    int count=0;
		    int insertedCount =0;
		    xmlData = "<Transactions>";
		    for(int i=0;i<s.length;i++){
     	    	  
				   if(count==100){
					   xmlData = xmlData.concat(s[i]);
					   xmlData =xmlData.concat("</Transactions>");
					   System.out.println("count values "+count+"   "+i);
 	                   updateDetails=insertBatch(xmlData);
         			 
         			   count=0;
           			   insertedCount=insertedCount+100;
         			   xmlData="";
         			   xmlData="<Transactions>";
				   }
	  			  count=count+1;
	  			 
		    }
		    System.out.println("count "+count+" insertedCount "+insertedCount);
		    if(count>0){
		    	for(int i=insertedCount;i<s.length;i++){
		    	   xmlData = xmlData.concat(s[i]);		    	 
		    	}
				   xmlData =xmlData.concat("</Transactions>");
				   System.out.println("count values for less than 100 records");
				   updateDetails=insertBatch(xmlData);				   
			}
			return updateDetails;   
	       
		  }
  	    
  	 //inserting in batch of 100 records 
  	  public String insertBatch(String xmlData){
			String updateDetails = null;
			String authtoken = mainProperties.getProperty("authtoken");
			String scope = "crmapi";
			String newFormat = "2";
			String version = "4";			
			String duplicatecheck="2";			
			String targetURL = mainProperties.getProperty("targetURL");			
			PostMethod post = new PostMethod(targetURL);
			post.setParameter("authtoken", authtoken);
			post.setParameter("scope", scope);
			post.setParameter("newFormat", newFormat);
			post.setParameter(" duplicateCheck", duplicatecheck);
			post.setParameter("xmlData", xmlData);
			post.setParameter("version", version);			
			  
			    System.out.println(xmlData);
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
		   	    return updateDetails;
  	  }
  	  
		    				
	//mian method
		public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ParseException {
			InvoiceHeader ih=new InvoiceHeader();
			ArrayList<InvoiceGetSet> invoice = ih.getAllInvoice();		
			String[] als=ih.formZohoXml(invoice);			
			ih.invoiceInsert(als);			
		}		

}



