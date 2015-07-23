import java.io.IOException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Main {
	
	public static void main(String[] args) {
		//FacebookVideoDownloader dl = new FacebookVideoDownloader("https://m.facebook.com/story.php?story_fbid=804873796270798&id=162557827169068&refsrc=https%3A%2F%2Fm.facebook.com%2FJuliensBlogTV%2Fvideos%2F804873796270798%2F&_rdr");
		FacebookVideoDownloader dl = new FacebookVideoDownloader("https://www.facebook.com/swissmeme1/videos/vb.194707187359049/499721983524233/?type=2&theater");
		
		dl.getVideo();
		try {
			dl.downloadMP4();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
