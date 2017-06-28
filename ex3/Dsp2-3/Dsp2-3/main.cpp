/*dsp2-3/5J16/杉山隼太*/

#include <iostream>
#include <vector>
#include <cmath>
#include <stdlib.h>
#include <string.h>
#include "Matrix.h"
using namespace std;

Matrix::Matrix():numOfRow(0), numOfColumn(0){}

Matrix::Matrix(vector<double> input) {
    vector<vector <double> > v(input.size(), vector<double>(1, 0));
    //m(input.size(), vector<double>(1));
    for (int i = 0; i < input.size(); i++) {
        v[i][0] = input[i];
    }
    m = v;
    numOfRow = (int)input.size();
    numOfColumn = 1;
}

Matrix::Matrix(vector<vector <double> > input) {
    m = input;
    numOfRow = (int)input.size();
    numOfColumn = (int)input[0].size();
}

Matrix::Matrix(int row, int column) {
    numOfRow = row;
    numOfColumn = column;
    m.resize(numOfRow);
}

int Matrix::getNumOfRow() {
    return numOfRow;
}

int Matrix::getNumOfColumn() {
    return numOfColumn;
}

void Matrix::display() {
    for (int i = 0; i < m.size(); i++) {
        cout << "[";
        
        for (int j = 0; j < m[i].size(); j++) {
            if (j < m[i].size() - 1) {
                cout << m[i][j] << " ";
            } else {
                cout << m[i][j];
            }
        }
        
        cout << "]" << endl;
    }
    
    cout << endl;
}

double Matrix::getInnerProduct(Matrix b) {
    double innerProduct = 0;
    
    if (getNumOfColumn() < 1) {
        cout << "A is error" << endl;
        exit(1);
    } else if (getNumOfColumn() == b.getNumOfColumn() && getNumOfRow() == 1 && b.getNumOfRow() == 1) {
        for (int i = 0; i < getNumOfColumn(); i++) {
            innerProduct += m[0][i] * b.m[0][i];
        }
    } else if (getNumOfColumn() == b.getNumOfRow() && getNumOfRow() == 1 && b.getNumOfColumn() == 1) {
        for (int i = 0; i < getNumOfColumn(); i++) {
            innerProduct += m[0][i] * b.m[i][0];
        }
    } else if (getNumOfRow() == b.getNumOfRow() && getNumOfColumn() == 1 && b.getNumOfColumn() == 1) {
        for (int i = 0; i < getNumOfRow(); i++) {
            innerProduct += m[i][0] * b.m[i][0];
        }
    } else {
        cout << "can't calculate" << endl;
        exit(1);
    }
    
    return innerProduct;
}

bool Matrix::multipliable(Matrix target) {
    if (getNumOfColumn() == target.getNumOfRow()) return true;
    else {
        cout << "can't calculate" << endl;
        exit(1);
    }
}

Matrix Matrix::multiplyMatrix(Matrix target) {
    vector<vector <double> > result(getNumOfRow(), vector<double>(target.getNumOfColumn(), 0));
    
    if (getNumOfColumn() == target.getNumOfRow()) {
        for (int i = 0; i < getNumOfRow(); i++) {
            for (int j = 0; j< target.getNumOfColumn(); j++) {
                for (int k = 0; k < getNumOfColumn(); k++) {
                    result[i][j] += m[i][k] * target.m[k][j];
                }
            }
        }
    } else {
        cout << "error" << endl;
        exit(1);
    }
    
    return Matrix(result);
}

double Matrix::getAbsoluteValue() {
    double result = 0;
    
    if (getNumOfRow() == 1) {
        for (int i = 0; i < getNumOfColumn(); i++) {
            result += m[0][i] * m[0][i];
        }
    } else if (getNumOfColumn() == 1) {
        for (int i = 0; i < getNumOfRow(); i++) {
            result += m[i][0] * m[i][0];
        }
    } else {
        cout << "error" << endl;
        exit(1);
    }
    
    return result;
}

