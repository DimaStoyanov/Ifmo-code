#include <fstream>
#include <vector>
#include <iostream> 
#include <set>
using namespace::std;
const long long INF = INT64_MAX;
 
 
int main() {
    ifstream in("pathbgep.in");
    ofstream out("pathbgep.out");
        int n, m;
        in >> n >> m;
        vector < vector < pair<int, long long> > > g(n);
     
        for (int i = 0; i < m; i++) {
            int from, to;
            long long w;
            in >> to >> from >> w;
            from--, to--;
            g[from].push_back(make_pair(to, w));
            g[to].push_back(make_pair(from, w));
        }
 
        vector<long long> d(n, INF);
    d[0] = 0;
    set < pair<long long, int> > q;
    q.insert(make_pair(d[0], 0));
    while (!q.empty()) {
        int v = q.begin()->second;
        q.erase(q.begin());
 
        for (size_t j = 0; j<g[v].size(); ++j) {
            int to = g[v][j].first;
                long long len = g[v][j].second;
            if (d[v] + len < d[to]) {
                q.erase(make_pair(d[to], to));
                d[to] = d[v] + len;
                q.insert(make_pair(d[to], to));
            }
        }
    }
    for (int i = 0; i < n; i++) {
        out << d[i] << " ";
    }
    out.close();
}