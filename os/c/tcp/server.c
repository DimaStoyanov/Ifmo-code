#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <pthread.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <errno.h>
#include <arpa/inet.h>
#include "hostinfo.h"

#define PORT 12614



void *recv_msg(void *args);
char * get_hosts(char *address);


struct client {
  int sock_desriptor;
  char * sock_addr;
};

int main()
{
    // Create socket
    int serv_socket = socket(AF_INET, SOCK_STREAM, 0);
    if(serv_socket < 0)
    {
        perror("Can't create socket");
        exit(1);
    }
    struct sockaddr_in local;

    // Set args
    memset(&local, 0, sizeof(local));
    local.sin_family = AF_INET;
    local.sin_port = htons(PORT);
    local.sin_addr.s_addr = INADDR_ANY;
    int enable = 1;
    // To avoid error: address is already use
    setsockopt(serv_socket, SOL_SOCKET, SO_REUSEADDR, &enable, sizeof(int));

    // Bind with specified address
    if(bind(serv_socket, (struct sockaddr *) &local, sizeof(local)))
    {
        perror("Bind error");
        exit(1);
    }

    // Register for listening connections
    listen(serv_socket, 5);

    // Processing new connections
    while(1)
    {
        struct sockaddr_in client;
        unsigned int len = sizeof(client);
        // Waiting for new connection
        int cl_socket = accept(serv_socket, (struct sockaddr *) &client, &len);

        if(cl_socket < 0)
        {
            perror("Can't get client connection");
            exit(1);
        }

        char  client_adress[BUFSIZ];
        // New client connected, start recieving msg from him in new thread
        inet_ntop(AF_INET, &(client.sin_addr), client_adress, BUFSIZ);
        printf("%s Connected\n", client_adress);
        send(cl_socket, "Connected!\nType name of host and server give you info about it\nTo leave from server type \":q\\n\"\n",
             100, MSG_NOSIGNAL /* don't generate sigpipe if server is closed */);

        pthread_t thread;
        struct client cl_info = {cl_socket, client_adress};
        if(pthread_create(&thread,NULL, recv_msg, (void *) &cl_info))
        {
            perror("Can't create thread");
            exit(1);
        }
    }
}


// Get msg from client and return hostinfo about service specified in msg
void *recv_msg(void *args)
{
    struct client * cl = (struct client *) args;

    int cl_socket = cl->sock_desriptor;
    char * addr = cl->sock_addr;
    char buf[BUFSIZ];
    while(1)
    {
        memset(buf, 0, BUFSIZ);
        read(cl_socket, buf, BUFSIZ);
        printf("%s: %s", addr, buf);
        if(!strncmp(buf, ":q\n", 3))
        {
            printf("%s: Disconnected from server\n", addr);
            send(cl_socket, "Disconnected from server\n", BUFSIZ, MSG_NOSIGNAL);
            break;
        }
        char * res = get_hosts(buf);
        printf("Server: %s", res);
        send(cl_socket, res, BUFSIZ, MSG_NOSIGNAL);
    }
    return NULL;
}
