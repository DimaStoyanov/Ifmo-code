#include "forest.h"
#include <errno.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <stdio.h>

int fd[2];
forest f;

int run();
void dfs_process(int v);



int run()
{
    // Init pipe and count of vertices
    pipe(fd);
    int count = 0;
    write(fd[1], &count, sizeof(int));

    int i;
    int_vector roots = f.get_roots(&f);

    // Current process just run new process for every roots
    for(i = 0; i < roots.size; i++)
    {
        printf("Start traversing tree from root %d\n", roots.values[i]);

        int pid = fork();
        if(pid < 0)
        {
            perror("Fork error");
            exit(1);
        } else if(!pid)
        {
            // Child process will terminate in the end of dfs
            dfs_process(roots.values[i]);
        }

    }

    // Waiting for all roots processes
    int pid, status;
    while((pid = wait(&status)))
    {
        if(errno == ECHILD || (pid == -1 && errno == ECHILD)) break;
    }

    // And return count of vertices
    read(fd[0], &count, sizeof(int));
    return count;
}


// Processing tree(or subtree) with root v
void dfs_process(int v)
{
    printf("Start process %d in vertex %d\n", getpid(), v);

    int i;
    for(i = 0; i < f.edges[v].size; i++)
    {
            int son = f.edges[v].values[i];
            // Start new processes for son
            int pid = fork();

            if(pid < 0)
            {
                perror("Fork error");
                exit(1);
            } else if(pid == 0)
            {
                // In case of child continue dfs
                dfs_process(son);
            }

            //In parent case continue to start new processes for child
    }

    int status, pid;

    // If vertex has sons, waiting their termination
    if(f.edges[v].size)
    {
        while((pid = wait(&status)))
        {
            if(status == ECHILD || (pid == -1 && errno == ECHILD)) break;
        }
    }

    // Increment count of vertices
    int count;
    read(fd[0], &count, sizeof(int));
    count++;
    write(fd[1], &count, sizeof(int));

    // Work done, leaving
    // printf("Exit process %d\n", v);
    exit(0);
}



int main(int argc, char *argv[])
{

    int size;
    if(argc < 2)
    {
        printf("You should specify size of forest\n");
        scanf("%d", &size);
        if(size > 10000) { printf("Size too large, setting size 10"); size = 10; }
    } else size = atoi(argv[1]);

    f = generate(size);
    printf("%s", f.forest_toString(&f));

    printf("Vertices count %d\n", run());
    exit(0);
    return 0;
}
