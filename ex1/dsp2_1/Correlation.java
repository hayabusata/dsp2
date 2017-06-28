package dsp2_1;

public class Correlation {
  final int NUM = 256;

  private double[] data;
  private Complex[] comp;

  public Correlation(int N) {
    this.data = new double[N];
    this.comp = new Complex[N];
  }

  public Correlation(String filename, int mode) {
    if (mode <= 0) {
      this.data = FileClass.readDoubleDataFromFile(filename);
    } else {
      this.comp = FileClass.readDoubleDataToComplex(filename, NUM);
    }
  }

  public void setData(int i, double x) {
    this.data[i] = x;
  }

  public void setComp(int i, double re, double im) {
    this.comp[i] =  new Complex(re, im);
  }

  public double getData(int i) {
    return this.data[i];
  }

  public Complex[] getCompArray() {
    return this.comp;
  }

  public Complex getComp(int i) {
    return this.comp[i];
  }

  public int getCompLength() {
    return this.comp.length;
  }

  public void autoCorrelationByNormal() {
    int i, j;
    double[] answer = new double[this.data.length];

    for (i = 0; i < this.data.length; i++) {
      answer[i] = 0;

      for (j = 0; j < this.data.length; j++) {
        if (j + i < this.data.length) {
          answer[i] += this.data[j] * this.data[j + i];
        } else {
          answer[i] += 0;
        }
      }
      answer[i] /= this.data.length;
      //System.out.println(answer[i]);
    }

    FileClass.writeDoubleDataToFile("autoCorrelationByNormal.txt", answer);
  }

  //回転子
  public static Complex[] twid_fft(int N) {
    int i;
    Complex[] result = new Complex[N];

    for (i = 0; i < N; i++) {
      result[i] = new Complex(Math.cos(2 * Math.PI / N * i), Math.sin(-2 * Math.PI / N * i));
    }

    return result;
  }

  public static Complex[] twid_ifft(int N) {
    int i;
    Complex[] result = new Complex[N];

    for (i = 0; i < N; i++) {
      result[i] = new Complex(Math.cos(2 * Math.PI / N * i), Math.sin(2 * Math.PI / N * i));
    }

    return result;
  }

  //ビットリバーサル
  public static int[] bit_r(int N) {
    int i, j;
    int[] bit = new int[N];
    int r = (int)(Math.log(N) / Math.log(2.0) + 0.5);

    for (i = 0; i < N; i++) {
      bit[i] = 0;
      for (j = 0; j < r; j++) {
        bit[i] += ((i >> j) & 1) << (r - j - 1);
      }
    }

    return bit;
  }

  public void fft(Complex[] wnk, int N) {
    int r_big = 1, r_sma = N / 2;
    int i, j, k, in1, in2, nk;
    int r = (int)(Math.log(N)/Math.log(2.0) + 0.5);
    Complex dummy = new Complex();

    for (i = 0; i < r; i++) {
      for (j = 0; j < r_big; j++) {
        for (k = 0; k < r_sma; k++) {
          in1 = r_big * 2 * k + j;
          in2 = in1 + r_big;
          nk = j * r_sma;

          dummy = this.comp[in2].multiplyComplex(wnk[nk]);
          this.comp[in2] = this.comp[in1].subtractComplex(dummy);
          this.comp[in1] = this.comp[in1].addComplex(dummy);
        }
      }

      r_big *= 2;
      r_sma /= 2;
    }
  }

  public void ifft(Complex[] wnk, int N) {
    int r_big = 1, r_sma = N / 2;
    int i, j, k, in1, in2, nk;
    int r = (int)(Math.log(N)/Math.log(2.0) + 0.5);
    Complex dummy = new Complex();

    for (i = 0; i < r; i++) {
      for (j = 0; j < r_big; j++) {
        for (k = 0; k < r_sma; k++) {
          in1 = r_big * 2 * k + j;
          in2 = in1 + r_big;
          nk = j * r_sma;

          dummy = this.comp[in2].multiplyComplex(wnk[nk]);
          this.comp[in2] = this.comp[in1].subtractComplex(dummy);
          this.comp[in1] = this.comp[in1].addComplex(dummy);
        }
      }

      r_big *= 2;
      r_sma /= 2;
    }

    for (i = 0; i < this.comp.length; i++) {
      this.comp[i].setComp_re(this.comp[i].getComp_re() / N);
      this.comp[i].setComp_im(this.comp[i].getComp_im() / N);
    }
  }

  public static double[] getAmplitudeSpc(Complex[] comp, int N) {
    int i;
    double[] result = new double[N];

    for (i = 0; i < N; i++) {
      result[i] = comp[i].absComplex() * comp[i].absComplex();
    }

    return result;
  }

  public static void main(String[] args) {
    int i;
    int line = FileClass.countLine("data3.txt");
    long start = System.nanoTime();

    /*Correlation cor = new Correlation("data3.txt", 0);
    cor.autoCorrelationByNormal();*/

    Correlation cor1 = new Correlation("data3.txt", 1);

    Complex[] wnk = twid_fft(cor1.comp.length);
    int[] bit = bit_r(cor1.comp.length);

    Correlation cor2 = new Correlation(cor1.comp.length);
    for (i = 0; i < cor1.comp.length; i++) {
      cor2.comp[i] = new Complex(cor1.comp[bit[i]].getComp_re(), cor1.comp[bit[i]].getComp_im());
    }

    cor2.fft(wnk, cor2.comp.length);

    if (FileClass.writeDoubleDataToFile("powerSPC.txt", getAmplitudeSpc(cor2.comp, cor2.comp.length))) {
      System.out.println("amplitude output success");
    } else {
      System.out.println("amplitude output fault");
    }

    Correlation cor3 = new Correlation("powerSPC.txt", 1);    //Complex[] data = FileClass.readDoubleDataToComplex("amplitudeSPC.txt");
    wnk = twid_ifft(cor3.comp.length);

    Correlation cor4 = new Correlation(cor3.comp.length);
    for (i = 0; i < cor3.comp.length; i++) {
      cor4.comp[i] = new Complex(cor3.comp[bit[i]].getComp_re(), cor3.comp[bit[i]].getComp_im());
    }

    cor4.ifft(wnk, cor4.comp.length);

    double[] result = new double[cor4.comp.length];
    for (i = 0; i < cor4.comp.length; i++) {
      cor4.comp[i].setComp_re(cor4.comp[i].getComp_re() / line);
      cor4.comp[i].setComp_im(cor4.comp[i].getComp_im() / line);
      //System.out.println(String.format("%f, %f", cor4.comp[i].getComp_re(), cor4.comp[i].getComp_im()));
      result[i] = cor4.comp[i].getComp_re();
    }

    if (FileClass.writeDoubleDataToFile("autoCorrelationByFFT.txt", result)) {
      System.out.println("auto correlation output success");
    } else {
      System.out.println("auto correlation output fault");
    }

    long end = System.nanoTime();
    System.out.println(end - start);
  }
}
