
#include "intvector.h"
#include <stdio.h>
#include "forest.h"
#include <pthread.h>
#include <stdlib.h>


forest f;

pthread_mutex_t lock;

int count;



void *worker(void *args)
{
    // Block acess to variable from other threads, while we changing value
    pthread_mutex_lock(&lock);
    count++;
    pthread_mutex_unlock(&lock);

    int v = ((int*) args)[0];
    printf("Start thread %ld in vertex %d\n", pthread_self(), v);

    pthread_t *threads = (pthread_t *) malloc(f.edges[v].size * sizeof(pthread_t));
    int i, * child;
    for(i = 0; i < f.edges[v].size; i++)
    {
        child = (int *) malloc(sizeof(int));
        *child = f.edges[v].values[i];
        if(pthread_create(&threads[i], NULL, worker, (void *) child))
        {
            perror("Can't create thread");
            exit(1);
        }
    }

    for(i = 0; i < f.edges[v].size; i++)
    {
        if(pthread_join(threads[i], NULL))
        {
            perror("Join error");
        }
    }

    free(threads);
    free(args);
    // printf("Leaving process %d\n", v);
    return 0;
}

int main()
{


    printf("Specify size of forest: ");
    int size;
    scanf("%d", &size);
    if(size < 0) { printf("Incorrect size\n"); exit(1); }

    f = generate(size);
    printf("%s", f.forest_toString(&f));

    if(pthread_mutex_init(&lock, NULL))
    {
        perror("Can't init mutex");
        exit(1);
    }
    int_vector roots = f.get_roots(&f);
    pthread_t * threads = (pthread_t *) malloc(roots.size * sizeof(pthread_t));
    int i, * v;
    for(i = 0; i < roots.size; i++)
    {
        v = (int *) malloc(sizeof(int));
        *v = roots.values[i];
        if(pthread_create(&threads[i], NULL, worker, (void *) v))
        {
            perror("Can' create thread");
            exit(1);
        }
    }

    for(i = 0; i < roots.size; i++)
    {
      pthread_join(threads[i], NULL);

    }

    printf("Forest has %d vertices\n", count);

    free(threads);
    pthread_mutex_destroy(&lock);
    return 0;
}
