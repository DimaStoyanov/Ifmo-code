#ifndef FOREST_H
#define FOREST_H

#include "intvector.h"

typedef struct forest forest, * pf;

struct forest
{
    // List of vertex edges
    int_vector * edges;

    // Array of nodes parents. -1 if doesn't have parent
    int * parent;

    // Count of vertex
    int size;

    // Check if new edge from->to violate forest structure
    // (multiple parent, cycle)
    // If new edge violate structure return 0, otherwise 1
    int (*check_edge)(pf self, int from, int to);

    // Add new edge from->to in the forest
    pf (*add_edge)(pf self, int from, int to);

    // Get all not isolated roots (roots without edges are ignored)
    int_vector (*get_ni_roots)(pf self);

    // Get all roots of forest(including roots without edges)
    int_vector (*get_roots)(pf self);

    // Free mem
    pf (*clear_forest)(pf self);

    // String representation of forest
    char * (*forest_toString)(pf self);

};

// Create forest with specified count of vertices
forest create(int size);

// Generate random forest with specified count of vertices
// and (random() % size + 1) edges
forest generate(int size);

#endif // FOREST_H
