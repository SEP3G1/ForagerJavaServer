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
        HttpGet request = new HttpGet("http://10.152.210.21:5001/api/userlogin?userName=Oliver&pass=654321");
        HttpResponse response = client.execute(request);
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
