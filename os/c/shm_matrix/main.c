
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <time.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>

#define ACCESS_RDWR 0666

// Just random constants
#define SHM__SZ_KEY 136232112
#define SHM_STAT_KEY 46111252
#define SHM_A1_KEY 12913203
#define SHM_A2_KEY 72362326
#define SHM_A3_KEY 12945702

#define STAT_GEN 1
#define STAT_EVAL 2
#define STAT_PRINT 3

// Boundaries of the size of the generated matrix
int min_matrix_cols;
int min_matrix_rows;
int max_matrix_cols;
int max_matrix_rows;

// Ids of shared memory
int ids[5];

// Output file descriptor
int fod = 0;

void signal_handler(int signalno)
{
    switch(signalno)
    {
        case SIGINT:
           printf("Leaving\n");
           if(fod) close(fod);
           int i;
           for(i = 0; i < 5; i++)
           {
                 shmctl(ids[i], IPC_RMID, NULL);
           }
           exit(0);
    }
}

void create_mem()
{
    ids[0] = shmget(SHM_STAT_KEY, 1 * sizeof(int), IPC_CREAT | ACCESS_RDWR);
    ids[1]  = shmget(SHM__SZ_KEY, 2 * sizeof(int), IPC_CREAT | ACCESS_RDWR);
    ids[2]  = shmget(SHM_A1_KEY, max_matrix_cols * max_matrix_rows * sizeof(int), IPC_CREAT | ACCESS_RDWR);
    ids[3]  = shmget(SHM_A2_KEY, max_matrix_cols * max_matrix_rows * sizeof(int), IPC_CREAT | ACCESS_RDWR);
    ids[4]  = shmget(SHM_A3_KEY, max_matrix_cols * max_matrix_rows * sizeof(int), IPC_CREAT | ACCESS_RDWR);


    if(ids[0] < 0 || ids[1] < 0 || ids[2] < 0 || ids[3] < 0 || ids[4] < 0)
    {
        perror("Can't create shared memory");
        exit(1);
    }

    signal(SIGINT, signal_handler);

}

void get_mem(int ** status, int ** size, int ** A1, int ** A2, int ** A3)
{
    int stat_id = shmget(SHM_STAT_KEY, 1 * sizeof(int), ACCESS_RDWR);
    int sz_id  = shmget(SHM__SZ_KEY, 2 * sizeof(int), ACCESS_RDWR);
    int A1_id  = shmget(SHM_A1_KEY, max_matrix_cols * max_matrix_rows * sizeof(int), ACCESS_RDWR);
    int A2_id  = shmget(SHM_A2_KEY, max_matrix_cols * max_matrix_rows * sizeof(int), ACCESS_RDWR);
    int A3_id  = shmget(SHM_A3_KEY, max_matrix_cols * max_matrix_rows * sizeof(int), ACCESS_RDWR);

    if(stat_id < 0 || sz_id < 0 || A1_id < 0 || A2_id < 0 || A3_id < 0)
    {
        perror("Can't get acess to shared memory");
        exit(1);
    }

    * status = (int *) shmat(stat_id, (void *) 0, 0);
    * size   = (int *) shmat(sz_id, (void *) 0, 0);
    * A1     = (int *) shmat(A1_id, (void *) 0, 0);
    * A2     = (int *) shmat(A2_id, (void *) 0, 0);
    * A3     = (int *) shmat(A3_id, (void *) 0, 0);
}


void generator()
{
    int * status, * size, * A1, * A2, * A3;
    get_mem(&status, &size, &A1, &A2, &A3);
    *status = STAT_GEN;

    time_t t;
    srand((unsigned) time(&t));

    while(1)
    {
        // Waiting
        if(*status != STAT_GEN)
        {
            sleep(2);
            continue;
        }

        size[0] = rand() % (max_matrix_rows - min_matrix_rows ) + min_matrix_rows;
        size[1] = rand() % (max_matrix_cols - min_matrix_cols) + min_matrix_cols;
        printf("Generating matrix %dx%d\n", size[0], size[1]);




        int i,j;
        for(i = 0; i < size[0]; i++)
        {
          for(j = 0; j < size[1]; j++)
          {
            A1[i * size[0] + j] = rand() % 101;
            A2[i * size[0] + j] = rand() % 101;
          }

        }

        *status = STAT_EVAL;
    }

}

