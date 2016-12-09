#include <stdio.h>
#include <malloc.h>

int main(int argc, char **argv) {
    int i, j, n;
    scanf("%d", &n);
    int **a = (int **) malloc((n + 1) * sizeof(int *));
    for (i = 1; i <= n; i++) 
    {
        a[i] = (int *) malloc((n + 1) * sizeof(int));
        for (j = 1; j <= n; j++) 
        {
            a[i][j] = i * j;
        }
    }
    int x1, y1, x2, y2;
    int maxNumber, spaces;
    scanf("%d", &x1);
    while (x1) 
    {
        scanf("%d%d%d", &y1, &x2, &y2);
        maxNumber = x2 * y2;
        spaces = 0;
        while (maxNumber > 0) 
        {
            spaces++;
            maxNumber /= 10;
        }
        for (i = y1; i <= y2; i++) 
        {
            for (j = x1; j <= x2; j++) 
            {
                printf("%*d ", spaces, a[i][j]);
            }
            printf("\n");
        }
        scanf("%d", &x1);
    }
    for (i = 1; i <= n; i++)
    {
        free(a[i]);
    }
    free(a);
    return 0;
}
