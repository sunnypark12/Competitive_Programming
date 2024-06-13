#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_SIZE 501
#define MAX_DISTANCE 0x3f3f3f3f

typedef struct {
    int nodeX, nodeY;
    bool state;
} path;

int width, height, startX, startY, endX, endY;
int distances[MAX_SIZE][MAX_SIZE][2];
path* maze[MAX_SIZE][MAX_SIZE];
int count[MAX_SIZE][MAX_SIZE];

void addEdge(int fromX, int fromY, int toX, int toY, char type) {
    if (type == 'n') return;
    bool isWalkable = (type == 'w');

    path edge = {toX, toY, isWalkable};
    int index = count[fromX][fromY]++;
    maze[fromX][fromY] = realloc(maze[fromX][fromY], sizeof(path) * count[fromX][fromY]);
    maze[fromX][fromY][index] = edge;

    index = count[toX][toY]++;
    maze[toX][toY] = realloc(maze[toX][toY], sizeof(path) * count[toX][toY]);
    maze[toX][toY][index] = (path){fromX, fromY, isWalkable};
}

void initializeMaze() {
    for (int i = 0; i < MAX_SIZE; i++)
        for (int j = 0; j < MAX_SIZE; j++)
            maze[i][j] = NULL, count[i][j] = 0;
}

typedef struct {
    path* queue[MAX_SIZE * MAX_SIZE * 2];
    int head, tail;
} EdgeQueue;

void enqueue(EdgeQueue *q, path* e) {
    q->queue[q->tail++] = e;
}
path* dequeue(EdgeQueue *q) {
    return q->queue[q->head++];
}


bool isEmpty(EdgeQueue *q) {
    return q->head == q->tail;
}

int main() {
    scanf("%d %d %d %d %d %d", &width, &height, &startY, &startX, &endY, &endX);
    height++; width++;

    initializeMaze();

    char line[MAX_SIZE * 2];
    for (int i = 0; i < height-1; i++) {
        scanf("%s", line);
        for (int j = 0; j < width-1; j++)
            addEdge(i, j, i, j + 1, line[j]);

        scanf("%s", line);
        for (int j = 0; j < width; j++)
            addEdge(i, j, i + 1, j, line[2 * j]);
        for (int j = 1; j < width; j++)
            addEdge(i, j, i + 1, j - 1, line[2 * j - 1]);
    }
    scanf("%s", line);
    for (int i = 0; i < width-1; i++)
        addEdge(height-1, i, height-1, i + 1, line[i]);

    memset(distances, -1, sizeof(distances));
    EdgeQueue queue = { .head = 0, .tail = 0 };
    path e1 = {startX, startY, false}, e2 = {startX, startY, true};
    distances[startX][startY][0] = 0;
    distances[startX][startY][1] = 0;
    enqueue(&queue, &e1);
    enqueue(&queue, &e2);

    while (!isEmpty(&queue)) {
        path *curr = dequeue(&queue);
        int dist = distances[curr->nodeX][curr->nodeY][curr->state] + 1;
        for (int i = 0; i < count[curr->nodeX][curr->nodeY]; i++) {
            path next = maze[curr->nodeX][curr->nodeY][i];
            if (next.state == curr->state) continue;
            if (distances[next.nodeX][next.nodeY][next.state] == -1) {
                distances[next.nodeX][next.nodeY][next.state] = dist;
                path* newPathEdge = malloc(sizeof(path));
                *newPathEdge = next;
                enqueue(&queue, newPathEdge);
            }
        }
    }

    int shortestPath = MAX_DISTANCE;
    for (int i = 0; i < 2; i++)
        if (distances[endX][endY][i] != -1)
            if (distances[endX][endY][i] < shortestPath)
                shortestPath = distances[endX][endY][i];
    printf("%d\n", shortestPath);

    for (int i = 0; i < MAX_SIZE; i++) {
        for (int j = 0; j < MAX_SIZE; j++) {
            if (maze[i][j] != NULL)
                free(maze[i][j]);
        }
    }

    return 0;
}