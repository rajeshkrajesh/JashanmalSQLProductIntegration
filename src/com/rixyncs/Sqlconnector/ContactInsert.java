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
public class ContactInsert {
						
		public  Properties mainProperties = new Properties();
		public ContactInsert() throws IOException{		
		mainProperties=Utility.loadProperties();
		}
		
	//reading the csv 
		public ArrayList<ContactsGetSet> getAllContacts(){
			ArrayList<ContactsGetSet> contacts=new  ArrayList<ContactsGetSet>();
			 int rowCount = -1;
		        try {	         
		            String customercsv=mainProperties.getProperty("customercsv");
		            BufferedReader csvFileReader;
	   	            csvFileReader = new BufferedReader(new FileReader(customercsv));
		            
		            /* Initialize the number of fields to 0 */
		            int fieldCount = 0;
		            String[] csvFields = null;
		            StringTokenizer stringTokenizer = null;
		           
		            /* Initialize the current line variable */
		            String currLine = csvFileReader.readLine();		            
		            System.out.println(currLine);
		            /* reading rows from the CSV file */
		            while((currLine = csvFileReader.readLine()) != null) {
		            	ContactsGetSet ct=new ContactsGetSet();
		                stringTokenizer = new StringTokenizer(currLine, ",");
		                fieldCount = stringTokenizer.countTokens();
		                /* if rows exist in the CSV file*/
		                if(fieldCount > 0) {
		                	
		                    /* Create the row element*/		                	
		                	ct.setLastName(String.valueOf(stringTokenizer.nextElement()));
		                	ct.setMobile(String.valueOf(stringTokenizer.nextElement()));
		                	ct.setCustomerId(String.valueOf(stringTokenizer.nextElement()));
		                	
		                	contacts.add(ct);	                               	    
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
		    return contacts;
		}
		
	//csv data to zoho xml form
		public String[] formZohoXml( ArrayList<ContactsGetSet> contacts ) throws ClassNotFoundException, SQLException, IOException, ParseException{
			  
			  //converting the sql arraylist into zoho xml data			  
			    String[] s = new String[contacts.size()];
			    int count=0;
			    int i = 0;
  			    String row="";
			    for (i = 0; i < contacts.size(); i++) {
			      int index = i + 1;			    
			      row = row.concat("<row no=\"" + index + "\">");
			      row = row.concat("<FL val=\"Last Name\"><![CDATA[" + ((ContactsGetSet)contacts.get(i)).getLastName() + "]]></FL>");
			      row = row.concat("<FL val=\"Mobile\"><![CDATA[" + ((ContactsGetSet)contacts.get(i)).getMobile() + "]]></FL>");
			      row = row.concat("<FL val=\"Customer Id\"><![CDATA[" + ((ContactsGetSet)contacts.get(i)).getCustomerId() + "]]></FL>");
			      row = row.concat("</row>");
			      s[i] = row;
			      row = "";
			  }
			    return s;
		}
		
	//pushing data into zoho crm
  	    public String contactsInsert(String[] s){
		   
		    String xmlData = "";		   
		    		  		    
		    //more than 100 records
		    String updateDetails="";
		    int count=0;
		    int insertedCount =0;
		    xmlData = "<Contacts>";
		    for(int i=0;i<s.length;i++){
		    	  xmlData = xmlData.concat(s[i]);
				   if(count==100){
					 
					   xmlData =xmlData.concat("</Contacts>");
					   System.out.println("count values "+count+"   "+i);
 	                   updateDetails=insertBatch(xmlData);
         			 
         			   count=0;
           			   insertedCount=insertedCount+100;
         			   xmlData="";
         			   xmlData="<Contacts>";
				   }
	  			  count=count+1;
	  			 
		    }
		    System.out.println("count "+count+" insertedCount "+insertedCount);
		    if(count>0){
		    	xmlData=("<Contacts>");
		    	for(int i=insertedCount;i<s.length;i++){
		    	   xmlData = xmlData.concat(s[i]);		    	 
		    	}
				   xmlData =xmlData.concat("</Contacts>");
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
			String targetURL3 = mainProperties.getProperty("targetURL3");			
			PostMethod post = new PostMethod(targetURL3);
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
  	  
		    				
	//main method
		public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ParseException {
			ContactInsert ci=new ContactInsert();
			ArrayList<ContactsGetSet> contacts = ci.getAllContacts();		
			String[] als=ci.formZohoXml(contacts);			
		    ci.contactsInsert(als);			
		}		

}