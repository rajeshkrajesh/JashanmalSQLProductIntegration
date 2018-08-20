package com.rixyncs.Sqlconnector;

import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class ProductDomParser
{
  public ProductDomParser() {}
  
  public ArrayList<Object> parser(String productDetails)
  {
    ArrayList<Object> arr = new ArrayList();
    


    try
    {
      InputSource is = new InputSource();
      is.setCharacterStream(new StringReader(productDetails));
      

      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(is);
      
      doc.getDocumentElement().normalize();
      




      NodeList nList = doc.getElementsByTagName("row");
      System.out.println("----------------------------");
      for (int i = 0; i < nList.getLength(); i++) {
        System.out.println("**************Loop" + i + "******************");
        
        ProductGetSet account = new ProductGetSet();
        
        Node nNode = nList.item(i);
        
        System.out.println("\nCurrent Element :");
        System.out.print(nNode.getNodeName());
        
        if (nNode.getNodeType() == 1)
        {
          Element eElement = (Element)nNode;
          
          System.out.println("row : " + eElement.getAttribute("no"));
          


          NodeList flList = eElement.getElementsByTagName("FL");
          
          for (int count = 0; count < flList.getLength(); count++)
          {

            Node node1 = flList.item(count);
            if (node1.getNodeType() == 1)
            {

              Element fl = (Element)node1;
              String a = fl.getAttribute("val");
              String b = fl.getTextContent();
              
              System.out.print(a);
              System.out.println("      " + b);
              
              if (a.equals("Product Name")) {
                arr.add(b);

              }
              

            }
            

          }
          
        }
        
      }
      

    }
    catch (Exception e)
    {

      e.printStackTrace();
    }
    
    return arr;
  }
}

