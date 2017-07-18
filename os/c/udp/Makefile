TARGET_C = client
TARGET_S = server
LDIR = ../lib
CC = gcc
CFLAGS = -Wall -pthread -I$(LDIR)
OBJ_C =	client.o
OBJ_S = server.o $(LDIR)/hostinfo.o
DEPS = $(LDIR)/hostinfo.h

%.o: %.c $(DEPS)
		$(CC) $(CFLAGS) -c -o $@ $<

all: $(TARGET_C) $(TARGET_S)

$(TARGET_C):	$(OBJ_C)
				gcc $(CFLAGS) -o $@ $^

$(TARGET_S):	$(OBJ_S)
	gcc $(CFLAGS) -o $@ $^


clean: $(OBJ_C) $(OBJ_S)
			rm -f $^ $(TARGET_S) $(TARGET_C)
