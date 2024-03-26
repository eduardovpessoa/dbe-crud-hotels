package br.com.xpe.resource

import br.com.xpe.model.HotelDTO
import br.com.xpe.repository.HotelRepository
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/hotels")
class HotelResource {

    @Inject
    lateinit var repository: HotelRepository

    @GET
    fun listHotels(): Response = Response.ok(repository.listAll()).build()

    @Path("/{id}")
    @GET
    fun listHotelById(@PathParam("id") id: String): Response {
        repository.findById(ObjectId(id))?.let {
            return Response.ok(it).build()
        }
        return Response.status(Response.Status.NOT_FOUND).build()
    }

    @POST
    fun createHotel(hotel: HotelDTO): Response {
        if (hotel.name.isEmpty() || hotel.avgPrice <= 0.0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Dados não podem ser vazios!").build()
        }
        repository.persist(hotel.toEntity())
        return Response.status(Response.Status.CREATED).entity(repository.listAll()).build()
    }

    @Path("/{id}")
    @PUT
    fun updateHotel(@PathParam("id") id: String, dto: HotelDTO): Response {
        if (dto.name.isEmpty() || dto.avgPrice <= 0.0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Dados não podem ser vazios!").build()
        }
        repository.findById(ObjectId(id))?.let {
            it.name = dto.name
            it.avgPrice = dto.avgPrice
            it.status = dto.status
            repository.update(it)
            return Response.ok(it).build()
        }
        return Response.status(Response.Status.NOT_FOUND).build()
    }

    @Path("/{id}")
    @DELETE
    fun deleteHotel(@PathParam("id") id: String): Response {
        repository.findById(ObjectId(id))?.let {
            repository.delete(it)
            return Response.status(Response.Status.NO_CONTENT).build()
        }
        return Response.status(Response.Status.NOT_FOUND).build()
    }
}