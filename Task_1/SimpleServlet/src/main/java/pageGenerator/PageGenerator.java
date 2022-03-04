package pageGenerator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private final static String HTML_DIR = "templates";

    private final Configuration cfg;

    /* singleton */
    private static PageGenerator pageGenerator;

    private PageGenerator(){
        cfg = new Configuration();
    }

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }


    public String getPage(String fileName, Map<String, Object> dataModel) {
        String PAGE = null;

        try(Writer out = new StringWriter()) {
            Template template = cfg.getTemplate(HTML_DIR + File.separator + fileName);
            template.process(dataModel, out);
            PAGE = out.toString();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return PAGE;
    }
}
