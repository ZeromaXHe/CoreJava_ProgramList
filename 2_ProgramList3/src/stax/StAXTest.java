//II 3-9: 181/818
//程序清单3-9 包含了用StAX解析器实现的网络爬虫程序。
//正如你所见，这段代码比等效的SAX代码要简短了许多，因为此时我们不必操心事件处理问题。
package stax;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.net.URL;

/**
 * This program demonstrates how to use a StAX parser. The program prints all hyperlinks of an XHTML web page. <br>
 *     Usage: java stax.StAXTest URL
 * @author Cay Horstmann
 * @version 1.0 2007-6-23
 */
public class StAXTest {
    public static void main(String[] args) throws Exception{
        String urlString;
        if(args.length == 0){
            urlString = "https://www.w3c.org";
            System.out.println("Using "+urlString);
        }
        else urlString = args[0];
        URL url = new URL(urlString);
        InputStream in = url.openStream();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = factory.createXMLStreamReader(in);
        while(parser.hasNext()){
            int event = parser.next();
            if(event == XMLStreamConstants.START_ELEMENT){
                if(parser.getLocalName().equals("a")){
                    String href = parser.getAttributeValue(null, "href");
                    if(href!=null)
                        System.out.println(href);
                }
            }
        }
    }
}
