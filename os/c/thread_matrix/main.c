#include <pthread.h>
#include <time.h>
#include <signal.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

#define ACCESS_RDWR 0666

#define STAT_GEN   1
#define STAT_EVAL  2
#define STAT_PRINT 3

// Boundaries of the size of the generated matrix
int min_matrix_cols;
int min_matrix_rows;
int max_matrix_cols;
int max_matrix_rows;

// Matrices
int * A1, * A2, * A3;

// Current size of matrix
int * size;

int status;

// File output descriptor
int fod = 0;

pthread_mutex_t lock;


void signal_handler(int signalno)
{
    switch(signalno)
    {
        case SIGINT :
            if(fod && close(fod) < 0)
            {
                fod = 0;
                perror("Can't close output file");
            }

            exit(0);
    }
}

void *generator(void * args)
{
    time_t t;
    srand((unsigned) time(&t));
    status = STAT_GEN;

    while(1)
    {
        pthread_mutex_lock(&lock);
        if(status != STAT_GEN)
        {
            pthread_mutex_unlock(&lock);
            sleep(2);
            continue;
        }


        size[0] = rand() % (max_matrix_rows - min_matrix_rows) + min_matrix_rows;
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
        status = STAT_EVAL;
        pthread_mutex_unlock(&lock);
    }
}

void *evaluator(void * args)
{
    while(1)
    {
        pthread_mutex_lock(&lock);
        if(status != STAT_EVAL)
        {
            pthread_mutex_unlock(&lock);
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
        status = STAT_PRINT;
        pthread_mutex_unlock(&lock);
    }
}

void matrix_toString(char * buf);
void append_row(char * buf, int * A, int * size, int i);

void *printer(void * args)
{
    fod = open("matrix_sum.out", O_CREAT | O_TRUNC | O_WRONLY, ACCESS_RDWR);
    if(fod < 0)
    {
        perror("Can't create output file");
        exit(1);
    }

    while(1)
    {
        pthread_mutex_lock(&lock);
        if(status != STAT_PRINT)
        {
            pthread_mutex_unlock(&lock);
            sleep(2);
            continue;
        }
        printf("Printnig\n");
        // Write resulting matrix in file
        // If size of matrix is low enough also print in stdout
        char * res = (char *) malloc(size[0] * size[1] * 16 * sizeof(char));
        res[0] = 0;
        matrix_toString(res);
        write(fod, res, strlen(res));
        if(size[0] <= 10 && size[1] <= 7) printf("%s", res);
        free(res);

        status = STAT_GEN;
        pthread_mutex_unlock(&lock);
    }
}


void matrix_toString(char * res)
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

    if(min_matrix_cols * min_matrix_rows > 10000000 || max_matrix_cols * max_matrix_rows > 10000000)
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

    A1 = (int *) malloc(max_matrix_cols * max_matrix_rows * sizeof(int));
    A2 = (int *) malloc(max_matrix_cols * max_matrix_rows * sizeof(int));
    A3 = (int *) malloc(max_matrix_cols * max_matrix_rows * sizeof(int));
    size = (int *) malloc(2 * sizeof(int));

    signal(SIGINT, signal_handler);
    pthread_mutex_init(&lock, NULL);

    pthread_t * threads = (pthread_t *) malloc(3 * sizeof(pthread_t));
    if(pthread_create(&threads[0],NULL,generator, NULL) ||
            pthread_create(&threads[1], NULL, evaluator, NULL) ||
            pthread_create(&threads[2], NULL, printer, NULL))
    {
        perror("Can't create thread");
        exit(1);
    }


    int i;
    for(i = 0; i < 3; i++)
    {
        pthread_join(threads[i], NULL);
    }

    return 0;
}
