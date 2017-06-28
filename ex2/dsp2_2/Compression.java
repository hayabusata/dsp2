package dsp2_2;

public class Compression extends Matrix{

	public Compression() {

	}

	public Compression(double[][] input) {
		super(input);
	}

	public Compression makeDctMatrix() {
		int k, n;
		int size = this.numOfRow;

		double[][] mat = new double[size][size];

		for (n = 0; n < size; n++) {
			for (k = 0; k < size; k++) {
				if (k == 0) {
					mat[k][n] = 1 / Math.sqrt(size);
				} else {
					mat[k][n] = Math.sqrt(2.0 / size) * Math.cos(((2 * n + 1) * k * Math.PI) / (2.0 * size));
				}
			}
		}

		return new Compression(mat);
	}

	public Compression makeIdctMatrix() {
		int k, n;
		int size = this.numOfRow;

		double[][] mat = new double[size][size];

		for (k = 0; k < size; k++) {
			for (n = 0; n < size; n++) {
				if (k == 0) {
					mat[n][k] = 1 / Math.sqrt(size);
				} else {
					mat[n][k] = Math.sqrt(2.0 / size) * Math.cos(((2 * n + 1) * k * Math.PI) / (2.0 * size));
				}
			}
		}

		return new Compression(mat);
	}

	public boolean multipliable(Compression y) {
		if (this.numOfColumn == y.numOfRow) return true;
		else {
			System.out.println("要素数が計算できる組み合わせとなっていません.");
			return false;
		}
	}

    // 行列同士、もしくは行列とベクトルとの積を計算する
    public Compression multiplyMatrix(Compression target){
    	int i, j, k, l;
    	Compression result;
    	result = new Compression();
    	
    	result.numOfRow = this.numOfRow;			//計算結果の行列の行数を決定
    	result.numOfColumn = target.numOfColumn;	//計算結果の行列の列数を決定
		result.m = new double[result.numOfRow][result.numOfColumn];

		for (i = 0; i < result.numOfRow; i++) {
			for (j = 0; j < result.numOfColumn; j++) {
				result.m[i][j] = 0;					//配列の初期化
			}
		}

		// 掛けられる行列の列数と掛ける行列の行数が等しいなら
		if(this.numOfColumn == target.numOfRow){
			// 積の計算処理を実装せよ
			for (i = 0; i < this.numOfRow; i++) {
				for (j = 0; j < target.numOfColumn; j++) {
					for (k = 0; k < this.numOfColumn; k++) {
						result.m[i][j] += this.m[i][k] * target.m[k][j];
					}
				}
			}
        }
        else{
            System.out.println("要素数が計算できる組み合わせとなっていません");
            System.exit(0);
        }
        
        // 積の結果をMatrix型で返す
        return result;
    }

	//量子化
	public void quantization(double threshold) {
		for (int i = 0; i < this.getNumOfRow(); i++) {
			for (int j = 0; j < this.getNumOfColumn(); j++) {
				if (this.m[i][j] < threshold && this.m[i][j] > -threshold && threshold >= 0) {
					this.m[i][j] = 0;
				}
			}
		}
	}

	public void quantization() {
		int[][] quaMatrix = 
			{{16, 11, 10, 16, 24, 40, 51, 61},
			 {12, 12, 14, 19, 26, 58, 60, 55},
			 {14, 13, 16, 24, 40, 57, 69, 56},
			 {14, 17, 22, 29, 51, 87, 80, 62},
			 {18, 22, 37, 56, 68, 109, 103, 77},
			 {24, 35, 55, 64, 81, 104, 113, 92},
			 {49, 64, 78, 87, 103, 212, 120, 101},
			 {72, 92, 95, 98, 112, 100, 103, 99}};

		for (int i = 0; i < this.getNumOfRow(); i++) {
			for (int j = 0; j < this.getNumOfColumn(); j++) {
				this.m[i][j] = (int)(this.m[i][j] / quaMatrix[i][j] + 0.5);
				System.out.println(i + ":" + j + ":" + this.m[i][j]);
			}
		}
	}

	public static void main(String[] args) {
		Matrix ans, idctAns;
		//double[] input = FileClass.readDoubleDataFromFile("test.txt");
		//double[][] input = {{1.0, 1.0, 1.0, 1.0}, {2.0, 1.0, 2.0, 1.0}, {3.0, 1.0, 3.0, 1.0}, {4.0, 1.0, 4.0, 1.0}};
		double[][] input = {{5, 4}, {3, 0}};

		System.out.println("equation");

		Compression com = new Compression(input);
		com.display();
		Matrix dct = com.makeDctMatrix();
		Matrix idct = com.makeIdctMatrix();

		System.out.println("\ndctResult");

		if (dct.multipliable(com)) {
			ans = dct.multiplyMatrix(com).multiplyMatrix(idct);
			ans.display();

			System.out.println("\nidctResult");
			idctAns = idct.multiplyMatrix(ans).multiplyMatrix(dct);
			idctAns.display();
			/*if (FileClass.writeDoubleDataToFile("output.txt", ans.m)) {
				System.out.println("success");
			}*/
		}

	}
}