TARGET = main
CC = gcc
LDIR = ../lib
CFLAGS = -Wall -I$(LDIR)

_DEPS = forest.h intvector.h
DEPS = $(patsubst %, $(LDIR)/%, $(_DEPS))
OBJ = $(LDIR)/forest.o $(LDIR)/intvector.o main.o


%.o: %.c $(DEPS)
		$(CC) $(CFLAGS) -c -o $@ $<

$(TARGET):	$(OBJ)
				gcc $(CFLAGS) -o $@ $^

clean: $(OBJ)
			rm -f $^ $(TARGET)
