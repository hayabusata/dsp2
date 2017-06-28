//dsp2-1・4J16杉山隼太

package dsp2_1;

class Dsp2_1 {
	public static void main(String[] args) {
	    int i;
	    int line = FileClass.countLine("data3.txt");
	    //long start = System.nanoTime();

	    System.out.println("H29年度/dsp2-1/4J16杉山隼太");
	    System.out.println("プログラムを起動すると，data3.txtからデータを持ってきて");
	    System.out.println("通常の方法とFFTを用いる方法で自己相関関数を求めてくれる．");
	    System.out.println("出力結果はファイルに出力される．");

	    Correlation cor = new Correlation("data3.txt", 0);
	    cor.autoCorrelationByNormal();

	    /*long end = System.nanoTime();
	    System.out.println(end - start);

	    start = System.nanoTime();*/

	    Correlation cor1 = new Correlation("data3.txt", 1);

	    Complex[] wnk = Correlation.twid_fft(cor1.getCompLength());
	    int[] bit = Correlation.bit_r(cor1.getCompLength());

	    Correlation cor2 = new Correlation(cor1.getCompLength());
	    for (i = 0; i < cor1.getCompLength(); i++) {
	    	cor2.setComp(i, cor1.getComp(bit[i]).getComp_re(), cor1.getComp(bit[i]).getComp_im());
	    }

	    cor2.fft(wnk, cor2.getCompLength());

	    if (FileClass.writeDoubleDataToFile("powerSPC.txt", Correlation.getAmplitudeSpc(cor2.getCompArray(), cor2.getCompLength()))) {
	      System.out.println("amplitude output success");
	      /*for (i = 0; i < cor2.getCompLength(); i++) {
	      	System.out.println(String.format("%f %f", cor2.getComp(i).getComp_re(),cor2.getComp(i).getComp_im()));
	      }*/
	    } else {
	      System.out.println("amplitude output fault");
	    }

	    Correlation cor3 = new Correlation("powerSPC.txt", 1);    //Complex[] data = FileClass.readDoubleDataToComplex("amplitudeSPC.txt");
	    wnk = Correlation.twid_ifft(cor3.getCompLength());

	    Correlation cor4 = new Correlation(cor3.getCompLength());
	    for (i = 0; i < cor3.getCompLength(); i++) {
	      cor4.setComp(i, cor3.getComp(bit[i]).getComp_re(), cor3.getComp(bit[i]).getComp_im());
	    }

	    cor4.ifft(wnk, cor4.getCompLength());

	    double[] result = new double[cor4.getCompLength()];
	    for (i = 0; i < cor4.getCompLength(); i++) {
	      //System.out.println("value " + cor4.getComp(i).getComp_re());
	      cor4.setComp(i, cor4.getComp(i).getComp_re() / line, cor4.getComp(i).getComp_im() / line);
	      //System.out.println(String.format("%f, %f", cor4.comp[i].getComp_re(), cor4.comp[i].getComp_im()));
	      result[i] = cor4.getComp(i).getComp_re();
	    }

	    if (FileClass.writeDoubleDataToFile("autoCorrelationByFFT.txt", result)) {
	      System.out.println("auto correlation output success");
	    } else {
	      System.out.println("auto correlation output fault");
	    }

	    /*end = System.nanoTime();
	    System.out.println(end - start);*/
	}
}