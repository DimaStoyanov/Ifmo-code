#include "intvector.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>




pno append(int_vector * vector, int value)
{
    while(vector->size + 1 >= vector->capacity)
    {
        vector->capacity <<= 1;
        vector->values = (int *) realloc(vector->values, vector->capacity * sizeof(int));
    }

    vector->values[vector->size++] = value;
    return vector;
}




int contains(int_vector * vector, int value)
{
    int i;
    for(i = 0; i < vector->size; i++)
    {
        if(vector->values[i] == value) return i;
    }
    return -1;
}

pno remove_at(int_vector * vector, int index)
{
    if(index < 0 || index > vector->size - 1) { printf("Incorrect index\n"); exit(1); }

    int * temp = (int *) malloc(vector->capacity * sizeof(int));

    int i, j = 0;
    for(i = 0; i < vector->size; i++)
    {
        if(i == index) continue;
        temp[j++] = vector->values[i];
    }
    vector->size--;
    free(vector->values);
    vector->values = temp;

    return vector;
}

pno remove_value(int_vector * vector, int value)
{
    int index;
    while((index = contains(vector, value)) != -1)
    {
        vector = remove_at(vector, index);
    }
    return vector;
}



int get_size(int_vector * vector)
{
    return vector->size;
}



pno clear(int_vector * vector)
{
    free(vector->values);
    vector->size = 0;
    vector->capacity = 1;
    return vector;
}



char * toString(int_vector * vector)
{
    char * res = (char *) malloc(vector->size * 16 * sizeof(char));
    res[0] = 0;
    int i;

    for(i = 0; i < vector->size; i++)
    {
        printf("adding char %d\n", i);
        char el[16];
        sprintf(el, "%d ", vector->values[i]);
        strcat(res, el);
    }
    strcat(res, "\n");
    return res;
}





int_vector init()
{
    int_vector *vec = (int_vector *) malloc(sizeof(int_vector));
    // Init vars
    vec->size = 0;
    vec->capacity = 128;
    vec->values = (int *) malloc(vec->capacity * sizeof(int));

    // Init methods
    vec->append = append;
    vec->contains = contains;
    vec->remove_value = remove_value;
    vec->get_size = get_size;
    vec->clear = clear;
    vec->toString = toString;
    return *vec;
}
