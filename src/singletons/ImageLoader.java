package singletons;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*; 

public class ImageLoader {
	private final static String IMAGE_DIR = "/imagenes/";
	private HashMap imagesMap;
	private HashMap gNamesMap;
	private GraphicsConfiguration gc;
	private static ImageLoader loader;

	private ImageLoader(String fnm){
		initLoader();
		loadImagesFile(fnm);
	}
	
	private ImageLoader(){ 
		initLoader();  
	} 
	
	public static ImageLoader getImageLoader(String fnm) {
		if (loader == null) {
			loader= new ImageLoader(fnm);
		} return loader;
	}
	
	public static ImageLoader getImageLoader() {
		if (loader == null) {
			loader = new ImageLoader("imsInfo.txt");
		} return loader;
	}
	private void initLoader(){
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
				else if (ch == 'n')  // a numbered sequence of images
					getNumberedImages(line);
				else if (ch == 's')  // an images strip
					getStripImages(line);
				else if (ch == 'g')  // a group of images
					getGroupImages(line);
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
	//single image
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
	//numered image
	private void getNumberedImages(String line) {
		StringTokenizer tokens = new StringTokenizer(line);

		if (tokens.countTokens() != 3)
			System.out.println("Wrong no. of arguments for " + line);
		else {
			tokens.nextToken();    // skip command label
			System.out.print("n Line: ");

			String fnm = tokens.nextToken();
			int number = -1;
			try {
				number = Integer.parseInt( tokens.nextToken() );
			}
			catch(Exception e)
			{ System.out.println("Number is incorrect for " + line);  }

			loadNumImages(fnm, number);
		}
	}
	public int loadNumImages(String fnm, int number) {
		String prefix = null;
		String postfix = null;
		int starPosn = fnm.lastIndexOf("*");   // find the '*'
		if (starPosn == -1) {
			System.out.println("No '*' in filename: " + fnm);
			prefix = getPrefix(fnm);
		}
		else {   // treat the fnm as prefix + "*" + postfix
			prefix = fnm.substring(0, starPosn);
			postfix = fnm.substring(starPosn+1);
		}

		if (imagesMap.containsKey(prefix)) {
			System.out.println( "Error: " + prefix + "already used");
			return 0;
		}

		return loadNumImages(prefix, postfix, number); 
	}
	private int loadNumImages(String prefix, String postfix, int number) {
		String imFnm;
		BufferedImage bi;
		ArrayList imsList = new ArrayList();
		int loadCount = 0;

		if (number <= 0) {
			System.out.println("Error: Number <= 0: " + number);
			imFnm = prefix + postfix;
			if ((bi = loadImage(imFnm)) != null) {
				loadCount++;
				imsList.add(bi);
				System.out.println("  Stored " + prefix + "/" + imFnm);
			}
		}
		else {  
			System.out.print("  Adding " + prefix + "/" + 
					prefix + "*" + postfix + "... ");
			for(int i=0; i < number; i++) {
				imFnm = prefix + i + postfix;
				if ((bi = loadImage(imFnm)) != null) {
					loadCount++;
					imsList.add(bi);
					System.out.print(i + " ");
				}
			}
			System.out.println();
		}

		if (loadCount == 0)
			System.out.println("No images loaded for " + prefix);
		else 
			imagesMap.put(prefix, imsList);

		return loadCount; 
	}
	//strip image
	private void getStripImages(String line) {
		StringTokenizer tokens = new StringTokenizer(line);

		if (tokens.countTokens() != 3)
			System.out.println("Wrong no. of arguments for " + line);
		else {
			tokens.nextToken();    // skip command label
			System.out.print("s Line: ");

			String fnm = tokens.nextToken();
			int number = -1;
			try {
				number = Integer.parseInt( tokens.nextToken() );
			}
			catch(Exception e)
			{ System.out.println("Number is incorrect for " + line);  }

			loadStripImages(fnm, number);
		} 
	}
	public int loadStripImages(String fnm, int number) {
		String name = getPrefix(fnm);
		if (imagesMap.containsKey(name)) {
			System.out.println( "Error: " + name + "already used");
			return 0;
		}
		// load the images into an array
		BufferedImage[] strip = loadStripImageArray(fnm, number);
		if (strip == null)
			return 0;

		ArrayList imsList = new ArrayList();
		int loadCount = 0;
		System.out.print("  Adding " + name + "/" + fnm + "... ");
		for (int i=0; i < strip.length; i++) {
			loadCount++;
			imsList.add(strip[i]);
			System.out.print(i + " ");
		}
		System.out.println();

		if (loadCount == 0)
			System.out.println("No images loaded for " + name);
		else 
			imagesMap.put(name, imsList);

		return loadCount;
	}
	//seuence images
	private void getGroupImages(String line) {
		StringTokenizer tokens = new StringTokenizer(line);

		if (tokens.countTokens() < 3)
			System.out.println("Wrong no. of arguments for " + line);
		else {
			tokens.nextToken();    // skip command label
			System.out.print("g Line: ");

			String name = tokens.nextToken();

			ArrayList fnms = new ArrayList();
			fnms.add( tokens.nextToken() );  // read filenames
			while (tokens.hasMoreTokens())
				fnms.add( tokens.nextToken() );

			loadGroupImages(name, fnms);
		} 
	}
	public int loadGroupImages(String name, ArrayList fnms) {
		if (imagesMap.containsKey(name)) {
			System.out.println( "Error: " + name + "already used");
			return 0;
		}

		if (fnms.size() == 0) {
			System.out.println("List of filenames is empty");
			return 0;
		}

		BufferedImage bi;
		ArrayList nms = new ArrayList();
		ArrayList imsList = new ArrayList();
		String nm, fnm;
		int loadCount = 0;

		System.out.println("  Adding to " + name + "...");
		System.out.print("  ");
		for (int i=0; i < fnms.size(); i++) {    // load the files
			fnm = (String) fnms.get(i);
			nm = getPrefix(fnm);
			if ((bi = loadImage(fnm)) != null) {
				loadCount++;
				imsList.add(bi);
				nms.add( nm );
				System.out.print(nm + "/" + fnm + " ");
			}
		}
		System.out.println();

		if (loadCount == 0)
			System.out.println("No images loaded for " + name);
		else {
			imagesMap.put(name, imsList);
			gNamesMap.put(name, nms);
		}

		return loadCount; 
	}
	public int loadGroupImages(String name, String[] fnms) {
		ArrayList al = new ArrayList( Arrays.asList(fnms) );
		return loadGroupImages(name, al);   
	}
	//access methods
	public BufferedImage getImage(String name) {
		ArrayList imsList = (ArrayList) imagesMap.get(name);
		if (imsList == null) {
			System.out.println("No image(s) stored under " + name);  
			return null;
		}
		return (BufferedImage) imsList.get(0);
	}
	public BufferedImage getImage(String name, int posn) {
		ArrayList imsList = (ArrayList) imagesMap.get(name);
		if (imsList == null) {
			System.out.println("No image(s) stored under " + name);  
			return null;
		}

		int size = imsList.size();
		if (posn < 0) {

			return (BufferedImage) imsList.get(0);   // return first image
		}
		else if (posn >= size) {
			int newPosn = posn % size;   
			return (BufferedImage) imsList.get(newPosn);
		}
		return (BufferedImage) imsList.get(posn);
	}
	public BufferedImage getImage(String name, String fnmPrefix) {
		ArrayList imsList = (ArrayList) imagesMap.get(name);
		if (imsList == null) {
			System.out.println("No image(s) stored under " + name);  
			return null;
		}

		int posn = getGroupPosition(name, fnmPrefix);
		if (posn < 0) {
			return (BufferedImage) imsList.get(0);   // return first image
		}
		return (BufferedImage) imsList.get(posn); 
	}
	private int getGroupPosition(String name, String fnmPrefix) {
		ArrayList groupNames = (ArrayList) gNamesMap.get(name);
		if (groupNames == null) {
			System.out.println("No group names for " + name);  
			return -1;
		}

		String nm;
		for (int i=0; i < groupNames.size(); i++) {
			nm = (String) groupNames.get(i);
			if (nm.equals(fnmPrefix))
				return i;          // the posn of <fnmPrefix> in the list of names
		}

		System.out.println("No " + fnmPrefix + 
				" group name found for " + name);  
		return -1;
	}
	public ArrayList getImages(String name) {
		ArrayList imsList = (ArrayList) imagesMap.get(name);
		if (imsList == null) {
			System.out.println("No image(s) stored under " + name);  
			return null;
		}

		System.out.println("Returning all images stored under " + name);  
		return imsList;
	}
	public boolean isLoaded(String name) {
		ArrayList imsList = (ArrayList) imagesMap.get(name);
		if (imsList == null)
			return false;
		return true; 
	}
	public int numImages(String name) {
		ArrayList imsList = (ArrayList) imagesMap.get(name);
		if (imsList == null) {
			System.out.println("No image(s) stored under " + name);  
			return 0;
		}
		return imsList.size(); 
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
	private void reportTransparency(String fnm, int transparency) {
		System.out.print(fnm + " transparency: ");
		switch(transparency) {
		case Transparency.OPAQUE:
			System.out.println("opaque");
			break;
		case Transparency.BITMASK:
			System.out.println("bitmask");
			break;
		case Transparency.TRANSLUCENT:
			System.out.println("translucent");
			break;
		default:
			System.out.println("unknown");
			break;
		} 
	} 
	private BufferedImage loadImage2(String fnm) {
		ImageIcon imIcon = new ImageIcon(
				getClass().getResource(IMAGE_DIR + fnm) );
		if (imIcon == null)
			return null;

		int width = imIcon.getIconWidth();
		int height = imIcon.getIconHeight();
		Image im = imIcon.getImage();

		return makeBIM(im, width, height); 
	}
	private BufferedImage makeBIM(Image im, int width, int height) {
		BufferedImage copy = new BufferedImage(width, height, 
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = copy.createGraphics();
		g2d.drawImage(im,0,0,null);
		g2d.dispose();
		return copy;
	}
	public BufferedImage loadImage3(String fnm) {
		Image im = readImage(fnm);
		if (im == null)
			return null;

		int width = im.getWidth( null );
		int height = im.getHeight( null );

		return makeBIM(im, width, height);
	}
	private Image readImage(String fnm) {
		Image image = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource(IMAGE_DIR + fnm) );
		MediaTracker imageTracker = new MediaTracker( new JPanel() );

		imageTracker.addImage(image, 0);
		try {
			imageTracker.waitForID(0);
		}
		catch (InterruptedException e) {
			return null;
		}
		if (imageTracker.isErrorID(0))
			return null;
		return image;
	}
	public BufferedImage[] loadStripImageArray(String fnm, int number) {
		if (number <= 0) {
			System.out.println("number <= 0; returning null");
			return null;
		}

		BufferedImage stripIm;
		if ((stripIm = loadImage(fnm)) == null) {
			System.out.println("Returning null");
			return null;
		}

		int imWidth = (int) stripIm.getWidth() / number;
		int height = stripIm.getHeight();
		int transparency = stripIm.getColorModel().getTransparency();

		BufferedImage[] strip = new BufferedImage[number];
		Graphics2D stripGC;
		for (int i=0; i < number; i++) {
			strip[i] =  gc.createCompatibleImage(imWidth, height, transparency);
			stripGC = strip[i].createGraphics();
			stripGC.drawImage(stripIm, 
					0,0, imWidth,height,
					i*imWidth,0, (i*imWidth)+imWidth,height,
					null);
			stripGC.dispose();
		} 
		return strip;
	}

}