void evaluator()
{
    int * status, * size, * A1, * A2, * A3;
    get_mem(&status, &size, &A1, &A2, &A3);

    while(1)
    {
        // Waiting
        if(*status != STAT_EVAL)
        {
            sleep(2);
            continue;
        }
        printf("Evaluating\n");
        int i, j;
        for(i = 0; i < size[0]; i++)
        {
          for(j = 0; j < size[1]; j++)
          {
            A3[i * size[0] + j] = A1[i * size[0] + j] + A2[i * size[0] + j];
          }
        }

        *status = STAT_PRINT;
    }
}


void append_row(char * buf, int * A, int *size, int i)
{
    int j;
        for(j = 0; j < size[1]; j++)
    {
       char * el = (char *) malloc(4 * sizeof(char));
       sprintf(el, "%3d ", A[i * size[0] + j]);
       strcat(buf,el);
       free(el);
    }

}

void matrix_toString(char * res, int * size, int * A1, int * A2, int * A3)
{
    int i;
    for(i = 0; i < size[0]; i++)
    {
        strcat(res, "|");
        append_row(res, A1, size, i);
        strcat(res, "|");
        if(i == size[0] >> 1)
        {
            strcat(res, "+");
        } else strcat(res, " ");
        strcat(res, "|");
        append_row(res, A2, size, i);
        strcat(res, "|");
        if(i == size[0] >> 1)
        {
         strcat(res, "=");
        } else strcat(res, " ");
        strcat(res, "|");
        append_row(res, A3, size, i);
        strcat(res, "|\n");
    }
    strcat(res, "\n");
}

void printer()
{
    int *status, *size, * A1, * A2, * A3;
    get_mem(&status, &size, &A1, &A2, &A3);

    fod = open("matrix_sum.out", O_WRONLY | O_CREAT | O_TRUNC, ACCESS_RDWR);
    if(fod < 0)
    {
        perror("Can't create output file");
        exit(1);
    }

    while(1)
    {
        // Waiting
        if(*status != STAT_PRINT)
        {
            sleep(2);
            continue;
        }
      printf("Printing\n");

      // Write resulting matrix in file
      // If size of matrix is low enough also print in stdout
      char * res = (char *) malloc(size[0] * size[1] * 16 * sizeof(char));
      res[0] = 0;
      matrix_toString(res, size, A1, A2, A3);
      write(fod, res, strlen(res));
      if(size[0] <= 10 && size[1] <= 7) printf("%s", res);
      free(res);

      *status = STAT_GEN;
    }
}





int main()
{

    printf("Specify boundaries of the size of the generated matrix\n");
    printf("Minimum rows: ");
    scanf("%d", &min_matrix_rows);
    printf("Maximum rows: ");
    scanf("%d", &max_matrix_rows);
    printf("Minimum columns: ");
    scanf("%d", &min_matrix_cols);
    printf("Maximum rows: ");
    scanf("%d", &max_matrix_cols);

    min_matrix_cols++; min_matrix_rows++; max_matrix_rows++; max_matrix_cols++;

    if(min_matrix_cols * min_matrix_rows > 1000000 || max_matrix_cols * max_matrix_rows > 1000000)
    {
        printf("Too big matrix\n");
        exit(1);
    }

    if(min_matrix_cols > max_matrix_cols || min_matrix_rows > max_matrix_rows)
    {
        printf("Incorrect boundaries\n");
        exit(1);
    }

    if(min_matrix_cols == max_matrix_cols) min_matrix_cols = 0;
    if(min_matrix_rows == max_matrix_rows) min_matrix_rows = 0;



    create_mem();
    int pid1 = fork();

    if(pid1 < 0) { perror("Generator fork error"); exit(1); }
    if(!pid1) { generator(); exit(0); }

    int pid2 = fork();

    if(pid2 < 0) { perror("Evaluator fork error"); exit(1); }
    if(!pid2) { evaluator(); exit(0); }

    int pid3 = fork();

    if(pid3 < 0) { perror("Printer fork error"); exit(1); }
    if(!pid3) { printer(); exit(0); }

    while(1) { }

    return 0;
}
