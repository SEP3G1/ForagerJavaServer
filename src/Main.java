import Config.Config;
import Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException
    {
        HttpClient client = new DefaultHttpClient();

        // Creates the request for the HTTP server
        HttpGet request = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/userlogin?userName=Oliver&pass=654321");

        // Executes the HTTP server request
        HttpResponse response = client.execute(request);

        // Get the value of the header in the HTTP request. Expected 200 OK
        String status = String.valueOf(response.getStatusLine());
        System.out.println(status);

        // Reads the body of the HTTP response
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        ObjectMapper mapper = new ObjectMapper();
        User user;

        while ((line = rd.readLine()) != null) {
            System.out.println(line);
            user = mapper.readValue(line, User.class);
            System.out.println(user.toString());
        }


    }
}
