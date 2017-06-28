package dsp2_2;

//H29年度・dsp2-2・5J16杉山隼太

class Dsp2_2 {

	public static void main(String[] args) {
		System.out.println("H29年度・dsp2-2・5J16杉山隼太");
		System.out.println("--------how to use--------");
		System.out.println("プログラムを実行すると，samp.bmpからYCCデータを読み込んで，\n2次元DCT結果をファイルに書き込み，画像を生成する");

		Image test = new Image("samp.bmp");
		test.rgbToYcc();

		//test.printYccData();

		FileClass.writeDoubleDataToFile("yccData.txt", test.getYccData());

		int count = 0;

		double[][] luminance = new double[120][120];
		for (int i = 0; i < luminance.length; i++) {
			for (int j = 0; j < luminance[i].length; j++) {
				luminance[i][j] = test.getYccData(count++, 0);
			}
		}
		Compression mat = new Compression(luminance);

		Compression dctMat = mat.makeDctMatrix();
		Compression idctMat = mat.makeIdctMatrix();

		Compression resultDct;
		Compression resultIdct;

		resultDct = dctMat.multiplyMatrix(mat).multiplyMatrix(idctMat);
		if (FileClass.writeTwoDimensionDoubleData("outputYcc.csv", resultDct.m)) {
			System.out.println("writing to file succeeded");
		}
		resultIdct = idctMat.multiplyMatrix(resultDct).multiplyMatrix(dctMat);

		count = 0;

		for (int i = 0; i < resultIdct.getNumOfRow(); i++) {
			for (int j = 0; j < resultIdct.getNumOfColumn(); j++) {
				test.setYccData(count, resultIdct.m[i][j], 0, 0);
				count++;
			}
		}

		//--------------自主課題--------------

		//block
		/*Compression[] mat = new Compression[225];
		double[][][] luminance = new double[225][8][8];
		for (int i = 0; i < luminance.length; i++) {
			for (int j = 0; j < luminance[i].length; j++) {
				for (int k = 0; k < luminance[i][j].length; k++) {
					luminance[i][j][k] = test.getYccData(count++, 0);
				}
			}

			mat[i] = new Compression(luminance[i]);
		}

		Compression dctMat = mat[0].makeDctMatrix();
		Compression idctMat = mat[0].makeIdctMatrix();

		Compression[] resultDct = new Compression[mat.length];
		Compression[] resultIdct = new Compression[mat.length];

		for (int i = 0; i < mat.length; i++) {
			if (dctMat.multipliable(mat[i]) && mat[i].multipliable(idctMat)) {
				resultDct[i] = dctMat.multiplyMatrix(mat[i]).multiplyMatrix(idctMat);
				FileClass.writeDoubleDataToFile(String.format("dctOfBlock/%d.txt", i), resultDct[i].m);

				//量子化
				resultDct[i].quantization(40.0);
				FileClass.writeDoubleDataToFile(String.format("afterQuantization/%d.txt", i), resultDct[i].m);

				resultIdct[i] = idctMat.multiplyMatrix(resultDct[i]).multiplyMatrix(dctMat);
				FileClass.writeDoubleDataToFile(String.format("idctMatrix/%d.txt", i), resultIdct[i].m);
			}	
		}

		count = 0;

		for (int i = 0; i < resultIdct.length; i++) {
			for (int j = 0; j < resultIdct[i].getNumOfRow(); j++) {
				for (int k = 0; k < resultIdct[i].getNumOfColumn(); k++) {
					test.setYccData(count, resultIdct[i].m[j][k], 0, 0);
					count++;
				}
			}
		}*/

		test.yccToRgb();
		test.rgbToByteData();
		if (test.makeBmpFile("outputBmp.bmp")) {
			System.out.println("success");
		} else {
			System.out.println("fault");
		}

	}
}