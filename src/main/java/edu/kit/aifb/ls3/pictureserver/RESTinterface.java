package edu.kit.aifb.ls3.pictureserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.kit.aifb.ls3.pictureserver.DerServletContextListener.ImagePanel;

@Path("/")
public class RESTinterface {

	static final Logger _log = Logger.getLogger(RESTinterface.class.getName());

	@Context
	ServletContext _ctx;

	@POST
	public Response zeigBild(InputStream is) {
		ImagePanel ip = (ImagePanel) _ctx
				.getAttribute(DerServletContextListener.PictureServerAttributes.INHALTSPANEL.name());

		Boolean b = false;
		try {
			b = ip.render(is);
		} catch (IOException e) {
			_log.warning(e.getMessage());
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (b)
			return Response.noContent().build();
		else
			return Response.status(Status.BAD_REQUEST).build();
	}

}