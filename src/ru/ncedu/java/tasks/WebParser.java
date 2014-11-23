package ru.ncedu.java.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author azbus
 */
public class WebParser {

    private String link = "";

    /**
     * Gets subLinks from code of page
     * @param text html-code of page
     * @return String[] massive with subLinks
     */
    public String[] WebParse(String text) {

        Pattern p = Pattern.compile("\u003ca href=\"[\\w/.:\u002d]+\" *\u003e[\\wа-яА-Я/ \u002d]*\u003c/a\u003e");
        Matcher m = p.matcher(text);

        int count = 0;
        List<String> list = new ArrayList<String>();
        int start, end;
        while (m.find()) {
            count++;
            start = m.start();
            end = m.end();
            System.out.println("found: " + count + " : " + start + " - " + end);
            String fg = text.substring(start, end);
            list.add(fg);
            System.out.println(fg);
        }
        String[] links = new String[count];
        int j = 0;
        for (String line : list) {
            links[j] = line.replaceAll("\\u003ca href=\"", "").replaceAll("\" *\u003e[\\wа-яА-Я/ \u002d]*\u003c/a\u003e", "");
            j++;
        }

        return links;
    }

    /**
     * Gets the code page on the link.
     * @param url link to page
     * @return String code page
     */
    protected String pageContent(String url) {
        String content = "";
        URL u;

        try {
            u = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                content += line;
                //System.out.println(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.printf("Error with URL!", e);

        }

        return content;
    }

    public WebParser() {

    }
//    public WebParser(String link) {
//        this.link = link;
//    }

    public static void main(String[] args) {
        String link = "http://www.netcracker.com/rus/";
        WebParser pars = new WebParser();

        String text = pars.pageContent(link);
        String[] subLinks = pars.WebParse(text);
        
        
        //System.out.print(subLinks);

    }

}
