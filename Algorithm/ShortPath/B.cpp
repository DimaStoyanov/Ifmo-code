#include <fstream>
#include <vector>
#include <set>
#include <iostream>
 
using namespace::std;
 
const long long NOT_EDGE = -1;
const long long INF = INT64_MAX;
 
int main() {
    FILE *in, *out;
    in = fopen("pathmgep.in", "r");
    out = fopen("pathmgep.out", "w");
    int n, s, f;
    fscanf(in, "%i %i %i", &n, &s, &f);
    s--, f--;
    vector < vector < pair<int, long long> > > g(n);
 
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            long long w;
            fscanf(in, "%I64i", &w);
            if (w != NOT_EDGE && i != j)
                g[i].push_back(make_pair(j, w));
        }
    }
 
    vector<long long> d(n, INF);
    vector<bool> used(n, false);
    d[s] = 0L;
    for (int i = 0; i < n; i++) {
        int v = -1;
        long long dv = INF;
        for (int j = 0; j < n; j++) {
            if (!used[j] && dv >= d[j]) {
                v = j;
                dv = d[j];
            }
        }
        int l = g[v].size();
        for (int j = 0; j < l; j++) {
            int u = g[v][j].first;
            long long wu = g[v][j].second;
            if (d[v] + wu < d[u]) {
                d[u] = d[v] + wu;
            }
        }
        used[v] = true;
    }
    fprintf(out, "%I64i", (d[f] == INF ? -1 : d[f]));
    fclose(out);
}