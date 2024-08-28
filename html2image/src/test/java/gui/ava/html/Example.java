package gui.ava.html;

import gui.ava.html.image.HtmlImageGenerator;

/**
 * Created by hki on 07-01-2016.
 */
public class Example extends BaseTest {

    private static final String html = """
            <html>
            <head>
                <!-- commented style :)
                <style>
                    div { border: 2px solid red; text-align: center; }
                </style>
                -->
            </head>
            <body>
                <h1>Hello World!</h1>
                <div style="border: 2px solid #90ee90; text-align: center;">
                    Please goto <a title="Goto Google" href="https://www.google.com" target="new">Google</a>.
                </div>
            </body>
            </html>
            """;

    public static void main(String[] args) {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml(html);
        imageGenerator.saveAsImage( getTestOutputFile("hello-world.jpg") );
//        imageGenerator.saveAsImage("hello-world.png");
//        imageGenerator.saveAsImage("hello-world.jpg");
//        imageGenerator.saveAsHtmlWithMap("hello-world.html", "hello-world.png");
    }

}
