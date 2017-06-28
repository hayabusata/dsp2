package dsp2_2;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

class Image {
	private byte[] imageData;
	private double[][] rgbData;
	private double[][] yccData;

	public Image() {

	}

	//画像を読み込み，byte配列にするコンストラクタ
	public Image(String filename) {
		try {
			BufferedImage bi = ImageIO.read(new File(filename));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, filename.substring(filename.lastIndexOf(".") + 1), baos);
			baos.flush();
			this.imageData = baos.toByteArray();

			//this.printImageData();
			FileClass.writeByteDataToFile("imageData.txt", this.imageData);
			FileClass.writeIntDataToFile("unsignedImageData.txt", this.byteToUnsigned());

			// System.out.println(bi.getWidth());
			// System.out.println(bi.getHeight());
			// System.out.println(bi.getRGB(1, 1));

			this.rgbData = new double[(this.imageData.length - 54) / 3][3];
			for (int i = 0; i < this.rgbData.length; i++) {
				for (int j = 0; j < this.rgbData[i].length; j++) {
					this.rgbData[i][j] = (this.imageData[54 + i * 3 + j] + 255) % 255;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public double getRgbData(int x, String color) {
		if (color.equals("red")) return this.rgbData[x][0];
		else if (color.equals("green")) return this.rgbData[x][1];
		else if (color.equals("blue")) return this.rgbData[x][2];
		else return -1;
	}

	public double[][] getRgbData() {
		return this.rgbData;
	}

	public double[][] getYccData() {
		return this.yccData;
	}

	public double getYccData(int x, int y) {
		if (x < this.yccData.length && x >= 0 && y < this.yccData[0].length && y >= 0) {
			return this.yccData[x][y];
		}
		return -1;
	}

	public void setYccData(int i, double y, double cb, double cr) {
		if (i >= 0 && i < this.yccData.length) {
			this.yccData[i][0] = y;
			this.yccData[i][1] = cb;
			this.yccData[i][2] = cr;
		}
	}

	public int[] byteToUnsigned() {
		int[] output = new int[this.imageData.length];

		for (int i = 0; i < this.imageData.length; i++) {
			output[i] = (this.imageData[i] + 255) % 255;
		}

		return output;
	}

	public void printImageData() {
		for (int i = 0; i < this.imageData.length; i++) {
			System.out.println(i + ":" + imageData[i]);
		}
	}

	public void printRgbData() {
		for (int i = 0; i < this.rgbData.length; i++) {
			for (int j = 0; j < this.rgbData[i].length; j++) {
				System.out.println(i + ":" + j + ": " + this.rgbData[i][j]);
			}
		}
	}

	public void printYccData() {
		for (int i = 0; i < this.rgbData.length; i++) {
			for (int j = 0; j < this.rgbData[i].length; j++) {
				System.out.println(i + ":" + j + ": " + this.yccData[i][j]);
			}
		}
	}

	public boolean makeBmpFile(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(filename));
			fos.write(this.imageData);
			fos.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/*public boolean makeBmpFile(String readFilename) {
		this.imageData = FileClass.readByteDataFromFile(readFilename);

		try {
			FileOutputStream fos = new FileOutputStream(new File("outputBmp.bmp"));
			fos.write(this.imageData);
			fos.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}*/

	public void rgbToYcc() {
		this.yccData = new double[this.rgbData.length][3];

		for (int i = 0; i < this.yccData.length; i++) {
			this.yccData[i][0] = (int)(0.299 * this.rgbData[i][0] + 0.587 * this.rgbData[i][1] + 0.114 * this.rgbData[i][2]);
			this.yccData[i][1] = (int)(-0.169 * this.rgbData[i][0] - 0.331 * this.rgbData[i][1] + 0.5 * this.rgbData[i][2]);
			this.yccData[i][2] = (int)(0.5 * this.rgbData[i][0] - 0.419 * this.rgbData[i][1] - 0.081 * this.rgbData[i][2]);
		}
	}

	public void yccToRgb() {
		for (int i = 0; i < this.rgbData.length; i++) {
			this.rgbData[i][0] = (int)(this.yccData[i][0] + 1.402 * this.yccData[i][2]);
			this.rgbData[i][1] = (int)(this.yccData[i][0] - 0.344 * this.yccData[i][1] - 0.714 * this.yccData[i][2]);
			this.rgbData[i][2] = (int)(this.yccData[i][0] + 1.772 * this.yccData[i][1]);
		}
	}

	public double[][] luminanceToSquare() {
		double[][] luminance = new double[(int)Math.sqrt(this.yccData.length)][(int)Math.sqrt(this.yccData.length)];

		for (int i = 0; i < luminance.length; i++) {
			for (int j = 0; j < luminance[i].length; j++) {
				luminance[i][j] = this.yccData[j + i * luminance.length][0];
			}
		}

		return luminance;
	}

	public void rgbToByteData() {
		for (int i = 0; i < this.rgbData.length; i++) {
			for (int j = 0; j < this.rgbData[i].length; j++) {
				this.imageData[i * this.rgbData[i].length + j + 54] = (byte)rgbData[i][j];
			}
		}
	}

	public static void main(String[] args) {
		Image test = new Image("samp.bmp");
		

		/*Image test2 = new Image();
		if (test2.makeBmpFile("imageData.txt")) {
			System.out.println("success");
		}*/
	}
}