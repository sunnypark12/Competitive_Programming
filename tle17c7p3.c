#include <stdio.h>
#include <math.h>

int readInput() {
    int value;
    scanf("%d", &value);
    return value;
}

double count(double b, int h, int t) {
    double output = b;
    for (int i = 0; i < h - 1; ++i) {
        if (output > t) {
            break;
        }
        output = pow(b, output);
    }
    return output;
}

int main() {
    int q = readInput();
    for (int curr = 0; curr < q; ++curr) {
        int r = readInput();
        int final = readInput();
        double upper = final, lower = 0.0;
        for (int i = 0; i < 50; ++i) {
            double midPoint = (upper + lower) / 2.0;
            double result = count(midPoint, r, final);

            if (result > final) {
                upper = midPoint;
            } else {
                lower = midPoint;
            }
        }
        printf("%.9lf\n", lower);
    }
    return 0;
}