package dsp2_1;

public class Complex {
  private double re_;
  private double im_;

  public Complex() {
    this.re_ = 0;
    this.im_ = 0;
  }

  public Complex(double x, double y) {
    this.re_ = x;
    this.im_ = y;
  }

  public double getComp_re() {
    return this.re_;
  }

  public double getComp_im() {
    return this.im_;
  }

  public void setComp_re(double re) {
    this.re_ = re;
  }

  public void setComp_im(double im) {
    this.im_ = im;
  }

  public Complex addComplex(Complex comp) {
    return new Complex(this.re_ + comp.re_, this.im_ + comp.im_);
  }

  public Complex subtractComplex(Complex comp) {
    return new Complex(this.re_ - comp.re_, this.im_ - comp.im_);
  }

  public Complex multiplyComplex(Complex comp) {
    return new Complex(this.re_ * comp.re_ - this.im_ * comp.im_, this.re_ * comp.im_ + this.im_ * comp.re_);
  }

  public Complex divideComplex(Complex comp) {
    return new Complex((this.re_*comp.re_ + this.im_*comp.im_) / (comp.re_*comp.re_ + comp.im_*comp.im_),
                        (this.im_*comp.re_ - this.re_*comp.im_) / (comp.re_*comp.re_ + comp.im_*comp.im_));
  }

  public double absComplex() {
    return Math.sqrt(this.re_ * this.re_ + this.im_ * this.im_);
  }

  public Complex conjugate() {
    return new Complex(this.re_, -this.im_);
  }

  public Complex polarToOrth(double z, double theta) {
    return new Complex(z * Math.cos(theta), z * Math.sin(theta));
  }

  /*public void autoCorrelationByFFT(Complex[] wnk, int N) {
    int r_big = 1, r_sma = N / 2;
    int i, j, k, in1, in2, nk;
    int r = (int)(log(N)/log(2.0) + 0.5);
    Complex dummy = new Complex();

    for (i = 0; i < r; i++) {
      for (j = 0; j < r_big; j++) {
        for (k = 0; k < r_sma; k++) {
          in1 = r_big * 2 * k + j;
          in2 = in1 + r_big;
          nk = j * r_sma;

          dummy = in[in2].multiplyComplex(wnk[nk]);
          this[in2] = this[in1].subtractComplex(dummy);
          this[in1] = this[in1].addComplex(dummy);
        }
      }

      r_big *= 2;
      r_sma /= 2;
    }
  }*/
}
