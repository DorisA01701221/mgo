package singletons;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import javax.imageio.ImageIO;

public class ImageLoader {
	private static ImageLoader loader;
	private final static String IMAGE_DIR = "/img/";
	private HashMap imagesMap;
	private HashMap gNamesMap;
	private GraphicsConfiguration gc;

	private ImageLoader() {
		initLoader();
		loadImagesFile("imsInfo.txt");
	}
	public static ImageLoader getLoader() {
		if(loader == null) {
			loader = new ImageLoader();
		}
		return loader;
	}
	private void initLoader() {
		imagesMap = new HashMap();
		gNamesMap = new HashMap();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
	}
	private void loadImagesFile(String fnm){
		String imsFNm = IMAGE_DIR + fnm;
		System.out.println("Reading file: " + imsFNm);
		try {
			InputStream in = this.getClass().getResourceAsStream(imsFNm);
			BufferedReader br = new BufferedReader( new InputStreamReader(in));
			String line;
			char ch;
			while((line = br.readLine()) != null) {
				if (line.length() == 0)
					continue;
				if (line.startsWith("//"))
					continue;
				ch = Character.toLowerCase( line.charAt(0) );
				if (ch == 'o')  // a single image
					getFileNameImage(line);
				else
					System.out.println("Do not recognize line: " + line);
			}
			br.close();
		}
		catch (IOException e)
		{ System.out.println("Error reading file: " + imsFNm);
		System.exit(1);
		}
	}
	private void getFileNameImage(String line){
		StringTokenizer tokens = new StringTokenizer(line);
		if (tokens.countTokens() != 2)
			System.out.println("Wrong no. of arguments for " + line);
		else {
			tokens.nextToken();
			System.out.print("o Line: ");
			loadSingleImage( tokens.nextToken() );
		}
	}
	public boolean loadSingleImage(String fnm) {
		String name = getPrefix(fnm);
		if (imagesMap.containsKey(name)) {
			System.out.println( "Error: " + name + "already used");
			return false;
		}
		BufferedImage bi = loadImage(fnm);
		if (bi != null) {
			ArrayList imsList = new ArrayList();
			imsList.add(bi);
			imagesMap.put(name, imsList);
			System.out.println("  Stored " + name + "/" + fnm);
			return true;
		}
		else
			return false;
	}
	private String getPrefix(String fnm) {
		int posn;
		if ((posn = fnm.lastIndexOf(".")) == -1) {
			System.out.println("No prefix found for filename: " + fnm);
			return fnm;
		}
		else
			return fnm.substring(0, posn);
	}
	public BufferedImage loadImage(String fnm) {
		try {
			BufferedImage im =  ImageIO.read(getClass().getResource(IMAGE_DIR + fnm) );
			int transparency = im.getColorModel().getTransparency();
			BufferedImage copy =  gc.createCompatibleImage(
					im.getWidth(), im.getHeight(),
					transparency );
			Graphics2D g2d = copy.createGraphics();
			g2d.drawImage(im,0,0,null);
			g2d.dispose();
			return copy;
		}
		catch(IOException e) {
			System.out.println("Load Image error for " +
					IMAGE_DIR + "/" + fnm + ":\n" + e);
			return null;
		}
	}
	public BufferedImage getImage(String name) {
		ArrayList imsList = (ArrayList) imagesMap.get(name);
		if (imsList == null) {
			System.out.println("No image(s) stored under " + name);
			return null;
		}
		return (BufferedImage) imsList.get(0);
	}
}