Matrix Matrix::normalizeMatrix() {
    double normalizeValue = sqrt(getAbsoluteValue());
    //cout << "normalizeValue:" << normalizeValue << endl;
    Matrix result(m);
    
    for (int i = 0; i < getNumOfRow(); i++) {
        for (int j = 0; j < getNumOfColumn(); j++) {
            result.m[i][j] = m[i][j] / normalizeValue;
        }
    }
    
    return result;
}

Matrix Matrix::subtractMatrix(Matrix target) {
    vector<vector <double> > result(getNumOfRow(), vector<double>(getNumOfColumn(), 0));
    
    if (getNumOfRow() == target.getNumOfRow() && getNumOfColumn() == target.getNumOfColumn()) {
        for (int i = 0; i < getNumOfRow(); i++) {
            for (int j = 0; j < getNumOfColumn(); j++) {
                result[i][j] = m[i][j] - target.m[i][j];
            }
        }
    }
    
    return Matrix(result);
}

void Matrix::constantMult(double num) {
    for (int i = 0; i < getNumOfRow(); i++) {
        for (int j = 0; j < getNumOfColumn(); j++) {
            m[i][j] *= num;
        }
    }
}

Matrix Matrix::transposeMatrix() {
    vector<vector <double> > result(getNumOfColumn(), vector<double>(getNumOfRow(), 0));
    
    for (int i = 0; i < getNumOfRow(); i++) {
        for (int j = 0; j < getNumOfColumn(); j++) {
            result[j][i] = m[i][j];
        }
    }
    
    return Matrix(result);
}

void Matrix::power() {
    const double EPSILON = 0.0001;
    static int count = 0;
    double eigenValue = 0;
    Matrix pastVector;
    vector<double> v(getNumOfRow(), 0);
    
    for (int i = 0; i < v.size(); i++) {
        if (i == 0) v[i] = 1;
        else v[i] = 0;
    }
    
    Matrix eigenVector(v);
    
    do {
        pastVector = eigenVector.normalizeMatrix();
        
        if (multipliable(pastVector)) {
            eigenVector = multiplyMatrix(pastVector);
            eigenValue = eigenVector.getInnerProduct(pastVector);
        }

        cout << "e = " << eigenVector.normalizeMatrix().subtractMatrix(pastVector).getAbsoluteValue() << endl;
    } while (eigenVector.normalizeMatrix().subtractMatrix(pastVector).getAbsoluteValue() >= EPSILON);
    
    cout << endl << "eigen value:" << eigenValue << endl << endl;
    eigenVector = eigenVector.normalizeMatrix();
    cout << "eigen vector:" << endl;
    eigenVector.display();
    
    Matrix trans = eigenVector.transposeMatrix();
    trans.constantMult(eigenValue);
    count++;

    cout << "the residual matrix:" << endl;
    subtractMatrix(eigenVector.multiplyMatrix(trans)).display();

    cout << "---------------------------" << endl << endl;
    
    if (count != getNumOfRow()) subtractMatrix(eigenVector.multiplyMatrix(trans)).power();
    
}

int main(int argc, const char * argv[]) {
    // insert code here...

    cout << "H29年度・dsp2-3・5J16杉山隼太" << endl;
    cout << "--------how to use--------" << endl;
    cout << "プログラムを実行すると，課題の行列の固有値・固有ベクトルをパワー法で求めてくれる．" << endl << endl;

    //vector<vector <double> > v = {{2, 1, 3}, {1, 2, 3}, {3, 3, 20}};
    //vector<vector <double> > v = {{2, 1}, {1, 2}};
    /*vector<vector <double> > v(2, vector<double>(2));
    v[0][0] = 2;
    v[0][1] = 1;
    v[1][0] = 1;
    v[1][1] = 2;*/
    vector<vector <double> > v(3, vector<double>(3));
    v[0][0] = 2;
    v[0][1] = 1;
    v[0][2] = 3;
    v[1][0] = 1;
    v[1][1] = 2;
    v[1][2] = 3;
    v[2][0] = 3;
    v[2][1] = 3;
    v[2][2] = 20;
    Matrix mat(v);
    cout << "matrix of the challenges:" << endl;
    mat.display();
    cout << "---------------------------" << endl << endl;
    mat.power();
    return 0;
}
