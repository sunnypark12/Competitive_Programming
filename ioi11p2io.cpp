#include <bits/stdc++.h>
#include <functional>

using namespace std;

typedef long long l;

void readInput(){}

template<typename F, typename... R> void readInput(F &f, R&... r){
    cin >> f; readInput(r...);
    
}

struct Edge {
    int target, weight;
};

const int MAX_NODES = 200001, MAX_DIST = 1000001;
int numNodes, maxDist, minCost = 0x3f3f3f3f, subtreeSize[MAX_NODES], distCost[MAX_DIST];
bool nodeBlocked[MAX_NODES];

vector<Edge> graph[MAX_NODES];
vector<pair<int, int>> updates;

int findCentroid(int node, int parent, int half) {
    for (Edge adj : graph[node])
        if ((adj.target ^ parent) && subtreeSize[adj.target] > half && !nodeBlocked[adj.target])
            return findCentroid(adj.target, node, half);
    return node;
}

void depthFirstSearch(int node, int parent, int distance, int level) {
    if (distance <= maxDist)
        minCost = min(minCost, level + distCost[maxDist - distance]);
    for (Edge adj : graph[node])
        if ((adj.target ^ parent) && !nodeBlocked[adj.target])
            depthFirstSearch(adj.target, node, distance + adj.weight, level + 1);
}

void updateDistances(int node, int parent, int distance, int level) {
    if (distance <= maxDist) {
        updates.push_back(make_pair(distance, distCost[distance]));
        distCost[distance] = min(distCost[distance], level);
    }
    for (Edge adj : graph[node])
        if ((adj.target ^ parent) && !nodeBlocked[adj.target])
            updateDistances(adj.target, node, distance + adj.weight, level + 1);
}



void decompose(int node, int parent = -1) {
    std::function<int(int, int)> computeSubtreeSize = [&](int n, int p) {
        subtreeSize[n] = 1;
        for (const auto& adj : graph[n]) {
            if (adj.target != p && !nodeBlocked[adj.target])
                subtreeSize[n] += computeSubtreeSize(adj.target, n);
        }
        return subtreeSize[n];
    };

    computeSubtreeSize(node, parent);
    if (subtreeSize[node] == 1) return;

    int centroid = findCentroid(node, parent, subtreeSize[node] >> 1);

    for (Edge adj : graph[centroid]) {
        if (adj.target ^ parent) {
            depthFirstSearch(adj.target, centroid, adj.weight, 1);
            updateDistances(adj.target, centroid, adj.weight, 1);
        }
    }

    for (auto it = updates.rbegin(); it != updates.rend(); it++)
        distCost[it->first] = it->second;
    
    updates.clear();

    nodeBlocked[centroid] = true;
    for (Edge adj : graph[centroid])
        if (!nodeBlocked[adj.target])
            decompose(adj.target, centroid);
}

int calculateBestPath(int N, int K, int H[][2], int W[]) {
    if (N < 1) return -1;
    numNodes = N; maxDist = K;
    
    for (int i = 0; i < N - 1; i++) {
        graph[H[i][0]].push_back({H[i][1], W[i]});
        graph[H[i][1]].push_back({H[i][0], W[i]});
    }

    memset(distCost, 0x3f, sizeof distCost);
    distCost[0] = 0;
    decompose(0);

    return minCost >= 0x3f3f3f3f ? -1 : minCost;
}

int nodeConnections[MAX_NODES][2], weights[MAX_NODES];

void readGraph(int connections[][2], int weights[], int numEdges) {
    for (int i = 0; i < numEdges; i++) {
        cin >> connections[i][0] >> connections[i][1] >> weights[i];
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> numNodes >> maxDist;
    readGraph(nodeConnections, weights, numNodes - 1);

    cout << calculateBestPath(numNodes, maxDist, nodeConnections, weights) << endl;
    return 0;
}