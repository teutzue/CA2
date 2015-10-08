
package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author CosticaTeodor
 */
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>
{

    Gson gson  = new GsonBuilder().setPrettyPrinting().create();
    @Context
    ServletContext context;

    @Override
    public Response toResponse(DataNotFoundException e)
    {
       boolean isDebug = context.getInitParameter("debug").equals("true");
       JsonObject obj = new JsonObject();
       obj.addProperty("code", 404);
       obj.addProperty("debug", isDebug);
       obj.addProperty("msg", " Not found ");
       return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(obj)).type(MediaType.APPLICATION_JSON).build();
    }

}
