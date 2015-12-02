package de.schreibvehler;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.annotation.Resource;
import javax.xml.parsers.*;

import org.apache.commons.io.IOUtils;
import org.infinispan.manager.CacheContainer;
import org.w3c.dom.*;
import org.xml.sax.*;

public class OpenWeatherService {

    @Resource(lookup = "java:jboss/infinispan/container/myCache")
    CacheContainer cc;

    Map<String, List<WeatherForcast>> cache;

    void initCache() {
        this.cache = cc.getCache();
    }

    public OpenWeatherService() {
        initCache();
    }

    public List<WeatherForcast> getWeatherForcast(String city, String country)

    {
        String output = null;
        String key = getKey(country, city);
        
        if(cache.get(key) != null){
            System.out.println("found weatherforcast in cache for key " + key);
            return cache.get(key);
        }
        
        List<WeatherForcast> wfList = new ArrayList<>();
        
        try {
            String webservice = String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s,%s&mode=xml", city,
                    country);
            URL url = new URL(webservice);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

            }
            InputStream in = conn.getInputStream();
            String xml = IOUtils.toString(in);
            // BufferedReader br = new BufferedReader(new
            // InputStreamReader(in));
            // System.out.println("Output from Server .... \n");
            // StringBuffer xmlString = new
            // StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            // while ((output = br.readLine()) != null) {
            // System.out.println(output);
            // // xmlString.append(output);
            // }

            System.out.println("huhu " + xml);

            try {
                Document doc = readXml(xml);
                NodeList list = doc.getElementsByTagName("time");
                for (int i = 0; i < list.getLength(); i++) {
                    Element element = (Element) list.item(i);
                    System.out
                            .println(((Element) element.getElementsByTagName("clouds").item(0)).getAttribute("value"));
                    WeatherForcast wf = new WeatherForcast();
                    wf.setClouds(((Element) element.getElementsByTagName("clouds").item(0)).getAttribute("value"));
                    wfList.add(wf);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cache.put(key, wfList);
        return wfList;
    }

    private String getKey(String country, String city) {
        if (country != null && city != null) {
            return country.toLowerCase() + "#" + city.toLowerCase();
        }

        return null;
    }

    private Document readXml(String is) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating(false);
        dbf.setIgnoringComments(false);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setNamespaceAware(true);
        // dbf.setCoalescing(true);
        // dbf.setExpandEntityReferences(true);

        DocumentBuilder db = null;
        db = dbf.newDocumentBuilder();
        db.setEntityResolver(new NullResolver());

        // db.setErrorHandler( new MyErrorHandler());

        return db.parse(new InputSource(new StringReader(is)));
    }

    class NullResolver implements EntityResolver {
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return new InputSource(new StringReader(""));
        }
    }
}
