package de.banapple.confluence.plugin.sitemap;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.setup.settings.SettingsManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;

public class SitemapServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5019691121005898175L;

	private static Log log = LogFactory.getLog(SitemapServlet.class);
	
	private static final DateFormat DATEFORMAT =
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00");
	
	private PageManager pageManager;
    private SpaceManager spaceManager;
    private SettingsManager settingsManager;
    private Configuration configuration;
    
    @SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		final String METHOD = "doGet: ";
		log.debug(METHOD+"entered");
		
		Writer writer = resp.getWriter();
		resp.setContentType("text/xml");
		
		/* get the spaces */
		List<Space> sitemapSpaces = new LinkedList<Space>();
		List<Space> spaces = spaceManager.getAllSpaces();
		for (Space space : spaces) {
			if (configuration.getSpaceList().contains(space.getName())) {
				sitemapSpaces.add(space);
			}
		}
		
		writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		writer.append("<urlset \n")
			.append("xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"\n")
			.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n")
			.append("xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9\n")
            .append("http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">");
		
		/* get urls for all spaces */
		for (Space space : sitemapSpaces) {
			List<Page> pages = pageManager.getPages(space, true);
			for (Page page : pages) {
				
				
				String pageUrl =
					settingsManager.getGlobalSettings().getBaseUrl()
					+page.getUrlPath();
				writer.append("<url>\n")
					.append("<loc>")
					.append(pageUrl)
					.append("</loc>\n");
				writer.append("<lastmod>")
					.append(DATEFORMAT.format(
							page.getLastModificationDate()))
					.append("</lastmod>\n");
				writer.append("</url>\n");
			}
		}
		
		writer.append("</urlset>");
	}
	
    
	public void setPageManager(PageManager pageManager)
	{
		this.pageManager = pageManager;
	}
	public void setSpaceManager(SpaceManager spaceManager)
	{
		this.spaceManager = spaceManager;
	}


	@Override
	public void init() throws ServletException
	{
		final String METHOD = "init: ";
		log.debug(METHOD+"entered");
		
		super.init();
		
		configuration = new Configuration();
		
		List<String> spaceList = new LinkedList<String>();
		spaceList.add("banapple");
		configuration.setSpaceList(spaceList);
	}


	public static void setLog(Log log)
	{
		SitemapServlet.log = log;
	}


	public void setSettingsManager(SettingsManager settingsManager)
	{
		this.settingsManager = settingsManager;
	}


	public void setConfiguration(Configuration configuration)
	{
		this.configuration = configuration;
	}
	
	
	
}
