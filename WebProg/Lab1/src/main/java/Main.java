import com.fastcgi.*;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        while (new FCGIInterface().FCGIaccept() >= 0) {
            Date startTime = new Date();
            String content = null;

            /*try {
                content = readRequestBody();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/


            String query = readRequestQuery();


            double x, y, r;
            String[] split = query.split("(?=^|&)(.*?)(?<==)");
            x = Double.parseDouble(split[1]);
            y = Double.parseDouble(split[2]);
            r = Double.parseDouble(split[3]);

            content = x + "\n" + y + "\n" + r + "\n";
            
            
            String status = "";
            if(x==0 && y == 0){
                status += "hit";
            }
            else if (x<=0 && y<=0){
                status += "miss";
            } else if (x>=0 && y>=0) {
                if(x<=r/2 && y<=r){
                    status += "hit";
                }else status += "miss";
            } else if (x<=0 && y>=0) {
                if(r*r >= x*x + y*y){
                    status += "hit";
                }else status += "miss";
            } else if (x>=0 && y<=0) {
                double func = 2*x-r;
                if(func <= y){
                    status += "hit";
                }else status += "miss";
            } else {
                status += "miss";
            }

            content += "\n" + (new Date());
            content += "\n" + (new Date().getTime()-startTime.getTime());

            content = convert(String.valueOf(x), String.valueOf(y), String.valueOf(r), status, new Date().toString(), String.valueOf(new Date().getTime()-startTime.getTime()));

            String httpResponse = """
            HTTP/1.1 200 OK
            Content-Type: text/json
            Content-Length: %d
            Access-Control-Allow-Origin: *
            
            %s
            """.formatted(content.length(), content);
            System.out.println(httpResponse);

        }
    }

    private static String readRequestQuery(){
        return FCGIInterface.request.params.getProperty("QUERY_STRING");
    }

    private static String convert(String ... strings){
        String answer = "{";
        answer += "\n\t\"X\": " + strings[0];
        answer += ",\n\t\"Y\": " + strings[1];
        answer += ",\n\t\"R\": " + strings[2];
        answer += ",\n\t\"Status\": \"" + strings[3];
        answer += "\",\n\t\"Time\": \"" + strings[4];
        answer += "\",\n\t\"Run\": " + strings[5];
        answer += "\n}";
        return answer;
    }
}