#include <iostream>
#include "format.h"

using namespace std;

int main() {
    double a = 124;
    double b = 2.9;
    char c = 'q';
    string s = "abacaba";
    cout << format("%i %i",1, 4);
    //cout << format("%f %.3f hello%%world %c %s %%", a, b, c, "kekerlab") << endl;
    return 0;
}