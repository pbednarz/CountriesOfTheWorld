package pl.solaris.countries.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

//class is used to retrieve every piece of data from web service.
//every call is passed to it and managed by it
public class DataRequest {
    private HttpResponse response;
    private HttpClient client;
    private HttpContext context;
    private HttpGet httpGet;
    public String content;
    public String params;
    private String encParams;
    public JSONObject jOb;
    private final String BASE_URL = "http://api.geonames.org/countryInfoJSON?formatted=true&lang=en&username=pbednarz&style=full";

    public DataRequest() {
        context = new BasicHttpContext();
        httpGet = new HttpGet(BASE_URL);
        client =  new DefaultHttpClient();
    }

    //Used to add headers to call
    private void addHeaders() {
        httpGet.addHeader("Content-Type", "application/json; charset=UTF-8");
    }

    // new method, no deprecated objects
    public boolean sendGet() {

        addHeaders();
        try {
            response = client.execute(httpGet, context);
            HttpEntity ent = response.getEntity();
            if (ent != null && response.getStatusLine().getStatusCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(ent.getContent(), "UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (String line = null; (line = reader.readLine()) != null; ) {
                    builder.append(line).append("\n");
                }
                content = builder.toString();
                jOb = new JSONObject(content);
                return true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }
}
