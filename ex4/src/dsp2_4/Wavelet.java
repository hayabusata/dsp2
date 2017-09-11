package dsp2_4;

class Wavelet {
    private double[] data;
    private double[] answer;
    private double lastScalingFactor;
    private double lastWaveletFactor;

    public Wavelet() {
        this.lastScalingFactor = 0;
        this.lastWaveletFactor = 0;
    }

    public Wavelet(double[] array) {
        this.data = array;
        this.lastScalingFactor = 0;
        this.lastWaveletFactor = 0;
    }

    public double getScalingFactor() {
        return this.lastScalingFactor;
    }

    public double getWaveletFactor() {
        return this.lastWaveletFactor;
    }

    public void waveletTransform() {
        double[] array = this.data;
        this.answer = new double[this.data.length];
        int num = this.answer.length;
        double normalizeValue = Math.sqrt(2);
        int maxLevel = (int)(Math.log(this.data.length) / Math.log(2));

        for (int i = 1; i <= maxLevel; i++) {
            num /= 2;

            for (int j = 0; j < num; j++) {
                this.answer[j] = (array[j * 2] + array[j * 2 + 1]) / normalizeValue;
                this.answer[j + num] = (array[j * 2] - array[j * 2 + 1]) / normalizeValue;
            }

            for (int j = 0; j < this.answer.length; j++) {
                array[j] = this.answer[j];
            }

            System.out.println("level" + i + " scaling");
            for (int j = 0; j < 8 / Math.pow(2, i); j++) {
                System.out.println(answer[j]);
            }

            System.out.println("level" + i + " wavelet");
            for (int j = 0; j < 8 / Math.pow(2, i); j++) {
                System.out.println(answer[j + (int)(8 / Math.pow(2, i))]);
            }

            System.out.println();
        }

        this.lastScalingFactor = this.answer[0];
        this.lastWaveletFactor = this.answer[1];
    }

    public double[] waveletReverse() {
        double[] ret = new double[this.answer.length];
        double[] ret2 = new double[this.answer.length];
        int count = 1;
        int num = 1;
        int maxLevel = (int)(Math.log(this.answer.length) / Math.log(2));
        double normalizeValue = Math.sqrt(2);
        ret[0] = this.answer[0];

        for (int i = 1; i <= maxLevel; i++) {

            for (int j = 0; j < num; j++) {
                ret2[j * 2] = (ret[j] + this.answer[count]) / normalizeValue;
                ret2[j * 2 + 1] = (ret[j] - this.answer[count]) / normalizeValue;
                count++;
            }

            num *= 2;

            for (int j = 0; j < num; j++) {
                ret[j] = ret2[j];
            }
        }

        System.out.println("result of wavelet reverse");
        for (int i = 0; i < ret.length; i++) {
            System.out.println(ret[i]);
        }

        return ret;
    }
}
