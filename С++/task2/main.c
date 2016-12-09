#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <signal.h>

#define BS 16             // Bufer size = 16
#define DELIM " \r\n"    // Need for strtok()


FILE *f;
char *fileName;
unsigned int id = 0;
unsigned int rowCount = 0;
char *line;


// The correct name consists only of alphabetic characters in both registers
int checkName(char *stream)
{
  if (stream == NULL) return 0;
  int len = strlen(stream);
  int i;
  for (i = 0; i < len; i++)
    {
      if (!(((int) stream[i] >= (int) 'A' && (int) stream[i] <= (int) 'Z') ||
            ((int) stream[i] >= (int) 'a' && (int) stream[i] <= (int) 'z')))
        {
          return 0;
        }
    }
  return 1;
}

// The phone contains the correct digits, any number of dashes, not consecutive,
//  not more than one pair of parentheses and can start with a plus.
int checkNumber(char *stream)
{
  if (stream == NULL) return 0;
  unsigned int len = strlen(stream) - 1;
  unsigned int i;
  int prevChar = 0;
  int countOfBrackets = 0;
  int isOpenBrackets = 0;
  for (i = 0; i < len; i++)
    {
      if (!(isdigit(stream[i]) || (int) stream[i] == '-' || (int) stream[i] == '+' || (int) stream[i] == '(' ||
            (int) stream[i] == ')'))
        return 0;
      if (stream[i] == '+' && i != 0)
        return 0;
      int tmp = prevChar;
      prevChar = (stream[i] == '-');
      if (tmp == prevChar && prevChar)
        return 0;
      if (stream[i] == '(')
        {
          if (isOpenBrackets)
            {
              return 0;
            }
          else
            {
              isOpenBrackets = 1;
              countOfBrackets++;
            }
        }
      else if (stream[i] == ')')
        {
          if (isOpenBrackets)
            {
              countOfBrackets++;
            }
          else
            {
              return 0;
            }
        }
      if (countOfBrackets > 2)
        return 0;
    }
  return 1;
}

// Converts a string to lowercase string
char *stringToLower(char *str)
{
  char *newStr = (char *) malloc(strlen(str) + 1);

  int i = 0;
  for (; str[i]; i++)
    {
      newStr[i] = (char) tolower(str[i]);
    }

  newStr[i] = '\0';
  return newStr;
}

// Delete spaces, brackets and hyphen from number
char *reformatNumber(char *str)
{
  // ok!
  unsigned int len = strlen(str) + 1;
  char *number = (char *) malloc(len * sizeof(char));
  int i;
  int j = 0;
  for (i = 0; i < len; i++)
    {
      if (!(str[i] == '-' || str[i] == '(' || str[i] == ')' || str[i] == '+'))
        {
          number[j++] = str[i];
        }
    }
  number = (char *) realloc(number, j * sizeof(char));
  return number;
}


// functuion create(), find(), delete(), change() witout arguments
// because we write stdin in a global variable *line
void create()
{
  char *name = strtok(NULL, DELIM);
  char *number = strtok(NULL, DELIM);
  if ((checkName(name)) && (checkNumber(number)))
    {
      name = stringToLower(name);
      number = reformatNumber(number);
      fprintf(f, "%d %s %s\n", ++id, name, number);
      ++rowCount;
    }
  else
    {
      printf("Incorrect name\\number\n");
    }
}

// Reads a line from the file \ stream and writes to the variable line
void readLine(int type)
{
  // type == 1 <=> Read from file
  // type == 0 <=> Read from stdin
  char *buffer = malloc(BS);
  line = calloc(1, 1);
  unsigned int len = 0;

  for (; ;)
    {
      len += BS;
      line = realloc(line, len);
      if (type)
        {
          if (fgets(buffer, BS, f) == NULL)
            break;
        }
      else
        {
          if (fgets(buffer, BS, stdin) == NULL)
            break;
        }
      unsigned int l = strlen(buffer);
      strcat(line, buffer);
      if (buffer[l - 1] == '\n')
        {
          break;
        }
    }
  free(buffer);
}

// Auxiliary function for func find().
// Reads all lines from a file and makes the necessary comparison
void readFile(char *stream, int type)
{
  // type == 1 <=> find by number
  // type == 0 <=> find by name
  rewind(f);
  for (; ;)
    {
      readLine(1);
      char *curID, *curName, *curNumber;
      curID = strtok(line, DELIM);
      curName = strtok(NULL, DELIM);
      curNumber = strtok(NULL, DELIM);
      if (curID == NULL)
        return;
      if (type)
        {
          if (!(strcmp(curNumber, stream)))
            {
              printf("%s %s %s\n", curID, curName, curNumber);
            }
        }
      else
        {
          if (strstr(curName, stream))
            {
              printf("%s %s %s\n", curID, curName, curNumber);
            }
        }
      free(line);

    }

  free(stream);
}


void find()
{
  char *stream = strtok(NULL, DELIM);
  if (checkName(stream))
    {
      stream = stringToLower(stream);
      readFile(stream, 0);
    }
  else if (checkNumber)
    {
      stream = reformatNumber(stream);
      readFile(stream, 1);
    }
  else
    {
      printf("Incorrect input\n");
    }
}

