import com.fastcgi.*;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        while (new FCGIInterface().FCGIaccept() >= 0) {
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

            if(x==0 && y == 0){
                content += "hit";
            }
            else if (x<0 && y<0){
                content += "miss";
            } else if (x>0 && y>0) {
                if(x<=r/2 && y<=r){
                    content += "hit";
                }else content += "miss";
            } else if (x<0 && y>0) {
                if(r*r >= x*x + y*y){
                    content += "hit";
                }else content += "miss";
            } else if (x>0 && y<0) {
                double func = 2*x-r;
                if(func <= y){
                    content += "hit";
                }else content += "miss";
            } else {
                content += "miss";
            }

            String httpResponse = """
            HTTP/1.1 200 OK
            Content-Type: text/html
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

    private static String readRequestBody() throws IOException {
        FCGIInterface.request.inStream.fill();
        var contentLength = FCGIInterface.request.inStream.available();
        var buffer = ByteBuffer.allocate(contentLength);
        var readBytes =
                FCGIInterface.request.inStream.read(buffer.array(), 0,
                        contentLength);
        var requestBodyRaw = new byte[readBytes];
        buffer.get(requestBodyRaw);
        buffer.clear();
        return new String(requestBodyRaw, StandardCharsets.UTF_8);
    }

}