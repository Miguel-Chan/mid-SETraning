#include <iostream>

using namespace std;

int main() {
    int i = 1;
    while (true) {
        int k;
        cin >> k;
        cout << k << ": " << i++ << endl;
    }
}