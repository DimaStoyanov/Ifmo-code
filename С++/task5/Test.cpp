#include <iostream>
#include "format.h"

using namespace std;

int main() {
    int a[5] = {1, 2, 3, 4, 5};
    int *b = a + 1;
    nullptr_t qa = nullptr;
    void *c = nullptr;
    string s = "hello";
   cout << format("%@", a) << endl;        //returns "[1,2,3,4,5]"
    cout << format("%@", b) << endl;        //returns "ptr<int>(2)"
    cout << format("%@", c) << endl;        //returns "nullptr<void>"
    cout << format("%@", nullptr) << endl;  //returns "nullptr"
   cout << format("%@", s) << endl;
    cout << format("%@", "helloworld") << endl;
    cout << format("%@", (int*) nullptr) << endl;
  //  cout << format("%f %.3f hello%%world %c %s %%", a, b, c, "kekerlab") << endl;
    return 0;
}