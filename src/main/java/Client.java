import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet request = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=UfZ6UxuW7we5Iyi9B7LrBtHiRbkbGOXQ69qGHj8f");
        CloseableHttpResponse response1 = httpClient.execute(request);
        ObjectMapper mapper = new ObjectMapper();
        Post post = mapper.readValue(response1.getEntity().getContent(), new TypeReference<>() {});

        CloseableHttpResponse response2 = httpClient.execute(new HttpGet(post.getUrl()));
        String filename = Paths.get(new URL(post.getUrl()).getPath()).getFileName().toString();
        response2.getEntity().writeTo(new FileOutputStream("D://" + filename));

    }
}
