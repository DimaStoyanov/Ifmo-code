#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>

#define BUF_SZ 1024
#define FILENM_SZ 128

int is_regular_file(const char * path);
int is_dir_exist(const char * path);
void copy_dir(char * source, char * destination);
void copy_file(char * source, char * destination);

int is_regular_file(const char *path)
{
    struct stat path_stat;
    stat(path, &path_stat);
    return S_ISREG(path_stat.st_mode);
}

int is_dir_exist(const char * path)
{
  DIR *dir = opendir(path);
  int is_dir;
  if(dir)
    is_dir = 1;
  else if (ENOENT == errno)
    is_dir = 0;
  else
  {
    return 0;
  }
  closedir(dir);
  return is_dir;
}

// Recourcisvely copy all regular files
void copy_dir(char * source, char * destination)
{

    char * dirn = (char *) malloc(FILENM_SZ);
    DIR *dir = NULL;
    struct dirent *direntp;
    char *s = (char *) malloc(FILENM_SZ);
    char *d = (char *) malloc(FILENM_SZ);
    char *ss = (char *) malloc(FILENM_SZ);
    char *dd = (char *) malloc(FILENM_SZ);
    strcat(strcat(s, source), "/");
    strcat(strcat(d, destination), "/");
    strcpy(ss, s);
    strcpy(dd, d);
    mkdir(destination, 0777);


    if((dir = opendir(s)) == NULL)
    {
      perror("Can't open dir");
      exit(1);
    }

    while((direntp = readdir(dir)))
    {
        if(!strcmp(direntp->d_name, ".") || !(strcmp(direntp->d_name, ".."))) continue;
        strcat(dd, direntp->d_name);
        strcat(ss, direntp->d_name);
        if(is_regular_file(ss))
        {
          printf("Copy file %s %s\n", ss, dd);
          copy_file(ss,dd);
        } else if(is_dir_exist(ss))
          copy_dir(ss,dd);

        strcpy(ss, s);
        strcpy(dd, d);
    }
    closedir(dir);

    free(s); free(ss); free(d); free(dd); free(dirn);

}


void copy_file(char * source, char * destination)
{
  if(!strcmp(source, destination)) printf("Source and destination file are eqvivalent\n");

  int fsd = open(source, O_RDONLY);
  int fdd = open(destination, O_WRONLY | O_CREAT | O_TRUNC, 0777);

  if(fsd < 0)
  {
    perror("Can't open source file");
    exit(1);
  }


  if(fdd < 0)
  {
    if(errno == EISDIR)
    {
        char *dir = (char *) malloc(128);
        fdd = open(strcat(strcat(strcat(dir,destination), "/"), source),
         O_WRONLY | O_CREAT | O_TRUNC, S_IRWXU);
        if(fdd < 0)
        {
          printf("FILE %s", dir);
          perror ("Can't create destination file");
          exit(1);
        }
    } else
    {
      printf("FILE %s\n", destination);
      perror("Can't create destination file");
      exit(1);
    }
  }

  char * buf = (char *) malloc(BUF_SZ);
  int buf_read;

  while ((buf_read = read(fsd, buf, BUF_SZ))) {
    if(write(fdd, buf, buf_read) != buf_read)
    {
      perror("Error writing file");
      exit(1);
    }
  }

  free(buf);
  if(close(fsd)) perror("Can't close source file");
  if(close(fdd)) perror("Can't close destination file");


}


int main(int argc, char ** argv)
{
    if(argc < 3)
  {
    printf("Ussage:\n");
    printf("Copy one file to another file : `cp file1 file2`\n");
    printf("Copy many file to directory : `cp file1 file2 ... fileN dir`\n");
    printf("Copy directory to directory : `cp -r dir1 dir2`\n");
    return 0;
  }

  switch (argc)
  {
    // file to file/dir
    case 3:
        if(is_regular_file(argv[1]))
          copy_file(argv[1], argv[2]);
        else
          printf("To copy directory use -r flag\n");
      break;
    // dir to dir
    case 4:
        if(!strcmp(argv[1], "-r"))
        {
          // If destination dir is already exists, `cp a/ b/` -> b/a
          if(is_dir_exist(argv[3]))
          {
            char * path = (char *) malloc(FILENM_SZ);
            strcpy(path, argv[3]);
            strcat(strcat(path, "/"), argv[2]);
            copy_dir(argv[2], path);
          } else
            copy_dir(argv[2], argv[3]);
          break;

        }
    // Many files to dir
    default:
    {
      int i;
      for(i = 1; i < argc - 1; i++)
      {
        copy_file(argv[i], argv[argc - 1]);
      }
    }
  }
}
