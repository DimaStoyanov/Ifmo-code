TARGET = main
CC = gcc
CFLAGS = -Wall -pthread
OBJ = main.o
%.o: %.c $(DEPS)
		$(CC) $(CFLAGS) -c -o $@ $<

$(TARGET):	$(OBJ)
				gcc $(CFLAGS) -o $@ $^

clean: $(OBJ)
			rm -f $^ $(TARGET)
