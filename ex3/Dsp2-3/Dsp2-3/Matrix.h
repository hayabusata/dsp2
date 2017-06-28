#ifndef Matrix_h
#define Matrix_h

#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

class Matrix {
    vector<vector <double> > m;
    int numOfRow;
    int numOfColumn;
public:
    Matrix();
    Matrix(vector<double> input);
    Matrix(vector<vector <double> > input);
    Matrix(int row, int column);
    int getNumOfRow();
    int getNumOfColumn();
    void display();
    double getInnerProduct(Matrix b);
    bool multipliable(Matrix target);
    Matrix multiplyMatrix(Matrix targer);
    double getAbsoluteValue();
    Matrix normalizeMatrix();
    Matrix subtractMatrix(Matrix target);
    void constantMult(double num);
    Matrix transposeMatrix();
    void power();
};



#endif /* Matrix_hpp */
