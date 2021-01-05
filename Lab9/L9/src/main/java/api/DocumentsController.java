package api;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import domain.UserFile;
import org.bson.types.ObjectId;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import persistence.FileRepo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("/documents")
public class DocumentsController {

    @Inject
    private FileRepo fileRepo;

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response add(@FormDataParam("file") InputStream stream, @FormDataParam("file") FormDataContentDisposition details) {

        byte[] bytes = new byte[0];
        try {
            bytes = ByteStreams.toByteArray(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = details.getFileName();

        UserFile file = new UserFile(fileName, bytes);
        fileRepo.InsertOne(file);

        return Response.ok().build();
    }

    @GET
    @Path("/get/{fileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("fileId") String param) {


        UserFile file = fileRepo.GetById(new ObjectId(param));
        if(file != null) {
            String json = new Gson().toJson(file);
            return Response.ok(json).build();
        }
        return Response.status(404).build();
    }

    @DELETE
    @Path("/delete/{fileId}")
    public Response del(@PathParam("fileId") String param) {
        fileRepo.Delete(new ObjectId(param));

        return Response.noContent().build();
    }

    @PUT
    @Path("/put/{fileId}")
    public Response put(@PathParam("fileId") String param, @FormDataParam("file") InputStream stream, @FormDataParam("file") FormDataContentDisposition details)
    {
        byte[] bytes = new byte[0];
        try {
            bytes = ByteStreams.toByteArray(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = details.getFileName();

        UserFile file = new UserFile(fileName, bytes);
        file.Id = new ObjectId(param);

        UserFile result = fileRepo.Update(new ObjectId(param), file);
        if(result != null){
            return Response.noContent().build();
        }

        return Response.status(404).build();
    }
}
