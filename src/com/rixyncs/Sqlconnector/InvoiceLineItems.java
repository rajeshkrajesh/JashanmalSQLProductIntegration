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

public class InvoiceLineItems {
	
		//pushing the invoice header data into zoho transaction module 
						
			public  Properties mainProperties = new Properties();
		    public InvoiceLineItems() throws IOException{		
		       mainProperties=Utility.loadProperties();
		    }
			
		//reading the csv 
			public ArrayList<LineItemGetSet> getAllInvoice(){
				ArrayList<LineItemGetSet> line=new  ArrayList<LineItemGetSet>();
				 int rowCount = -1;
			        try {	         
			            String customercsv=mainProperties.getProperty("lineitempath");
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
			            	LineItemGetSet li=new LineItemGetSet();
			                stringTokenizer = new StringTokenizer(currLine, ",");
			                fieldCount = stringTokenizer.countTokens();
			                /* if rows exist in the CSV file*/
			                if(fieldCount > 0) {
			                	
			                    /* Create the row element*/		                	
			                	li.setInvoicename(String.valueOf(stringTokenizer.nextElement()));
			                	li.setProductName(String.valueOf(stringTokenizer.nextElement()));
			                	li.setQuantity(String.valueOf(stringTokenizer.nextElement()));
			                	li.setSoldPrice(String.valueOf(stringTokenizer.nextElement()));
			                	li.setTax(String.valueOf(stringTokenizer.nextElement()));
			                	li.setFullPrice(String.valueOf(stringTokenizer.nextElement()));
			                	li.setArabicDescription(String.valueOf(stringTokenizer.nextElement()));
			                	li.setSalesRepName(String.valueOf(stringTokenizer.nextElement()));			                	
			                	line.add(li);	                               	    
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
			    return line;
			}
			
		//csv data to zoho xml form
			public String[] formZohoXml( ArrayList<LineItemGetSet> line ) throws ClassNotFoundException, SQLException, IOException, ParseException{
				  
				  //converting the sql arraylist into zoho xml data			  
				    String[] s = new String[line.size()];
				    int count=0;
				    int i = 0;
	  			    String row="";
				    for (i = 0; i < line.size(); i++) {
				      int index = i + 1;			    
				      row = row.concat("<row no=\"" + index + "\">");
				      row = row.concat("<FL val=\"Related Transaction\"><![CDATA[" + ((LineItemGetSet)line.get(i)).getInvoicename() + "]]></FL>");
				      row = row.concat("<FL val=\"Line Item No\"><![CDATA[" + ((LineItemGetSet)line.get(i)).getProductName() + "]]></FL>");
				      row = row.concat("<FL val=\"Line Qty\"><![CDATA[" + ((LineItemGetSet)line.get(i)).getQuantity() + "]]></FL>");
				      row = row.concat("<FL val=\"Sold Price\"><![CDATA[" + ((LineItemGetSet)line.get(i)).getSoldPrice() + "]]></FL>");
				      row = row.concat("<FL val=\"Sales Tax\"><![CDATA[" + ((LineItemGetSet)line.get(i)).getTax() + "]]></FL>");
				      row = row.concat("<FL val=\"Full Price\"><![CDATA[" + ((LineItemGetSet)line.get(i)).getFullPrice() + "]]></FL>");
				      row = row.concat("<FL val=\"Arabic Description\"><![CDATA[" + ((LineItemGetSet)line.get(i)).getArabicDescription() + "]]></FL>");
				      row = row.concat("<FL val=\"Sales Rep Name \"><![CDATA[" + ((LineItemGetSet)line.get(i)).getSalesRepName() + "]]></FL>");
				     				      
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
			    xmlData = "<LineItems>";
			    for(int i=0;i<s.length;i++){
			    	   xmlData = xmlData.concat(s[i]);
					   if(count==100){
						   
						   xmlData =xmlData.concat("</LineItems>");
						   System.out.println("count values "+count+"   "+i);
	 	                   updateDetails=insertBatch(xmlData);
	         			 
	         			   count=0;
	           			   insertedCount=insertedCount+100;
	         			   xmlData="";
	         			   xmlData="<LineItems>";
					   }
		  			  count=count+1;
		  			 
			    }
			    System.out.println("count "+count+" insertedCount "+insertedCount);
			    if(count>0){
			    	xmlData=("<LineItems>");
			    	for(int i=insertedCount;i<s.length;i++){
			    	   xmlData = xmlData.concat(s[i]);		    	 
			    	}
					   xmlData =xmlData.concat("</LineItems>");
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
				String targetURL = mainProperties.getProperty("targetURL2");			
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
				InvoiceLineItems ih=new InvoiceLineItems();
				ArrayList<LineItemGetSet> line = ih.getAllInvoice();		
				String[] als=ih.formZohoXml(line);			
				ih.invoiceInsert(als);			
			}		

	}






