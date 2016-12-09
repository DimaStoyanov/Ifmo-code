#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>
#define FILE_NAME "multiassignment"
 
using namespace std;
 
 
typedef pair<int, int> pii;
 
 
 
 
struct Queue {
    static const int cap = 602;
    int q[cap], head, tail, size;
 
    inline void push(int a) {
        q[head++] = a;
        size++;
        if (head == cap) head = 0;
    }
 
    inline int pop() {
        int res = q[tail++];
        size--;
        if (tail == cap) tail = 0;
        return res;
    }
 
    inline bool empty() {
        return size == 0;
    }
 
    inline void clear() {
        head = tail = size = 0;
    }
};
 
struct edge {
    int from, to, cap, flow, cost;
};
 
ifstream fin("multiassignment.in");
ofstream fout("multiassignment.out");
const int MAXN = 102;
const int MAXM = 2600;
edge e[MAXM * 2];
int G[MAXN][MAXN], match[MAXN], f[MAXN], iter;
vector<int> g[MAXN];
int total, s, t, INF = 1e9, n, m, k;
int phi[MAXN], in[MAXN];
Queue Q;
long long minCost;
int d[MAXN], p[MAXN], maxFlow, used[MAXN];
 
void add_edge(int from, int to, int cap, int cost) {
    e[total] = { from, to, cap, 0, cost };
    g[from].push_back(total++);
    e[total] = { to, from, 0, 0, -cost };
    g[to].push_back(total++);
}
 
 
void ford_bellman() {
    fill(phi, phi + 2 * n + 2, INF);
    Q.push(s), in[s] = 1, phi[s] = 0;
    while (!Q.empty()) {
        int v = Q.pop();
        in[v] = 0;
        for (int id : g[v]) {
            int to = e[id].to;
            if (e[id].flow < e[id].cap && phi[to] > phi[v] + e[id].cost) {
                phi[to] = phi[v] + e[id].cost;
                if (!in[to]) {
                    Q.push(to);
                    in[to] = 1;
                }
            }
        }
    }
}
 
 
 
 
bool djikstra() {
    fill(d, d + 2 + 2 * n, INF);
    d[s] = 0;
    memset(used, 0, sizeof(int) * (2 + 2 * n));
    for (int i = 0; i < 2 + 2 * n; i++) {
        int v = -1;
        for (int j = 0; j < 2 + 2 * n; j++)
            if (!used[j] && (v == -1 || d[v] > d[j]))
                v = j;
        if (v == -1)
            break;
        used[v] = 1;
        for (int id : g[v]) {
            int to = e[id].to;
            if (e[id].cap > e[id].flow && d[to] > d[v] + e[id].cost + phi[v] - phi[to]) {
                d[to] = d[v] + e[id].cost + phi[v] - phi[to];
                p[to] = id;
            }
        }
    }
    if (d[t] == INF)
        return 0;
    int flow = INF, len = d[t] - phi[s] + phi[t];
    for (int v = t; v != s; ) {
        int id = p[v];
        flow = min(flow, e[id].cap - e[id].flow);
        v = e[id].from;
    }
    for (int v = t; v != s; ) {
        int id = p[v];
        e[id].flow += flow;
        e[id ^ 1].flow -= flow;
        v = e[id].from;
    }
    maxFlow += flow;
    minCost += (long long)len * flow;
    for (int i = 0; i < 2 + 2 * n; i++)
        if (d[i] < INF)
            phi[i] += d[i];
    return 1;
}
 
void min_cost_max_flow() {
    ford_bellman();
    while (djikstra());
}
 
 
int dfs(int v) {
    used[v] = iter;
    for (int to = 0; to < n; to++)
        if (G[v][to] && (match[to] == -1 || (used[match[to]] != iter && dfs(match[to])))) {
            match[to] = v, f[v] = to;
            return 1;
        }
    return 0;
}
 
void solve() {
    fin >> n >> k;
    s = 0, t = n * 2 + 1;
    for (int i = 0; i < n; i++)
        add_edge(s, 1 + i, k, 0);
    int c;
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++) {
            fin >> c;
            add_edge(1 + i, 1 + n + j, 1, c);
        }
    for (int i = 0; i < n; i++)
        add_edge(1 + n + i, t, k, 0);
    min_cost_max_flow();
    fout << minCost << endl;
    for (int v = 1; v <= n; v++)
        for (int id : g[v])
            if (e[id].flow == 1)
                G[v - 1][e[id].to - n - 1] = 1;
    for (int i = 0; i < k; i++) {
        fill(match, match + n, -1);
        fill(f, f + n, -1);
        for (int run = 1; run; ) {
            run = 0, iter++;
            for (int v = 0; v < n; v++)
                if (f[v] == -1 && dfs(v))
                    run = 1;
        }
        for (int i = 0; i < n; i++) {
            fout << f[i] + 1 << " ", G[i][f[i]] = 0;
        }
        fout << endl;
    }
}
 
int main() {
    solve();
    return 0;
}