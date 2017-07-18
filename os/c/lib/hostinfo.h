#ifndef HOSTINFO_H
#define HOSTINFO_H


/**
* Returns info about host with specified address in format
* IPv4/IPv6/unknown
* IP adress list, every adress in new line
* If specified adress doesn't exist return error msg
**/
char * get_hosts(char * address);

#endif // HOSTINFO_H
