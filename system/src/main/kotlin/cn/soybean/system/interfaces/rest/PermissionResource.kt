package cn.soybean.system.interfaces.rest

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/permission")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Permissions", description = "Operations related to permissions")
class PermissionResource
