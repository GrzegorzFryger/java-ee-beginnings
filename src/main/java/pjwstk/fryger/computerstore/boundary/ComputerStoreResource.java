package pjwstk.fryger.computerstore.boundary;

import pjwstk.fryger.computerstore.entity.Comment;
import pjwstk.fryger.computerstore.entity.Part;
import pjwstk.fryger.computerstore.repository.ComputerPartsRepository;
import pjwstk.fryger.computerstore.repository.ComputerPartsRepositoryImplementation;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 *
 * @author airhacks.com
 */
@Path("ComputerStore")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComputerStoreResource
{
   @Inject
   ComputerPartsRepository computerparts;

   @Inject
    ComputerPartsRepositoryImplementation computerPartsRepository;


    @Context
    UriInfo uriInfo;


  @GET
  @Path("/computerparts")
    public Response getAllComputerParts()
  {

      List<Part> partList;

      try
      {
          partList =  computerPartsRepository.query();

      }catch (RuntimeException e)
      {
          //add class exceptions handler
         return Response.serverError().build();
      }

      return Response.status(200).entity(partList).build();


  }

    @GET
    @Path("/computerparts/filters")
    public String getPartByFilter(@QueryParam("price_from") int from,
                                  @QueryParam("price_to") int to,
                                  @QueryParam("name") String name,
                                  @QueryParam("orderBy") List<String> orderBy)
    {


        if (from != 0 && to != 0)
        {
            //do something
        }
        if(name != "")
        {
            //do something
        }






      return  null;



    }

    @GET
    @Path("/computerparts/{id}")
    public Response getComputerPartsById(@PathParam("id") @Min(0) Long id)
  {
      Part part;

      try
      {
          part = computerparts.getById();

      }catch (RuntimeException e)
      {
          throw new RuntimeException(e);
      }


      return Response.ok(Response.Status.OK).build();

  }

  @POST
  @Path("/computerparts")
  public Response addComputerPart(@Valid @NotNull Part part)
  {

      Long id;

      try
      {
         id = computerparts.addPart(part);

      }catch (RuntimeException e)
      {
          throw new RuntimeException(e);
      }

      URI uri = uriInfo.getBaseUriBuilder()
              .path(ComputerStoreResource.class)
              .path("/")
              .path(ComputerStoreResource.class, "getComputerPartsById").build(id);

      return Response.ok(uri).build();

  }

  @PUT
  @Path("/computerparts/{id}")
  public Response updateComputerPart(@PathParam("id") @Min(0) Long id, @Valid @NotNull Part part)
  {


      try {

          computerparts.updatePart(id,part);

      }catch (RuntimeException e)
      {
          throw new RuntimeException(e);
      }

      URI uri = uriInfo.getBaseUriBuilder()
              .path(ComputerStoreResource.class)
              .path("/")
              .path(ComputerStoreResource.class, "getComputerPartsById").build(id);

      return Response.ok(uri).build();
  }

    @GET
    @Path("/computerparts/{id}/comments")
    public Response getAllCommentPart(@PathParam("id") @Min(0) Long id)
    {

        List<Comment> comments;

        try
        {

          comments =  computerparts.getAllComments();

        }catch (RuntimeException e)
        {
            throw new RuntimeException(e);
        }

        return Response.ok(comments).build();
    }

    @POST
    @Path("/computerparts/{id}/comments")
    public Response addCommentToPart(@PathParam("id") @Min(0) Long id, @Valid @NotNull Comment comment)
    {
        try
        {
            computerparts.addComment(id,comment);

        }catch (RuntimeException e)
        {
            throw new RuntimeException(e);
        }

        URI uri = uriInfo.getBaseUriBuilder()
                .path(ComputerStoreResource.class)
                .path("/")
                .path(ComputerStoreResource.class, "getAllCommentPart").build(id);

        return Response.ok(uri).build();

    }

    @DELETE
    @Path("/computerparts/{id}/comments/{idComment}")
    public Response deleteCommentFromPart(@PathParam("id") @Min(0) Long id, @PathParam("idComment") @Min(0) Long idComment)
    {
        try {

            computerparts.deleteComment(id,idComment);

        }catch (RuntimeException e)
        {
            throw new RuntimeException(e);
        }

        return Response.ok(Response.Status.OK).build();
    }






}
