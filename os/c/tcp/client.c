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


#define PORT 12614



int main(int argc, char *argv[])
{
  if(argc != 2)
  {
      printf("Ussage: %s <Server IP> (127.0.0.1 for localhost)\n", argv[0]);
      exit(1);
  }

  // Create socket
  int sock = socket(AF_INET, SOCK_STREAM, 0);
  if(sock < 0)
  {
      perror("Can't create socket");
      exit(1);
  }

  // Set address
  struct sockaddr_in addr;
  memset(&addr, 0, sizeof(addr));
  addr.sin_family = AF_INET;
  addr.sin_port = htons(PORT);
  addr.sin_addr.s_addr = inet_addr(argv[1]);

  // Connect to server
  if(connect(sock, (struct sockaddr *) &addr, sizeof(addr)) < 0)
  {
      perror("Can't connect to server");
      exit(1);
  }

  // In main thread send msg from stdin to server
  char buf[BUFSIZ];
  while(1)
  {
      memset(buf, 0, BUFSIZ);
      // Waiting for answer from server
      read(sock, buf, BUFSIZ);
      printf("Server: %s", buf);
      if(!strncmp(buf, "Disconnected from server\n", 13)) break;
      // Read from stdin and send to server
      memset(buf, 0, BUFSIZ);
      read(STDIN_FILENO, buf, BUFSIZ);
      send(sock, buf, BUFSIZ, MSG_NOSIGNAL);
  }


  if(close(sock) < 0)
  {
      perror("Can't close socket");
  }

}
