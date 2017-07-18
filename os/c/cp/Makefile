TARGET = main
CC = gcc
CFLAGS = -Wall
OBJ =	cp.o

$(TARGET):	$(OBJ)
				gcc $(CFLAGS) -o $@ $^

clean: $(OBJ)
			rm -f $^ $(TARGET)
