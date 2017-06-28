package dsp2_2;

public class Matrix {
    // 必要なフィールド（インスタンス）変数を宣言せよ
    double[][] m;
    int numOfRow;
    int numOfColumn;
	
	public Matrix(){
		// 行列の行数,列数を格納するインスタンス変数の値を0に初期化
		this.numOfRow = 0;
		this.numOfColumn = 0;
	}

	public Matrix(double[][] input){
		// 二次元配列 input の内容で、行列（インスタンス変数）を初期化せよ(例：配列 inputの0行0列目の値を、行列の0行0列目とする)
		this.numOfRow = input.length;
		this.numOfColumn = input[0].length;
		this.m = new double[this.numOfRow][this.numOfColumn];
		
		this.m = input;
    }

	public Matrix(double[] input){
		// 一次元配列 input の内容で、行列（インスタンス変数）を初期化せよ(例：行数は1、列数はinputの要素数とする)
		int j;
		
		this.numOfRow = 1;
		this.numOfColumn = input.length;
		this.m = new double[this.numOfRow][this.numOfColumn];
		
		for (j = 0; j < input.length; j++) {
			this.m[0][j] = input[j];
		}
	}

	public int getNumOfRow(){
		return this.numOfRow;
    }
        
	public int getNumOfColumn(){
		return this.numOfColumn;
    }

	public double showsComponentOf(int rowIndex, int columnIndex){
		// 指定した範囲が存在しない場合
        if(rowIndex > this.numOfRow || columnIndex > this.numOfColumn){
            System.out.println("指定する要素は存在しません.");
            System.exit(0);
        }
        // 指定された要素に対応する値を返す
        return this.m[rowIndex][columnIndex];
    }
        
	public void display(){
		// 行列内容の表示処理を実装せよ
		int i, j;
		
		for (i = 0; i < this.m.length; i++) {
			System.out.print("[");
			for (j = 0; j < this.m[i].length; j++) {
				if (j < this.m[i].length - 1) {
					System.out.print(this.m[i][j] + " ");
					//System.out.printf("%f ", this.m[i][j]);
				} else {
					System.out.print(this.m[i][j]);
					//System.out.printf("%f", this.m[i][j]);
				}
			}
			System.out.println("]");
		}
	}

	// ベクトルAとBの内積 A・Bの結果を返す
	public double getInnerProduct(Matrix b){
		int i;
		double innerProduct = 0;
		
		// Aが列ベクトルである場合、エラーメッセージを表示させて System.exit(0)
		// A, B 双方とも行ベクトル、かつ、要素数が等しければ内積を計算
		// Aが行ベクトル、Bが列ベクトルで、要素数が等しければ内積を計算
		// 内積計算が可能な条件を満たさない場合は、エラーメッセージを表示させてSystem.exit(0)
		if (this.numOfColumn < 1) {
			System.out.println("Aが列ベクトルです.");
			System.exit(0);
		} else if (this.numOfColumn == b.numOfColumn && this.numOfRow == 1 && b.numOfRow == 1) {
			for (i = 0; i < this.numOfColumn; i++) {
				innerProduct += this.m[0][i] * b.m[0][i];
			}
		} else if (this.numOfColumn == b.numOfRow && this.numOfRow == 1 && b.numOfColumn == 1) {
			for (i = 0; i < this.numOfColumn; i++) {
				innerProduct += this.m[0][i] * b.m[i][0];
			}
		} else {
			System.out.println("内積計算が可能ではありません");
			System.exit(0);
		}
		
		// 計算結果を返す
		return innerProduct;
	}
	
	public boolean multipliable(Matrix y) {
		if (this.numOfColumn == y.numOfRow) return true;
		else {
			System.out.println("要素数が計算できる組み合わせとなっていません.");
			return false;
		}
	}

    // 行列同士、もしくは行列とベクトルとの積を計算する
    public Matrix multiplyMatrix(Matrix target){
    	int i, j, k, l;
    	Matrix result;
    	result = new Matrix();
    	
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
	
	public static double convertIntoRadian(double theta) {
		return theta * (Math.PI / 180);
	}
	
	public Matrix rotate(double degree) {
		if (this.numOfRow <= 1 || this.numOfColumn > 1) {
			System.out.println("列ベクトルが指定されていません");
			System.exit(0);
		}
		
		double radian = Matrix.convertIntoRadian(degree);
		double rot[][] = {{Math.cos(radian), -Math.sin(radian)},
						   {Math.sin(radian), Math.cos(radian)}};
		Matrix rotMatrix = new Matrix(rot);
		Matrix result;
		
		result = rotMatrix.multiplyMatrix(this);
		
		return result;
	}

	public Matrix transpose() {
		int i, j;
		Matrix transMatrix;
		transMatrix = new Matrix();
		
		transMatrix.numOfRow = this.numOfColumn;
		transMatrix.numOfColumn = this.numOfRow;
		transMatrix.m = new double[transMatrix.numOfRow][transMatrix.numOfColumn];
		
		for (i = 0; i < transMatrix.numOfRow; i++) {
			for (j = 0; j < transMatrix.numOfColumn; j++) {
				transMatrix.m[i][j] = this.m[j][i];
			}
		}
		
		return transMatrix;
	}

	

	public static void main(String[] args) {
		Matrix mat1, mat2, mat3;
		double degree1 = 45, degree2 = -60;
		
		double[][]
			v = {
				{5.0, 3.0},
				{4.0, -3.0},
				{2.0, 6.0}},
				
			q1 = {
				{-5.0},
				{-5.0}},
			
			q2 = {
				{3.0},
				{5.196}};
		
		mat1 = new Matrix(v);
		mat2 = new Matrix(q1);
		mat3 = new Matrix(q2);
		
		System.out.println("Original matrix:");
		mat1.display();
		
		System.out.println("\nTransposed matrix:");
		(mat1.transpose()).display();
		
		System.out.println("\nCoordinate point (1) before rotation");
		mat2.display();
		
		System.out.println("\nCoordinate point (1) after rotating " + degree1 + " degree");
		(mat2.rotate(degree1)).display();
		
		System.out.println("\nCoordinate point (2) before rotation");
		mat3.display();
		
		System.out.println("\nCoordinate point (2) after rotating " + degree2 + " degree");
		(mat3.rotate(degree2)).display();
    }
}