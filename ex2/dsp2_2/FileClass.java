package dsp2_2;

import java.io.*;

public class FileClass {
  public FileClass() {
  }

  public static int countLine(String filename) {
    int count = 0;

    try {
      FileReader fr = new FileReader(new File(filename));
      BufferedReader br = new BufferedReader(fr);

      while (br.readLine() != null) {
        count++;
      }

      br.close();
      return count;
    } catch (FileNotFoundException e) {
      System.out.println(e);
      return -1;
    } catch (IOException e) {
      System.out.println(e);
      return -1;
    }
  }

  public static double[] readDoubleDataFromFile(String filename) {
    String line;
    int i;
    double[] data;
    data = new double[countLine(filename)];

    if (data.length != -1) {
      try {
        FileReader fr = new FileReader(new File(filename));
        BufferedReader br = new BufferedReader(fr);

        for (i = 0; i < data.length; i++) {
          line = br.readLine();
          data[i] = Double.parseDouble(line);
        }

        br.close();
        return data;
      } catch (FileNotFoundException e) {
        System.out.println(e);
        System.exit(0);
      } catch (IOException e) {
        System.out.println(e);
        System.exit(0);
      }
    } else {
      System.out.println("error");
    }

    return data;
  }

  public static byte[] readByteDataFromFile(String filename) {
    String line;
    int i;
    byte[] data;
    data = new byte[countLine(filename)];

    if (data.length != -1) {
      try {
        FileReader fr = new FileReader(new File(filename));
        BufferedReader br = new BufferedReader(fr);

        for (i = 0; i < data.length; i++) {
          line = br.readLine();
          data[i] = Byte.valueOf(line);
        }

        br.close();
        return data;
      } catch (FileNotFoundException e) {
        System.out.println(e);
        System.exit(0);
      } catch (IOException e) {
        System.out.println(e);
        System.exit(0);
      }
    } else {
      System.out.println("error");
    }

    return data;
  }

  public static double[][] readTwoDimensionData(String filename) {
    String line;
    int i, j;
    double[][] data;
    int size = countLine(filename);
    data = new double[(int)Math.sqrt(size)][(int)Math.sqrt(size)];

    if (data.length != -1) {
      try {
        FileReader fr = new FileReader(new File(filename));
        BufferedReader br = new BufferedReader(fr);

        for (i = 0; i < data.length; i++) {
          for (j = 0; j < data[i].length; j++) {
            line = br.readLine();
            data[i][j] = Double.parseDouble(line);
          }
        }

        br.close();
        return data;
      } catch (FileNotFoundException e) {
        System.out.println(e);
        System.exit(0);
      } catch (IOException e) {
        System.out.println(e);
        System.exit(0);
      }
    } else {
      System.out.println("error");
    }

    return data;
  }

  /*public static Complex[] readDoubleDataToComplex(String filename, int N) {
    String line;
    int i;
    Complex[] comp = new Complex[N];
    int num = countLine(filename);

    if (num != -1) {
      try {
        FileReader fr = new FileReader(new File(filename));
        BufferedReader br = new BufferedReader(fr);

        for (i = 0; i < num; i++) {
          line = br.readLine();
          comp[i] = new Complex();
          comp[i].setComp_re(Double.parseDouble(line));
          comp[i].setComp_im(0.0);
        }

        br.close();

        for (i = num; i < N; i++) {
          comp[i] = new Complex();
          comp[i].setComp_re(0.0);
          comp[i].setComp_im(0.0);
        }

        return comp;
      } catch (FileNotFoundException e) {
        System.out.println(e);
      } catch (IOException e) {
        System.out.println(e);
      }
    } else {
      System.out.println("error");
    }

    return comp;
  }*/

  public static boolean writeDoubleDataToFile(String filename, double[] data) {
    try {
      File file = new File(filename);
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

      if (checkBeforeWritefile(file)) {
        for (int i = 0; i < data.length; i++) {
          pw.println(data[i]);
        }

        pw.close();
        return true;
      } else {
        System.out.println("error");
      }
    } catch (IOException e) {
      System.out.println(e);
    }

    return false;
  }

  public static boolean writeDoubleDataToFile(String filename, double[][] data) {
    try {
      File file = new File(filename);
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

      if (checkBeforeWritefile(file)) {
        for (int i = 0; i < data.length; i++) {
          for (int j = 0; j < data[i].length; j++) {
            pw.println(data[i][j]);
          }
        }

        pw.close();
        return true;
      } else {
        System.out.println("error");
      }
    } catch (IOException e) {
      System.out.println(e);
    }

    return false;
  }

  public static boolean writeIntDataToFile(String filename, int[] data) {
    try {
      File file = new File(filename);
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

      if (checkBeforeWritefile(file)) {
        for (int i = 0; i < data.length; i++) {
          pw.println(data[i]);
        }

        pw.close();
        return true;
      } else {
        System.out.println("error");
      }
    } catch (IOException e) {
      System.out.println(e);
    }

    return false;
  }

  public static boolean writeIntDataToFile(String filename, int[][] data) {
    try {
      File file = new File(filename);
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

      if (checkBeforeWritefile(file)) {
        for (int i = 0; i < data.length; i++) {
          for (int j = 0; j < data[i].length; j++) {
            pw.println(data[i][j]);
          }
        }

        pw.close();
        return true;
      } else {
        System.out.println("error");
      }
    } catch (IOException e) {
      System.out.println(e);
    }

    return false;
  }

  public static boolean writeByteDataToFile(String filename, byte[] data) {
    try {
      File file = new File(filename);
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

      if (checkBeforeWritefile(file)) {
        for (int i = 0; i < data.length; i++) {
          pw.println(data[i]);
        }

        pw.close();
        return true;
      } else {
        System.out.println("error");
      }
    } catch (IOException e) {
      System.out.println(e);
    }

    return false;
  }

  public static boolean writeByteDataToFile(String filename, byte[][] data) {
    try {
      File file = new File(filename);
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

      if (checkBeforeWritefile(file)) {
        for (int i = 0; i < data.length; i++) {
          for (int j = 0; j < data[i].length; j++) {
            pw.println(data[i][j]);
          }
        }

        pw.close();
        return true;
      } else {
        System.out.println("error");
      }
    } catch (IOException e) {
      System.out.println(e);
    }

    return false;
  }

  public static boolean writeTwoDimensionDoubleData(String filename, double[][] data) {
    try {
      File file = new File(filename);
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

      if (checkBeforeWritefile(file)) {
        for (int i = 0; i < data.length; i++) {
          for (int j = 0; j < data[i].length; j++) {
            pw.print(data[i][j] + ",");
          }

          pw.println();
        }

        pw.close();
        return true;
      } else {
        System.out.println("error");
      }
    } catch(IOException e) {
      System.out.println(e);
    }

    return false;
  }

  private static boolean checkBeforeWritefile(File file){
    if (file.exists()){
      if (file.isFile() && file.canWrite()){
        return true;
      }
    }

    return false;
  }
}
