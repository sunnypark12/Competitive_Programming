#include <bits/stdc++.h>
using namespace std;

const long long INF = LLONG_MAX / 3;
const int MAX_WEIGHT = 2001 * (1 << 7), DELTA = MAX_WEIGHT / 2;
int N, M;
long long dp[2][MAX_WEIGHT];

struct Pair {
    int weight, value;
    Pair(int weight, int value) : weight(weight), value(value) {}
};

void solve() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cin >> N >> M;
    vector<Pair> items;
    for (int i = 0; i < N; i++) {
        int w, v;
        cin >> w >> v;
        items.emplace_back(w, v);
    }
    for (int i = 0; i < M; i++) {
        int w, v;
        cin >> w >> v;
        items.emplace_back(-w, v);
    }
    
    random_device rd;
    mt19937 g(rd());
    shuffle(items.begin(), items.end(), g);

    memset(dp, 0xc0, sizeof(dp));
    dp[0][DELTA] = 0;
    int curr = 0, next = 1;

    for (auto &item : items) {
        memset(dp[next], 0xc0, sizeof(dp[next]));
        for (int i = 0; i < MAX_WEIGHT; i++) {
            if (0 <= i + item.weight && i + item.weight < MAX_WEIGHT) {
                dp[next][i + item.weight] = max(dp[next][i + item.weight], dp[curr][i] + item.value);
            }
            dp[next][i] = max(dp[next][i], dp[curr][i]);
        }
        swap(curr, next);
    }

    cout << dp[curr][DELTA] << "\n";
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        solve();
    }

    return 0;
}