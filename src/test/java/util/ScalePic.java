
package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScalePic {

	public static void main(String[] args) throws IOException {
		File root = new File("./src/main/resources/assets/l2weaponry/textures/item/generated/");
		for (var mat : root.listFiles()) {
			if (!mat.isDirectory()) continue;
			for (var file : mat.listFiles()) {
				if (file.getName().startsWith("nunchaku")) {
					flip(file);
				}
			}
		}
	}

	private static void flip(File in) throws IOException {
		BufferedImage img = ImageIO.read(in);
		int sx = img.getWidth();
		int sy = img.getHeight();
		BufferedImage ans = new BufferedImage(sx, sy, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < sx; x++)
			for (int y = 0; y < sy; y++) {
				int col = img.getRGB(x, y);
				ans.setRGB(sx - x - 1, y, col);
			}
		ImageIO.write(ans, "PNG", in);
	}

}
