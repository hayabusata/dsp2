package dsp2_4;

//H29年度・dsp2-4・5J16

public class Dsp2_4 {
    public static void main(String[] args) {
        System.out.println("H29年度・dsp2-4・5J16");
        System.out.println("--------how to use--------");
        System.out.println("Dsp2_4.javaを実行すると，プログラムが実行され,変換逆変換を行う。\n" );

        double[] array = {10, 6, 2, 4, 8, 2, 6, 4};
        //double[] array = {12, 0, 4, 6, 10, 2, 14, 8};

        Wavelet wavelet = new Wavelet(array);
        wavelet.waveletTransform();
        //System.out.println("scaling:" + wavelet.getScalingFactor());
        //System.out.println("wavelet:" + wavelet.getWaveletFactor());
        wavelet.waveletReverse();
    }
}
