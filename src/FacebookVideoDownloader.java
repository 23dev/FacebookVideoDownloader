import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class FacebookVideoDownloader {

	private String url;
	private String encodedMP4Link;
	private String decodedMP4Link;

	public FacebookVideoDownloader(String url) {
		getMobileUrl(url);
	}

	public void getVideo() {
		try {
			getPageSourceAfterJs();
			getMP4Link();
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
		// System.out.println("Resultat : " + decodedMP4Link);
	}

	public void getPageSourceAfterJs() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
		WebClient webClient = new WebClient();
		System.out.println("Loading page now: " + url);
		HtmlPage page = webClient.getPage(url);
		webClient.waitForBackgroundJavaScript(
				30 * 1000); /* will wait JavaScript to execute up to 30s */
		String pageAsXml = page.asXml();
		webClient.close();
		savePageSourceAfterJs(pageAsXml);
	}

	
	private void savePageSourceAfterJs(String src) { 
		try {
			PrintWriter out = new PrintWriter("site.html");
			out.println(src);
			out.close();
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		}
	}

	private void getMobileUrl(String urlAlt) {
		url = urlAlt.replace("https://www.", "https://m.");
	}

	public void getMP4Link() throws IOException {
		// Mkyong
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("site.html"));

			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains(".mp4")) {
					System.out.println(sCurrentLine);
					String[] parts = sCurrentLine.split("=");
					encodedMP4Link = parts[3];
					encodedMP4Link = encodedMP4Link.replace("&amp;source", "");
					decodedMP4Link = java.net.URLDecoder.decode(encodedMP4Link, "UTF-8");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void downloadMP4() throws MalformedURLException, IOException {
		URLConnection conn = new URL(decodedMP4Link).openConnection();
		InputStream is = conn.getInputStream();

		OutputStream outstream = new FileOutputStream(new File("video.mp4"));
		byte[] buffer = new byte[4096];
		int len;
		while ((len = is.read(buffer)) > 0) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
