package com.xiaobai.blog.until;

import com.xiaobai.blog.bean.YellowCalendar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;

@Component
public class JsoupUntils {
    public YellowCalendar getDocument(String url) {

        YellowCalendar yellowCalendar =new YellowCalendar();

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Element calender=doc.selectFirst("div.calendar");
            Element kalendar=calender.selectFirst("div.kalendar");
            Elements goodcalendar=calender.select("div.goodcalendar");
            Element kalendartop=kalendar.selectFirst("div.kalendar_top");
            String h3 = kalendartop.select("h3").text();
            yellowCalendar.setGregorianCalendar(h3);
            String h5 = kalendartop.select("h5").text();
            yellowCalendar.setLunarCalendar(h5);
            String text = kalendar.selectFirst("div.kalendar_foot").text();
            yellowCalendar.setProposal(text);
            for (int i = 0; i < goodcalendar.size(); i++) {
                HashSet<String> set=new HashSet<String>();
                Elements li = goodcalendar.get(i).selectFirst("ul").select("li");
                for (Element element : li) {
                    String text1 = element.text();
                    set.add(text1);
                }
                if (i==0){
                    yellowCalendar.setShould(set);
                }
                else{
                    yellowCalendar.setAvoid(set);
                }
            }
           return yellowCalendar;
        } catch (Exception e) {
            return null;
        }
    }

}
