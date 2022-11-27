import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private String[] parts;
    private List<NameValuePair> nameValuePairList;

    public Request(BufferedReader bufferedReader) throws IOException, URISyntaxException {
        var requestLine = bufferedReader.readLine();
        parts = requestLine.split(" ");
//        как-то переделать из String NameValuePair
        nameValuePairList = URLEncodedUtils.parse(new URI(getPath()), StandardCharsets.UTF_8);
    }

    private String getPath() {
        return parts[1];
    }

    public String[] getParts() {
        return parts;
    }

    public List<NameValuePair> getQueryParams() {
        return nameValuePairList;
    }

//    подумать как реализовать не через List
    public List<String> getQueryParam(String name) {
        List<String> queryList = new ArrayList<>();
        for (NameValuePair nameValuePair : nameValuePairList) {
            if (nameValuePair.getName().equals(name)) {
                queryList.add(nameValuePair.getValue());
            }
        }
        return queryList;
    }

//    доработайте функциональность поиска handler'а так, чтобы учитывался только путь без Query
    public String getPathWithoutQuery() {
        int indexPath = getPath().lastIndexOf('?');
        if (indexPath == -1){
            return getPath();
        }
        return getPath().substring(0, indexPath);
    }
}