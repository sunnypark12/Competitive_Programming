#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>
using namespace std;

vector<int> compute(int length, const vector<int>& values) {
    if (length == 4) return vector<int>(length, 0);
    vector<int> solution(length, 0);
    auto idxCalculate = [length](int index) { return (index + length) % length; };

    for (int i = 0; i < length; ++i) {
        int left = idxCalculate(i - 1);
        int right = idxCalculate(i + 1);
        if (values[left] == values[right]) {
            solution[i] = values[left];
        }
        if (values[left] <= values[right] && values[right] < values[idxCalculate(i + 3)]) {
            solution[i] = values[right];
        }
        if (values[right] <= values[left] && values[left] < values[idxCalculate(i - 3)]) {
            solution[i] = values[left];
        }
    }

    vector<pair<int, int>> toFill;
    vector<int> values;
    vector<bool> visited(length, false);
    for (int i = 0; i < length; ++i) {
        if (solution[i] == 0) {
            toFill.push_back({max(values[idxCalculate(i - 1)], values[idxCalculate(i + 1)]), i});
        } else {
            visited[solution[i] - 1] = true;
        }
    }
    for (int i = 0; i < length; ++i) {
        if (!visited[i]) values.push_back(i + 1);
    }

    sort(toFill.begin(), toFill.end());
    sort(values.begin(), values.end());
    int size = toFill.size(), curr = 0;
    for (int i = 0; i < size; ++i) {
        int count = 0;
        while (curr < size && toFill[curr].first <= values[i]) {
            curr++;
            count++;
        }
        if (curr == i + 1 && count == 1) {
            solution[toFill[i].second] = values[i];
        }
    }
    return solution;
}

int main() {
    int T;
    cin >> T;
    while (T--) {
        int size;
        cin >> size;
        vector<int> elements(size);
        for (int i = 0; i < size; i++) {
            cin >> elements[i];
        }
        vector<int> result = compute(size, elements);
        for (int x : result) {
            cout << x << " ";
        }
        cout << endl;
    }
    return 0;
}