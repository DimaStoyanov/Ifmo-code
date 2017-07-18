#include "hostinfo.h"
#include <string.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <netdb.h>
#include <stdio.h>
#include <unistd.h>

char * get_hosts(char * address)
{
    // Remove '\n' symbol
    address[strlen(address) - 1] = 0;

    int sock;
    struct addrinfo hints, *res;
    int err;
    char * result = (char *) malloc(BUFSIZ * sizeof(char));
    result[0] = 0;

    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_UNSPEC;
    hints.ai_socktype = SOCK_DGRAM;
    err = getaddrinfo(address, "1234", &hints, &res);

    // If hostname doesn't registered, return error info
    if(err != 0) return strcat(strcat(result, gai_strerror(err)), "\n");

    sock = socket(res->ai_family,res->ai_socktype,0);
       if(sock < 0)
       {
           perror("socket");
           return strcat(result, "socket error\n");
       }

       // Add sin_family info
       const char *ipverstr;
       switch (res->ai_family)
       {
           case AF_INET:
               ipverstr = "IPv4";
               break;
            case AF_INET6:
                ipverstr = "IPv6";
                break;
            default:
                ipverstr = "unknown";
                break;
       }
       strcat(strcat(result, ipverstr), "\n");

      // Add ip
      while(res->ai_next != NULL)
      {
          struct sockaddr_in *addr = (struct sockaddr_in *) res->ai_addr;
          strcat(strcat(result, inet_ntoa((struct in_addr) addr->sin_addr)), "\n");
          res = res->ai_next;
      }

      close(sock);
      freeaddrinfo(res);
      return result;
}
