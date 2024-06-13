#include<iostream>
#include<vector>
#include<algorithm>

#define systemio() ios_base::sync_with_stdio(0);cin.tie(0);
using namespace std;

int cost, edgeWeight;
long long int result;

int nodeFrom, nodeTo;
int nodeCount, edgeCount;
long long int maxDistances[100002], processedCount;

vector<pair<int, long long int>> adjacency[100002];
int visited[100002];

long long int high, low;
int visit_state, furthestNode;


long long int distances[100002];
void dfs(int curr, long long int dist, int depth) {
    if (depth) {
        distances[curr] = max(distances[curr], dist);
        if (low == -1) {
            low = distances[curr];
        } else {
            low = min(low, distances[curr]);
        }
        high = max(high, distances[curr]);
    } else {
        distances[curr] = dist;
    }
    visited[curr] = visit_state;
    for (size_t i = 0; i < adjacency[curr].size(); i++)
        if (visited[adjacency[curr][i].first] != visit_state)
            dfs(adjacency[curr][i].first, dist + adjacency[curr][i].second, depth);
}

void findFurthest(int curr, long long int dist) {
    if (dist > high) {
        furthestNode = curr;
        high = dist;
    }

    visited[curr] = visit_state;

    for (size_t i = 0; i < adjacency[curr].size(); i++)
        if (visited[adjacency[curr][i].first] != visit_state)
            findFurthest(adjacency[curr][i].first, dist + adjacency[curr][i].second);
}

long long int evaluateForest(int root) {
    int start, end;

    high = -1;
    visit_state++;
    furthestNode = 0;
    
    findFurthest(root, 0);
    start = furthestNode;
    
    high = -1;
    visit_state++;
    furthestNode = 0;

    findFurthest(start, 0);
    end = furthestNode;

    high = -1;
    low = -1;
    visit_state++;
    dfs(start, 0, false);

    visit_state++;
    dfs(end, 0, true);

    result = max(result, high);
    return low;
}

int main() {
    systemio();
    cin >> nodeCount >> edgeCount >> cost;
    for (int i = 1; i <= edgeCount; i++) {
        cin >> nodeFrom >> nodeTo >> edgeWeight;
        adjacency[nodeFrom].push_back(make_pair(nodeTo, edgeWeight));
        adjacency[nodeTo].push_back(make_pair(nodeFrom, edgeWeight));
    }

    for (int i = 0; i < nodeCount; i++)
        if (!visited[i])
            maxDistances[++processedCount] = evaluateForest(i);
    sort(maxDistances + 1, maxDistances + processedCount + 1);
    reverse(maxDistances + 1, maxDistances + processedCount + 1);

    if (processedCount > 1) {
        result = max(result, maxDistances[1] + maxDistances[2] + cost);
        if (processedCount > 2)
            result = max(result, maxDistances[2] + maxDistances[3] + 2 * cost);
    }
    cout << result << "\n";
    return 0;
}