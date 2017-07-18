#include "forest.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>


// Check path from v to u, if path exist return 0
int check_path(pf forest, int u, int v)
{

    while(forest->parent[u] != -1)
       {
           u = forest->parent[u];
           if(u == v) return 0;
       }
    return 1;
}

int check_edge(pf forest, int from, int to)
{
    return forest->parent[to] == -1 && from != to && check_path(forest, from, to);
}


pf add_edge(pf forest, int from, int to)
{
    printf("Add edge %d->%d\n", from, to);
    forest->edges[from].append(&forest->edges[from], to);
    forest->parent[to] = from;
    return forest;
}




void dfs(pf forest, int v, int p, int * used, pno roots)
{
    used[v] = 1;
    int i;
    for(i = 0; i < forest->edges[v].size; i++)
    {
            int son = forest->edges[v].values[i];
            if(son == p) printf("Tree has cycle\n");
            if(!used[son]) dfs(forest, son, v, used, roots);
            else roots->remove_value(roots, son);
    }
}


// mode  = 1 -> include isolated roots, otherwise ignore
int_vector get_custom_roots(pf forest, int mode)
{
    int_vector roots = init();
    int * used = (int *) malloc(forest->size * sizeof(int));
    memset(used, 0, forest->size * sizeof(int));
    int i;
    for(i = 0; i < forest->size; i++)
    {
        if(!used[i] && (mode || forest->edges[i].size))
        {
            roots.append(&roots, i);
            dfs(forest, i, i, used, &roots);
        }
    }
    free(used);
    return roots;
}


int_vector get_ni_roots(pf forest)
{
    return get_custom_roots(forest, 0);
}

int_vector get_roots(pf forest)
{
    return get_custom_roots(forest, 1);
}

pf clear_forest(pf forest)
{
    int i;
    for(i = 0; i <forest->size; i++)
    {
        forest->edges[i].clear(&forest->edges[i]);
    }
    free(forest->edges);
    forest->size = 0;
    free(forest->parent);
    return forest;
}





void print_tree(pf forest, int v, char * buf);

char * forest_toString(pf forest)
{
    char * buf = (char *) malloc(forest->size * 1024 * sizeof(char));
    buf[0] = 0;
    strcat(buf, "Forest representation\n");


    int_vector roots = get_roots(forest);
    int i;
    for(i = 0; i < roots.size; i++)
    {
        char el[1024];
        sprintf(el, "Root %d:\n", roots.values[i]);
        strcat(buf, el);
        print_tree(forest, roots.values[i], buf);
    }
    return buf;
}


void print_tree(pf forest, int v, char * buf)
{
    if(!forest->edges[v].size) return;
    int i;
    for(i = 0; i < forest->edges[v].size; i++)
    {
        char el[32];
        sprintf(el, "%d->%d\t", v, forest->edges[v].values[i]);
        strcat(buf, el);
    }
    strcat(buf, "\n");

    for(i = 0; i < forest->edges[v].size; i++)
    {
        print_tree(forest, forest->edges[v].values[i], buf);
    }
}




forest create(int size)
{
    forest f;

    f.parent = (int *) malloc(size * sizeof(int));
    memset(f.parent, -1, size * sizeof(int));
    f.size = size;


    f.edges = (int_vector *) malloc(size * sizeof(int_vector));
    int i;
    for(i = 0; i < size; i++)
    {
        f.edges[i] = init();
    }
    f.add_edge = add_edge;
    f.check_edge = check_edge;
    f.clear_forest= clear_forest;
    f.get_ni_roots = get_ni_roots;
    f.get_roots = get_roots;
    f.forest_toString = forest_toString;


    return f;
}





forest generate(int size)
{
    forest f = create(size);

    time_t t;
    srand((unsigned) time(&t));
    int edges_count = rand() % (size - 1) + 1;
    int i;
    for(i = 0; i < edges_count; i++)
    {
        while(1)
        {
            int from = rand() % size;
            int to = rand() % size;
            if(check_edge(&f, from, to))
            {
                add_edge(&f, from, to);
                break;
            }
        }
    }

    return f;
}
