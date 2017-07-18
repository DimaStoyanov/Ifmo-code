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

#define PORT 1245

char * get_hosts(char *address);

int main()
{
    struct sockaddr_in local, client;
    unsigned int client_len;
    // Set addr of server
    memset(&local, 0, sizeof(local));
    local.sin_family = AF_INET;
    local.sin_port = htons(PORT);
    local.sin_addr.s_addr = htonl(INADDR_ANY);
    // Create socket
    int sock = socket(AF_INET, SOCK_DGRAM, 0);
    if(sock < 0)
    {
        perror("Can't create socket");
        exit(1);
    }
    // Avoid bind error : addr is used
    int enabled = 1;
    setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, (const void *) &enabled, sizeof(int));
    // Bind server to specific addr
    if(bind(sock, (struct sockaddr *) &local, sizeof(local)) < 0)
    {
        perror("Bind error");
        exit(1);
    }

    client_len = sizeof(client);
    char * buf = (char *) malloc(BUFSIZ * sizeof(char));

    // Recieve msg from clients and send answer
    while(1)
    {
        memset(buf, 0, BUFSIZ);
        if(recvfrom(sock, buf, BUFSIZ, 0, (struct sockaddr *)&client, &client_len) < 0)
        {
            perror("Recvfrom error");
            exit(1);
        }

        // Determine client address
        char client_addr[BUFSIZ];
        inet_ntop(AF_INET, &(client.sin_addr), client_addr, BUFSIZ);
        printf("%s: %s", client_addr, buf);
        // Intro msg
        if(!strcmp(buf, "ping\n")) strcpy(buf, "Connected!\nType name of host and server give you info about it\nTo leave from server type \":q\\n\"\n");
        else
        {
            buf =  get_hosts(buf);
        }
        // Send answer to client
        if(sendto(sock, buf, BUFSIZ, MSG_NOSIGNAL, (struct sockaddr * ) &client, client_len) < 0)
        {
            perror("Sendto error");
            exit(1);
        }
        printf("Server: %s", buf);
    }

    close(sock);
    return 0;
}
