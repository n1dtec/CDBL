import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

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
    public JsonObject getCurrencyCode(){
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target("http://api.ipgeolocationapi.com/geolocate/" + getIP());
        JsonObject response = resource.request(MediaType.APPLICATION_JSON)
                //.header("IP Address", )
                .get(JsonObject.class);

        return response;
    }

}
