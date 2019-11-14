package com.sensor.sellCabinet.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
  
  
public class Sax {  
//  用来装载解析后的数据(所有的Student对象-->list  
//  单个对象stu  
//  用来存放解析过程中的标签名(节点名)--tag  
  
    public Map<String, String> parserXml(String xmlStr)  
    {    
    	Map<String, String> mapres = new HashMap<String, String>();

        InputStream in;
		try {
			in = new   ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
        SAXReader saxReader = new SAXReader();    
            Document document = saxReader.read(in);   //把文件读入到文档 
            Element barcodeInfo=document.getRootElement();   //获取文档根节点 
            mapres = doublexml(barcodeInfo); 
                 
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
        } catch (DocumentException e) {    
        	e.printStackTrace();
        }
		return mapres;    
    }    
      
      
    private Map<String, String> doublexml(Element ele) 
    { 
    	Map<String, String> mapres = new HashMap<String,String>();
    	String nodeName;
    	String nodeText;
        for(Iterator iter = ele.elementIterator();iter.hasNext();) 
        { 
            Element node = (Element)iter.next(); 
            nodeName = node.getName();
            if(node.getText().length()>0) 
            { 
                nodeText = node.getText();
                mapres.put(nodeName, nodeText);
            } 
            if(node.elementIterator().hasNext()) 
            { 
                this.doublexml(node); 
            } 
        }
		return mapres;
    } 
	}
