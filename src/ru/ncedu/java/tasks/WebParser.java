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
    private final String pattern = "\\u003ca href=\"[\\w/.:\\u002d]+\" *\\u003e[\\wа-яА-Я/ \\u002d]*\\u003c/a\\u003e"; // <a href="[any link]">[any text]</a>
    private final String leftExcessPart = "\\u003ca href=\""; //  <a href=" 
    private final String rightExcessPart = "\" *\u003e[\\wа-яА-Я/ \u002d]*\u003c/a\u003e"; //  ">[any text]</a> 

    /**
     * Gets subLinks from code of page
     *
     * @param html html-code of page
     * @return String[] massive with subLinks
     */
    public String[] getLinksFromHtml(String html) {

        String text = pageContent(html);
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);

        int count = 0;
        List<String> list = new ArrayList<String>();
        int start, end;
        while (m.find()) {
            count++;
            start = m.start();
            end = m.end();
            String fg = text.substring(start, end);
            list.add(fg);
        }
        String[] links = new String[count];
        int j = 0;
        for (String line : list) {
            links[j] = line.replaceAll(leftExcessPart, "").replaceAll(rightExcessPart, "");
            j++;
        }

        return links;
    }

    public String[] getLinksFromHtml(String html, int depth) {
        String text = pageContent(html);
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);

        int count = 0;
        List<String> list = new ArrayList<String>();
        int start, end;
        while (m.find()) {
            count++;
            start = m.start();
            end = m.end();
            String fg = text.substring(start, end);
            list.add(fg);
        }
        String[] links = new String[count];
        int j = 0;
        for (String line : list) {
            links[j] = line.replaceAll(leftExcessPart, "").replaceAll(rightExcessPart, "");
            j++;
        }

        return links;
    }

    /**
     * Gets the code page on the link.
     *
     * @param url link to page
     * @return String code page
     */
    private String pageContent(String url) {
        String content = "";
        URL u;

        try {
            u = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                content += line;
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
        //String link = "http://www.netcracker.com/rus/";
        String link;
        int depth;
        if (args.length == 2) {
            link = args[0];
            depth = Integer.parseInt(args[1]);
        }
        if(args.length == 1){
            link = args[0];
            depth = 0;
        }
        else{
            throw new Error("Set input parameters! Parameters: -link[, depth]");
        }
        WebParser pars = new WebParser();

        //String text = pars.pageContent(link);
        String[] subLinks = pars.getLinksFromHtml(link, depth);

        //System.out.print(subLinks);
    }

}
