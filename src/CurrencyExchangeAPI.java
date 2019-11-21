import kong.unirest.json.JSONObject;

import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

@Path("/")
public class CurrencyExchangeAPI {

    @Path("/ip")
    @GET
    public String getIP(){
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target("http://api.ipify.org");
        String response = resource.request(MediaType.APPLICATION_JSON)
                //.header("IP Address", )
                .get(String.class);

        return response;
    }

    @Path("/currencyCode")
    @GET
    public JSONObject getCurrencyCode(){
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target("http://api.ipgeolocationapi.com/geolocate/" + getIP());
        JSONObject response = resource.request(MediaType.APPLICATION_JSON)
                //.header("IP Address", )
                .get(JSONObject.class);

        return response;
    }

}
