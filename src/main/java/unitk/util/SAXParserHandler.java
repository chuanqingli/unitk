package unitk.util;

import java.util.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserHandler extends DefaultHandler {

	private List<Map<String, Object>> __itemList = new ArrayList<Map<String, Object>>();
	protected Map<String, Object> __item = new HashMap<String,Object>();

	// private String currentTag = null;
	private String nodeName = null;

    protected StringBuilder __buf = new StringBuilder();

	public SAXParserHandler(String nodeName) {
		// TODO Auto-generated constructor stub
		this.nodeName = nodeName;
	}

	public List<Map<String, Object>> getList() {
		return __itemList;
	}

    public void doWithAttributes(String qName, Attributes attributes){
        for (int i = 0; i < attributes.getLength(); i++) {
            String aname = attributes.getQName(i);
            String value = attributes.getValue(i);// 获取属性的value值

            //logger.debug(attributes.getQName(i) + "-----" + value);  
        }
        
        // if("property".equals(qName)&&attributes.getLength()==2&&attributes.getQName(0).equals("name")&&attributes.getQName(1).equals("value")){
        //     __item.put(attributes.getValue(0),attributes.getValue(1));
        // }  
    }

	@Override
	public void startDocument() throws SAXException {
		//logger.debug("--startDocument--");
	}

	@Override
	public void startElement(String url, String localName, String qName,
			Attributes attributes) throws SAXException {

        __buf.setLength(0);

		//logger.debug("startElement localName: " + localName + "qName:"+ qName);
		// currentTag = qName;
		if (nodeName.equals(qName)) { // nodeName 是 person ,由构造函数传入。
			__item = new HashMap<String, Object>();
			// __item.put("id", attributes.getValue("id"));
		}

        //处理属性
        if(attributes.getLength()>0){
            doWithAttributes(qName,attributes);
        }
	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        __buf.append(arg0,  arg1,  arg2);  

		// //logger.debug("characters in " + currentTag + "="
		// 		+ new String(arg0, arg1, arg2) + "!");

		// if ("name".equals(currentTag)) {
		// 	__item.put("name", new String(arg0, arg1, arg2));
		// }
		// if ("age".equals(currentTag)) {
		// 	__item.put("age", new String(arg0, arg1, arg2));
		// }
	}

	@Override
	public void endElement(String url, String localName, String qName)
			throws SAXException {
        String value = __buf.toString();
		//logger.debug("endElement in " + localName);
		if (nodeName.equals(qName)) {
			__itemList.add(__item);
            __item = new HashMap<String,Object>();
        }else{
            __item.put(qName,value);
        }
		// currentTag = null;
	}

	@Override
	public void endDocument() throws SAXException {
		//logger.debug("--endDocument--");
	}

}