// Need to get id, because strtok breaks  the orihinal string
char *getID(char *stream)
{
  char *strcp = (char *) malloc((strlen(stream)) * sizeof(char));
  strcpy(strcp, stream);
  char *curID = strtok(strcp, DELIM);
  return curID;
}


void delete()
{
  char *thisID = strtok(NULL, DELIM);
// error processing
  if(thisID == NULL)
    {
      printf("Incorrect id\n");
      return;
    }
  int check = atoi(thisID);
  if(check == 0 || check > id)
    {
      printf("Incorect input\n");
      return;
    }
// Read file
  int i, j;
  char **fileArray = (char **) malloc(rowCount * sizeof(char *));
  rewind(f);
  for(
    i = 0;
    i<rowCount;
    i++)
    {
      readLine(1);
      unsigned int len = strlen(line) + 1;
      fileArray[i]=(char *)
                   malloc(len
                          * sizeof(char));
      for(
        j = 0;
        j<len;
        j++)
        {
          fileArray[i][j] = line [j];
        }
      free(line);
    }
  fclose(f);
// Rewrite file
  f = fopen(fileName, "w+");
  for(
    i = 0;
    i<rowCount;
    i++)
    {
      char *curID = getID(fileArray[i]);
      if(!(
            strcmp(curID, thisID
                  )))
        {
          continue;
        }
      fprintf(f,
              "%s", fileArray[i]);
    }
  for(
    i = 0;
    i<rowCount;
    i++)
    {
      free(fileArray[i]);
    }
  free(fileArray);
  --
  rowCount;
}


void change()
{
  char *thisID = strtok(NULL, DELIM);
  char *command = strtok(NULL, DELIM);
  char *stream = strtok(NULL, DELIM);
  // Error processing
  if (stream == NULL || command == NULL || thisID == NULL)
    {
      printf("Incorrect input\n");
      return;
    }
  int check = atoi(thisID);
  if (check == 0 || check > id)
    {
      printf("Incorrect id\n");
      return;
    }
  int type, i, j;
  // type==0 <=> findByName
  // type==1 <=> findByNumber
  if (!(strcmp(command, "name")))
    {
      type = 0;
    }
  else if (!(strcmp(command, "number")))
    {
      type = 1;
    }
  else
    {
      printf("Incorect input\n");
      return;
    }
  if (type)
    {
      if (checkNumber(stream))
        {
          stream = reformatNumber(stream);
        }
      else
        {
          printf("Incorect number\n");
          return;
        }
    }
  else
    {
      if (checkName(stream))
        {
          stream = stringToLower(stream);
        }
      else
        {
          printf("Incorrect name\n");
          return;
        }
    }
  // Read file
  char **fileArray = (char **) malloc(rowCount * sizeof(char *));
  rewind(f);
  for (i = 0; i < rowCount; i++)
    {
      readLine(1);
      unsigned int len = strlen(line) + 1;
      fileArray[i] = (char *) malloc(len * sizeof(char));
      for (j = 0; j < len; j++)
        {
          fileArray[i][j] = line[j];
        }
      free(line);
    }
  fclose(f);
  // Rewrite in file
  f = fopen(fileName, "w+");
  for (i = 0; i < rowCount; i++)
    {
      char *curID = getID(fileArray[i]);
      if (!(strcmp(curID, thisID)))
        {
          char *curID = strtok(fileArray[i], DELIM);
          char *curName = strtok(NULL, DELIM);
          char *curNumber = strtok(NULL, DELIM);
          if (type)
            {
              fprintf(f, "%s %s %s\n", curID, curName, stream);
            }
          else
            {
              fprintf(f, "%s %s %s\n", curID, stream, curNumber);
            }
        }
      else
        {
          fprintf(f, "%s", fileArray[i]);
        }
    }
  for (i = 0; i < rowCount; i++)
    {
      free(fileArray[i]);
    }
  free(fileArray);
}


void close(void)
{
  fclose(f);
  exit(0);
}


void getCommand()
{
  // fix buf with input "\n"
  if (strlen(line) == 1 && line[0] == '\n')
    {
      printf("Error, empty input\n");
      return;
    }
  char *cmd = strtok(line, DELIM);
  if (!(strcmp(cmd, "find")))
    {
      find();
    }
  else if (!(strcmp(cmd, "create")))
    {
      create();
    }
  else if (!(strcmp(cmd, "delete")))
    {
      delete ();
    }
  else if (!(strcmp(cmd, "change")))
    {
      change();
    }
  else if (!(strcmp(cmd, "exit")))
    {
      close();
    }
  else
    {
      printf("Incorrect input from command\n");
    }
}


// Saves changes in the file
void sigintHandler(int code)
{
  getCommand();
  fclose(f);
  exit(0);
}

int main(int argc, const char **argv)
{
  signal(SIGINT, sigintHandler);
  if (argc < 2)
    {
      printf("Can't read filename\n");
      return 0;
    }
  f = fopen(argv[1], "r+");
  if (f == NULL)
    {
      f = fopen(argv[1], "w+");
      if (f == NULL)
        {
          printf("Can't create a file");
          //exit(1);
        }
    }
  size_t len = strlen(argv[1]);
  fileName = (char *) malloc((len + 1) * sizeof(char));
  strcpy(fileName, argv[1]);
  fileName[len] = '\0';
  for (; ;)
    {
      readLine(0);
      getCommand();
      fflush (stdout);
      free(line);
    }
  return 0;
}
