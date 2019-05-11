//II 2-7: 127/818
//程序清单2-7对这种机制进行了应用，它定位一个Web页面上的所有超文本引用，并打印它们。
//为了运行这个程序，你需要在命令行中提供一个URL，例如
//java match.HrefMatch http://horstmann.com
package match;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class HrefMatch {
    public static void main(String[] args) {
        try{
            //get URL string from command line or use default
            String urlString;
            if(args.length > 0) urlString = args[0];
            else urlString = "http://java.sun.com";

            //open reader for URL
            InputStreamReader in = new InputStreamReader(new URL(urlString).openStream(), StandardCharsets.UTF_8);

            //read contents into string builder
            StringBuilder input = new StringBuilder();
            int ch;
            while((ch=in.read())!=-1)
                input.append((char)ch);

            //search for all occurences of pattern
            String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*>";
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);

            while(matcher.find()){
                String match = matcher.group();
                System.out.println(match);
            }
        }
        catch(IOException| PatternSyntaxException e){
            e.printStackTrace();
        }
    }
}
