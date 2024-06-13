#include <iostream>
#include <cmath>
#include <cstdlib>
#include <ctime>

double monteCarlo(double L) {
    int points = 30000;
    int count = 0;
    double x, y;

    srand(time(NULL));

    for (int i = 0; i < points; ++i) {
        x = (double)rand() / RAND_MAX * L;
        y = exp(-x * x);
        double randomY = (double)rand() / RAND_MAX;
        if (randomY <= y) {
            ++count;
        }
    }
    double area = (double)count / points * L;
    return area;
}

int main() {
    double L;
    while (true) {
        std::cin >> L;
        if (L == -1) {
            break;
        }
        std::cout.precision(2);
        std::cout << std::fixed << monteCarlo(L) << std::endl;
    }

    return 0;
}