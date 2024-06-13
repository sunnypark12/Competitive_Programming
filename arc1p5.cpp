#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>

using namespace std;

struct Edge {
    int src, dest, weight, index;
    bool operator<(const Edge& other) const {
        return weight < other.weight;
    }
};

struct UnionFind {
    vector<int> parent, rank;
    UnionFind(int n) : parent(n), rank(n, 0) {
        iota(parent.begin(), parent.end(), 0);
    }

    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]);
        return parent[x];
    }

    void unionSet(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY])
                parent[rootY] = rootX;
            else if (rank[rootX] < rank[rootY])
                parent[rootX] = rootY;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N, M, K;
    cin >> N >> M >> K;
    
    vector<Edge> edges(M);
    vector<int> activationDay(M, -1);

    for (int i = 0; i < M; ++i) {
        cin >> edges[i].src >> edges[i].dest >> edges[i].weight;
        edges[i].src--;
        edges[i].dest--;
        edges[i].index = i;
    }

    sort(edges.begin(), edges.end());

    for (int day = 1; day <= K; ++day) {
        UnionFind uf(N);
        int count = 0;

        for (const auto& e : edges) {
            if (activationDay[e.index] == -1) {
                if (uf.find(e.src) != uf.find(e.dest)) {
                    uf.unionSet(e.src, e.dest);
                    activationDay[e.index] = day;
                    count++;
                    if (count == N - 1) break; 
                }
            }
        }

        if (count == 0) break; 
    }

    for (int day : activationDay) {
        cout << day << "\n";
    }

    return 0;
}