import java.io.IOException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

/**
 * @author 23dev
 *
 */
public class Main {

	// For testing purposes
	public static void main(String[] args) {
		// Random video
		FacebookVideoDownloader dl = new FacebookVideoDownloader(
				"https://www.facebook.com/swissmeme1/videos/vb.194707187359049/499721983524233/?type=2&theater");

		try {
			dl.setForDownload();
			dl.downloadMP4("video");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
