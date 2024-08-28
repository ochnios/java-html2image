package gui.ava.html;

import gui.ava.html.image.HtmlImageGenerator;

/**
 * Created by hki on 12-01-2016.
 */
public class WordWrapExample extends BaseTest {

    private static final String html = """
            <html>
            <body>
            <b>Hello World!</b> Please goto <a title="Goto Google" href="https://www.google.com">Google</a>.
            <div style="width:200px">
            word wrap all
            <span style="word-wrap:break-all; display:block;">
            llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll
            </span>
            </div>
            <div style="width:200px">
            word wrap word
            <span style="word-wrap:break-word; display:block;">
            wwwwwwwwwwwwwwwwww wwwwwwwwwwwwwwwwwwwww wwwwwwwwwwwwwwwwww wwwwwwwwwwwwwww w wwwwwwwwwwwwwwwwwwww w wwwwwwwwwwwwwwww w wwwwwwwwwwwwwwwwwwwwww w w w wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww wwwwwwwwwwwwwwwwwwwww wwwwwwwwwwwwwwwwww wwwwwwwwwwwwwww w wwwwwwwwwwwwwwwwwwww w wwwwwwwwwwwwwwww w wwwwwwwwwwwwwwwwwwwwww w w w wwwwwwwwwwwwwwwwwwwwwww
            </span>
            </div>
            </body>
            </html>
            """;

    public static void main(String[] args) {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml(html);
        // imageGenerator.saveAsImage("hello-form.png");
        // imageGenerator.setSize(new Dimension(150, 600));
        imageGenerator.saveAsImage(getTestOutputFile("word-wrap.jpg"));
        imageGenerator.show();
    }

}
